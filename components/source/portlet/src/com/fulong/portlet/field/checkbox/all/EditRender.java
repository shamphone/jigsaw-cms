package com.fulong.portlet.field.checkbox.all;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.portlet.PortletRender;

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
 * @author liuzijun
 * @version 2.0
 */
public class EditRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		return mapping.findForward("success");
	}
}
