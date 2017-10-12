package com.fulong.longcon.site.dao.mysql;

import java.sql.SQLException;
import java.util.Date;

import com.fulong.longcon.site.SiteObject;
import com.fulong.longcon.site.dao.SiteCategoryDao;
import com.fulong.longcon.site.data.SiteCategoryData;
import junit.framework.Assert;;
/**
 * 
 * @author LJY
 *
 */

public class MysqlSiteCategoryDaoTest extends MysqlSiteDaoTestCase {
	private SiteCategoryDao dao=null;
	private SiteCategoryData data =new SiteCategoryData();
	private String ID="test.pkid";

	protected void setUp() throws Exception {
		super.setUp();
		dao=(SiteCategoryDao)this.factory.getDao(SiteCategoryDao.class);
		Date date = new Date();
		data.setCreateDate(date);
		data.setName("test.name");
		data.setPkid(ID);
		data.setState(SiteObject.State.CREATED);
		try{
			dao.insert(data);
		}catch(SQLException E){
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try{
			dao.delete(ID);
		}catch(SQLException E){
			factory.rollback();
		}
		super.tearDown();
	}

	public final void testFindByID() throws SQLException {
		SiteCategoryData rs=dao.findByID(ID);
		Assert.assertEquals("passed", rs.getPkid(),ID);
		SiteCategoryData[] res=dao.getAllCategories();
		Assert.assertEquals("passed", res.length,3);//系统默认2个加上一个测试的。
		
	}	

	public final void testUpdate() {
		data.setDisplayName("test.desc");
		try{
			dao.update(data);
			SiteCategoryData rs=dao.findByID(ID);
			Assert.assertEquals("passed", rs.getDisplayName(),"test.desc");			
		}catch(SQLException E){
			factory.rollback();
		}
	}


}
