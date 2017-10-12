package com.fulong.longcon.site.dao.mysql;

import com.fulong.longcon.site.SiteObject;
import com.fulong.longcon.site.dao.SiteDao;
import com.fulong.longcon.site.data.SiteData;
import java.sql.SQLException;
import junit.framework.Assert;;
/**
 * 
 * @author LJY
 *
 */
public class MysqlSiteDaoTest extends MysqlSiteDaoTestCase {
	private SiteDao dao=null;
	private SiteData data=new SiteData();
	private String ID="test.pkid";

	protected void setUp() throws Exception {
		super.setUp();
		dao=(SiteDao)this.factory.getDao(SiteDao.class);
		data.setDomain("test.domain");
		data.setName("test.domain");
		data.setOwnerID("test.owner");
		data.setOwnerType(1);
		data.setTemplateID("test.templateID");
		data.setCategoryID("test.categoryID");
		data.setState(SiteObject.State.CREATED);
		data.setPkid(ID);
		try{
			dao.insert(data);
		}catch(SQLException e){
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try{
			dao.delete(ID);
		}catch(SQLException e){
			factory.rollback();
		}
		super.tearDown();
	}

	public final void testFindByID() throws SQLException {
		SiteData rs= dao.findByID(ID);
		Assert.assertEquals("passed", rs.getPkid(), ID);
		rs=dao.findByOwner("test.owner");
		Assert.assertEquals("passed", rs.getPkid(), ID);
		int res=dao.getSitesCount();
		Assert.assertEquals("passed", res, 2);//系统默认一个加测试数据库一个
		res=dao.getSitesCountByTemplate("test.categoryID");
		Assert.assertEquals("passed", res, 1);
		res=dao.getSitesCountByTemplate("test.templateID");
		Assert.assertEquals("passed", res, 1);
	}

	public final void testUpdate() {
		data.setDisplayName("test.name.desc");
		try{
		dao.update(data);
		SiteData rs= dao.findByID(ID);
		Assert.assertEquals("passed", rs.getDisplayName(), "test.name.desc");
		}catch(SQLException e){
			factory.rollback();
		}
	}

}
