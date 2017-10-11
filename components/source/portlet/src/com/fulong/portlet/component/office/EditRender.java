package com.fulong.portlet.component.office;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.FormEditRender;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-8-18	
 * @version 1.0.1
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
		request.setAttribute("properties", definition.propertyDefinitions());
		String propertyId = request.getPreferences().getValue("propertyId", null);
		if (propertyId != null && propertyId.length() > 0 && definition.getPropertyDefinition(propertyId)!=null){
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
		}else if(definition.getPropertyDefinition(propertyId)==null && propertyId!=null){
			request.setAttribute("propertyDeleted",propertyId+"已被删除");
		}
		request.setAttribute("definition", definition.getID());
		return mapping.findForward("success");
	}
}
