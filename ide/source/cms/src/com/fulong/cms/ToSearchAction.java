package com.fulong.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.ContentForm;
import com.fulong.common.servlet.MultipartRequestWrapper;
import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.workflow.Activity;

public class ToSearchAction extends CMSBaseAction {
	/**
	 * doPerform
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String categoryId = request.getParameter("categoryID");
		List list = new ArrayList();
		
		if(categoryId==null){
			request.setAttribute("properties", list);
			return mapping.findForward("success");
		}
			
		Iterator propertyDefinitions = this.getRepository(request)
				.getDefinitionManager().getDefinition(categoryId)
				.propertyDefinitions();
		
		while (propertyDefinitions.hasNext()) {
			PropertyDefinition propertyDefinition = (PropertyDefinition) propertyDefinitions
					.next();
			if (propertyDefinition.getType() != PropertyType.TEXT) {
				list.add(propertyDefinition);
			}
		}
		request.setAttribute("properties", list);
		request.setAttribute("propertyIDs", request.getParameterValues("displayColumns"));
		request.setAttribute("definition", this.getRepository(request)
				.getDefinitionManager().getDefinition(categoryId));
		return mapping.findForward("success");
	}
}