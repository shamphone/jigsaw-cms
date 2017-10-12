/**
 * 
 */
package com.fulong.service.container;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
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
public class MessagesAction extends ContainerBaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String code = request.getParameter("msgCode");
		if (code == null)
			return null;
		List<ActionMessage> messages = (List<ActionMessage>) request.getSession().getAttribute(code);
		ActionMessages retrieved = new ActionMessages();
		if (messages != null) {
			for (int i = 0; i < messages.size(); i++)
				retrieved.add(ActionMessages.GLOBAL_MESSAGE, messages.get(i));
			messages.clear();
		}
		this.saveMessages(request, retrieved);
		return mapping.findForward("success");
	}
}
