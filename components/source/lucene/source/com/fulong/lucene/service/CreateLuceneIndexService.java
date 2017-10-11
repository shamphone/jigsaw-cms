package com.fulong.lucene.service;

import com.fulong.longcon.repository.Node;
import com.fulong.lucene.IndexManager;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 服务功能：每次创建一个节点，为该节点即时的建立Lucene索引。
 * 
 * Title: Coolink平台协同服务管理系统
 * 
 * Description: Coolink平台协同服务管理系统
 * 
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2010
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author 刘磊
 * @version 3.1
 */

public class CreateLuceneIndexService extends NodeService {

	private IndexManager IndexManager;


	public void setIndexManager(IndexManager indexManager) {
		// this.luceneIndexManager = () beanFactory.getBean("indexManager");
		this.IndexManager = indexManager;
	}



	public void doProcess(Node node, ServiceParameters parameters) throws Exception {
		// 就在此处创建索引

		// NodeIterator<Node> childrennodes = node.getAllNodes();
		String[] properties = parameters.getValues("properties");
		this.IndexManager.createIndex(node,properties);

	}
}
