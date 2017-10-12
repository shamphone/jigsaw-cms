package com.fulong.taglib.common;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

import com.fulong.common.util.Tree;
import com.fulong.common.util.TreeNode;

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

public class TreeTag extends BodyTagSupport {
	
	private static final long serialVersionUID = -6893671727543707973L;
	
	private String name = null;
	private String property = null;
	private String scope = null;
	private String objectId = null;
	private Tree tree = null;
	
	@SuppressWarnings("unchecked")
	private Iterator iterator = null;
	private TreeNode current = null;
	private TagUtils utils = TagUtils.getInstance();
	private int depth = 0;
	private String nodeId;
	private String style = "tree";

	private String renderStartLI(TreeNode node) {
		StringBuffer result = new StringBuffer("");
		if (node.getChildNum() > 0 && node.getDepth() != 0) {
			result.append("<li class=\"children closed " + "\" id=\""+node.getId()+"\">");
		}

		else {
			result.append("<li id=\"" + node.getId() + "\">");

		}
		return result.toString();
	}

	private String renderEndLI(int depth) {
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < depth; i++)
			result.append(" ");
		result.append("</li>\r\n");
		return result.toString();
	}

	private String renderStartUL(TreeNode node) {
		this.depth++;
		StringBuffer result = new StringBuffer();
		result.append("<ul>\r\n");
		return result.toString();
	}

	private String renderEndUL(int depth) {
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < depth; i++)
			result.append(" ");
		result.append("</ul>");
		this.depth--;
		return result.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getStyle() {
		return style;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int doStartTag() throws JspException {

		// Acquire the collection we are going to iterate over
		this.tree = (Tree) utils.lookup(pageContext, name, property, scope);
		this.depth = 0;
		if (tree == null) {
			JspException e = new JspException("Error in Tree Tag");
			utils.saveException(pageContext, e);
			throw e;
		}
		iterator = tree.getNodes().iterator();

		if (iterator.hasNext()) {
			TreeNode element = (TreeNode) iterator.next();
			current = element;
			if (objectId != null)
				pageContext.setAttribute(objectId, element.getNode());
			if (nodeId != null)
				pageContext.setAttribute(nodeId, element);
			utils.write(pageContext, "<ul class=\"" + this.style + "\">\r\n");
			utils.write(pageContext, this.renderStartLI(element));
			return (EVAL_BODY_BUFFERED);
		} else {
			return (SKIP_BODY);
		}
	}

	/**
	 * Make the next collection element available and loop, or finish the
	 * iterations if there are no more elements.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doAfterBody() throws JspException {
		// Render the output from this iteration to the output stream
		if (bodyContent != null) {
			// utils.write(pageContext, this.renderEndLI(current));
			utils.writePrevious(pageContext, "<span>");
			utils.writePrevious(pageContext, bodyContent.getString());
			utils.writePrevious(pageContext, "</span>");
			bodyContent.clearBody();
		}
		if (iterator.hasNext()) {
			TreeNode element = (TreeNode) iterator.next();
			if (element == null) {
				pageContext.removeAttribute(objectId);
			} else {
				if (element.getDepth() > current.getDepth()) {
					utils.writePrevious(pageContext, "\r\n");
					utils.writePrevious(pageContext, this
							.renderStartUL(element));
				} else if (element.getDepth() < current.getDepth()) {
					for (int i = element.getDepth(); i < current.getDepth(); i++) {
						utils.writePrevious(pageContext, this.renderEndLI(i));
						utils.writePrevious(pageContext, "\r\n");
						utils.writePrevious(pageContext, this.renderEndUL(i));
					}
				} else {
					utils.writePrevious(pageContext, this.renderEndLI(element
							.getDepth()));
				}

				utils.writePrevious(pageContext, this.renderStartLI(element));
				current = element;
				if (objectId != null)
					pageContext.setAttribute(objectId, element.getNode());
				if (nodeId != null)
					pageContext.setAttribute(nodeId, element);

			}
			return (EVAL_BODY_AGAIN);
		} else {
			return (SKIP_BODY);
		}

	}

	/**
	 * Clean up after processing this enumeration.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {
		// utils.write(pageContext, this.renderEnd());
		for (int i = this.depth; i >= 0; i--) {
			utils.write(pageContext, this.renderEndLI(0));
			utils.write(pageContext, this.renderEndUL(0));
		}
		if (objectId != null)
			pageContext.removeAttribute(objectId);
		if (nodeId != null)
			pageContext.removeAttribute(nodeId);
		iterator = null;
		this.current = null;
		this.depth = 0;
		return (EVAL_PAGE);
	}

	/**
	 * Release all allocated resources.
	 */
	public void release() {

		super.release();

		name = null;
		property = null;
		scope = null;
		objectId = null;
		tree = null;
		iterator = null;
		nodeId = null;

	}

}
