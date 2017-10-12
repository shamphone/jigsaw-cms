package com.fulong.taglib.common;

import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.RewriteTag;
import org.apache.struts.util.RequestUtils;

/**
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2008
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 3.0
 */
public class RewriteURLTag extends RewriteTag {
	
	private static final long serialVersionUID = -2954755142432259621L;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

		// Generate the hyperlink URL
		Map params = TagUtils.getInstance().computeParameters(pageContext,
				paramId, paramName, paramProperty, paramScope, name, property,
				scope, transaction);

		String url = null;
		try {
			// Note that we're encoding the & character to &amp; in XHTML mode
			// only,
			// otherwise the & is written as is to work in javascripts.
			url = TagUtils.getInstance().computeURLWithCharEncoding(
					pageContext, forward, href, page, action, module, params,
					anchor, false, this.isXhtml(), useLocalEncoding);
			url = RequestUtils.serverURL(
					(HttpServletRequest) pageContext.getRequest()).toString()
					+ url;
		} catch (MalformedURLException e) {
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(messages.getMessage("rewrite.url", e
					.toString()));
		}

		TagUtils.getInstance().write(pageContext, url);

		return (SKIP_BODY);

	}

}
