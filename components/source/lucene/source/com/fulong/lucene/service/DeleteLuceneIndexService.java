package com.fulong.lucene.service;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.lucene.IndexManager;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 服务功能：每删除一个节点，即时删除该节点的Lucene索引。
 * 
 * Title: Coolink平台协同服务管理系统
 * 
 * Description: Coolink平台协同服务管理系统
 * 
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2010
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author songbo
 * @version 3.1
 */

public class DeleteLuceneIndexService extends NodeService {

	private IndexManager IndexManager;

	

	public void setIndexManager(IndexManager indexManager) {
		this.IndexManager = indexManager;
	}

	
	public synchronized void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {

		this.IndexManager.deleteIndex(node); // 在这里删除索引

	}
}
