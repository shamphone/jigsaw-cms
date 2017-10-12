package com.fulong.longcon.repository.dao;

import java.sql.*;
import com.fulong.longcon.repository.data.*;
import com.fulong.common.dao.DaoTestCase;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.PropertiesDaoProvider;

/**
 * <p>Title: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Description: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Copyright: �����пƸ������������޹�˾ 2006</p>
 *
 * <p>Company: �����пƸ������������޹�˾</p>
 *
 * @author not attributable
 * @version 2.0
 */
public class TestNodeTypeDao extends DaoTestCase {
    private NodeTypeDao nodeTypeDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.repository.impl.oracle");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(true);

        nodeTypeDao = (NodeTypeDao)this.factory.getDao(NodeTypeDao.class);
    }

    protected void tearDown() throws Exception {
        nodeTypeDao = null;
        super.tearDown();
    }

    public void testDelete() throws SQLException {
        NodeTypeData data = new NodeTypeData();
        String nodeID = "nodeID";
        String definitionID = "definitionID";
        data.setID(nodeID);
        data.setDefinitionID(definitionID);
        data.setMain(true);
        nodeTypeDao.insert(data);

        NodeTypeData actualReturn = nodeTypeDao.findByDefinitionAndPKID(
            definitionID, nodeID);
        this.assertEquals(actualReturn.getID(), data.getID());
        this.assertEquals(actualReturn.getID(), data.getID());
        nodeTypeDao.deleteByDefinitionAndPKID(definitionID, nodeID);
        actualReturn = nodeTypeDao.findByDefinitionAndPKID(definitionID,
            nodeID);
        this.assertNull(actualReturn);
    }

}
