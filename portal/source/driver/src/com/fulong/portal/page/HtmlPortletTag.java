package com.fulong.portal.page;

import org.htmlparser.tags.CompositeTag;
import com.fulong.portal.model.PreferenceSet;
import java.util.Iterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.Node;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class HtmlPortletTag extends CompositeTag {
	
	private static final long serialVersionUID = 2466048126770760825L;
	
	public HtmlPortletTag() {
	}

	public String[] getIds() {
		return mIds;
	}

	public String[] getEndTagEnders() {
		return mEndTagEnders;
	}

	protected void putChildrenInto(StringBuffer sb) {
		Node node;
		for (SimpleNodeIterator e = children(); e.hasMoreNodes();) {
			node = e.nextNode();
			if (node instanceof HTMLPreferenceTag)
				sb.append(node.toHtml());
		}
	}

	@SuppressWarnings("unchecked")
	public void setPreferences(PreferenceSet preferences) {
		NodeList children = new NodeList();

		for (Iterator names = preferences.names(); names.hasNext();) {
			String name = (String) names.next();
			String[] values = preferences.getValues(name);
			HTMLPreferenceTag tag = new HTMLPreferenceTag();
			tag.setPreference(name, values);
			children.add(tag);
		}
		this.setChildren(children);
	}

	private static final String mIds[] = { "fulong:portlet" };
	private static final String mEndTagEnders[] = { "BODY", "HTML" };

}
