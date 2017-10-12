package com.fulong.site.channel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: 显示模型管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class AuthAction extends ChannelBaseAction {

	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm aform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("definitionTree", new NodeDefinitionTreeBuilder(
				this.getRepository(request).getDefinitionManager().getDefinition("principal-scheme")).build());
		return mapping.findForward("success");
	}
}
