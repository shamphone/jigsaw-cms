package com.fulong.longcon.repository;

import java.util.*;

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
public class TestNodeDefinition
    extends RepositoryTestCase {
    private NodeDefinition definition = null;

    public void testCreateNodeDefinition() {
        /*
                int type = PropertyType.STRING;
                String name = "This is property definition name.";
                String propValue = "propValue";
         NodeDefinition superDef = repository.getDefinition("affair-scheme");
                NodeDefinition def = superDef.addNodeDefinition(superDef,
                    "test20080122");
         PropertyDefinition prop = def.addPropertyDefinition(type, name);
                this.assertNotNull(prop.getID());
                this.assertFalse(prop.isProtected());
         PropertyDefinition inheritedPro =  def.getPropertyDefinition("affair-open");
                this.assertTrue(inheritedPro.isProtected());
         */
    }



    public void testChildNodeDefinition() {
        definition = repository.getDefinitionManager().createDefinition("test.def",
            repository.getDefinitionManager().
            getDefinition(
            "no-properties-scheme"));
        NodeDefinition nd = repository.getDefinitionManager().createDefinition(
        "test.def2", null);

        ChildNodeDefinition cd = definition.addChildNodeDefinition(nd,
            "test.property");
        this.assertEquals(cd.getNodeDefinition().getID(),((ChildNodeDefinition)
                          definition.getPropertyDefinition(cd.getID())).
                          getNodeDefinition().getID());

        this.assertEquals(cd.getNodeDefinition().getName(), "test.def2");
        definition.delete(cd);
        this.repository.getDefinitionManager().delete(definition);
        this.repository.getDefinitionManager().delete(nd);

    }

    public void testAddPropertyDefinition() {
        int type = PropertyType.STRING;
        String name = "This is property definition name";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().createDefinition(
            "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(type, name);
        this.assertNotNull(prop.getID());
        this.assertFalse(prop.isProtected());

        this.assertEquals(((PropertyDefinition)definition.getDeclaredPropertyDefinitions().next()).getID(), prop.getID());
        //确认创建成功
        this.assertEquals(prop, definition.getPropertyDefinition(prop.getID()));
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        this.assertEquals(property.getType(), type);

        this.assertEquals( property.getDefinition(),prop);


        node.setProperty(prop.getID(), propValue);
        this.assertEquals(property.getString(), propValue);
        //测试删除,删除之后,根据ID无法查到该节点定义.
        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(definition.getID()));

    }

    public void testDeletePropertyDefinition() {
        int type = PropertyType.BINARY;
        String name = "This is property definition name";
        NodeDefinition definition = repository.getDefinitionManager().createDefinition(
            "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(type, name);
        this.assertNotNull(prop.getID());
        //确认创建成功
        this.assertEquals(prop, definition.getPropertyDefinition(prop.getID()));
        //测试删除,删除之后,根据ID无法查到该节点定义,但是并不实际删除这个定义下的节点。
        definition.delete(prop);
        this.assertNull(definition.getPropertyDefinition(prop.getID()));
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(definition.getID()));

    }

    public void testPropertyDefinitions() {
        int type = PropertyType.STRING;
        String name = "This is property definition name";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().createDefinition(
            "This is definition name", repository.getDefinitionManager().getDefinition("no-properties-scheme"));
        PropertyDefinition propDefinition = definition.addPropertyDefinition(
            type, name);
        this.assertNotNull(propDefinition.getID());
        //确认创建成功
        this.assertEquals(propDefinition,
                          definition.getPropertyDefinition(propDefinition.getID()));
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(propDefinition.getID());
        this.assertNotNull(property);
        node.setProperty(propDefinition.getID(), propValue);
        this.assertEquals(property.getString(), propValue);
        //可遍历到这个属性
        Iterator iterator = definition.propertyDefinitions();
        this.assertEquals(iterator.next(), propDefinition);

        propDefinition.setReadonly(true);
        this.assertEquals(true, propDefinition.isReadonly());
        iterator = definition.propertyDefinitions(false);
        this.assertEquals(iterator.hasNext(), false);

        //测试删除,删除之后,无法遍历到任何属性
        definition.delete(propDefinition);
        iterator = definition.propertyDefinitions();
        this.assertFalse(iterator.hasNext());
        this.repository.getDefinitionManager().delete(definition);

    }

    public void testPropertyDefinitionsByType() {
        int type = PropertyType.STRING;
        String name = "This is property definition name";
        String propValue = "propValue";
        //从|"root"大纲继承下来的大纲
        NodeDefinition definition = repository.getDefinitionManager().createDefinition(
            "This is definition name");
        PropertyDefinition propDefinition = definition.addPropertyDefinition(
            type, name);
        this.assertNotNull(propDefinition.getID());
        //确认创建成功
        this.assertEquals(propDefinition,
                          definition.getPropertyDefinition(propDefinition.getID()));
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(propDefinition.getID());
        this.assertNotNull(property);
        node.setProperty(propDefinition.getID(), propValue);
        this.assertEquals(property.getString(), propValue);
        //可遍历到这个属性
        Iterator iterator = definition.propertyDefinitions(type);
        this.assertTrue(iterator.hasNext());

        //测试删除,删除之后,根据ID无法查到该节点定义.
        //测试删除,删除之后,无法遍历到任何属性
        definition.delete(propDefinition);

        this.repository.getDefinitionManager().delete(definition);

    }

    public void testSetName() {
        //测试可以设置名称
        String name = "This is definition name";
        NodeDefinition definition = repository.getDefinitionManager().createDefinition(name);
        this.assertEquals(definition.getName(), name);
        name = name + name;
        definition.setName(name);
        this.assertEquals(definition.getName(), name);

        String des ="description";
        definition.setDescription(des);
        this.assertEquals(definition.getDescription(), des);


        //测试名称是否可以持久化
        definition = repository.getDefinitionManager().getDefinition(definition.getID());
        this.assertEquals(definition.getName(), name);
        this.repository.getDefinitionManager().delete(definition);

    }

    /**
     *测试获取大纲所有属性 包括复合属性

    public void testGetProperties() {
        NodeDefinition definition = repository.getDefinitionManager().getDefinition(
            "content-category-scheme");
        //非复合属性
        Iterator it = definition.propertyDefinitions(true);
        while (it.hasNext()) {
            PropertyDefinition pd = (PropertyDefinition) it.next();
            System.out.println("非复合属性 :" + pd.getID() + ";" + pd.getName());
        }
        //复合属性
        Iterator iterator = definition.childNodeDefinitions();
        //遍历definition下的所有复合属性
        while (iterator.hasNext()) {
            ChildNodeDefinition pro = (ChildNodeDefinition) iterator.next();
            System.out.println("复合属性 :" + pro.getID() + ";" + pro.getName());
            //遍历复合属性对应大纲下的所有非复合属性
            Iterator tor = pro.getNodeDefinition().propertyDefinitions();
            while (tor.hasNext()) {
                PropertyDefinition property = (PropertyDefinition) tor.next();
                System.out.println(pro.getName() + "的 非复合属性 :" + property.getID() +
                                   ";" +
                                   property.getName());
            }
        }
    }
 */
    /**
     *测试 复制大纲属性

    public void testCopyDefinition() {
        //复制新闻大纲
        NodeDefinition nodeDef = this.repository.getDefinitionManager().getDefinition("generalnews");
        NodeDefinition copyDef = nodeDef.clone("NEW.CLONE.SCHEME.TEST");
        this.repository.getDefinitionManager().delete(copyDef);
    }
*/
}
