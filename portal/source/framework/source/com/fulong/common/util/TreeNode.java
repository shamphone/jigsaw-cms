package com.fulong.common.util;

import java.io.Serializable;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */

@SuppressWarnings("unchecked")
public class TreeNode implements Serializable, Comparable {

	private static final long serialVersionUID = -729157642196085131L;
	
	/**
     * 节点ID
     */
    private String id;
    /**
     * 父节点ID
     */
    private String parentId;
    /**
     * 节点的值
     */
    private Object node;
    /**
     * 节点是否被选中
     */
    private boolean selected;
    /**
     * 节点是否被激活可用
     */
    private boolean enabled;
    /**
     * 节点的深度，根节点为0，子节点深度=父节点+1
     */
    private int depth;
    /**
     * 节点是否可见，一个节点是可见的，指它在树状表示中并不被折叠隐藏起来。如果这个节点是打开的，或者他的父节点是打开的，则这个节点是可见的。
     */
    private boolean visible;
    /**
     * 子节点（不包含子子节点）个数
     */
    private int childNum;
    /**
     * 节点是否被打开，如果当前节点是打开的，则其父节点必然也是打开的。
     */
    private boolean open;
    /**
     * 节点是否是保留，如果一个节点是enabled的，则其所有父节点都是reserved的。
     * 这个功能用于过滤掉那些从当前节点到叶节点都是被disabled的节点，这些节点可能在页面上都不显示
     */
    private boolean reserved;
    public TreeNode() {
        selected = false;
        enabled = true;
        depth = 0;
        childNum = 0;
        visible = false;
        reserved = false;
    }

    public int compareTo(Object object) {
        TreeNode node = (TreeNode) object;
        if (node == null) {
            return -1;
        }
        return node.getId().compareTo(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Object getNode() {
        return node;
    }

    public void setNode(Object node) {
        this.node = node;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
