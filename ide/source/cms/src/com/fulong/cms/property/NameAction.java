/**
 * 
 */
package com.fulong.cms.property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
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
public class NameAction extends PropertyBaseAction {
    /**
     * dictPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.dict.DictBaseAction method
     */
    public ActionForward propertyPerform(ActionMapping mapping,
                                           ActionForm aform,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws
            Exception {
    	String definitionID = request.getParameter("definitionID");
    	String propertyID = request.getParameter("propertyID");
    	response.setContentType("text/plain; charset=UTF-8");
    	PropertyDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(definitionID).getPropertyDefinition(propertyID);
    	/**
    	 * luobin modified in 2010-3-17
    	 * Bug5304
    	 * 修改目的：PropertyDefinition为null时报错
    	 * 修改描述：增加判断
    	 */
    	String propertyName = "";
    	if(definition!=null){
    		propertyName = definition.getName();
    	}
    	response.getWriter().write(propertyName);
    	return null;
    }

}
