package com.fulong.portlet.site.statical;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.fulong.longcon.repository.NodeDefinition;
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
public class EditRender extends SitePortletRender {

	/**
	 * execute
	 * 
	 * @param config
	 *            PortletConfig
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		request.setAttribute("siteTemplate", template);
		String clipPath = request.getPreferences().getValue("source", "index.jsp");
		if(!clipPath.endsWith("jspf")&&!clipPath.endsWith("jsp")){
			clipPath = clipPath + ".jspf";
		}
		request.setAttribute("channel", template.getChannel(clipPath));
		String[] pathes= request.getPreferences().getValues("clip-paths", new String[0]);		
		List<LabelValueBean> mappings = new ArrayList<LabelValueBean>();
		for(int i=0;i<pathes.length;i++){
			String[] splits = pathes[i].split("\\=");
			String channelID = splits[0];
			String principalID = splits[1];
			Channel channel = template.getChannel(channelID);
			NodeDefinition principal = this.getRepository().getDefinitionManager().getDefinition(principalID);
			if(channel!=null && principal!=null){
				String label = channel.getDisplayName() + "("+principal.getName()+ ")";
				String value = channel.getID() + "=" + principal.getID();
				mappings.add(new LabelValueBean(label, value));
			}						
		}
		request.setAttribute("mappings", mappings);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}

}
