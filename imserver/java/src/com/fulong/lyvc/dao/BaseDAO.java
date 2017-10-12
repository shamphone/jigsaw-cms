/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.Connection;

/**
 * BaseDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-17
 */
public class BaseDAO implements DAO{
	protected Connection connection;
	
	public void setConnection(Connection connection) {
		this.connection=connection;
	}

}
