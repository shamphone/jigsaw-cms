package com.fulong.portlet.chart.bar.enumeration;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.chart.ChartRender;

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
public class EditRender extends ChartRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {

		PortletPreferences preferences = request.getPreferences();		
	
		NodeDefinition definition = null;
		if (preferences.getValue("definition","").length()>0) {
			definition = this.getRepository().getDefinitionManager().getDefinition(preferences.getValue("definition",""));
		}
		if ((definition == null) && (request.getParameter("definition") != null)) {
			definition = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
		}
		if (definition == null) {
			definition = this.getRepository().getDefinitionManager().getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
		}
		request.setAttribute("definition", definition);
		
		String[] props = preferences.getValues("property", new String[0]);
		if (props.length>0&&props[0]!=null&&!props[0].equals("")) {
			PropertyDefinition[] properties = new PropertyDefinition[props.length];
			for(int i=0;i<props.length;i++){
				if(definition.getPropertyDefinition(props[i])!=null){
					PropertyDefinition property = definition.getPropertyDefinition(props[i]);
					properties[i] = property;
				}else if(props[i]!=null){
					properties[i] = null;
				}
			}
			request.setAttribute("properties", properties);
		}
		
		String row = preferences.getValue("row", "");
		if(definition.getPropertyDefinition(row)==null && row.length() > 0){
			request.setAttribute("rowDeleted", row+"已被删除");
		}else if (row != null && row.length() > 0){
			request.setAttribute("row", definition.getPropertyDefinition(row));
		}
		
		//获取栏目页绑定的大纲作为重复器的过滤搜索大纲
		Channel Currentchannel = this.getCurrentChannel(request, null);
		if (Currentchannel != null) {
			NodeDefinition searchDefinition = Currentchannel.getBindingNode();
			if(searchDefinition!=null){
				request.setAttribute("searchDefinition", searchDefinition);
			}
		}
		
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		String channel = request.getPreferences().getValue("channel", "");
		request.setAttribute("channel", template.getChannel(channel));
		request.setAttribute("siteTemplate", template);	

		return mapping.findForward("success");
	}
	

}