package com.fulong.longcon.report.impl;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.common.dao.DatabaseException;

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
 * @author lixf
 * @version 2.0
 */
public class ResultSetTableModel implements TableModel {
	private ResultSetMetaData meta;
	private ResultSet rs;
	private static final Log log = LogFactory.getLog(ResultSetTableModel.class);

	public ResultSetTableModel(ResultSet rs) {
		this.rs = rs;
		try {
			this.meta = rs.getMetaData();
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	public void addTableModelListener(TableModelListener l) {
		throw new UnsupportedOperationException();
	}

	public String getColumnName(int c) {
		try {
			return meta.getColumnName(c + 1);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return "";
		}
	}

	public int getColumnCount() {
		try {
			return meta.getColumnCount();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return 0;
		}
	}

	public Object getValueAt(int r, int c) {
		try {
			rs.absolute(r + 1);
			return rs.getObject(c + 1);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public int getRowCount() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return 0;
		}
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
