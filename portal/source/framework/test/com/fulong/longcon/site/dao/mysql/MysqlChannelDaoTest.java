package com.fulong.longcon.site.dao.mysql;

import java.sql.SQLException;
import java.util.Date;

import com.fulong.longcon.site.SiteObject;
import com.fulong.longcon.site.dao.ChannelDao;
import com.fulong.longcon.site.data.ChannelData;

import junit.framework.Assert;

/**
 * 
 * @author LJY
 * 
 */
public class MysqlChannelDaoTest extends MysqlSiteDaoTestCase {
	private ChannelDao dao = null;
	private ChannelData grandfather = new ChannelData();
	private ChannelData father = new ChannelData();
	private ChannelData son = new ChannelData();

	protected void setUp() throws Exception {
		super.setUp();
		dao = (ChannelDao) factory.getDao(ChannelDao.class);
		try {
			grandfather.setPkid("test.grandfather");
			grandfather.setName("test.name");
			grandfather.setCreateDate(new Date());
			grandfather.setTemplateID("test.template");
			grandfather.setType("test");
			grandfather.setState(SiteObject.State.CREATED);
			dao.insert(grandfather);
		} catch (SQLException e) {
			factory.rollback();
		}

	}

	protected void tearDown() throws Exception {
		try {
			dao.delete("test.grandfather");
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	public final void testFindByID() throws SQLException {
		ChannelData rs=dao.findByID("test.grandfather");
		Assert.assertEquals("passed", rs.getPkid(),"test.grandfather");
		rs=dao.findByTemplateAndID("test.template", "test.grandfather");
		Assert.assertEquals("passed", rs.getPkid(),"test.grandfather");
		ChannelData[] res=dao.findByTemplate("test.template");
		Assert.assertEquals("passed", res[0].getPkid(),"test.grandfather");
		rs=dao.findByTemplateAndName("test.template", "test.name");
		Assert.assertEquals("passed", rs.getPkid(),"test.grandfather");
	}

	public final void testFindBindingChannel() {
		grandfather.setNodeID("test.node");
		try {
			dao.update(grandfather);
			ChannelData rs=dao.findBindingChannel("test.template", "test.node", "test");
			Assert.assertEquals("passed", rs.getPkid(),"test.grandfather");
			rs=dao.findRootChannelByTemplate("test.template");
			Assert.assertEquals("passed", rs.getPkid(),"test.grandfather");
		} catch (SQLException e) {
			factory.rollback();
		}		
	}

	public final void testDeleteStringString() {
		father.setPkid("test.father");
		father.setParentID("test.grandfather");
		father.setName("test.name");
		father.setCreateDate(new Date());
		father.setTemplateID("test.template");
		father.setType("test");
		father.setState(SiteObject.State.CREATED);
		
		son.setPkid("test.son");
		son.setParentID("test.father");
		son.setName("test.name");
		son.setCreateDate(new Date());
		son.setTemplateID("test.template");
		son.setType("test");
		son.setState(SiteObject.State.CREATED);
		
		try {
			dao.insert(father);
			dao.insert(son);
			ChannelData[] res=dao.findByTemplate("test.template");
			Assert.assertEquals("passed", res.length,3);
		} catch (SQLException e) {
			factory.rollback();
		}
		try {
			dao.delete("test.template", "test.grandfather");
			ChannelData[] res=dao.findByTemplate("test.template");
			Assert.assertEquals("passed", res.length,0);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

}
