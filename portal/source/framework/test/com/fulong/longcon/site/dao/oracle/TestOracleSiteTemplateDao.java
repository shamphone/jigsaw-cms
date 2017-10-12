package com.fulong.longcon.site.dao.oracle;

import junit.framework.*;
import java.sql.*;
import com.fulong.common.dao.*;
import com.fulong.longcon.site.data.*;
import com.fulong.longcon.site.dao.SiteTemplateDao;
import java.util.Date;

public class TestOracleSiteTemplateDao
    extends DaoTestCase {
    private SiteTemplateDao oracleSiteTemplateDao = null;
    private SiteTemplateData data = new SiteTemplateData();

    protected void setUp() throws Exception {
        super.setUp();
        oracleSiteTemplateDao = (SiteTemplateDao)this.factory.getDao(
            SiteTemplateDao.class);
        /**
         * 创建测试用siteTemplate
         */
        data.setPkid("0311310202");
        data.setCategoryID("0311310102");
        data.setCreateDate(new Date());
        data.setState("created");
        oracleSiteTemplateDao.insert(data);
    }

    protected void tearDown() throws Exception {
        //删除测试用siteTemplate
        oracleSiteTemplateDao.delete(data.getPkid());
        //判断是否已经删除
        this.assertNull(oracleSiteTemplateDao.findByID(data.getPkid()));
        oracleSiteTemplateDao = null;
        super.tearDown();
    }
    /**
     * 测试update和findByID
     * @throws SQLException
     */
    public void testFindByID() throws SQLException {
        String newName="新颖模版";
        String ID = data.getPkid();
        data.setName(newName);
        oracleSiteTemplateDao.update(data);
        SiteTemplateData actualReturn = oracleSiteTemplateDao.findByID(ID);
        assertEquals("return value", newName, actualReturn.getName());
    }

    public void testSearch() throws SQLException {

        String query = "select * from site_template where pkid = ? ";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter(1, data.getPkid());
        int fromIndex = 0;
        int number = 1;
        SiteTemplateData[] actualReturn = oracleSiteTemplateDao.search(query,
            parameters, fromIndex, number);
        assertEquals("return value", data.getPkid(), actualReturn[0].getPkid());
    }

    public void testCountResultNum() throws SQLException {
        String query = "select count(*) from SITE_TEMPLATE where CATEGORY_ID =?";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0]= new SQLParameter(1, data.getCategoryID());
        long expectedReturn = 1;
        long actualReturn = oracleSiteTemplateDao.countResultNum(query,
            parameters);
        assertEquals("return value", expectedReturn, actualReturn);
    }
}
