package com.fulong.longcon.repository;

import java.util.Calendar;
import java.util.Date;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.apache.lucene.document.DateTools;

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
public class TestNode extends RepositoryTestCase {

    protected void setUp() throws Exception {

        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testDateTool(){
    	String result = 	DateTools.dateToString(new Date(), DateTools.Resolution.DAY);
    	this.assertEquals(result, "20090313");
    	int i=0;
    	
    	Query query = this.repository.getQueryManager().createQuery(this.repository.getRootNode(), Query.SQL);
    	query.filterBySpecifiedDate("", new Date());
    	
    	
    	
    }
    
    public void testDateProperty() {
        String name = "This is date property";
        Calendar calendar = Calendar.getInstance();
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.DATE, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        node.setProperty(prop.getID(), "1990-1-2");

        this.assertEquals(node.getProperty(prop.getID()).getDate().get(Calendar.
            DATE), 2);
        node.setProperty(prop.getID(), calendar.getTimeInMillis());
        this.assertEquals(node.getProperty(prop.getID()).getDate(), calendar);

        node.setProperty(prop.getID(), calendar);
        this.assertEquals(node.getProperty(prop.getID()).getLong(),
                          calendar.getTimeInMillis());
        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);

    }
    
    
    
    public void testProperties() {
        //获得课程大纲,课程大纲里面有课件大纲作为复合属性子大纲
        NodeDefinition definition = repository.getDefinitionManager().
            getDefinition("course-scheme");
        Iterator proDefs = definition.propertyDefinitions();
        while (proDefs.hasNext()) {
            PropertyDefinition pDef = (PropertyDefinition) proDefs.next();
            if (pDef instanceof ChildNodeDefinition) {
                NodeDefinition nDef = ( (ChildNodeDefinition) pDef).
                    getNodeDefinition();

            }
        }

    }

    public void testAddNode() {
        String name = "This is String property";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition string = definition.addPropertyDefinition(
            PropertyType.STRING, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(string.getID());
        this.assertNotNull(property);
        node.setProperty(string.getID(), propValue);
        this.assertEquals(property.getString(), propValue);

        //用value的机制设置属性值
        Value strValue = this.repository.getValueFactory().createValue(
            propValue);
        node.setProperty(string.getID(), strValue);
        this.assertEquals(property.getValue(), strValue);

        //删除属性定义
        definition.delete(string);

        //测试删除,删除之后,根据ID无法查到该节点定义.
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));
        this.assertEquals(node.getParent(), repository.getRootNode());

