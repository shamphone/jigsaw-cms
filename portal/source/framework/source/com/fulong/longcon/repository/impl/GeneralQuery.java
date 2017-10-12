package com.fulong.longcon.repository.impl;

import java.util.Calendar;
import java.util.Date;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.RowIterator;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFactory;
/**
 * Query接口中一些和实现无关的接口，可以调用其它接口缺省实现的，在这个类中提供缺省实现
* GeneralQuery
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-9-12
 */
public abstract class GeneralQuery implements Query {
	protected 	ValueFactory valueFactory;
	/**
	 * 区间比较
	 */
	public void filterByProperty(String field, Value valueFrom, Value valueTo) {
		if (valueFrom != null)
			this.filterByFromValue(field, valueFrom);
		if (valueTo != null)
			this.filterByToValue(field, valueTo);

	}
	
	public void filterByKeywords(String keywords) {
		// just do nothing

	}

	public void filterByKeywords(String[] keywords) {
		// just do nothing
	}
	public void filterByProperty(String property, String value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, boolean value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, long value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, Node value) {
		Value ivalue = this.valueFactory.createValue(value);
		this.filterByProperty(property, ivalue);
	}

	public void filterByProperty(String property, long from, long to) {
		Value ifrom = this.valueFactory.createValue(from);
		Value ito = this.valueFactory.createValue(to);
		this.filterByProperty(property, ifrom, ito);
	}

	public void filterByProperty(String property, double from, double to) {
		Value ifrom = this.valueFactory.createValue(from);
		Value ito = this.valueFactory.createValue(to);
		this.filterByProperty(property, ifrom, ito);
	}

	public void filterByProperty(String property, Calendar from, Calendar to) {
		Value ifrom = this.valueFactory.createValue(from);
		Value ito = this.valueFactory.createValue(to);
		this.filterByProperty(property, ifrom, ito);
	}
	public void filterBySpecifiedDate(String property, Date date) {
		Calendar current = Calendar.getInstance();
		current.setTime(date);
		Calendar from = Calendar.getInstance();
		from.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Calendar to = Calendar.getInstance();
		to.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		this.filterByProperty(property, this.valueFactory.createValue(from), this.valueFactory.createValue(to));

	}
	/*
	 * 按关键字查找
	 * 
	 * @see
	 * com.fulong.longcon.repository.Query#filterByKeywords(java.lang.String,
	 * java.lang.String)
	 */
	public void filterByKeywords(String field, String value) {
		if(value == null || value.length()==0)
			return ;
		this.filterByKeywords(field, new String[]{value});
	}
	
	public void filterByRefed(String def ,String refPro, Node value) {
		this.filterByPropertyCompareID(def,refPro, this.valueFactory.createValue(value));
	}
	
	public RowIterator execute() {
		throw new UnsupportedOperationException();
	}

	public NodeIterator<Node> nodes() {
		return this.nodes(true);
	}
}
