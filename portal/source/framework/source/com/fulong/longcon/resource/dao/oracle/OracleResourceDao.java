package com.fulong.longcon.resource.dao.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.sql.RowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.JdbcException;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.resource.dao.ResourceDao;
import com.fulong.longcon.resource.data.ResourceData;

/**
 * 
 * <p>
 * Title: Longcon Passport System
 * </p>
 * 
 * <p>
 * Description: Longcon Passport
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD. *
 * 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author lishaobo
 * @version 2.0
 */
public class OracleResourceDao extends JdbcDao implements ResourceDao {
	private static final Log log = LogFactory.getLog(OracleResourceDao.class);

	/**
	 * 插入数据，并产生一个新的ID
	 * 
	 * @param data
	 *            ForumTopicData
	 * @throws SQLException
	 */
	private static final String SQL_insert = "INSERT INTO RESOURCES(OWNER_ID,PATH, CREATE_TIME, UPDATE_TIME, CREATOR_ID, "
			+ "IS_FOLDER, NAME, MIME, READONLY, ISHIDDEN, SUBJECT,  "
			+ " LENGTH, PARENT_PATH, pkid) "
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * private static final String SQL_insert_content =
	 * "select CONTENT from RESOURCES where PATH=? for update";
	 */
	public void insert(ResourceData data) throws SQLException {
		if (data.getPkid() == null) {
			data.setPkid("" + getNextID());
		}
		ResourceData existData = getByPath(data.getPkid());
		if (existData != null) {
			data = existData;
			return;
		}
		existData = getByPath(data.getPath());
		if (existData != null) {
			data = existData;
			return;
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_insert);

			statement.setString(1, data.getOwnerID());
			statement.setString(2, data.getPath());
			statement.setTimestamp(3, toTimestame(data.getCreationTime()));
			statement.setTimestamp(4, toTimestame(data.getUpdateTime()));
			statement.setString(5, data.getCreatorID());
			statement.setBoolean(6, data.isIsFolder());
			statement.setString(7, data.getName());
			statement.setString(8, data.getMime());
			statement.setBoolean(9, data.isReadOnly());
			statement.setBoolean(10, data.isHidden());
			statement.setString(11, data.getSubject());
			statement.setLong(12, data.getLength());
			statement.setString(13, data.getParentPath());
			statement.setString(14, data.getPkid());
			statement.execute();
		} finally {
			close(statement);
		}
	}

	/*
	 * private static final String sql_update =
	 * "update RESOURCES set OWNER_ID= ?, CREATE_TIME = ?, UPDATE_TIME = ?, CREATOR_ID = ?, "
	 * +
	 * "IS_FOLDER = ?, NAME = ?, MIME = ?, READONLY = ?, ISHIDDEN = ?, SUBJECT = ?,  "
	 * + " LENGTH = ?, PARENT_PATH=? ,PATH=?  where pkid = ?";
	 * 
	 * public void update(ResourceData data, String oldPath) throws SQLException
	 * { PreparedStatement statement = null; try { statement =
	 * connection.prepareStatement( sql_update); statement.setString(1,
	 * data.getOwnerID()); statement.setTimestamp(2,
	 * toTimestame(data.getCreationTime())); statement.setTimestamp(3,
	 * toTimestame(data.getUpdateTime())); statement.setString(4,
	 * data.getCreatorID()); statement.setBoolean(5, data.isIsFolder());
	 * statement.setString(6, data.getName()); statement.setString(7,
	 * data.getMime()); statement.setBoolean(8, data.isReadOnly());
	 * statement.setBoolean(9, data.isHidden()); statement.setString(10,
	 * data.getSubject()); statement.setLong(11, data.getLength());
	 * statement.setString(12, data.getParentPath()); statement.setString(13,
	 * data.getPath()); statement.setString(14, data.getPkid());
	 * statement.executeUpdate(); } finally { close(statement); } }
	 */

	private static final String sql_update2 = "update RESOURCES set OWNER_ID= ?, CREATE_TIME = ?, UPDATE_TIME = ?, CREATOR_ID = ?, "
			+ "IS_FOLDER = ?, NAME = ?, MIME = ?, READONLY = ?, ISHIDDEN = ?, SUBJECT = ?,  "
			+ " LENGTH = ?, PARENT_PATH=?, PATH=?   where  pkid = ?";

	public void update(ResourceData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql_update2);
			statement.setString(1, data.getOwnerID());
			statement.setTimestamp(2, toTimestame(data.getCreationTime()));
			statement.setTimestamp(3, toTimestame(data.getUpdateTime()));
			statement.setString(4, data.getCreatorID());
			statement.setBoolean(5, data.isIsFolder());
			statement.setString(6, data.getName());
			statement.setString(7, data.getMime());
			statement.setBoolean(8, data.isReadOnly());
			statement.setBoolean(9, data.isHidden());
			statement.setString(10, data.getSubject());
			statement.setLong(11, data.getLength());
			statement.setString(12, data.getParentPath());
			statement.setString(13, data.getPath());
			statement.setString(14, data.getPkid());
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	/**
	 * private static final String updateBlob =
	 * "Update  blob_value t Set t.Value = empty_blob() where t.Node_Id=? ";
	 * 
	 * public long updateContent(ResourceData data, InputStream in) throws
	 * SQLException { PreparedStatement statement = null; try { statement =
	 * connection.prepareStatement( updateBlob); statement.setString(1,
	 * data.getPath()); statement.executeUpdate(); try { return
	 * insertBlob(data.getPkid(), in); } catch (FileNotFoundException ex) {
	 * throw new SQLException(); } } finally { close(statement); } }
	 */

	/**
	 * 修改DESCRIPTION字段
	 * 
	 * @param data
	 *            ResourceData
	 * @throws SQLException
	 */
	private static final String updateDesc = "update resources t set t.description =? where t.path=?";

	public void updateDescription(ResourceData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(updateDesc);
			statement.setString(1, data.getDescription());
			statement.setString(2, data.getPath());
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	/**
	 * 插入blob
	 * 
	 * @param data
	 *            ResourceData
	 * @param in
	 *            InputStream
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	/**
	 * private static final String SQL_insert_blob =
	 * "select CONTENT from RESOURCES where PATH=? for update";
	 * 
	 * public long insertBlob(String path, InputStream in) throws SQLException,
	 * FileNotFoundException { ResultSet resultset = null; PreparedStatement
	 * statementUpdate = null; statementUpdate =
	 * connection.prepareStatement(SQL_insert_content);
	 * statementUpdate.setString(1, path); resultset =
	 * statementUpdate.executeQuery(); int length = 0; while (resultset.next())
	 * {
	 * 
	 * // 取出此BLOB对象 oracle.sql.BLOB blob = (oracle.sql.BLOB) resultset.getBlob(
	 * "CONTENT"); // 向BLOB对象中写入数据 BufferedOutputStream out = new
	 * BufferedOutputStream(blob. getBinaryOutputStream()); try { int c; while (
	 * (c = in.read()) != -1) { out.write(c); length += c; } in.close();
	 * out.close();
	 * 
	 * } catch (Exception ex) { throw new FileNotFoundException(); }
	 * 
	 * } return length; }
	 */

	private static final String sql_delete = "delete from  RESOURCES where PATH=?";

	public void delete(String path) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql_delete);
			statement.setString(1, path);
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	private static final String sql_deleteTree = "delete from resources t where t.pkid in "
			+ " (select t.pkid from resources t start with t.path=? connect by prior t.path = t.parent_path)";

	/**
	 * 删除path以及path以下的资源
	 * 
	 * @param path
	 *            String
	 * @throws SQLException
	 */
	public void deleteTree(String path) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql_deleteTree);
			statement.setString(1, path);
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	private static final String SQL_select = "select t.owner_id , t.path , t.create_time , t.update_time ,"
			+ " t.creator_id , t.is_folder , t.name , t.mime , t.readonly , "
			+ "t.ishidden , t.subject , t.length , t.parent_path , t.pkid "
			+ "from resources t where PATH=? or t.pkid=?";

	public ResourceData getByPath(String path) throws SQLException {
		PreparedStatement statement = null;
		statement = connection.prepareStatement(SQL_select);
		statement.setString(1, path);
		statement.setString(2, path);

		ResourceData[] result = retrieve(statement, SQL_select);
		if ((result != null) && (result.length > 0)) {
			return result[0];
		}
		return null;
	}

	private ResourceData[] retrieve(PreparedStatement statement, String sql)
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
		ArrayList<ResourceData> result = new ArrayList<ResourceData>();
		while (rs.next()) {
			ResourceData group = new ResourceData();
			;
			populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (ResourceData[]) result.toArray(new ResourceData[result.size()]);
	}

	protected void populate(ResultSet rs, ResourceData data)
			throws SQLException {
		data.setOwnerID(rs.getString("OWNER_ID"));
		data.setPath(rs.getString("PATH"));
		data.setCreationTime(rs.getTimestamp("CREATE_TIME"));
		data.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
		data.setCreatorID(rs.getString("CREATOR_ID"));
		data.setIsFolder(rs.getBoolean("IS_FOLDER"));
		data.setName(rs.getString("NAME"));
		data.setMime(rs.getString("MIME"));
		data.setReadOnly(rs.getBoolean("READONLY"));
		data.setHidden(rs.getBoolean("ISHIDDEN"));
		data.setSubject(rs.getString("SUBJECT"));
		data.setLength(rs.getInt("LENGTH"));
		data.setParentPath(rs.getString("PARENT_PATH"));
		data.setPkid(rs.getString("PKID"));

	}

	/**
	 * private static final String selectBlob =
	 * "select t.content from resources t where t.path=? ";
	 * 
	 * public InputStream getBlob(String path) throws SQLException {
	 * PreparedStatement statement = null; statement =
	 * connection.prepareStatement( selectBlob); statement.setString(1, path);
	 * ResultSet rs = statement.executeQuery(); while (rs.next()) { InputStream
	 * is = rs.getBinaryStream("CONTENT"); return is; } return null; }
	 */
	private static final String findDescription = "select t.DESCRIPTION from resources t where t.path=?";

	public String[] getDescription(ResourceData data) throws SQLException {
		PreparedStatement statement = null;
		statement = connection.prepareStatement(findDescription);
		statement.setString(1, data.getPath());
		ResultSet rs = statement.executeQuery();
		Vector<String> vect = new Vector<String>();
		while (rs.next()) {
			String description = rs.getString("DESCRIPTION");
			vect.add(description);
		}
		return (String[]) vect.toArray(new String[vect.size()]);
	}

	public ResourceData[] search(String query, SQLParameter[] parameters,
			int fromIndex, int number) throws SQLException {
		String sql = toRangeQuery(query);
		PreparedStatement statement = null;
		try {
			List<SQLParameter> all = new ArrayList<SQLParameter>();
			if (parameters != null) {
				Collections.addAll(all, parameters);
			}
			all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex
					+ number)));
			all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex)));
			statement = createStatement(sql, (SQLParameter[]) all
					.toArray(new SQLParameter[all.size()]));
			ResourceData[] datas = retrieve(statement, sql);
			return datas;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			if (statement != null) {
				statement.close();
			}
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
