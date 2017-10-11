package com.fulong.service.content;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 内容复制服务
 * <p>
 * Title: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author sunyuchao
 * @version 3.1
 */
public class CopyService extends NodeService {
	

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		NodeDefinition newDefinition = this.repository.getDefinitionManager().getDefinition(parameters.getValue("desCategoryID"));
		Node newNode=node.clone();
		newNode.setDefinition(newDefinition);
	}

	
}
