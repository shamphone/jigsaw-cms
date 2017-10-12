package com.fulong.common.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */

public class Tree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2749969926105232926L;
	/**
	 * 当前选中的节点，在树展示的时候，这个节点可能需要高亮显示
	 */
	private TreeNode selected;
	/**
	 * <节点ID，TreeNode节点>的映射表
	 */
	private Map<String, TreeNode> nodes;
	/**
	 * 标记是否对节点属性作了重新计算
	 */
	private boolean calculated = false;

	public Tree() {
		selected = null;
		nodes = new LinkedHashMap<String, TreeNode>();
		this.calculated = false;
	}

	/**
	 * 添加节点到树中
	 * 
	 * @param nodeId
	 *            String 节点标识，在整个树中都是唯一的
	 * @param parentId
	 *            String 父节点，新节点将作为这个节点的子节点
	 * @param value
	 *            Object 节点附带的对象
	 */
	public void addNode(String nodeId, String parentId, Object value) {
		this.addNode(nodeId, parentId, true, false, value);
	}

	/**
	 * 将nodeID对应的节点标识为选中的节点。如果原来有选中的节点，则原节点不再被选中。
	 * 
	 * @param nodeId
	 *            String
	 */
	public void select(String nodeId) {
		this.calculated = false;
		if (nodeId == null) {
			return;
		}
		TreeNode node = (TreeNode) nodes.get(nodeId);
		if (node == null) {
			return;
		}
		node.setSelected(true);
		this.selected = node;
	}

	public TreeNode getSelected() {
		if (selected != null) {
			return selected;
		} else {
			return null;
		}
	}

	/**
	 * 使节点nodeId处于激活状态
	 * 
	 * @param nodeId
	 *            String
	 */
	public void enable(String nodeId) {
		this.calculated = false;
		TreeNode node = (TreeNode) nodes.get(nodeId);
		if (node != null) {
			node.setEnabled(true);
		}

	}

	/**
	 * 删除节点，同时删除所有的子节点
	 * 
	 * @param nodeID
	 *            String
	 */
	public void delete(String nodeID) {
		this.calculated = false;
		if (this.nodes.get(nodeID) == null)
			return;
		Vector<String> children = new Vector<String>();
		this.getChildren(nodeID, children);
		Enumeration<String> enu = children.elements();
		while (enu.hasMoreElements())
			this.nodes.remove(enu.nextElement());
		this.nodes.remove(nodeID);
	}

	private void getChildren(String ID, Vector<String> children) {
		for (TreeNode node : this.nodes.values()) {
			if ((node != null) && (node.getParentId() != null)
					&& (node.getParentId().equals(ID))) {
				children.add(node.getId());
				getChildren(node.getId(), children);
			}
		}
	}

	/**
	 * 将所有节点置于disabled状态
	 */
	public void diableAll() {
		this.calculated = false;
		for (TreeNode node : this.nodes.values()) {
			node.setEnabled(false);
		}
	}

	/**
	 * 将节点nodeId置于disable的状态
	 * 
	 * @param nodeId
	 *            String
	 */
	public void disable(String nodeId) {
		this.calculated = false;
		TreeNode node = (TreeNode) nodes.get(nodeId);
		if (node == null) {
			throw new IllegalArgumentException("No node found for id:" + nodeId
					+ ".");
		}
		node.setEnabled(false);
	}

	/**
	 * 添加节点
	 * 
	 * @param nodeId
	 *            String 节点ID
	 * @param parentId
	 *            String 父节点
	 * @param enabled
	 *            boolean 节点是否可用，即在显示树的时候，这个节点是否是激活的。
	 * @param selected
	 *            boolean 节点是否被选中
	 * @param value
	 *            Object 节点对应的值
	 */
	public void addNode(String nodeId, String parentId, boolean enabled,
			boolean selected, Object value) {
		this.calculated = false;
		TreeNode node = new TreeNode();
		node.setEnabled(enabled);
		node.setId(nodeId);
		node.setNode(value);
		node.setParentId(parentId);
		node.setSelected(selected);
		if (selected) {
			this.selected = node;
		}
		nodes.put(nodeId, node);
	}

	/**
	 * 计算子节点的个数
	 */
	private void calcChildNum() {
		for (TreeNode node : this.nodes.values()) {
			int count = 0;
			for (TreeNode child : this.nodes.values()) {
				if ((child.getParentId() != null)
						&& child.getParentId().equals(node.getId()))
					count++;
			}
			node.setChildNum(count);
		}
	}

	/**
	 * private void calcChild() { for (Iterator iterator =
	 * this.index.values().iterator(); iterator.hasNext(); ) { TreeNode node =
	 * (TreeNode) iterator.next(); TreeNode parent = (TreeNode)
	 * index.get(node.getParentId()); if (parent != null && node.isEnabled()) {
	 * parent.setChildNum(parent.getChildNum() + 1); } if (node.isEnabled()) {
	 * node.setReserved(true); while (parent != null) {
	 * parent.setReserved(true); parent = (TreeNode)
	 * index.get(parent.getParentId()); } } if (parent == null) {
	 * node.setReserved(true); } } //calc those grandfather node for (int i = 0;
	 * i < nodes.size(); i++) { TreeNode node = (TreeNode) nodes.get(i);
	 * TreeNode parent = (TreeNode) index.get(node.getParentId()); if (parent !=
	 * null && node.isReserved() && parent.getChildNum() == 0) {
	 * parent.setChildNum(parent.getChildNum() + 1); } } }
	 */
	/**
	 * 重新计算节点选中属性
	 */
	private void calcSelected() {
		if (selected == null) {
			if (this.nodes.size() > 0) {
				selected = (TreeNode) this.nodes.values().iterator().next();
				selected.setSelected(true);
			}
		}
	}

	/**
	 * 计算Open属性，缺省的，被选中的节点是open的。被选中的节点的父节点也是open的。
	 */
	private void calcOpen() {

		selected.setOpen(true);
		selected.setVisible(true);
		TreeNode parent = (TreeNode) nodes.get(selected.getParentId());
		List<String> calced= new Vector<String>(); //防止死循环
		while ((parent != null)&&(!calced.contains(parent.getId()))) {
			calced.add(parent.getId());
			parent.setOpen(true);
			parent = (TreeNode) nodes.get(parent.getParentId());
		}
	}

	/**
	 * 获取最大深度，假设深度已经计算了
	 * 
	 * @return int
	 */
	public int getMaxDepth() {
		int depth = 0;
		for (TreeNode node : this.nodes.values()) {
			int max = node.getDepth();
			if (depth < max) {
				depth = max;
			}
		}
		return depth;
	}

	/**
	 * 计算各个节点的深度，跟节点深度为 0,子节点的深度=父节点的深度+1
	 */
	private void calcDepth() {
		for (TreeNode node : this.nodes.values()) {
			TreeNode parent = (TreeNode) nodes.get(node.getParentId());
			int depth = 0;
			List<String> parents= new Vector<String>();
			while ((parent != null)&&(!parents.contains(parent.getId()))) {
				depth++;
				parents.add(parent.getId());
				parent = (TreeNode) nodes.get(parent.getParentId());
			}
			node.setDepth(depth);
		}
	}

	/**
	 * 计算节点是否可见。一个节点是可见的，指它在树状表示中并不被折叠隐藏起来。如果这个节点是打开的，或者他的父节点是打开的，则这个节点是可见的。
	 */
	private void calcVisible() {
		for (TreeNode node : this.nodes.values()) {
			TreeNode parent = (TreeNode) nodes.get(node.getParentId());
			boolean open = true;
			List<String> parents= new Vector<String>();
			while ((parent != null)&&(!parents.contains(parent.getId()))) {
				if (!parent.isOpen()) {
					open = false;
				}
				parents.add(parent.getId());
				parent = (TreeNode) nodes.get(parent.getParentId());
			}
			node.setVisible(open);
		}
	}

	/**
	 * 如果有结点是enabled的，则其所有父节点是保留的。
	 */
	private void calcReserved() {
		for (TreeNode node : this.nodes.values()) {
			if (node.isEnabled()) {
				node.setReserved(true);
				TreeNode parent = (TreeNode) nodes.get(node.getParentId());
				List<String> parents= new Vector<String>();
				while ((parent != null)&&(!parents.contains(parent.getId()))) {
					parent.setReserved(true);
					parents.add(parent.getId());
					parent = (TreeNode) nodes.get(parent.getParentId());
				}
			}
		}
	}

	/**
	 * 获取所有 节点
	 * 
	 * @return List <TreeNode>
	 */
	public List<TreeNode> getNodes() {

		return this.getNodes(true);
	}

	/**
	 * 获取所有 节点
	 * 
	 * @param reserved
	 *            仅获取所有reserved的节点，或者全部节点
	 * @return List <TreeNode>
	 */
	public List<TreeNode> getNodes(boolean reserved) {
		if ((this.nodes.size() > 0) && (!this.calculated)) {
			this.calcChildNum();
			// filterNodes();
			calcSelected();
			calcOpen();
			calcDepth();
			calcVisible();
			calcReserved();
			this.calculated = true;
		}
		if (reserved) {
			Vector<TreeNode> nodes = new Vector<TreeNode>();
			for (TreeNode node : this.nodes.values()) {
				if (node.isReserved())
					nodes.add(node);
			}
			return nodes;
		} else
			return new Vector<TreeNode>(this.nodes.values());
	}

}
