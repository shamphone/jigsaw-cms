/**
 * 
 */
package com.fulong.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;

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
public class UsersAction extends SecurityBaseAction{

	@SuppressWarnings("unchecked")
	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Repository repository = this.getRepository(request);
		NodeDefinition definition = repository.getDefinitionManager().getDefinition("principal-scheme");
		Query query = repository.getQueryManager().createQuery(definition, Query.SQL);
		NodeIterator users = query.nodes();
		users.skip(this.getPager(request).getFromIndex());
		request.setAttribute("users", users);
		this.setPager(request, users.getSize(),30);
		return mapping.findForward("success");
	}

}


