package com.fulong.service.content.share.common;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.service.ServiceAjaxAction;

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
 * @author sunyuchao
 *
 * @version 1.0
 *
 */
public class GetPropertyAction extends ServiceAjaxAction {
    /**
     * renderXML
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Document
     * @throws Exception
     */
    public Document renderXML(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String categoryID = request.getParameter("categoryID");
        Iterator<PropertyDefinition> it=this.getRepository().getDefinitionManager().getDefinition(categoryID).propertyDefinitions(false);
        ParameterSet set = new ParameterSet();
        while(it.hasNext()){
            PropertyDefinition pd=(PropertyDefinition)it.next();
            set.put(pd.getID(), pd.getName());
        }
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        return set.toDocument();
    }
}
