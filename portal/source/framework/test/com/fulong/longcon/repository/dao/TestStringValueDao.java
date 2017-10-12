package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;

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
 * @author lishaobo@fulong.com.cn
 * @version 1.0
 */
public class TestStringValueDao extends DaoTestCase {
    private StringValueDao stringValueDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        stringValueDao = (StringValueDao)this.factory.getDao(StringValueDao.class);
    }

    protected void tearDown() throws Exception {
        stringValueDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        String contentID = "testcase_contentID";
        String property = "PROP156";
        int index = 0;
        String value = "testcase_value";
        stringValueDao.insert(contentID, property, index, value);
        String[] actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);
        stringValueDao.delete(contentID,property);
        actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", 0, actualReturn.length);
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testDelete1() throws SQLException {
        String contentID = "testcase_contentID";
        String property = "PROP156";
        int index = 0;
        String value = "testcase_value";
        stringValueDao.insert(contentID, property, index, value);
        String[] actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);
        stringValueDao.delete(contentID, property);
        actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testInsert() throws SQLException {
        String contentID = "testcase_contentID";
        String property = "PROP156";
        int index = 0;
        String value = "testcase_value";
        stringValueDao.insert(contentID, property, index, value);

        String[] actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);
        stringValueDao.delete(contentID, property);
        actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", 0, actualReturn.length);
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testLoad() throws SQLException {
        String contentID = "testcase_contentID";
        String property = "PROP156";
        int index = 0;
        String value = "testcase_value";
        stringValueDao.insert(contentID, property, index, value);

        String[] actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);
        stringValueDao.delete(contentID, property);
        actualReturn = stringValueDao.load(contentID, property);
        assertEquals("return value", 0, actualReturn.length);

    }

}
