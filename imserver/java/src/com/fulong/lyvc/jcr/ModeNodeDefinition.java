package com.fulong.lyvc.jcr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.ModeRole;

/**
 * ModeNodeDefinition
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class ModeNodeDefinition implements Mode {

	private NodeDefinition definition;
	
	public ModeNodeDefinition(NodeDefinition definition) {
		this.definition = definition;
	}
	
	/**
	 * 该方法用来获取会议模式的描述
	 *
	 */
	public String getDesc() {
		return definition.getDescription(); 
	}

	/**
	 * 该方法用来获取会议模式的id
	 *
	 */
	public String getId() {
		String modeId = definition.getID();
		modeId = modeId.substring(modeId.length()-1);
		
		return modeId;
	}

	/**
	 * 该方法用来获取会议模式的名称
	 *
	 */
	public String getName() {
		return definition.getName();
	}

	/**
	 * 该方法用来获取某种会议模式的所有用户角色（内置的）
	 *
	 */
	public Collection<ModeRole> getRoles() {
		//TODO
		Collection<ModeRole> roles = new ArrayList<ModeRole>();
		
		//得到除继承以外的属性集合（只包含自己这一级定义的属性）
		Iterator<PropertyDefinition> iterator = definition.getDeclaredPropertyDefinitions();
		while(iterator.hasNext()) {
			PropertyDefinition temp = iterator.next();
			//获取引用类型的属性
			if(temp.getType() == 9) {
				ModeRolePropertyDefinition role = new ModeRolePropertyDefinition(temp);
				roles.add(role);
			}
		}
		
		return roles;
	}

	/**
	 * 该方法用来通过用户的角色id获取其角色
	 *
	 */
	public ModeRole getRole(String roleID) {
		ModeRole role = null;
		
		Collection<ModeRole> roles = this.getRoles();
		Iterator<ModeRole> iterator = roles.iterator();
		while(iterator.hasNext()) {
			ModeRole temp = iterator.next();
			if(temp.getId().equals(roleID)) {
				role = temp;
				break;
			}
		}
		
		return role;
	}
}
