package com.fulong.longcon.report;

import java.util.Vector;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Title: 龙驭核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lishaobo
 * @version 2.0
 */
public class TableModelRow {
	private List<?> cells;

	public TableModelRow(List<?> cells) {
		this.cells = cells;
	}

	/**
	 * 获得当前行的指定列的对象
	 * 
	 * @param columnNum
	 *            int
	 * @return Object
	 */
	public Object getTableModelCell(int columnNum) {
		if ((columnNum < 0) || (columnNum > this.cells.size()))
			return null;
		return cells.get(columnNum);
	}

	/**
	 * 获得当前行所有列的对象
	 * 
	 * @return Iterator
	 */
	public Iterator<Object> getCells() {
		Vector<Object> v = new Vector<Object>();
		for (int i = 0; i < this.cells.size(); i++) {
			Object cell = this.cells.get(i);
			v.add(cell);
		}
		return v.iterator();
	}

}
