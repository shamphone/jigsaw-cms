package com.fulong.longcon.report.impl;

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
public class TableModelTranspose implements TableModel {
	private TableModel tableModel;

	public TableModelTranspose(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void addTableModelListener(TableModelListener l) {
		throw new UnsupportedOperationException();
	}

	public String getColumnName(int c) {
		if (c == 0)
			return this.tableModel.getColumnName(c);
		else
			return (String) this.tableModel.getValueAt(c, 0);
	}

	public int getColumnCount() {
		return this.tableModel.getRowCount() + 1;
	}

	public Object getValueAt(int r, int c) {
		if (c == 0)
			return this.tableModel.getColumnName(r + 1);
		else
			return this.tableModel.getValueAt(c, r);
	}

	public int getRowCount() {
		return this.tableModel.getColumnCount() - 1;
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
