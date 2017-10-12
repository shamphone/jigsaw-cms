package com.fulong.longcon.site.dao.mysql;

import java.sql.SQLException;
import java.util.Date;

import com.fulong.longcon.site.SiteObject;
import com.fulong.longcon.site.dao.SiteTemplateDao;
import com.fulong.longcon.site.data.SiteTemplateData;
import junit.framework.Assert;

;
/**
 * 
 * @author LJY
 * 
 */
public class MysqlSiteTemplateDaoTest extends MysqlSiteDaoTestCase {
	private SiteTemplateDao dao = null;
	private SiteTemplateData data = new SiteTemplateData();
	private String ID = "test.pkid";

	protected void setUp() throws Exception {
		super.setUp();
		dao = (SiteTemplateDao) this.factory.getDao(SiteTemplateDao.class);
		Date date = new Date();
		data.setCategoryID("test.category");
		data.setName("test.name");
		data.setDisplayName("test.name");
		data.setCreateDate(date);
		data.setState(SiteObject.State.CREATED);
		data.setPkid(ID);
		try {
			dao.insert(data);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}		
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(ID);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}

		super.tearDown();
	}

	public final void testFindByID() throws SQLException {
		SiteTemplateData rs = dao.findByID(ID);
		Assert.assertEquals("passed", rs.getPkid(), ID);
		rs = dao.findByName("test.name");
		Assert.assertEquals("passed", rs.getPkid(), ID);
	}

	public final void testUpdate() {
		data.setName("test.name.update");
		try {
			dao.update(data);
			SiteTemplateData rs = dao.findByID(ID);
			Assert.assertEquals("passed", rs.getName(), "test.name.update");
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}
	}

	public final void testGetCountbyCategory() throws SQLException {
		int rs = dao.getCountbyCategory("test.category");
		Assert.assertEquals("passed", rs, 1);
	}

}
