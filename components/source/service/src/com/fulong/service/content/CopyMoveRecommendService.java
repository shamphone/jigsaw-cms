package com.fulong.service.content;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-9-30	
 * @version 1.0.1
 */
public class CopyMoveRecommendService extends NodeService {
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		String type = parameters.getValue("type");
		String[] definitionIDs = parameters.getValues("definitions");
		NodeDefinition[] definitions = new NodeDefinition[definitionIDs.length];
		for(int i=0;i<definitionIDs.length;i++){
			definitions[i] = this.repository.getDefinitionManager().getDefinition(definitionIDs[i]);
		}
		if(type.equals("copy")){
			for(int i=0;i<definitions.length;i++){
				Node newNode=node.clone();
				newNode.setDefinition(definitions[i]);
			}
		}else if(type.equals("move")){
			for(int i=0;i<definitions.length;i++){
				node.setDefinition(definitions[i]);
			}
		}else if(type.equals("recommend")){
			for(int i=0;i<definitions.length;i++){
				node.addMixinDefinition(definitions[i]);
			}
		}
	}
	
}
