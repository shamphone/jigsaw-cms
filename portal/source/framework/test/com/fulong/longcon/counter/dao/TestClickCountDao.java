package com.fulong.longcon.counter.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.*;
/**
 *
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @author lishaobo
 * @version 1.0
 */


public class TestClickCountDao extends DaoTestCase {
    private ClickCountDao oracleClickCountDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        oracleClickCountDao = (ClickCountDao)this.factory.getDao(ClickCountDao.class);
    }

    protected void tearDown() throws Exception {
        oracleClickCountDao = null;
        super.tearDown();
    }

    public void testInsertCount() {
        String name = "";
        try {
            oracleClickCountDao.insertCount(name);
        } catch (SQLException ex) {
        }

    }

    public void testLoadCount() {
        String name = "";
        long expectedReturn = 0L;
        long actualReturn = 0L;
        try {
            actualReturn = oracleClickCountDao.loadCount(name);
        } catch (SQLException ex) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testSaveCount() {
        String name = "";
        long newValue = 0L;
        try {
            oracleClickCountDao.saveCount(name, newValue);
        } catch (SQLException ex) {
        }
    }

}
