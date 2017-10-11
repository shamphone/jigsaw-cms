package com.fulong.service.content.share.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.service.ServiceBaseAction;
import com.fulong.service.container.ServiceForm;

/**
 * 
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author sunyuchao
 * 
 * @version 1.0
 * 
 */
public class SaveSendInfoAction extends ServiceBaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuilder stringBuilder = new StringBuilder("<ActualParameters>");
		ServiceForm sform = (ServiceForm) form;
		String localDomain=request.getServerName();
		String localTemplateID=null;
		String localDomainName=null;
//		if(localSite!=null){
//			localTemplateID=localSite.getTemplate().getName();
//			localDomainName=localSite.getName();
//		}
		sform.setValue("localDomain", localDomain+request.getContextPath());
		sform.setValue("localTemplateID", localTemplateID);
		sform.setValue("localDomainName",localDomainName);
		for (String name : sform.getNames()) {
			stringBuilder.append("<ActualParameter>");
			stringBuilder.append("<name>");
			stringBuilder.append(name);
			stringBuilder.append("</name>");
			stringBuilder.append("<value>");
			stringBuilder.append(sform.getValue(name));
			stringBuilder.append("</value>");
			stringBuilder.append("</ActualParameter>");
		}
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String propMatch = enumeration.nextElement();
			if (propMatch.split("[.]").length > 1 ) {
				stringBuilder.append("<ActualParameter>");
				stringBuilder.append("<name>");
				stringBuilder.append(propMatch);
				stringBuilder.append("</name>");
				stringBuilder.append("<value>");
				stringBuilder.append(request.getParameter(propMatch));
				stringBuilder.append("</value>");
				stringBuilder.append("</ActualParameter>");
			}
			
		}
		stringBuilder.append("</ActualParameters>");
		request.setAttribute("serviceParam", stringBuilder.toString());
		return mapping.findForward("success");
	}
}
