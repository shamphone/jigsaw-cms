package com.fulong.longcon.security;

import java.io.Serializable;
import java.security.Principal;

/**
 *
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author Lixf
 *
 * @version 2.0
 */
public interface PassportIdentity extends Principal, Serializable {

    //管理用户
    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    /**
     *
     */
    public static final int EMPTY = 0;
    /**
     * 个人用户
     */
    public static final int USER = 1;
    /**
     * 组
     */
    public static final int GROUP = 2;
    /**
     * 机构
     */
    public static final int ORGANIZATION = 3;
    /**
     * 可显示的名称
     * @return String
     */
    public String getCommonname();

    /**
     * 唯一标识
     * @return String
     */
    public String getID();

    /**
     * 类型，参见本类中定义的常量
     * @return String
     */
    public int getType();

    /**
     *
     * @return boolean
     */
    public boolean isUser();

    /**
     *
     * @return boolean
     */
    public boolean isOrganization();
}
