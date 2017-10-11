package com.fulong.lucene.service;

import com.fulong.lucene.IndexManager;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 服务功能：为一个节点和其下所有的引用节点建立Lucene索引。
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

public class CreateRefLuceneIndexService extends NodeService {
	private IndexManager IndexManager;

	// 对引用节点建立索引
	public void doProcess(Node node, ServiceParameters parameters)
			throws Exception {
		String refField = parameters.getValue("refField");
		String[] properties = parameters.getValues("properties");
		if (refField != null) {
			// 通过node.getProperty(String path)方法获得所有引用节点
			Property refProp = node.getProperty(refField);
			Value[] contents = refProp.getValues();
			Node[] nodes = ValueUtils.toNodeArray(contents);
			for (int i = 0; i < nodes.length; i++)
				this.IndexManager.createIndex(nodes[i], properties);

		}

	}

}
