package com.fulong.portlet.button.search;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.FormEditRender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 保存按钮 编辑页
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
 * @author lixf
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
		SiteTemplate template = null;
		Channel channel = null;
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath!=null&&!channelPath.equals("")&&channelPath.indexOf("/")>=0){
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}else{
			template = this.getCurrentSiteTemplate(request, response);
		}
		String[] conditions = request.getPreferences().getValues("conditions", new String[0]);
		request.setAttribute("conditions", conditions);
		
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);
		
		request.getPreferences().setValue("category", definition.getID());
		request.setAttribute("category", definition);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
