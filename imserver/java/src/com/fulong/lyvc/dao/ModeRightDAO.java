/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

/**
 * ConferenceModeRightDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-17
 */
public interface ModeRightDAO extends DAO {

	/**
	 * 权限增加
	 * 
	 */
	public void insert(long roleId, int rightID) throws SQLException;

	/**
	 * 角色权限查看
	 * 
	 * @return ConferenceMemberRight[]
	 */
	public int[] findByRole(long roleId) throws SQLException;

	/**
	 * 权限删除
	 * 
	 * @return long
	 */
	public void delete(long roleId, int rightID) throws SQLException;

	/**
	 * 删除所有权限
	 * 
	 * @return void
	 */
	public void deleteByRole(long roleId) throws SQLException;

}
