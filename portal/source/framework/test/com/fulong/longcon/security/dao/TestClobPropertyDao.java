package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;
import java.util.Map;
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
public class TestClobPropertyDao extends DaoTestCase {
    private ClobPropertyDao jdbcClobPropertyDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcClobPropertyDao = (ClobPropertyDao)this.factory.getDao(
                ClobPropertyDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcClobPropertyDao = null;
        super.tearDown();
    }

    public void testDelete() {
        String ID = "ID";
        String name = "name";
        String type = "type";
        try {
            jdbcClobPropertyDao.insert(ID, name, type);

        Object value = "";

        String actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name,
                type);
        assertEquals("return value", value, actualReturn);

        value = "value";
        jdbcClobPropertyDao.update(ID, name, value, type);
        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", value, actualReturn);

        jdbcClobPropertyDao.delete(ID);

        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", "", actualReturn);
    } catch (SQLException ex) {
           }

    }

    public void testDelete1() {

        String ID = "ID";
        String name = "name";
        String type = "type";
        try {
            jdbcClobPropertyDao.insert(ID, name, type);


        Object value = "";

        String actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name,
                type);
        assertEquals("return value", value, actualReturn);

        value = "value";
        jdbcClobPropertyDao.update(ID, name, value, type);
        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", value, actualReturn);

        jdbcClobPropertyDao.delete(ID, name);

        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", "", actualReturn);
    } catch (SQLException ex) {
        }
    }

    public void testFindByPrimaryKey() {
        String ID = "";
        Map expectedReturn = null;
        Map actualReturn = null;
        try {
            actualReturn = jdbcClobPropertyDao.findByPrimaryKey(ID);

        assertEquals("return value", true, actualReturn.isEmpty());

        ID = "ID";
        String name = "name";
        String type = "type";
        jdbcClobPropertyDao.insert(ID, name, type);

        String value = "value";
        jdbcClobPropertyDao.update(ID, name, value, type);
        actualReturn = jdbcClobPropertyDao.findByPrimaryKey(ID);
        assertEquals("return value", false, actualReturn.isEmpty());

        jdbcClobPropertyDao.delete(ID, name);

        actualReturn = jdbcClobPropertyDao.findByPrimaryKey(ID);
        assertEquals("return value", true, actualReturn.isEmpty());
    } catch (SQLException ex) {
        }
    }

    public void testGetClobAttribute() {
        String ID = "ID";
        String name = "name";
        String type = "type";
        try {
            jdbcClobPropertyDao.insert(ID, name, type);


        Object value = "";

        String actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name,
                type);
        assertEquals("return value", value, actualReturn);

        value = "value";
        jdbcClobPropertyDao.update(ID, name, value, type);
        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", value, actualReturn);

        jdbcClobPropertyDao.delete(ID);
    } catch (SQLException ex) {
        }
    }

    public void testInsert() {
        String ID = "ID";
        String name = "name";
        String type = "type";
        try {
            jdbcClobPropertyDao.insert(ID, name, type);

        Object value = "";
        String actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name,
                type);
        assertEquals("return value", value, actualReturn);
        value = "value";
        jdbcClobPropertyDao.update(ID, name, value, type);
        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", value, actualReturn);

        jdbcClobPropertyDao.delete(ID);  } catch (SQLException ex) {
        }
    }

    public void testUpdate() {
        String ID = "ID";
        String name = "name";
        String type = "type";
        try {
            jdbcClobPropertyDao.insert(ID, name, type);

        Object value = "";
        String actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name,
                type);
        assertEquals("return value", value, actualReturn);
        value = "value";
        jdbcClobPropertyDao.update(ID, name, value, type);
        actualReturn = jdbcClobPropertyDao.getClobAttribute(ID, name, type);
        assertEquals("return value", value, actualReturn);
        jdbcClobPropertyDao.delete(ID); } catch (SQLException ex) {
        }
    }

}
