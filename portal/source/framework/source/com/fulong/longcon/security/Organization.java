package com.fulong.longcon.security;

import java.util.Date;

import com.fulong.longcon.repository.Node;

/**
 *
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
public interface Organization extends PassportIdentity, Node {
//    public static final String ORG_SCHEME = "org-scheme";
    /**
     * 管理员,即创建者
     * @return User
     */
    public User getAdministrator();

    /**
     * 设置组管理员,只能有一个管理员
     * @param admin User
     */
    public void setAdministrator(User admin);

    /**
     * 获取机构名称
     * @return String
     */
    public String getEnterpriseName();

    /**
     * 修改机构名称
     * @param name String
     */
    public void setEnterpriseName(String name);

    /**
     * 最后修改事件
     * @param lastModifiedDate Date
     */
    public void setLastModifiedDate(Date lastModifiedDate);

    /**
     * 获取机构简介,等同于getAttribute("description");
     * @return 简介
     */
    public String getDescription();

    /**
     * 设置机构简介,等同于setAttribute("description",description);
     * description字符串长度和底层的数据库相关，一般不能超过2000个（oracle）。
     * 如果需要更长的描述，请使用businessDescription;
     * @param description String
     */
    public void setDescription(String description);

    /**
     * 机构业务介绍
     * @return String
     */
    public String getBusinessDescription();

    /**
     * 设置机构业务介绍，和description属性相比，这个属性能够接受高达1G的数据量。
     * @param description String
     */
    public void setBusinessDescription(String description);

    /**
     * 获取机构所在地区
     * @return Region
     */
    public Node getArea();

    /**
     * 设置机构所在地区
     * @param region Region
     */
    public void setArea(Node region);

    /**
     * 获取机构的行业分类.
     * @return Siccode[]
     */
    public Node[] getSiccodes();

    /**
     * 设置机构的行业分类
     * @param codes Siccode[]
     */
    public void setSiccodes(Node[] codes);

    /**
     * 获取用户积分
     * @return int
     */
    public int getPoints();

    /**
     * 添加积分
     * @param points int
     */
    public void addPoints(int points);

    /**
     * 设置积分
     * @param points int
     */
    public void setPoints(int points);

    /**
     * 身份的失效日期
     * @param expiringDate Date
     */
    public void setExpiringDate(Date expiringDate);

    /**
     * 身份的失效日期
     * @return Date
     */
    public Date getExpiringDate();

    /**
     * 注册日期
     * @return Date
     */
    public Date getRegisterDate();

    /**
     * 最后修改日期
     * @return Date
     */
    public Date getLastModifiedDate();

}
