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
public class TestStringPropertyDao extends DaoTestCase {
    private StringPropertyDao jdbcStringPropertyDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcStringPropertyDao = (StringPropertyDao)this.factory.getDao(
            StringPropertyDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcStringPropertyDao = null;
        super.tearDown();
    }

    /**
     * 构建一组数据先插入
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map不为空
     * 删除刚才的数据
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map为空
     */
    public void testDelete() {

        String ID = "ID";
        String name = "name";
        String value = "value";
        String type = "type";
        try {
            jdbcStringPropertyDao.insert(ID, name, value, type);
        } catch (SQLException ex1) {
        }

        Map actualReturn = null;
        try {
            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);

            boolean empty = actualReturn.isEmpty();
            assertEquals("return value", false, empty);

            jdbcStringPropertyDao.delete(ID);

            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);
            empty = actualReturn.isEmpty();
            assertEquals("return value", true, empty);
        } catch (SQLException ex) {
        }
    }

    /**
     * 构建一组数据先插入
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map不为空
     * 删除刚才的数据
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map为空
     */
    public void testDelete1() {

        String ID = "ID";
        String name = "name";
        String value = "value";
        String type = "type";
        try {
            jdbcStringPropertyDao.insert(ID, name, value, type);

            Map actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);
            boolean empty = actualReturn.isEmpty();
            assertEquals("return value", false, empty);

            jdbcStringPropertyDao.delete(ID, name);

            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);
            empty = actualReturn.isEmpty();
            assertEquals("return value", true, empty);
        } catch (SQLException ex) {
        }
    }

    /**
     * 构建一组数据先插入
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map不为空
     * 删除刚才的数据
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map为空
     */
    public void testFindByPrimaryKey() {
        String ID = "";
        Map expectedReturn = null;
        Map actualReturn = null;
        try {
            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);

            boolean empty = actualReturn.isEmpty();
            assertEquals("return value", true, empty);

            ID = "ID";
            String name = "name";
            String value = "value";
            String type = "type";
            jdbcStringPropertyDao.insert(ID, name, value, type);

            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);
            empty = actualReturn.isEmpty();
            assertEquals("return value", false, empty);

            jdbcStringPropertyDao.delete(ID);

            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);
            empty = actualReturn.isEmpty();
            assertEquals("return value", true, empty);
        } catch (SQLException ex) {
        }
    }

    /**
     * 构建一组数据先插入
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map不为空
     * 删除刚才的数据
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map为空
     */
    public void testFindValueByPrimaryKey() {
        String id = "";
        String name = "";
        String expectedReturn = null;
        String actualReturn = null;
        try {
            actualReturn = jdbcStringPropertyDao.findValueByPrimaryKey(id,
                name);
        } catch (SQLException ex1) {
        }
        assertEquals("return value", expectedReturn, actualReturn);

        String ID = "ID";
        name = "name";
        String value = "value";
        String type = "type";
        try {
            jdbcStringPropertyDao.insert(ID, name, value, type);

            actualReturn = jdbcStringPropertyDao.findValueByPrimaryKey(ID, name);

            assertEquals("return value", value, actualReturn);

            jdbcStringPropertyDao.delete(ID);

            actualReturn = jdbcStringPropertyDao.findValueByPrimaryKey(ID, name);

            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }
    }

    /**
     * 构建一组数据先插入
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map不为空
     * 删除刚才的数据
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，即判断获取的Map为空
     */
    public void testInsert() {
        String ID = "ID";
        String name = "name";
        String value = "value";
        String type = "type";
        try {
            jdbcStringPropertyDao.insert(ID, name, value, type);

            Map actualReturn = null;

            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);

            boolean empty = actualReturn.isEmpty();
            assertEquals("return value", false, empty);

            jdbcStringPropertyDao.delete(ID);

            actualReturn = jdbcStringPropertyDao.findByPrimaryKey(ID);
            empty = actualReturn.isEmpty();
            assertEquals("return value", true, empty);
        } catch (SQLException ex) {
        }
    }

    /**
     * 构建一组数据先插入
     * 通过查询取得刚才插入的数据
     * 确认得到的数据，
     * 更改刚才的数据
     * 通过查询取得刚才插入的数据
     * 确认得到的数据
     */
    public void testUpdate() {
        String ID = "ID";
        String name = "name";
        String value = "value";
        String type = "type";
        try {
            jdbcStringPropertyDao.insert(ID, name, value, type);

            String actualReturn = jdbcStringPropertyDao.findValueByPrimaryKey(
                ID,
                name);

            assertEquals("return value", value, actualReturn);

            String valueUpdated = "value after updated";
            jdbcStringPropertyDao.update(ID, name, valueUpdated, type);
            actualReturn = jdbcStringPropertyDao.findValueByPrimaryKey(ID, name);
            assertEquals("return value", valueUpdated, actualReturn);
            jdbcStringPropertyDao.delete(ID);

            actualReturn = jdbcStringPropertyDao.findValueByPrimaryKey(ID, name);
            assertEquals("return value", null, actualReturn);
        } catch (SQLException ex) {
        }

    }

}
