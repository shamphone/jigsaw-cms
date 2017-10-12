package com.fulong.cms.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Repository;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lichengzhao
 * @version 1.0
 */
public class DeleteResourceAction extends ResourceBaseAction {

	protected ActionForward resourcePerform(ActionMapping mapping,
											ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		String[] ids = request.getParameterValues("id");
		Repository repository = this.getRepository(request);
		for (int i = 0; i < ids.length; i++)
			repository.delete(repository.getNode(ids[i]));
		return null;
	}
}
