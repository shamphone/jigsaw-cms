package com.fulong.portal.page;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.BodyTag;

/**
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
 * @author 李雄锋
 * @version 1.0
 */
public class PortletMerger implements NodeFilter {
	
	private static final long serialVersionUID = -8367553037881249197L;
	
	private int start = 0;
	private int end = 0;
	private String fullText;
	private StringBuffer result;
	private NodeList portlets;
	private static final String TAG_NAME = "div";
	private static final Log log = LogFactory.getLog(PortletMerger.class);

	public PortletMerger(NodeList portlets, String fullText) {
		this.portlets = portlets;
		this.fullText = fullText;
		start = 0;
		end = this.fullText.length();
		result = new StringBuffer();
	}

	public String getResult() {
		result.append(fullText.substring(start, end));
		return result.toString();
	}

	private boolean isPortletSpan(TagNode tagNode) {
		return tagNode.getTagName().equalsIgnoreCase(TAG_NAME)
				&& "portletWindow".equalsIgnoreCase(tagNode
						.getAttribute("class"));
	}

	private TagNode searchPortlet(NodeList portlets, final String id) {
		if (id == null) {
			return null;
		}
		NodeList list = portlets.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof TagNode) {
					TagNode tagNode = (TagNode) node;
					return id.equalsIgnoreCase(tagNode.getAttribute("id"));
				} else {
					return false;
				}
			}

		});
		if (list.size() > 0) {
			return (TagNode) list.elementAt(0);
		} else {
			return null;
		}
	}

	public boolean accept(Node node) {
		if (node instanceof BodyTag) {
			BodyTag tag = (BodyTag) node;
			if (tag.getChildCount() > 0)
				start = tag.getChild(0).getStartPosition();
			end = tag.getEndTag().getStartPosition();
		} else if (node instanceof Div) {
			Div tagNode = (Div) node;
			if (isPortletSpan(tagNode)) {
				log.trace("Find portlet div:" + tagNode.toHtml());
				result.append(fullText.substring(start, tagNode
						.getStartPosition()));
				start = tagNode.getEndTag().getEndPosition();
				String id = tagNode.getAttribute("id");
				TagNode portlet = searchPortlet(portlets, id);
				log.trace("begin insert " + result.toString());
				if (portlet != null) {
					result.append(portlet.toHtml());
				} else {
					result.append("<fulong:portlet id=\"");
					result.append(tagNode.getAttribute("id"));
					result.append("\" type=\"");
					result.append(tagNode.getAttribute("type"));
					result.append("\"></fulong:portlet>");
				}
				log.trace("after insert " + result.toString());
			}
		}
		return false;
	}

}
