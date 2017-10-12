package com.fulong.longcon.repository;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
/**
 * 
* Query
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-9-12
 */
public interface Query {
	public static String LUCENE = "lucene";
	public static String SQL = "sql";
	public static String DESC_ORDER = " DESC ";
	public static String ASC_ORDER = " ASC ";

	/**
	 * 设置关键字条件。
	 * 
	 * @param keywords
	 *            String
	 */
	public void filterByKeywords(String keywords);
	
	/**
	 * 按照多个关键字进行“或”查询
	 * @param keywords
	 */
	public void filterByKeywords(String[] keywords);

	/**
	 * 设置在某个域中使用like条件来查询。这个域必须是字符串类型的。
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            String
	 */
	public void filterByKeywords(String field, String value);

	/**
	 * 设置在某个域中使用like条件来查询。这个域必须是字符串类型的。 支持多个值的模糊匹配
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            String[]
	 */
	public void filterByKeywords(String field, String[] value);

	/**
	 * 等值查询
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            Value
	 */
	public void filterByProperty(String field, Value value);
	
	/**
	 * 等值查询
	 * 
	 * @param def
	 *            String
	 * @param field
	 *            String
	 * @param value
	 *            Value
	 */
	public void filterByPropertyCompareID(String def, String field, Value value);

	/**
	 * 在指定范围内查询
	 * 
	 * @param field
	 *            String
	 * @param min
	 *            String
	 * @param max
	 *            String
	 */
	public void filterByProperty(String field, Value min, Value max);

	/**
	 * 
	 * @param field
	 *            String
	 * @param min
	 *            Value
	 */
	public void filterByFromValue(String field, Value min);

	/**
	 * 小于（包括等于）max的值
	 * 
	 * @param field
	 *            String
	 * @param max
	 *            Value
	 */
	public void filterByToValue(String field, Value max);

	/**
	 * 大于（不包括等于）value的值
	 * 
	 * @param field
	 * @param value
	 */
	public void filterGreaterThan(String field, Value value);

	/**
	 * 小于（不包括等于）value的值
	 * 
	 * @param field
	 * @param value
	 */
	public void filterLessThan(String field, Value value);
	

	/**
	 * 
	 * @param field
	 *            String
	 * @param value
	 *            Value, value不能为空
	 * @deprecated， 推荐使用 public void filterByNotEqualValue(Property field, Value
	 *              value)
	 */
	public void filterByNotEqualValue(String field, Value value);



	/**
	 * 按照给定的域来排序
	 * 
	 * @param field
	 *            String
	 * @param order
	 *            boolean
	 */
	public void sortByProperty(String field, boolean asc);

	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            String
	 */
	public void filterByProperty(String property, String value);

	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            String
	 */
	public void filterByProperty(String property, boolean value);

	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            String
	 */
	public void filterByProperty(String property, long value);

	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            String
	 */
	public void filterByProperty(String property, Node value);

	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param from
	 *            最小值（包括）
	 * @param to
	 *            最大值（包括）
	 */
	public void filterByProperty(String property, long from, long to);

	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param from
	 *            最小值（包括）
	 * @param to
	 *            最大值（包括）
	 */
	public void filterByProperty(String property, double from, double to);

	/**
	 * 按照日期属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param from
	 *            最小值（包括）
	 * @param to
	 *            最大值（包括）
	 */
	public void filterByNotEqualProperty(String property, Calendar from, Calendar to);
	
	/**
	 * 按照属性来过滤
	 * 
	 * @param property
	 *            String
	 * @param from
	 *            最小值（包括）
	 * @param to
	 *            最大值（包括）
	 */
	public void filterByProperty(String property, Calendar from, Calendar to);

	/**
	 * 按照属性来过滤,过滤条件为具体某一天的整个时间段。
	 * 
	 * @param property
	 * @param date
	 */
	public void filterBySpecifiedDate(String property, Date date);


	/**
	 * 
	 * @param parent
	 *            Node
	 * @param recursive
	 *            boolean
	 * @throws SQLException 
	 */
	public void filterByParent(Node parent, boolean recursive) throws SQLException;
	

	
	/**
	 * 传入id 调用filterByParent(Node parent, boolean recursive)
	 * @param id
	 * @param recursive
	 */
	public void filterByParent(String id, boolean recursive);
	
	/**
	 * 根据parentID和name过滤
	 * @param id
	 * @param name
	 * @param recursive
	 */
	public void filterByParentAndName(String id, String name, boolean recursive) throws SQLException;
	
	/**
	 * 
	 * @param parent
	 * @param recursive
	 */
	public void filterByNotEqualParent(Node parent, boolean recursive);
	
	/**
	 * 按照序号排序
	 * 
	 * @param asc
	 *            boolean true为升序，false为降序
	 */
	public void sortByOrdinal(boolean asc);

	/**
	 * 被Node的引用属性引用的节点
	 * @param def    String
	 * @param refPro String
	 * @param value  Node
	 */
	public void filterByRefed(String def, String refPro, Node value);

	/**
	 * 
	 * 属性没有设置过值
	 * 
	 * @param field
	 */
	public void filterByPropertyNotExist(String field);
	
	/**
	 * 获取查询结果
	 * 
	 * @return NodeIterator
	 */
	public NodeIterator<Node> nodes();
	/**
	 * 获取查询结果
	 * 
	 * @return NodeIterator
	 */
	public NodeIterator<Node> nodes(boolean recursive);
	
}
