package com.fulong.longcon.repository.remote.impl;

import junit.framework.*;
import com.fulong.longcon.repository.remote.*;
import com.fulong.longcon.repository.*;
import java.util.Date;
import com.fulong.longcon.repository.remote.ext.*;
import com.fulong.longcon.repository.remote.data.*;
import org.apache.commons.dbcp.BasicDataSource;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.longcon.repository.remote.dao.*;
import java.util.Iterator;
import java.sql.SQLException;
import java.util.Map;
import com.fulong.common.dao.DatabaseException;

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
 * @version 2.0
 */

public class TestRemoteCategoryImpl extends RepositoryTestCase {
    public TestRemoteCategoryImpl() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JdbcDaoFactory factory;
    private BasicDataSource datasource;
    private RemoteDao oracleRemoteDao = null;
    private RemoteCategoryDao oracleRemoteCategoryDao = null;
    private RemotePropertyDao oracleRemotePropertyDao = null;
    //private RemoteCategory remoteCategory = null;

    protected void setUp() throws Exception {
        datasource = new BasicDataSource();
        datasource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        datasource.setUrl("jdbc:oracle:thin:@192.168.0.81:1522:orcl");
        datasource.setUsername("txsd3");
        datasource.setPassword("fulong");

        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.remote.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        oracleRemoteDao = (RemoteDao)factory.getDao(RemoteDao.class);
        oracleRemotePropertyDao = (RemotePropertyDao)factory.getDao(RemotePropertyDao.class);
        oracleRemoteCategoryDao = (RemoteCategoryDao)factory.getDao(RemoteCategoryDao.class);

        super.setUp();
        /**@todo verify the constructors*/
    }

