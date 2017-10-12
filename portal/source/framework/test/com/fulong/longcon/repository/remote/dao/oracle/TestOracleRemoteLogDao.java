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
public class TestOracleRemoteLogDao extends DaoTestCase {
    private RemoteLogDao oracleRemoteLogDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.remote.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        oracleRemoteLogDao = (RemoteLogDao)this.factory.getDao(RemoteLogDao.class);

    }

    protected void tearDown() throws Exception {
        oracleRemoteLogDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.
     * 2.insert方法插入一条元组
     * 3.findByID方法得到插入的记录
     * 4.判断插入的值是否被取出，否则抛出异常
     * 5.delete方法删除插入的记录，
     * 6.findByID方法查找指定PKID的记录
     * 7.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testDeleteByID() throws SQLException {
        String operatorID = "operator_testDelete";
        RemoteLogData data = new RemoteLogData();

        oracleRemoteLogDao.insert(data);
        String pkID = data.getID();

        RemoteLogData expectedReturn = data;
        RemoteLogData actualReturn = oracleRemoteLogDao.findByID(pkID);

        if (!expectedReturn.equals(actualReturn)) {
            throw new AssertionFailedError("Return value: expected:<" +
                                          expectedReturn + "> but was:<" + actualReturn + ">");
       }

        oracleRemoteLogDao.deleteByID(pkID);

        RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);

        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入多个初始值
     * 2.findAll方法得到所有元组
     * 3.判断插入的值是否全部被取出，否则抛出异常
     * 4.deleteByID方法删除每个插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindAll() throws SQLException {
        final int num = 5;
        //建立num个RemoteLogData对象
        RemoteLogData datas[] = new RemoteLogData[num];
        String pkID[] = new String[num]; //datas的PKID数组

        //向DB中插入num个RemoteLogData
        for(int i=0; i<num; i++){
            StringBuffer strBuf = new StringBuffer("testFindAll");
            pkID[i] = new String( strBuf.append(i) );
            datas[i] = new RemoteLogData();
            datas[i].setID( pkID[i] );
            oracleRemoteLogDao.insert(datas[i]);
        }

        //查找DB中所有元组，放入actualReturn数组中；
        RemoteLogData[] actualReturn = oracleRemoteLogDao.findAll();

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
        //如果有data没有被找到，则抛出异常
        if( num > count )
            throw new AssertionFailedError("Some data not found!");

        //删除pkID对应的元组
        for(int j=0; j<num; j++){
            oracleRemoteLogDao.deleteByID(pkID[j]);
            RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID[j]);
            assertEquals("return value", null, actualReturn2);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.deleteByID方法删除该记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByID() throws SQLException {
        String pkID = "pkIDTest";

        RemoteLogData data = new RemoteLogData();
        data.setID(pkID);
        oracleRemoteLogDao.insert(data);

        RemoteLogData expectedReturn = data;
        RemoteLogData actualReturn = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteLogDao.deleteByID(pkID);
        RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定type的元组
     * 2.findByType方法取得对应type的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByType() throws SQLException {
        //建立num个RemoteLogData对象
        RemoteLogData data = new RemoteLogData();
        int type = 2; //type指定为2

        data.setType(type);
        oracleRemoteLogDao.insert(data);
        String pkID = data.getID();

        RemoteLogData[] actualReturn = oracleRemoteLogDao.findByType(type);

        boolean found = false;
        for(int i=0; i<actualReturn.length; i++){
            if( data.equals(actualReturn[i]) ){
                found = true;
            }
        }
        if( !found ){
            throw new AssertionFailedError("The findByType() has something wrong!");
        }

        //删除pkID对应的元组
        oracleRemoteLogDao.deleteByID(pkID);
        RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定User的元组
     * 2.findByUser方法取得对应User的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByUser() throws SQLException {
        RemoteLogData data = new RemoteLogData();
        String localUser = "testFindByUser"; //localUser指定为testFindByUser

        data.setUserID(localUser);
        oracleRemoteLogDao.insert(data);

        RemoteLogData[] actualReturn = oracleRemoteLogDao.findByUser(localUser);

        boolean found = false;
        for(int i=0; i<actualReturn.length; i++){
            if( data.equals(actualReturn[i]) ){
                found = true;
            }
        }
        if( !found ){
            throw new AssertionFailedError("The findByUser() has something wrong!");
        }

        //删除所插入的元组
        String pkID = data.getID();
        oracleRemoteLogDao.deleteByID(pkID);
        RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定OPERATORID和TYPE的元组
     * 2.findByUserAndType方法取得对应OPERATORID和TYPE的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByUserAndType() throws SQLException {
        RemoteLogData data = new RemoteLogData();
        String localUser = "testFindByUser"; //OPERATORID指定为testFindByUser
        int type = 2; //TYPE指定为2

        data.setUserID(localUser);
        data.setType(type);
        oracleRemoteLogDao.insert(data);

        RemoteLogData[] actualReturn = oracleRemoteLogDao.findByUserAndType(localUser, type);

        boolean found = false;
        for(int i=0; i<actualReturn.length; i++){
            if( data.equals(actualReturn[i]) ){
                found = true;
            }
        }
        if( !found ){
            throw new AssertionFailedError("The findByUserAndType() has something wrong!");
        }

        //删除所插入的元组
        String pkID = data.getID();
        oracleRemoteLogDao.deleteByID(pkID);
        RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.deleteByID方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
      */
    public void testInsert() throws SQLException {
        String contentID = "testInsert_CONTENTID";

        RemoteLogData data = new RemoteLogData();
        data.setContentID(contentID);

        oracleRemoteLogDao.insert(data);
        String pkID = data.getID();

        RemoteLogData expectedReturn = data;
        RemoteLogData actualReturn = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteLogDao.deleteByID(pkID);
        RemoteLogData actualReturn2 = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
         /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始元组
     * 2.修改初始元组的除PKID外所有属性，并记录为新元组
     * 3.新元组的的值去update()初始元组
     * 4.用始终未变的PKID通过findByID方法查找到更新后的初始元组
     * 5.比较查找到的初始元组和新元组是否相等，不等则抛出异常
     * 6.delete方法删除记录，
     * 注意：Timestamp、java.sql.Date与java.util.Date之间的区分
     * @throws SQLException
     */
    public void testUpdate() throws SQLException {
        String contentID = "testInsert_CONTENTID";

        RemoteLogData data = new RemoteLogData();
        data.setContentID(contentID);
        oracleRemoteLogDao.insert(data);

        String pkID = data.getID();
        data.setID(pkID);

        RemoteLogData newData = new RemoteLogData();
        newData.setID(pkID);
        newData.setContentID("newContentID");
        newData.setCategoryID("newCategoryID");
        newData.setLogDate( new java.util.Date(2007, 7, 7, 7, 7, 7) );
        newData.setUserID("newOperatorID");
        newData.setStatus(0);
        newData.setType(1);
        oracleRemoteLogDao.update(newData);

        RemoteLogData returnData = oracleRemoteLogDao.findByID(pkID);
        assertEquals("return value", newData, returnData);
        oracleRemoteLogDao.deleteByID(pkID);
        /**@todo fill in the test code*/
    }

}
