package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;
import com.fulong.longcon.repository.data.NodeData;

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
 * @author lishaobo@fulong.com.cn
 * @version 1.0
 */
public class TestNodeDao extends DaoTestCase {
    private NodeDao nodeDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        nodeDao = (NodeDao)this.factory.getDao(NodeDao.class);
    }

    protected void tearDown() throws Exception {
        nodeDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.countByParent方法得到需要的值
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testCountByParent() throws SQLException {
        NodeData data = new NodeData();
        String definitionID = "DefinitionID";
        String parentID = "ParentID";
        data.setParentID(parentID);

        nodeDao.insert(data);
        String ID = data.getID();

        long expectedReturn = 1L;
        long actualReturn = nodeDao.countByParent(parentID);
        assertEquals("return value", expectedReturn, actualReturn);

        nodeDao.delete(ID);
        expectedReturn = 0L;
        actualReturn = nodeDao.countByParent(parentID);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        NodeData data = new NodeData();
        String definitionID = "DefinitionID";
        String parentID = "ParentID";
        data.setParentID(parentID);

        nodeDao.insert(data);
        String ID = data.getID();

        NodeData actualReturn = nodeDao.findByID(ID);
        assertEquals("return value", data, actualReturn);

        nodeDao.delete(ID);
        actualReturn = nodeDao.findByID(ID);
        assertEquals("return value", null, actualReturn);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.deleteByDefinition方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */


    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testFindByID() throws SQLException {
        NodeData data = new NodeData();
        String definitionID = "DefinitionID";
        String parentID = "ParentID";
        data.setParentID(parentID);

        nodeDao.insert(data);
        String ID = data.getID();

        NodeData actualReturn = nodeDao.findByID(ID);
        assertEquals("return value", data, actualReturn);

        nodeDao.delete(ID);
        actualReturn = nodeDao.findByID(ID);
        assertEquals("return value", null, actualReturn);

    }


    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByParent方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testFindByParent() throws SQLException {

        NodeData data = new NodeData();
        String definitionID = "DefinitionID";
        String parentID = "ParentID";
        data.setParentID(parentID);

        nodeDao.insert(data);
        String ID = data.getID();

        long fromIndex = 0L;
        long number = 10L;
        NodeData[] expectedReturn = null;
        NodeData[] actualReturn = nodeDao.findByParent(parentID, fromIndex,
                number);
        assertEquals("return value", 1, actualReturn.length);


        nodeDao.delete(ID);
        actualReturn = nodeDao.findByParent(parentID, fromIndex,
                                            number);
        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testInsert() throws SQLException {
        NodeData data = new NodeData();
        String definitionID = "DefinitionID";
        String parentID = "ParentID";
        data.setParentID(parentID);

        nodeDao.insert(data);
        String ID = data.getID();

        NodeData actualReturn = nodeDao.findByID(ID);
        assertEquals("return value", data, actualReturn);

        nodeDao.delete(ID);
        actualReturn = nodeDao.findByID(ID);
        assertEquals("return value", null, actualReturn);
    }

}