        //删除节点定义
        this.repository.delete(node);
    }

    public void testStringProperty() throws Exception {
        String name = "This is String property";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.STRING, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        node.setProperty(prop.getID(), propValue);
        this.assertEquals(property.getString(), propValue);
        node.setProperty(prop.getID(), propValue, PropertyType.STRING);
        this.assertEquals(property.getString(), propValue);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(
            "simplelog.properties");
        node.setProperty(prop.getID(), is);
        this.assertTrue(node.getProperty(prop.getID()).getString().startsWith(
            "org.apache"));
        is.close();
        //删除属性定义
        definition.delete(prop);
        //删除节点定义
        this.repository.delete(node);
        //测试删除,删除之后,根据ID无法查到该节点定义.
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));

    }

    public void testMultiString() {
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");

        String multiname = "This is String property for multi";
        PropertyDefinition multiString = definition.addPropertyDefinition(
            PropertyType.STRING, multiname);
        multiString.setMultiple(true);
        this.assertEquals(true, multiString.isMultiple());
        multiString.setLength(0, 5);
        this.assertEquals(0, multiString.getMinLength());
        this.assertEquals(5, multiString.getMaxLength());
        multiString.setReadonly(false);
        this.assertEquals(multiString.getDeclaringNodeDefinition().getID(),
                          definition.getID());

        String nameForProDef = "PropertyDefinition multiString";
        multiString.setName(nameForProDef);
        this.assertEquals(multiString.getName(), nameForProDef);

        this.assertEquals(multiString.getType(), PropertyType.STRING);

        String desForProDef =
            "this is description for the PropertyDefinition multiString";
        multiString.setDescription(desForProDef);
        this.assertEquals(multiString.getDescription(), desForProDef);

        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);

        Property multistrproperty = node.getProperty(multiString.getID());
        //为这个新建的node设置这个属性的值
        String[] values = {
            "string1", "string2", "string3", "string4"};
        node.setProperty(multiString.getID(), values);
        this.assertEquals(multistrproperty.getArray().length, values.length);

        //测试直接用Property的接口设置值
        multistrproperty.setValue(values);
        this.assertEquals(multistrproperty.getArray().length, values.length);

        //用value机制设置值
        Value[] strValues = new Value[values.length];
        for (int i = 0; i < values.length; i++) {
            strValues[i] = this.repository.getValueFactory().createValue(values[
                i]);
        }
        node.setProperty(multiString.getID(), strValues);
        this.assertEquals(multistrproperty.getValues().length, strValues.length);
        multistrproperty.setValue(strValues);
        this.assertEquals(multistrproperty.getValues().length, strValues.length);

        this.assertEquals(node.getParent(), repository.getRootNode());

        //删除节点定义
        this.repository.delete(node);
        //测试删除,删除之后,根据ID无法查到该节点定义.

        //删除属性定义
        definition.delete(multiString);
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));

    }

    public void testBooleanProperty() {
        String name = "This is boolean property";
        String propValue = "true";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.BOOLEAN, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        node.setProperty(prop.getID(), propValue);
        this.assertTrue(property.getBoolean());
        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);
    }

    public void testMultiBoolean() {
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");

        String multiname = "This is Boolean property for multi";
        PropertyDefinition multiString = definition.addPropertyDefinition(
            PropertyType.BOOLEAN, multiname);
        multiString.setMultiple(true);
        multiString.setReadonly(false);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);

        Property multistrproperty = node.getProperty(multiString.getID());
        //为这个新建的node设置这个属性的值
        Boolean[] values = {
            true, true, false, true};
        //用value机制设置值
        Value[] strValues = new Value[values.length];
        for (int i = 0; i < values.length; i++) {
            strValues[i] = this.repository.getValueFactory().createValue(values[
                i]);
        }
        node.setProperty(multiString.getID(), strValues);
        this.assertEquals(multistrproperty.getValues().length, strValues.length);

        node.setProperty(multiString.getID(), strValues, PropertyType.BOOLEAN);
        this.assertEquals(multistrproperty.getValues().length, strValues.length);

        multistrproperty.setValue(strValues);
        this.assertEquals(multistrproperty.getValues().length, strValues.length);

        this.assertEquals(node.getParent(), repository.getRootNode());
        //删除属性定义
        definition.delete(multiString); //测试删除,删除之后,根据ID无法查到该节点定义.
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));
        //删除节点定义
        this.repository.delete(node);
    }

    public void testLongProperty() {
        String name = "This is long property";
        String propValue = "1000000";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.LONG, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        node.setProperty(prop.getID(), propValue);
        this.assertEquals(node.getProperty(prop.getID()).getLong(), 1000000l);
        node.setProperty(prop.getID(), 123456l);
        this.assertEquals(node.getProperty(prop.getID()).getLong(), 123456l);
        Calendar calendar = Calendar.getInstance();
        node.setProperty(prop.getID(), calendar);
        this.assertEquals(node.getProperty(prop.getID()).getLong(),
                          calendar.getTimeInMillis());
        node.setProperty(prop.getID(), true);
        this.assertEquals(node.getProperty(prop.getID()).getLong(), 1);
        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);
    }

    public void testDoubleProperty() {
        String name = "This is double property";
        String propValue = "1000000.00";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.DOUBLE, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        node.setProperty(prop.getID(), propValue);
        this.assertEquals(node.getProperty(prop.getID()).getDouble(),
                          1000000.00);
        node.setProperty(prop.getID(), 123456l);
        this.assertEquals(node.getProperty(prop.getID()).getLong(), 123456l);
        Calendar calendar = Calendar.getInstance();
        node.setProperty(prop.getID(), calendar);
        this.assertEquals(node.getProperty(prop.getID()).getLong(),
                          calendar.getTimeInMillis());
        node.setProperty(prop.getID(), true);
        this.assertEquals(node.getProperty(prop.getID()).getLong(), 1);
        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);
    }

    public void testTextProperty() throws Exception {

        Calendar calendar = Calendar.getInstance();
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.TEXT, "the  name");
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(
            "simplelog.properties");
        node.setProperty(prop.getID(), is);
        this.assertTrue(node.getProperty(prop.getID()).getString().startsWith(
            "org.apache"));
        is.close();
        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);
    }

   

    public void testBinaryProperty() {
        String name = "This is binary property";

        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.BINARY, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);
        try {
            InputStream is = new FileInputStream("D:\\server.xml");
            node.setProperty(prop.getID(), is);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        node.setProperty(prop.getID(),
                         "/sites/123/0ae0c1b445f23df24c4838f00571341a.jpg", 2);

        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);

    }

    public void testNodeProperty() {
        String name = "This is binary property";

        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition prop = definition.addPropertyDefinition(
            PropertyType.REFERENCE, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(prop.getID());
        this.assertNotNull(property);

        NodeDefinition definitionCourseware = repository.getDefinitionManager().
            getDefinition("root");
        Node courseware = this.repository.getRootNode().addNode(
            definitionCourseware, "courseware1");
        this.assertEquals(courseware.getName(), "courseware1");
        node.setProperty(prop.getID(), courseware);
        this.assertEquals(node.getProperty(prop.getID()).getValue().getType(),
                          PropertyType.REFERENCE);
        this.assertEquals(node.getProperty(prop.getID()).getParent(), node);

        PropertyIterator refs = courseware.getReferences();
        this.assertNotNull(refs.nextProperty());
        this.repository.delete(courseware);
        //删除属性定义

        definition.delete(prop);
        this.repository.getDefinitionManager().delete(definition);
        //删除节点定义
        this.repository.delete(node);
    }

    public void testGetNodes() {
        String name = "This is String property";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition string = definition.addPropertyDefinition(
            PropertyType.STRING, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(string.getID());
        this.assertNotNull(property);
        node.setProperty(string.getID(), propValue);
        this.assertEquals(property.getString(), propValue);

        Node child1 = node.addNode("child1");
        Node grandson1 = child1.addNode("grandson1");

        this.assertEquals(node.getNodes().nextNode(), child1);
        this.assertEquals(node.getAllNodes().getSize(), 2);

        this.assertEquals(node.getNodes("child1").nextNode(), child1);

        this.assertTrue(grandson1.isChild(child1));

        this.repository.delete(grandson1);
        this.repository.delete(child1);

        //测试删除,删除之后,根据ID无法查到该节点定义.

        this.assertEquals(node.getParent(), repository.getRootNode());
        //删除属性定义
        definition.delete(string);
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));
        //删除节点定义
        this.repository.delete(node);
    }

    public void testGetMaxOrderNo() {
        String name = "This is String property";
        String propValue = "propValue";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(
                "This is definition name");
        PropertyDefinition string = definition.addPropertyDefinition(
            PropertyType.STRING, name);
        //确认属性可用
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertEquals(node.getDefinition(), definition);
        Property property = node.getProperty(string.getID());
        this.assertNotNull(property);
        node.setProperty(string.getID(), propValue);
        this.assertEquals(property.getString(), propValue);

        Node child1 = node.addNode("child1");
        child1.setOrderNo(1);
        Node child2 = node.addNode("child1");
        child2.setOrderNo(2);
        Node child3 = node.addNode("child1");
        child3.setOrderNo(3);
        this.assertEquals(3, node.getMaxOrderNo());

        //     this.repository.delete(child1);


        this.assertEquals(node.getParent(), repository.getRootNode());
        //测试删除,删除之后,根据ID无法查到该节点定义.
        //删除节点定义


        this.assertEquals(node.getNodes().getSize(), 3);
      this.repository.delete(node);


//删除属性定义
        definition.delete(string);     this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));

    }

}
