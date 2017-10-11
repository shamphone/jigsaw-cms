package com.fulong.portlet.field.image;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 * 图片内容域占位符
 * 
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
 * @author lichengzhao
 * @version 3.1
 */
public class EditRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		
		SiteTemplate template = null;
		Channel channel = null;
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath==null||channelPath.equals("")){
			template = this.getCurrentSiteTemplate(request, response);
		}else{
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);
		
		request.setAttribute("sites", this.getSiteFactory().getSites());
		
		request.setAttribute("showWhat", preferences.getValue("showWhat", null));
		NodeDefinition category = lookUpDefinition(request);
		request.getPreferences().setValue("category", category.getID());
		request.setAttribute("category", category);
		if (config.getField() != null) {
			PropertyDefinition property = category.getPropertyDefinition(config.getField());
			request.setAttribute("property", property);
			StringBuffer propertyName = new StringBuffer();
			if(property!=null){
				String idstr="";
				if(config.getField().split("[\\.\\/]").length>0){
					String ids[] = config.getField().split("[\\.\\/]");
					for(int i=0;i<ids.length;i++){
						idstr = idstr+ids[i];
						PropertyDefinition property2 = category.getPropertyDefinition(idstr);
						propertyName.append(property2.getName());
						if(i!=ids.length-1){
							idstr+=".";
							propertyName.append(".");
						}
					}
				}
			}else{
				propertyName.append(config.getField()+"已被删除");
			}
			request.setAttribute("propertyName",propertyName);
			request.setAttribute("altProperty", category.getPropertyDefinition(preferences.getValue("altfield", null)));
		}
		if (config.getContentID() != null)
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));

		return mapping.findForward("success");
	}
}
