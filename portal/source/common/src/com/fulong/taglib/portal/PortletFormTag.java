package com.fulong.taglib.portal;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import org.apache.struts.taglib.html.FormTag;
import com.fulong.portlet.Constants;

/**
 * <p>
 * Title: 龙驭门户引擎
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
 * @author Lixf
 * @version 1.0
 */
public class PortletFormTag extends FormTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2689960937721088408L;

	public PortletFormTag() {
		super();
	}

	protected void renderAction(StringBuffer results) {
		RenderResponse response = (RenderResponse) this.pageContext.getResponse();
		PortletURL url = response.createActionURL();
		url.setParameter(Constants.KEY_ACTION_NAME, this.getAction());
		results.append(" action=\"");
		results.append(url.toString());
		results.append("\"");
	}

	/**
	 * 除掉开始的"/"符号
	 * 
	 * @param action
	 *            String
	 */
	public String getAction() {
		if (action.startsWith("/"))
			return action.substring(1);
		else
			return action;
	}
}
