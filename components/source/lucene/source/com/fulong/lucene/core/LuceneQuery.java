package com.fulong.lucene.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.highlight.Encoder;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.htmlparser.util.ParserException;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.fulong.common.util.StringUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.repository.RowIterator;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFactory;
import com.fulong.lucene.IndexManager;

/**
 * < 全文检索的查询实现类>
 * 
 * < Title: Coolink协同工作支撑平台Lucene全文检索引擎 >
 * 
 * < Description: Coolink协同工作支撑平台Lucene全文检索引擎 >
 * 
 * < Copyright: 北京中科辅龙计算机技术股份有限公司 2010 >
 * 
 * < Company: 北京中科辅龙计算机技术股份有限公司 >
 * 
 * @author LJY
 * @author MALI 2010-6-18
 * @version 1.0.1
 */
public class LuceneQuery implements Query {
	private LuceneIndexManager manager;
	private BooleanQuery primitiveQuery;
	private Collection<SortField> sorts;	
	private Repository repository;
	// private NodeDefinition node;

	private Highlighter highlighter;
	private org.apache.lucene.search.Query query;
	private Sort sort;

	private Encoder encoder;
	private String seperator;
	private int fragmentLength;
	private int fragmentNumber;
	private Formatter formater;
	private ValueFactory valueFactory;
	private int hits = 1000;
	
	private NodeDefinition definition;

//	private static Log log = LogFactory.getLog(LuceneQuery.class);

	public LuceneQuery(Repository repository,LuceneIndexManager manager, NodeDefinition node) {
		this.repository = repository;
		// this.node = node;
		this.manager = manager;

		this.primitiveQuery = new BooleanQuery();
		this.sorts = new Vector<SortField>();
		//this.analyzer = this.manager.getAnalyzer();
		this.valueFactory = repository.getValueFactory();

		// 编码 SimpleHTMLEncoder 会自动过滤Html代码
		this.encoder = new HTMLEncoder();
		// 片断之间的分隔符
		this.seperator = "...";
		// 每个片段的长度
		this.fragmentLength = 20;
		// 显示多少个片段
		this.fragmentNumber = 5;
		// 缺省格式化器
		this.formater = new SimpleHTMLFormatter("<B>", "</B>");
		
		this.definition = node;
	}
	
