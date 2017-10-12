package com.fulong.taglib.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.htmlparser.util.ParserException;

import com.fulong.common.util.StringUtils;

/**
 * 
 * 过滤html标签 Coolink协同工作框架模型
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
public class FilterHtmlTag extends BodyTagSupport {
	private static Log log = LogFactory.getLog(FilterHtmlTag.class);
	private String length;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4084689729104734309L;

	public int doEndTag() throws JspException {
		String source = this.getBodyContent().getString();
		if (source == null)
			source = "";
		String result = "";
		try {
			result = StringUtils.html2Text(source);
		} catch (ParserException e) {
			log.error("Unable to convert sourc to text:" + source);
			result = source;
		}
		if ((this.length != null) && (this.length.length() > 0)) {
			if (result.length() > Integer.parseInt(length)) {
				result = result.substring(0, Integer.parseInt(length));
			}
		}
		TagUtils.getInstance().write(this.pageContext, result);
		this.length = null;
		return EVAL_PAGE;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
}
