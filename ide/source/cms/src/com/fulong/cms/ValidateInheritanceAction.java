package com.fulong.cms;

import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.cms.content.ContentBaseAction;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.NodeDefinition;
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
 * @author liuzijun
 *
 * @version 1.0
 *
 */
public class ValidateInheritanceAction extends ContentBaseAction {
    public ActionForward doExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            Exception {
        response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        Writer writer = response.getWriter();
        String categoryID = request.getParameter("categoryID");
        String parentCategoryID = request.getParameter("parentCategoryID");
        String parentFieldID = request.getParameter("parentFieldID");
        NodeDefinition def = this.getRepository(request).getDefinitionManager().getDefinition(categoryID);
        NodeDefinition parentDef = this.getRepository(request).getDefinitionManager().getDefinition(parentCategoryID);
        if(def!=null&&parentDef!=null){
        	PropertyDefinition parentFixField = parentDef.getPropertyDefinition(parentFieldID);
        	if(parentFixField!=null&&parentFixField.getType()==PropertyType.FIX){
        		NodeDefinition fixPropDefinition = ((ChildNodeDefinition)parentFixField).getNodeDefinition();
        		if(fixPropDefinition!=null){
        			if(def.isNodeType(fixPropDefinition)||def.getID().equals("principal-scheme")){
                		writer.append("true");
                	}else{
                		writer.append("false");
                	}
        		}else{
        			writer.append("false");
        		}
        	}else{
        		writer.append("false");
        	}
        }else{
        	writer.append("false");
        }
        writer.close();
        return null;
    }
}
