package com.fulong.portlet.validator.property;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * 属性值验证占位符
 * <p>
 * Title: Coolink协同工作框架模型
 * </p>
 *  
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixiang
 * @version 1.0
 */
public class ViewRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
