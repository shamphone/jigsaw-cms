package com.fulong.longcon.repository.definition;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.JdbcDaoProvider;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.longcon.repository.ValueFactory;
import com.fulong.longcon.repository.core.InternalNodeDefinitionManager;
import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.dao.PropertyDefinitionDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;
import com.fulong.longcon.repository.data.PropertyDefinitionData;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class NodeDefinitionManagerImpl implements InternalNodeDefinitionManager {
	private static final Log log = LogFactory.getLog(NodeDefinitionManagerImpl.class);
	
	private Map<String, NodeDefinition> definitions;
	private DataSource dataSource;
	private JdbcDaoProvider provider;
	private ValueFactory valueFactory;
	
	public NodeDefinitionManagerImpl() {
	}

	public void init() throws Exception{
		PropertiesDaoProvider provider = new PropertiesDaoProvider();
		provider.loadMappingFile("com.fulong.longcon.repository.definition.dao",dataSource);
		this.provider = provider;
		this.loadDefinitions();
		log.info("ready.");
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

	public void setValueFactory(ValueFactory valueFactory) {
		this.valueFactory = valueFactory;
	}

	/**
	 * 设置dao映射。在ForumService.getInstance()方法中通过java bean的反射机制来调用这个方法。
	 * 
	 * @param dao
	 *            String
	 */
	public void setDao(String dao) {
		
	}

	public JdbcDaoFactory newJdbcDaoFactory() {
		return new JdbcDaoFactory(this.dataSource, this.provider);
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return NodeDefinition
	 */
	public NodeDefinition createDefinition(String name) {
		return this.createDefinition(name, this.getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME));
	}

	public NodeDefinition createDefinition(NodeDefinition superDef) {
		if (superDef == null)
			superDef = this.getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
		return this.createDefinition("", superDef);
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return NodeDefinition
	 */
	public NodeDefinition createDefinition(String name, NodeDefinition superDef) {
		return this.createDefinition(null,name,superDef);
	}
	/* 
	 * 
	 * @param id 指定ID，不能和现有的ID重复
	 * @param name 指定名称。
	 * @param superDef
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-20上午11:30:09
	 */
	public NodeDefinition createDefinition(String id, String name, NodeDefinition superDef) {
		if (superDef == null)
			superDef = this.getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
		if(id!=null && this.getDefinition(id)!=null)
			throw new IllegalArgumentException("NodeDefinition with id "+ id+" already exists, craete failed.");
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDefinitionDao dao = (NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);
			NodeDefinitionData data = new NodeDefinitionData();
			data.setID(id);
			data.setName(name);
			data.setCreateTime(new Date());
			data.setSuperID(superDef.getID());
			dao.insert(data);
			//dao.copyAll(data.getID(), superDef.getID());
			NodeDefinition definition = new NodeDefinitionImpl(this, data);
			this.definitions.put(definition.getID(), definition);
			return definition;
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		
	}
	/**
	 * 删除结点，实现上仅将这个结点标记为删除，并未实际删除数据。
	 * 
	 * @param definition
	 *            String
	 */
	public boolean delete(NodeDefinition definition) {
		// 如果是系统大纲，则不支持删除
		if (definition.isProtected())
			throw new RepositoryException("error.delete.protected.definition");
		if(definition.getInheritDefinitions().hasNext())
			throw new RepositoryException("error.definition.has.children");
		if(definition.getReferencedPropertyDefinitions().hasNext())
			throw new RepositoryException("error.definition.is.referenced");
		
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDefinitionDao dao = (NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);
			dao.delete(definition.getID());
			this.definitions.remove(definition.getID());
			return true;
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	/**
	 * 获取指定ID的内容库 create the database table if this repository is not exists
	 * yet.
	 * 
	 * @param pkid
	 *            String
	 * @return NodeDefinition
	 */
	public NodeDefinition getDefinition(String pkid) {
		if (pkid == null) {
			return null;
		}
		NodeDefinition definition = (NodeDefinition) this.definitions.get(pkid);
		return definition;
	}

	

	/**
	 * 大纲定义列表 按照PKID排序
	 */
	private Map<String,NodeDefinition> loadDefinitions() {
		if (this.definitions != null) {
			return this.definitions;
		}
		this.definitions = Collections.synchronizedMap(new LinkedHashMap<String,NodeDefinition>());
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDefinitionDao dao = (NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);
			NodeDefinitionData[] data = dao.findAll();
			for (int i = 0; i < data.length; i++) {
				NodeDefinition newDef = new NodeDefinitionImpl(this, data[i]);
				this.definitions.put(newDef.getID(), newDef);
			}
			log.info(data.length + " definitions have been loaded");
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		return this.definitions;
	}

	public NodeDefinitionIterator definitions() {
		return new NodeDefinitionIteratorImpl(this.loadDefinitions().values());
	}

	/**
	 * 复制一个大纲定义，深度复制其中的属性定义
	 * 
	 * @param name
	 *            String
	 * @return NodeDefinition
	 */
	public NodeDefinition copyDefinition(String name, NodeDefinition source) {
		return this.copyDefinition(name, source, false);	
	}

	public NodeDefinition copyDefinition(String name, NodeDefinition source, boolean recursive) {
		return this.copyDefinition(name, source, recursive, source.getSuperDefinition());		
	}

	public NodeDefinition copyDefinition(String name, NodeDefinition source, boolean recursive, NodeDefinition destParent) {
		JdbcDaoFactory factory = this.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDefinitionDao dao = (NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);
			NodeDefinitionData data = new NodeDefinitionData();
			data.setName(name);
			data.setCreateTime(new Date());
			if (destParent != null)
				data.setSuperID(destParent.getID());
			else if (source.getSuperDefinition() != null)
				data.setSuperID(source.getSuperDefinition().getID());
			dao.insert(data);

			// 复制大纲的工作流，权限等信息，参见其存储过程
			//dao.copyAll(source.getID(), data.getID());

			NodeDefinition definition = new NodeDefinitionImpl(this, data);
			// 复制原大纲定义的所有属性
			PropertyDefinitionDao proDao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);
			// 取得所有属性
			PropertyDefinitionData[] proDatas = proDao.findDefs(source.getID());
			for (int m = 0; m < proDatas.length; m++) {
				// 属性定义对应的大纲为新建的大纲定义
				proDatas[m].setNodeDefinitionID(definition.getID());
				proDao.insert(proDatas[m]);
			}
			if (recursive) {
				NodeDefinitionIterator children = source.getInheritDefinitions(!recursive);
				for (Iterator<NodeDefinition> iterator = children; iterator.hasNext();) {
					NodeDefinition entry = (NodeDefinition) iterator.next();
					this.copyDefinition(entry.getName(), entry, true, definition);
				}
			}

			this.definitions.put(definition.getID(), definition);
			return definition;
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	public ValueFactory getValueFactory() {
		return this.valueFactory;
	}

	public ValidatorParser getValidatorParser() {
		return ValidatorParser.getParser();
	}

}
