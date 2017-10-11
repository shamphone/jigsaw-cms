package com.fulong.service.property;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

public class RemoveRefService extends NodeService {

	public void doProcess(Node node, ServiceParameters parameters,HttpServletRequest request) throws Exception {
			
		String RefDef = parameters.getValue("RefDef");  	//引用属性所在的分类			
		String RefDefPro = parameters.getValue("RefDefPro");	//引用本大纲的属性

		Iterator<Node> allNodes = repository.getAllNodes(RefDef);   
		while(allNodes.hasNext())
			repository.removeRefNode(allNodes.next().getID(), node.getID(), RefDefPro);   //删除引用关系	
	}
}
