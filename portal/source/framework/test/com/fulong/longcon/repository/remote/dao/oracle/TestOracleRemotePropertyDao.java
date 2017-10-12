package com.fulong.longcon.repository.remote.dao.oracle;

import java.sql.*;

import com.fulong.common.dao.*;
import com.fulong.longcon.repository.remote.dao.*;
import com.fulong.longcon.repository.remote.data.*;
import junit.framework.*;

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
 * @author xiaming
 * @version 1.0
 */
public class TestOracleRemotePropertyDao extends DaoTestCase {
    private RemotePropertyDao oracleRemotePropertyDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.remote.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        oracleRemotePropertyDao = (RemotePropertyDao)this.factory.getDao(RemotePropertyDao.class);
    }

    protected void tearDown() throws Exception {
        oracleRemotePropertyDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByProperty方法查找该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.removeByProperty方法删除该记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
      */
    public void testInsert() throws SQLException {
        RemotePropertyData data = new RemotePropertyData();

        String contentProperty = "remoteCategoryPropertyTest";
        String remoteCategoryID = "remoteCategoryIDTest";
        String boundProperty = "boundPropertyTest";
        data.setRemoteCategoryProperty(contentProperty);
        data.setRemoteCategoryID(remoteCategoryID);
        data.setBoundProperty(boundProperty);

        oracleRemotePropertyDao.insert(data);
        String actualReturn = oracleRemotePropertyDao.findByProperty(contentProperty, remoteCategoryID);
        //如果没查找到指定的元组则抛出异常
        if( (actualReturn == null) || !boundProperty.equals(actualReturn) ){
            throw new AssertionFailedError("ExpectedData wasn't found, maybe the insert() is wrong!");
        }
        //移除插入的元组
        oracleRemotePropertyDao.removeByProperty(contentProperty, remoteCategoryID);
        String actualReturn2 = oracleRemotePropertyDao.findByProperty(contentProperty, remoteCategoryID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入多个初始值
     * 2.findByRemoteCategoryID方法得到插入的多个记录
     * 3.判断插入的值是否全部被取出，否则抛出异常
     * 4.removeByProperty方法删除每个插入的记录，
     * @throws SQLException
     */
    public void testFindByRemoteCategoryID() throws SQLException {
        final int num = 3;
        //建立num个RemotePropertyData对象
        RemotePropertyData datas[] = new RemotePropertyData[num];
        //指定Remote_Category_ID
        String remoteCategoryID = "TestremoteCategoryID";
        String remoteCategoryProperty[] = new String[num]; //datas的remoteCategoryProperty数组

        //向DB中插入num个RemoteCategoryData
        for(int i=0; i<num; i++){
            StringBuffer strBuf = new StringBuffer("testFindByRemoteCategoryID");
            remoteCategoryProperty[i] = new String( strBuf.append(i) );
            datas[i] = new RemotePropertyData();
            datas[i].setRemoteCategoryID( remoteCategoryID );
            datas[i].setRemoteCategoryProperty( remoteCategoryProperty[i] ); //TestremoteCategoryID的num个property
            oracleRemotePropertyDao.insert(datas[i]);
        }

        //查找DB中所有元组，放入actualReturn数组中；
        RemotePropertyData[] actualReturn = oracleRemotePropertyDao.findByRemoteCategoryID(remoteCategoryID);

        int count = 0; //记录在返回数组中找到几个上面插入的元组
        for(int m=0; m<num; m++){
            boolean found = false; //记录每次循环是否在返回数组中找到对应元组,进入时初始化为假

            for(int n=0; n<actualReturn.length; n++){
                if( datas[m].equals(actualReturn[n]) ){
                    found = true; //有元组被找到
                }
            }

            if(found){
                count++; //found为真，说明找到对应元组，计数加一
            }
        }

        //如果插入的元组没有被全部找到，则抛出异常
        if( num > count )
            throw new AssertionFailedError("Some data not found!");

        //删除插入的测试元组
        for(int j=0; j<num; j++){
            oracleRemotePropertyDao.removeByProperty(remoteCategoryProperty[j], remoteCategoryID);
        }

        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByProperty方法得到该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.delete方法删除该记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByProperty() throws SQLException {
        RemotePropertyData data = new RemotePropertyData();

        String contentProperty = "remoteCategoryPropertyTest";
        String remoteCategoryID = "remoteCategoryIDTest";
        String boundProperty = "boundPropertyTest";
        data.setRemoteCategoryProperty(contentProperty);
        data.setRemoteCategoryID(remoteCategoryID);
        data.setBoundProperty(boundProperty);

        oracleRemotePropertyDao.insert(data);

        String expectedReturn = boundProperty;
        String actualReturn = oracleRemotePropertyDao.findByProperty(contentProperty, remoteCategoryID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemotePropertyDao.removeByProperty(contentProperty, remoteCategoryID);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByProperty方法得到该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.removeByProperty方法删除该记录，
     * 5.assertEquals断言二次插取出的值为空
     * @throws SQLException
     */
    public void testRemoveByProperty() throws SQLException {
        RemotePropertyData data = new RemotePropertyData();

        String contentProperty = "remoteCategoryPropertyTest";
        String remoteCategoryID = "remoteCategoryIDTest";
        String boundProperty = "boundPropertyTest";
        data.setRemoteCategoryProperty(contentProperty);
        data.setRemoteCategoryID(remoteCategoryID);
        data.setBoundProperty(boundProperty);

        oracleRemotePropertyDao.insert(data);

        String expectedReturn = boundProperty;
        String actualReturn = oracleRemotePropertyDao.findByProperty(contentProperty, remoteCategoryID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemotePropertyDao.removeByProperty(contentProperty, remoteCategoryID);

        RemotePropertyData expectedReturn2 = null;
        String actualReturn2 = oracleRemotePropertyDao.findByProperty(contentProperty, remoteCategoryID);
        assertEquals("return value", expectedReturn2, actualReturn2);
        /**@todo fill in the test code*/
    }

}
