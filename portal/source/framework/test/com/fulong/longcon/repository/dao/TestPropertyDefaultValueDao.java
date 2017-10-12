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
public class TestPropertyDefaultValueDao extends DaoTestCase {
    private PropertyDefaultValueDao propertyDefaultValueDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        propertyDefaultValueDao = (PropertyDefaultValueDao)this.factory.getDao(
                PropertyDefaultValueDao.class);
    }

    protected void tearDown() throws Exception {
        propertyDefaultValueDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByPropertyDefinition方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        String propertyID = "propertyID";
        String value = "value";
        String defID = "definitionID";
        propertyDefaultValueDao.insert(propertyID, value, defID);

        String[] actualReturn = propertyDefaultValueDao.
                                findByPropertyDefinition(propertyID, defID);
        assertEquals("return value", value, actualReturn[0]);
        propertyDefaultValueDao.delete(propertyID, defID);
        actualReturn = propertyDefaultValueDao.findByPropertyDefinition(
                propertyID, defID);
        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByPropertyDefinition方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testFindByPropertyDefinition() throws SQLException {
        String propertyID = "propertyID";
        String value = "value";
        String defID = "definitionID";
        propertyDefaultValueDao.insert(propertyID, value, defID);

        String[] actualReturn = propertyDefaultValueDao.
                                findByPropertyDefinition(propertyID, defID);
        assertEquals("return value", value, actualReturn[0]);
        propertyDefaultValueDao.delete(propertyID, defID);
        actualReturn = propertyDefaultValueDao.findByPropertyDefinition(
                propertyID, defID);
        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByPropertyDefinition方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录
     * 5.assertEquals断言该记录已经不存在
     * @throws SQLException
     */
    public void testInsert() throws SQLException {
        String propertyID = "propertyID";
        String value = "value";
        String defID = "definitionID";
        propertyDefaultValueDao.insert(propertyID, value, defID);

        String[] actualReturn = propertyDefaultValueDao.
                                findByPropertyDefinition(propertyID, defID);
        assertEquals("return value", value, actualReturn[0]);
        propertyDefaultValueDao.delete(propertyID, defID);
        actualReturn = propertyDefaultValueDao.findByPropertyDefinition(
                propertyID, defID);
        assertEquals("return value", 0, actualReturn.length);

    }

}
