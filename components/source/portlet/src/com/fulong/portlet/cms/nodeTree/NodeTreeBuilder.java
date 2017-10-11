/**
 * 
 */
package com.fulong.portlet.cms.nodeTree;

import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * @author lixf
 * 
 */
public class NodeTreeBuilder {

	private Node root;
	private String selectedId;

	/**
	 * 
	 * @param root
	 *            ContentCategory
	 */
	public NodeTreeBuilder(Node root) {
		this.root = root;
	}

	/**
	 * 
	 * @param groupId
	 *            String
	 */
	public void setSelectedId(String groupId) {
		this.selectedId = groupId;
	}

	/**
	 * 构建树
	 * 
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
	 * 指定节点下的field复合属性下的树
	 * 
	 * @return Tree
	 */
	public Tree build(String field) {
		if(field == null || field.length()==0){
			return build();
		}else{
			final Tree tree = new Tree();
			if (this.root == null) {
				return tree;
			}
			if (selectedId == null || selectedId.equals("")) {
				selectedId = root.getID();
			}
			tree.addNode(root.getID(), "", root);

			addTreeChildren(tree, root , field);
			tree.select(selectedId);
			return tree;
		}
	}

	/**
	 * 只构建包含当前节点及其子节点的树
	 * 
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
	 * 
	 * @param tree
	 *            Tree
	 * @param node
	 *            Node
	 * @param hasMembers
	 *            boolean
	 */
	private void addTreeChildren(Tree tree, Node node) {
		NodeIterator<Node> children = node.getNodes();
		while (children.hasNext()) {
			Node child = children.nextNode();
			tree.addNode(child.getID(), node.getID(), child);
			addTreeChildren(tree, child);
		}
	}
	/**
	 * 
	 *在组织结构树tree的基础上，找到指定的node下所有子节点
	 */
	private void addTreeChildren(Tree tree, Node node,String field) {
		NodeIterator<Node> children = node.getNodes(field);
		while (children.hasNext()) {
			Node child = children.nextNode();
			tree.addNode(child.getID(), node.getID(), child);
			addTreeChildren(tree, child , field);
		}
	}

	/**
	 * 只为当前树节点添加子节点
	 * 
	 * @param tree
	 *            Tree
	 * @param root
	 *            NodeDefinition
	 */
	private void addRootChildren(Tree tree, Node root) {
		NodeIterator<Node> children = root.getNodes();
		while (children.hasNext()) {
			Node child = children.nextNode();
			tree.addNode(child.getID(), root.getID(), child);
		}
	}
}
