package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.security.data.MembershipData;
import java.util.Date;
import java.sql.*;
import com.fulong.longcon.security.data.*;

/**
 *
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport System</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 * 对MembershipDao建立对应的单元测试类
 */
public class TestMembershipDao extends DaoTestCase {
    private MembershipDao jdbcMembershipDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcMembershipDao = (MembershipDao)this.factory.getDao(MembershipDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcMembershipDao = null;
        super.tearDown();
    }

    /**
     * 根据parentid字段查询相应的MEMBERSHIP表中的记录
     *
     */
    public void testCountByParent() {
        String groupID = "";
        int expectedReturn = 0;
        int actualReturn = 0;
        try {
            actualReturn = jdbcMembershipDao.countByParent(groupID);
        } catch (SQLException ex1) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

        //插入一个MembershipData的记录
        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        try {
            jdbcMembershipDao.create(data);

            //查询出刚才插入到数据库的MembershipData的记录
            actualReturn = jdbcMembershipDao.countByParent(parentID);
            //断言，记录数应该为1
            assertEquals("return value", 1, actualReturn);
            //删除刚才插入的记录
            jdbcMembershipDao.deleteByItem(itemID);
            //查询出刚才插入到数据库的MembershipData的记录
            actualReturn = jdbcMembershipDao.countByParent(parentID);
            //断言，记录数应该为0
            assertEquals("return value", 0, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testCountByParentAndStatus() {
        String groupID = "";
        String status = "";
        int expectedReturn = 0;
        int actualReturn = 0;
        try {
            actualReturn = jdbcMembershipDao.countByParentAndStatus(groupID,
                status);

            assertEquals("return value", expectedReturn, actualReturn);

            MembershipData data = new MembershipData();
            String parentID = "parentID";
            String itemID = "itemID";
            int itemType = 2;
            int role = 1;
            status = "status";
            data.setStatus(status);
            data.setParentId(parentID);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemType);
            jdbcMembershipDao.create(data);

            actualReturn = jdbcMembershipDao.countByParentAndStatus(parentID,
                status);
            assertEquals("return value", 1, actualReturn);

            jdbcMembershipDao.deleteByItem(itemID);
            actualReturn = jdbcMembershipDao.countByParentAndStatus(parentID,
                status);
            assertEquals("return value", 0, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testCountResultNum() {
        String query =
            "select count(*) from membership t where t.itemid='itemID'";
        SQLParameter[] parameters = null;
        long expectedReturn = 0L;
        long actualReturn = 0L;
        try {
            actualReturn = jdbcMembershipDao.countResultNum(query, parameters);

            assertEquals("return value", expectedReturn, actualReturn);

            MembershipData data = new MembershipData();
            String parentID = "parentID";
            String itemID = "itemID";
            int itemType = 2;
            int role = 1;
            String status = "status";
            data.setStatus(status);
            data.setParentId(parentID);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemType);
            jdbcMembershipDao.create(data);

            actualReturn = jdbcMembershipDao.countResultNum(query, parameters);

            assertEquals("return value", 1, actualReturn);

            jdbcMembershipDao.deleteByItem(itemID);
        } catch (SQLException ex) {
        }
    }

    public void testCreate() {
        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        data.setCreateDate(new Date());
        data.setApplyDate(new Date());
        try {
            jdbcMembershipDao.create(data);

            MembershipData actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);
            assertEquals("return value", data, actualReturn);
            jdbcMembershipDao.deleteByItem(itemID);

            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testDelete() {
        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        try {
            jdbcMembershipDao.create(data);

            MembershipData actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);
            assertEquals("return value", data, actualReturn);
            jdbcMembershipDao.delete(parentID, itemID);

            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testDeleteByItem() {
        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        try {
            jdbcMembershipDao.create(data);

            MembershipData actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);
            assertEquals("return value", data, actualReturn);
            jdbcMembershipDao.deleteByItem(itemID);

            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testDeleteByParent() {
        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        try {
            jdbcMembershipDao.create(data);

            MembershipData actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);
            assertEquals("return value", data, actualReturn);
            jdbcMembershipDao.deleteByParent(parentID);

            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testFindByGroup() {
        String groupID = "";
        MembershipData[] expectedReturn = null;
        MembershipData[] actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.findByGroup(groupID,
                0, 20);

            assertEquals("return value", 0, actualReturn.length);

            MembershipData data = new MembershipData();
            String parentID = "parentID";
            String itemID = "itemID";
            int role = 1;
            data.setParentId(parentID);
            data.setItemId(itemID);
            data.setRole(role);
            jdbcMembershipDao.create(data);
            actualReturn = jdbcMembershipDao.findByGroup(parentID, 0, 20);
            assertEquals("return value", 1, actualReturn.length);
            jdbcMembershipDao.deleteByParent(parentID);

            actualReturn = jdbcMembershipDao.findByGroup(parentID, 0, 20);
            assertEquals("return value", 0, actualReturn.length);
        } catch (SQLException ex) {
        }
    }

    public void testFindByItemAndType() {
        String itemID = "";
        int type = 0;
        MembershipData[] expectedReturn = null;
        MembershipData[] actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.findByItemAndType(
                itemID, type);

            assertEquals("return value", 0, actualReturn.length);

            MembershipData data = new MembershipData();
            String parentID = "parentID";
            itemID = "itemID";
            int role = 1;
            int itemType = 1;
            data.setParentId(parentID);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemType);

            jdbcMembershipDao.create(data);
            actualReturn = jdbcMembershipDao.findByItemAndType(
                itemID, itemType);
            assertEquals("return value", 1, actualReturn.length);
            jdbcMembershipDao.deleteByParent(parentID);

            data = jdbcMembershipDao.findByParentAndItem(parentID, itemID);
            assertEquals("return value", null, data);
        } catch (SQLException ex) {
        }
    }

    public void testFindByParentAndItem() {
        String parentID = "";
        String itemID = "";
        MembershipData expectedReturn = null;
        MembershipData actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);

            assertEquals("return value", expectedReturn, actualReturn);

            MembershipData data = new MembershipData();
            parentID = "parentID";
            itemID = "itemID";
            int role = 1;
            int itemType = 1;
            data.setParentId(parentID);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemType);

            jdbcMembershipDao.create(data);
            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);

            assertEquals("return value", data, actualReturn);
            jdbcMembershipDao.deleteByParent(parentID);

            data = jdbcMembershipDao.findByParentAndItem(parentID, itemID);

            assertEquals("return value", null, data);
        } catch (SQLException ex) {
        }
    }

    public void testFindByParentAndItemtype() {
        String parentid = "";
        int itemtype = 0;
        MembershipData[] expectedReturn = null;
        MembershipData[] actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.
                findByParentAndItemtype(parentid,
                                        itemtype);

            assertEquals("return value", 0, actualReturn.length);

            MembershipData data = new MembershipData();
            parentid = "parentID";
            String itemID = "itemID";
            int role = 1;
            itemtype = 1;
            data.setParentId(parentid);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemtype);

            jdbcMembershipDao.create(data);
            actualReturn = jdbcMembershipDao.findByParentAndItemtype(parentid,
                itemtype);

            assertEquals("return value", 1, actualReturn.length);
            jdbcMembershipDao.deleteByParent(parentid);

            actualReturn = jdbcMembershipDao.findByParentAndItemtype(parentid,
                itemtype);

            assertEquals("return value", 0, actualReturn.length);
        } catch (SQLException ex) {
        }
    }

    public void testFindClientGroupsByProposerAndType() {
        String orgID = "";
        int type = 0;
        MembershipData[] expectedReturn = null;
        MembershipData[] actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.
                findClientGroupsByProposerAndType(orgID,
                                                  type);
        } catch (SQLException ex) {
        }
        assertEquals("return value", 0, actualReturn.length);

    }

    public void testFindParentGroupId() {
        String itemId = "";
        String expectedReturn = null;
        String actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.findParentGroupId(itemId);

            assertEquals("return value", expectedReturn, actualReturn);

            String parentid = "";
            int itemtype = 0;

            MembershipData data = new MembershipData();
            parentid = "parentID";
            String itemID = "itemID";
            int role = 1;
            itemtype = 2;
            data.setParentId(parentid);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemtype);

            jdbcMembershipDao.create(data);
            actualReturn = jdbcMembershipDao.findParentGroupId(itemID);

            assertEquals("return value", parentid, actualReturn);
            jdbcMembershipDao.deleteByParent(parentid);

            actualReturn = jdbcMembershipDao.findParentGroupId(itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testIsChild() {

        String parentId = "";
        String childId = "";
        boolean expectedReturn = false;
        boolean actualReturn = false;
        try {
            actualReturn = jdbcMembershipDao.isChild(parentId, childId);

            assertEquals("return value", expectedReturn, actualReturn);

            MembershipData data = new MembershipData();
            parentId = "parentID";
            childId = "itemID";
            int role = 1;

            data.setParentId(parentId);
            data.setItemId(childId);
            data.setRole(role);

            jdbcMembershipDao.create(data);
            expectedReturn = true;
            actualReturn = jdbcMembershipDao.isChild(parentId, childId);

            assertEquals("return value", expectedReturn, actualReturn);

            jdbcMembershipDao.deleteByParent(parentId);
        } catch (SQLException ex) {
        }
    }

    public void testSearch() {
        String query = "select * from membership t where t.itemid='itemID'";
        SQLParameter[] parameters = null;
        MembershipData[] expectedReturn = null;
        MembershipData[] actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.search(query,
                parameters);
        } catch (SQLException ex1) {
        }
        assertEquals("return value", 0, actualReturn.length);

        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int itemType = 2;
        int role = 1;
        String status = "status";
        data.setStatus(status);
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        data.setItemType(itemType);
        try {
            jdbcMembershipDao.create(data);

            actualReturn = jdbcMembershipDao.search(query,
                parameters);
            assertEquals("return value", 1, actualReturn.length);
            assertEquals("return value", itemID, actualReturn[0].getItemId());

            jdbcMembershipDao.deleteByItem(itemID);
        } catch (SQLException ex) {
        }
    }

    public void testSearch1() {
        String query = "select * from membership t where t.itemid='itemID'";
        SQLParameter[] parameters = null;
        int fromIndex = 0;
        int number = 0;

        MembershipData[] expectedReturn = null;
        MembershipData[] actualReturn = null;
        try {
            actualReturn = jdbcMembershipDao.search(query,
                parameters, fromIndex, number);

            assertEquals("return value", 0, actualReturn.length);

            MembershipData data = new MembershipData();
            String parentID = "parentID";
            String itemID = "itemID";
            int itemType = 2;
            int role = 1;
            String status = "status";
            data.setStatus(status);
            data.setParentId(parentID);
            data.setItemId(itemID);
            data.setRole(role);
            data.setItemType(itemType);
            jdbcMembershipDao.create(data);
            fromIndex = 0;
            number = 5;
            actualReturn = jdbcMembershipDao.search(query,
                parameters, fromIndex, number);
            assertEquals("return value", 1, actualReturn.length);
            assertEquals("return value", itemID, actualReturn[0].getItemId());

            jdbcMembershipDao.deleteByItem(itemID);
        } catch (SQLException ex) {
        }
    }

    public void testUpdate() {

        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int itemType = 2;
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        data.setItemType(itemType);
        try {
            jdbcMembershipDao.create(data);

            MembershipData actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);
            assertEquals("return value", data.getParentId(),
                         actualReturn.getParentId());

            String status = "status";
            data.setStatus(status);

            jdbcMembershipDao.update(data);
            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);

            assertEquals("return value", status, actualReturn.getStatus());

            jdbcMembershipDao.deleteByItem(itemID);
            actualReturn = jdbcMembershipDao.findByParentAndItem(parentID,
                itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testUpdateParentId() {
        MembershipData data = new MembershipData();
        String parentID = "parentID";
        String itemID = "itemID";
        int itemType = 2;
        int role = 1;
        data.setParentId(parentID);
        data.setItemId(itemID);
        data.setRole(role);
        data.setItemType(itemType);
        try {
            jdbcMembershipDao.create(data);

            MembershipData actualReturn = jdbcMembershipDao.findByParentAndItem(
                parentID, itemID);
            assertEquals("return value", data.getParentId(),
                         actualReturn.getParentId());

            String parentIdUpdate = "parentIdUpdate";
            jdbcMembershipDao.updateParentId(parentIdUpdate, itemID);
            actualReturn = jdbcMembershipDao.findByParentAndItem(parentIdUpdate,
                itemID);

            assertEquals("return value", parentIdUpdate,
                         actualReturn.getParentId());

            jdbcMembershipDao.deleteByItem(itemID);
            actualReturn = jdbcMembershipDao.findByParentAndItem(parentIdUpdate,
                itemID);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

}
