package com.fulong.longcon.site.dao.mysql;

import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.DatabaseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.longcon.site.dao.SiteDao;
import com.fulong.longcon.site.data.SiteData;
import com.fulong.common.dao.SQLParameter;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import com.fulong.common.dao.JdbcException;
import javax.sql.RowSet;
import java.util.Date;

/**
 * 
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author ljy
 * @version 2.0
 */
public class MysqlSiteDao extends JdbcDao implements SiteDao {
	private static final Log log = LogFactory.getLog(MysqlSiteDao.class);

	private static final String SQL_FIND_BY_ID = "select * from SITE where (PKID=? or NAME=? or DOMAIN=?) and  del_flag='0'";

	/**
	 * 方法输入的参数可以是ID、domain、name
	 * 
	 * @param ID
	 *            String
	 * @return SiteData
	 * @throws SQLException
	 */
	public SiteData findByID(String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setString(1, ID);
			statement.setString(2, ID);
			statement.setString(3, ID);
			SiteData[] result = retrieve(statement, SQL_FIND_BY_ID);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			close(statement);
		}
	}

	private SiteData[] retrieve(PreparedStatement statement, String sql)
			throws SQLException {
		long start = 0;
		if (log.isTraceEnabled()) {
			start = System.currentTimeMillis();
		}
		ResultSet rs = statement.executeQuery();
		if (log.isTraceEnabled()) {
			log.trace("[" + (System.currentTimeMillis() - start) + "]: query "
					+ sql);
		}
		ArrayList<SiteData> result = new ArrayList<SiteData>();
		while (rs.next()) {
			SiteData group = new SiteData();
			;
			populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (SiteData[]) result.toArray(new SiteData[result.size()]);
	}

	protected void populate(ResultSet rs, SiteData data) throws SQLException {
		data.setPkid(rs.getString("PKID"));
		data.setCreateDate(rs.getDate("CREATE_DATE"));
		data.setDisplayName(rs.getString("DISPLAY_NAME"));
		data.setDelFlag(rs.getBoolean("DEL_FLAG"));
		data.setOwnerID(rs.getString("OWNER_ID"));
		data.setOwnerType(rs.getInt("OWNER_TYPE"));
		data.setDomain(rs.getString("DOMAIN"));
		data.setExpiryDate(rs.getDate("EXPIRY_DATE"));
		data.setName(rs.getString("NAME"));
		data.setStartDate(rs.getDate("START_DATE"));
		data.setState(rs.getString("STATE"));
		data.setTemplateID(rs.getString("TMEPLATE_ID"));
		data.setCategoryID(rs.getString("CATEGORY_ID"));
	}

	private static final String SQL_FIND_BY_OwnerID = "select * from SITE where OWNER_ID=? and del_flag='0'";

	public SiteData findByOwner(String ownerID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_FIND_BY_OwnerID);
			statement.setString(1, ownerID);
			SiteData[] result = retrieve(statement, SQL_FIND_BY_OwnerID);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			close(statement);
		}

	}

	private static final String SQL_GetSiteCount = "select COUNT(*) from SITE where del_flag='0'";

	public int getSitesCount() throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_GetSiteCount);
			int result = queryInt(statement);
			return result;
		} finally {
			close(statement);
		}
	}

	private static final String SQL_GetSiteCountByTemplate = "select COUNT(*) from SITE t where t.tmeplate_id=? and t.del_flag='0' ";

	public int getSitesCountByTemplate(String templateID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_GetSiteCountByTemplate);
			statement.setString(1, templateID);
			int result = queryInt(statement);
			return result;
		} finally {
			close(statement);
		}
	}

	private static final String SQL_GetSiteCountByCategory = "select COUNT(*) from SITE t where t.category_id=? and t.del_flag='0' ";

	public int getSitesCountByCategory(String categoryID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_GetSiteCountByCategory);
			statement.setString(1, categoryID);
			int result = queryInt(statement);
			return result;
		} finally {
			close(statement);
		}

	}

	private static final String SQL_INSERT = "insert into SITE (PKID, NAME, DISPLAY_NAME, DEL_FLAG, OWNER_ID, OWNER_TYPE, DOMAIN, STATE, CREATE_DATE, START_DATE, EXPIRY_DATE, TMEPLATE_ID, CATEGORY_ID)"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	public void insert(SiteData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			if (data.getPkid() == null) {
				data.setPkid(getNextID() + "");
			}
			statement = connection.prepareStatement(SQL_INSERT);
			statement.setString(1, data.getPkid());
			statement.setString(2, data.getName());
			statement.setString(3, data.getDisplayName());
			statement.setBoolean(4, data.isDelFlag());
			statement.setString(5, data.getOwnerID());
			statement.setInt(6, data.getOwnerType());
			statement.setString(7, data.getDomain());
			statement.setString(8, data.getState());
			if (data.getCreateDate() != null)
				statement.setTimestamp(9, JdbcDao.toTimestame(data
						.getCreateDate()));
			else
				statement.setTimestamp(9, JdbcDao.toTimestame(new Date()));
			if (data.getStartDate() != null)
				statement.setTimestamp(10, JdbcDao.toTimestame(data
						.getStartDate()));
			else
				statement.setTimestamp(10, null);
			if (data.getStartDate() != null)
				statement.setTimestamp(11, JdbcDao.toTimestame(data
						.getExpiryDate()));
			else
				statement.setTimestamp(11, null);
			statement.setString(12, data.getTemplateID());
			statement.setString(13, data.getCategoryID());
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	private static final String SQL_DELETE = "update SITE set DEL_FLAG='1',pkid=concat(pkid,now()) where pkid = ?";

	public void delete(String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_DELETE);
			statement.setString(1, ID);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			close(statement);
		}
	}

	private static final String SQL_UPDATE = "update site set NAME = ?, DISPLAY_NAME = ?, DEL_FLAG = ?, OWNER_ID = ?, OWNER_TYPE = ?, DOMAIN = ?, STATE = ?, CREATE_DATE = ?, START_DATE = ?, EXPIRY_DATE = ?, TMEPLATE_ID = ?, CATEGORY_ID = ? "
			+ "where pkid =? ";

	public void update(SiteData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_UPDATE);
			statement.setString(1, data.getName());
			statement.setString(2, data.getDisplayName());
			statement.setBoolean(3, data.isDelFlag());
			statement.setString(4, data.getOwnerID());
			statement.setInt(5, data.getOwnerType());
			statement.setString(6, data.getDomain());
			statement.setString(7, data.getState());
			if (data.getCreateDate() != null)
				statement.setTimestamp(8, JdbcDao.toTimestame(data
						.getCreateDate()));
			else
				statement.setTimestamp(8, JdbcDao.toTimestame(new Date()));
			if (data.getStartDate() != null)
				statement.setTimestamp(9, JdbcDao.toTimestame(data
						.getStartDate()));
			else
				statement.setTimestamp(9, null);
			if (data.getExpiryDate() != null)
				statement.setTimestamp(10, JdbcDao.toTimestame(data
						.getExpiryDate()));
			else
				statement.setTimestamp(10, null);
			statement.setString(11, data.getTemplateID());
			statement.setString(12, data.getCategoryID());
			statement.setString(13, data.getPkid());
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	public SiteData[] search(String query, SQLParameter[] parameters,
			int fromIndex, int number) throws SQLException {
		String sql = JdbcDao.toRangeQueryForMysql(query);
		PreparedStatement statement = null;
		try {
			List<SQLParameter> all = new ArrayList<SQLParameter>();
			if (parameters != null)
				Collections.addAll(all, parameters);
			all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex)));
			all.add(new SQLParameter(Types.INTEGER, new Integer(number)));
			statement = createStatement(sql, (SQLParameter[]) all
					.toArray(new SQLParameter[all.size()]));
			SiteData[] datas = retrieve(statement, sql);
			return datas;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			close(statement);
		}

	}

	private PreparedStatement createStatement(String query,
			SQLParameter[] parameters) {

		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(query);
			if ((parameters != null) && (parameters.length > 0)) {
				for (int i = 0; i < parameters.length; i++) {
					command.setObject(i + 1, parameters[i].getValue());
				}
			}
			return command;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		}
	}

	public long countResultNum(String query, SQLParameter[] parameters)
			throws SQLException {
		long result = 0;
		PreparedStatement command = null;
		try {
			command = createStatement(query, parameters);

			RowSet rowset = createCacheRowSet(command);
			while (rowset.next()) {
				result = rowset.getLong(1);
			}
			return result;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			close(command);
		}

	}

}
