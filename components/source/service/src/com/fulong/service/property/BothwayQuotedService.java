package com.fulong.service.property;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
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
 * @author songbo
 * @date 2010-12-28
 * 
 */

public class BothwayQuotedService extends NodeService {

	public void doProcess(Node node, ServiceParameters parameters,HttpServletRequest request) throws Exception {
		
		String RefPro = parameters.getValue("RefPro");	    //引用属性			
		String RefDef = parameters.getValue("RefDef");  	//引用属性所引用的大纲			
		String RefDefPro = parameters.getValue("RefDefPro");	//被引用大纲中的引用属性

		Iterator<Node> allNodes = repository.getAllNodes(RefDef);   
		while(allNodes.hasNext())
			repository.removeRefNode(allNodes.next().getID(), node.getID(), RefDefPro);   //删除被引用大纲中引用属性的引用关系
		
		Value[] values = node.getProperty(RefPro).getValues();
		Node[] nodes = ValueUtils.toNodeArray(values);
		
		for(int i=0;i<nodes.length;i++){
			repository.insertRefNode(nodes[i].getID(), node.getID(), RefDefPro);   //重置引用关系
		}
		
	}
}
