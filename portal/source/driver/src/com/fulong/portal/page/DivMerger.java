package com.fulong.portal.page;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.tags.Div;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */

class DivMerger implements NodeFilter {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6032113883794176660L;
	private int start = 0;
    private String fullText;
    private StringBuffer result;
    private NodeList portlets;
    private static final Log log = LogFactory.getLog(DivMerger.class);
    public DivMerger(NodeList portlets, String fullText) {
        this.portlets = portlets;
        this.fullText = fullText;
        start = 0;
        result = new StringBuffer();
    }

    public String getResult() {
        result.append(fullText.substring(start, fullText.length()));
        return result.toString();
    }

    private boolean isPortletDiv(TagNode tagNode) {
        return tagNode.getTagName().equalsIgnoreCase("div") &&
                "portletWindow".equalsIgnoreCase(tagNode.getAttribute("class"));
    }

    private TagNode searchPortlet(NodeList portlets, final String id) {
        if (id == null) {
            return null;
        }
        NodeList list = portlets.extractAllNodesThatMatch(new NodeFilter() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 312756268478181202L;

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
        if (node instanceof Div) {
            Div tagNode = (Div) node;
            if (isPortletDiv(tagNode)) {
                log.trace("Find portlet div:" + tagNode.toHtml());
                result.append(fullText.substring(start,
                                                 tagNode.getStartPosition()));
                start = tagNode.getEndTag().getEndPosition();
                String id = tagNode.getAttribute("id");
                TagNode portlet = searchPortlet(portlets,
                                                id);
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
