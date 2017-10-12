package com.fulong.longcon.site.dao.oracle;

import junit.framework.*;
import java.sql.*;
import java.util.Date;
import com.fulong.common.dao.*;
import com.fulong.longcon.site.data.*;
import com.fulong.longcon.site.dao.ChannelDao;
import com.fulong.common.dao.SQLParameter;

public class TestOracleChannelDao
    extends DaoTestCase {
    private ChannelDao oracleChannelDao = null;
    private ChannelData channelData = new ChannelData();

    protected void setUp() throws Exception {
        super.setUp();
        oracleChannelDao = (ChannelDao)this.factory.getDao(ChannelDao.class);
        /**
         * 创建channelData用于测试
         */
        channelData.setPkid("123456789");
        channelData.setTemplateID("123456789");
        channelData.setType("1");
        channelData.setName("SPORTS-WORLD");
        channelData.setState("published");
        channelData.setCreateDate(new Date());
        channelData.setStartDate(new Date());
        oracleChannelDao.insert(channelData);
    }

    protected void tearDown() throws Exception {
        //删除创建的channelData
        this.oracleChannelDao.delete(channelData.getPkid());
        this.assertNull(oracleChannelDao.findByID(channelData.getPkid()));
        oracleChannelDao = null;
        super.tearDown();
    }

    public void testFindByID() throws SQLException {
        String ID = channelData.getPkid();
        ChannelData actualReturn = oracleChannelDao.findByID(ID);
        assertEquals("return value", channelData.getPkid(),
                     actualReturn.getPkid());
    }

    public void testCountResultNum() throws SQLException {
        String query = "select count(*) from CHANNEL where pkid =? ";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter();
        parameters[0].setType(1);
        parameters[0].setValue("123456789");
        long expectedReturn = 1;
        long actualReturn = oracleChannelDao.countResultNum(query, parameters);
        assertEquals("return value", expectedReturn, actualReturn);
    }

    public void testFindByTemplateAndID() throws SQLException {
        String templateID = channelData.getTemplateID();
        String ID = channelData.getPkid();
        ChannelData actualReturn = oracleChannelDao.findByTemplateAndID(
            templateID, ID);
        assertEquals("return value", channelData.getPkid(),
                     actualReturn.getPkid());
    }

    public void testSearch() throws SQLException {
        String query = "select * from CHANNEL c where c.pkid = ?";
        SQLParameter[] parameters = new SQLParameter[1];
        parameters[0] = new SQLParameter();
        parameters[0].setType(1);
        parameters[0].setValue("123456789");
        int fromIndex = 0;
        int number = 1;
        ChannelData expectedReturn = channelData;
        ChannelData[] actualReturn = oracleChannelDao.search(query, parameters,
            fromIndex, number);
        assertEquals("return value", expectedReturn.getName(),
                     actualReturn[0].getName());
    }

    public void testUpdate() throws SQLException {
        channelData.setTemplateID("20070906");
        channelData.setType("4");
        channelData.setName("NEWS_WORLD");
        channelData.setState("published");
        channelData.setCreateDate(new Date());
        channelData.setStartDate(new Date());
        oracleChannelDao.update(channelData);

        String ID = channelData.getPkid();

        ChannelData actualReturn = oracleChannelDao.findByID(ID);
        assertEquals("return value", channelData.getTemplateID(),
                     actualReturn.getTemplateID());
    }
}

