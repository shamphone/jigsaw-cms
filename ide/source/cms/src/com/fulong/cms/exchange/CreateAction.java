/**
 * 
 */
package com.fulong.cms.exchange;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.cms.form.ExchangeForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 *  创建一个新的同步分类 
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
public class CreateAction  extends CMSBaseAction {

	/* 
	 * 
	 * @see com.fulong.cms.CMSBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author lixf
	 * @lastmodified 2009-9-27下午02:42:01
	 */
	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExchangeForm form =(ExchangeForm)aform;
		NodeDefinition parent = this.getRepository(request).getDefinitionManager().getDefinition("exchange-root");
		NodeDefinition definition=this.getRepository(request).getDefinitionManager().createDefinition(form.getName(), parent);
		PropertyDefinition property = definition.getPropertyDefinition("URL");
		property.setDefaultValue(form.getRemoteURL());
		property = definition.getPropertyDefinition("remoteId");
		property.setDefaultValue(form.getRemoteDefinition());
		property = definition.getPropertyDefinition("localId");
		property.setDefaultValue(form.getLocalDefinition());
		property = definition.getPropertyDefinition("exchangeTime");
		Calendar last = Calendar.getInstance();
		last.set(Calendar.YEAR, 1990);		
		property.setDefaultValue(last);
		request.setAttribute("definition", definition);
		return mapping.findForward("success");
	}

}
