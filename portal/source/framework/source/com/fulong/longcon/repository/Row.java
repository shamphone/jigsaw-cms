package com.fulong.longcon.repository;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public interface Row {
    /**
     * 获取这个行对应的节点
     * @return Node
     */
    public Node getNode();
    /**
     * 获取指定的属性值
     * @param name String
     * @return Value
     */
    public Value getValue(String name);

    /**
     * 获取所有的属性，数组的顺序和RowIterator.getColumnNames()保持一致
     * @return Value[]
     */
    public Value[] getValues();
}
