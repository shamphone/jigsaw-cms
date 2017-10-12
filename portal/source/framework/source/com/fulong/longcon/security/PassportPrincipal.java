package com.fulong.longcon.security;

import java.util.Date;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public interface PassportPrincipal extends
        User, Organization {
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
