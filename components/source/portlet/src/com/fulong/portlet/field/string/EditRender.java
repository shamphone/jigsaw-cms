package com.fulong.portlet.field.string;

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
import com.fulong.portlet.cms.ListContentPortletRender;

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
public class EditRender extends ListContentPortletRender {
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		NodeDefinition category = lookUpDefinition(request);
		preferences.setValue("category", category.getID());
		request.setAttribute("category", category);
		
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
		
		if (config.getField() != null) {
			PropertyDefinition property = category.getPropertyDefinition(config.getField());
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
			request.setAttribute("property", property);
		}
		String forwardFieldID = request.getPreferences().getValue("forwardField", "");
		if(forwardFieldID!=null&&!forwardFieldID.equals("")){
			PropertyDefinition forwardProperty = category.getPropertyDefinition(forwardFieldID);
			StringBuffer forwardPropertyName = new StringBuffer();
			if(forwardProperty!=null){
				String idstr="";
				if(forwardFieldID.split("[\\.\\/]").length>0){
					String ids[] = forwardFieldID.split("[\\.\\/]");
					for(int i=0;i<ids.length;i++){
						idstr = idstr+ids[i];
						PropertyDefinition forwardProperty2 = category.getPropertyDefinition(idstr);
						forwardPropertyName.append(forwardProperty2.getName());
						if(i!=ids.length-1){
							idstr+=".";
							forwardPropertyName.append(".");
						}
					}
				}
			}else{
				forwardPropertyName.append(config.getField()+"已被删除");
			}
			request.setAttribute("forwardPropertyName",forwardPropertyName);
			request.setAttribute("forwardProperty", forwardProperty);
		}
		if (config.getContentID() != null) {
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));
		}
		String type = preferences.getValue("type", null); 
		if (type != null)
			request.setAttribute("type", type);
		
		String sensitiveCategoryID = preferences.getValue("sensitiveCategory", "");
		String sensitiveFieldID = preferences.getValue("sensitiveField", "");
		
		if(!sensitiveCategoryID.equals("")){
			NodeDefinition sensitiveCategory = this.getRepository().getDefinitionManager().getDefinition(sensitiveCategoryID);
			if(sensitiveCategory!=null){
				request.setAttribute("sensitiveCategory", sensitiveCategory);
				if(!sensitiveFieldID.equals("")){
					PropertyDefinition sensitiveField = sensitiveCategory.getPropertyDefinition(sensitiveFieldID);
					if(sensitiveField!=null){
						request.setAttribute("sensitivePropertyName", sensitiveField.getName());
					}else{
						request.setAttribute("sensitivePropertyName", sensitiveFieldID+"已被删除");
					}
				}
			}
		}
		return mapping.findForward("success");
	}
}
