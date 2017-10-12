package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;
import com.fulong.longcon.repository.data.NodeDefinitionData;

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
public class TestNodeDefinitionDao extends DaoTestCase {
    private NodeDefinitionDao nodeDefinitionDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        nodeDefinitionDao = (NodeDefinitionDao)this.factory.getDao(
                NodeDefinitionDao.class);
    }

    protected void tearDown() throws Exception {
        nodeDefinitionDao = null;
        super.tearDown();
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，注意，该记录依然存在，但其被删除标志为“1”。
     * 5.assertEquals断言插取出的值和原来的值的名字相同。
     * 此处，是否考虑提供接口，表示该记录已经被删除。
     * 否则，无法在单元测试知道该记录已经被删除了。
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        NodeDefinitionData data = new NodeDefinitionData();
        String name = "Name";
        data.setName(name);
        //data.setParentID("root");
        nodeDefinitionDao.insert(data);

        String id = data.getID();
        NodeDefinitionData actualReturn = nodeDefinitionDao.findByID(id);
        assertEquals("return value", name, actualReturn.getName());

        nodeDefinitionDao.delete(id);
        actualReturn = nodeDefinitionDao.findByID(id);
        assertEquals("return value", null, actualReturn);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，注意，该记录依然存在，但其被删除标志为“1”。
     * 5.assertEquals断言插取出的值和原来的值的名字相同。
     * 此处，是否考虑提供接口，表示该记录已经被删除。
     * 否则，无法在单元测试知道该记录已经被删除了。
     * @throws SQLException
     */
    public void testFindByID() throws SQLException {
        NodeDefinitionData data = new NodeDefinitionData();
        String name = "Name";
        data.setName(name);
        nodeDefinitionDao.insert(data);

        String id = data.getID();
        NodeDefinitionData actualReturn = nodeDefinitionDao.findByID(id);
        assertEquals("return value", name, actualReturn.getName());

        nodeDefinitionDao.delete(id);
        actualReturn = nodeDefinitionDao.findByID(id);
      //  assertEquals("return value", name, actualReturn.getName());

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该记录，注意，该记录依然存在，但其被删除标志为“1”。
     * 5.assertEquals断言插取出的值和原来的值的名字相同。
     * 此处，是否考虑提供接口，表示该记录已经被删除。
     * 否则，无法在单元测试知道该记录已经被删除了。
     * @throws SQLException
     */
    public void testInsert() throws SQLException {
        NodeDefinitionData data = new NodeDefinitionData();
        String name = "Name";
        data.setName(name);
        nodeDefinitionDao.insert(data);

        String id = data.getID();
        NodeDefinitionData actualReturn = nodeDefinitionDao.findByID(id);
        assertEquals("return value", name, actualReturn.getName());

        nodeDefinitionDao.delete(id);
        actualReturn = nodeDefinitionDao.findByID(id);
       // assertEquals("return value", name, actualReturn.getName());

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.findByID方法得到该记录
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.update方法
     * 5.assertEquals断言更新后的值和取出的值相等
     * 4.delete方法删除该记录，注意，该记录依然存在，但其被删除标志为“1”。
     * 5.assertEquals断言插取出的值和原来的值的名字相同。
     * 此处，是否考虑提供接口，表示该记录已经被删除。
     * 否则，无法在单元测试知道该记录已经被删除了。
     * @throws SQLException
     */
    public void testUpdate() throws SQLException {
        NodeDefinitionData data = new NodeDefinitionData();
        String name = "Name";
        data.setName(name);
        nodeDefinitionDao.insert(data);

        String id = data.getID();
        NodeDefinitionData actualReturn = nodeDefinitionDao.findByID(id);
        assertEquals("return value", name, actualReturn.getName());
        String name1 = "name1";
        data.setName(name1);
        //data.setParentID("new.root");
        nodeDefinitionDao.update(data);
        actualReturn = nodeDefinitionDao.findByID(id);
        assertEquals("return value", name1, actualReturn.getName());
        nodeDefinitionDao.delete(id);
        actualReturn = nodeDefinitionDao.findByID(id);
        //assertEquals("return value", name1, actualReturn.getName());
    }

}
