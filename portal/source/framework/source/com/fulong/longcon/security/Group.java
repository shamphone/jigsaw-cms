package com.fulong.longcon.security;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Iterator;

import com.fulong.common.Duration;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * 是会员的集合。组从属于某个机构。参者根据需要可以在机构中建立各种组，例如部门、客户组、联系人组、负责人组、经销商组、供应商组….。同一类组之间可以有嵌套关系。其他系统也可以使用组来实现对授权的管理。
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
public interface Group extends PassportIdentity, java.security.acl.Group {

    /**
     * 用户根组
     */
    public static final String ROOT="principal-scheme";

    /**
     * 机构根组
     */
    public static final String ORG_ROOT="org-scheme";
    
    /**
     * 部门
     */
    public static final String DEPARTMENT = "dept-scheme";
    
    /**
     * 客户组
     */
    public static final String CLIENT = "client";
    
    /**
     * 个人客户组
     */
    public static final String USERCLIENT = "userclient";

    /**
     * 组类型
     * @return String
     */
    public String getGroupType();

    /**
     * 组成员类型，为
     * USER(1): 用户
     * ORGANIZATION(3): 机构
     * @return int
     */
    public int getMemberType();

    /**
     * 获取父组
     * 如果当其组为根组则返回null
     * @return 组
     */
    public Group getParentGroup();

    /**
     * 获取子组。
     * @param name String 组的ID。
     * @return Group
     */
    public Group getGroup(String ID);

    /**
     * 获取直接的子组,不包括下级的子组
     * @return GroupIterator
     */
    public GroupIterator children();

    /**
     * 判断当前组是否为参数组的子组,包括下级的子组
     *
     * @param parent Group
     * @return boolean
     */
    public boolean isChild(Group parent);

    /**
     * 获取当前组默认享受服务时间
     * @return Duration
     */
    public Duration getDefaultPeriod();


    /**
     * 组默认享受服务时间 单位: 年(y)，月(m)，日(d)
     * @param month int
     */
    public void setDefaultPeriod(int count, String field);


    /**
     * 获取当前组简介
     * @return 组简介
     */
    public String getDescription();


    /**
     * 获取正式成员总数,包括子组的成员
     * @return 成员数量
     * @throws SQLException 
     */
    public int getAllMemberCount() throws SQLException;

    /**
     * 修改组名
     * @param name String
     */
    public void setCommonname(String name);

    /**
     * 设置描述
     * @param description String
     */
    public void setDescription(String description);

    /**
     * 修改父组
     * @param parent Group
     */
    public void setParentGroup(Group parent);


    /**
     * 清除所有成员
     * @return boolean
     */
    public void clearMember();

    /**
     * 默认积分
     * @return int
     */
    public int getDefaulPoints();

    /**
     * 设置默认积分
     * @param point int
     */
    public void setDefaultPoints(int point);

    /**
     *是否是根组
     * @return boolean
     */
    public boolean isRoot();


    /**
     * 获取用户加入的子组
     * @param principal Principal
     * @return Iterator
     */
     public Iterator<? extends Group> getAffiliatedGroups(Principal principal);


    /**
     *该组所封装的节点定义
     * @return NodeDefinition
     */
    public NodeDefinition getNodeDefinition();
}
