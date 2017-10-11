package com.fulong.portlet.button.nextNode;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.cms.RepeaterEditRender;

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
public class EditRender extends RepeaterEditRender {

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		
		String orderProperty = preferences.getValue("order-field", "");
		if(def.getPropertyDefinition(orderProperty)==null){
			request.setAttribute("orderPropertyName", orderProperty+"已被删除");
		}else if (orderProperty != null && orderProperty.length() > 0){
			request.setAttribute("orderProperty", def.getPropertyDefinition(orderProperty));
		}	
		request.setAttribute("definition", def);
       
		String refField = preferences.getValue("refField", null);
		if (refField != null) {
			PropertyDefinition pd = def.getPropertyDefinition(refField);
			if (pd.getType() == 9) {
					request.setAttribute("property", pd);
				}
			if (pd.getType() == 0) {
				ChildNodeDefinition fh = (ChildNodeDefinition)pd;
				request.setAttribute("property", fh);
			}
		}

		//获取栏目页绑定的大纲作为重复器的过滤搜索大纲
		Channel channel = this.getCurrentChannel(request, null);
		if (channel != null) {
			NodeDefinition searchDefinition = channel.getBindingNode();			 
			if(searchDefinition!=null){
				request.setAttribute("searchDefinition", searchDefinition);
			}
		}
		
		SiteTemplate template = null;
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath!=null&&!channelPath.equals("")&&channelPath.indexOf("/")>=0){
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}else{
			template = this.getCurrentSiteTemplate(request, response);
		}
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);
		
		return mapping.findForward("success");

	}
}
