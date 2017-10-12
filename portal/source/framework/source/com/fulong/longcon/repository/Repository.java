package com.fulong.longcon.repository;

import java.util.Iterator;

/**
 * 接口Repository是对内容库访问的总入口。系统提供缺省的基于数据库的内容库实现。缺省实现是一个Java
 * Bean，用户可以通过SpringFramework或者其他的方式来提供初始化参数。
 * 系统提供对内容库的遍历方法。用户可以通过如下方法来访问内容库的根节点：Repository.getRootNode()，之后可以调用
 * Node.getNodes
 * ()方法来遍历所有子节点，或者通过Node.getNode()来获取指定ID的子节点。通过Node.addNode()可以为当前节点添加子节点。
 * 
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
public interface Repository {
	/**
	 * 节点定义管理器
	 * 
	 * @return NodeDefinitionManager
	 */
	public NodeDefinitionManager getDefinitionManager();

	/**
	 * 获取指定ID的节点
	 * 
	 * @param nodeId
	 *            String
	 * @return Node
	 */
	public Node getNode(String nodeId);
	
	/**
	 * 根据大纲ID返回所有节点
	 * 
	 * @param DefId
	 *            String
	 * @return Iterator<Node>
	 */
	public Iterator<Node> getAllNodes(String DefId);

	/**
	 * 根据绝对路径来获取节点
	 * 
	 * @param absPath
	 *            String 绝对路径
	 * @return Node
	 */
	public Node getNodeByPath(String absPath);

	/**
	 * 获取根结点
	 * 
	 * @return Node
	 */
	public Node getRootNode();

	/**
	 * 删除节点
	 * 
	 * @param node
	 *            Node
	 * @return true:删除成功
	 */
	public boolean delete(Node node);

	/**
	 * 获取查询管理器
	 * 
	 * @return QueryManager
	 */
	public QueryManager getQueryManager();

	/**
	 * 获取值工厂
	 * 
	 * @return ValueFactory
	 */
	public ValueFactory getValueFactory();

	/**
	 * 清除当前大纲下内容的关联关系，但不删除内容。
	 * 
	 * @param definition
	 *            NodeDefinition
	 * @param recursive
	 *            boolean参数为true时，表示要递归清除大纲下的子孙大纲所有的内容关联关系
	 */
	public void removeNodes(NodeDefinition definition, boolean recursive);

	/**
	 * 根据节点和值删除引用关系
	 * 
	 * @param nodeID
	 * 			String
	 * @param value
	 * 			StringId
	 * @param property
	 * 			引用关系所在的属性
	 */
	public void removeRefNode(String nodeID, String value ,String property);
	
	/**
	 * 根据节点和值删插入引用关系
	 * 
	 * @param nodeID
	 * 			String
	 * @param value
	 * 			StringId
	 * @param property
	 * 			引用关系所在的属性
	 */
	public void insertRefNode(String nodeID, String value, String property);
	/**
	 * 创建了lucene索引的node
	 * @return
	 */
	//public NodeIterator<Node> getIndexedNodes();
}
