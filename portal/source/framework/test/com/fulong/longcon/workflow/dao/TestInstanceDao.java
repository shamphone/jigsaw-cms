package com.fulong.longcon.workflow.dao;

import junit.framework.*;
import java.sql.*;
import com.fulong.longcon.workflow.data.*;

/**
 *
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class TestInstanceDao extends DaoTestCase {
    public TestInstanceDao() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private InstanceDao instanceDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        instanceDao = (InstanceDao)this.factory.getDao(InstanceDao.class);
    }

    protected void tearDown() throws Exception {
        instanceDao = null;
        super.tearDown();
    }


    public void testInsert() throws SQLException {
        InstanceData data = new InstanceData();
        data.setDefinitionID("2341637516320");
        data.setState(2);
        instanceDao.insert(data);
    }


    public void testDelete() throws SQLException {
        InstanceData data = new InstanceData();
        data.setDefinitionID("2341637516320");
        data.setState(2);
        instanceDao.insert(data);

        instanceDao.delete(data);
    }


    public void testFindByPKID() throws SQLException {
        InstanceData data = new InstanceData();
        data.setDefinitionID("2341637516320");
        data.setState(2);
        instanceDao.insert(data);
        String id = data.getPkid();

        data = instanceDao.findByPKID("id");
    }

    public void testUpdate() throws SQLException {
        InstanceData data = new InstanceData();
       data.setDefinitionID("2341637516320");
       data.setState(2);
       instanceDao.insert(data);
       String id = data.getPkid();
       data.setDefinitionID("2341637516320");

       instanceDao.update(data);
    }

    private void jbInit() throws Exception {
    }


}
