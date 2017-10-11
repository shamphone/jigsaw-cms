package com.fulong.portlet.button.send.multiple;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.FormEditRender;

/**
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
 * @author jiangqi
 * @version 2.0
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

//		String channel = request.getPreferences().getValue("channel", "");
//		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
//		request.setAttribute("channel", template.getChannel(channel));
//		request.setAttribute("siteTemplate", template);
//
//		List<RemoteCategory> remoteCategorys = new ArrayList<RemoteCategory>();
//		String[] categorys = request.getPreferences().getValues("categorys", new String[0]);
//		for (int i = 0; i < categorys.length; i++) {
//			RemoteCategory rc = this.getRemoteManager().getCategory(categorys[i]);
//			if (rc != null) {
//				remoteCategorys.add(rc);
//			}
//		}
//		request.setAttribute("categorys", remoteCategorys);
//		request.setAttribute("category", definition);
		return mapping.findForward("success");
	}
}