	/**
	 * 
	 * @param keywords
	 *            String
	 */
	public void filterByKeywords(String keywords) {
		//QueryParser parser = new QueryParser(Version.LUCENE_20, IndexManager.FIELD_ALL, this.analyzer);
		if ((keywords != null) && (keywords.length() > 0)) {
			org.apache.lucene.search.Query primitive = null;
			try {
				//运用IKQueryParser，解决全文搜索中保留字搜索的问题  by mali 2010-06-17
				primitive = IKQueryParser.parse(IndexManager.FIELD_ALL,keywords);
			} catch (Exception ex) {
				throw new RepositoryException(ex);
			}
			this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST);
		}
	}

	/**
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            String
	 */
	public void filterByKeywords(String field, String keywords) {
		//QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, field,this.analyzer);
		if ((keywords != null) && (keywords.length() > 0)) {
			org.apache.lucene.search.Query primitive = null;
			try {
				//运用IKQueryParser，解决全文搜索中保留字搜索的问题  by mali 2010-06-17
				primitive = IKQueryParser.parse(field,keywords);
				this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST);
			} catch (Exception ex) {
				throw new RepositoryException(ex);
			}
		}
	}

	public void filterByKeywords(String field, String[] keywords) {
//		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, field,
//				this.analyzer);
		if ((keywords != null) && (keywords.length > 0)) {
			org.apache.lucene.search.Query primitive = null;
			try {
				BooleanQuery orQuery = new BooleanQuery();
				for (int i = 0; i < keywords.length; i++) {
					//运用IKQueryParser，解决全文搜索中保留字搜索的问题  by mali 2010-06-17
					primitive = IKQueryParser.parse(field,keywords[i]);
					orQuery.add(primitive, BooleanClause.Occur.SHOULD);
					// orQuery.add(primitive, false, false);
				}
				this.primitiveQuery.add(orQuery, BooleanClause.Occur.MUST);
			} catch (Exception ex) {
				throw new RepositoryException(ex);
			}
		}
	}
	
	/**
	 *同时搜索多个关键词。
	 * 
	 */
	public void filterByKeywords(String[] keywords) {
//		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT,
//				IndexManager.FIELD_ALL, this.analyzer);
		BooleanQuery orQuery = new BooleanQuery();
		for (String keyword : keywords) {
			org.apache.lucene.search.Query primitive = null;
			try {
				primitive = IKQueryParser.parse(IndexManager.FIELD_ALL,keyword);
				orQuery.add(primitive, BooleanClause.Occur.SHOULD);
			} catch (Exception ex) {
				throw new RepositoryException(ex);
			}
			this.primitiveQuery.add(orQuery, BooleanClause.Occur.MUST);
		}
	}

	
	/**
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            Value
	 */
	public void filterByProperty(String field, Value value) 
	{	
	
		PropertyDefinition property= this.definition.getPropertyDefinition(field);
    	if(property==null)
    		return;
    	org.apache.lucene.search.Query primitive = null;
        switch (property.getType()) {
        case PropertyType.DATE:
        case PropertyType.LONG:
        	if(value == null || value.getDate()==null){
        		this.primitiveQuery.add(NumericRangeQuery.newLongRange(field,Long.parseLong("0"),Long.parseLong("0"),true, true), BooleanClause.Occur.MUST);
        	}else{
        		this.primitiveQuery.add(NumericRangeQuery.newLongRange(field,value.getLong(),value.getLong(), true, true), BooleanClause.Occur.MUST);
        	}
        	break;
        case PropertyType.DOUBLE:
        	if(value == null || value.getDate()==null){
        		this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field,Double.parseDouble("0"),Double.parseDouble("0"), true, true), BooleanClause.Occur.MUST);
        	}else{
        		this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field,value.getDouble(),value.getDouble(), true, true), BooleanClause.Occur.MUST);
        	}
        	break;
        default:
        	if(value.getString()==null || value.getString().equals("")){
    			try {
					primitive = IKQueryParser.parse(field, "null");
				} catch (Exception ex) {
					throw new RepositoryException(ex);
				}
				this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST);
    		}else{
				try {
					primitive = IKQueryParser.parse(field, value.getString());
				} catch (Exception ex) {
					throw new RepositoryException(ex);
				}
				this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST);
			}
        }
	}
	
	public void filterByProperty(String property, String value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, boolean value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, long value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, Node value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, long from, long to) {
		Value ifrom = this.valueFactory.createValue(from);
		Value ito = this.valueFactory.createValue(to);
		this.filterByProperty(property, ifrom, ito);
	}

	public void filterByProperty(String property, double from, double to) {
		Value ifrom = this.valueFactory.createValue(from);
		Value ito = this.valueFactory.createValue(to);
		this.filterByProperty(property, ifrom, ito);
	}

	public void filterByProperty(String property, Calendar from, Calendar to) {
		Value ifrom = this.valueFactory.createValue(from);
		Value ito = this.valueFactory.createValue(to);
		this.filterByProperty(property, ifrom, ito);
	}



	/**
	 * 
	 * @param field
	 *            String
	 * @param min
	 *            String
	 * @param max
	 *            String
	 */
	public void filterByProperty(String field, Value min, Value max) {
		if (min == null) {
			if (max == null)
				return;
			else
				this.filterByToValue(field, max);
		} else {
			if (max == null)
				this.filterByFromValue(field, min);
			else {
				if (min.getType() == PropertyType.DATE || min.getType() == PropertyType.LONG) {
					this.primitiveQuery.add(NumericRangeQuery.newLongRange(field, min.getLong(),max.getLong(), true, true), BooleanClause.Occur.MUST);
				}else if(min.getType() == PropertyType.DOUBLE){
					this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field, min.getDouble(),max.getDouble(), true, true), BooleanClause.Occur.MUST);
				}
				else
					this.primitiveQuery.add(new TermRangeQuery(field, min
							.getString(), max.getString(), true, true),
							BooleanClause.Occur.MUST);
			}
		}
	}
	
	
	public void filterByDefinition(NodeDefinition definition) {
		//this.filterByProperty(IndexManager.FIELD_DEFINITION_ID, definition.getID());
		this.filterByDefinition(definition, false);
	}

	public void filterByDefinition(NodeDefinition definition, boolean recursive) {
		if(recursive){
			BooleanQuery orQuery = new BooleanQuery(); 
			orQuery.add(new TermQuery(new Term(IndexManager.FIELD_DEFINITION_ID, definition.getID())), BooleanClause.Occur.SHOULD);
			for(Iterator<NodeDefinition> iterator=this.definition.getInheritDefinitions(true);iterator.hasNext();){
	        	orQuery.add(new TermQuery(new Term(IndexManager.FIELD_DEFINITION_ID, iterator.next().getID())), BooleanClause.Occur.SHOULD);
	        }	
			this.primitiveQuery.add(orQuery, BooleanClause.Occur.MUST);
		}else{
			this.primitiveQuery.add(new TermQuery(new Term(IndexManager.FIELD_DEFINITION_ID, definition.getID())), BooleanClause.Occur.MUST);
		}
	}

	public void filterByParent(Node parent, boolean recursive) {
		if(parent == null)
			return;
		BooleanQuery orQuery = new BooleanQuery();
		if(recursive){
			//递归取出parentId,构建查询语句
			NodeIterator<Node> iterator = parent.getAllNodes();
			Set<String> set = new HashSet<String>();
			while(iterator.hasNext()){
				set.add(iterator.nextNode().getParent().getID());
			}
			for(Iterator<String> iterator2 = set.iterator();iterator2.hasNext();){
				orQuery.add(new TermQuery(new Term(IndexManager.FIELD_PARENT_ID, (String)iterator2.next())), BooleanClause.Occur.SHOULD);
			}
			this.primitiveQuery.add(orQuery,BooleanClause.Occur.MUST);
		}else{	
			orQuery.add(new TermQuery(new Term(IndexManager.FIELD_PARENT_ID, parent.getID())), BooleanClause.Occur.SHOULD);
			this.primitiveQuery.add(orQuery,BooleanClause.Occur.MUST);
		}	
	}

	public void filterByFromValue(String field, Value min) {
		if (min == null)
			return;
		if(min.getType() == PropertyType.DATE || min.getType() == PropertyType.LONG){
			this.primitiveQuery.add(NumericRangeQuery.newLongRange(field, min.getLong(),null, true, false), BooleanClause.Occur.MUST);
		}else if(min.getType() == PropertyType.DOUBLE){
			this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field, min.getDouble(),null, true, false), BooleanClause.Occur.MUST);
		} else {
			this.primitiveQuery.add(new TermRangeQuery(field, min.getString(),null, true, false), BooleanClause.Occur.MUST);
		}
	}

	/**
	 * 
	 * @param field
	 *            String
	 * @param max
	 *            Value
	 */
	public void filterByToValue(String field, Value max) 
	{
		if (max == null)
			return;
		if (max.getType() == PropertyType.DATE || max.getType() == PropertyType.LONG)
		{
			this.primitiveQuery.add(NumericRangeQuery.newLongRange(field,null,max.getLong(), false, true), BooleanClause.Occur.MUST);
		}else if(max.getType() == PropertyType.DOUBLE){
			this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field,null,max.getDouble(), false, true), BooleanClause.Occur.MUST);
		}
		else
		{
			this.primitiveQuery.add(new TermRangeQuery(field, null, max.getString(), false, true), BooleanClause.Occur.MUST);
		}
	}

	/**
	 * 大于（不包括等于）value的值
	 * 
	 * @param field
	 * @param value
	 */
	public void filterGreaterThan(String field, Value min) 
	{
		if (min == null)
			return;
		if (min.getType() == PropertyType.DATE || min.getType() == PropertyType.LONG)
		{
			this.primitiveQuery.add(NumericRangeQuery.newLongRange(field, min.getLong(),null, false, false), BooleanClause.Occur.MUST);
		}else if(min.getType() == PropertyType.DOUBLE){
			this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field, min.getDouble(),null, false, false), BooleanClause.Occur.MUST);
		}else 
		{
			this.primitiveQuery.add(new TermRangeQuery(field, min.getString(),null, false, false), BooleanClause.Occur.MUST);
		}
	}

	/**
	 * 小于（不包括等于）value的值
	 * 
	 * @param field
	 * @param value
	 */
	public void filterLessThan(String field, Value max)
	{
		if (max == null)
			return;
		if (max.getType() == PropertyType.DATE || max.getType() == PropertyType.LONG)
		{
			this.primitiveQuery.add(NumericRangeQuery.newLongRange(field,null,max.getLong(), false, false), BooleanClause.Occur.MUST);
		}else if(max.getType() == PropertyType.DOUBLE){
			this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field,null,max.getDouble(), false, false), BooleanClause.Occur.MUST);
		} 
		else 
		{
			this.primitiveQuery.add(new TermRangeQuery(field, null, max.getString(), false, false), BooleanClause.Occur.MUST);
		}
	}

	/**
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            Value
	 */
	public void filterByNotEqualValue(String field, Value value) {
		PropertyDefinition property= this.definition.getPropertyDefinition(field);
    	if(property==null)
    		return;
		org.apache.lucene.search.Query primitive = null;
        switch (property.getType()) {
        case PropertyType.DATE:
        case PropertyType.LONG:
        	if(value == null || value.getDate()==null){
        		this.primitiveQuery.add(NumericRangeQuery.newLongRange(field,Long.parseLong("0"),Long.parseLong("0"),true, true), BooleanClause.Occur.MUST_NOT);
        	}else{
        		this.primitiveQuery.add(NumericRangeQuery.newLongRange(field,value.getLong(),value.getLong(), true, true), BooleanClause.Occur.MUST_NOT);
        	}
        	break;
        case PropertyType.DOUBLE:
        	if(value == null || value.getDate()==null){
        		this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field,Double.parseDouble("0"),Double.parseDouble("0"), true, true), BooleanClause.Occur.MUST_NOT);
        	}else{
        		this.primitiveQuery.add(NumericRangeQuery.newDoubleRange(field,value.getDouble(),value.getDouble(), true, true), BooleanClause.Occur.MUST_NOT);
        	}
        	break;
        default:
			if (value.getString() == null || value.getString().equals("")) {
				try {
					primitive = IKQueryParser.parse(field, "null");
				} catch (Exception ex) {
					throw new RepositoryException(ex);
				}
				this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST_NOT);
			} else {
				try {
					primitive = IKQueryParser.parse(field, value.getString());
				} catch (Exception ex) {
					throw new RepositoryException(ex);
				}
				this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST_NOT);
			}
        }
	}
	
	public void filterByNotEqualProperty(String property, Calendar from,Calendar to) {
		this.filterGreaterThan(property, this.valueFactory.createValue(to));
		this.filterLessThan(property, this.valueFactory.createValue(from));
	}

	public void filterBySpecifiedDate(String property, Date date) {
		if (date == null)
			return;
//		Term term = new Term(property, DateTools.dateToString(date,DateTools.Resolution.DAY));
//		this.primitiveQuery.add(new TermQuery(term), BooleanClause.Occur.MUST);
		Calendar start = Calendar.getInstance();
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.HOUR_OF_DAY, 23);
		//end.add(Calendar.DAY_OF_YEAR, 1);
		start.set(Calendar.MINUTE, 59);
		start.set(Calendar.SECOND, 59);
		this.filterByProperty(property, start, end);
	}

	
	/**
	 * 
	 * @param field
	 *            String
	 * @param order
	 *            String
	 */

	public void sortByProperty(String field, boolean asc) {
		PropertyDefinition prop = null;
		if(field!= null && !field.equals("")){
			prop = this.definition.getPropertyDefinition(field);
			if (!"hits".equals(field) && (prop!=null)) {
				switch (prop.getType()) {
				case PropertyType.DATE:
				case PropertyType.LONG:
					//by mali 排序错误
					this.sorts.add(new SortField(field,SortField.LONG, !asc));
					break;
				case PropertyType.DOUBLE:	
					this.sorts.add(new SortField(field,SortField.DOUBLE,!asc));
					break;
				default:
					this.sorts.add(new SortField(field, Locale.CHINESE, !asc));
					break;
				}
			}
		}else{
			this.sortByOrdinal(asc);
		}
	}
	
	public void sortByOrdinal(boolean asc) {
		this.sorts.add(new SortField(IndexManager.FIELD_ORDERNO,SortField.DOUBLE, !asc));
	}
	
	public NodeIterator<Node> nodes() {
		return nodes(true);
	}

	public NodeIterator<Node> nodes(boolean recursive) {
		if(!recursive){
			this.filterByDefinition(this.definition);
			return new LuceneNodeIterator(this.execute(),this.manager);
		}else{
			this.filterByDefinition(definition, true);
			return new LuceneNodeIterator(this.execute(),this.manager);
		}	
	}

	protected String highlight(String field, String text) {
		try {
			String plainText = StringUtils.html2Text(text);
			StringBuffer buffer = new StringBuffer();
			String[] fragments = highlighter.getBestFragments(this.manager
					.getAnalyzer(), field, plainText, this.fragmentNumber);
			for (int i = 0; i < fragments.length; i++) {
				if (i > 0)
					buffer.append(this.seperator);
				buffer.append(fragments[i]);
			}
			String result = buffer.toString();
			if (result.length() == 0) {
				if (text.length() > 20 * 5)
					return text.substring(0, 20 * 5);
				return text;
			}
			return result;
		} catch (IOException ex) {
			return text;
		} catch (ParserException ex) {
			return text;
		} catch (InvalidTokenOffsetsException e) {
			throw new RepositoryException(e);
		}
	}

	/**
	 * 
	 * @return PageIterator
	 */
	public RowIterator execute() 
	{
		try
		{
			System.out.println(this.primitiveQuery);
			this.query = this.parseQuery(this.primitiveQuery);
			this.highlighter = new Highlighter(this.formater, this.encoder, new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(this.fragmentLength));
			if (this.sorts.size() == 0) 
			{
				this.sort = Sort.RELEVANCE;
			} 
			else
				this.sort = new Sort((this.sorts.toArray(new SortField[this.sorts.size()])));
			return new LuceneRowIterator(this.repository,this.manager, this);
		} 
		catch (IOException ex)
		{
			throw new RepositoryException(ex);
		}
	}

	private org.apache.lucene.search.Query parseQuery(org.apache.lucene.search.Query query) throws IOException
	{
		IndexReader reader = IndexReader.open(this.manager.getDirectory());
		org.apache.lucene.search.Query primitive = query.rewrite(reader);
		reader.close();
		return primitive;
	}

	protected Highlighter getHighlighter() {
		return this.highlighter;
	}

	protected Sort getSorts() {
		return this.sort;
	}

	protected org.apache.lucene.search.Query getQuery() {
		return query;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public void setFragmentLength(int fragmentLength) {

		this.fragmentLength = fragmentLength;
	}

	public void setFragmentNumber(int fragmentNumber) {

		this.fragmentNumber = fragmentNumber;
	}

	public void setFormater(Formatter formater) {

		this.formater = formater;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void filterByParent(String id, boolean recursive) {
		if (id == null)
			return;
		Node parent =this.repository.getNode(id);
		BooleanQuery orQuery = new BooleanQuery();
		if(parent == null)
			return;
		if(recursive){
			//递归取出parentId,构建查询语句
			NodeIterator<Node> iterator = parent.getAllNodes();
			Set<String> set = new HashSet<String>();
			while(iterator.hasNext()){
				set.add(iterator.nextNode().getParent().getID());
			}
			for(Iterator<String> iterator2 = set.iterator();iterator2.hasNext();){
				orQuery.add(new TermQuery(new Term(IndexManager.FIELD_PARENT_ID, (String)iterator2.next())), BooleanClause.Occur.SHOULD);
			}
			this.primitiveQuery.add(orQuery,BooleanClause.Occur.MUST);
		}else{	
			orQuery.add(new TermQuery(new Term(IndexManager.FIELD_PARENT_ID, parent.getID())), BooleanClause.Occur.SHOULD);
			this.primitiveQuery.add(orQuery,BooleanClause.Occur.MUST);
		}	
		
	}

	public void filterByNotEqualParent(Node parent, boolean recursive) {
		if(parent == null)
			return;
		if(recursive){
			//递归取出parentId,构建查询语句
			NodeIterator<Node> iterator = parent.getAllNodes();
			Set<String> set = new HashSet<String>();
			while(iterator.hasNext()){
				set.add(iterator.nextNode().getParent().getID());
			}
			for(Iterator<String> iterator2 = set.iterator();iterator2.hasNext();){
				this.filterByNotEqualValue(IndexManager.FIELD_PARENT_ID, this.valueFactory.createValue((String)iterator2.next()));
			}
		}else{	
			this.filterByNotEqualValue(IndexManager.FIELD_PARENT_ID, this.valueFactory.createValue(parent.getID()));
		}	
		
	}

	@Override
	public void filterByParentAndName(String id, String name, boolean recursive)
			throws SQLException {
		if (id == null||name == null)
			return;
		Node parent =this.repository.getNode(id);
		if(parent == null)
			return;
		BooleanQuery orQuery = new BooleanQuery();
		org.apache.lucene.search.Query primitive = null;
		if(recursive){
			//递归取出parentId,构建查询语句
			NodeIterator<Node> iterator = parent.getAllNodes();
			Set<String> set = new HashSet<String>();
			while(iterator.hasNext()){
				set.add(iterator.nextNode().getParent().getID());
			}
			for(Iterator<String> iterator2 = set.iterator();iterator2.hasNext();){
				orQuery.add(new TermQuery(new Term(IndexManager.FIELD_PARENT_ID, (String)iterator2.next())), BooleanClause.Occur.SHOULD);
			}
			this.primitiveQuery.add(orQuery,BooleanClause.Occur.MUST);
		}else{	
			orQuery.add(new TermQuery(new Term(IndexManager.FIELD_PARENT_ID,parent.getID())), BooleanClause.Occur.SHOULD);
			this.primitiveQuery.add(orQuery,BooleanClause.Occur.MUST);
		}
		try {
			primitive = IKQueryParser.parse(IndexManager.FIELD_NAME, name);
		} catch (Exception ex) {
			throw new RepositoryException(ex);
		}
		this.primitiveQuery.add(primitive, BooleanClause.Occur.MUST);
	}

	public void filterByPropertyCompareID(String def, String field, Value value) {
		// TODO 没有实现方法。不符合全文检索的应用场景。
	}

	public void filterByRefed(String def, String refPro, Node value) {
		// TODO 引用属性在建立索引时，被引用节点不创建索引。
	}

	public void filterByPropertyNotExist(String field) {
		// TODO 没有实现方法。不符合全文检索的应用场景。
		
	}
}
