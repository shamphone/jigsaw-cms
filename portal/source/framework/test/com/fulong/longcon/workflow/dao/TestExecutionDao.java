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
public class TestExecutionDao extends DaoTestCase {
    public TestExecutionDao() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ExecutionDao executionDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        executionDao = (ExecutionDao)this.factory.getDao(ExecutionDao.class);
    }

    protected void tearDown() throws Exception {
        executionDao = null;
        super.tearDown();
    }


    public void testInsert() throws SQLException {
        ExecutionData data = new ExecutionData();
        data.setInstanceID("2340601860030");
        data.setExecutionTime(new Date(System.currentTimeMillis()));
        data.setExecutorID("2339561100270");
        data.setTransitionID("2341501902973");
        executionDao.insert(data);
        String id = data.getTransitionID();

    }

    public void testFindByPKID() throws SQLException {
        ExecutionData[] data = executionDao.findByInstanceID("2340601860030");

    }

    public void testDelete() throws SQLException {

        ExecutionData data = new ExecutionData();
        data.setInstanceID("2340601860030");
        data.setExecutionTime(new Date(System.currentTimeMillis()));
        data.setExecutorID("2339561100270");
        data.setTransitionID("2341501902973");
        executionDao.insert(data);

        executionDao.delete(data);

    }


    public void testFindFirstExecutionByPKID() throws SQLException {
        ExecutionData data = executionDao.findFirstExecutionByInstanceID(
                "2340440061842");

    }


    private void jbInit() throws Exception {
    }


}
