/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * LoggableConnection
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-9-13
 */
public class LoggableConnection extends ConnectionWrapper {
	private StringBuffer log;
	public LoggableConnection(Connection connection){
		super(connection);
		log = new StringBuffer("");
	}
	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		log.append(sql+".");
		return new LoggablePreparedStatement(this, connection.prepareStatement(sql));
	}
	
	public void log(String msg){
		log.append(msg);
	}
	
	public String toString(){
		return this.log.toString();
	}
}
