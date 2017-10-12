package com.fulong.lyvc;

/**
 * 
 * ConferenceModelRole
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2009-3-11
 */
public interface ModeRole {
	/**
	 * 角色ID
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 是否缺省角色
	 * 
	 * @return
	 */
	public boolean isDefault();

	/**
	 * 角色描述
	 * 
	 * @return
	 */
	public String getDesc();

	/**
	 * 获取所有权限
	 * 
	 * @return
	 */
	public int[] getRights();
}
