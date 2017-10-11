package com.fulong.portlet.site.statical;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.site.SitePortletRender;

/**
 * 
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
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
 * @author lixf
 * @version 1.0
 */
public class FinalRender extends SitePortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		String path = request.getPreferences().getValue("source", "");
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		if(!path.endsWith("jspf")&&!path.endsWith("jsp")){
			path = path + ".jspf";
		}
		if(request.getUserPrincipal()!=null){
			Node user = (Node) request.getUserPrincipal();
			String[] pathes= request.getPreferences().getValues("clip-paths", new String[0]);		
			for(int i=0;i<pathes.length;i++){
				String[] splits = pathes[i].split("\\=");
				String channelID = splits[0];
				String principalID = splits[1];
				Channel channel = template.getChannel(channelID);
				if(user.isNodeType(principalID)){
					path = channel.getContextPath();
				}
			}
		}
		request.setAttribute("contextName", template.getName());
		request.setAttribute("clipPath", path);
		return mapping.findForward("success");
	}
}
