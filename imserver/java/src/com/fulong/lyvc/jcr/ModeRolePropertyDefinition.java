package com.fulong.lyvc.jcr;

import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.lyvc.ModeRole;

/**
 * ModeRolePropertyDefinition
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class ModeRolePropertyDefinition implements ModeRole {

	private PropertyDefinition property;

	public ModeRolePropertyDefinition(PropertyDefinition property) {
		this.property = property;
	}

	/**
	 * 该方法用来获取用户角色的属性定义
	 *
	 */
	public PropertyDefinition getProperty() {
		return property;
	}
	
	/**
	 * 该方法用来获取用户角色的属性描述
	 *
	 */
	public String getDesc() {
		return property.getDescription();
	}

	/**
	 * 该方法用来获取用户角色的属性的id
	 *
	 */
	public String getId() {
		return property.getID();
	}

	/**
	 * 该方法用来获取用户角色的属性的名称
	 *
	 */
	public String getName() {
		return property.getName();
	}

	/**
	 * 该方法用来获取用户角色的所有权限
	 *
	 */
	public int[] getRights() {
		//TODO
		String[] temps = property.getValueConstraints();
		
		int[] rights = new int[temps.length];
		for(int i=0; i<rights.length; i++)
			rights[i] = Integer.parseInt(temps[i]);
		
		return rights;
	}

	/**
	 * 该方法用来判断该用户角色是否是默认的（内置的，不能删除的）
	 *
	 */
	public boolean isDefault() {
		return property.isProtected();
	}
}
