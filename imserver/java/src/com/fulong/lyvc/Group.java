/**
 * 
 */
package com.fulong.lyvc;

import java.security.Principal;
import java.util.Collection;

/**
 * 
 * Group
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-8-24
 */

public interface Group extends java.security.acl.Group {
	/**
	 * 获取唯一标识符
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 获取组名
	 */
	public String getName();

	/**
	 * 获取组的描述
	 * 
	 * @return
	 */
	public String getDesc();

	/**
	 * 获取组的创建者
	 * 
	 * @return
	 */
	public User getCreator();

	/**
	 * 获取组的管理员
	 * 
	 * @return
	 */
	public User getManager();

	/**
	 * 设置组名
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 设置组的描述
	 * 
	 * @param desc
	 */
	public void setDesc(String desc);
	
	/**
	 * 获取组的父组
	 * @return
	 */
	public Group getParentGroup();

	/**
	 * 该组是否能添加成员
	 * 
	 * @param principal
	 * @return
	 */
	public boolean canAddMember(Principal principal);
	
	/**
	 * 添加子组
	 * @param name
	 * @param desc
	 * @param creatorId
	 * @return
	 */
	public Group addChild(String name, String desc,	String creatorId);
	
	/**
	 * 获取该组下的成员（第一层的）
	 * @return
	 */
	public Collection<? extends User> users();
	
	/**
	 * 获取该组的子组（第一层的）
	 * @return
	 */
	public Collection<? extends Group> children();
	
	/**
	 * 设置组的父组
	 * @param parentGroup
	 */
	public void setParentGroup(Group parentGroup);
}
