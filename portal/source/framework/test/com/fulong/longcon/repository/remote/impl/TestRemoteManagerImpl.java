package com.fulong.longcon.repository.remote.impl;

import junit.framework.*;
import java.net.*;
import com.fulong.longcon.repository.remote.*;
import java.util.*;
import java.security.*;
import com.fulong.longcon.repository.*;
import javax.sql.*;
import com.fulong.common.dao.*;

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

public class TestRemoteManagerImpl extends RepositoryTestCase {


    protected void setUp() throws Exception {
        super.setUp();

    }

    protected void tearDown() throws Exception {
        remoteManager = null;
        super.tearDown();
    }


    /**
     * 测试策略：
     * 1.使用getAllCategories()方法取出所有category
     * 2.针对每一个取出的category，根据其PKID使用getCategory(ID)另外得到category对象
     * 3.比较两次取出的category对象是否一致，不一致则抛出异常
     */
    public void testGetAllCategories() {
        RemoteCategory expectedReturn = null;
        Iterator<? extends RemoteCategory> actualReturn = remoteManager.getAllCategories();

        while(actualReturn.hasNext()){
            RemoteCategory remoteCategoryImplInActualreturn = (RemoteCategory)actualReturn.next();
            String pkID = remoteCategoryImplInActualreturn.getID();
            expectedReturn = remoteManager.getCategory(pkID);
            assertEquals("return value", expectedReturn, remoteCategoryImplInActualreturn);
        }

        /*
        RemoteCategory category = remoteManager.getCategory("2453302066722");
        category.setDefaultMappingValue("testProperty1", "testPropertyValue1");
        category.setDefaultMappingValue("testProperty2", "testPropertyValue2");
        Map map = category.getDefaultValues();
        this.assertEquals(map.get("testProperty2"), "testPropertyValue2");
        this.assertEquals(category.getDefaultMappingValue("testProperty2"), "testPropertyValue2");
        //*/
    }

    /**
     * 测试策略：
     * 1.指定type的值，使用getAllCategories(type)方法取出所有对应type的category对象
     * 2.针对每一个取出的category，根据其PKID使用getCategory(ID)另外得到category对象
     * 3.比较两次取出的category对象是否一致，不一致则抛出异常
     */
    public void testGetAllCategories1() {
        /**@todo fill in the test code*/
        int type = 0;
        RemoteCategory expectedReturn = null;
        Iterator actualReturn = remoteManager.getAllCategories(type);

        while(actualReturn.hasNext()){
            RemoteCategory remoteCategoryImplInActualreturn = (RemoteCategory)actualReturn.next();
            String pkID = remoteCategoryImplInActualreturn.getID();
            expectedReturn = remoteManager.getCategory(pkID);
            assertEquals("return value", expectedReturn, remoteCategoryImplInActualreturn);
        }
    }

    public void testGetAllCategories2() {
        int type = 0;
        Principal localUser = null;
        Iterator expectedReturn = null;
        Iterator actualReturn = remoteManager.getAllCategories(type, localUser);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetAllCategories3() {
        int type = 0;
        Principal localUser = null;
        NodeDefinition definition = null;
        Iterator expectedReturn = null;
        Iterator actualReturn = remoteManager.getAllCategories(type, localUser, definition);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.使用getAllCategories()方法取出所有category
     * 2.针对每一个取出的category，根据其PKID使用getCategory(ID)另外得到category对象
     * 3.比较两次取出的category对象是否一致，不一致则抛出异常
     */
    public void testGetCategory() {
        RemoteCategory expectedReturn = null;
        Iterator<? extends RemoteCategory> actualReturn = remoteManager.getAllCategories();

        while(actualReturn.hasNext()){
            RemoteCategory remoteCategoryImplInActualreturn = (RemoteCategory)actualReturn.next();
            String pkID = remoteCategoryImplInActualreturn.getID();
            expectedReturn = remoteManager.getCategory(pkID);
            assertEquals("return value", expectedReturn, remoteCategoryImplInActualreturn);
        }
    }



    public void testGetLogManager() {
        Principal principal = null;
        LogManager expectedReturn = null;
        LogManager actualReturn = remoteManager.getLogManager(principal);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetRemoteRepositoryCollection() {
        Principal principal = null;
        int type = 0;
        RepositoryCollection expectedReturn = null;
        RepositoryCollection actualReturn = remoteManager.getRemoteRepositoryCollection(principal, type);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    /**
     * 测试策略：
     * 1.使用getRepositories()方法取出所有Repository
     * 2.针对每一个取出的Repository，根据其PKID使用getRepository(ID)另外得到Repository对象
     * 3.比较两次取出的Repository对象是否一致，不一致则抛出异常
     */
    public void testGetRepositories() {
        RemoteRepository expectedReturn = null;
        Iterator<? extends RemoteRepository> actualReturn = remoteManager.getRepositories();

        while(actualReturn.hasNext()){
            RemoteRepository remoteRepositoryImplInActualreturn = (RemoteRepository)actualReturn.next();
            String pkID = remoteRepositoryImplInActualreturn.getID();
            expectedReturn = remoteManager.getRepository(pkID);
            assertEquals("return value", expectedReturn, remoteRepositoryImplInActualreturn);
        }
    }

    /**
     * 测试策略：
     * 1.任意指定type的值，使用getRepositories(type)方法取出所有对应type的Repository对象
     * 2.针对每一个取出的Repository，根据其PKID使用getRepository(ID)另外得到Repository对象
     * 3.比较两次取出的Repository对象是否一致，不一致则抛出异常
     */
    public void testGetRepositories3() {
        int type = 0;
        RemoteRepository expectedReturn = null;
        Iterator<? extends RemoteRepository> actualReturn = remoteManager.getRepositories(type);

        while(actualReturn.hasNext()){
            RemoteRepository remoteRepositoryImplInActualreturn = (RemoteRepository)actualReturn.next();
            String pkID = remoteRepositoryImplInActualreturn.getID();
            expectedReturn = remoteManager.getRepository(pkID);
            assertEquals("return value", expectedReturn, remoteRepositoryImplInActualreturn);
        }
    }

    /**
     * 测试策略：
     * 1.使用getRepositories()方法取出所有Repository
     * 2.针对每一个取出的Repository，根据其PKID使用getRepository(ID)另外得到Repository对象
     * 3.比较两次取出的Repository对象是否一致，不一致则抛出异常
     */
    public void testGetRepository1() {
        RemoteRepository expectedReturn = null;
        Iterator<? extends RemoteRepository> actualReturn = remoteManager.getRepositories();

        while(actualReturn.hasNext()){
            RemoteRepository remoteRepositoryImplInActualreturn = (RemoteRepository)actualReturn.next();
            String pkID = remoteRepositoryImplInActualreturn.getID();
            expectedReturn = remoteManager.getRepository(pkID);
            assertEquals("return value", expectedReturn, remoteRepositoryImplInActualreturn);
        }
    }


}
