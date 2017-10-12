package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;
import com.fulong.longcon.security.data.GroupData;
import java.sql.*;
import com.fulong.longcon.security.data.*;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public class TestGroupDao extends DaoTestCase {
    private GroupDao jdbcGroupDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcGroupDao = (GroupDao)this.factory.getDao(GroupDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcGroupDao = null;
        super.tearDown();
    }

    public void testCountAvailableGroup() {
        String serName = "";
        String serDescription = "";
        String itemid = "";
        String orgid = "";
        long expectedReturn = 0L;
        long actualReturn = 0L;
        try {
            actualReturn = jdbcGroupDao.countAvailableGroup(serName,
                serDescription, itemid, orgid);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testCreate() {
        GroupData data = new GroupData();
        String groupName = "groupName20070322";
        data.setGroupName(groupName);
        try {
            jdbcGroupDao.create(data);

            String ID = data.getId();

            GroupData actualReturn = jdbcGroupDao.findByPrimaryKey(ID);
            assertEquals("return value", ID, actualReturn.getId());

            jdbcGroupDao.delete(data.getId());
            actualReturn = jdbcGroupDao.findByPrimaryKey(data.getId());

            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testDelete() {
        GroupData data = new GroupData();
        String groupName = "groupName20070322";
        data.setGroupName(groupName);
        try {
            jdbcGroupDao.create(data);

            String ID = data.getId();

            GroupData actualReturn = jdbcGroupDao.findByPrimaryKey(ID);
            assertEquals("return value", ID, actualReturn.getId());

            jdbcGroupDao.delete(data.getId());
            actualReturn = jdbcGroupDao.findByPrimaryKey(data.getId());

            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testFindAvailableGroup() {
        String serName = "";
        String serDescription = "";
        String itemid = "";
        String orgid = "";
        int index = 0;
        int number = 0;
        GroupData[] expectedReturn = null;
        GroupData[] actualReturn = null;
        try {
            actualReturn = jdbcGroupDao.findAvailableGroup(serName,
                serDescription, itemid, orgid, index, number);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testFindByMembership() {
        String memberID = "";
        GroupData[] expectedReturn = null;
        GroupData[] actualReturn = null;
        try {
            actualReturn = jdbcGroupDao.findByMembership(memberID);
        } catch (SQLException ex) {
        }
        assertEquals("return value", memberID, actualReturn[0].getId());

    }

    public void testFindByOrganization() {

        String orgID = "";
        GroupData[] expectedReturn = new GroupData[0];
        GroupData[] actualReturn = null;
        try {
            actualReturn = jdbcGroupDao.findByOrganization(orgID);

            assertEquals("return value", expectedReturn.length,
                         actualReturn.length);

            GroupData data = new GroupData();
            orgID = "orgID20070322";
            data.setOrgId(orgID);
            jdbcGroupDao.create(data);
            String ID = data.getId();

            GroupData actual = jdbcGroupDao.findByPrimaryKey(ID);
            assertEquals("return value", ID, actual.getId());

            jdbcGroupDao.delete(ID);
            actual = jdbcGroupDao.findByPrimaryKey(ID);

            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testFindByParent() {
        String parentId = "";
        GroupData[] expectedReturn = new GroupData[0];
        GroupData[] actualReturn = null;
        try {
            actualReturn = jdbcGroupDao.findByParent(parentId);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn.length, actualReturn.length);

    }

    public void testFindByPrimaryKey() {
        GroupData data = new GroupData();
        String groupName = "groupName20070322";
        data.setGroupName(groupName);
        try {
            jdbcGroupDao.create(data);

            String ID = data.getId();

            GroupData actualReturn = jdbcGroupDao.findByPrimaryKey(ID);
            assertEquals("return value", ID, actualReturn.getId());

            jdbcGroupDao.delete(data.getId());
            actualReturn = jdbcGroupDao.findByPrimaryKey(data.getId());

            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testFindByServiceId() {
        String serviceId = "";
        GroupData[] expectedReturn = new GroupData[0];
        GroupData[] actualReturn = null;
        try {
            actualReturn = jdbcGroupDao.findByServiceId(serviceId);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn.length, actualReturn.length);

    }

    public void testUpdate() {
        GroupData data = new GroupData();
        String groupName = "groupName20070322";
        data.setGroupName(groupName);
        try {
            jdbcGroupDao.create(data);

            String ID = data.getId();

            GroupData actualReturn = jdbcGroupDao.findByPrimaryKey(ID);
            actualReturn.setGroupName("updatedGroupName");
            jdbcGroupDao.update(actualReturn);
            actualReturn = jdbcGroupDao.findByPrimaryKey(ID);
            assertEquals("return value", "updatedGroupName",
                         actualReturn.getGroupName());

            jdbcGroupDao.delete(data.getId());
            actualReturn = jdbcGroupDao.findByPrimaryKey(data.getId());

            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

}
