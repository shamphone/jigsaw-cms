package com.fulong.longcon.repository;

import java.util.Iterator;

/**
 *
 * <p>Title: 龙驭内容管理引擎</p>
 *
 * <p>Description: 龙驭内容管理引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class TestPropertyDefinition
    extends RepositoryTestCase {

    protected void setUp() throws Exception {
        super.setUp();
        /**@todo verify the constructors*/

    }

    protected void tearDown() throws Exception {

        super.tearDown();
    }

    public void testDefaultValues() {
        int type = PropertyType.STRING;
        String name = "This is property definition name.";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().createDefinition(
            "This is definition name.");
        PropertyDefinition prop = definition.addPropertyDefinition(type, name);
        this.assertNotNull(prop.getID());
        //确认创建成功
        this.assertEquals(prop, definition.getPropertyDefinition(prop.getID()));
        //确认可以设置缺省值
        Value value = this.repository.getValueFactory().createValue("default");
        prop.setDefaultValues(new Value[] {value});
        this.assertEquals(prop.getDefaultValues()[0], value);
        //删除节点定义和属性定义
        this.repository.getDefinitionManager().delete(definition);
        definition.delete(prop);
    }
/*
    public void testPropertyOrderNo() {
        NodeDefinition nodeDef = this.repository.getDefinitionManager().getDefinition(
            "no-properties-scheme");
        NodeDefinition nodeDef1 = repository.getDefinitionManager().createDefinition("test.1");
        NodeDefinition nodeDef2 = repository.getDefinitionManager().createDefinition("test.2");
        NodeDefinition nodeDef3 = repository.getDefinitionManager().createDefinition("test.3");
        ChildNodeDefinition cnd1 = nodeDef.addChildNodeDefinition(nodeDef1,
            "test1");
        ChildNodeDefinition cnd2 = nodeDef.addChildNodeDefinition(nodeDef2,
            "test2");
        ChildNodeDefinition cnd3 = nodeDef.addChildNodeDefinition(nodeDef3,
            "test3");
        cnd1.setOrderNo(90);
        cnd2.setOrderNo(10);
        cnd3.setOrderNo(2);

        Iterator children = nodeDef.childNodeDefinitions();
        while (children.hasNext()) {
            System.out.println( ( (ChildNodeDefinition) children.next()).
                               getOrderNo());
        }
        nodeDef.delete(cnd1);
        nodeDef.delete(cnd2);
        nodeDef.delete(cnd3);
        repository.delete(nodeDef1);
        repository.delete(nodeDef2);
        repository.delete(nodeDef3);

    }
*/
}
