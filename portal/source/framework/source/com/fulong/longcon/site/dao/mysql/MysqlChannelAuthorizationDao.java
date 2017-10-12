package com.fulong.longcon.site.dao.mysql;

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
 * @author LJY
 * 
 */
public class MysqlChannelAuthorizationDao extends JdbcDao implements
		ChannelAuthorizationDao {
	private static final Log log = LogFactory
			.getLog(MysqlChannelAuthorizationDao.class);

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
			command = this.connection.prepareStatement(SQL_CREATE);
			command.setString(1, data.getPrincipalID());
			command.setInt(2, data.getPrincipalType());
			command.setString(3, data.getChannelID());
			command.setString(4, data.getTemplateID());
			command.executeUpdate();

		} finally {
			this.close(command);
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
			this.close(command);
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
			this.close(command);
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
			command = this.connection.prepareStatement(SQL_FIND_BY_TEMPLATEID);
			command.setString(1, templateId);
			ChannelAuthorizationData[] result = this.retrieve(command,
					SQL_FIND_BY_TEMPLATEID);
			return result;
		}

		finally {
			this.close(command);
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
			command = this.connection
					.prepareStatement(SQL_FIND_BY_TEMPLATEID_CHANNELID);
			command.setString(1, templateId);
			command.setString(2, channelId);
			ChannelAuthorizationData[] result = this.retrieve(command,
					SQL_FIND_BY_TEMPLATEID_CHANNELID);
			return result;
		}

		finally {
			this.close(command);
		}
	}

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
		if (principalID == null)
			return null;
		String SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID = " select *  from channel_authorization ca where ca.channel_id = ?   and ca.template_id = ?   and ca.principal_id  in  ";
		String sql = "select definition from node_type nt where nt.pkid ='"+principalID+"'";				
		SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID = SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID
				+ "("
				+ this.getClauseForRecursiveSQL("node_definition", "super_id",
						"pkid", null, sql, "pkid") + ")";
		PreparedStatement command = null;
		try {
			command = this.connection
					.prepareStatement(SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID);
			command.setString(1, channelId);
			command.setString(2, templateId);
			ChannelAuthorizationData[] result = this.retrieve(command,
					SQL_FIND_BY_TEMPLATEID_CHANNELID_PRINCIPALID);
			if (result != null && result.length > 0)
				return result[0];
			return null;
		} finally {
			this.close(command);
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
			this.populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (ChannelAuthorizationData[]) result
				.toArray(new ChannelAuthorizationData[result.size()]);
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

	public ChannelAuthorizationData[] findByGroupID(String templateId,
			String channelId, String principalId) throws SQLException {
		if (principalId == null)
			return null;
		String FIND_ALLGROUPS = " select *  from channel_authorization ca  where   ca.channel_id = ?   and ca.template_id = ? and   ca.principal_id in  ";
		FIND_ALLGROUPS = FIND_ALLGROUPS
				+ "("
				+ this.getClauseForRecursive("node_definition", "super_id",
						"pkid", null, principalId, "pkid") + ")";
		PreparedStatement command = null;
		try {
			command = this.connection.prepareStatement(FIND_ALLGROUPS);
			command.setString(1, channelId);
			command.setString(2, templateId);
			ChannelAuthorizationData[] result = this.retrieve(command,
					FIND_ALLGROUPS);
			return result;
		}

		finally {
			this.close(command);
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
	public ChannelAuthorizationData[] findByGroupIDAndTemplateID(
			String groupID, String templateID) throws SQLException {
		if (groupID == null)
			return null;
		String FIND_BY_GROUPID_AND_TMEPLATEID = "   select * from channel_authorization ca  where  ca.template_id = ? and ca.principal_id  in ";
		FIND_BY_GROUPID_AND_TMEPLATEID = FIND_BY_GROUPID_AND_TMEPLATEID
				+ "("
				+ this.getClauseForRecursive("node_definition", "super_id",
						"pkid", null, groupID, "pkid") + ")";
		PreparedStatement command = null;
		try {
			command = this.connection
					.prepareStatement(FIND_BY_GROUPID_AND_TMEPLATEID);
			command.setString(1, templateID);
			ChannelAuthorizationData[] result = this.retrieve(command,
					FIND_BY_GROUPID_AND_TMEPLATEID);
			return result;
		} finally {
			this.close(command);
		}
	}

	/**
	 * 根据用户组ID和模板ID和栏目ID获取对应栏目
	 * 
	 * @param groupID
	 * @param templateID
	 * @param channelID
	 * @return ChannelAuthorizationData
	 * @throws SQLException
	 */
	// private String FIND_BY_GROUPID_AND_TMEPLATEID_AND_CHANNELID =
	// "select ca.* from channel_authorization ca, (select * from node_definition nd start with nd.pkid=? connect by prior nd.super_id = nd.pkid) def where ca.principal_id=def.pkid AND ca.template_id= ? AND ca.channel_id= ?";
	public ChannelAuthorizationData findByGroupIDAndTemplateIDAndChannelID(
			String groupID, String templateID, String channelID) {		
		try {
			ChannelAuthorizationData[] rs= this.findByGroupID(templateID, channelID, groupID);
			if(rs!=null&&rs.length>0)
				return rs[0];
		} catch (SQLException e) {
		}
		return null;
	}
}
