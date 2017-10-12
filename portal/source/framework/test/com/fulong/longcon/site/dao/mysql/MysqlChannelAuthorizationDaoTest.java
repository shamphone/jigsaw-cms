package com.fulong.longcon.site.dao.mysql;

import java.sql.SQLException;

import com.fulong.longcon.site.dao.ChannelAuthorizationDao;
import com.fulong.longcon.site.data.ChannelAuthorizationData;

import junit.framework.Assert;
/**
 * 
 * @author LJY
 *
 */

public class MysqlChannelAuthorizationDaoTest extends MysqlSiteDaoTestCase {
	private ChannelAuthorizationDao dao=null;
	private ChannelAuthorizationData data =new ChannelAuthorizationData();
	private String admin="1000000000000";
	private String channelID="test.channel";
	private String templateID="test.template";
	private String principal="no-properties-scheme";

	
	protected void setUp() throws Exception {
		super.setUp();
		dao=(ChannelAuthorizationDao)this.factory.getDao(ChannelAuthorizationDao.class);
		 data.setChannelID(channelID);
         data.setTemplateID(templateID);
         data.setPrincipalID(principal);
         data.setPrincipalType(1);
		try {
			dao.create(data);
		} catch (SQLException e) {
			factory.rollback();
		}
		
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(data);			
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}



	public final void testDeleteStringString() {		
		try {
			dao.delete(templateID, channelID);
			ChannelAuthorizationData[] rs=dao.findByTemplateID(templateID);
			Assert.assertEquals("passed", rs.length,0);

		} catch (SQLException e) {
          factory.rollback();
		}
	}

	public final void testFindByTemplateID() throws SQLException {
		ChannelAuthorizationData[] rs=dao.findByTemplateID(templateID);
		Assert.assertEquals("passed", rs[0].getChannelID(),this.channelID);
		rs=dao.findByChannelID(templateID, channelID);
		Assert.assertEquals("passed", rs[0].getChannelID(),this.channelID);
		
	}

	public final void testFindByPrincipalID() throws SQLException {
		ChannelAuthorizationData rs=dao.findByPrincipalID(templateID, channelID, admin);
		Assert.assertEquals("passed", rs.getChannelID(),this.channelID);
		String groupID="principal-scheme";			
		rs=dao.findByGroupIDAndTemplateIDAndChannelID(groupID, templateID, channelID);
		Assert.assertEquals("passed", rs.getChannelID(),this.channelID);
		ChannelAuthorizationData[] res=dao.findByGroupIDAndTemplateID(groupID, templateID);
		Assert.assertEquals("passed", res[0].getChannelID(),this.channelID);	
	}


}
