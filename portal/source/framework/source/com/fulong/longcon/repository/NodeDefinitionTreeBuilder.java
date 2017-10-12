package com.fulong.longcon.repository;

import java.util.Iterator;

import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * 建立树
 * <p>
 * Title: 龙驭建站系统3.0
 * </p>
 * 
 * <p>
 * Description: 龙驭建站系统3.0
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lishaobo
 * @version 2.0
 */
public class NodeDefinitionTreeBuilder {
	private NodeDefinition root;
	private String selectedId;
	/**
	 * 
	 * @param root
	 *            ContentCategory
	 */
	public NodeDefinitionTreeBuilder(NodeDefinition root) {
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
	 * 构建一个不含根节点的树
	 * 
	 * @param num
	 *            int 显示一级子节点的个数
	 * @return Tree
	 */
	public Tree build(Boolean hasRoot, String num) {
		final Tree tree = new Tree();
		if (this.root == null) {
			return tree;
		}
		if (selectedId == null || selectedId.equals("")) {
			selectedId = root.getID();
		}
		if (hasRoot)
			tree.addNode(root.getID(), "", root);

		addTreeChildren(tree, root, num);
		tree.select(selectedId);
		return tree;
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
	 * 在组织结构树tree的基础上，添加以root为根的树节点。 根据hasMembers参数决定是否添加root中各个节点的成员。
	 * 
	 * @param tree
	 *            Tree
	 * @param root
	 *            Group
	 * @param hasMembers
	 *            boolean
	 */
	@SuppressWarnings("unchecked")
	private void addTreeChildren(Tree tree, NodeDefinition root) {
		Iterator children = root.getInheritDefinitions(false);
		while (children.hasNext()) {
			NodeDefinition child = (NodeDefinition) children.next();
			tree.addNode(child.getID(), root.getID(), child);
			// 只加载一级子分类 by mali 2010-6-30
			// if (child.getInheritDefinitions(false).hasNext()) {
			// addTreeChildren(tree, child);
			// }

		}
	}

	/**
	 * 在组织结构树tree的基础上，添加以root为根的树节点。 根据hasMembers参数决定是否添加root中各个节点的成员。
	 * 
	 * @param tree
	 *            Tree
	 * @param root
	 *            Group
	 * @param num
	 *            String 取一级子节点的个数
	 * @param hasMembers
	 *            boolean
	 */
	@SuppressWarnings("unchecked")
	private void addTreeChildren(Tree tree, NodeDefinition root, String num) {
		Iterator children = root.getInheritDefinitions(false);
		if (num == null || num.equals("")) {
			while (children.hasNext()) {
				NodeDefinition child = (NodeDefinition) children.next();
				tree.addNode(child.getID(), root.getID(), child);
			}
		} else {
			int n = Integer.parseInt(num);
			for (int i = 0; children.hasNext() && i < n; i++) {
				NodeDefinition child = (NodeDefinition) children.next();
				tree.addNode(child.getID(), root.getID(), child);
				// 只加载一级子分类 by mali 2010-6-30
				// if (child.getInheritDefinitions(false).hasNext()) {
				// addTreeChildren(tree, child);
				// }
			}
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
	@SuppressWarnings("unchecked")
	private void addRootChildren(Tree tree, NodeDefinition root) {
		Iterator children = root.getInheritDefinitions(false);
		while (children.hasNext()) {
			NodeDefinition child = (NodeDefinition) children.next();
			tree.addNode(child.getID(), root.getID(), child);
		}
	}
}
