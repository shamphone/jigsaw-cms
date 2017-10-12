package com.fulong.portal.page;

import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;

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
public class HtmlNameTag extends CompositeTag {

	private static final long serialVersionUID = -2871187965652973020L;

	public HtmlNameTag() {
	}

	public String[] getIds() {
		return mIds;
	}

	public String[] getEnders() {
		return mEnders;
	}

	public String[] getEndTagEnders() {
		return mEndTagEnders;
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer("<fulong:name>");
		putChildrenInto(sb);
		sb.append("</fulong:name>");

		return sb.toString();
	}

	public void setName(String name) {
		TextNode node = new TextNode(name);
		this.setChildren(new NodeList(node));
	}

	private static final String mIds[] = { "fulong:name" };
	private static final String mEndTagEnders[] = { "BODY", "HTML", "FULONG:PORTLET", "FULONG:VALUE", "FULONG:PREFERENCE" };
	private static final String[] mEnders = new String[] { "FULONG:PREFERENCE", "FULONG:NAME", "FULONG:VALUE" };

}
