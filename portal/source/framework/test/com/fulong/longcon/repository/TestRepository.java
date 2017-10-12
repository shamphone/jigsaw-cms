package com.fulong.longcon.repository;

import com.fulong.longcon.site.SiteFactory;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import java.util.Properties;
import org.springframework.core.io.FileSystemResource;
import java.util.Enumeration;
import java.net.URL;
import java.io.File;
import com.fulong.longcon.dict.ext.DictManagerExt;
import java.util.Vector;
import java.util.Date;
import java.util.Iterator;
import java.util.Calendar;

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
public class TestRepository extends RepositoryTestCase {

    protected void setUp() throws Exception {
        super.setUp();

    }

    protected void tearDown() throws Exception {
        super.tearDown();

    }

    public void testGetSiteRoot() {
        Node newCreatedNode = this.repository.getNode(SiteFactory.
            SITE_CATEGORY_ROOT);

    }


    public void testCopyAllDefinition() {
           NodeDefinition definition = repository.getDefinitionManager().getDefinition("2447909486126");
           NodeDefinition copy = this.repository.getDefinitionManager().copyDefinition("copy of"+ definition.getID(), definition, true, definition.getSuperDefinition());
           this.assertNotNull(copy);
           this.assertEquals("destDefinitionName", copy.getName());

//           this.repository.getDefinitionManager().delete(definition);
           this.repository.getDefinitionManager().delete(copy);

    }
    /*
        public void testRebuilderIndex()
        {
            repository.reBuildIndex("C:\\luceneSearch") ;
          //  while(true);
          int i = 1;
          int j = 2;
        }
     */
    /**
     * 创建一个新大纲，继承自一个父大纲，
     * 父大纲中继承过来的属性自动为“受保护”的状态。
     *
     */
    public void testCreateDefinition() {
        String name = "definition_name" + new Date();
        NodeDefinition superDef = repository.getDefinitionManager().createDefinition("test_nodeDefinition"+ new Date(),
            repository.getDefinitionManager().getDefinition("no-properties-scheme"));
        PropertyDefinition proDefinition = superDef.addPropertyDefinition(PropertyType.STRING, "string_propertyDefinition");
        NodeDefinition definition = this.repository.getDefinitionManager().
            createDefinition(name,
                             superDef);
        this.assertNotNull(definition.getID());
        this.assertEquals(definition.getSuperDefinition().getID(), superDef.getID());
        this.assertEquals(((PropertyDefinition)definition.propertyDefinitions().next()).getID(), ((PropertyDefinition)superDef.propertyDefinitions().next()).getID());
        this.assertTrue(definition.propertyDefinitions().hasNext());

        Iterator proDefs  = definition.propertyDefinitions();
        proDefs.next();
        this.assertFalse(proDefs.hasNext());

        this.assertEquals(superDef.getInheritDefinitions().nextDefinition().getID(), definition.getID());
        this.assertTrue(definition.isNodeType(superDef.getID()));
        this.assertFalse(superDef.isNodeType(definition.getID()));

        this.assertEquals(definition,
                          repository.getDefinitionManager().
                          getDefinition(definition.getID()));
        Iterator pros = superDef.propertyDefinitions();
        while (pros.hasNext()) {
            PropertyDefinition pro = (PropertyDefinition) pros.next();
            PropertyDefinition sonpro = definition.getPropertyDefinition(pro.
                getID());
            this.assertTrue(sonpro.isProtected());
        }
        this.repository.getDefinitionManager().delete(superDef);
        this.repository.getDefinitionManager().delete(definition);
    }

    public void testCreateDefinition1() {
        String name = "This is definition name.";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(name);
        this.assertNotNull(definition.getID());
        this.assertEquals(definition,
                          repository.getDefinitionManager().
                          getDefinition(definition.getID()));
        this.repository.getDefinitionManager().delete(definition);
    }

    public void testCreateDefinition2() {
        String parentName = "product-scheme";
        NodeDefinition parent = this.repository.getDefinitionManager().
            getDefinition(parentName);
        String name = "test-definition-name.";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(name, parent);
        this.assertNotNull(definition.getID());
        this.assertEquals(definition,
                          repository.getDefinitionManager().
                          getDefinition(definition.getID()));
        this.assertEquals(definition.propertyDefinitions().hasNext(), true);
        this.repository.getDefinitionManager().delete(definition);
    }

    public void testDeleteNode() {
        Node node = repository.getRootNode();
        String name = "This is definition name.";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(name);
        Node newNode = node.addNode(definition);
        this.assertNotNull(newNode.getID());
        this.assertEquals(newNode, repository.getNode(newNode.getID()));
        this.assertEquals(newNode.getDefinition(), definition);
        repository.delete(newNode);
        this.assertNull(repository.getNode(newNode.getID()));
        this.repository.getDefinitionManager().delete(definition);
    }

    public void testDeleteDefinition() {
        //创建结点定义,确定定义存在
        String name = "This is definition name.";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(name);
        this.assertNotNull(definition.getID());
        this.assertEquals(definition,
                          repository.getDefinitionManager().
                          getDefinition(definition.getID()));
        //根据定义创建结点,确定定义可用.
        Node node = this.repository.getRootNode().addNode(definition);
        this.assertNotNull(node.getDefinition());
        //删除结点定义
        this.repository.getDefinitionManager().delete(definition);
        this.assertNull(this.repository.getDefinitionManager().getDefinition(
            definition.getID()));

    }

