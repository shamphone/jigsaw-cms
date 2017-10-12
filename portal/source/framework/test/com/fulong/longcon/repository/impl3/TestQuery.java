package com.fulong.longcon.repository.impl3;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.QueryManager;
import com.fulong.longcon.repository.RepositoryTestCase;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFactory;
import com.fulong.longcon.repository.value.DateValue;
import com.fulong.longcon.repository.value.LongValue;
import com.fulong.longcon.repository.value.StringValue;

//import com.fulong.longcon.repository.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Title: 龙驭内容管理引擎</p>
 *
 * <p>Description: 龙驭内容管理引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author LIULEI
 * @version 1.0
 */

public class TestQuery extends RepositoryTestCase 
{
	private Vector nodes = new Vector();
    private Node level1 = null;
    private Node level2 = null;
    NodeDefinition definition;
    Calendar calendar = Calendar.getInstance();
    protected Value value;
    private ValueFactory valueFactory;
    private Query query;
    
    protected void setUp() throws Exception 
    {
        super.setUp();
        definition = repository.getDefinitionManager().getDefinition("2551240198218");
        /*level1 = this.repository.getRootNode().addNode(definition);
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
        }*/
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testTestKeywordSearchForString() 
    {
        /*query.filterByKeywords("title", "");
        NodeIterator foo = query.nodes();
        long tmp = foo.getSize();
        System.out.println(tmp);
        this.assertTrue(tmp > 0);   */
    	
    	QueryManager bar = this.repository.getQueryManager();
    	this.query = bar.createQuery(this.definition, Query.SQL);
    	query.filterByKeywords("title", new String[]{"3的","大","阿"});
        NodeIterator foo = query.nodes();
        long tmp = foo.getSize();
        System.out.println(tmp);
        this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterByPropertySingleValue()
    {
    	//String
    	String a = null;
    	QueryManager bar = this.repository.getQueryManager();
    	this.query = bar.createQuery(this.definition, Query.SQL);
    	query.filterByProperty("title","3的");
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//text
    	/*query.filterByProperty("prop4","测试");
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//long
    	/*query.filterByProperty("long",55);
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//calendar 
    	/*String date = "2010-12-01 09:56:56";
    	DateValue value = new DateValue();
    	//value.setValue(date);
    	query.filterByProperty("date",value);
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);*/
    }
    
    public void testTestFilterByPropertyMuplieValue()
    {
    	//String
    	String a = null;
    	QueryManager bar = this.repository.getQueryManager();
    	this.query = bar.createQuery(this.definition, Query.SQL);
    	Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-12-01 09:56:56 ");
			Calendar ca = Calendar.getInstance();
	    	ca.setTime(date);
	    	query.filterByProperty("date", ca, calendar);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    }
    
 
    public void testTestFilterByPropertyFromTo()
    {
    	//long
    	/*query.filterByProperty("long",-2,0);
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//date 
    	/*Calendar calendar1 = Calendar.getInstance();
    	calendar1.set(2010, 12, 20);
    	Date a = new Date();
		try {
			a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-12-01 00:00:02");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(a);
    	System.out.println(a.toString());
    	query.filterByProperty("date",calendar,calendar1);
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);*/
    }
    
    //full text search
    public void testTestFullTextSearch()
    {
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	String tmpa = this.getTestString("search.keywords.1");
    	query.filterByKeywords(tmpa);
    	
    	//数据库每隔一分钟收集一次分词
    	try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
		long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    }
    
    /**
     * @build value
     * @param v
     * @return value
     */  
    public Value createValue(long value) 
    {
        LongValue lvalue = new LongValue();
        lvalue.setValue(value);
        return lvalue;
    }
    
    private Value createValue(Calendar v)
    {
    	 DateValue dvalue = new DateValue();
         dvalue.setValue(v);
         return dvalue;
    }
    
    public Value createValue(String value) 
    {
        StringValue svalue = new StringValue();
        svalue.setValue(value);
        return svalue;
    }
    
    //mark
    //filterByFromValue(String field, Value min)
    public void testTestFilterByFromValue()
    {
        //long
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByFromValue("long",createValue(0));
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//date 
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByFromValue("date",createValue(calendar));
    	tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);*/
    }
    
    //filterGreaterThan(String field, Value value)
    public void testTestFilterGreaterThan()
    {
    	//long
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterGreaterThan("long",createValue(0) );
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//date 
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterGreaterThan("date",createValue(calendar));
    	tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertFalse(tmp > 0);*/
    }
    
    //filterLessThan(String field, Value value)
    public void testTestFilterLessThan()
    {
    	//long
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterLessThan("long",createValue(1) );
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//date 
    	query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterLessThan("date",createValue(calendar));
    	tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertFalse(tmp > 0);
    }
    
    //filterByToValue(String field, Value max)
    public void testTestFilterByToValue()
    {
    	//long
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByToValue("long",createValue(1) );
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//date 
    	query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByToValue("date",createValue(calendar));
    	tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterByEqualValue()
    {
    	//String
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	String tmpa = this.getTestString("search.keywords.3");
    	query.filterBy.filterByEqualValue("string",createValue(tmpa));
    	long tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//long
    	query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByEqualValue("test-long",createValue(1));
    	tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    	
    	//calendar 
    	query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByEqualValue("test-date",createValue(calendar));
    	tmp = query.nodes().getSize();
    	System.out.println(tmp);
    	this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterByNotEqualValue()
    {
    	//String
    	/*Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	//String tmpa = this.getTestString("3的");
    	query.filterByNotEqualValue("title",createValue(""));
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//if null
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByNotEqualValue("title",null);
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//long
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByNotEqualValue("long",createValue(-1));
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//if null
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByNotEqualValue("long",null);
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//calendar 
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByNotEqualValue("date",createValue(calendar));
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertFalse(tmp > 0);*/
    	
    	//if null
    	query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByNotEqualValue("date",null);
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterByPropertyNotExist()
    {
    	//String
    	/*Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByPropertyNotExist("title");
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//long
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByPropertyNotExist("long");
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);*/
    	
    	//calendar 
    	query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByPropertyNotExist("date");
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterBySpecifiedDate()
    {
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-12-01 00:00:01 ");
			query.filterBySpecifiedDate("date",date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0);
    }
    
    public void testTestFilterByKeywords()
    {
    	/*Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
        query.filterByKeywords("title","3");
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0); */
    	
    	/*query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByKeywords("prop4","测");
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0); */
    }
    
    public void testTestFilterByRefed()
    {
    	Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
    	query.filterByRefed("prop5","");
    	long tmp = query.nodes().getSize();
    	System.out.println("test count: "+tmp);
    	this.assertTrue(tmp > 0); 
    } 
}
