package com.fulong.longcon.repository;

import java.util.Iterator;

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
 * @author 李少波
 * @version 1.0
 */
public abstract class NodeDefinitionWrapper implements NodeDefinition {
	protected NodeDefinition _nodeDefinition;

	public NodeDefinitionWrapper(NodeDefinition nodeDefinition) {
		this._nodeDefinition = nodeDefinition;

	}

	public NodeDefinition getNodeDefinition() {
		return this._nodeDefinition;
	}

	/**
	 * 属性的标识符,和属性定义中的ID是相同的。
	 * 
	 * @return String 标识符
	 */
	public String getID() {
		return this._nodeDefinition.getID();
	}

	/**
	 * 名称，可以使用中文
	 * 
	 * @return java.lang.String
	 */

	public String getName() {
		return this._nodeDefinition.getName();
	}

	/**
	 * 重命名
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this._nodeDefinition.setName(name);
	}

	/**
	 * 描述
	 * 
	 * @return String
	 */
	public String getDescription() {
		return this._nodeDefinition.getDescription();
	}

	/**
	 * 描述
	 * 
	 * @param description
	 *            String
	 */
	public void setDescription(String description) {
		this._nodeDefinition.setDescription(description);
	}

	/**
	 * 该节点定义/属性定义是不能删除的
	 * 
	 * @return boolean
	 */
	public boolean isProtected() {
		return this._nodeDefinition.isProtected();
	}

	/**
	 * 遍历所有属性，包括系统属性和用户自定义属性。
	 * 
	 * @return java.util.Iterator
	 */
	public Iterator<PropertyDefinition> propertyDefinitions() {
		return this._nodeDefinition.propertyDefinitions();
	}

	/**
	 * 根据readonly遍历所有属性，包括系统属性和用户自定义属性。 readonly=true 不可修改的属性(系统属性)
	 * readonly=false 可修改的(用户自定义属性)
	 * 
	 * @return java.util.Iterator
	 */
	public Iterator<PropertyDefinition> propertyDefinitions(boolean readonly) {
		return this._nodeDefinition.propertyDefinitions(readonly);
	}

	/**
	 * 获取指定类型的属性定义
	 * 
	 * @param type
	 *            String
	 * @return java.util.Iterator
	 */
	public Iterator<PropertyDefinition> propertyDefinitions(int type) {
		return this.propertyDefinitions(type);
	}

	/**
	 * 获取指定ID的属性
	 * 
	 * @param property
	 *            String
	 * @return PropertyDescriptor
	 */
	public PropertyDefinition getPropertyDefinition(String ID) {
		return this._nodeDefinition.getPropertyDefinition(ID);
	}

	/**
	 * 创建指定类型的属性并添加到库定义中。
	 * 
	 * @param type
	 *            String 类型
	 * @param name
	 *            String 名称
	 * @return PropertyDescriptor
	 */
	public PropertyDefinition addPropertyDefinition(int type, String name) {
		return this._nodeDefinition.addPropertyDefinition(type, name);
	}

	/**
	 * 为当前NodeDefinition添加一个复合属性
	 * 
	 * @param type
	 *            NodeDefinition，这个参数设置该复合属性到底是用的哪个大纲
	 * @param id
	 *            String
	 * @return ChildNodeDefinition
	 */
	public ChildNodeDefinition addChildNodeDefinition(NodeDefinition type,
			String id) {
		return this._nodeDefinition.addChildNodeDefinition(type, id);
	}

	/**
	 * 删除属性
	 * 
	 * @param property
	 *            PropertyDescriptor
	 */
	public void delete(PropertyDefinition property) {
		this._nodeDefinition.delete(property);
	}

	/**
	 * 验证给定的值是否可以设置给当前属性
	 * 
	 * @param value
	 *            Value
	 * @return ValidatedResult
	 */
	public boolean canSetProperty(String property, Value value) {
		return this._nodeDefinition.canSetProperty(property, value);
	}

	/**
	 * 验证给定的值是否可以设置给当前属性
	 * 
	 * @param value
	 *            Value[]
	 * @return ValidatedResult
	 * @deprecated 使用canSetProperty(String property, Value[] value, Node
	 *             node)方法代替 可以得出当前库下内容是否唯一等校验
	 */
	public boolean canSetProperty(String property, Value[] value) {
		return this._nodeDefinition.canSetProperty(property, value);
	}

	/**
	 * 父定义。
	 * 
	 * @return NodeDefinition
	 */
	public NodeDefinition getSuperDefinition() {
		return this._nodeDefinition.getSuperDefinition();
	}

	/**
	 * 获得继承自当前大纲的大纲列表
	 * 
	 * @return NodeDefinitionIterator
	 */
	public NodeDefinitionIterator getInheritDefinitions() {
		return this._nodeDefinition.getInheritDefinitions();
	}

	/**
	 * 如果这个节点类型是nodeDefinitionId所定义的节点定义的子节点，则返回true
	 * 
	 * @param nodeDefinitionId
	 *            the name of a node type.
	 * @return a boolean
	 */
	public boolean isNodeType(String nodeDefinitionId) {
		return this._nodeDefinition.isNodeType(nodeDefinitionId);
	}

	/**
	 * 是否递归遍历大纲
	 * 
	 * @param recursive
	 *            boolean
	 * @return NodeDefinitionIterator
	 */
	public NodeDefinitionIterator getInheritDefinitions(boolean recursive) {
		return this._nodeDefinition.getInheritDefinitions(recursive);
	}

	/**
	 * 指定节点应用属性值的约束验证
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            Value[]
	 * @param node
	 *            Node
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public boolean canSetProperty(String property, Value[] value, Node node) {
		return this._nodeDefinition.canSetProperty(property, value, node);
	}

	/**
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            Value[]
	 * @param node
	 *            Node 当前节点所在的父节点
	 * @param self
	 *            Node 当前节点
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public boolean canSetProperty(String property, Value[] value, Node node,
			Node self) {
		return this._nodeDefinition.canSetProperty(property, value, node, self);
	}

}
