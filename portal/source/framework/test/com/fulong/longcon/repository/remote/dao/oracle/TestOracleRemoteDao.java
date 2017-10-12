package com.fulong.longcon.repository.remote.dao.oracle;

import junit.framework.*;
import java.sql.*;
import com.fulong.longcon.repository.remote.data.*;
import com.fulong.common.dao.DaoTestCase;
import com.fulong.longcon.repository.remote.dao.RemoteDao;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.common.dao.JdbcDaoFactory;

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
public class TestOracleRemoteDao extends DaoTestCase {
    private RemoteDao oracleRemoteDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.remote.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        oracleRemoteDao = (RemoteDao)this.factory.getDao(RemoteDao.class);
    }

    protected void tearDown() throws Exception {
        oracleRemoteDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.
     * 2.insert方法插入一条元组
     * 3.findByID方法得到插入的记录
     * 4.判断插入的值是否被取出，否则抛出异常
     * 5.deleteByID方法删除记录
     * 6.findByID方法查找记录
     * 7.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        String displayName = "testDelete";
        RemoteData data = new RemoteData();
        data.setDisplayName(displayName);

        oracleRemoteDao.insert(data);
        String pkID = data.getID();

        RemoteData expectedReturn = data;
        RemoteData actualReturn = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteDao.deleteByID(pkID);

        RemoteData actualReturn2 = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.
     * 2.insert方法插入一条元组
     * 3.findByID方法得到插入的记录
     * 4.判断插入的值是否被取出，否则抛出异常
     * 5.delete方法删除记录
     * 6.findByID方法查找记录
     * 7.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testDelete1() throws SQLException {
        String URL = "testDeleteURl";
        int type = 2;
        String userID = "testDeleteUserID";
        RemoteData data = new RemoteData();
        data.setURL(URL);
        data.setType(type);
        data.setUserID(userID);

        //Insert one data with the special ID into the table
        oracleRemoteDao.insert(data);

        String id = data.getID();
        RemoteData expectedReturn = data;
        //Find the data with the special ID from the table using findByID()
        RemoteData actualReturn = oracleRemoteDao.findByID(id);
        //Compare the inserted data with the found data
        assertEquals("return value", expectedReturn, actualReturn);

        //Delete the data with the special ID
        oracleRemoteDao.delete(URL, type, userID);

        RemoteData actualReturn2 = oracleRemoteDao.findByID(id);
        //Make sure that the data has been deleted
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.
     * 2.insert方法插入指定PKID的一条元组
     * 3.findByID方法得到插入的记录
     * 4.判断插入的值是否被取出，否则抛出异常
     * 5.delete方法删除指定PKID的记录
     * 6.findByID方法查找指定PKID的记录
     * 7.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testDeleteByID() throws SQLException {
        String ID = "testID";
        RemoteData data = new RemoteData();
        data.setID(ID);

        //Insert one data with the special ID into the table
        oracleRemoteDao.insert(data);

        RemoteData expectedReturn = data;
        //Find the data with the special ID from the table using findByID()
        RemoteData actualReturn = oracleRemoteDao.findByID(ID);
        //Compare the inserted data with the found data
        assertEquals("return value", expectedReturn, actualReturn);

       //Delete the data with the special ID
        oracleRemoteDao.deleteByID(ID);

        RemoteData actualReturn2 = oracleRemoteDao.findByID(ID);
        //Make sure that the data has been deleted
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
        //建立num个Remoteata对象
        RemoteData datas[] = new RemoteData[num];
        String pkID[] = new String[num]; //datas的PKID数组

        //向DB中插入num个RemoteData
        for(int i=0; i<num; i++){
            StringBuffer strBuf = new StringBuffer("testFindAll");
            pkID[i] = new String( strBuf.append(i) );
            datas[i] = new RemoteData();
            datas[i].setID( pkID[i] );
            oracleRemoteDao.insert(datas[i]);
        }

        //查找DB中所有元组，放入actualReturn数组中；
        RemoteData[] actualReturn = oracleRemoteDao.findAll();

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
            oracleRemoteDao.deleteByID(pkID[j]);
            RemoteData actualReturn2 = oracleRemoteDao.findByID(pkID[j]);
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

        RemoteData data = new RemoteData();
        data.setID(pkID);
        oracleRemoteDao.insert(data);

        RemoteData expectedReturn = data;
        RemoteData actualReturn = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteDao.deleteByID(pkID);
        actualReturn = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, actualReturn);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定type的元组
     * 2.findByType方法取得对应type的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法删除插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByType() throws SQLException {
         RemoteData data = new RemoteData();
         int type = 2; //type指定为2

         data.setType(type);
         oracleRemoteDao.insert(data);
         String pkID = data.getID();

         RemoteData[] actualReturn = oracleRemoteDao.findByType(type);

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
         oracleRemoteDao.deleteByID(pkID);
         RemoteData actualReturn2 = oracleRemoteDao.findByID(pkID);
         assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定URL和type值的元组
     * 2.findByURL(url, type)方法取得对应的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByURL() throws SQLException {
        RemoteData data = new RemoteData();
        String url = "testFindByURL"; //指定url
        int type = 0;//type指定为0

        data.setURL(url);
        data.setType(type);
        oracleRemoteDao.insert(data);

        RemoteData actualReturn = oracleRemoteDao.findByURL(url, type);
        assertEquals("return value", data, actualReturn);

        //删除pkID对应的元组
        String pkID = data.getID();
        oracleRemoteDao.deleteByID(pkID);
        RemoteData actualReturn2 = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定userid的元组
     * 2.findByUser(String localUser)方法取得对应的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByUser() throws SQLException {
        RemoteData data = new RemoteData();
        String localUser = "testLocalUser"; //指定userid

        data.setUserID(localUser);
        oracleRemoteDao.insert(data);

        String pkID = data.getID();
        RemoteData[] actualReturn = oracleRemoteDao.findByUser(localUser);

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
        oracleRemoteDao.deleteByID(pkID);
        RemoteData actualReturn2 = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定userid和type值的元组
     * 2.findByUser(String localUser, int type)方法取得对应的所有记录
     * 3.在查找到的记录中查找所插入的元组，如不存在则抛出异常
     * 4.deleteByID方法插入的记录，
     * 5.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindByUser1() throws SQLException {
        RemoteData data = new RemoteData();
        String localUser = "testLocalUser&type"; //指定userid
        int type = 0;//type指定为0

        data.setUserID(localUser);
        data.setType(type);
        oracleRemoteDao.insert(data);

        String pkID = data.getID();
        RemoteData[] actualReturn = oracleRemoteDao.findByUser(localUser, type);

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
        oracleRemoteDao.deleteByID(pkID);
        RemoteData actualReturn2 = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, actualReturn2);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定PASSWORD的元组
     * 2.求取插入元组被系统分配的PKID
     * 3.findPassword方法取得对应PKID的PASSWORD
     * 4.比较原插入元组的PASSWORD和上步求取出的PASSWORD，如不相同则抛出异常
     * 5.deleteByID方法删除插入的记录，
     * 6.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindPassword() throws SQLException {
        RemoteData data = new RemoteData();
        String passWord = "testFindPassword";
        data.setPassword(passWord);

        oracleRemoteDao.insert(data);
        String pkID =  data.getID();

        String expectedReturn = passWord;
        String actualReturn = oracleRemoteDao.findPassword(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteDao.deleteByID(pkID);
        RemoteData returnData = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, returnData);
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个指定USERNAME的元组
     * 2.求取插入元组被系统分配的PKID
     * 3.findUserName方法取得对应PKID的USERNAME
     * 4.比较原插入元组的USERNAME和上步求取出的USERNAME，如不相同则抛出异常
     * 5.deleteByID方法删除插入的记录，
     * 6.assertEquals断言再次插取出的值为空
     * @throws SQLException
     */
    public void testFindUserName() throws SQLException {
        RemoteData data = new RemoteData();
        String userName = "testFindUserName";
        data.setUsername(userName);

        oracleRemoteDao.insert(data);
        String pkID =  data.getID();

        String expectedReturn = userName;
        String actualReturn = oracleRemoteDao.findUserName(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        oracleRemoteDao.deleteByID(pkID);
        RemoteData returnData = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", null, returnData);
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

        RemoteData data = new RemoteData();
        data.setID(pkID);

        //Insert one data
        oracleRemoteDao.insert(data);

        RemoteData expectedReturn = data;
        //Find the inserted data from the table and compare it with data
        RemoteData actualReturn = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", expectedReturn, actualReturn);

        //Delete the data from the table
        oracleRemoteDao.deleteByID(pkID);
        //Find the inserted data from the table and make sure that the return is null
        RemoteData actualReturn2 = oracleRemoteDao.findByID(
            pkID);
         assertEquals("return value", null, actualReturn2);
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始元组
     * 2.修改初始元组的除PKID外所有属性，并记录为新元组
     * 3.新元组的的值去update()初始元组
     * 4.用始终未变的PKID通过findByID方法查找到更新后的初始元组
     * 5.比较查找到的初始元组和新元组是否相等，不等则抛出异常
     * 6.deleteByID方法删除记录，
     * 注意：Timestamp、java.sql.Date与java.util.Date之间的区分
     * @throws SQLException
     */
    public void testUpdate() throws SQLException {
        RemoteData data = new RemoteData();
        String pkID = "testUpdate";
        oracleRemoteDao.deleteByID(pkID);

        data.setID(pkID);
        oracleRemoteDao.insert(data);

        RemoteData newData = new RemoteData();
        newData.setID(pkID);
        newData.setPassword("newPassword");
        newData.setType(5);
        newData.setURL("newUrl");
        newData.setUserID("newUserID");
        newData.setUsername("newUserName");
        newData.setDisplayName("newDiaplayName");
        newData.setCreateTime( new java.util.Date(2007, 7, 7, 7, 7, 7) );
        oracleRemoteDao.update(newData);

        RemoteData returnData = oracleRemoteDao.findByID(pkID);
        assertEquals("return value", newData, returnData);
        oracleRemoteDao.deleteByID(pkID);
        /**@todo fill in the test code*/
    }

}
