package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;
import java.util.Map;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.security.data.OrganizationData;
import com.fulong.common.ReportData;
import java.sql.Date;
import com.fulong.longcon.security.data.GroupData;
import com.fulong.longcon.security.data.MembershipData;
import java.sql.*;
import com.fulong.longcon.security.data.*;
import com.fulong.common.*;

public class TestOrganizationDao extends DaoTestCase {
    private OrganizationDao jdbcOrganizationDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcOrganizationDao = (OrganizationDao)this.factory.getDao(
            OrganizationDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcOrganizationDao = null;
        super.tearDown();
    }

    /**
     * 此测试用例与数据库初始数据有关。
     * 以下按照当前数据库的测试
     */
    public void testCountByIndustry() {
        Map expectedReturn = null;
        Map actualReturn = null;
        try {
            actualReturn = jdbcOrganizationDao.countByIndustry();
        } catch (SQLException ex) {
        }
        assertEquals("return value", 20, actualReturn.size());

    }

    public void testCountResultNum() {
        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();

            String query = "select count(*) from organization t where t.id = ?";
            SQLParameter[] parameters = new SQLParameter[1];
            parameters[0] = new SQLParameter();
            parameters[0].setValue(ID);

            long expectedReturn = 1L;
            long actualReturn = jdbcOrganizationDao.countResultNum(query,
                parameters);
            assertEquals("return value", expectedReturn, actualReturn);

            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            expectedReturn = 0L;
            actualReturn = jdbcOrganizationDao.countResultNum(query,
                parameters);
            assertEquals("return value", expectedReturn, actualReturn);
        } catch (SQLException ex) {
        }
    }

    /**
     * 测试create方法，
     * 数据库中ID、CREATETIME字段不能为空，
     * 其中ID为自动生成
     * 因此在构建OrganizationData时，必须填充CREATETIME字段。
     * 否则，插入操作会报异常。
     */
    public void testCreate() {
        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();
            //查询出刚才插入的数据
            OrganizationData actualReturn = jdbcOrganizationDao.
                findByPrimaryKey(ID);
            //断言：查询得到的数据和插入数据对比
            assertEquals("return value", enterpriseName,
                         actualReturn.getEnterpriseName());
            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            //查询出刚才插入的数据
            actualReturn = jdbcOrganizationDao.findByPrimaryKey(ID);
            //断言：查询得到的数据和null对比
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    /**
     * copy Create()的测试用例。
     */
    public void testDelete() {
        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();
            //查询出刚才插入的数据
            OrganizationData actualReturn = jdbcOrganizationDao.
                findByPrimaryKey(ID);
            //断言：查询得到的数据和插入数据对比
            assertEquals("return value", enterpriseName,
                         actualReturn.getEnterpriseName());
            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            //查询出刚才插入的数据
            actualReturn = jdbcOrganizationDao.findByPrimaryKey(ID);
            //断言：查询得到的数据和null对比
            assertEquals("return value", null, actualReturn);

        } catch (SQLException ex) {
        }
    }

    /**
     * 此测试用例与数据库初始数据有关。
     * 以下按照当前数据库的测试
     */
    public void testEnumerateAllYear() {
        String[] expectedReturn = null;
        String[] actualReturn = null;
        try {
            actualReturn = jdbcOrganizationDao.enumerateAllYear();
        } catch (SQLException ex) {
        }
        assertEquals("return value", 8, actualReturn.length);

    }

    public void testFindByAdmin() {
        OrganizationData data = new OrganizationData();
        AdministratorDao jdbcAdministratorDao = (AdministratorDao)this.factory.
            getDao(AdministratorDao.class);

        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        String creatorId = "CreatorId";
        data.setCreatorId(creatorId);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();

            //插入机构管理者数据
            String adminID = "userID";
            jdbcAdministratorDao.insert(ID, adminID, "admin");

            OrganizationData[] actualReturn = jdbcOrganizationDao.findByAdmin(
                adminID);

            assertEquals("return value", 1, actualReturn.length);

            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            //查询出刚才插入的数据
            actualReturn = jdbcOrganizationDao.findByAdmin(creatorId);
            assertEquals("return value", 0, actualReturn.length);
            jdbcAdministratorDao.delete(ID, adminID, "admin");
        } catch (SQLException ex) {
        }
    }

    public void testFindByCommonname() {
        String commonname = "";
        OrganizationData[] expectedReturn = null;
        OrganizationData[] actualReturn = null;
        try {
            actualReturn = jdbcOrganizationDao.findByCommonname(
                commonname);

            assertEquals("return value", 0, actualReturn.length);

            OrganizationData data = new OrganizationData();

            Date date = new Date(System.currentTimeMillis());
            //填充必填数据CreateTime
            data.setCreateTime(date);
            String enterpriseName = "EnterpriseName20070328";
            data.setEnterpriseName(enterpriseName);
            String creatorId = "CreatorId";
            data.setCreatorId(creatorId);
            //插入数据
            jdbcOrganizationDao.create(data);
            //获得该机构数据的ID。
            String ID = data.getId();

            actualReturn = jdbcOrganizationDao.findByCommonname(enterpriseName);

            assertEquals("return value", 1, actualReturn.length);

            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            //查询出刚才插入的数据
            actualReturn = jdbcOrganizationDao.findByCommonname(enterpriseName);
            assertEquals("return value", 0, actualReturn.length);
        } catch (SQLException ex) {
        }
    }

    /**
     * 此测试用例
     */
    public void testFindByMember() {
        MembershipDao membershipDao = (MembershipDao)this.factory.getDao(
            MembershipDao.class);
        GroupDao groupDao = (GroupDao)this.factory.getDao(GroupDao.class);

        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        String creatorId = "CreatorId";
        data.setCreatorId(creatorId);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String orgID = data.getId();

            GroupData groupData = new GroupData();
            groupData.setOrgId(orgID);
            groupData.setType("group");
            groupDao.create(groupData);
            String groupID = groupData.getId();

            MembershipData membershipData = new MembershipData();
            String itemID = "itemID";
            int role = 1;
            membershipData.setParentId(groupID);
            membershipData.setItemId(itemID);
            membershipData.setRole(role);

            membershipDao.create(membershipData);

            String memberId = itemID;

            OrganizationData[] actualReturn = jdbcOrganizationDao.findByMember(
                memberId);
            assertEquals("return value", 1, actualReturn.length);

            membershipDao.deleteByItem(itemID);
            groupDao.delete(groupID);
            jdbcOrganizationDao.delete(orgID);
        } catch (SQLException ex) {
        }
    }

    public void testFindByPrimaryKey() {
        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();
            //查询出刚才插入的数据
            OrganizationData actualReturn = jdbcOrganizationDao.
                findByPrimaryKey(ID);
            //断言：查询得到的数据和插入数据对比
            assertEquals("return value", enterpriseName,
                         actualReturn.getEnterpriseName());
            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            //查询出刚才插入的数据
            actualReturn = jdbcOrganizationDao.findByPrimaryKey(ID);
            //断言：查询得到的数据和null对比
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    public void testSearch() {

        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();

            String query = "select * from organization t where t.id = ?";
            SQLParameter[] parameters = new SQLParameter[1];
            parameters[0] = new SQLParameter();
            parameters[0].setValue(ID);

            int fromIndex = 0;
            int number = 10;
            OrganizationData[] expectedReturn = null;
            OrganizationData[] actualReturn = jdbcOrganizationDao.search(query,
                parameters, fromIndex, number);
            assertEquals("return value", 1, actualReturn.length);

            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);

            actualReturn = jdbcOrganizationDao.search(query,
                parameters, fromIndex, number);
            assertEquals("return value", 0, actualReturn.length);
        } catch (SQLException ex) {
        }
    }

    public void testStat() {
        String query = "";
        SQLParameter[] parameters = null;
        ReportData[] expectedReturn = null;
        ReportData[] actualReturn = null;
        try {
            actualReturn = jdbcOrganizationDao.stat(query, parameters);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

    }

    /**
     * 统计功能现在还没实现。
     * 暂时不测试。
     */
    public void testStatTwoDimension() {
        /*
                 String query = "";
                 SQLParameter[] parameters = null;
                 Map expectedReturn = null;
                 Map actualReturn = jdbcOrganizationDao.statTwoDimension(query,
                parameters);
                 assertEquals("return value", expectedReturn, actualReturn);
         */
    }

    public void testUpdate() {

        OrganizationData data = new OrganizationData();
        Date date = new Date(System.currentTimeMillis());
        //填充必填数据CreateTime
        data.setCreateTime(date);
        String enterpriseName = "EnterpriseName20070328";
        data.setEnterpriseName(enterpriseName);
        //插入数据
        try {
            jdbcOrganizationDao.create(data);

            //获得该机构数据的ID。
            String ID = data.getId();
            //查询出刚才插入的数据
            OrganizationData actualReturn = jdbcOrganizationDao.
                findByPrimaryKey(ID);
            //断言：查询得到的数据和插入数据对比
            assertEquals("return value", enterpriseName,
                         actualReturn.getEnterpriseName());


            jdbcOrganizationDao.update(data);

            actualReturn = jdbcOrganizationDao.findByPrimaryKey(ID);


            //删除刚才插入的数据，恢复数据库的初始状态
            jdbcOrganizationDao.delete(ID);
            //查询出刚才插入的数据
            actualReturn = jdbcOrganizationDao.findByPrimaryKey(ID);
            //断言：查询得到的数据和null对比
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }

    }

}
