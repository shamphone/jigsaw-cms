package com.fulong.taglib.common;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

import com.fulong.common.util.Tree;
import com.fulong.common.util.TreeNode;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 中科辅龙计算机技术有限公司 2004</p>
 * <p>Company: 中科辅龙计算机技术有限公司</p>
 * @author <a href="mailto:lixf@fulong.com.cn">李雄锋</a>
 * @author 姜崎
 * @version 1.0
 */

public class XTreeTag extends BodyTagSupport {

	private static final long serialVersionUID = -4601278181273776994L;
	
	public static final String XTREE_TAG = "com.fulong.taglib.xtree";
    private String name = null;
    private String property = null;
    private String scope = null;
    private Tree tree = null;
    @SuppressWarnings("unchecked")
	private Iterator iterator = null;
    private TreeNode current = null;
    private TagUtils utils = TagUtils.getInstance();
    private String nodeId;
    private boolean varxtree = false;
    private String sText;
    private String sAction;
    private String sIcon;
    private String sOpenIcon;
    private String eParent;
    private boolean isInTreeEmpty = true; //判断tree中有没有内容,默认为true,即无内容

    private String treeNode;
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

    public String getNodeId() {
        return nodeId;
    }

    public String getSText() {
        return sText;
    }

    public String getSAction() {
        return sAction;
    }

    public String getSIcon() {
        return sIcon;
    }

    public String getSOpenIcon() {
        return sOpenIcon;
    }

    public String getEParent() {
        return eParent;
    }

    public String getTreeNode() {
        return treeNode;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setSText(String sText) {
        this.sText = sText;
    }

    public void setSAction(String sAction) {
        this.sAction = sAction;
    }

    public void setSIcon(String sIcon) {
        this.sIcon = sIcon;
    }

    public void setSOpenIcon(String sOpenIcon) {
        this.sOpenIcon = sOpenIcon;
    }

    public void setEParent(String eParent) {
        this.eParent = eParent;
    }

    public void setTreeNode(String treeNode) {
        this.treeNode = treeNode;
    }

    public int doStartTag() throws JspException {

        this.pageContext.setAttribute(XTREE_TAG, this);
        // Acquire the collection we are going to iterate over
        this.tree = (Tree) utils.lookup(pageContext, name, property, scope);
        if (tree == null) {
            JspException e = new JspException("Error in Tree Tag");
            utils.saveException(pageContext, e);
            throw e;
        }
        varxtree = false;
        iterator = tree.getNodes().iterator();
        utils.write(pageContext,
                    "<script type='text/Javascript' language='Javascript'>");
        utils.write(pageContext, "\n");
        if (iterator.hasNext()) {
            isInTreeEmpty = false;
            TreeNode element = (TreeNode) iterator.next();
            current = element;
            pageContext.setAttribute(nodeId, element.getNode());
            if (treeNode != null) {
                pageContext.setAttribute(treeNode, current);
            }
            return (EVAL_BODY_BUFFERED);
        } else {
            return (SKIP_BODY);
        }
    }

    /**
     * Make the next collection element available and loop, or
     * finish the iterations if there are no more elements.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {
        // Render the output from this iteration to the output stream
        if (!varxtree) {
            varxtree = true;
            if (current != null && !current.isEnabled()) {
                this.sAction = null;
            }

            StringBuffer js = new StringBuffer();
            js.append(" var tree = new WebFXTree('");
            js.append(escape(this.sText)).append("','");
            js.append(this.sAction == null ? "" : escape(this.sAction)).append("','");
            js.append(this.eParent == null ? "" : this.eParent).append("','");
            js.append(this.sIcon == null ? "" : this.sIcon).append("','");
            js.append(this.sOpenIcon == null ? "" :
                      this.sOpenIcon).append("');\n");
            js.append(" var item" + makeID(current.getId()) +
                      " = tree;\n");
            js.append("tree.setID('" + current.getId() + "');\n");
            js.append("tree.setRoot();\n");
            utils.writePrevious(pageContext, js.toString());
            if (iterator.hasNext()) {
                TreeNode element = (TreeNode) iterator.next();
                current = element;
                pageContext.setAttribute(nodeId, element.getNode());
                if (treeNode != null) {
                    pageContext.setAttribute(treeNode, current);
                }
                bodyContent.clearBody();
                return (EVAL_BODY_AGAIN);
            } else {
                return (SKIP_BODY);
            }
        }
        if (current != null) {
            StringBuffer js = new StringBuffer();
            if (!current.isEnabled()) {
                this.sAction = null;
            }
            js.append(" var item").append(makeID(current.getId())).
                    append(" = new WebFXTreeItem('");
            js.append(escape(this.sText)).append("','");
            js.append(this.sAction == null ? "" : escape(this.sAction)).append("','");
            js.append(this.eParent == null ? "" : this.eParent).append("','");
            js.append(this.sIcon == null ? "" : this.sIcon).append("','");
            js.append(this.sOpenIcon == null ? "" :
                      this.sOpenIcon).append("');\n");
            js.append("item" + makeID(current.getId()) + ".setID('" + current.getId() + "');\n");
            utils.writePrevious(pageContext, js.toString());
            if (current.getDepth() == 1) {
                utils.writePrevious(pageContext,
                                    " tree.add(item" +
                                    makeID(current.getId()) + ");");
            } else {
                utils.writePrevious(pageContext,
                                    " item" + makeID(current.getParentId()) +
                                    ".add(item" +
                                    makeID(current.getId()) + ");");
            }
            if (current.isSelected()) {
                utils.writePrevious(pageContext,
                                    " _oSelectedItem=item" +
                                    makeID(current.getId()) + ";");
                
                utils.writePrevious(pageContext,
                        "item" +
                        makeID(current.getId()) + ".expand();");
            }
            utils.writePrevious(pageContext, "\n");
        }
        if (iterator.hasNext()) {
            TreeNode element = (TreeNode) iterator.next();
            current = element;
            pageContext.setAttribute(nodeId, element.getNode());
            if (treeNode != null) {
                pageContext.setAttribute(treeNode, current);
            }
            bodyContent.clearBody();
            return (EVAL_BODY_AGAIN);
        } else {
            return (SKIP_BODY);
        }
    }

    /**
     * Clean up after processing this enumeration.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        if (isInTreeEmpty == false || varxtree == true) {
            utils.write(pageContext, "document.write(tree);");
        }
        utils.writePrevious(pageContext, "\n");
        utils.write(pageContext, "</script>");
        utils.writePrevious(pageContext, "\n");
        if (nodeId != null) {
            pageContext.removeAttribute(nodeId);
        }
        iterator = null;
        return (EVAL_PAGE);

    }

    /**
     * Release all allocated resources.
     */
    public void release() {

        super.release();
        varxtree = false;
        name = null;
        property = null;
        scope = null;
        tree = null;
        iterator = null;
        nodeId = null;
        treeNode = null;
    }

    private String makeID(String s) {
        return s.replaceAll("\\W","");
    }

    private String escape(String s) {
            return TagUtils.getInstance().filter(s);
    }

}
