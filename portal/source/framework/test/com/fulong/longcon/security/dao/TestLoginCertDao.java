package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;
import com.fulong.longcon.security.data.LoginCertData;
import java.sql.*;

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
public class TestLoginCertDao extends DaoTestCase {
    private LoginCertDao jdbcLoginCertDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcLoginCertDao = (LoginCertDao)this.factory.getDao(LoginCertDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcLoginCertDao = null;
        super.tearDown();
    }

    /**
     * LoginCertDao没有提供delete的接口，因此，在create后应该从
     * 数据库中删除对应的记录，
     * 避免给数据库造成的副作用。
     * 调用语句
     * delete from serviceagent t where t.userid='userID20070323'
     */
    public void testCreate() {
        LoginCertData data = new LoginCertData();
        String userID = "userID20070323";
        String ServiceId = "serviceID20070323";
        String userName = "userName20070323";
        data.setServiceid(ServiceId);
        data.setUserid(userID);
        data.setUsername("userName20070323");

        try {
            jdbcLoginCertDao.create(data);

        LoginCertData actualReturn = jdbcLoginCertDao.findByUserIdAndServiceId(
                userID, ServiceId);
            assertEquals("return value", data.getServiceid(), actualReturn.getServiceid());
            assertEquals("return value", data.getUserid(), actualReturn.getUserid());
        } catch (SQLException ex) {
        }  }

    public void testFindByUserIdAndServiceId() {
        String userID = "";
        String ServiceId = "";
        LoginCertData expectedReturn = null;
        LoginCertData actualReturn = null;
        try {
            actualReturn = jdbcLoginCertDao.findByUserIdAndServiceId(
                userID, ServiceId);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

    }


    public void testUpdate() {

        String userID = "userID20070323";
        String ServiceId = "serviceID20070323";
        String userName = "userName20070323";

        LoginCertData actualReturn = null;
        try {
            actualReturn = jdbcLoginCertDao.findByUserIdAndServiceId(
                userID, ServiceId);

            actualReturn.setUsername("nameafterUpdated");
            jdbcLoginCertDao.update(actualReturn);
            actualReturn = jdbcLoginCertDao.findByUserIdAndServiceId(userID,
                ServiceId);
            assertEquals("return value", "nameafterUpdated",
                         actualReturn.getUsername());
        } catch (SQLException ex) {
        }
    }

}
