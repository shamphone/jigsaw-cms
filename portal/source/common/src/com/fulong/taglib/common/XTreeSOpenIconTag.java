package com.fulong.taglib.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 中科辅龙计算机技术有限公司 2004
 * </p>
 * <p>
 * Company: 中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href="mailto:lixf@fulong.com.cn">李雄锋</a>
 * @author 姜崎
 * @version 1.0
 */

public class XTreeSOpenIconTag extends BodyTagSupport {

	private static final long serialVersionUID = -4194175326642307054L;

	public int doEndTag() throws JspException {
		XTreeTag xtreeTag = (XTreeTag) this.pageContext
				.getAttribute(XTreeTag.XTREE_TAG);
		if (xtreeTag != null) {
			xtreeTag.setSOpenIcon(bodyContent.getString().trim());
		}
		return EVAL_PAGE;
	}

}
