package com.fulong.portlet.button.time;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.PortletRender;

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
public class EditRender extends PortletRender {
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
		String propertyS = request.getPreferences().getValue("propertyId", null);
		NodeDefinition category = lookUpDefinition(request);
		request.getPreferences().setValue("category-id", category.getID());
		request.setAttribute("category", category);
		if(category.getPropertyDefinition(propertyS)==null && propertyS != null){
			request.setAttribute("propertyName", propertyS+"已被删除");
		}else if (propertyS != null && propertyS.length() > 0){
			request.setAttribute("property", category.getPropertyDefinition(propertyS));
		}

		return mapping.findForward("success");
	}
}
