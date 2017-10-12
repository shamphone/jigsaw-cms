package com.fulong.longcon.repository;

import com.fulong.longcon.ItemExistsException;

/**
 * 设计内容大纲测试
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author not attributable
 * @version 2.0
 */
public class TestDesignScheme
    extends RepositoryTestCase {
    private NodeDefinition definition = null;
    private PropertyDefinition propertyDefinition = null;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 创建测试用内容大纲
     * @return NodeDefinition
     */
    private NodeDefinition createNodeDefinition() {
        String name = "TEST_DEFINITION";
        definition = repository.getDefinitionManager().createDefinition(name);
        return definition;
    }

    /**
     * 创建测试用属性
     * @return PropertyDefinition
     */
    private PropertyDefinition createPropertyDefinition() {
        int type = PropertyType.DATE;
        String proName = "test-date";
        propertyDefinition = createNodeDefinition().addPropertyDefinition(type,
            proName);
        return propertyDefinition;
    }

    /*测试 添加内容大纲，删除*/
    public void testAddNOdeDefinition() {
        this.createNodeDefinition();
        assertEquals("return value", definition.getName(),
                     repository.getDefinitionManager().getDefinition(definition.getID()).getName());
        //delete
        repository.getDefinitionManager().delete(definition);
        assertEquals("return value", null, repository.getDefinitionManager().getDefinition(""));
    }

    /*测试 修改内容大纲，删除*/
    public void testModifyNodeDefinition() {
        this.createNodeDefinition();

        String newName = "TEST_DEFINITION_NEW";
        definition.setName(newName);
        assertEquals("return value", newName,
                     repository.getDefinitionManager().getDefinition(definition.getID()).getName());

        String description = "测试用大纲描述";
        definition.setDescription(description);

        //delete
        repository.getDefinitionManager().delete(definition);

    }

    /*测试 复制内容大纲
     *复制大纲同时复制所有大纲属性

    public void testCopyNodeDefinition() {
        this.createPropertyDefinition();

        String name = "TEST_COPY_DEFINITION";
        NodeDefinition copyDefinition = definition.clone(name);

        assertEquals("return value", name,
                     copyDefinition.getName());

        assertEquals("return value", propertyDefinition.getID(),
                     copyDefinition.getPropertyDefinition("test-date").getID());
        //delete
        definition.delete(propertyDefinition);
        repository.delete(definition);
        copyDefinition.delete(propertyDefinition);
        repository.delete(copyDefinition);
    }
*/
    /*测试 添加属性，删除*/
    public void testAddProperty() {
        this.createPropertyDefinition();
        assertEquals("return value", propertyDefinition,
                     repository.getDefinitionManager().getDefinition(definition.getID()).
                     getPropertyDefinition(propertyDefinition.getName()));

        //delete
        definition.delete(propertyDefinition);
        repository.getDefinitionManager().delete(definition);
    }

    /*测试 修改属性，删除*/
    public void testModifyProperty() {
        this.createPropertyDefinition();
        propertyDefinition.setOrderNo(15);
        assertEquals("return value", 15,
                     definition.getPropertyDefinition("test-date").getOrderNo());
        //delete
        definition.delete(propertyDefinition);
        repository.getDefinitionManager().delete(definition);
    }

    /*测试 在一个大纲下创建两个相同PKID的属性*/
    public void testCreateTwoProperty() {
        try {
            this.createPropertyDefinition();
            int type = PropertyType.DATE;
            String proName = "test-date";
            definition.addPropertyDefinition(type,
                                             proName);
        }
        catch (ItemExistsException e) {
            definition.delete(propertyDefinition);
            repository.getDefinitionManager().delete(definition);
        }
    }

    /*测试 添加大纲分组，删除*/
    public void testAddNodeDefinitionGroup() {

    }

    /*测试 修改大纲分组，删除*/
    public void testModifyNodeDefinitionGroup() {

    }

}
