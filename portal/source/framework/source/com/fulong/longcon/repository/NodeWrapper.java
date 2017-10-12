package com.fulong.longcon.repository;

import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;

import com.fulong.longcon.repository.property.ReadonlyProperty;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public abstract class NodeWrapper extends GeneralNode {
	private Node _node;

	public Node getNode() {
		return this._node;
	}

	public NodeWrapper(Node node) {
		this._node = node;
		this.markReadonlyProperties();
	}

	public Node getWrapperedNode(Class<NodeWrapper> type) {
		Node node = this;
		while (node instanceof NodeWrapper) {
			NodeWrapper wrapper = (NodeWrapper) node;
			if (type.isInstance(wrapper))
				return wrapper;
			node = wrapper.getNode();
		}
		throw new ClassCastException();
	}

	public String getID() {
		return this.getNode().getID();
	}

	public Repository getRepository() {
		return this.getNode().getRepository();
	}

	/**
	 * 获取所有引用这个节点的其他节点
	 * 
	 * @return PropertyIterator
	 */
	@SuppressWarnings("rawtypes")
	public PropertyIterator getReferences() {
		return this.getNode().getReferences();
	}

	/**
	 * 新增一个节点
	 * 
	 * @param definition
	 *            大纲定义
	 * @param name
	 *            String
	 * @return Node
	 */
	public Node addNode(NodeDefinition definition, String name) {
		return getNode().addNode(definition, name);
	}

	/**
	 * 新增一个节点
	 * 
	 * @param definition
	 *            大纲定义
	 * @param name
	 *            String
	 * @return Node
	 */
	public Node addNode(NodeDefinition definition, String name, String id) {
		return getNode().addNode(definition, name, id);
	}

	/**
	 * 节点定义的大纲
	 * 
	 * @return NodeDefinition
	 */
	public NodeDefinition getDefinition() {
		return getNode().getDefinition();
	}

	/**
	 * 
	 * @param defintion
	 *            NodeDefinition
	 */
	public void setDefinition(NodeDefinition defintion) {
		getNode().setDefinition(defintion);
	}

	/**
	 * 指定ID的节点
	 * 
	 * @param ID
	 *            String
	 * @return Node
	 */
	public Node getNode(String name) {
		return getNode().getNode(name);
	}

	/**
	 * 指定ID的节点
	 * 
	 * @param ID
	 *            String
	 * @return Node
	 */
	/*
	 * public Node getNode(String name, int index) { return
	 * getNode().getNode(name, index); }
	 */
	/**
	 * 遍历所有的子节点
	 * 
	 * @return Node
	 */
	public NodeIterator<Node> getNodes() {
		return getNode().getNodes();
	}

	/**
	 * 遍历所有的子节点
	 * 
	 * @return Node
	 */
	public NodeIterator<Node> getAllNodes() {
		return getNode().getAllNodes();
	}

	/**
	 * 遍历参数name属性的子节点
	 * 
	 * @param name
	 *            String
	 * @return NodeIterator
	 */
	public NodeIterator<Node> getNodes(String name) {
		return getNode().getNodes(name);
	}

	/**
	 *父结点
	 * 
	 * @return Node
	 */
	public Node getParent() {
		return getNode().getParent();
	}

	/**
	 * 设置父节点
	 * 
	 * @param parent
	 *            Node
	 */
	public void setParent(Node parent) {
		getNode().setParent(parent);
	}

	/**
	 * 获得该节点在其兄弟节点的序号
	 * 
	 * @return int
	 */
	public int getOrderNo() {
		return getNode().getOrderNo();
	}

	/**
	 * 设置该节点在其兄弟节点的序号
	 * 
	 * @param orderNo
	 *            int
	 */
	public void setOrderNo(int orderNo) {
		getNode().setOrderNo(orderNo);
	}

	/**
	 * 节点名称
	 * 
	 * @return String
	 */
	public String getName() {
		return this.getNode().getName();
	}

	/**
	 * 设置节点名称
	 */
	public void setName(String name) {
		this.getNode().setName(name);
	}

	/**
	 * 将指定属性标记为只读,并将属性值和名称绑定起来
	 * 
	 * @param property
	 *            String
	 * @param beanPropertyName
	 *            String
	 */
	protected boolean markReadonlyProperty(String propertyName,
			String beanPropertyName) {
		Property property = (Property) this.getNode().getProperty(propertyName);
		if (property == null)
			return false;
		if (!property.getDefinition().isReadonly())
			throw new IllegalArgumentException(propertyName
					+ "  is not readonly.");
		ReadonlyProperty readonly = (ReadonlyProperty) property;
		readonly.hook(this, beanPropertyName);
		return true;
	}

	/**
	 * 将指定属性标记为只读,并将属性值和名称绑定起来
	 * 
	 * @param property
	 *            String
	 * @param beanPropertyName
	 *            String
	 */
	protected boolean markReadonlyProperty(String propertyName) {
		return this.markReadonlyProperty(propertyName, propertyName);
	}

	/**
	 * 将指定属性标记为只读,并将属性值和名称绑定起来
	 * 
	 * @param property
	 *            String
	 * @param beanPropertyName
	 *            String
	 */
	protected void markReadonlyProperties() {
		for (Iterator<Property> iterator = this.getNode().properties(); iterator
				.hasNext();) {
			Property property = (Property) iterator.next();
			String name = property.getDefinition().getID();
			if (property.getDefinition().isReadonly()) {
				try {
					PropertyUtils.getPropertyDescriptor(this, name);
					((ReadonlyProperty) property).hook(this);
				} catch (Exception ex) {
					// just ignore this exception;
				}
			}
		}
	}

	/**
	 * 节点下内容的最大序号
     * @param parentID String
     * @param name String
	 * @return int {@code 没有实现？}
	 */
	public int getMaxOrderNo(String parentID,String name) {
		return this.getNode().getMaxOrderNo(parentID,name);
	}

	public NodeDefinitionManager getDefinitionManager() {
		return this.getNode().getRepository().getDefinitionManager();
	}

	public Property getProperty(String name) {
		return this.getNode().getProperty(name);
	}

	public Iterator<Property> properties() {
		return this.getNode().properties();
	}

	/**
	 * 判断在指定路径下是否有节点存在
	 * 
	 * @param path
	 *            String 子节点的相对或者绝对路径
	 * @return boolean
	 */
	public boolean hasNode(String path) {
		return this.getNode().hasNode(path);
	}

	/**
	 * 判断在指定路径下是否有属性存在
	 * 
	 * @param path
	 *            String 子节点的相对或者绝对路径
	 * @return boolean
	 */
	public boolean hasProperty(String path) {
		return this.getNode().hasProperty(path);
	}

	/**
	 * 获取附加的格式定义，一个内容可以有多个格式定义
	 * 
	 * @return NodeDefinition[]
	 */
	public NodeDefinition[] getMixinDefinitions() {
		return this.getNode().getMixinDefinitions();
	}

	/**
	 * 判断是否是nodeDefinition类型
	 * 
	 * @param nodeDefinitionName
	 *            String
	 * @return boolean
	 */
	public boolean isNodeType(String nodeDefinitionName) {
		return this.getNode().isNodeType(nodeDefinitionName);
	}

	/**
	 * 添加附加类型，设置成功后，节点自动拥有指定类型下的所有属性， 如果属性和现有的属性有重复的，则保持现有属性
	 * 
	 * @param nodeDefinitionName
	 *            String
	 */
	public void addMixinDefinition(NodeDefinition nodeDefinition) {
		this.getNode().addMixinDefinition(nodeDefinition);

	}

	/**
	 * 删除类型，删除后，这个类型对应的属性将被删除，如果某个属性在已有的类型中还在使用，则保留
	 * 
	 * @param nodeDefinitionName
	 *            String
	 */
	public boolean removeMixin(NodeDefinition definition) {
		return this.getNode().removeMixin(definition);

	}

	/**
	 * 判断是否可以将nodeDefinitionName类型设置为这个节点的附加类型
	 * 
	 * @param nodeDefinitionName
	 *            String
	 * @return boolean
	 */
	public boolean canAddMixinDefinition(NodeDefinition definition) {
		return this.getNode().canAddMixinDefinition(definition);
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
		return this.getNode().getDepth();
	}

	/**
	 * 获取指定深度的父节点
	 * 
	 * @param depth
	 *            int 0<=depth<= getDepth()，0为根节点，以此类推
	 * @return Item
	 */
	public Item getAncestor(int depth) {
		return this.getNode().getAncestor(depth);
	}

	/**
	 * 复制一个自身的拷贝，包括所有的属性，只有pkid是自动生成的，其他的信息和原件一样
	 * 
	 * @return Node
	 */
	public Node clone() {
		return this.getNode().clone();
	}

	@Override
	public int getNodeType(String defid) {
		return this.getNode().getNodeType(defid);
	}

	/**
	 * 获取节点路径 节点路径为： 根节点的路径为/ 当前节点路径=父节点路径+/+ 本节点的name + [当前节点的OrderNo]
	 * 如果当前节点在父节点下name是唯一的，则 [当前节点的OrderNo]在路径上可以省略 *
	 * 
	 * @return String 类似 /a/b/c[1]/d/e[10]的路径
	 */
	public String getPath() {
		return this.getNode().getPath();
	}

	public NodeIterator<Node> getAllNodes(String name) {
		return this.getNode().getAllNodes(name);
	}

}
