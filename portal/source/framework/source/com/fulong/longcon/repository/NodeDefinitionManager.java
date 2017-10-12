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
public interface NodeDefinitionManager {
    /**
     * 获取所有的属性定义
     * @return NodeDefinitionIterator
     */
    public NodeDefinitionIterator definitions();

    /**
     * 获取指定ID的内容库
     * @param pkid String
     * @return NodeDefinition
     */
    public NodeDefinition getDefinition(String pkid);

    /**
     * 创建一个空的内容库，并将创建成功的内容库加载到本仓库中。
     * @param name String
     * @return NodeDefinition
     */
    public NodeDefinition createDefinition(String name);

    /**
     * 创建节点定义。它继承自superDef，父节点为内容库根节点，标识符为ID
     * @param ID String
     * @param parent NodeDefinition
     * @return NodeDefinition
     */
    public NodeDefinition createDefinition(String name, NodeDefinition superDef);
    /* 
     * 
     * @param id 标识符，不能和现有ID重复
     * @param name 显示名称 
     * @param superDef 父分类
     * @return 新创建的节点定义
     * @author lixf
     * @lastupdate 2009-10-20上午11:30:50
     */
    public NodeDefinition createDefinition(String id, String name, NodeDefinition superDef);
    /**
     *
     * @param superDef NodeDefinition
     * @return NodeDefinition
     */
    public NodeDefinition createDefinition(NodeDefinition superDef);

    /**
     * 复制一个大纲定义，深度复制其中的属性定义
     * @param ID String
     * @param source NodeDefinition
     * @return NodeDefinition
     */
    public NodeDefinition copyDefinition(String name, NodeDefinition source);

    /**
     *
     * @param name String
     * @param source NodeDefinition
     * @param recursive boolean
     * @return NodeDefinition
     */
    public NodeDefinition copyDefinition(String name, NodeDefinition source, boolean recursive);

    /**
     *
     * @param name String
     * @param source NodeDefinition
     * @param recursive boolean
     * @param destParent NodeDefinition
     * @return NodeDefinition
     */
    public NodeDefinition copyDefinition(String name, NodeDefinition source, boolean recursive, NodeDefinition destParent);
    /**
     * 删除结点定义，同时删除这个定义的所有相关内容
     * @param definition String
     */
    public boolean delete(NodeDefinition definition);

}
