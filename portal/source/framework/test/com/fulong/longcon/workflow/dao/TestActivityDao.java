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
public class TestActivityDao extends DaoTestCase {
    public TestActivityDao() {

    }

    private ActivityDao activityDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        activityDao = (ActivityDao)this.factory.getDao(ActivityDao.class);
    }

    protected void tearDown() throws Exception {
        activityDao = null;
        super.tearDown();
    }


    public void testInsert() throws SQLException {
        ActivityData data = new ActivityData();
        data.setName("Activity01");
        data.setPkid("10000002");
        data.setType("login in member");
        data.setDefinitionID("2340421572436");
        activityDao.insert(data);
        String id = data.getPkid();

    }


    public void testDelete() throws SQLException {
        ActivityData data = new ActivityData();
        data.setName("Activity01");
        data.setPkid("10000002");
        data.setType("login in member");
        data.setDefinitionID("2340421572436");
        activityDao.insert(data);
        String id = data.getPkid();


        activityDao.delete(data.getPkid(),data.getDefinitionID());

    }

    public void testFindByPKID() throws SQLException {
        ActivityData data = new ActivityData();
        data.setName("Activity01");
        data.setPkid("10000002");
        data.setType("login in member");
        data.setDefinitionID("2340421572436");
        activityDao.insert(data);
        String id = data.getPkid();

        data = activityDao.findByPKID(id,data.getDefinitionID());

    }

    public void testUpdate() throws SQLException {
        ActivityData data = new ActivityData();
        data.setName("Activity01");
        data.setPkid("10000002");
        data.setType("login in member");
        data.setDefinitionID("2340421572436");
        activityDao.insert(data);
        data.setName("after");

        activityDao.update(data);

    }



}
