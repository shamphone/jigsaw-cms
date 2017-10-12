package com.fulong.longcon.report;

import java.util.Vector;
import java.util.Iterator;
import javax.swing.table.TableModel;
import com.fulong.longcon.report.TwoDimensionTableModel;

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
public class TableModelWrapper {
	private TableModel tableModel;

	public TableModelWrapper(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public TableModel turnTableModel() {
		Object[][] twoDimensionArray = this.turnArrays(this.getArrays());
		return new TwoDimensionTableModel(twoDimensionArray);
	}

	/**
	 * 将现在的table填充到一个二维数组里面去， 表头作为第一行数据插入
	 * 
	 * @return Object[][]
	 */
	private Object[][] getArrays() {
		int rowN = this.tableModel.getRowCount();
		int colN = this.tableModel.getColumnCount();
		Object[][] cells = new Object[rowN + 1][colN];
		// 把表头填充到二维数组里面去
		for (int i = 0; i < colN; i++) {
			cells[0][i] = this.tableModel.getColumnName(i);
		}

		for (int i = 0; i < rowN; i++) {
			for (int j = 0; j < colN; j++) {
				cells[i + 1][j] = this.tableModel.getValueAt(i, j);
			}
		}
		return cells;
	}

	/**
	 * 将作为二维参数的数组行列进行倒置，行列互换
	 * 
	 * @param oldArrays
	 *            Object[][]
	 * @return Object[][]
	 */
	private Object[][] turnArrays(Object[][] oldArrays) {
		int rowN = oldArrays.length;
		int colN = oldArrays[0].length;
		Object[][] newArrays = new Object[colN][rowN];

		for (int i = 0; i < rowN; i++) {
			for (int j = 0; j < colN; j++) {
				newArrays[j][i] = oldArrays[i][j];
			}
		}
		return newArrays;
	}

	/**
	 * 获得指定的列
	 * 
	 * @param rowNum
	 *            int
	 * @return TableModelRow
	 */
	public TableModelRow getRow(int rowNum) {
		Vector<Object> v = new Vector<Object>();
		for (int i = 0; i < this.tableModel.getColumnCount(); i++) {
			Object o = this.tableModel.getValueAt(rowNum, i);
			v.add(o);
		}
		return new TableModelRow(v);
	}

	/**
	 * 获得所有列
	 * 
	 * @return Iterator
	 */
	public Iterator<TableModelRow> getRows() {
		Vector<TableModelRow> v = new Vector<TableModelRow>();
		for (int i = 0; i < this.tableModel.getRowCount(); i++) {
			TableModelRow tr = this.getRow(i);
			v.add(tr);
		}
		return v.iterator();
	}

	/**
	 * 获得表头
	 * 
	 * @return Iterator
	 */
	public Iterator<String> getColumnHeaders() {
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < this.tableModel.getColumnCount(); i++) {
			String colName = this.tableModel.getColumnName(i);
			v.add(colName);
		}
		return v.iterator();
	}

	/**
	 * 获得指定列名
	 * 
	 * @param c
	 *            int
	 * @return String
	 */
	public String getColumnName(int c) {
		return this.tableModel.getColumnName(c);
	}

	public int getColumnCount() {
		return this.tableModel.getColumnCount();
	}

	public int getRowCount() {
		return this.tableModel.getRowCount();
	}

}
