package com.fulong.longcon.repository.remote.impl;

import junit.framework.*;
import com.fulong.longcon.repository.remote.ext.*;
import com.fulong.longcon.repository.remote.data.*;
import com.fulong.longcon.repository.RepositoryTestCase;
import com.fulong.longcon.repository.remote.RemoteProperty;
import com.fulong.longcon.repository.remote.RemoteCategory;
import java.util.Iterator;

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
public class TestRemotePropertyImpl extends RepositoryTestCase {
    RemoteCategory remoteCategory = null;
    private RemoteProperty remoteProperty = null;

    //首先要保证REMOTE_CATEGORY表中有PKID='TestRemotePropertyImpl'这样一个元组(可以直接操作数据库添加)
    protected void setUp() throws Exception {
        super.setUp();
        /**@todo verify the constructors*/
        remoteCategory = remoteManager.getCategory("TestRemotePropertyImpl");

        //判断分类下是否有属性，如果有则返回第一个属性分类映射RemoteProperty
        Iterator iter = remoteCategory.getProperties();
        if ( iter.hasNext() ){
            remoteProperty = (RemoteProperty) iter.next();
        }
    }

    //释放remoteCategory、remoteProperty
    protected void tearDown() throws Exception {
        remoteProperty = null;
        remoteCategory = null;
        super.tearDown();
    }

    public void testGetBindingProperty() {
        String expectedReturn = null;
        String actualReturn = remoteProperty.getBindingProperty();
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetDefaultValue() {
        String expectedReturn = null;
        String actualReturn = remoteProperty.getDefaultValue();
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetDisplayName() {
        String expectedReturn = null;
        String actualReturn = remoteProperty.getDisplayName();
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetPropertyEditorType() {
        String expectedReturn = null;
        String actualReturn = remoteProperty.getPropertyEditorType();
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetPropertyID() {
        String expectedReturn = null;
        String actualReturn = remoteProperty.getPropertyID();
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testSetBindingProperty() {
        String bindingProperty = "";
        remoteProperty.setBindingProperty(bindingProperty);
        /**@todo fill in the test code*/
    }

    public void testSetDefaultValue() {
        String value = "";
        remoteProperty.setDefaultValue(value);
        /**@todo fill in the test code*/
    }

    public void testSetDisplayName() {
        String displayName = "";
        remoteProperty.setDisplayName(displayName);
        /**@todo fill in the test code*/
    }

    public void testSetPropertyEditorType() {
        String type = "";
        remoteProperty.setPropertyEditorType(type);
        /**@todo fill in the test code*/
    }

    public void testRemotePropertyImpl() {
        RemoteManagerExt repository = null;
        RemotePropertyData data = null;
        remoteProperty = new RemotePropertyImpl(repository, data);
        /**@todo fill in the test code*/
    }

}
