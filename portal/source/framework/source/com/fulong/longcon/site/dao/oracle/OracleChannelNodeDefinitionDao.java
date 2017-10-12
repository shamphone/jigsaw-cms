package com.fulong.longcon.site.dao.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.site.dao.ChannelNodeDefinitionDao;
import com.fulong.longcon.site.data.ChannelNodeDefinitionData;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎--栏目绑定内容分类数据操作类</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公�? 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公�?</p>
 *
 * @author sunyuchao
 */

public class OracleChannelNodeDefinitionDao extends JdbcDao implements
		ChannelNodeDefinitionDao {
	private static final Log log = LogFactory.getLog(OracleChannelNodeDefinitionDao.class);

	private static String SQL_DELETE = "delete from channel_nodedefinition t where t.template_id=? and t.node_definition_id=?";
	public void delete(String templateID,String nodeDefinitionID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_DELETE);
			statement.setString(1, templateID);
			statement.setString(2, nodeDefinitionID);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			this.close(statement);
		}

	}

	private static String SQL_INSERT = "insert into channel_nodedefinition (template_id,channel_id,node_definition_id,create_time) values (?,?,?,?)";

	public void insert(ChannelNodeDefinitionData data) throws SQLException {
		PreparedStatement statement = null;
		try {

			statement = this.connection.prepareStatement(SQL_INSERT);
			statement.setString(1, data.getTemplateID());
			statement.setString(2, data.getChannelID());
			statement.setString(3, data.getNodeDdefinitionID());
			statement.setTimestamp(4, toTimestame(new Date()));
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}


	private static String SQL_FIND2 = "select * from channel_nodedefinition where template_id=? Order By create_time asc";

	public ChannelNodeDefinitionData[] find(String templateID)
			throws SQLException {
		PreparedStatement statement = null;
        try {
        	statement = connection.prepareStatement(SQL_FIND2);
        	statement.setString(1, templateID);          
            return this.retrieve(statement, SQL_FIND2);
        } finally {
            this.close(statement);
        }
	}

	private ChannelNodeDefinitionData[] retrieve(PreparedStatement statement, String sql)
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
		ArrayList<ChannelNodeDefinitionData> groups = new ArrayList<ChannelNodeDefinitionData>();
		while(rs.next()) {
			ChannelNodeDefinitionData	datum= new ChannelNodeDefinitionData();		
		this.populate(rs, datum);
		groups.add(datum);
		}
		rs.close();
		statement.close();
		return (ChannelNodeDefinitionData[])groups.toArray(new ChannelNodeDefinitionData[groups.size()]);
	}

	private void populate(ResultSet rs, ChannelNodeDefinitionData data)
			throws SQLException {
		data.setTemplateID(rs.getString("TEMPLATE_ID"));
		data.setChannelID(rs.getString("CHANNEL_ID"));
		data.setNodeDdefinitionID(rs.getString("NODE_DEFINITION_ID"));

	}

	

	private String SQL_DELETE_ALL="delete from channel_nodedefinition t where t.template_id=? and t.channel_id=?";
	public void deleteAll(String templateID, String channelID)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_DELETE_ALL);
			statement.setString(1, templateID);
			statement.setString(2, channelID);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			this.close(statement);
		}
		
	}
    

	
	
}
