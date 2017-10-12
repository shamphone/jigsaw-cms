package com.fulong.longcon.repository.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.cache.Cache;
import com.fulong.common.cache.CacheFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.JdbcDaoProvider;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeCollection;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionManager;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.QueryManager;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.longcon.repository.ValueFactory;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.NodeDao;
import com.fulong.longcon.repository.dao.NodeTypeDao;
import com.fulong.longcon.repository.dao.ReferenceValueDao;
import com.fulong.longcon.repository.data.NodeData;
import com.fulong.longcon.repository.data.NodePropertyData;

/**
 * <p>
 * Title: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class RepositoryImpl implements InternalRepository {
	private static final Log log = LogFactory.getLog(RepositoryImpl.class);
	private NodeDefinitionManager definitionManager;
	private ValueFactory valueFactory;
	private CacheFactory cacheFactory;
	private Cache nodes;
	private static final String ROOT_ID = "root";
	private Node root;
	private DataSource dataSource;
	private JdbcDaoProvider provider;
	//private String dao = "oracle";
	private QueryManager queryManager;

	public RepositoryImpl() {
	}

	public void init() throws Exception {
		this.nodes = this.cacheFactory.getCache(Node.class);
		PropertiesDaoProvider provider = new PropertiesDaoProvider();
		provider.loadMappingFile("com.fulong.longcon.repository.impl.dao", this.dataSource);
		this.provider = provider;
		this.root = this.getNode(ROOT_ID);
		log.info("Node Repository ready.");
	}

	/**
	 * 数据源
	 * 
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 设置dao映射。在ForumService.getInstance()方法中通过java bean的反射机制来调用这个方法。
	 * 
	 * @param dao
	 *            String
	 */
	public void setDao(String dao) {
		//this.dao = dao;
	}

	public JdbcDaoFactory newJdbcDaoFactory() {

		return new JdbcDaoFactory(this.dataSource, this.provider);
	}

	public ValidatorParser getValidatorParser() {
		return ValidatorParser.getParser();
	}

	/**
	 * 
	 * @param node
	 *            Node
	 * 
	 */
	public boolean delete(Node node) {
		if (node == null)
			return false;

		NodeData[] NodeData = null;
		NodePropertyData[] NodePropertyData = null;
		String[] childrenIDs = null;
		JdbcDaoFactory factory = this.newJdbcDaoFactory();

		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			NodeData = dao.findByParent(node.getParent().getID(),
					node.getName());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

		try {
			factory.open();
			ReferenceValueDao dao = (ReferenceValueDao) factory
					.getDao(ReferenceValueDao.class);
			NodePropertyData = dao.findReferenceByNodeId(node.getID());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			childrenIDs = dao.childrenIDs(node.getID());
			dao.deleteAll(node.getID());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

		// 将缓存中该节点和引用该节点的节点删除
		this.nodes.remove(node.getID());
		if ((NodePropertyData != null) && (NodePropertyData.length > 0)) {
			for (int i = 0; i < NodePropertyData.length; i++) {
				this.nodes.remove(NodePropertyData[i].getID());
			}
		}
		// 将缓存中该节点的子节点和引用子节点的节点删除
		if ((childrenIDs != null) && (childrenIDs.length > 0)) {
			for (int i = 0; i < childrenIDs.length; i++) {
				this.nodes.remove(childrenIDs[i]);
				try {
					factory.open();
					ReferenceValueDao dao = (ReferenceValueDao) factory
							.getDao(ReferenceValueDao.class);
					NodePropertyData = dao
							.findReferenceByNodeId(childrenIDs[i]);
				} catch (SQLException ex) {
					factory.rollback();
					throw new DatabaseException(ex);
				} finally {
					factory.close();
				}
				if ((NodePropertyData != null) && (NodePropertyData.length > 0)) {
					for (int n = 0; n < NodePropertyData.length; n++) {
						this.nodes.remove(NodePropertyData[n].getID());
					}
				}
			}
		}
		// 将缓存中和节点同父同名的节点清除
		if ((NodeData != null) && (NodeData.length > 0)) {
			for (int i = 0; i < NodeData.length; i++) {
				this.nodes.remove(NodeData[i].getID());
			}
		}
		return true;
	}

	/**
	 * 
	 * @param nodeId
	 *            String
	 * @return Node 该方法首先在缓存中查找node，如果不能找到，则通过Dao文件在数据库中查找
	 */
	public Node getNode(String nodeId) {
		if (nodeId == null) {
			return null;
		}
		Node node = (Node) this.nodes.get(nodeId);
		if (node != null) {
			return node;
		}
		NodeData data = null;
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			data = dao.findByID(nodeId);
			if (data == null) {
				return null;
			}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		node = this.makeNode(data);
		return node;
	}

	public ValueFactory getValueFactory() {
		return this.valueFactory;
	}

	/**
	 * 获取根结点
	 * 
	 * @return Node
	 */
	public Node getRootNode() {
		return this.root;
	}

	public QueryManager getQueryManager() {
		return this.queryManager;
	}

	public void setQueryManager(QueryManager manager) {
		this.queryManager = manager;
	}

	public void setCacheFactory(CacheFactory factory) {
		this.cacheFactory = factory;
	}

	public void setDefinitionManager(NodeDefinitionManager definitionManager) {
		this.definitionManager = definitionManager;
	}

	public void setValueFactory(ValueFactory valueFactory) {
		this.valueFactory = valueFactory;
	}

	public NodeDefinitionManager getDefinitionManager() {
		return definitionManager;
	}

	/**
	 * @todo
	 * @return NodeCollection
	 */
	public NodeCollection getNodes() {
		throw new UnsupportedOperationException();
	}

	public Node makeNode(NodeData datum) {
		if (datum == null)
			return null;
		Node node = (Node) this.nodes.get(datum.getID());
		if (node != null) {
			return node;
		}
		node = new BasicNodeImpl(this, datum);

		if (node != null)
			this.nodes.put(node.getID(), node);
		return node;
	}

	/**
	 * 
	 * @param absPath
	 *            String
	 * @return Node
	 */
	public Node getNodeByPath(String absPath) {
		String[] paths = absPath.split("/");
		Node node = this.root;
		for (int i = 0; i < paths.length && (node != null); i++) {
			if (!paths[i].equals("")) {
				String name = getPathName(paths[i]);
				NodeIterator<Node> iterator = node.getNodes(name);
				if (this.getPathIndex(paths[i]) != -1)
					iterator.skip(this.getPathIndex(paths[i]));
				node = iterator.nextNode();
			}
		}
		return node;
	}

	private long getPathIndex(String path) {
		int lPos = path.indexOf('[');
		int rPos = path.indexOf(']');
		if (lPos >= 0) {
			String index = path.substring(lPos, rPos);
			long result = Long.parseLong(index);
			return result;
		} else
			return -1;
	}

	private String getPathName(String path) {
		int lPos = path.indexOf('[');
		if (lPos >= 0) {
			String name = path.substring(0, lPos);
			return name;
		} else
			return path;
	}

	/**
	 * 清除当前大纲下内容的关联关系，但不删除内容。
	 * 
	 * @param definition
	 *            NodeDefinition
	 * @param recursive
	 *            boolean参数为true时，表示要递归清除大纲下的子孙大纲所有的内容关联关系
	 */
	public void removeNodes(NodeDefinition definition, boolean recursive) {
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			NodeTypeDao dao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
			if (!recursive)
				dao.deleteByDefinition(definition.getID());
			else
				dao.deleteByDefinitionRec(definition.getID());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	public Iterator<Node> getAllNodes(String DefId) {
        Collection<Node> nodes = new Vector<Node>(20);
        JdbcDaoFactory factory = this.newJdbcDaoFactory();
        try {
            factory.open();
            NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
            NodeData[] data = null;
            try {
				data = dao.findAllNodesByDefinition(DefId);
			} catch (SQLException e) {
				factory.rollback();
				throw new DatabaseException(e);
			}
            for (int i = 0; i < data.length; i++){
                Node node=this.makeNode(data[i]);
                nodes.add(node);
            }
            Iterator<Node> allnodes = nodes.iterator();
            return allnodes;
        } finally {
            factory.close();
        }
	}

	public void removeRefNode(String nodeID, String value, String property) {
		
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(ReferenceValueDao.class);
			dao.delete(nodeID, value, property);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		// 将缓存中的节点清除。
		this.nodes.remove(nodeID);
	}
	
	public void insertRefNode(String nodeID, String value, String property) {	
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(ReferenceValueDao.class);
			dao.insert(nodeID, property, 0, value);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}
}
