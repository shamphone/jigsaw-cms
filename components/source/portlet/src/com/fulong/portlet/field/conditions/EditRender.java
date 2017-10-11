package com.fulong.portlet.field.conditions;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.cms.RepeaterEditRender;

/**
 * 
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class EditRender extends RepeaterEditRender {
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {
		String truePath = iniJspf(request, response,"true");
		request.setAttribute("truePath", truePath);
		truePath = "/"+this.getCurrentSiteTemplate(request, response).getName()+ truePath;
		request.setAttribute("trueClipPath", truePath);
		
		String falsePath = iniJspf(request, response,"false");
		request.setAttribute("falsePath", falsePath);
		falsePath = "/"+this.getCurrentSiteTemplate(request, response).getName()+ falsePath;
		request.setAttribute("falseClipPath", falsePath);
		
		request.getPreferences().setValue("category", def.getID());
		request.setAttribute("category", def);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
