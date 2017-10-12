package com.fulong.longcon.repository.remote.dao.oracle;

import junit.framework.*;
import java.sql.*;
import com.fulong.longcon.repository.remote.data.*;
import com.fulong.common.dao.DaoTestCase;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.remote.dao.RemotePropertyDefaultValueDao;

/**
 * <p>Title: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Description: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Copyright: �����пƸ������������޹�˾ 2006</p>
 *
 * <p>Company: �����пƸ������������޹�˾</p>
 *
 * @author not attributable
 * @version 2.0
 */
public class TestOracleRemotePropertyDefaultValueDao extends DaoTestCase {
    private RemotePropertyDefaultValueDao oracleRemotePropertyDefaultValueDao = null;

    protected void setUp() throws Exception {
        super.setUp();

        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.remote.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        oracleRemotePropertyDefaultValueDao = (RemotePropertyDefaultValueDao) (RemotePropertyDefaultValueDao)this.factory.getDao(RemotePropertyDefaultValueDao.class);
    }

    protected void tearDown() throws Exception {
        oracleRemotePropertyDefaultValueDao = null;
        super.tearDown();
    }

    public void testFindByCategory() throws SQLException {
        String remoteCategoryID = "";
        RemotePropertyValueData[] expectedReturn = null;
        RemotePropertyValueData[] actualReturn = oracleRemotePropertyDefaultValueDao.findByCategory(remoteCategoryID);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testFindByProperty() throws SQLException {
        String contentProperty = "";
        String remoteCategoryID = "";
        String expectedReturn = null;
        String actualReturn = oracleRemotePropertyDefaultValueDao.findByProperty(contentProperty, remoteCategoryID);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testInsert() throws SQLException {
        RemotePropertyValueData data = null;
        oracleRemotePropertyDefaultValueDao.insert(data);
        /**@todo fill in the test code*/
    }

    public void testRemoveByProperty() throws SQLException {
        String contentProperty = "";
        String remoteCategoryID = "";
        oracleRemotePropertyDefaultValueDao.removeByProperty(contentProperty, remoteCategoryID);
        /**@todo fill in the test code*/
    }

}
