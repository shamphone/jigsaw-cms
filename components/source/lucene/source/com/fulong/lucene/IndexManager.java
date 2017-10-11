package com.fulong.lucene;

import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.store.FSDirectory;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * 索引管理器
 * 
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
public interface IndexManager {
	public static final String FIELD_ALL = "content-page";
	public static final String FIELD_ID = "content-ID";
	public static final String FIELD_PARENT_ID = "content-parent-ID";
	public static final String FIELD_CHILDREN = "content-children";
	public static final String FIELD_DEFINITION_ID = "content-definition-ID";
	public static final String FIELD_ORDERNO = "content-orderno";
	public static final String FIELD_NAME = "content-name";

	/**
	 * 删除索引,这个实现与数据库删除节点实现保持一致， 在删除该节点的时候删除其所以子节点，同样，也删除该节点以及所有子节点的索引文件
	 * 用户需要确保在删除节点之前调用这个方法保证删除节点索引。
	 * 
	 * @param node
	 *            Node
	 * @throws IOException
	 */
	public void deleteIndex(Node node) throws IOException;

	public void deleteIndexByDefID(String id) throws IOException;
	/**
	 * 创建索引
	 * （三种不同参数形式）
	 * @param node
	 *            Node
	 * @param String
	 *            [] properties
	 * @throws IOException
	 */
	public void createIndex(NodeIterator<Node> it, String[] properties)
			throws IOException;

	public void createIndex(Node node, String[] properties) throws IOException;

	public void createIndex(Iterator<Node> it, String[] properties,
			int currentThread) throws IOException;

	/**
	 * 合并索引并优化
	 * 
	 * @throws IOException
	 */
	public void addIndexesOptimize() throws IOException;

	/**
	 * 索引所在目录
	 * 
	 * @return
	 */
	public FSDirectory getDirectory();

	
}
