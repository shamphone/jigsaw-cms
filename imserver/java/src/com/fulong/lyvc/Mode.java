package com.fulong.lyvc;

import java.util.Collection;

/**
 * 
 * ConferenceModel
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2009-3-11
 */

public interface Mode {
	/**
	 * 唯一标识
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 获取会议模式名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取会议模式描述
	 * 
	 * @return
	 */
	public String getDesc();

	/**
	 * 获取该会议模式中的所有角色
	 * 
	 * @return
	 */
	public Collection<ModeRole> getRoles();

	/**
	 * 通过角色id获取该角色
	 * @param roleID
	 * @return
	 */
	public ModeRole getRole(String roleID);
}
