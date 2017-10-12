package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.*;

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
 */
public class TestAdministratorDao extends DaoTestCase {
    private AdministratorDao jdbcAdministratorDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcAdministratorDao = (AdministratorDao)this.factory.getDao(
            AdministratorDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcAdministratorDao = null;
        super.tearDown();
    }

    public void testDelete() {
        String userId = "20040728183441";
        String orgId = "1000000059775";
        String type = "linkman_admin";
        try {
            jdbcAdministratorDao.insert(orgId, userId, type);

            jdbcAdministratorDao.delete(orgId, userId, type);
        } catch (SQLException ex) {
        }
    }

    public void testDeleteByOrganization() {
        String orgID = "";
        try {
            jdbcAdministratorDao.deleteByOrganization(orgID);
        } catch (SQLException ex) {
        }

    }

    public void testInsert() {
        String userId = "20040728183441";
        String orgId = "1000000059775";
        String type = "linkman_admin";
        try {
            jdbcAdministratorDao.insert(orgId, userId, type);
            jdbcAdministratorDao.delete(orgId, userId, type);
        } catch (SQLException ex) {
        }
    }

}
