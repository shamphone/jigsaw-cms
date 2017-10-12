package com.fulong.cms.content;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.EditForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 *
 * <p>Title: 龙驭内容管理系统-插件</p>
 *
 * <p>Description: 主要包括工作流、编辑器、校验、格式化</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 姜崎
 * @author lixf 
 * @since 2009.9.22
 * @version 2.0
 */
public  class CreateAction extends ContentBaseAction {


	public ActionForward doExecute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String categoryId = request.getParameter("categoryID");
		NodeDefinition nodeDefinition = this.getRepository(request).getDefinitionManager().getDefinition(categoryId);
		Iterator<PropertyDefinition> iterator = nodeDefinition.propertyDefinitions();
		EditForm form = (EditForm)aform;
		while(iterator.hasNext()){
			PropertyDefinition property = iterator.next();
			if(property.getDefaultValue()!=null)
			form.setValue(property.getID(), property.getDefaultValue().getString());
		}
		form.setDefinitionID(categoryId);
		form.setParentID(this.getCurrentUser(request, response).getID());
		form.setName("content");        
        request.setAttribute("definition", nodeDefinition);
        request.setAttribute("properties", nodeDefinition.propertyDefinitions());
        return mapping.findForward("success");
    }
}