    protected void tearDown() throws Exception {
        oracleRemoteCategoryDao = null;
        oracleRemotePropertyDao = null;
        //remoteCategory = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.首先在REMOTE_CATEGORY中创建指定PKID的元组testTuple；
     * 2.对应testTuple，使用RemoteCategory::addRemoteProperty(String)方法添加内容属性:contentProperty;
     * 3.使用RemoteCategory::getProperties()取出REMOTE_PREPERTY中testTuple.CategoryID对应的所有元组;
     * 4.针对每一个取得的元组取出其REMOTE_CATEGORY_PROPERTY属性，与插入的contentProperty比较；
     * 5.如果没有元组的REMOTE_CATEGORY_PROPERTY与contentProperty一致，说明添加内容属性的操作有误，抛出异常。
     */
    public void testAddRemoteProperty() {
        String contentProperty = "TestAddRemoteProperty"; // 指定内容属性

        String pkID = "PKIDtestAddRemoteProperty";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            remoteCategory.addRemoteProperty(contentProperty); //插入指定的内容属性

            Iterator remoteProperties = remoteCategory.getProperties();
            String tempContentProperty = "";
            //循环检测被添加的内容属性是否能被找到
            while (remoteProperties.hasNext()) {
                RemoteProperty remotePropertyImplInActualreturn = (
                    RemoteProperty) remoteProperties.next();
                tempContentProperty = remotePropertyImplInActualreturn.
                    getPropertyID();

                if (contentProperty.equals(tempContentProperty)) {
                    break; //找到插入的内容属性，说明上面的插入成功，退出(此时的tempContentProperty被记录下来，以备随后断言)。
                }
            }
            assertEquals("Error occured, return value", contentProperty,
                         tempContentProperty);
            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.
     */
    //public void testGetBindingCategory() {
        //NodeDefinition expectedReturn = null;
        //NodeDefinition actualReturn = remoteCategory.getBindingCategory();
        //assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    //}


    /**
     * 测试策略：
     * 1.事先在数据库中建立PKID为测试用例中指定pkID的元组，并指定其CATEGORYID为用例中的 expectedReturn
     * 2.使用RemoteManager::getCategory()方法取出对应PKID的"RemoteCategory"对象;
     * 3.针对取出的RemoteCategory，使用RemoteCategory::getCategoryID()得到元组的CATEGORYID字段值;
     * 4.比较expectedReturn与最后取出的CATEGORYID字段值是否一致，不一致则抛出异常.
     */
    public void testGetCategoryID() {
        String expectedCategoryID = "CATEGORYIDtestGetCategoryID";
        String pkID = "PKIDtestGetCategoryID";

        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        data.setCategoryID(expectedCategoryID);
        try {
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            String actualReturn = remoteCategory.getCategoryID();

            assertEquals("return value", expectedCategoryID, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setCreateDate()重新设置对应PKID元组的CREATE_TIME;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getCreateDate()方法取出对应PKID元组现在的CREATE_TIME；
     * 5.比较比较设置的CREATE_TIME与最后取出的CREATE_TIME是否一致，不一致则抛出异常.
     */
    public void testGetCreateDate() {
        String pkID = "PKIDtestGetCreateDate";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            Date expectedReturn = new java.util.Date(2007, 7, 7, 7, 7, 7);
            remoteCategory.setCreateDate(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            Date actualReturn = newRemoteCategory.getCreateDate();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，向REMOTE_CATEGORY和REMOTE_PROPERTY中分别插入一个元组，并保持categoryID的对应；
     * 2.使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象；
     * 3.针对取出的RemoteCategory，使用RemoteCategory::setDefaultMappingValue()设置REMOTE_PROPERTY中对应PKID、contentProperty元组的DefaultValue;
     * 4.使用RemoteCategory::getDefaultMappingValue(String contengProperty)取出上步设置的DefaultValue;
     * 5.比较取出的DefaultValue与设置的值是够相等，不等则抛出异常；
     * 6.删去第一步插入的两条元组。
     */
    public void testGetDefaultMappingValue() {
        String pkID = "PKIDtestGetDefaultMappingValue";
        RemoteCategoryData categoryData = new RemoteCategoryData();
        categoryData.setID(pkID);

        String contentProperty = "ContentPropertyGestSetDefaultMappingValue";
        RemotePropertyData propertyData = new RemotePropertyData();
        propertyData.setRemoteCategoryID(pkID);
        propertyData.setRemoteCategoryProperty(contentProperty);

        try {
            oracleRemoteCategoryDao.insert(categoryData);
            oracleRemotePropertyDao.insert(propertyData);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            String value = "DefaultValueTestGetDefaultMappingValue";
            remoteCategory.setDefaultMappingValue(contentProperty, value);

            String actualReturn = remoteCategory.getDefaultMappingValue(contentProperty);

            assertEquals("return value", value, actualReturn);

            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，向REMOTE_CATEGORY和REMOTE_PROPERTY中分别插入一个元组，并保持categoryID的对应；
     * 2.使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象；
     * 3.针对取出的RemoteCategory，使用RemoteCategory::setDefaultMappingValue()设置REMOTE_PROPERTY中对应PKID、contentProperty元组的DefaultValue;
     * 4.使用RemoteCategory::getDefaultValues()取出属性&缺省值对应的Map;
     * 5.查看出入的缺省值是否存在于上步返回的Map中，不存在则抛出异常；
     * 6.删去第一步插入的两条元组。
     */
    public void testGetDefaultValues() {
        /**@todo fill in the test code*/
        String pkID = "PKIDTestGetDefaultValues";
        RemoteCategoryData categoryData = new RemoteCategoryData();
        categoryData.setID(pkID);

        String contentProperty = "ContentPropertyGestSetDefaultMappingValue";
        RemotePropertyData propertyData = new RemotePropertyData();
        propertyData.setRemoteCategoryID(pkID);
        propertyData.setRemoteCategoryProperty(contentProperty);

        try {
            oracleRemoteCategoryDao.insert(categoryData);
            oracleRemotePropertyDao.insert(propertyData);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            String value = "DefaultValueTestGetDefaultMappingValue";
            remoteCategory.setDefaultMappingValue(contentProperty, value);

            Map actualReturn = remoteCategory.getDefaultValues();

            assertEquals("return value", value, actualReturn.get(contentProperty) );

            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setDescription()重新设置对应PKID元组的DESCRIPTION;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getDescription()方法取出对应PKID元组现在的DESCRIPTION；
     * 5.比较设置的DESCRIPTION与最后取出的DESCRIPTION是否一致，不一致则抛出异常.
     */
    public void testGetDescription() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestGetDescription";
        try {
            RemoteCategoryData data = new RemoteCategoryData();
            data.setID(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            String expectedReturn = "testGetDescription";
            remoteCategory.setDescription(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            String actualReturn = newRemoteCategory.getDescription();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setDisplayName()重新设置对应PKID元组的DISPLAY_NAME;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getDisplayName()方法取出对应PKID元组现在的DISPLAY_NAME；
     * 5.比较设置的DISPLAY_NAME与最后取出的DISPLAY_NAME是否一致，不一致则抛出异常.
     */
    public void testGetDisplayName() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestGetDisplayName";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            String expectedReturn = "testGetDisplayName";
            remoteCategory.setDisplayName(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            String actualReturn = newRemoteCategory.getDisplayName();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"元组(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::getCategoryID()另外得到此对象的PKID;
     * 3.比较两次取出的PKID是否一致，不一致则抛出异常.
     */
    public void testGetID() {
        String pkID = "PKIDtestGetID";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(
                pkID);
            String actualReturn = remoteCategory.getID();
            assertEquals("return value", pkID, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setLastUpdateTime()重新设置对应PKID元组的LASTUPDATETIME;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getLastUpdateTime()方法取出对应PKID元组现在的LASTUPDATETIME；
     * 5.比较设置的LASTUPDATETIME与最后取出的LASTUPDATETIME是否一致，不一致则抛出异常.
     */
    public void testGetLastUpdateTime() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestGetLastUpdateTime";

        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            Date expectedReturn = new java.util.Date(2007, 7, 7, 7, 7, 7);
            remoteCategory.setLastUpdateTime(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            Date actualReturn = newRemoteCategory.getLastUpdateTime();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.首先在REMOTE_CATEGORY中创建指定PKID的元组testTuple；
     * 2.对应testTuple，使用RemoteCategory::addRemoteProperty(String)方法添加内容属性:contentProperty;
     * 3.使用RemoteCategory::getProperties()取出REMOTE_PREPERTY中testTuple.CategoryID对应的所有元组;
     * 4.针对每一个取得的元组取出其REMOTE_CATEGORY_PROPERTY属性，与插入的contentProperty比较；
     * 5.如果没有元组的REMOTE_CATEGORY_PROPERTY与contentProperty一致，说明没有取出新插入的元组，抛出异常。
     */
    public void testGetProperties() {
        /**@todo fill in the test code*/
        String contentProperty = "TestGetProperties"; // 指定内容属性

        String pkID = "PKIDtestGetProperties";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            remoteCategory.addRemoteProperty(contentProperty); //插入指定的内容属性

            Iterator remoteProperties = remoteCategory.getProperties();
            String tempContentProperty = "";
            //循环检测被添加的内容属性是否能被找到
            while (remoteProperties.hasNext()) {
                RemoteProperty remotePropertyImplInActualreturn = (
                    RemoteProperty) remoteProperties.next();
                tempContentProperty = remotePropertyImplInActualreturn.
                    getPropertyID();

                if (contentProperty.equals(tempContentProperty)) {
                    break; //找到插入的内容属性，说明上面的插入成功，退出(此时的tempContentProperty被记录下来，以备随后断言)。
                }
            }
            assertEquals("Error occured, return value", contentProperty,
                         tempContentProperty);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    public void testGetPropertyMapping() {
        String contentProperty = "contentPropertyTestGetPropertyMapping";
        String pkID = "PKIDTestGetPropertyMapping";
        RemoteCategoryData categoryData = new RemoteCategoryData();
        categoryData.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(categoryData);
            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            String boundProperty = "boundPropertyTestGetPropertyMapping";
            remoteCategory.setPropertyMapping(contentProperty, boundProperty);

            String actualReturn = remoteCategory.getPropertyMapping(
                contentProperty);
            assertEquals("return value", boundProperty, actualReturn);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    public void testGetRepository() {
        RemoteRepository expectedReturn = null;
        //RemoteRepository actualReturn = remoteCategory.getRepository();
        //assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.事先在数据库中建立PKID为测试用例中指定pkID的元组;
     * 2.使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象;
     * 3.使用RemoteCategory::isAutoMatched()方法得到PKID元组之AUTO_MATCH字段值;
     * 4.使用RemoteCategory::setAutoMatched()方法重新设置PKID元组之AUTO_MATCH字段值为原值的反;
     * 5.再次使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象;
     * 6.针对取出的新RemoteCategory，使用RemoteCategory::isAutoMatched()得到元组的AUTO_MATCH字段值;
     * 7.比较expectedReturn与最后取出的AUTO_MATCH字段值是否一致，不一致则抛出异常.
     */
    public void testIsAutoMatched() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestIsAutoMatched";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            boolean expectedReturn = ! (remoteCategory.isAutoMatched());
            remoteCategory.setAutoMatched(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            boolean actualReturn = newRemoteCategory.isAutoMatched();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.事先在数据库中建立PKID为测试用例中指定pkID的元组;
     * 2.使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象;
     * 3.使用RemoteCategory::isAutoUpdated()方法得到PKID元组之AUTO_UPDATE字段值;
     * 4.使用RemoteCategory::setAutoUpdated()方法重新设置PKID元组之AUTO_UPDATE字段值为原值的反;
     * 5.再次使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象;
     * 6.针对取出的新RemoteCategory，使用RemoteCategory::isAutoUpdated()得到元组的AUTO_UPDATE字段值;
     * 7.比较expectedReturn与最后取出的AUTO_UPDATE字段值是否一致，不一致则抛出异常.
     */
    public void testIsAutoUpdated() {
        String pkID = "PKIDtestIsAutoUpdated";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            boolean expectedReturn = ! (remoteCategory.isAutoUpdated());
            remoteCategory.setAutoUpdated(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            boolean actualReturn = newRemoteCategory.isAutoUpdated();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    public void testRemoveDefaultMappingValue() {
        String contentProperty = "";
        //remoteCategory.removeDefaultMappingValue(contentProperty);
        /**@todo fill in the test code*/
    }

    public void testRemoveRemoteProperty() {
        String contentProperty = "";
        //remoteCategory.removeRemoteProperty(contentProperty);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.事先在数据库中建立PKID为测试用例中指定pkID的元组;
     * 2.使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象;
     * 3.使用RemoteCategory::isAutoMatched()方法得到PKID元组之AUTO_MATCH字段值;
     * 4.使用RemoteCategory::setAutoMatched()方法重新设置PKID元组之AUTO_MATCH字段值为原值的反;
     * 5.再次使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象;
     * 6.针对取出的新RemoteCategory，使用RemoteCategory::isAutoMatched()得到元组的AUTO_MATCH字段值;
     * 7.比较expectedReturn与最后取出的AUTO_MATCH字段值是否一致，不一致则抛出异常.
     */
    public void testSetAutoMatched() {
        String pkID = "PKIDtestSetAutoMatched";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            boolean expectedReturn = ! (remoteCategory.isAutoMatched());
            remoteCategory.setAutoMatched(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            boolean actualReturn = newRemoteCategory.isAutoMatched();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.事先在数据库中建立PKID为测试用例中指定pkID的元组;
     * 2.使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象;
     * 3.使用RemoteCategory::isAutoUpdated()方法得到PKID元组之AUTO_UPDATE字段值;
     * 4.使用RemoteCategory::setAutoUpdated()方法重新设置PKID元组之AUTO_UPDATE字段值为原值的反;
     * 5.再次使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象;
     * 6.针对取出的新RemoteCategory，使用RemoteCategory::isAutoUpdated()得到元组的AUTO_UPDATE字段值;
     * 7.比较expectedReturn与最后取出的AUTO_UPDATE字段值是否一致，不一致则抛出异常.
     */
    public void testSetAutoUpdated() {
        String pkID = "PKIDtestSetAutoUpdated";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            boolean expectedReturn = ! (remoteCategory.isAutoUpdated());
            remoteCategory.setAutoUpdated(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            boolean actualReturn = newRemoteCategory.isAutoUpdated();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    public void testSetBindingCategory() {
        NodeDefinition bindingCategory = null;
        //remoteCategory.setBindingCategory(bindingCategory);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setCreateDate()重新设置对应PKID元组的CREATE_TIME;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getCreateDate()方法取出对应PKID元组现在的CREATE_TIME；
     * 5.比较比较设置的CREATE_TIME与最后取出的CREATE_TIME是否一致，不一致则抛出异常.
     */
    public void testSetCreateDate() {
        String pkID = "PKIDtestSetCreateDate";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);
            Date expectedReturn = new java.util.Date(2007, 7, 7, 7, 7, 7);
            remoteCategory.setCreateDate(expectedReturn);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            Date actualReturn = newRemoteCategory.getCreateDate();
            assertEquals("return value", expectedReturn, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，向REMOTE_CATEGORY和REMOTE_PROPERTY中分别插入一个元组，并保持categoryID的对应；
     * 2.使用RemoteManager::getCategory(String ID)方法取出对应PKID的"RemoteCategory"对象；
     * 3.针对取出的RemoteCategory，使用RemoteCategory::setDefaultMappingValue()设置REMOTE_PROPERTY中对应PKID、contentProperty元组的DefaultValue;
     * 4.使用RemoteCategory::getDefaultMappingValue(String contengProperty)取出上步设置的DefaultValue;
     * 5.比较取出的DefaultValue与设置的值是够相等，不等则抛出异常；
     * 6.删去第一步插入的两条元组。
     */
    public void testSetDefaultMappingValue() {
        String pkID = "PKIDtestSetDefaultMappingValue";
        RemoteCategoryData categoryData = new RemoteCategoryData();
        categoryData.setID(pkID);

        String contentProperty = "ContentPropertyTestSetDefaultMappingValue";
        RemotePropertyData propertyData = new RemotePropertyData();
        propertyData.setRemoteCategoryID(pkID);
        propertyData.setRemoteCategoryProperty(contentProperty);

        try {
            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(categoryData);
            oracleRemotePropertyDao.insert(propertyData);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            String value = "DefaultValueTestSetDefaultMappingValue";
            remoteCategory.setDefaultMappingValue(contentProperty, value);

            String actualReturn = remoteCategory.getDefaultMappingValue(contentProperty);

            assertEquals("return value", value, actualReturn);

            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setDescription()重新设置对应PKID元组的DESCRIPTION;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getDescription()方法取出对应PKID元组现在的DESCRIPTION；
     * 5.比较设置的DESCRIPTION与最后取出的DESCRIPTION是否一致，不一致则抛出异常.
     */
    public void testSetDescription() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestSetDescription";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            String newDescription = "testSetDescription";
            remoteCategory.setDescription(newDescription);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            String actualReturn = newRemoteCategory.getDescription();
            assertEquals("return value", newDescription, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setDisplayName()重新设置对应PKID元组的DISPLAY_NAME;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getDisplayName()方法取出对应PKID元组现在的DISPLAY_NAME；
     * 5.比较设置的DISPLAY_NAME与最后取出的DISPLAY_NAME是否一致，不一致则抛出异常.
     */
    public void testSetDisplayName() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestSetDisplayName";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            String newDisplayName = "testSetDisplayName";
            remoteCategory.setDisplayName(newDisplayName);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            String actualReturn = newRemoteCategory.getDisplayName();
            assertEquals("return value", newDisplayName, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    /**
     * 测试策略：
     * 1.指定PKID的值，使用RemoteManager::getCategory(String ID)方法取出所有对应PKID的"RemoteCategory"对象(事先在数据库中建立对应PKID的元组);
     * 2.针对取出的RemoteCategory，使用RemoteCategory::setLastUpdateTime()重新设置对应PKID元组的LASTUPDATETIME;
     * 3.再次使用RemoteManager::getCategory(String ID)取出修改后的"RemoteCategory";
     * 4.使用RemoteCategory::getLastUpdateTime()方法取出对应PKID元组现在的LASTUPDATETIME；
     * 5.比较设置的LASTUPDATETIME与最后取出的LASTUPDATETIME是否一致，不一致则抛出异常.
     */
    public void testSetLastUpdateTime() {
        /**@todo fill in the test code*/
        String pkID = "PKIDtestSetLastUpdateTime";
        RemoteCategoryData data = new RemoteCategoryData();
        data.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(data);

            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            Date newLastUpdateTime = new java.util.Date(2007, 7, 7, 7, 7, 7);
            remoteCategory.setLastUpdateTime(newLastUpdateTime);

            RemoteCategory newRemoteCategory = remoteManager.getCategory(pkID);
            Date actualReturn = newRemoteCategory.getLastUpdateTime();
            assertEquals("return value", newLastUpdateTime, actualReturn);

            oracleRemoteCategoryDao.delete(pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    public void testSetPropertyMapping() {
        //String contentProperty = "";
        //String sourceProperty = "";
        //RemoteProperty expectedReturn = null;
        //RemoteProperty actualReturn = remoteCategory.setPropertyMapping(contentProperty, sourceProperty);
        //assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
        String contentProperty = "contentPropertyTestSetPropertyMapping";
        String pkID = "PKIDTestSetPropertyMapping";
        RemoteCategoryData categoryData = new RemoteCategoryData();
        categoryData.setID(pkID);
        try {
            oracleRemoteCategoryDao.delete(pkID);
            oracleRemoteCategoryDao.insert(categoryData);
            RemoteCategory remoteCategory = remoteManager.getCategory(pkID);

            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
            String boundProperty = "boundPropertyTestSetPropertyMapping";
            remoteCategory.setPropertyMapping(contentProperty, boundProperty);

            String actualReturn = remoteCategory.getPropertyMapping(
                contentProperty);
            assertEquals("return value", boundProperty, actualReturn);

            oracleRemotePropertyDao.removeByProperty(contentProperty, pkID);
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        }
    }

    private void jbInit() throws Exception {
    }

}
