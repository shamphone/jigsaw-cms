package com.fulong.lucene.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.core.io.Resource;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;   

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.core.QueryCreator;
import com.fulong.longcon.repository.value.ReferenceValue;
import com.fulong.lucene.BinaryConverter;
import com.fulong.lucene.ConverterFactory;
import com.fulong.lucene.IndexManager;
import com.fulong.lucene.NodeConverter;

/**
 * 
 * LuceneIndexManager
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-23
 * @modifier songbo 2010-10-25
 */
public class LuceneIndexManager implements IndexManager, QueryCreator {

	private Repository repository;
	private Analyzer analyzer;
	private FSDirectory directory;
	private FSDirectory[] threadDirectory;
	private RAMDirectory ramDir;
	private RAMDirectory[] threadRamDir;
	private String searchDirectory;
	private IndexWriter ramWriter;
	private IndexWriter[] threadRamWriter;
	private IndexWriter fsWriter;
	private IndexWriter[] threadFsWriter;
	public int threadNum;
	private int mergeRamToFs;
	private int nodeNum;
	private int[] threadNodeNum;
	private int mergeFactor = 10;
	private int maxBufferedDocs = IndexWriter.DEFAULT_MAX_BUFFERED_DOCS;
	private double ramMB = IndexWriter.DEFAULT_RAM_BUFFER_SIZE_MB;
	private Object fsWriterLock = new Object();

	private static Log log = LogFactory.getLog(LuceneIndexManager.class);

	public LuceneIndexManager() throws IOException {
		//analyzer = new IKAnalyzer();
		//analyzer = new MaxWordAnalyzer();
		ramDir = new RAMDirectory();
	}
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	
	public void setMergeRamToFs(int mergeRamToFs) {
		this.mergeRamToFs = mergeRamToFs;
	}

	/**
	 * 初始化创建索引物理地址目录
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		File indexDir = new File(searchDirectory);
		directory = null;
		if (directory == null) {
			if (!indexDir.exists()) {
				indexDir.mkdirs();
				log.info("directory is created in " + indexDir.getPath()
						+ " for search index directory.");
			}
			if (!indexDir.isDirectory()) {
				throw new IllegalArgumentException(
						"Error in search index config. directory " + indexDir
								+ " not found!");
			}

			directory = FSDirectory.open(indexDir);
			if (!IndexReader.indexExists(directory)) {
				IndexWriter writer = new IndexWriter(directory, this.analyzer,
						true, IndexWriter.MaxFieldLength.UNLIMITED);
				writer.close();
			} else {
				if (IndexWriter.isLocked(directory)) {
					log.info("Index directory locked, try unlock it...");
					IndexWriter.unlock(directory);
					log.info("Index directory unlocked.");
				}
			}
			log.info("Use " + indexDir.getPath() + " for searching index.");
		}
		repository.getQueryManager().register(Query.LUCENE, this);

		threadDirectory = new FSDirectory[threadNum];
		threadRamDir = new RAMDirectory[threadNum];
		threadRamWriter = new IndexWriter[threadNum];
		threadFsWriter = new IndexWriter[threadNum];
		threadNodeNum = new int[threadNum];
		for (int i = 0; i < threadNum; i++) {
			threadRamDir[i] = new RAMDirectory();
			if (threadDirectory[i] == null) {
				threadDirectory[i] = this.threadDirectory(i);
			}
		}
	}

	/**
	 * 获取索引所在的目录
	 * 
	 * @return
	 */
	public FSDirectory getDirectory() {
		return this.directory;
	}

	/**
	 * 对节点删除索引
	 * 
	 * @param node
	 *            Node
	 * @throws IOException
	 */
	public void deleteIndex(Node node) throws IOException {
		IndexWriter writer = null;
		NodeIterator<Node> children = node.getAllNodes();
		try {
			writer = this.createWriter();
			if (children.getSize() < 20)
				while (children.hasNext()) {
					Node child = children.nextNode();
					writer.deleteDocuments(new Term(FIELD_ID, child.getID()));
				}
			writer.deleteDocuments(new Term(FIELD_ID, node.getID()));
		} catch (IOException ex) {
			if (writer != null)
				writer.rollback();
			log.trace(ex);
		} finally {
			if (writer != null)
				writer.close();
		}

	}

