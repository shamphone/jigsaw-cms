package com.fulong.security.group;


import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.security.SecurityBaseAction;
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
 * @author lixiang
 *
 * @version 3.1
 *
 */
public class ValidateUniqueAction extends SecurityBaseAction {
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	boolean ok = true;
        response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        String parentNodeID = request.getParameter("parentNodeID");
        String nodeID = request.getParameter("nodeID");
        String property = request.getParameter("property");
        
        String value = request.getParameter("value").trim();
        Node parentNode = this.getRepository(request).getNode(parentNodeID);
        if(parentNode!=null){
        	NodeIterator<Node> childGroups = parentNode.getNodes("childGroup");
        	while(childGroups.hasNext()){
        		Node childGroup = childGroups.nextNode();
        		String oldValue = childGroup.getProperty(property).getString();
        		if(nodeID!=null&&!nodeID.equals("")){
        			if(nodeID.equals(childGroup.getID())){
        				continue;
        			}
        		}
        		if(oldValue!=null&&value!=null&&oldValue.equals(value)){
        			ok = false;
        			break;
        		}
        	}
        }
        if(ok){
        	writer.append("true");
        }else{
        	writer.append("false");
        }
        writer.close();
        return null;
    }
}
