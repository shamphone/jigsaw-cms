package com.fulong.longcon.site.dao.mysql;

import com.fulong.longcon.site.dao.ChannelNodeDefinitionDao;
import com.fulong.longcon.site.data.ChannelNodeDefinitionData;

import junit.framework.Assert;
import java.sql.*;

/**
 * 
 * @author LJY
 * 
 */
public class MysqlChannelNodeDefinitionDaoTest extends MysqlSiteDaoTestCase {
	private ChannelNodeDefinitionDao dao = null;
	private ChannelNodeDefinitionData data = new ChannelNodeDefinitionData();
	private String templateID = "test.templateID";
	private String nodeDefinitionID = "test.definitionID";

	protected void setUp() throws Exception {
		super.setUp();
		dao = (ChannelNodeDefinitionDao) this.factory
				.getDao(ChannelNodeDefinitionDao.class);
		data.setTemplateID(templateID);
		data.setChannelID("test.channelID");
		data.setNodeDdefinitionID(nodeDefinitionID);
		try {
			dao.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		
		super.tearDown();
	}

	public final void testFind() throws SQLException {
		ChannelNodeDefinitionData[] rs=dao.find(templateID);
		Assert.assertEquals("passed", rs[0].getNodeDdefinitionID(),nodeDefinitionID);
		try {
			dao.delete(templateID, nodeDefinitionID);
		} catch (SQLException e) {
			factory.rollback();
		}
		
	}

	public final void testDeleteAll() throws SQLException {
		try {
			dao.deleteAll(templateID,"test.channelID");
		} catch (SQLException e) {
			factory.rollback();
		}
		ChannelNodeDefinitionData[] rs=dao.find(templateID);
		Assert.assertEquals("passed", rs.length,0);
	}

}
