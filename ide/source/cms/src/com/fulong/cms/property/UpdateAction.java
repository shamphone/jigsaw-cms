package com.fulong.cms.property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.PropertyForm;
import com.fulong.longcon.repository.PropertyDefinition;
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
 * @author lixf
 *
 * @version 2.0
 *
 */
public class UpdateAction extends PropertyBaseAction {
	


	/* 
	 * 
	 * @see com.fulong.cms.property.PropertyBaseAction#propertyPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @author lixf
	 * @param mapping
	 * @param aform
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward propertyPerform(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PropertyForm form = (PropertyForm) aform;
		PropertyDefinition property = this.getRepository(request).getDefinitionManager().getDefinition(form.getDefinitionId()).getPropertyDefinition(form.getID());

		property.setName(form.getName());
		if (form.getDataNum().equals("1")) {
			property.setMultiple(false);
		} else if (!form.getDataNum().equals("1")) {
			property.setMultiple(true);
			if (form.getDataNum().equals("n")) {
				int min = 0, max = 0;
				if (form.getMinLength().length() > 0)
					min = Integer.parseInt(form.getMinLength());
				if (form.getMaxLength().length() > 0)
					max = Integer.parseInt(form.getMaxLength());
				property.setLength(min, max);
			}
		}
		 property.setReferenceType(form.getReferenceType());
		request.setAttribute("property", property);
		return mapping.findForward("success");

	}
}
