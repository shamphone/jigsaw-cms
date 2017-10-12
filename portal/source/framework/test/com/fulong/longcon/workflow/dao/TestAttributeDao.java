package com.fulong.longcon.workflow.dao;

import junit.framework.*;
import java.sql.*;
import com.fulong.longcon.workflow.data.*;

public class TestAttributeDao extends DaoTestCase {
    private AttributeDao attributeDao = null;

    public TestAttributeDao() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected void setUp() throws Exception {
        super.setUp();
        attributeDao = (AttributeDao)this.factory.getDao(AttributeDao.class);
    }

    protected void tearDown() throws Exception {
        attributeDao = null;
        super.tearDown();
    }

    public void testDelete() throws SQLException {
        AttributeData data = new AttributeData();
        data.setName("description");
        data.setType(1);
        data.setValue("会员管理系统申请流程");
        data.setObjectId("2341637516320");
        attributeDao.insert(data);


        AttributeData[] dataset = attributeDao.findByPKID("2341637516320");
        attributeDao.delete(dataset[0].getObjectId());
        /**@todo fill in the test code*/
    }

    public void testFindByPKID() throws SQLException {
        String pkid = "2341637516320";
        AttributeData[] expectedReturn = null;
        AttributeData[] actualReturn = attributeDao.findByPKID(pkid);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testInsert() throws SQLException {
        AttributeData data = new AttributeData();
        data.setName("description");
        data.setType(1);
        data.setValue("会员管理系统申请流程");
        data.setObjectId("2341637516320");
        attributeDao.insert(data);
        /**@todo fill in the test code*/
    }

    public void testPopulate() throws SQLException {
        ResultSet rs = null;
        AttributeData data = null;
        attributeDao.populate(rs, data);
        /**@todo fill in the test code*/
    }

    public void testRetrieve() throws SQLException {
        PreparedStatement statement = null;
        String sql = "";
        AttributeData[] expectedReturn = null;
        AttributeData[] actualReturn = attributeDao.retrieve(statement, sql);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testUpdate() throws SQLException {
        AttributeData[] data = attributeDao.findByPKID("2340773766374");
        data[0].setValue("内容管理系统的内容发布");
        data[0].setType(2);
        data[0].setName("value1");

        attributeDao.update(data[0]);
        /**@todo fill in the test code*/
    }


    private void jbInit() throws Exception {
    }
}

