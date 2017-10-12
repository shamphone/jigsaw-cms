package com.fulong.longcon.site;

import com.fulong.common.util.Tree;
import com.fulong.common.util.TreeNode;

/**
 * 建立组树
 * <p>Title: 龙驭建站系统3.0</p>
 *
 * <p>Description: 龙驭建站系统3.0</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishaobo
 * @version 2.0
 */
public class ChannelTreeBuilder {
    @SuppressWarnings("unused")
	private SiteTemplate template;
    @SuppressWarnings("unused")
	private Channel root;
    @SuppressWarnings("unused")
	private String selectedId;

    /**
     *
     * @param org Organization
     * @param type String
     * @param hasMembers boolean
     */
    public ChannelTreeBuilder(SiteTemplate template) {
        this.template = template;
    }

    /**
     *
     * @param root Group
     * @param hasMembers boolean
     */
    public ChannelTreeBuilder(Channel root) {
        this.root = root;
    }

    /**
     *
     * @param groupId String
     */
    public void setSelectedId(String channelID) {
        this.selectedId = channelID;
    }

    /**
     * 构建组树
     * @return Tree
     */
    public Tree build() {
    	return null;
    	/*
        final Tree tree = new Tree();
        if (this.root == null) {
            root = template.getRootChannel();
        }
        if (this.root == null) {
            return tree;
        }
        if (selectedId == null || selectedId.equals("")) {
            selectedId = root.getID();
        }
        tree.addNode(root.getID(), "", root);
        addTreeChildren(tree, root);
        tree.select(selectedId);
        return tree;
        */
    }

    /**
     * 按栏目类型进行过滤
     * @param type String
     * @param tree Tree
     */
    public void filterByType(Tree tree,String type){
    	for(TreeNode tn:tree.getNodes()){
    		Channel channel=(Channel)tn.getNode();
    		if(channel.getType().equals(type)){
    			tn.setEnabled(false);
    		}
    	}
    }
}
