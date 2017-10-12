package com.fulong.longcon.repository;

import java.util.Iterator;
import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.common.util.TreeNode;
import java.util.Vector;


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
 * @author lishaobo
 * @version 2.0
 */
public class PropertyDefinitionTreeBuilder {
    private NodeDefinition root;
    private String selectedId;
    private Vector<Integer> disable;

    /**
     *
     * @param root ContentCategory
     */
    public PropertyDefinitionTreeBuilder(NodeDefinition root) {
        this.root = root;
        this.disable = new Vector<Integer>();
    }

    /**
     *
     * @param groupId String
     */
    public void setSelectedId(String groupId) {
        this.selectedId = groupId;
    }

    /**
     * 添加过滤条件，树中类型为type的节点不能使用
     * @param type int
     */
    public void addDisabledType(int type){
        this.disable.add(type);
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
        //然后根据配额人，将其有权限的node再恢复enable
        tree.select(selectedId);
        Iterator<TreeNode> iterator = tree.getNodes(false).iterator();
        int[] types = new int[this.disable.size()];
        for(int i=0;i<types.length;i++){
            types[i]= ((Integer)this.disable.elementAt(i)).intValue();
        }

        while (iterator.hasNext()) {
            TreeNode treeNode = (TreeNode) iterator.next();
            //根节点不是PropertyDefinition，而是NodeDefinition,而且，我们不需要处理根节点。
            if(!treeNode.getId().equals(this.root.getID())){
                PropertyDefinition def = (PropertyDefinition) treeNode.getNode();

                if (this.inOrNot(def, types)) {
                    tree.disable(treeNode.getId());
                    this.disableChild(tree,types,treeNode.getId());
                    treeNode.setOpen(false);
                    treeNode.setReserved(false);
                }
            }
        }
        tree.select(selectedId);
        return tree;
    }

    /**
     * 将节点的部分子节点设置为disable
     * @param nodeId String
     */
    private void disableChild(Tree t,int[] types,String nodeId){
        Iterator<TreeNode> it=t.getNodes(false).iterator();
        while(it.hasNext()){
            TreeNode node=(TreeNode)it.next();
            if(nodeId==node.getParentId()){
                if (node == null) {
                    throw new IllegalArgumentException("No node found for id:" + nodeId +
                                       ".");
                           }

                if(this.inOrNot((PropertyDefinition)node.getNode(),types)){
                    node.setEnabled(false);
                    node.setReserved(false);
                    this.disableChild(t,types,node.getId());
                }
            }
        }
    }


   private boolean inOrNot(PropertyDefinition def, int[] types){
       for(int i=0;i<types.length;i++){
           if(def.getType()==types[i])
               return true;
       }
       return false;
   }


    /**
     * 在组织结构树tree的基础上，添加以root为根的树节点。
     * 根据hasMembers参数决定是否添加root中各个节点的成员。
     * @param tree Tree
     * @param root Group
     * @param hasMembers boolean
     */
    private void addTreeChildren(Tree tree, NodeDefinition root) {
        Iterator<PropertyDefinition> all = root.propertyDefinitions();
        while (all.hasNext()) {
            PropertyDefinition child = (PropertyDefinition) all.next();
            if ( (child.getType() != PropertyType.REFERENCE) &&
                (child.getType() != PropertyType.FIX))
                tree.addNode(child.getID(), root.getID(), child);
        }
        Iterator<PropertyDefinition> refchildren = root.propertyDefinitions(PropertyType.REFERENCE);
        Iterator<PropertyDefinition> fixchildren = root.propertyDefinitions(PropertyType.FIX);

        while (refchildren.hasNext()) {
            PropertyDefinition child = (PropertyDefinition) refchildren.next();
            tree.addNode(child.getID(), root.getID(), child);
            addRefChildren(tree, child);
        }
        while (fixchildren.hasNext()) {
            ChildNodeDefinition child = (ChildNodeDefinition) fixchildren.next();
            tree.addNode(child.getID(), root.getID(), child);
            addFixChildren(tree, child);
        }
    }

    private void addRefChildren(Tree tree, PropertyDefinition root) {
        if(root.getReferenceDefinition()==null)
            return;
        Iterator<PropertyDefinition> all = root.getReferenceDefinition().propertyDefinitions();
        while (all.hasNext()) {
            PropertyDefinition child = (PropertyDefinition) all.next();
            tree.addNode(root.getID()+"."+child.getID(), root.getID(), child);
        }
    }


    private void addFixChildren(Tree tree, ChildNodeDefinition root) {
        if(root.getNodeDefinition()==null)
            return;
        Iterator<PropertyDefinition> all = root.getNodeDefinition().propertyDefinitions();
        while (all.hasNext()) {
            PropertyDefinition child = (PropertyDefinition) all.next();
            tree.addNode(root.getID()+"."+child.getID(), root.getID(), child);
        }
    }


}

