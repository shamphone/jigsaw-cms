package com.fulong.portlet.component.text.fuzzySearchProp;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.fulong.portlet.PortletAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * 
 * <p>
 * Title: coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Description: coolink协同工作支撑平台
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
 * @author liuzijun
 * @version 1.0
 */
public class SaveAction extends PortletAction {
	public ActionForward action(ActionMapping mapping, ActionForm form, ActionRequest request, ActionResponse response)
			throws Exception {
		request.getPreferences().store();
		return null;
	}

}
