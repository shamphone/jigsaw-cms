package com.fulong.longcon.repository;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public interface Item {
    /**
         * 获取唯一标识符
         * @return String
         */
      public String getID();

    /**
     * 获取对其所在的内容库的引用
     * @return ContentRepository
     */
    public Repository getRepository();
    /**
     * 名称，指在父节点下的名称
     * @return String
     */
    public String getName();

    /**
     * 所在的父结点
     * @return Node
     */
    public Node getParent();
    /**
     * 返回节点或者属性的深度，即从根节点到当前节点/属性的距离
     * <ul>
     * <li>根节点深度为 0 .
     * <li>节点/属性的深度为其父节点的深度+1.
     * </ul>
     *
     * @return 节点或者属性的深度，即从根节点到当前节点/属性的距离.
     */
    public int getDepth();
    /**
     * 获取指定深度的父节点
     * @param depth int 0<=depth<= getDepth()，0为根节点，以此类推
     * @return Item
     */
    public Item getAncestor(int depth);
    /**
     * 获取节点路径
     * 节点路径为：
     *  根节点的路径为/
     *  当前节点路径=父节点路径+/+ 本节点的name + [当前节点的OrderNo]
     *  如果当前节点在父节点下name是唯一的，则 [当前节点的OrderNo]在路径上可以省略     *
     * @return String 类似 /a/b/c[1]/d/e[10]的路径
     */
    public String getPath();
}
