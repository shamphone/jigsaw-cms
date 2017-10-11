package com.fulong.portlet.cms.list;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.fulong.portlet.PortletAction;
import javax.portlet.PortletPreferences;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class SaveAction extends PortletAction {
	/**
	 * execute
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @throws Exception
	 * @todo Implement this com.fulong.portlet.PortletAction method
	 */
	public void execute(ActionRequest request, ActionResponse response) throws Exception {
		PortletPreferences modified = request.getPreferences();
		modified.store();
	}
}
