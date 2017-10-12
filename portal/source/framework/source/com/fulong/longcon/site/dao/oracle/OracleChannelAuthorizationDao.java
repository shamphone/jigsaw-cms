package com.fulong.longcon.site.dao.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.site.dao.ChannelAuthorizationDao;
import com.fulong.longcon.site.data.ChannelAuthorizationData;

/**
 * 
 * <p>
 * Title: 龙驭建站系统核心引擎－栏目授权数据操作类
 * </p>
 * 
 * <p>
 * Description: 龙驭建站系统核心引擎
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
 * @author sunyuchao
 * 
 */
public class OracleChannelAuthorizationDao extends JdbcDao implements
		ChannelAuthorizationDao {
	private static final Log log = LogFactory
			.getLog(OracleChannelAuthorizationDao.class);

	/**
	 * 向栏目授权表中插入数据
	 * 
	 * @param data
	 * @throws SQLException
	 */
	private static final String SQL_CREATE = "insert into CHANNEL_AUTHORIZATION(PRINCIPAL_ID, PRINCIPAL_TYPE, CHANNEL_ID, TEMPLATE_ID) values(?,?,?,?)";

	public void create(ChannelAuthorizationData data) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_CREATE);
			command.setString(1, data.getPrincipalID());
			command.setInt(2, data.getPrincipalType());
			command.setString(3, data.getChannelID());
			command.setString(4, data.getTemplateID());
			command.executeUpdate();

		} finally {
			close(command);
		}

	}

	/**
	 * 删除栏目授权表中的数据
	 * 
	 * @param data
	 * @throws SQLException
	 */
	private static final String SQL_DELETE = "delete from CHANNEL_AUTHORIZATION where CHANNEL_ID=? and TEMPLATE_ID=? and PRINCIPAL_ID = ?";

	public void delete(ChannelAuthorizationData data) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_DELETE);
			command.setString(1, data.getChannelID());
			command.setString(2, data.getTemplateID());
			command.setString(3, data.getPrincipalID());
			command.executeUpdate();
		}

		finally {
			close(command);
		}

	}

	/**
	 * 删除栏目授权表中的数据
	 * 
	 * @param data
	 * @throws SQLException
	 */
	private static final String SQL_DELETE2 = "delete from CHANNEL_AUTHORIZATION where CHANNEL_ID=? and TEMPLATE_ID=? ";

	public void delete(String templateId, String channelId) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_DELETE2);
			command.setString(1, channelId);
			command.setString(2, templateId);
			command.executeUpdate();
		} finally {
			close(command);
		}

	}

	/**
	 * 根据模板ID获取该模板下全部授权栏目
	 * 
	 * @param templateId
	 * @return
	 * @throws SQLException
	 */
	private static final String SQL_FIND_BY_TEMPLATEID = "select * from CHANNEL_AUTHORIZATION where TEMPLATE_ID=?";

	public ChannelAuthorizationData[] findByTemplateID(String templateId)
			throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_FIND_BY_TEMPLATEID);
			command.setString(1, templateId);
			ChannelAuthorizationData[] result = retrieve(command,
					SQL_FIND_BY_TEMPLATEID);
			return result;
		}

		finally {
			close(command);
		}

	}

	/**
	 * 根据模板ID和栏目ID获取授权栏目
	 * 
	 * @param templateId
	 * @param channelID
	 * @return
	 * @throws SQLException
	 */
	private static final String SQL_FIND_BY_TEMPLATEID_CHANNELID = "select * from CHANNEL_AUTHORIZATION where TEMPLATE_ID=? and CHANNEL_ID = ?";

	public ChannelAuthorizationData[] findByChannelID(String templateId,
			String channelId) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection
					.prepareStatement(SQL_FIND_BY_TEMPLATEID_CHANNELID);
			command.setString(1, templateId);
			command.setString(2, channelId);
			ChannelAuthorizationData[] result = retrieve(command,
					SQL_FIND_BY_TEMPLATEID_CHANNELID);
			return result;
		}

		finally {
			close(command);
		}
	}

	// private static final String SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID
	// =
	// "select * from CHANNEL_AUTHORIZATION where TEMPLATE_ID=? and CHANNEL_ID = ? and PRINCIPAL_ID = ?";
	private String SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID = "select * from channel_authorization ca, (select * from node_definition nd start with nd.pkid in (select definition from node_type nt where nt.pkid=?) connect by prior nd.super_id = nd.pkid) def where ca.principal_id=def.pkid AND ca.channel_id= ? and ca.template_id= ?";

	/**
	 * 根据模板ID和栏目ID，用户ID获取授权栏目
	 * 
	 * @param templateId
	 * @param channelId
	 * @param principalID
	 * @return
	 * @throws SQLException
	 */
	public ChannelAuthorizationData findByPrincipalID(String templateId,
			String channelId, String principalID) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection
					.prepareStatement(SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID);
			command.setString(3, templateId);
			command.setString(2, channelId);
			command.setString(1, principalID);
			ChannelAuthorizationData result = retrieveSingle(command,
					SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID);
			return result;
		}

		finally {
			close(command);
		}
	}

	private ChannelAuthorizationData[] retrieve(PreparedStatement statement,
			String sql) throws SQLException {
		long start = 0;
		if (log.isTraceEnabled()) {
			start = System.currentTimeMillis();
		}
		ResultSet rs = statement.executeQuery();
		if (log.isTraceEnabled()) {
			log.trace("[" + (System.currentTimeMillis() - start) + "]: query "
					+ sql);
		}
		ArrayList<ChannelAuthorizationData> result = new ArrayList<ChannelAuthorizationData>();
		while (rs.next()) {
			ChannelAuthorizationData group = new ChannelAuthorizationData();
			;
			populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		
		return (ChannelAuthorizationData[]) result.toArray(new ChannelAuthorizationData[result.size()]);
	}

	private ChannelAuthorizationData retrieveSingle(
			PreparedStatement statement, String sql) throws SQLException {
		long start = 0;
		if (log.isTraceEnabled()) {
			start = System.currentTimeMillis();
		}
		ResultSet rs = statement.executeQuery();
		if (log.isTraceEnabled()) {
			log.trace("[" + (System.currentTimeMillis() - start) + "]: query "
					+ sql);
		}
		ChannelAuthorizationData group = null;
		if (rs.next()) {
			group = new ChannelAuthorizationData();
			group.setPrincipalID(rs.getString("PRINCIPAL_ID"));
			group.setPrincipalType(rs.getInt("PRINCIPAL_TYPE"));
			group.setTemplateID(rs.getString("TEMPLATE_ID"));
			group.setChannelID(rs.getString("CHANNEL_ID"));
		}
		rs.close();
		statement.close();
		return group;
	}

	protected void populate(ResultSet rowset, ChannelAuthorizationData data)
			throws SQLException {
		data.setPrincipalID(rowset.getString("PRINCIPAL_ID"));
		data.setPrincipalType(rowset.getInt("PRINCIPAL_TYPE"));
		data.setTemplateID(rowset.getString("TEMPLATE_ID"));
		data.setChannelID(rowset.getString("CHANNEL_ID"));
	}

	/**
	 * 根据模板ID和栏目ID，用户ID获取授权给该用户所在组的全部栏目
	 * 
	 * @param tmeplateId
	 * @param channelId
	 * @param principalId
	 * @return
	 * @throws SQLException
	 */
	// private String
	// FIND_ALLGROUPS="select * from channel_authorization t,(Select b.definition_id, b.super_id From Node_Type v,node_definition_super b Where v.pkid=? And v.definition=b.definition_id) l Where t.principal_id=l.definition_id Or t.principal_id=l.super_id And t.channel_id=? And t.template_id=?";
	private String FIND_ALLGROUPS = "select * from channel_authorization ca, (select * from node_definition nd start with nd.pkid=? connect by prior nd.super_id = nd.pkid) def where ca.principal_id=def.pkid AND ca.channel_id= ? and ca.template_id= ?";

	public ChannelAuthorizationData[] findByGroupID(String templateId,
			String channelId, String principalId) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(FIND_ALLGROUPS);
			command.setString(1, principalId);
			command.setString(2, channelId);
			command.setString(3, templateId);
			ChannelAuthorizationData[] result = retrieve(command,
					FIND_ALLGROUPS);
			return result;
		}

		finally {
			close(command);
		}

	}

	/**
	 * 根据用户组ID和模板ID获取授权给该用户组的全部栏目
	 * 
	 * @param groupID
	 * @param templateID
	 * @return ChannelAuthorizationData[]
	 * @throws SQLException
	 */
	private String FIND_BY_GROUPID_AND_TMEPLATEID = "select ca.* from channel_authorization ca, (select * from node_definition nd start with nd.pkid=? connect by prior nd.super_id = nd.pkid) def where ca.principal_id=def.pkid AND ca.template_id= ?";

	public ChannelAuthorizationData[] findByGroupIDAndTemplateID(
			String groupID, String templateID) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection
					.prepareStatement(FIND_BY_GROUPID_AND_TMEPLATEID);
			command.setString(1, groupID);
			command.setString(2, templateID);
			ChannelAuthorizationData[] result = retrieve(command,
					FIND_BY_GROUPID_AND_TMEPLATEID);
			return result;
		} finally {
			close(command);
		}
	}

	// 此方法调用的SQL和findByGroupID方法完全一致。
	public ChannelAuthorizationData findByGroupIDAndTemplateIDAndChannelID(
			String groupID, String templateID, String channelID)
			throws SQLException {
		try {
			ChannelAuthorizationData[] rs = findByGroupID(templateID,
					channelID, groupID);
			if (rs != null && rs.length > 0)
				return rs[0];
		} catch (SQLException e) {
		}
		return null;
	}
}
