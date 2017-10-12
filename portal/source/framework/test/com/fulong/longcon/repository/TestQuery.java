package com.fulong.longcon.repository;

import java.util.*;
import com.fulong.longcon.repository.search.IndexManager;
import java.io.IOException;

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
public class TestQuery extends RepositoryTestCase {
    private Vector nodes = new Vector();
    private Node level1 = null;
    private Node level2 = null;
    NodeDefinition definition;
    Calendar calendar = Calendar.getInstance();
    protected void setUp() throws Exception {
        super.setUp();
        Date date = new Date();
        definition = repository.getDefinitionManager().getDefinition("test-full-scheme");
        level1 = this.repository.getRootNode().addNode(definition);
        level2 = level1.addNode();
        String[] str = this.getTestStrings("search.string");
        String[] text = this.getTestStrings("search.text");
        for (int i = 0; i < str.length; i++)
        {
            Node node = level2.addNode(definition);
            nodes.add(node);
            node.setProperty("test-string", str[i]);
            node.setProperty("test-text", text[i]);
            node.setProperty("test-long", i);
            node.setProperty("test-date", calendar);
        }
    }

    protected void tearDown() throws Exception {
        for (Enumeration enume = nodes.elements(); enume.hasMoreElements(); ) {
            Node node = (Node) enume.nextElement();
            this.repository.delete(node);
        }
        this.repository.delete(level1);
        this.repository.delete(level2);
        super.tearDown();
    }

//    public void testTestKeywordSearchForFullText() {
//        Node node = this.repository.getRootNode().addNode("test" + new Date());
//        Query query = this.repository.getQueryManager().createQuery(this.definition,Query.SQL);
//        query.filterByKeywords("温家宝");
//        query.filterByKeywords(IndexManager.FIELD_PARENTS,
//                               this.repository.getRootNode().getID());
//        this.assertTrue(query.nodes().getSize() > 0);
//    }
    //test
    public void testABC(){}
    
    public void testTestKeywordSearchForString() {
        Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
        String tmpa = this.getTestString("search.keywords.1");
        query.filterByKeywords("test-string", tmpa);
        NodeIterator foo = query.nodes();
        long tmp = foo.getSize();
        this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterByPropertyForString()
    {
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByProperty("test-string",this.getTestString("search.keywords.1"));
    	long tmp = query.nodes().getSize();
    	System.out.println(query.nodes().getCount());
    	System.out.println(query.nodes().getQuery());
    	this.assertTrue(tmp > 0);
    }
    
    public void testSort()
    {
    	
    }
   /* 
    public void testTestKeywordSearchForText() {
        Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
        query.filterByKeywords("test-text", this.getTestString("search.keywords.2"));
        RowIterator results = query.execute();
        this.assertTrue(results.getSize() > 0);
    }
   
    public void testShowDocument() {
        definition = repository.getDefinitionManager().getDefinition("user-scheme");
        Node node = this.repository.getRootNode().addNode(definition);
        nodes.add(node);

        Date yestoday = new Date(108, 7, 12);
        Calendar cYestoday = Calendar.getInstance();
        cYestoday.setTime(yestoday);

        Date today = new Date(108, 7, 13);
        Calendar cToday = Calendar.getInstance();
        cToday.setTime(today);

        Date tomorrow = new Date(108, 7, 14);
        Calendar cTomorrow = Calendar.getInstance();
        cTomorrow.setTime(tomorrow);


        node.setProperty("IP", "192.168.0.129");
        node.setProperty("user-mobile", "62968885");
        node.setProperty("user-birthday", cToday);
        node.setProperty("user-openwrite", false);
        try{
            this.indexManager.createIndex(node);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
        Query query = this.repository.getQueryManager().createQuery(this.
            repository.getRootNode(), Query.SQL);
        query.filterByProperty("user-mobile",
                               repository.getValueFactory().createValue("62968885"));

        query.filterByProperty("user-birthday",repository.getValueFactory().createValue(cToday));

        query.filterByProperty("user-openwrite",false);
        query.filterByProperty("user-birthday",repository.getValueFactory().createValue(cYestoday),
                       repository.getValueFactory().createValue(cTomorrow));
        RowIterator results = query.execute();
        this.assertTrue(results.getSize() > 0);
        while (results.hasNext()) {
            Row row = results.nextRow();
            if (row.getValue("IP").getString().equals("192.168.0.129")) {
                System.out.println("the search result is correct");
            }
        }
        try{
            this.indexManager.deleteIndex(node);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
        this.repository.delete(node);
    }

    public void testTestStringEqual() {
        Query query = this.repository.getQueryManager().createQuery(this.
            repository.getRootNode(), Query.SQL);

        query.filterByKeywords("test-string", this.
            getTestString("search.string.ascii"));
        RowIterator results = query.execute();
        this.assertTrue(results.getSize() > 0);


    }

    public void testTestLongEqual() {

        Query query = this.repository.getQueryManager().createQuery(this.
            repository.getRootNode(), Query.SQL);
        query.filterByProperty("test-long",
                       repository.getValueFactory().createValue(0), repository.getValueFactory().createValue(5));
        query.filterByProperty("test-long",
                       repository.getValueFactory().createValue(1));
        RowIterator results = query.execute();
        this.assertTrue(results.getSize() > 0);
        for (Row row = results.nextRow(); results.hasNext();
             row = (Row) results.next()) {
            this.assertEquals(row.getValue("test-long").getLong(), 1l);
        }

    }
*/
}
