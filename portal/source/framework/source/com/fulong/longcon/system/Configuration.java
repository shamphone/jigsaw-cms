package com.fulong.longcon.system;

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
public interface Configuration {
    /**
     * 全局属性
     * @param name String
     * @return String
     */
    public String getProperty(String name);
    /**
     * 获取已激活的模块列表
     * @param name String
     * @return Module
     */
    public Module[] getActiveModules(String position);

    /**
     * 根据Id来获取模块
     * @param id String
     * @return Module
     */
    public Module getModule(String id);

}