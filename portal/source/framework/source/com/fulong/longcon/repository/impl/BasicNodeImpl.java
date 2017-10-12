package com.fulong.longcon.repository.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.GeneralNode;
import com.fulong.longcon.repository.Item;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyIterator;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.core.InternalNode;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.NodeDao;
import com.fulong.longcon.repository.dao.NodeTypeDao;
import com.fulong.longcon.repository.data.NodeData;
import com.fulong.longcon.repository.property.BinaryProperty;
import com.fulong.longcon.repository.property.BooleanProperty;
import com.fulong.longcon.repository.property.DateProperty;
import com.fulong.longcon.repository.property.DoubleProperty;
import com.fulong.longcon.repository.property.LongProperty;
import com.fulong.longcon.repository.property.PathProperty;
import com.fulong.longcon.repository.property.ReferenceProperty;
import com.fulong.longcon.repository.property.StringProperty;
import com.fulong.longcon.repository.property.TextProperty;

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
 * @author lishaobo
 * @version 1.0
 */
public class BasicNodeImpl extends GeneralNode implements InternalNode {
	
	private InternalRepository service;
	private NodeData data;
	private String Id;
	private Map<String, Property> _properties;
	private NodeDefinition _definition;
	private Set<NodeDefinition> _mixinDefinitions;

	public BasicNodeImpl(InternalRepository service, NodeData data) {
		this.data = data;
		this.Id = data.getID();
		this.service = service;
		this._properties = null;
		this._mixinDefinitions = null;
		this._definition = null;
	}

	public String getName() {
		return this.data.getName();
	}

	private Set<NodeDefinition> mixinDefinitions() {
		if (this._mixinDefinitions != null)
			return this._mixinDefinitions;
		this._mixinDefinitions = Collections
				.synchronizedSet(new HashSet<NodeDefinition>());
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeTypeDao dao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
			String[] defIDs = dao.findMixinNodeDefinitions(this.getID());
			if ((defIDs != null) && (defIDs.length >= 0))
				for (int i = 0; i < defIDs.length; i++) {
					NodeDefinition definition = this.service
							.getDefinitionManager().getDefinition(defIDs[i]);
					if (definition != null)
						this._mixinDefinitions.add(definition);
				}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		return this._mixinDefinitions;

	}

	/**
	 * 实现的时候需要按照先mixin，后primary的顺序，保证当primary大纲的属性与mixin大纲的属性ID相同时，
	 * 加载的是primary的大纲
	 * 
	 * @param nodeDefinition
	 *            Node
	 * @return NodeProperties
	 */
	private Map<String, Property> getProperties() {
		if (this._properties != null)
			return this._properties;
		this._properties = Collections
				.synchronizedMap(new LinkedHashMap<String, Property>());
		// load mixin node properties;
		NodeDefinition[] definitions = this.getMixinDefinitions();
		for (int i = 0; i < definitions.length; i++) {
			for (Iterator<PropertyDefinition> defs = definitions[i]
					.propertyDefinitions(); defs.hasNext();) {
				PropertyDefinition def = (PropertyDefinition) defs.next();
				Property property = this.makeProperty(def);
				if (property != null)
					this._properties.put(def.getID(), property);
			}
		}
		// load primary node properties;
		if (getDefinition() != null) {
			for (Iterator<PropertyDefinition> defs = getDefinition()
					.propertyDefinitions(); defs.hasNext();) {
				PropertyDefinition def = (PropertyDefinition) defs.next();
				Property property = this.makeProperty(def);
				if (property != null)
					this._properties.put(def.getID(), property);
			}
		}
		return this._properties;
	}

