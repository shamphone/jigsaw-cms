package com.fulong.longcon.repository;

/**
 * 根据JCR标准,设计ChildNodeDefinition用来表示复合属性.
 * 与PropertyDefinition相补充
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishabo
 * @version 2.0
 */
public interface ChildNodeDefinition
    extends PropertyDefinition {

    /**
     * 返回当前复合属性的type所对应的NodeDefinition
     * @return NodeDefinition
     */
    public NodeDefinition getNodeDefinition();

}
