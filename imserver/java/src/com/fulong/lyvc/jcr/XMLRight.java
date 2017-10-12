package com.fulong.lyvc.jcr;

import com.fulong.lyvc.Right;

/**
 * MessageStore
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class XMLRight implements Right{

	private String id;
	private String name;
	private String desc;
	
	/**
	 * 获取唯一标识
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 获取用户角色的权限名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置唯一标识
	 * 
	 * @return
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 设置用户角色的权限名称
	 * 
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取用户角色的权限描述
	 * 
	 * @return
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * 设置用户角色的权限描述
	 * 
	 * @return
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
