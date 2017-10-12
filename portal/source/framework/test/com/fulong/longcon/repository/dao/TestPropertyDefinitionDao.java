package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;
import com.fulong.longcon.repository.data.PropertyDefinitionData;

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
public class TestPropertyDefinitionDao extends DaoTestCase {
    private PropertyDefinitionDao propertyDefinitionDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        propertyDefinitionDao = (PropertyDefinitionDao)this.factory.getDao(
                PropertyDefinitionDao.class);
    }

    protected void tearDown() throws Exception {
        propertyDefinitionDao = null;
        super.tearDown();
    }

    /**
     * 1:插入数据，并获取该数据的ID
     * 2：通过findByNodeDefinition方法查询出刚才插入的数据，
     * 3：通过断言测试
     * 4：删除该数据
     * 5：通过findByNodeDefinition方法查询出刚才插入的数据
     * 6：通过断言确定该数据已经被删除
     * @throws SQLException
     */
    public void testDelete() throws SQLException {
        PropertyDefinitionData data = new PropertyDefinitionData();

        int maxLength = 5;
        int minLength = 1;
        boolean isMultiple = true;
        String name = "name";
        String nodeDefinitionID = "NodeDefinitionID";
        int type = 1;

        data.setMaxLength(maxLength);
        data.setMinLength(minLength);
        data.setMultiple(isMultiple);
        data.setName(name);
        data.setNodeDefinitionID(nodeDefinitionID);
        data.setType(type);
        data.setReadOnly(false);
        data.setNodeType("test.node.definition");
        propertyDefinitionDao.insert(data);

        String ID = data.getID();

        PropertyDefinitionData[] actualReturn = propertyDefinitionDao.
                                                findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", nodeDefinitionID,
                     actualReturn[0].getNodeDefinitionID());

        propertyDefinitionDao.delete(nodeDefinitionID, ID);

        actualReturn = propertyDefinitionDao.findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 1:插入数据，并获取该数据的ID
     * 2：通过findByNodeDefinition方法查询出刚才插入的数据，
     * 3：通过断言测试
     * 4：删除该数据
     * 5：通过findByNodeDefinition方法查询出刚才插入的数据
     * 6：通过断言确定该数据已经被删除
     * @throws SQLException
     */
    public void testFindByNodeDefinition() throws SQLException {
        PropertyDefinitionData data = new PropertyDefinitionData();

        int maxLength = 5;
        int minLength = 1;
        boolean isMultiple = true;
        String name = "name";
        String nodeDefinitionID = "NodeDefinitionID";
        int type = 1;

        data.setMaxLength(maxLength);
        data.setMinLength(minLength);
        data.setMultiple(isMultiple);
        data.setName(name);
        data.setNodeDefinitionID(nodeDefinitionID);
        data.setType(type);
        propertyDefinitionDao.insert(data);

        String ID = data.getID();
        PropertyDefinitionData[] actualReturn = propertyDefinitionDao.
                                                findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", nodeDefinitionID,
                     actualReturn[0].getNodeDefinitionID());

        propertyDefinitionDao.delete(nodeDefinitionID, ID);

        actualReturn = propertyDefinitionDao.findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 1:插入数据，并获取该数据的ID
     * 2：通过findByNodeDefinition方法查询出刚才插入的数据，
     * 3：通过断言测试
     * 4：删除该数据
     * 5：通过findByNodeDefinition方法查询出刚才插入的数据
     * 6：通过断言确定该数据已经被删除
     * @throws SQLException
     */
    public void testInsert() throws SQLException {
        PropertyDefinitionData data = new PropertyDefinitionData();

        int maxLength = 5;
        int minLength = 1;
        boolean isMultiple = true;
        String name = "name";
        String nodeDefinitionID = "NodeDefinitionID";
        int type = 1;

        data.setMaxLength(maxLength);
        data.setMinLength(minLength);
        data.setMultiple(isMultiple);
        data.setName(name);
        data.setNodeDefinitionID(nodeDefinitionID);
        data.setType(type);
        propertyDefinitionDao.insert(data);

        String ID = data.getID();
        PropertyDefinitionData[] actualReturn = propertyDefinitionDao.
                                                findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", nodeDefinitionID,
                     actualReturn[0].getNodeDefinitionID());

        propertyDefinitionDao.delete(nodeDefinitionID, ID);

        actualReturn = propertyDefinitionDao.findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", 0, actualReturn.length);
    }

    /**
     * 1:插入数据，并获取该数据的ID，通过update方法修改此数据
     * 2：通过findByNodeDefinition方法查询出刚才插入的数据，
     * 3：通过断言测试
     * 4：删除该数据
     * 5：通过findByNodeDefinition方法查询出刚才插入的数据
     * 6：通过断言确定该数据已经被删除
     * @throws SQLException
     */
    public void testUpdate() throws SQLException {
        PropertyDefinitionData data = new PropertyDefinitionData();

        int maxLength = 5;
        int minLength = 1;
        boolean isMultiple = true;
        String name = "name";
        String nodeDefinitionID = "NodeDefinitionID";
        int type = 1;

        data.setMaxLength(maxLength);
        data.setMinLength(minLength);
        data.setMultiple(isMultiple);
        data.setName(name);
        data.setNodeDefinitionID(nodeDefinitionID);
        data.setType(type);
        propertyDefinitionDao.insert(data);

        String ID = data.getID();
        int udpatedMax = 10;
        data.setMaxLength(udpatedMax);
        propertyDefinitionDao.update(data);

        PropertyDefinitionData[] actualReturn = propertyDefinitionDao.
                                                findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", udpatedMax,
                     actualReturn[0].getMaxLength());

        propertyDefinitionDao.delete(nodeDefinitionID, ID);

        actualReturn = propertyDefinitionDao.findByNodeDefinition(
                nodeDefinitionID,ID);
        assertEquals("return value", 0, actualReturn.length);

    }

}
