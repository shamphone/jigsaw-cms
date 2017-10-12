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
public class TestOracleRemoteCategoryDao extends DaoTestCase {
    private RemoteCategoryDao oracleRemoteCategoryDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.remote.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        oracleRemoteCategoryDao = (RemoteCategoryDao)this.factory.getDao(RemoteCategoryDao.class);
    }

    protected void tearDown() throws Exception {
        oracleRemoteCategoryDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.
     * 2.insert方法插入指定DisplayName的一条元组
     * 3.findByID方法得到插入的记录
     * 4.判断插入的值是否被取出，否则抛出异常
     * 5.delete方法删除指定PKID的记录
     * 6.findByID方法查找指定PKID的记录
     * 7.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        String displayName = "testDelete";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setDisplayName(displayName);

        oracleRemoteCategoryDao.insert(data);
        String pkID = data.getID();

        RemoteCategoryData expectedReturn = data;
        RemoteCategoryData actualReturn = oracleRemoteCategoryDao.findByID(pkID);

       assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteCategoryDao.delete(pkID);

        RemoteCategoryData actualReturn2 = oracleRemoteCategoryDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入多个初始值
     * 2.findAll方法得到所有记录
     * 3.判断插入的值是否全部被取出，否则抛出异常
     * 4.delete方法删除每个插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindAll() throws SQLException {
        final int num = 5;
        //建立num个RemoteCategoryData对象
        RemoteCategoryData datas[] = new RemoteCategoryData[num];
        String pkID[] = new String[num]; //datas的PKID数组

        //向DB中插入num个RemoteCategoryData
        for(int i=0; i<num; i++){
            StringBuffer strBuf = new StringBuffer("testFindAll");
            pkID[i] = new String( strBuf.append(i) );
            datas[i] = new RemoteCategoryData();
            datas[i].setID( pkID[i] );
            oracleRemoteCategoryDao.insert(datas[i]);
        }

        //查找DB中所有元组，放入actualReturn数组中；
        RemoteCategoryData[] actualReturn = oracleRemoteCategoryDao.findAll();

        int count = 0; //记录在返回数组中找到几个上面插入的元组
        //Vector notFoundDataVec = new Vector();
        for(int m=0; m<num; m++){
            boolean found = false; //记录每次循环是否在返回数组中找到对应元组,进入时初始化为假

            for(int n=0; n<actualReturn.length; n++){
                if( datas[m].equals(actualReturn[n]) ){
                    found = true; //有元组被找到
                    //notFoundDataVec.add(datas[m]);
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
            oracleRemoteCategoryDao.delete(pkID[j]);
            RemoteCategoryData actualReturn2 = oracleRemoteCategoryDao.findByID(pkID[j]);
            assertEquals("return value", null, actualReturn2);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.delete方法删除该记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByID() throws SQLException {
        String pkID = "pkIDTest";

        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        oracleRemoteCategoryDao.insert(data);

        RemoteCategoryData expectedReturn = data;
        RemoteCategoryData actualReturn = oracleRemoteCategoryDao.findByID(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteCategoryDao.delete(pkID);
        actualReturn = oracleRemoteCategoryDao.findByID(pkID);
        assertEquals("return value", null, actualReturn);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByRemoteID方法得到该记录
     * 3.判断是否取出上步插入的值，否则抛出异常
     * 4.delete方法删除该记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByRemoteID() throws SQLException {
        String remoteID = "remote_idTest";

        RemoteCategoryData data = new RemoteCategoryData();
        data.setRemoteID(remoteID);
        oracleRemoteCategoryDao.insert(data);
        String ID = data.getID();

        RemoteCategoryData expectedReturn = data;
        RemoteCategoryData[] actualReturn = oracleRemoteCategoryDao.
            findByRemoteID(remoteID);

        //判断是否通过findByRemoteID()找到上面插入的元组,如果找不到则抛出异常
        boolean foundData = false;
        for(int i=0; i<actualReturn.length; i++){
            foundData = foundData || data.equals(actualReturn[i]);
        }
        if(!foundData){
            throw new AssertionFailedError("ExpectedData wasn't found");
        }

        //删除插入的元组
        oracleRemoteCategoryDao.delete(ID);
        RemoteCategoryData[] actualReturn2 = oracleRemoteCategoryDao.
            findByRemoteID(remoteID);
        //如果仍能找到插入的元组，则抛出异常
        for(int i=0; i<actualReturn2.length; i++){
            if(data.equals(actualReturn2[i]))
                throw new AssertionFailedError("There is something wrong!");
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定type的元组
     * 2.findByType方法取得对应type的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.delete方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByType() throws SQLException {
        RemoteCategoryData data = new RemoteCategoryData();
        int type = 2; //type指定为2

        data.setType(type);
        oracleRemoteCategoryDao.insert(data);
        String pkID = data.getID();

        RemoteCategoryData[] actualReturn = oracleRemoteCategoryDao.findByType(type);

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
        oracleRemoteCategoryDao.delete(pkID);
        RemoteCategoryData actualReturn2 = oracleRemoteCategoryDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }


     /**
      * 测试策略：
      * 1.insert方法插入一个初始值
      * 2.findByID方法得到该记录
      * 3.比较插入的值和取出的值是否相等，不等则抛出异常
      * 4.delete方法删除该记录，
      * 5.assertEquals断言插取出的值为空
      * @throws SQLException
      */

     public void testInsert() throws SQLException {

         String pkID = "testPKID";

         RemoteCategoryData data = new RemoteCategoryData();
         data.setID(pkID);

         oracleRemoteCategoryDao.insert(data);

         RemoteCategoryData expectedReturn = data;
         RemoteCategoryData actualReturn = oracleRemoteCategoryDao.findByID(pkID);
         assertEquals("return value", expectedReturn, actualReturn);

         oracleRemoteCategoryDao.delete(pkID);
         RemoteCategoryData actualReturn2 = oracleRemoteCategoryDao.findByID(
             pkID);
         assertEquals("return value", null, actualReturn2);
         /**@todo fill in the test code*/
     }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.比较插入的值和取出的值是否相等，不等则抛出异常
     * 4.remove方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testRemove() throws SQLException {
        String remoteID = "testRemoteID";

        RemoteCategoryData data = new RemoteCategoryData();
        data.setRemoteID(remoteID);
        oracleRemoteCategoryDao.insert(data);
        String ID = data.getID();

        RemoteCategoryData expectedReturn = data;
        RemoteCategoryData actualReturn = oracleRemoteCategoryDao.findByID(ID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteCategoryDao.removeByRepository(remoteID);
        RemoteCategoryData actualReturn2 = oracleRemoteCategoryDao.findByID(ID);
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
        RemoteCategoryData data = new RemoteCategoryData();
        String pkID = "testUpdate";
        oracleRemoteCategoryDao.delete(pkID);

        data.setID(pkID);
        oracleRemoteCategoryDao.insert(data);

        RemoteCategoryData newData = new RemoteCategoryData();
        newData.setID(pkID);
        newData.setCategoryID("newCategoryID");
        newData.setDescription("newDescription");
        newData.setDisplayName("newDiaplayName");
        newData.setBindingCategory("newBindingCategory");
        newData.setLastUpdateTime( new java.util.Date(2002, 2, 2, 2, 2, 2) );
        newData.setRemoteID("newRemoteID");
        newData.setCreateTime( new java.util.Date(2007, 7, 7, 7, 7, 7) );
        newData.setAutoUpdate(true);
        oracleRemoteCategoryDao.update(newData);

        RemoteCategoryData returnData = oracleRemoteCategoryDao.findByID(pkID);
        assertEquals("return value", newData, returnData);
        oracleRemoteCategoryDao.delete(pkID);
        /**@todo fill in the test code*/
    }
}
