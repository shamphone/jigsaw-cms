package com.fulong.cms;

import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * 建立树
 * <p>Title: 龙驭建站系统3.0</p>
 *
 * <p>Description: 龙驭建站系统3.0</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lichengzhao
 * @version 2.0
 */
public class NodeTreeBuilder {
    private Node root;
    private String selectedId;


    /**
     *
     * @param root ContentCategory
     */
    public NodeTreeBuilder(Node root) {
        this.root = root;
    }

    /**
     *
     * @param groupId String
     */
    public void setSelectedId(String groupId) {
        this.selectedId = groupId;
    }

    /**
     * 构建树
     * @return Tree
     */
    public Tree build() {
        final Tree tree = new Tree();
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
    }

    /**
     * 只构建包含当前节点及其子节点的树
     * @return Tree
     */
    public Tree buildPartTree() {
        final Tree tree = new Tree();
        if (this.root == null) {
            return tree;
        }
        if (selectedId == null || selectedId.equals("")) {
            selectedId = root.getID();
        }
        tree.addNode(root.getID(), "", root);

        addRootChildren(tree, root);
        tree.select(selectedId);
        return tree;
    }

    /**
     * 在组织结构树tree的基础上，添加以root为根的树节点。
     * @param tree Tree
     * @param node Node
     * @param hasMembers boolean
     */
    private void addTreeChildren(Tree tree, Node node) {
        NodeIterator children = node.getNodes();
        while (children.hasNext()) {
            Node child = children.nextNode();
            tree.addNode(child.getID(), node.getID(), child);
            addTreeChildren(tree, child);
        }
    }
    /**
     * 只为当前树节点添加子节点
     * @param tree Tree
     * @param root NodeDefinition
     */
    private void addRootChildren(Tree tree,Node root) {
        NodeIterator children = root.getNodes();
        while (children.hasNext()) {
            Node child = children.nextNode();
            tree.addNode(child.getID(), root.getID(), child);
        }
    }
}

