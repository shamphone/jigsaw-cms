package com.fulong.cms;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;

import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class AutoAddFilterAction extends CMSAjaxAction {

	
	public Document renderXML(HttpServletRequest request,
            HttpServletResponse response)
			throws Exception {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		String defID = request.getParameter("defID");
        String searchDefID = request.getParameter("searchDefID");
        String equal = request.getParameter("equal");
        String more = request.getParameter("more");
        String less = request.getParameter("less");
        if(defID!=null&&!defID.equals("")&&searchDefID!=null&&!searchDefID.equals("")){
        	NodeDefinition def = this.getRepository(request).getDefinitionManager().getDefinition(defID);
        	NodeDefinition searchDef = this.getRepository(request).getDefinitionManager().getDefinition(searchDefID);
        	if(def!=null&&searchDef!=null){
        		ParameterSet set = new ParameterSet();
        		for(Iterator<PropertyDefinition> propertyDefinitions = def.propertyDefinitions();propertyDefinitions.hasNext();) {
        			PropertyDefinition prop = (PropertyDefinition)propertyDefinitions.next();
        			PropertyDefinition searchProp = searchDef.getPropertyDefinition(prop.getID());
        			if(searchProp!=null){
        				set.put(prop.getName()+" "+equal+" "+searchProp.getName()+" ("+searchDef.getName()+")",prop.getID()+" equal ^"+searchProp.getID());        				
        			}else{
        				searchProp = searchDef.getPropertyDefinition("F"+prop.getID());
        				if(searchProp!=null){
        					set.put(prop.getName()+" "+more+" "+searchProp.getName()+" ("+searchDef.getName()+")",prop.getID()+" more ^"+searchProp.getID());
        				}
        				searchProp = searchDef.getPropertyDefinition("T"+prop.getID());
            			if(searchProp!=null){
            				set.put(prop.getName()+" "+less+" "+searchProp.getName()+" ("+searchDef.getName()+")",prop.getID()+" less ^"+searchProp.getID());
            			}
        			}
        		}
        		return set.toDocument();
        	}else{
        		return null;
        	}
        }else{
        	return null;
        }        
	}

}
