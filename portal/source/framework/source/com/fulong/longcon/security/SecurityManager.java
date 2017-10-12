package com.fulong.longcon.security;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Iterator;

import com.fulong.longcon.ItemExistsException;


/**
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
public interface SecurityManager {
    /**
     * 根据ID来获取机构、用户或者组。
     * @param ID String
     * @return PassportIdentity
     * @throws SQLException 
     */
    public PassportIdentity getPrincipal(String ID) throws SQLException;

    /**
     * 设置
     * @return Organization
     */
    public Organization getDefaultOrganization();

    /**
     * 根据ID或者Username查找用户
     * @param ID String　ID或者Username
     * @return User
     * @throws SQLException 
     */
    public User getUser(String ID) throws SQLException;

    /**
     * 创建会员
     * @param username 用户名
     * @param password 密码
     * @return User
     * @throws ItemExistsException the user with this username already exists.
     * @throws SQLException 
     */
    public User createUser(String username, String password) throws
        ItemExistsException, SQLException;

    /**
     * 创建机构
     * @param enterpriseName String
     * @param creator User
     * @return Organization
     */
    public Organization createOrganization(String enterpriseName,
                                           Principal creator);

    /**
     * 根据Id返回一个机构
     * @param ID String orgID 机构Id
     * @return Organization
     */
    public Organization getOrganization(String ID);

    /**
     * 用户当前帐号所关联的机构。
     * 缺省的，如果这个用户是某个机构的创建者，也就是管理员，则返回这个机构。
     * 如果用户是多个机构的管理员，则返回其中任意一个。系统应该禁止这种帐户出现。
     * @param admin Principal
     * @return OrganizationIterator
     */
    public Organization getOrganization(Principal admin);

  

    /**
     * 根据组id获得指定的组，系统预定义的组ID是Group中定义的常量
     * @param id String 组的全局ID。
     * @return Group
     */
    public Group getGroup(String id);

    /**
     * 获取用户/机构所在的所有组
     * @param principal Principal
     * @return GroupIterator
     */
    public Iterator<?> getAffiliatedGroups(Principal principal);

    /**
     * 获取全系统的根组。
     * @return Group
     */
    public Group getRootGroup();

    /**
     * 删除
     * @param group Group
     */
    public void delete(Principal group);


}