	/**
	 * 删除失效的索引
	 * 
	 * @param nodeid
	 * @throws IOException
	 */
	public void deleteIndexByDefID(String defId) throws IOException {
		IndexWriter writer = null;
		try {
			writer = this.createWriter();
			writer.deleteDocuments(new Term(FIELD_DEFINITION_ID, defId));
		} catch (IOException ex) {
			if (writer != null)
				writer.rollback();
			log.trace(ex);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * 删除失效的索引
	 * 
	 * @param nodeid
	 * @throws IOException
	 */
	public void deleteIndexByNodeID(Value nodeId) throws IOException {
		IndexWriter writer = null;
		try {
			writer = this.createWriter();
			writer.deleteDocuments(new Term(FIELD_ID, nodeId.toString()));
		} catch (IOException ex) {
			if (writer != null)
				writer.rollback();
			log.trace(ex);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * 适用于单线程批量创建索引并优化
	 * 
	 * @param it
	 * @param properties
	 * @throws IOException
	 */
	public void createIndex(NodeIterator<Node> it, String[] properties)
			throws IOException {
		ramWriter = new IndexWriter(ramDir, analyzer, true,
				IndexWriter.MaxFieldLength.UNLIMITED);
		fsWriter = this.createWriter();
		nodeNum = 0;
		long startIndexesTime = new Date().getTime();
		try {
			for (; it.hasNext();) {
				Node node = it.next();

				NodeIterator<Node> children = node.getAllNodes();
				if (children.getSize() < 20) // threshold for child num.
					while (children.hasNext()) {
						Node child = children.nextNode();
						fsWriter.deleteDocuments(new Term(FIELD_ID, child
								.getID()));
					}
				fsWriter.deleteDocuments(new Term(FIELD_ID, node.getID()));

				Document document = buildIndex(node, properties);
				ramWriter.addDocument(document);
				nodeNum++;
				if (nodeNum > mergeRamToFs) {
					ramWriter.close();
					fsWriter.addIndexesNoOptimize(new Directory[] { ramDir });
					ramWriter = new IndexWriter(ramDir, analyzer, true,
							IndexWriter.MaxFieldLength.UNLIMITED);
					nodeNum = 0;
				}
			}
		} catch (IOException ex) {
			if (fsWriter != null) {
				fsWriter.rollback();
				ramWriter.rollback();
				ramWriter.close();
				fsWriter.close();
			}
			log.trace(ex);
		}
		if (fsWriter != null) {
			ramWriter.close();
			fsWriter.addIndexesNoOptimize(new Directory[] { ramDir });
			long endIndexesTime = new Date().getTime();
			log.info("创建索引花费了 " + (endIndexesTime - startIndexesTime) + " 毫秒!");
			long startOptimizeTime = new Date().getTime();
			fsWriter.optimize();
			long endOptimizeTime = new Date().getTime();
			log.info("优化索引花费了 " + (endOptimizeTime - startOptimizeTime)
					+ " 毫秒!");
			fsWriter.close();
		}
	}

	/**
	 * 适用于单线程对单个节点创建索引并优化
	 * 
	 * @param node
	 * @param properties
	 * @throws IOException
	 */
	public void createIndex(Node node, String[] properties) throws IOException {
		fsWriter = this.createWriter();
		try {
			NodeIterator<Node> children = node.getAllNodes();
			if (children.getSize() < 20) // threshold for child num.
				while (children.hasNext()) {
					Node child = children.nextNode();
					fsWriter.deleteDocuments(new Term(FIELD_ID, child.getID()));
				}
			fsWriter.deleteDocuments(new Term(FIELD_ID, node.getID()));

			Document document = buildIndex(node, properties);
			fsWriter.addDocument(document);
		} catch (IOException ex) {
			if (fsWriter != null) {
				fsWriter.rollback();
				fsWriter.close();
			}
			log.trace(ex);
		} finally {
			if (fsWriter != null) {
				fsWriter.optimize();
				fsWriter.close();
			}
		}
	}

	/**
	 * 适用于多线程批量创建索引
	 * 
	 * @param it
	 * @param properties
	 * @throws IOException
	 */
	public void createIndex(Iterator<Node> it, String[] properties,
			int currentThread) throws IOException {
		if (currentThread == 0) {
			fsWriter = this.createWriter();
		}

		threadRamWriter[currentThread] = new IndexWriter(
				threadRamDir[currentThread], analyzer, true,
				IndexWriter.MaxFieldLength.UNLIMITED);
		threadFsWriter[currentThread] = this
				.createWriter(threadDirectory[currentThread]);
		threadNodeNum[currentThread] = 0;
		try {
			for (; it.hasNext();) {
				Node node = null;
				node = it.next();
				Document document = buildIndex(node, properties);
				threadRamWriter[currentThread].addDocument(document);
				threadNodeNum[currentThread]++;
				if (threadNodeNum[currentThread] > mergeRamToFs) {
					threadRamWriter[currentThread].close();
					threadFsWriter[currentThread]
							.addIndexesNoOptimize(new Directory[] { threadRamDir[currentThread] });
					threadRamWriter[currentThread] = new IndexWriter(
							threadRamDir[currentThread], analyzer, true,
							IndexWriter.MaxFieldLength.UNLIMITED);
					threadNodeNum[currentThread] = 0;
				}
				NodeIterator<Node> children = node.getAllNodes();
				if (children.getSize() < 20) // threshold for child num.
					while (children.hasNext()) {
						Node child = children.nextNode();
						synchronized (fsWriterLock) {
							fsWriter.deleteDocuments(new Term(FIELD_ID, child
									.getID()));
						}
					}
				synchronized (fsWriterLock) {
					fsWriter.deleteDocuments(new Term(FIELD_ID, node.getID()));
				}
			}
		} catch (IOException ex) {
			if (threadFsWriter[currentThread] != null) {
				threadFsWriter[currentThread].rollback();
				threadRamWriter[currentThread].rollback();
				threadRamWriter[currentThread].close();
				threadFsWriter[currentThread].close();
			}
			if (fsWriter != null) {
				fsWriter.rollback();
				fsWriter.close();
			}
			log.trace(ex);
		}
		if (threadFsWriter[currentThread] != null) {
			threadRamWriter[currentThread].close();
			threadFsWriter[currentThread]
					.addIndexesNoOptimize(new Directory[] { threadRamDir[currentThread] });
			threadFsWriter[currentThread].close();
		}
	}

	/**
	 * 合并多线程索引 优化
	 * 
	 * @throws IOException
	 */
	public void addIndexesOptimize() throws IOException {
		if (fsWriter != null) {
			fsWriter.addIndexesNoOptimize(threadDirectory);
			fsWriter.optimize();
			fsWriter.close();
		}
	}

	private FSDirectory threadDirectory(int currentThread) throws IOException {
		FSDirectory threadDirectory = null;
		File indexDir = new File(searchDirectory + "/thread" + currentThread);
		if (threadDirectory == null) {
			if (!indexDir.exists()) {
				indexDir.mkdirs();
				log.info("threadDirectory is created in " + indexDir.getPath()
						+ " for search index directory.");
				Runtime.getRuntime().exec(
						"attrib " + "\"" + indexDir.getAbsolutePath() + "\""
								+ " +H");
			}
			if (!indexDir.isDirectory()) {
				throw new IllegalArgumentException(
						"Error in search index config. directory " + indexDir
								+ " not found!");
			}

			threadDirectory = FSDirectory.open(indexDir);
			IndexWriter writer = new IndexWriter(threadDirectory,
					this.analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
			writer.close();
			log.info("Use " + indexDir.getPath() + " for threadindex.");
		}
		return threadDirectory;

	}

	/**
	 * 
	 * @param node
	 *            Node
	 * @return Document
	 * @throws IOException
	 */
	protected Document buildIndex(Node node, String[] properties)
			throws IOException {
		return this.buildIndex(node, new Document(), properties);
	}

	/**
	 * 
	 * @param node
	 * @param document
	 * @param properties
	 * @return Document
	 * @throws IOException
	 */
	protected Document buildIndex(Node node, Document document,
			String[] properties) throws IOException {
		document.add(new Field(FIELD_ID, node.getID(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		document.add(new Field(FIELD_NAME, node.getName(), Field.Store.YES,
				Field.Index.ANALYZED));
		this.indexProperties(node, document, properties);
		this.indexParents(node, document);
		this.indexNodeDefinitions(node, document);
		return document;
	}

	/**
	 * 对节点定义建立索引
	 * 
	 * @param node
	 * @param document
	 */
	protected void indexNodeDefinitions(Node node, Document document) {
		Set<String> built = new HashSet<String>();
		NodeDefinition definition = node.getDefinition();
		while (definition != null) {
			built.add(definition.getID());
			document.add(new Field(FIELD_DEFINITION_ID, definition.getID(),
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			definition = definition.getSuperDefinition();
		}
		NodeDefinition[] definitions = node.getMixinDefinitions();
		for (int i = 0; i < definitions.length; i++) {
			definition = definitions[i];
			while (definition != null) {
				if (!built.contains(definition.getID())) {
					built.add(definition.getID());
					document
							.add(new Field(FIELD_DEFINITION_ID, definition
									.getID(), Field.Store.YES,
									Field.Index.NOT_ANALYZED));
				}
				definition = definition.getSuperDefinition();
			}
		}
	}

	/**
	 * 对节点的父节点建立索引
	 * 
	 * @param node
	 * @param document
	 */
	protected void indexParents(Node node, Document document) {
		Node parent = node.getParent();
		while (parent != null) {
			document.add(new Field(FIELD_PARENT_ID, parent.getID(),
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			parent = parent.getParent();
		}
	}

	/**
	 * 对节点属性建立索引
	 * 
	 * @param node
	 * @param props
	 * @param document
	 */
	protected void indexProperties(Node node, Document document, String[] props)
			throws IOException {
		StringBuffer all = new StringBuffer("");
		boolean ifAnalyzed = true;
		for (Iterator<Property> properties = node.properties(); properties
				.hasNext();) {
			Property property = properties.next();
			ifAnalyzed = true;
			if (property.getType() == 1) {
				if (props != null)
					for (int i = 0; i < props.length; i++) {
						if (property.getID().equals(props[i]))
							ifAnalyzed = false;
					}
			}
			String ret = this.indexProperty(property, document, ifAnalyzed,
					node);
			if (ret != null)
				all.append(ret);
		}
		// 全文建索引
		document.add(new Field(FIELD_ALL, all.toString(), Field.Store.NO,
				Field.Index.ANALYZED));
		// orderNo建索引
		document.add(new NumericField(FIELD_ORDERNO, Field.Store.YES, true)
				.setDoubleValue(node.getOrderNo()));
	}

	/**
	 * 对指定属性建立索引
	 * 
	 * @param property
	 * @param document
	 * @param ifAnalyzed
	 * @return
	 */
	protected String indexProperty(Property property, Document document,
			boolean ifAnalyzed, Node node) throws IOException {
		StringBuffer all = new StringBuffer("");
		Value[] values = property.getValues();
		for (int i = 0; i < values.length; i++)
			if (values[i] != null)
				switch (property.getType()) {
				case PropertyType.NAME:
				case PropertyType.BOOLEAN:
					this.indexSimpleValue(property.getDefinition().getID(),
							values[i].getString(), document);
					break;
				case PropertyType.LONG:
					this.indexLongValue(property.getDefinition().getID(),
							values[i].getLong(), document);
					break;
				case PropertyType.DOUBLE:
					this.indexDoubleValue(property.getDefinition().getID(),
							values[i].getDouble(), document);
					break;
				case PropertyType.PATH:
					Node child = property.getParent().getNode(
							values[i].getString());
					if (child != null)
						this.indexReferenceValue(property.getDefinition()
								.getID(), child, document);
					break;
				case PropertyType.DATE:
					if (property.getDate() != null)
						this.indexLongValue(property.getDefinition().getID(),
								values[i].getLong(), document);
					break;
				case PropertyType.BINARY:
					// 对于word\pdf等类型的变量也应该建立索引。
					// 假定任何有二进制字段的内容，必须有mime属性用来标记二进制内容的类型的。
					Property type = property.getParent().getProperty("mime");
					if ((type != null) && (type.getString() != null)) {
						InputStream is = property.getStream();
						if (is != null)
							try {
								String text = this.indexBinaryValue(property
										.getDefinition().getID(), type
										.getString(), is, document);
								all.append(text);
							} finally {
								is.close();
							}
					}
					break;
				case PropertyType.FIX:
					// 不对子节点建立索引，如有需要，由建索引服务来调用这个子节点的索引服务
					break;
				case PropertyType.REFERENCE:
					if(values[i].getType()==9){
						Node reference = ((ReferenceValue) values[i])
								.getReference();
						if (reference != null) {
							String text = this.indexReferenceValue(property
									.getDefinition().getID(), reference, document);
							if (text != null)
								all.append(text);
						}
						break;
					}
				default:
					if (ifAnalyzed)
						this.indexTextValue(property.getDefinition().getID(),
								values[i].getString(), document);
					else
						this.indexSimpleValue(property.getDefinition().getID(),
								values[i].getString(), document);
					all.append(values[i].getString());
				}
		return all.toString();
	}

	/**
	 * 对等建立索引
	 * 
	 * @param property
	 * @param value
	 * @param document
	 */
	protected void indexSimpleValue(String property, String value,
			Document document) {
		if ((value != null) && (value.length() > 0))
			document.add(new Field(property, value, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
	}

	/**
	 * 对等建立索引
	 * 
	 * @param property
	 * @param value
	 * @param document
	 */
	protected void indexBooleanValue(String property, String value,
			Document document) {
		if ((value != null) && (value.length() > 0))
			document.add(new Field(property, value, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
	}

	/**
	 * 对等建立索引
	 * 
	 * @param property
	 * @param value
	 * @param document
	 */
	protected void indexLongValue(String property, long value, Document document) {
		document.add(new NumericField(property).setLongValue(value));
	}

	/**
	 * 对等建立索引
	 * 
	 * @param property
	 * @param value
	 * @param document
	 */
	protected void indexDoubleValue(String property, double value,
			Document document) {
		document.add(new NumericField(property).setDoubleValue(value));
	}

	/**
	 * 索引引用值，对以资源文件作为附件的引用类型，还需要把引用值建立到原节点中。
	 * 
	 * @param property
	 * @param referenceId
	 * @param document
	 */
	protected String indexReferenceValue(String property, Node reference,
			Document document) {
		document.add(new Field(property, reference.getID(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		for (Iterator<String> keys = ConverterFactory.getInstance()
				.nodeConverterKeys(); keys.hasNext();) {
			String key = keys.next();
			if (reference.isNodeType(key)) {
				NodeConverter converter = ConverterFactory.getInstance()
						.getNodeConverter(key);
				String text = null;
				try {
					text = converter.convert(reference);
				} catch (IOException ex) {
					log.error("Error in building index for node " + reference
							+ " property_ID " + property + "_"
							+ reference.getID());
				}
				if (text != null && text.length() > 0) {
					this.indexTextValue(property, text, document);
					return text;
				}
			}
		}
		return null;

	}

	/**
	 * 索引二进制属性
	 * 
	 * @param property
	 * @param referenceId
	 * @param document
	 * @throws IOException
	 */
	protected String indexBinaryValue(String property, String type,
			InputStream is, Document document) throws IOException {
		BinaryConverter converter = ConverterFactory.getInstance()
				.getBinaryConverter(type);
		if (converter == null)
			return null;
		String text = null;
		try {
			text = converter.convert(is);
		} catch (IOException ex) {
			log.error("Error in building index for type " + type + " property "
					+ property + ".");
		}
		if ((text != null) && (text.length() > 0))
			document.add(new Field(property, text, Field.Store.YES,
					Field.Index.ANALYZED));
		return text;
	}

	/**
	 * 索引文本属性
	 * 
	 * @param property
	 * @param referenceId
	 * @param document
	 * @param ifAnalyzed
	 */
	protected void indexTextValue(String property, String text,
			Document document) {
		if (text != null && text.length() > 0)
			document.add(new Field(property, text, Field.Store.YES,
					Field.Index.ANALYZED));
	}

	private IndexWriter createWriter() throws IOException {
		IndexWriter writer = new IndexWriter(directory, analyzer, false,
				IndexWriter.MaxFieldLength.UNLIMITED);
		// 合并segment的频率 how often segment indices are merged by
		// addDocument().
		writer.setMergeFactor(mergeFactor);
		// 写入segment前ram中document的持有数量
		writer.setMaxBufferedDocs(maxBufferedDocs);
		// Determines the amount of RAM that may be used for buffering added
		// documents and deletions before they are flushed to the Directory.
		writer.setRAMBufferSizeMB(ramMB);

		writer.setMaxMergeDocs(Integer.MAX_VALUE);
		// 设置为false将不会使用复合索引文件
		// writer.setUseCompoundFile(false);
		return writer;
	}

	private IndexWriter createWriter(FSDirectory directory) throws IOException {
		IndexWriter writer = new IndexWriter(directory, analyzer, true,
				IndexWriter.MaxFieldLength.UNLIMITED);
		// 合并segment的频率 how often segment indices are merged by
		// addDocument().
		writer.setMergeFactor(mergeFactor);
		// 写入segment前ram中document的持有数量
		writer.setMaxBufferedDocs(maxBufferedDocs);
		// Determines the amount of RAM that may be used for buffering added
		// documents and deletions before they are flushed to the Directory.
		writer.setRAMBufferSizeMB(ramMB);

		writer.setMaxMergeDocs(Integer.MAX_VALUE);
		// 设置为false将不会使用复合索引文件
		// writer.setUseCompoundFile(false);
		return writer;
	}

	public void setMergeFactor(int mergeFactor) {
		this.mergeFactor = mergeFactor;
	}

	public void setMaxBufferedDocs(int maxBufferedDocs) {
		this.maxBufferedDocs = maxBufferedDocs;
	}

	public void setRamMB(double ramMB) {
		this.ramMB = ramMB;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public Analyzer getAnalyzer() {
		return this.analyzer;
	}

	public void setSearchDirectory(Resource searchDirectory) throws IOException {
		this.searchDirectory = searchDirectory.getFile().getAbsolutePath();
	}

	@Override
	public Query createQuery(NodeDefinition node) {
		return new LuceneQuery(this.repository, this, node);
	}
}
