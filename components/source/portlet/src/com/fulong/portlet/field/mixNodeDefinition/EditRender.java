package com.fulong.portlet.field.mixNodeDefinition;

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
 * @author jiangqi
 * @version 1.0
 */
public class EditRender extends RepeaterEditRender {
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {

		String path = iniJspf(request, response);
		request.setAttribute("path", path);
		path = "/"+this.getCurrentSiteTemplate(request, response).getName()+ path;
		request.setAttribute("clipPath", path);
		return mapping.findForward("success");

	}

}
