/**
 * 
 */
package com.fulong.longcon.expression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.value.DateValue;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public abstract class DefaultParser implements FilterParser {
	protected String propertyDefinition;
	protected String operation;
	protected String expression;
	protected Value value;
	protected int valueType;

	public DefaultParser() {

	}

	/**
	 * 操作符
	 * 
	 * @return String
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * 设置操作符
	 * 
	 * @param operation
	 *            String
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 对应属性定义的ID
	 * 
	 * @return String
	 */
	public String getPropertyDefinition() {
		return propertyDefinition;
	}

	public String getValueExpression() {
		return expression;

	}

	/**
	 * 值
	 * 
	 * @return String
	 */
	public Value getValue() {
		return this.value;
	}

	/**
	 * 设置值
	 * 
	 */
	public void setValue(Value value) {
		this.value = value;
	}

	/**
	 * 值类型
	 * 
	 * @return int
	 */
	public int getValueType() {
		return valueType;
	}

	/**
	 * 设置空值
	 */
	public void setNull() {
		this.expression = null;
		this.valueType = NULL;
	}

	/**
	 * 设置常量值
	 * 
	 * @param value
	 *            String
	 */
	public void setConstantValue(String value) {
		if (value == null) {
			this.expression = null;
			this.valueType = NULL;
		} else {
			this.expression = "\"" + value + "\"";
			this.valueType = CONSTANT;
		}
	}

	/**
	 * 
	 * @param node
	 *            Node
	 */
	public void setReferenceValue(Node node) {
		this.expression = node.getID();
		this.valueType = REFERENCE;
	}

	/**
	 * 系统变量
	 * 
	 * @param variant
	 *            String
	 */
	public void setSystemVariant(String variant) {
		this.expression = variant;
		this.valueType = SYSVARIANT;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(this.propertyDefinition);
		buffer.append(" ");
		buffer.append(this.operation);
		buffer.append(" ");
		switch (this.valueType) {
		case NULL:
			buffer.append("null");
			break;
		case CONSTANT:
			buffer.append("\"" + expression + "\"");
			break;
		case SYSVARIANT:
			buffer.append("&" + expression);
			break;
		case REFERENCE:
			buffer.append("#" + expression);
			break;
		case SEARCHDEFINITION:
			buffer.append("^" + expression);
			break;
		}
		return buffer.toString();
	}

	public void addToQuery(Query query) {
		String property = this.getPropertyDefinition();
		value = this.getValue();
		// value.getDate();
		if (value == null) {
			if (this.getOperation().equals(FilterParser.EQUAL)) {
				query.filterByPropertyNotExist(property);
				return;
			} else if (this.getOperation().equals(FilterParser.NOTEQUAL)) {
				query.filterByNotEqualValue(property, value);
				return;
			} else {
				return;
			}
		}
		if (this.getOperation().equals(FilterParser.EQUAL)) {
			String valueStr = value.getString();
			if (valueStr != null) { // 处理日历类重复器的参数
				Calendar beginCalendar = Calendar.getInstance();
				Calendar endCalendar = Calendar.getInstance();
				if (valueStr.startsWith("weekPt")
						|| valueStr.startsWith("monthPt")) { // 处理周历和月历重复器的日期参数
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					try {
						Date date = dateFormat.parse(valueStr.substring(
								valueStr.length() - 10, valueStr.length()));
						beginCalendar.setTime(date);
						endCalendar.setTime(date);
						endCalendar.add(Calendar.DATE, 1);
						endCalendar.add(Calendar.MILLISECOND, -1);
						query.filterByProperty(property, beginCalendar,
								endCalendar);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (valueStr.startsWith("yearPt")) { // 处理年历重复器的日期参数
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM");
					try {
						Date date = dateFormat.parse(valueStr.substring(6));
						beginCalendar.setTime(date);
						endCalendar.setTime(date);
						endCalendar.add(Calendar.MONTH, 1);
						endCalendar.add(Calendar.MILLISECOND, -1);
						query.filterByProperty(property, beginCalendar,
								endCalendar);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					//兼容过滤条件 date参数值为yyyy-MM-dd modified by mali 2010-8-25
					if (value instanceof DateValue && (valueStr.indexOf(":") == -1)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						try {
							Date date = dateFormat.parse(valueStr);
							beginCalendar.setTime(date);
							endCalendar.setTime(date);
							endCalendar.add(Calendar.DATE, 1);
							endCalendar.add(Calendar.MILLISECOND, -1);
							query.filterByProperty(property, beginCalendar,
									endCalendar);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						//处理父节点过滤条件
						if(property.equals("coolink_parentNode")){
							query.filterByParent(value.getString(), false);
						}else{
							query.filterByProperty(property, value);
						}
					}
				}
			} else {
				query.filterByProperty(property, value);
			}
		} else if (this.getOperation().equals(FilterParser.LESS)) {
			query.filterByToValue(property, value);
		} else if (this.getOperation().equals(FilterParser.MORE)) {
			query.filterByFromValue(property, value);
		} else if (this.getOperation().equals(FilterParser.NOTEQUAL)) {
			query.filterByNotEqualValue(property, value);
		} else if (this.getOperation().equals(FilterParser.LIKE)) {
			query.filterByKeywords(property, value.getString());
		} else if (this.getOperation().equals(FilterParser.CONTAINS)) {
			String[] strSplit = value.getString().split(" ");
			List<String> strList = new ArrayList<String>();
			for (int j = 0; j < strSplit.length; j++) {
				if (!strSplit[j].equals(""))
					strList.add(strSplit[j]);
			}
			query.filterByKeywords(strList.toArray(new String[strList.size()]));
		}
	}

	public boolean validate(Node node) {
		String property = this.getPropertyDefinition();
		Value value = this.getValue();
		if (node == null || property == null
				|| node.getProperty(property) == null)
			return false;
		// Value toCompare = node.getProperty(property).getValue();
		Value[] toCompareValues = node.getProperty(property).getValues();
		if (this.getOperation().equals(FilterParser.EQUAL)) {
			if (value == null&&((toCompareValues == null)||(toCompareValues.length == 0)))
				return true;
			if(value==null){
				if (toCompareValues.length==1&&(toCompareValues[0]==null||toCompareValues[0].getString().equals(""))) {
					return true;
				}
				return false;
			}
		} else if (this.getOperation().equals(FilterParser.NOTEQUAL)) {
			if (value == null&&(toCompareValues != null)&&(toCompareValues.length != 0))
				return true;
			if(value==null){
				if (toCompareValues.length==1&&(toCompareValues[0]==null||toCompareValues[0].getString().equals(""))) {
					return false;
				}
				return true;
			}
		} else {
			if (value == null || toCompareValues == null
					|| toCompareValues.length == 0)
				return false;
		}

		if (this.getOperation().equals(FilterParser.EQUAL)) {
			for (int i = 0; i < toCompareValues.length; i++) {
				if (toCompareValues[i].getString() != null
						&& value.equals(toCompareValues[i])) {
					return true;
				}
			}
			return false;
		} else if (this.getOperation().equals(FilterParser.MORE)) {
			for (int i = 0; i < toCompareValues.length; i++) {
				if (toCompareValues[i].getString() != null
						&& value.compareTo(toCompareValues[i]) > 0) {
					return false;
				}
			}
			return true;
		} else if (this.getOperation().equals(FilterParser.LESS)) {
			for (int i = 0; i < toCompareValues.length; i++) {
				if (toCompareValues[i].getString() != null
						&& value.compareTo(toCompareValues[i]) < 0) {
					return false;
				}
			}
			return true;
		} else if (this.getOperation().equals(FilterParser.NOTEQUAL)) {
			for (int i = 0; i < toCompareValues.length; i++) {
				if (toCompareValues[i].getString() != null
						&& value.equals(toCompareValues[i])) {
					return false;
				}
			}
			return true;
		} else if (this.getOperation().equals(FilterParser.LIKE)) {
			for (int i = 0; i < toCompareValues.length; i++) {
				if ((toCompareValues[i].toString() != null)
						&& (toCompareValues[i].toString().indexOf(
								value.toString()) >= 0)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
}