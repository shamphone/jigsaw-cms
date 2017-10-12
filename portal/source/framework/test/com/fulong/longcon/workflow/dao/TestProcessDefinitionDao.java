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
public class TestProcessDefinitionDao extends DaoTestCase {
    public TestProcessDefinitionDao() {

    }

    private ProcessDefinitionDao processDefinitionDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        processDefinitionDao = (ProcessDefinitionDao)this.factory.getDao(
                ProcessDefinitionDao.class);
    }

    protected void tearDown() throws Exception {
        processDefinitionDao = null;
        super.tearDown();
    }


    public void testInsert() throws SQLException {
        ProcessDefinitionData data = new ProcessDefinitionData();
        data.setBeginID("Activity01");

        data.setName("workflowNo.1");

        data.setPkid("10000002");
        data.setType("login in member");
        processDefinitionDao.insert(data);
        processDefinitionDao.delete(data.getPkid());

        /**@todo fill in the test code*/
    }


    public void testFindByPKID() throws SQLException {
        ProcessDefinitionData data = new ProcessDefinitionData();
        data = processDefinitionDao.findByPKID("2340421572436");

        /**@todo fill in the test code*/
    }


    public void testUpdate() throws SQLException {
        ProcessDefinitionData data = new ProcessDefinitionData();
        data.setBeginID("Activity01");
        data.setName("workflowNo.1");
        data.setPkid("2340421572436");
        data.setType("login in cms");
        processDefinitionDao.update(data);
        /**@todo fill in the test code*/
    }


}
