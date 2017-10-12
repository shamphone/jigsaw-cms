package com.fulong.longcon.site.dao.mysql;

import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.DatabaseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.longcon.site.dao.ChannelDao;
import com.fulong.longcon.site.data.ChannelData;
import com.fulong.common.dao.SQLParameter;
import com.fulong.common.dao.JdbcException;
import javax.sql.RowSet;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
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
public class MysqlChannelDao extends JdbcDao implements ChannelDao {
	private static final Log log = LogFactory.getLog(MysqlChannelDao.class);
	private static final String SQL_FIND_BY_ID = "select * from CHANNEL where PKID=?  and state !='delete'";

	public ChannelData findByID(String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setString(1, ID);
			ChannelData[] result = this.retrieve(statement, SQL_FIND_BY_ID);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			this.close(statement);
		}
	}

	private ChannelData[] retrieve(PreparedStatement statement, String sql)
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
		ArrayList<ChannelData> result = new ArrayList<ChannelData>();
		while (rs.next()) {
			ChannelData group = new ChannelData();
			;
			this.populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (ChannelData[]) result.toArray(new ChannelData[result.size()]);
	}

	protected void populate(ResultSet rs, ChannelData data) throws SQLException {
		data.setPkid(rs.getString("PKID"));
		data.setCreateDate(rs.getDate("CREATE_DATE"));
		data.setDisplayName(rs.getString("DISPLAY_NAME"));
		data.setExpiryDate(rs.getDate("EXPIRY_DATE"));
		data.setName(rs.getString("NAME"));
		data.setNodeID(rs.getString("NODE_ID"));
		data.setParentID(rs.getString("PARENT_ID"));
		data.setStartDate(rs.getDate("START_DATE"));
		data.setState(rs.getString("STATE"));
		data.setTemplateID(rs.getString("TEMPLATE_ID"));
		data.setTemplatePath(rs.getString("TEMPLATE_PATH"));
		data.setType(rs.getString("TYPE"));
		data.setSecure(rs.getString("SECURE"));
	}

	private static final String SQL_FIND_BY_TemplateAndID = "select * from CHANNEL where TEMPLATE_ID = ? and PKID = ?  and state !='delete'";

	public ChannelData findByTemplateAndID(String templateID, String ID)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection
					.prepareStatement(SQL_FIND_BY_TemplateAndID);
			statement.setString(1, templateID);
			statement.setString(2, ID);
			ChannelData[] result = this.retrieve(statement,
					SQL_FIND_BY_TemplateAndID);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_FindBy_Template = "select * from CHANNEL where TEMPLATE_ID = ? and state !='delete' order by create_date";

	public ChannelData[] findByTemplate(String templateID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_FindBy_Template);
			statement.setString(1, templateID);
			ChannelData[] result = this
					.retrieve(statement, SQL_FindBy_Template);
			return result;
		} finally {
			this.close(statement);
		}

	}

	private static final String SQL_FindBy_TemplateAndName = "select * from CHANNEL where TEMPLATE_ID = ? AND NAME = ? and state !='delete'";

	public ChannelData findByTemplateAndName(String templateID, String name)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection
					.prepareStatement(SQL_FindBy_TemplateAndName);
			statement.setString(1, templateID);
			statement.setString(2, name);
			ChannelData[] result = this.retrieve(statement,
					SQL_FindBy_TemplateAndName);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_FindBy_TemplateAndNode = 
		"select * from CHANNEL where TEMPLATE_ID = ? AND node_id = ? AND type=? and state !='delete'";

	public ChannelData findBindingChannel(String templateID, String nodeID,
			String type) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection
					.prepareStatement(SQL_FindBy_TemplateAndNode);
			statement.setString(1, templateID);
			statement.setString(2, nodeID);
			statement.setString(3, type);
			ChannelData[] result = this.retrieve(statement,
					SQL_FindBy_TemplateAndNode);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_FindRootChannelBy_Template = "select * from CHANNEL where PARENT_ID is null and TEMPLATE_ID = ? ";

	public ChannelData findRootChannelByTemplate(String templateID)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection
					.prepareStatement(SQL_FindRootChannelBy_Template);
			statement.setString(1, templateID);
			ChannelData[] result = this.retrieve(statement,
					SQL_FindRootChannelBy_Template);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_Insert = "insert into CHANNEL (PKID, TEMPLATE_ID, TYPE, NAME, DISPLAY_NAME, PARENT_ID, TEMPLATE_PATH, NODE_ID, STATE, CREATE_DATE, START_DATE, EXPIRY_DATE, SECURE)"
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";

	public void insert(ChannelData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			if (data.getPkid() == null) {
				data.setPkid(JdbcDao.getNextID() + "");
			}
			statement = this.connection.prepareStatement(SQL_Insert);
			statement.setString(1, data.getPkid());
			statement.setString(2, data.getTemplateID());
			statement.setString(3, data.getType());
			statement.setString(4, data.getName());
			statement.setString(5, data.getDisplayName());
			statement.setString(6, data.getParentID());
//			statement.setString(7, data.getLocale());
			statement.setString(7, data.getTemplatePath());
			statement.setString(8, data.getNodeID());
			statement.setString(9, data.getState());
			if (data.getCreateDate() != null)
				statement.setTimestamp(10, JdbcDao.toTimestame(data
						.getCreateDate()));
			else
				statement.setTimestamp(10, JdbcDao.toTimestame(new Date()));
			if (data.getStartDate() != null)
				statement.setTimestamp(11, JdbcDao
						.toTimestame(data.getStartDate()));
			else
				statement.setTimestamp(11, null);
			if (data.getExpiryDate() != null)
				statement.setTimestamp(12, JdbcDao.toTimestame(data
						.getExpiryDate()));
			else
				statement.setTimestamp(12, null);
			statement.setString(13, data.getSecure());
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}

	private static final String SQL_UPDATE =
		"update CHANNEL set TEMPLATE_ID = ?, TYPE = ?, NAME = ?, DISPLAY_NAME = ?, PARENT_ID = ?, TEMPLATE_PATH = ?, NODE_ID = ?, STATE = ?, CREATE_DATE = ?, START_DATE = ?, EXPIRY_DATE = ?, SECURE = ?"
			+ " where PKID = ?";

	public void update(ChannelData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_UPDATE);
			statement.setString(1, data.getTemplateID());
			statement.setString(2, data.getType());
			statement.setString(3, data.getName());
			statement.setString(4, data.getDisplayName());
			statement.setString(5, data.getParentID());
			statement.setString(6, data.getTemplatePath());
			statement.setString(7, data.getNodeID());
			statement.setString(8, data.getState());
			if (data.getCreateDate() != null)
				statement.setTimestamp(9, JdbcDao.toTimestame(data
						.getCreateDate()));
			else
				statement.setTimestamp(9, JdbcDao.toTimestame(new Date()));
			if (data.getStartDate() != null)
				statement.setTimestamp(10, JdbcDao
						.toTimestame(data.getStartDate()));
			else
				statement.setTimestamp(10, null);
			if (data.getExpiryDate() != null)
				statement.setTimestamp(11, JdbcDao.toTimestame(data
						.getExpiryDate()));
			else
				statement.setTimestamp(11, null);
			statement.setString(12, data.getSecure());

			statement.setString(13, data.getPkid());
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}

	private static final String SQL_Delete_By_ID = "delete from CHANNEL where PKID = ? ";

	/**
	 * 
	 * @param ID
	 *            String
	 * @throws SQLException
	 */
	public void delete(String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_Delete_By_ID);
			statement.setString(1, ID);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			this.close(statement);
		}
	}



	/**
	 * 删除栏目同时删除子栏目
	 * 
	 * @param templateID
	 *            String
	 * @param ID
	 *            String
	 * @throws SQLException
	 */
	public void delete(String templateID, String ID) throws SQLException {
		java.sql.CallableStatement statement=null;
		try{
			statement =connection.prepareCall("{ call pro_delete_channel(?) }");
			statement.setString(1,ID);
			statement.executeUpdate();	
		}catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			this.close(statement);
		}
	}

	
	public ChannelData[] search(String query, SQLParameter[] parameters,
			int fromIndex, int number) throws SQLException {
		String sql = JdbcDao.toRangeQueryForMysql(query);
		PreparedStatement statement = null;
		try {
			List<SQLParameter> all = new ArrayList<SQLParameter>();
			if (parameters != null)
				Collections.addAll(all, parameters);
			all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex)));
			all.add(new SQLParameter(Types.INTEGER, new Integer(number)));
			statement = this.createStatement(sql, (SQLParameter[]) all
					.toArray(new SQLParameter[all.size()]));
			ChannelData[] datas = this.retrieve(statement, sql);
			return datas;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			this.close(statement);
		}
	}

	public long countResultNum(String query, SQLParameter[] parameters)
			throws SQLException {
		long result = 0;
		PreparedStatement command = null;
		try {
			command = this.createStatement(query, parameters);

			RowSet rowset = this.createCacheRowSet(command);
			while (rowset.next()) {
				result = rowset.getLong(1);
			}
			return result;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			this.close(command);
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

}
