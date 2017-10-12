package com.fulong.longcon.report;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

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
public class TwoDimensionTableModel implements TableModel {
	private Object[][] array;

	public TwoDimensionTableModel(Object[][] array) {
		this.array = array;
	}

	public void addTableModelListener(TableModelListener l) {
		throw new UnsupportedOperationException();
	}

	public String getColumnName(int c) {
		return (String) this.array[0][c];
	}

	public int getColumnCount() {
		return this.array[0].length;
	}

	public Object getValueAt(int r, int c) {
		return this.array[r + 1][c];
	}

	public int getRowCount() {
		return this.array.length - 1;
	}

	@SuppressWarnings("unchecked")
	public Class getColumnClass(int columnIndex) {
		throw new UnsupportedOperationException();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeTableModelListener(TableModelListener l) {
		throw new UnsupportedOperationException();
	}

}
