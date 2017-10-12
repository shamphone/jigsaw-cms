package com.fulong.portal.page;

import org.htmlparser.tags.CompositeTag;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;

/**
 * 用于支持htmlparser对jsp页面进行解析的fulong:value标签
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
public class HtmlValueTag extends CompositeTag {

	private static final long serialVersionUID = -6623596544312608380L;

	public HtmlValueTag() {
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

	public void setValue(String value) {
		TextNode node = new TextNode(value);
		this.setChildren(new NodeList(node));
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer("<fulong:value>");
		putChildrenInto(sb);
		sb.append("</fulong:value>");
		return sb.toString();
	}

	private static final String mIds[] = { "fulong:value" };
	private static final String mEndTagEnders[] = { "BODY", "HTML", "FULONG:PORTLET", "FULONG:PREFERENCE" };
	private static final String[] mEnders = new String[] { "FULONG:PREFERENCE", "FULONG:NAME", "FULONG:VALUE" };
}