    /*
        public void testSearch() {

            Query query = this.repository.getQueryManager().createQuery();
        //   query.addKeywords("石油乌鲁木齐石化分公司");
          Value value =  this.repository.getValueFactory().createValue("越多越好");
          query.addEqual("specification-desc", value);
            RowIterator rows = query.execute();
            this.assertEquals(rows.hasNext(), true);


            query = this.repository.getQueryManager().createQuery();
            query.addKeywords("杨振平");
            rows = query.execute();
            rows = query.execute();
            this.assertEquals(rows.hasNext(), false);

        }
     */
    /*
     public void testSearch1() {
            NodeDefinition definition = repository.getDefinitionManager().getDefinition("product-scheme");

            Node node = this.repository.getRootNode().addNode(definition,
                "test");
            Calendar c = Calendar.getInstance();
            c.set(2008, 1, 2);
            node.setProperty("specification-desc", "规格说明"); //1
            node.setProperty("expiry-date", c); //5
            node.setProperty("PRODUCT-PARA3", 1000.000); //4
            node.setProperty("PRODUCT-DESCRIPTION", "杨振平认为，许霆在取款时的行为是合法的，模式也不符合犯罪的条件，“法无名文不为罪，我们会坚持为他作无罪辩护”。目前，辩护律师正为许霆案开展准备工作。杨律师准备陆续发送邀请函，拟邀请十余名对此案有独到见解的法律界人士成立律师团，其中刑法学专家将占80％比例，约有10％是法理学专家，另外还将加上个别对民商法有研究的人士。" +
                             "　　杨振平表示，他尚在等候答复的过程中。但记者了解到，已经有6名法律界人士被列入“律师团”名单，除了两名辩护律师外，华南理工大学关永宏教授、徐松林教授、知名律师朱列玉均在名单内。另外，辩护律师还打算请清华大学、北京大学的法学权威做许霆案的顾问。" +
     "　　杨振平说：“争取本周末敲定确切名单，届时将会组织成员开会研究案情，制定一份全面的、能体现共同观点的辩护意见作为处理案子的思路。”（来源：新快报）");

            node = this.repository.getRootNode().addNode(definition,
                                                         "test");
            c = Calendar.getInstance();
            c.set(2008, 1, 3);
            node.setProperty("specification-desc", "规格说明"); //1
            node.setProperty("expiry-date", c); //5
            node.setProperty("PRODUCT-PARA3", 1000.000); //4
            node.setProperty("PRODUCT-DESCRIPTION", "杨振平认为，许霆在取款时的行为是合法的，模式也不符合犯罪的条件，“法无名文不为罪，我们会坚持为他作无罪辩护”。目前，辩护律师正为许霆案开展准备工作。杨律师准备陆续发送邀请函，拟邀请十余名对此案有独到见解的法律界人士成立律师团，其中刑法学专家将占80％比例，约有10％是法理学专家，另外还将加上个别对民商法有研究的人士。" +
                                     "　　杨振平表示，他尚在等候答复的过程中。但记者了解到，已经有6名法律界人士被列入“律师团”名单，除了两名辩护律师外，华南理工大学关永宏教授、徐松林教授、知名律师朱列玉均在名单内。另外，辩护律师还打算请清华大学、北京大学的法学权威做许霆案的顾问。" +
     "　　杨振平说：“争取本周末敲定确切名单，届时将会组织成员开会研究案情，制定一份全面的、能体现共同观点的辩护意见作为处理案子的思路。”（来源：新快报）");

            Query query = this.repository.getQueryManager().createQuery();
            query.addKeywords("许霆在取款时的行为是合法的，模式也不符合犯罪的条件，“法无名文不为罪，我们会坚持");
            ValueFactory vf = this.repository.getValueFactory();
         //   query.addEqual("PRODUCT-PARA3", vf.createValue(1000.000));
            Calendar cFrom = Calendar.getInstance();
            cFrom.set(2008, 1, 1);
            Calendar cTo = Calendar.getInstance();
            cTo.set(2008, 1, 6);
     query.addRange("expiry-date", vf.createValue(cFrom), vf.createValue(cTo));
          //  query.addRange("PRODUCT-PARA3", vf.createValue(999), vf.createValue(10001));

            RowIterator rows = query.execute();
            this.assertEquals(rows.hasNext(), true);
            this.repository.delete(node);

            query = this.repository.getQueryManager().createQuery();
            query.addKeywords("杨振平");
            rows = query.execute();
            rows = query.execute();
            this.assertEquals(rows.hasNext(), false);

        }
     */



    public void testDefinitions() {
        NodeDefinitionIterator definitions = this.repository.
            getDefinitionManager().definitions();
        this.assertNotNull(definitions.nextDefinition());

    }

    public void testCopyDefinition() {
        //创建结点定义,确定定义存在
        String name = "This is definition name.";
        NodeDefinition definition = repository.getDefinitionManager().
            createDefinition(name);
        this.assertNotNull(definition.getID());
        this.assertEquals(definition,
                          repository.getDefinitionManager().
                          getDefinition(definition.getID()));
        NodeDefinition copy = this.repository.getDefinitionManager().
            copyDefinition("destDefinitionName", definition);
        this.assertNotNull(copy);
        this.assertEquals("destDefinitionName", copy.getName());

        this.repository.getDefinitionManager().delete(definition);
        this.repository.getDefinitionManager().delete(copy);

    }

}