	private Property makeProperty(PropertyDefinition def) {
		Property property = null;
		switch (def.getType()) {
		case PropertyType.STRING:
			property = new StringProperty(this.service, this, def);
			break;
		case PropertyType.BINARY:
			property = new BinaryProperty(this.service, this, def);
			break;
		case PropertyType.BOOLEAN:
			property = new BooleanProperty(this.service, this, def);
			break;
		case PropertyType.DATE:
			property = new DateProperty(this.service, this, def);
			break;
		case PropertyType.DOUBLE:
			property = new DoubleProperty(this.service, this, def);
			break;
		case PropertyType.LONG:
			property = new LongProperty(this.service, this, def);
			break;
		case PropertyType.PATH:
			property = new PathProperty(this.service, this, def);
			break;
		case PropertyType.REFERENCE:
			property = new ReferenceProperty(this.service, this, def);
			break;
		case PropertyType.TEXT:
			property = new TextProperty(this.service, this, def);
			break;
		default:
			break;
		}

		return property;
	}

	/**
	 * 
	 * @param definition
	 *            NodeDefinition
	 * @param name
	 *            String
	 * @return Node
	 */
	public Node addNode(NodeDefinition definition, String name) {
		return this.addNode(definition, name, null);
	}

	/**
     * 
     */
	public Node addNode(NodeDefinition definition, String name, String id) {
		if ((id != null) && this.service.getNode(id) != null)
			throw new IllegalArgumentException("Node with id " + id
					+ " has exists. ");

		NodeData child = new NodeData();

		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			child.setParentID(this.getID());
			child.setName(name);
			child.setOrderNo(this.getMaxOrderNo(this.getID(),name) + 1);
			child.setID(id);
			child.setDefinitionID(definition.getID());
			child.setMain(true);
			dao.insert(child);

		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		return new BasicNodeImpl(this.service, child);
	}

