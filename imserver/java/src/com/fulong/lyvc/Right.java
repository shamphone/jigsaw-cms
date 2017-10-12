package com.fulong.lyvc;

/**
 * MessageNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public interface Right {
	/**
	 * 唯一标识
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 获取用户角色的权限名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取用户角色的权限描述
	 * 
	 * @return
	 */
	public String getDesc();
}
