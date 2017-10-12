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
public class TestTransitionDao extends DaoTestCase {
    public TestTransitionDao() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private TransitionDao transitionDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        transitionDao = (TransitionDao)this.factory.getDao(TransitionDao.class);
    }

    protected void tearDown() throws Exception {
        transitionDao = null;
        super.tearDown();
    }


    public void testInsert() throws SQLException {
        TransitionData data = new TransitionData();
        data.setName("Transition01");
        data.setPkid("10000002");
        data.setType("member");
        data.setDefinitionID("2340421572436");
        data.setBeginID("Activity01");
        data.setEndID("Activity02");
        transitionDao.insert(data);
        String id = data.getPkid();

    }


    public void testDelete() throws SQLException {

        transitionDao.delete("2340448277936","2340421572436");

    }


    public void testFindByPKID() throws SQLException {
        TransitionData data = new TransitionData();
        data = transitionDao.findByPKID("2340448415250","2340421572436");

    }

    public void testUpdate() throws SQLException {
        TransitionData data = new TransitionData();

        data.setName("Activity00001");
        data.setPkid("2340448415250");
        data.setType("cms");
        data.setDefinitionID("2340421572436");
        transitionDao.update(data);

    }

    public void testFindByDefinitionID() throws SQLException {
        TransitionData[] data = transitionDao.findByDefinitionID(
                "2340421572436");

    }

    private void jbInit() throws Exception {
    }


}
