package com.fulong.longcon.site.dao.oracle;

import junit.framework.*;
import java.sql.*;
import com.fulong.common.dao.*;
import com.fulong.longcon.site.data.*;
import com.fulong.longcon.site.dao.SiteCategoryDao;
import java.util.Date;

public class TestOracleSiteCategoryDao
    extends DaoTestCase {
    private SiteCategoryDao oracleSiteCategoryDao = null;
    private SiteCategoryData data = new SiteCategoryData();

    protected void setUp() throws Exception {
        super.setUp();
        oracleSiteCategoryDao = (SiteCategoryDao)this.factory.getDao(
            SiteCategoryDao.class);
        /**
         *创建测试用siteCategory
         */
        data.setPkid("0311310201");
        data.setCreateDate(new Date());
        data.setState("created");
        oracleSiteCategoryDao.insert(data);
    }

    protected void tearDown() throws Exception {
        //删除创建的测试用siteCategory
        oracleSiteCategoryDao.delete(data.getPkid());
        //判断是否已经删除
        this.assertNull(oracleSiteCategoryDao.findByID(data.getPkid()));
        oracleSiteCategoryDao = null;
        super.tearDown();
    }

    public void testCountResultNum() throws SQLException {
        String query = "select count(*) from SITE_CATEGORY  where pkid = ?";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter(1, "0311310201");
        long expectedReturn = 1;
        long actualReturn = oracleSiteCategoryDao.countResultNum(query,
            parameters);
        assertEquals("return value", expectedReturn, actualReturn);
    }

    public void testFindByID() throws SQLException {
        String ID = data.getPkid();
        SiteCategoryData actualReturn = oracleSiteCategoryDao.findByID(ID);
        assertEquals("return value", data.getState(), actualReturn.getState());
    }

    public void testGetAllCategories() throws SQLException {
        //查询所有分类
        SiteCategoryData[] actualReturn = oracleSiteCategoryDao.
            getAllCategories();
        assertTrue(actualReturn.length>0);
    }

    public void testSearch() throws SQLException {
        //给分类设置名称
        data.setName("科技网站");
        oracleSiteCategoryDao.update(data);
        String query = "select * from SITE_CATEGORY where pkid =?";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter(1, "0311310201");
        int fromIndex = 0;
        int number = 1;
        SiteCategoryData[] actualReturn = oracleSiteCategoryDao.search(query,
            parameters, fromIndex, number);
        assertEquals("return value", data.getName(), actualReturn[0].getName());
    }
}
