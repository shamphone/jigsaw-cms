package com.fulong.common.table;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Description: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixf
 * @version 2.0
 */
public abstract class TableAdapter implements TableModel {
	protected static final Log log = LogFactory.getLog(TableAdapter.class);

	/**
	 * Adds a listener to the list that is notified each time a change to the
	 * data model occurs.
	 * 
	 * @param l
	 *            the TableModelListener
	 * @todo Implement this javax.swing.table.TableModel method
	 */
	public void addTableModelListener(TableModelListener l) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the most specific superclass for all the cell values in the
	 * column.
	 * 
	 * @param columnIndex
	 *            the index of the column
	 * @return the common ancestor class of the object values in the model.
	 * @todo Implement this javax.swing.table.TableModel method
	 */
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int columnIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the name of the column at <code>columnIndex</code>.
	 * 
	 * @param columnIndex
	 *            the index of the column
	 * @return the name of the column
	 * @todo Implement this javax.swing.table.TableModel method
	 */
	public String getColumnName(int columnIndex) {
		return "";
	}

	/**
	 * Returns true if the cell at <code>rowIndex</code> and
	 * <code>columnIndex</code> is editable.
	 * 
	 * @param rowIndex
	 *            the row whose value to be queried
	 * @param columnIndex
	 *            the column whose value to be queried
	 * @return true if the cell is editable
	 * @todo Implement this javax.swing.table.TableModel method
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * Removes a listener from the list that is notified each time a change to
	 * the data model occurs.
	 * 
	 * @param l
	 *            the TableModelListener
	 * @todo Implement this javax.swing.table.TableModel method
	 */
	public void removeTableModelListener(TableModelListener l) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the value in the cell at <code>columnIndex</code> and
	 * <code>rowIndex</code> to <code>aValue</code>.
	 * 
	 * @param aValue
	 *            the new value
	 * @param rowIndex
	 *            the row whose value is to be changed
	 * @param columnIndex
	 *            the column whose value is to be changed
	 * @todo Implement this javax.swing.table.TableModel method
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取指定的列的所有的行值.
	 * 
	 * @param columnIndex
	 *            int
	 * @return Object[]
	 */
	public Object[] getColumnValues(int columnIndex) {
		Object[] values = new Object[this.getRowCount()];
		for (int i = 0; i < this.getRowCount(); i++) {
			values[i] = this.getValueAt(i, columnIndex);
		}
		return values;
	}

	/**
	 * 获取指定的列的所有的行值.
	 * 
	 * @param columnIndex
	 *            int
	 * @return Object[]
	 */
	public Object[] getRowValues(int rowIndex) {
		Object[] values = new Object[this.getColumnCount()];
		for (int i = 0; i < this.getColumnCount(); i++) {
			values[i] = this.getValueAt(rowIndex, i);
		}
		return values;
	}
}
