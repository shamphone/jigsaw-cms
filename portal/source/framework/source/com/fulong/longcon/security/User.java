package com.fulong.longcon.security;

import java.util.Date;

import com.fulong.longcon.repository.Node;

/**
 * <p>Title: Longcon Passport 2.0</p>
 *
 * <p>Description: Longcon Passport core System</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author JiangQi
 * @author lixf
 * @version 1.0
 */
public interface User extends PassportIdentity, Node {

    /**
     * 获取当前会员用户名
     * @return String
     */
    public String getUsername();

    /**
     * 设置真实姓名
     * @param name String
     */
    public void setCommonname(String name);

    /**
     * 所在地区
     *
     * @return String
     */
    public Node getArea();

    /**
     * 设置所在地区
     * @param region Region
     */
    public void setArea(Node region);

    /**
     * 更新用户密码
     * @param newPassword 新密码
     */
    public void changePassword(String newPassword);

    /**
     * 检查密码
     * @param password String
     * @return boolean 密码正确，返回true；否则为false;
     */
    public boolean checkPassword(String password);

    /**
     * 检查密码提示问题和答案
     * @param question String
     * @param answer String
     * @return boolean
     */
    public boolean checkQuestion(String question, String answer);

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

    /**
     *
     * @return String
     */
    public String getPassword();

}
