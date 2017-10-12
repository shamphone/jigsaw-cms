/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.lyvc.dao.BaseDAO;

/**
 * PostgresqlDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class PostgresqlDAO extends BaseDAO {
	protected long getPrimaryKey() throws SQLException {
		ResultSet rs;
		String sql;
		PreparedStatement ps;

		sql = " select nextval('primarykey_sequence')";
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		rs.next();
		long id = rs.getLong(1);
		rs.close();
		ps.close();
		return id;
	}

	protected long queryLong(PreparedStatement statement) throws SQLException {
		long result = 0;
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			result = rs.getLong(1);
		}
		rs.close();
		statement.close();
		return result;

	}

	protected long[] queryLongArray(PreparedStatement statement)
			throws SQLException {
		long[] result = new long[1024];
		int size = 0;
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			result[size++] = rs.getLong(1);
		}
		rs.close();
		statement.close();
		long[] trim = new long[size];
		System.arraycopy(result, 0, trim, 0, size);
		return trim;

	}

	protected int queryInt(PreparedStatement statement) throws SQLException {
		int result = 0;
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			result = rs.getInt(1);
		}
		rs.close();
		statement.close();
		return result;

	}

	protected int[] queryIntArray(PreparedStatement statement)
			throws SQLException {
		int[] result = new int[2048];
		int size = 0;
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			result[size++] = rs.getInt(1);
		}
		rs.close();
		statement.close();
		int[] trim = new int[size];
		System.arraycopy(result, 0, trim, 0, size);
		return trim;

	}
}
