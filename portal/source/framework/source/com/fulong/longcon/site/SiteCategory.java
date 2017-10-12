package com.fulong.longcon.site;

import com.fulong.longcon.security.Group;

/**
 * 网站类别，为了方便对网站和网站模版的管理，显示模型使用网站类别来对他们作分类。但是类别本身仅仅是分类，并不包含授权信息。授权将在显示层完成。
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public interface SiteCategory  extends SiteObject{

    public static final String SITE_CATEGORY_SCHEME="site-category-scheme";
    /**
     * 网站类别名称
     * @return String
     */
    public String getName();

    /**
     * 类别显示名字
     * @return String
     */
    public String getDisplayName();

    /**
     *状态
     * @param state String
     */
    public void setState(String state);
    /**
     * 修改显示名称
     * @param name String
     */
    public void setDisplayName(String name);

    /**
     * 获取对本类型的描述
     * @return String
     */
    public String getDescription();

    /**
     * 修改类别描述
     * @param description String
     */
    public void setDescription(String description);

    /**
     * 获得该分类下的模板的数量
     * @return int
     */
    public int getTemplateCount();

    /**
     * 获得该分类下的站点的数量
     * @return int
     */
    public int getSiteCount();

    /**
     * 获得该分类的所在的分组
     * @return Group
     */
    public Group getGroup();

    /**
     * 设置该分类的所在的分组
     * @param group Group
     */
    public void setGroup(Group group);
}
