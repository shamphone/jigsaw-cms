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
public interface Module {
    /**
     * 模块名称
     * @return String
     */
    public String getID();
    /**
     * 显示名称。
     * @return String
     */
    public String getName();
    /**
     * 模块相对地址
     * @return String
     */
    public String getURL();

    /**
     * 可以访问这个模块的角色
     * @return String[]
     */
    public String[] getUserRoles();

    /**
     * 是否激活
     * @return boolean
     */
    public boolean isActive();
}
