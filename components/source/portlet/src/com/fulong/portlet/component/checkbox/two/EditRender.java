package com.fulong.portlet.component.checkbox.two;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.FormEditRender;

/**
 * 文本框表单域占位符
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author liuzijun
 * @version 3.1
 */
public class EditRender extends FormEditRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		request.setAttribute("properties", definition.propertyDefinitions());
		String suggestCategoryID = request.getPreferences().getValue("category", null);
		String[] properties = request.getPreferences().getValues("filter-patterns", new String[0]);
		List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();
		for(int i=0;i<properties.length;i++){
			PropertyDefinition prop = definition.getPropertyDefinition(properties[i]);
			if(prop!=null){
				list.add(prop);
			}else{
				list.add(null);
			}
		}
		request.setAttribute("selectedProps", list);
		if (suggestCategoryID != null && suggestCategoryID.length() > 0){
			request.setAttribute("category", this.getRepository().getDefinitionManager().getDefinition(suggestCategoryID));
		}	
		request.setAttribute("definition", definition.getID());
		return mapping.findForward("success");
	}
}
