package com.fulong.longcon.expression;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;

/**
 * 过滤器表达式，用来描述过滤器，常用表达式如下
 * [属性定义的ID] [>,<,=,<>] [ "常量值" | 另一属性定义的ID | $系统常量 | #引用类型的ID ]
 * 例子1 两个属性相等 : title = name
 *
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 3.0
 */
public interface FilterParser {
    public final static int NULL = 0; // 空值
    public final static int CONSTANT = 1; // 常量,采用""表示
    public final static int SYSVARIANT = 2; //系统变量，采用$表示
    public final static int REFERENCE = 3; // 引用的内容变量， 采用#表示
    public final static int FIELD = 4; //另一内容域
    public final static int SEARCHDEFINITION = 5; //搜索大纲

    public final static String LESS = "less"; //<
    public final static String MORE = "more"; //>
    public final static String EQUAL = "equal"; //=
    public final static String NOTEQUAL = "notEqual"; //<>
    public final static String LESSEQUAL = "lessEqual"; //<=
    public final static String MOREQUAL = "moreEqual"; //>=
    public final static String LIKE = "like"; //like
    public final static String IN = "in"; // 区间
    public final static String CONTAINS = "contains"; // 全文匹配
	public void parser(String pattern) throws Exception;
	/**
	 * 操作符
	 * @return String
	 */
	public String getOperation();
	/**
	 * 设置操作符
	 * @param operation String
	 */
	public void setOperation(String operation);
	/**
	 * 对应属性定义的ID
	 * @return String
	 */
	public String getPropertyDefinition();
	/**
	 * 值
	 * @return String
	 */
	public Value getValue();
	
	/**
	 * 设置值
	 */
	public void setValue(Value value);
	
	/**
	 * 值对应的表达式
	 * @return
	 */
	public String getValueExpression();
	/**
	 * 值类型
	 * @return int
	 */
	public int getValueType();
	/**
	 * 设置空值
	 */
	public void setNull();
	/**
	 * 设置常量值
	 * @param value String
	 */
	public void setConstantValue(String value);
	/**
	 *
	 * @param node Node
	 */
	public void setReferenceValue(Node node);
	/**
	 *  系统变量
	 * @param variant String
	 */
	public void setSystemVariant(String variant);
	
	/**
	 * 判断节点node是否满足要求
	 * @param node
	 * @return
	 */
	public boolean validate(Node node);
	
	/**
	 * 应用到过滤器
	 * @param query
	 */
	public void addToQuery(Query query);
 

}
