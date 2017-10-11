package com.fulong.lucene.service;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.lucene.IndexManager;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 为大纲下的所有节点创建索引
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-8-5	
 * @version 1.0.1
 */
public  class BatchDeleteLuceneIndexService extends NodeService {
	private IndexManager IndexManager;
	//private Repository repository;

	public void setIndexManager(IndexManager indexManager) {
		this.IndexManager = indexManager;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	@Override
	public synchronized void doProcess(NodeDefinition definition, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
     /* Query query = repository.getQueryManager().createQuery(definition, Query.SQL);
		NodeIterator<Node> it = query.nodes();
		for(;it.hasNext();){
			IndexManager.deleteIndex(it.next());
		}*/
		this.IndexManager.deleteIndexByDefID(definition.getID());
	}
}
