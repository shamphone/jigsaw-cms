package com.fulong.taglib.common;

import java.net.URLEncoder;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.util.ResponseUtils;

/**
 * 对（将组成URL的字符串）进行编码
 * <p>
 * Title: LongCon WebMaster
 * </p>
 * 
 * <p>
 * Description: 龙驭内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 中科辅龙计算机技术有限公司 2005
 * </p>
 * 
 * <p>
 * Company: 中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public class EncodeTag extends WriteTag {
	
	private static final long serialVersionUID = -698019803722622005L;
	
	private String encode;
	private TagUtils tagUtils = TagUtils.getInstance();

	public int doStartTag() throws JspException {

		// Look up the requested bean (if necessary)
		if (ignore) {
			if (tagUtils.lookup(pageContext, name, scope) == null)
				return (SKIP_BODY); // Nothing to output
		}
		if (encode == null)
			encode = "UTF-8";

		// Look up the requested property value
		Object value = tagUtils.lookup(pageContext, name, property, scope);
		if (value == null)
			return (SKIP_BODY); // Nothing to output

		// Convert value to the String with some formatting
		String output = formatValue(value);

		try {
			output = URLEncoder.encode(output, encode);
		} catch (Exception ex) {

		}

		// Print this property value to our output writer, suitably filtered
		if (filter)
			tagUtils.write(pageContext, ResponseUtils.filter(output));
		else
			tagUtils.write(pageContext, output);

		// Continue processing this page
		return (SKIP_BODY);

	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getEncode() {
		return encode;
	}

}
