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
public interface QueryManager {
    /**
     * 创建一个查询构造器
     * @return ContentQuery
     */
    public Query createQuery(NodeDefinition definition,String language);
    
    
    /**
     * 注册查询构造器
     * @param language
     * @param creator
     */
    public void register(String language, Object creator);
    /**
     * 系统支持的查询语言
     * @return
     */
    public String[] getSupportedQueryLanguages();
}
