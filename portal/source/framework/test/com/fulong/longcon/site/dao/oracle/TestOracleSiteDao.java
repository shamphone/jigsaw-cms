package com.fulong.longcon.site.dao.oracle;

import junit.framework.*;
import java.sql.*;
import com.fulong.common.dao.*;
import com.fulong.longcon.site.data.*;
import java.util.Date;
import com.fulong.longcon.site.dao.SiteDao;
import com.fulong.longcon.site.data.SiteData;

public class TestOracleSiteDao
    extends DaoTestCase {
    private SiteDao oracleSiteDao = null;
    private SiteData data = new SiteData();

    protected void setUp() throws Exception {
        super.setUp();
        oracleSiteDao = (SiteDao)this.factory.getDao(SiteDao.class);
        /**
         * 创建测试用site
         */
        data.setPkid("20070906");
        data.setName("我的地盘");
        data.setOwnerID("0311310208");
        data.setOwnerType(1);
        data.setState("created");
        data.setCreateDate(new Date());
        data.setTemplateID("20070906");
        oracleSiteDao.insert(data);
  }

    protected void tearDown() throws Exception {
        //删除测试用site
        oracleSiteDao.delete(data.getPkid());
        //判断是否已经删除
        this.assertNull(oracleSiteDao.findByID(data.getPkid()));
        oracleSiteDao = null;
        super.tearDown();
    }

    public void testCountResultNum() throws SQLException {
        String query = "select count(*) from site where pkid = ?";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter(1, data.getPkid());
        long expectedReturn = 1;
        long actualReturn = oracleSiteDao.countResultNum(query, parameters);
        assertEquals("return value", expectedReturn, actualReturn);
    }

    public void testFindByID() throws SQLException {
        String ID = data.getPkid();
        SiteData actualReturn = oracleSiteDao.findByID(ID);
        assertEquals("return value", data.getPkid(), actualReturn.getPkid());
    }

    public void testFindByOwner() throws SQLException {
        String ownerID = data.getOwnerID();
        String expectReturn = data.getPkid();
        SiteData actualReturn = oracleSiteDao.findByOwner(ownerID);
        assertEquals("return value", expectReturn, actualReturn.getPkid());
    }

    public void testSearch() throws SQLException {
        String query = "select * from site where OWNER_ID = ?";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter(1, data.getOwnerID());
        int fromIndex = 0;
        int number = 1;
        SiteData expectedReturn = oracleSiteDao.findByOwner(data.getOwnerID());
        SiteData[] actualReturn = oracleSiteDao.search(query, parameters,
            fromIndex, number);
        assertEquals("return value", expectedReturn.getPkid(),
                     actualReturn[0].getPkid());
    }

    public void testUpdate() throws SQLException {
        String ID = data.getPkid();
        String newName="My world!!";
        data.setName(newName);
        oracleSiteDao.update(data);
        SiteData actualReturn = oracleSiteDao.findByID(ID);
        assertEquals("return value",newName , actualReturn.getName());
    }
}

