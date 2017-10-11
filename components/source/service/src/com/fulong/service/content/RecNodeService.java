package com.fulong.service.content;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class RecNodeService extends NodeService {
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		NodeDefinition nd = this.repository.getDefinitionManager().getDefinition(parameters.getValue("deliverCategoryID"));
		node.setDefinition(nd);
	}


}