	/**
	 * 
	 * @return ContentRepository
	 */
	public NodeDefinition getDefinition() {
		if (this._definition != null)
			return this._definition;
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		String definitionID = "";
		try {
			factory.open();
			NodeTypeDao dao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
			definitionID = dao.findPrimaryByPKID(this.getID());
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		this._definition = this.service.getDefinitionManager().getDefinition(
				definitionID);
		return this._definition;
	}

	/**
	 * 设置节点定义
	 * 
	 * @param definition
	 *            String
	 */
	public void setDefinition(String definitionId) {
		NodeDefinition definition = this.service.getDefinitionManager()
				.getDefinition(definitionId);
		if (definition != null) {
			setDefinition(definition);
		}
	}

	/**
	 * 设置节点定义
	 * 
	 * @param definition
	 *            NodeDefinition
	 */
	public void setDefinition(NodeDefinition definition) {
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			NodeData[] getData = dao.findById(this.getID());
			NodeTypeDao typeDao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
			// 删除该节点与参数分类的对应关系
			typeDao.deleteByDefinitionAndPKID(definition.getID(), this.getID());
			// 删除该节点与主分类的对应关系
			typeDao.deletePrimary(this.getID());
			
			NodeData data = new NodeData();
			data.setID(this.getID());
			data.setDefinitionID(definition.getID());
			data.setParentID(getData[0].getParentID());
			data.setName(getData[0].getName());
			data.setMain(true);
			data.setOrderNo(getData[0].getOrderNo());
			dao.insert(data);
			
			
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		this._definition = definition;
		// reset properties to null, force reload when used;
		this._properties = null;
	}

	/**
	 * 
	 * @return String
	 */
	public String getID() {
		return this.Id;
	}

	public Node getParent() {
		return this.getRepository().getNode(this.data.getParentID());
	}

	/**
	 * 设置父结点
	 * 
	 * @return Node
	 */
	public void setParent(Node parent) {
		String oldparentID = this.data.getParentID();
		this.data.setParentID(parent.getID());
		this.data.setOrderNo(this.getMaxOrderNo(parent.getID(), this.data.getName()));
		this.save(oldparentID, null);
	}

	/**
	 * 保存nodedata的orderno数据
	 */
	private void save() {
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			dao.update(this.data);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}
	
	/**
	 * 保存nodedata的parentID或者name数据
	 */
	private void save(String oldparentID,String oldname) {
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			dao.update(this.data,oldparentID,oldname);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	/**
	 * 获取所有的子节点
	 * 
	 * @return NodeIterator
	 */
	public NodeIterator<Node> getNodes() {
		return new ChildNodeIterator(this, this.service);
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return NodeIterator
	 */
	public NodeIterator<Node> getNodes(String name) {
		return new ChildNodeIterator(this, this.service, name);
	}

	/**
	 * 
	 * @return NodeIterator
	 */
	public NodeIterator<Node> getAllNodes() {
		return new ChildNodeRecIterator(this, this.service);
	}

	public NodeIterator<Node> getAllNodes(String name) {
		return new ChildNodeIterator(this, this.service, name, true);
	}

	/**
	 * 获取所有引用这个节点的其他节点
	 * 
	 * @return PropertyIterator
	 */
	public PropertyIterator<?> getReferences() {
		return new ReferencePropertyIterator(this, this.service);
	}

	/**
	 * 获得该节点在其兄弟节点的序号
	 * 
	 * @return int
	 */
	public int getOrderNo() {
		return this.data.getOrderNo();
	}

	/**
	 * 设置该节点在其兄弟节点的序号
	 * 
	 * @param orderNo
	 *            int
	 */
	public void setOrderNo(int orderNo) {
		this.data.setOrderNo(orderNo);
		this.save();
	}
	
	/**
	 * 设置该节点为最大序号
	 * 
	 * @param orderNo
	 *            int
	 */
	public void setMaxOrderNo(int orderNo) {
		this.data.setOrderNo(orderNo);
		this.save(null,null);
	}

	/**
	 * parentID和name组合下的最大序号
     * @param parentID String
     * @param name String
	 * @return int 
	 */
	public int getMaxOrderNo(String parentID,String name) {
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			int num = dao.getMaxOrderNo(parentID,name);
			return num;
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}
	/**
	 * 获取子节点
	 * 
	 * @param name
	 *            String
	 * @return Node
	 */
	public Node getNode(String path) {
		if (path == null)
			return null;
		if (path.length() == 0)
			return this;
		if (path.equalsIgnoreCase("/"))
			return this;
		if (path.indexOf('/') < 0)
			return this.getChildNode(path);
		if (path.startsWith("/")) {
			Node parent = this.getRoot();
			return parent.getNode(path.substring(1));
		}
		String name = path.substring(0, path.indexOf('/'));
		Node child = this.getChildNode(name);
		if (child == null)
			return null;
		String subpath = path.substring(path.indexOf('/') + 1);
		return child.getNode(subpath);
	}

	protected Node getRoot() {
		Node parent = this.getParent();
		Node relative = parent;
		while (parent != null && !parent.getID().equals("root")) {
			relative = parent;
			parent = parent.getParent();
		}
		return relative;
	}

	protected boolean isRoot() {
		Node parent = this.getParent();
		if (parent == null)
			return true;
		if (parent.getID() == "root")
			return true;
		return false;
	}

	protected Node getChildNode(String name) {
		int index = 0;
		if (name.indexOf('[') > 0) {
			index = Integer.parseInt(name.substring(name.indexOf('[') + 1, name
					.indexOf(']')));
			name = name.substring(0, name.indexOf('['));
		}
		return this.getChildNode(name, index);
	}

	/**
	 * 获取子节点
	 * 
	 * @param ID
	 *            String
	 * @return Node
	 */
	protected Node getChildNode(String name, int index) {
		NodeData[] data;
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			data = dao.findByParent(this.data.getID(), name, index, 1);
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		if (data.length == 0)
			return null;
		return this.service.makeNode(data[0]);
	}

	/**
	 * 
	 * @return Repository
	 */
	public Repository getRepository() {
		return this.service;
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return Property
	 */
	public Property getProperty(String name) {
		// 兼容遗留代码,如果包含.在name中,说明是reference属性节点的属性
		if (name.indexOf('.') > 0) {
			String[] splits = name.split("\\.");
			String sub = name.substring(name.indexOf('.') + 1);
			Property ref = this.getProperty(splits[0]);
			if (ref != null) {
				if (ref.getReference() != null)
					return ref.getReference().getProperty(sub);
				else
					return null;
			}
			Node node = this.getNode(splits[0]);
			if (node != null)
				return node.getProperty(sub);
			return null;
		}
		Property property = this.getProperties().get(name);
		// 侦测是否有NodeDefinition发生改变，则需要重新加载节点定义
		if ((property == null) && this.containsProperty(name)) {
			// this._properties.clear();
			this._properties = null;
			property = this.getProperties().get(name);
		}
		return property;

	}

	/**
	 * 判断是否包含指定节点的属性
	 * 
	 * @param name
	 *            String
	 * @return boolean
	 */
	private boolean containsProperty(String name) {
		if(this.getDefinition()==null)
			return false;
		if (this.getDefinition().getPropertyDefinition(name) != null)
			return true;
		NodeDefinition[] definitions = this.getMixinDefinitions();
		for (int i = 0; i < definitions.length; i++) {
			if (definitions[i].getPropertyDefinition(name) != null)
				return true;
		}
		return false;
	}

	/**
	 * 获取所有属性
	 * 
	 * @return Iterator
	 */
	public Iterator<Property> properties() {
		return this.getProperties().values().iterator();
	}

	/**
	 * 判断在指定路径下是否有节点存在
	 * 
	 * @param path
	 *            String 子节点的相对或者绝对路径
	 * @return boolean
	 */
	public boolean hasNode(String path) {
		String[] splits = path.split("\\");
		Node node = this;
		for (int i = 0; (i < splits.length - 1) && (node != null); i++) {
			node = node.getNode(splits[i]);
		}
		if (node == null)
			return false;
		return true;
	}

	/**
	 * 判断在指定路径下是否有属性存在
	 * 
	 * @param path
	 *            String 子节点的相对或者绝对路径
	 * @return boolean
	 */
	public boolean hasProperty(String path) {
		return false;
	}

	/**
	 * 获取附加的格式定义，一个内容可以有多个格式定义
	 * 
	 * @return NodeDefinition[]
	 */
	public NodeDefinition[] getMixinDefinitions() {
		Collection<NodeDefinition> definitions = this.mixinDefinitions();
		return (NodeDefinition[]) definitions
				.toArray(new NodeDefinition[definitions.size()]);
	}

	/**
	 * 判断是否是nodeDefinition类型,需要判断是否是继承关系
	 * 
	 * @param nodeDefinitionName
	 *            String
	 * @return boolean
	 */
	public boolean isNodeType(String nodeDefinitionName) {
		if (this.getDefinition() == null)
			return false;
		if (this.getDefinition().isNodeType(nodeDefinitionName))
			return true;
		NodeDefinition[] definitions = this.getMixinDefinitions();
		for (int i = 0; i < definitions.length; i++)
			if (definitions[i].isNodeType(nodeDefinitionName))
				return true;
		return false;
	}

	/**
	 * 添加附加类型，设置成功后，节点自动拥有指定类型下的所有属性， 如果属性和现有的属性有重复的，则保持现有属性
	 * 
	 * @param nodeDefinitionName
	 *            String
	 */
	public void addMixinDefinition(NodeDefinition definition) {
		if (!this.canAddMixinDefinition(definition))
			return;
		/*
		 * throw new RepositoryException(
		 * "Unable to add definition to this node.");
		 */
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			NodeData data = new NodeData();
			NodeData[] getData = dao.findById(this.getID());
			data.setID(this.getID());
			data.setDefinitionID(definition.getID());
			data.setParentID(getData[0].getParentID());
			data.setName(getData[0].getName());
			data.setMain(false);
			data.setOrderNo(getData[0].getOrderNo());
			dao.insert(data);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		this.mixinDefinitions().add(definition);
		this._properties = null;
	}

	/**
	 * 删除类型，删除后，这个类型对应的属性将被删除，如果某个属性在已有的类型中还在使用，则保留
	 * 
	 * @param nodeDefinitionName
	 *            String
	 */
	public boolean removeMixin(NodeDefinition definition) {
		if (!this.mixinDefinitions().contains(this.service.getDefinitionManager().getDefinition(definition.getID())))
			return false;
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeTypeDao dao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
			dao.deleteByDefinitionAndPKID(definition.getID(), this.getID());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		this.mixinDefinitions().remove(definition);
		// force reload properties;
		this._properties = null;
		return true;
	}

	/**
	 * modified by liulei at 2009-12-11 //TODO TEST 获得该节点对应的node_type
	 * 需要对应的NodeTypeDao
	 */
	public int getNodeType(String defid) {
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeTypeDao dao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
			return dao.getType(this.getID(), defid);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	/**
	 * 判断是否可以将nodeDefinitionName类型设置为这个节点的附加类型.
	 * 如果节点类型中已经包含了definition指定的类型，则不能再添加
	 * 
	 * @param nodeDefinitionName
	 *            String
	 * @return boolean
	 */
	public boolean canAddMixinDefinition(NodeDefinition definition) {
		return !this.isNodeType(definition.getID());
	}

	/**
	 * 返回节点或者属性的深度，即从根节点到当前节点/属性的距离
	 * <ul>
	 * <li>根节点深度为 0 .
	 * <li>节点/属性的深度为其父节点的深度+1.
	 * </ul>
	 * 
	 * @return 节点或者属性的深度，即从根节点到当前节点/属性的距离.
	 */
	public int getDepth() {
		if (this.equals(this.getRepository().getRootNode()))
			return 0;
		if (this.getParent() == null)
			return 0;
		return this.getParent().getDepth() + 1;
	}

	/**
	 * 获取指定深度的父节点
	 * 
	 * @param depth
	 *            int 0<=depth<= getDepth()，0为根节点，以此类推
	 * @return Item
	 */
	public Item getAncestor(int depth) {
		int MaxDepth = this.getDepth();
		if ((depth < 0) || (depth > MaxDepth))
			return null;
		else {
			Item parent = this.getParent();
			while (--depth != 0)
				parent = parent.getParent();
			return parent;
		}
	}

	/**
	 * 复制一个自身的拷贝，包括所有的属性，只有pkid是自动生成的，其他的信息和原件一样
	 * 
	 * @return Node
	 */
	public Node clone() {
		NodeData data = null;
		JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			data = dao.copy(this.getID());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		return this.service.getNode(data.getID());
	}

	/**
	 * 获取本节点在父节点下的属性定义
	 * 
	 * @return ChildNodeDefinition
	 */
	public ChildNodeDefinition getChildNodeDefinition() {
		if (this.getName() == null)
			return null;
		return (ChildNodeDefinition) this.getParent().getDefinition()
				.getPropertyDefinition(this.getName());

	}

	/**
	 * 获取节点路径 节点路径为： 根节点的路径为/ 当前节点路径=父节点路径+/+ 本节点的name + [当前节点的OrderNo]
	 * 如果当前节点在父节点下name是唯一的，则 [当前节点的OrderNo]在路径上可以省略 *
	 * 
	 * @return String 类似 /a/b/c[1]/d/e[10]的路径
	 */
	public String getPath() {
		if (this.getParent() == null)
			return "/";
		if (this.getParent().getID().equals("root"))
			return "/";
		String path = this.getParent().getPath();
		if (!path.endsWith("/"))
			path += "/";
		ChildNodeDefinition def = this.getChildNodeDefinition();
		// 父节点没有明确定义子节点的类型,则路径为父节点路径+name;
		if (def == null)
			return path + this.getName();
		// 父节点中定义的子节点类型是唯一的；
		if (!def.isMultiple())
			return path + this.getName();
		// 如果根节点不唯一
		return path + this.getName() + "[" + this.getOrderNo() + "]";
	}

	/**
	 * 修改内容关联的复合属性
	 */
	public void setName(String name) {
		String oldname = this.data.getName();
		this.data.setName(name);
		this.data.setOrderNo(this.getMaxOrderNo(this.data.getParentID(),name));
		this.save(null,oldname);
	}
}
