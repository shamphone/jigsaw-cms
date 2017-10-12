package com.fulong.portal.page;

import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.Node;

/**
 * 用于支持htmlparser对jsp页面进行解析的fulong:preference标签
 * 
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
public class HTMLPreferenceTag extends CompositeTag {

	private static final long serialVersionUID = -6547075646811706755L;
	
	public HTMLPreferenceTag() {
	}

	public String[] getIds() {
		return mIds;
	}

	public String[] getEndTagEnders() {
		return mEndTagEnders;
	}

	public String[] getEnders() {
		return mEnders;
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer("<fulong:preference>");
		for (int i = 0; i < this.getChildCount(); i++) {
			Node node = this.getChild(i);
			if (node instanceof CompositeTag) {
				sb.append(node.toHtml());
			}
		}
		sb.append("</fulong:preference>");
		return sb.toString();
	}

	public void setPreference(String name, String[] values) {
		NodeList children = new NodeList();
		HtmlNameTag nameTag = new HtmlNameTag();
		nameTag.setName(name);
		children.add(nameTag);
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null) {
				HtmlValueTag tag = new HtmlValueTag();
				tag.setValue(values[i]);
				children.add(tag);
			}
		}
		this.setChildren(children);
	}

	private static final String mIds[] = { "fulong:preference" };
	private static final String mEndTagEnders[] = { "BODY", "HTML", "FULONG:PORTLET" };
	private static final String[] mEnders = new String[] { "FULONG:PORTLET", "FULONG:PREFERENCE" };
}
