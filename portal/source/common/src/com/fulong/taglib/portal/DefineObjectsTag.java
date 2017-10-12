package com.fulong.taglib.portal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.VariableInfo;

import com.fulong.portal.core.Constants;

/**
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
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
 * @author 李雄锋
 * @version 1.0
 */

public class DefineObjectsTag extends TagSupport {

	private static final long serialVersionUID = -5237806530526117628L;

	/**
	 * Processes the <CODE>defineObjects</CODE> tag.
	 * 
	 * @return <CODE>SKIP_BODY</CODE>
	 */
	public int doStartTag() throws JspException {
		PortletRequest renderRequest = (PortletRequest) pageContext
				.getRequest().getAttribute(Constants.PORTLET_REQUEST);
		RenderResponse renderResponse = (RenderResponse) pageContext
				.getRequest().getAttribute(Constants.PORTLET_RESPONSE);
		PortletConfig portletConfig = (PortletConfig) pageContext.getRequest()
				.getAttribute(Constants.PORTLET_CONFIG);
		if (pageContext.getAttribute("renderRequest") == null) { // Set attributes only once
			pageContext.setAttribute("renderRequest", renderRequest, PageContext.PAGE_SCOPE);
		}

		if (pageContext.getAttribute("renderResponse") == null) {
			pageContext.setAttribute("renderResponse", renderResponse, PageContext.PAGE_SCOPE);
		}

		if (pageContext.getAttribute("portletConfig") == null) {
			pageContext.setAttribute("portletConfig", portletConfig, PageContext.PAGE_SCOPE);
		}

		return SKIP_BODY;
	}

	public static class TEI extends TagExtraInfo {

		public VariableInfo[] getVariableInfo(TagData tagData) {
			VariableInfo[] info = new VariableInfo[] {
					new VariableInfo("renderRequest", "javax.portlet.RenderRequest", true, VariableInfo.AT_BEGIN),
					new VariableInfo("renderResponse", "javax.portlet.RenderResponse", true, VariableInfo.AT_BEGIN),
					new VariableInfo("portletConfig", "javax.portlet.PortletConfig", true, VariableInfo.AT_BEGIN) };

			return info;
		}
	}

}
