package com.fulong.taglib.common;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

import com.fulong.common.HTMLWriter;
import com.fulong.common.util.Tree;
import com.fulong.common.util.TreeNode;

/**
 * 
 * 以表格形式产生的树形结构
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2008
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 3.0
 */
public class TreeTableTag extends BodyTagSupport {
	
	private static final long serialVersionUID = -8513434809527212429L;
	
	private String name = null;
	private String property = null;
	private String scope = null;
	private String objectId = null;
	private Tree tree = null;
	@SuppressWarnings("unchecked")
	private Iterator iterator = null;
	private TagUtils utils = TagUtils.getInstance();
	private String styleClass;
	private String nodeId;
	private String border;
	private String cellspacing;
	private String cellpadding;
	private String width;
	private String align;
	private String id;
	private boolean empty = true;

	public TreeTableTag() {
		this.styleClass = null;
		this.cellpadding = "0";
		this.cellspacing = "0";
		this.width = "100%";
		this.align = "center";
		this.border = "0";
		this.id = "treeTable";
	}

	private String renderTagStart() {
		HTMLWriter writer = new HTMLWriter();
		writer.beginElement("table");
		writer.writeAttribute("id", this.id);
		writer.writeAttribute("width", this.width);
		writer.writeAttribute("border", this.border);
		writer.writeAttribute("align", this.align);
		writer.writeAttribute("cellpadding", this.cellpadding);
		writer.writeAttribute("cellspacing", this.cellspacing);
		writer.writeAttribute("class", styleClass);
		writer.closeElement();
		return writer.toString();
	}

	private String renderTagEnd() {
		HTMLWriter writer = new HTMLWriter();
		writer.endElement("table");
		return writer.toString();
	}

	private String renderNodeStart(TreeNode node) {
		HTMLWriter writer = new HTMLWriter();
		writer.writeln();
		writer.beginElement("tr");
		if (node.isOpen()) {
			writer.writeAttribute("status", "open");
		} else {
			writer.writeAttribute("status", "closed");
		}
		writer.writeAttribute("id", node.getId());
		writer.writeAttribute("parentid", node.getParentId());
		writer.writeAttribute("level", "" + node.getDepth());
		if (!node.isVisible()) {
			writer.writeAttribute("style", "display:none");
		}
		writer.closeElement();
		writer.writeln();
		writer.beginElement("td");
		writer.writeAttribute("align", "left");
		writer.writeAttribute("style", "padding-left:" + (node.getDepth() * 10)
				+ "px");
		writer.closeElement();
		writer.beginElement("img");

		StringBuffer imagePath = new StringBuffer();
		imagePath.append(((HttpServletRequest) pageContext.getRequest())
				.getContextPath());
		if (node.getChildNum() > 0) {
			if (node.isOpen()) {
				imagePath.append("/common/images/minus.gif\" ");
			} else {
				imagePath.append("/common/images/plus.gif\" ");
			}
			writer.writeAttribute("src", imagePath.toString());
			writer.writeAttribute("onclick", "clickTreeSign(this)");
		} else {
			imagePath.append("/common/images/dian.gif\"");
			writer.writeAttribute("src", imagePath.toString());
		}
		writer.writeAttribute("style", "cursor:hand");
		writer.writeAttribute("border", "0");
		writer.closeElement();
		return writer.toString();
	}

	private String renderNodeEnd() {
		HTMLWriter writer = new HTMLWriter();
		writer.endElement("td");
		writer.writeln();
		writer.endElement("tr");
		return writer.toString();
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

	public String getStyleClass() {
		return styleClass;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getBorder() {
		return border;
	}

	public String getCellspacing() {
		return cellspacing;
	}

	public String getCellpadding() {
		return cellpadding;
	}

	public String getWidth() {
		return width;
	}

	public String getAlign() {
		return align;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int doStartTag() throws JspException {

		// Acquire the collection we are going to iterate over
		this.tree = (Tree) utils.lookup(pageContext, name, property, scope);

		if (tree == null) {
			JspException e = new JspException("Error in Tree Tag");
			utils.saveException(pageContext, e);
			throw e;
		}
		iterator = tree.getNodes().iterator();

		if (iterator.hasNext()) {
			this.empty = false;
			TreeNode element = (TreeNode) iterator.next();
			if (objectId != null) {
				pageContext.setAttribute(objectId, element.getNode());
			}
			if (nodeId != null) {
				pageContext.setAttribute(nodeId, element);
			}
			utils.write(pageContext, this.renderTagStart());
			utils.write(pageContext, this.renderNodeStart(element));
			return (EVAL_BODY_BUFFERED);
		} else {
			return (SKIP_BODY);
		}
	}

	public void doInitBody() throws JspException {

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
		utils.writePrevious(pageContext, this.renderNodeEnd());
		if (iterator.hasNext()) {
			TreeNode element = (TreeNode) iterator.next();

			utils.writePrevious(pageContext, this.renderNodeStart(element));
			if (objectId != null) {
				pageContext.setAttribute(objectId, element.getNode());
			}
			if (nodeId != null) {
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
		if (!this.empty) {
			utils.write(pageContext, this.renderTagEnd());
		}
		iterator = null;
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
		nodeId = null;
		tree = null;
		iterator = null;
		this.styleClass = null;
		this.cellpadding = "0";
		this.cellspacing = "0";
		this.width = "100%";
		this.align = "center";
		this.border = "0";

	}

}
