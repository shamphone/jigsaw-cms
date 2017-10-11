package com.fulong.portlet.button.save.single;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.button.ButtionEditRender;

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
public class EditRender extends ButtionEditRender {
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
		request.setAttribute("category", definition);
		request.setAttribute("copyCategorys", this.lookupDefinitions(request,"copyTo"));
	    request.setAttribute("moveCategorys", this.lookupDefinitions(request,"moveTo"));
	    request.setAttribute("recommendCategorys", this.lookupDefinitions(request,"recommendTo"));

		SiteTemplate template = null;
		Channel channel = null;
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath!=null&&!channelPath.equals("")&&channelPath.indexOf("/")>=0){
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}else{
			template = this.getCurrentSiteTemplate(request, response);
		}
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);
		
		String parentCategoryID = request.getPreferences().getValue("parentCategory", "");
		String parentFieldID = request.getPreferences().getValue("parentField", "");
		
		if(!parentCategoryID.equals("")){
			NodeDefinition parentCategory = this.getRepository().getDefinitionManager().getDefinition(parentCategoryID);
			if(parentCategory!=null){
				request.setAttribute("parentCategory", parentCategory);
				if(!parentFieldID.equals("")){
					PropertyDefinition parentField = parentCategory.getPropertyDefinition(parentFieldID);
					if(parentField!=null){
						request.setAttribute("parentPropertyName", parentField.getName());
					}else{
						request.setAttribute("parentPropertyName", parentFieldID+"已被删除");
					}
				}
			}
		}
		
		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("workflows", this.getWorkflowService().definitions());
		return mapping.findForward("success");
	}
}
