package com.fulong.longcon.view.impl;

import com.fulong.longcon.view.ListView;
import com.fulong.longcon.view.ext.ListViewManagerExt;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.view.data.NodesViewData;
import com.fulong.longcon.view.dao.NodesViewDao;
import java.sql.SQLException;
import com.fulong.common.dao.DaoFactory;
import java.util.Vector;

/**
 * <p>
 * Title: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class ListViewImpl implements ListView {
	private ListViewManagerExt manager;
	private String name;
	private NodesViewData[] datas = null;

	public ListViewImpl(ListViewManagerExt manager, String name) {
		this.manager = manager;
		this.name = name;
	}

	/**
	 * 获取所有的列
	 * 
	 * @return String[]
	 */
	public String[] getColumns() {
		this.loadDatas();
		return this.extractColumns();
	}

	private String[] extractColumns() {
		if (datas != null) {
			Vector<String> v = new Vector<String>();
			for (int i = 0; i < datas.length; i++) {
				v.add(datas[i].getProDefinitionID());
			}
			return (String[]) v.toArray(new String[v.size()]);
		}
		return new String[0];
	}

	private void loadDatas() {
		if (datas != null)
			return;
		else {
			DaoFactory factory = this.manager.getDaoFactory();
			try {
				factory.open();
				NodesViewDao dao = (NodesViewDao) factory
						.getDao(NodesViewDao.class);
				this.datas = dao.findByName(this.name);
			} catch (SQLException ex) {
				throw new DatabaseException(ex);
			} finally {
				factory.close();
			}
		}
	}

	/**
	 * 列宽
	 * 
	 * @return int
	 */
	public int getColumnWidth(String column) {
		this.loadDatas();
		for (int i = 0; i < datas.length; i++) {
			if (datas[i].getProDefinitionID().equals(column))
				return datas[i].getWidth();
		}
		return 0;
	}

	/**
	 * 设置列
	 * 
	 * @param columns
	 *            String[]
	 */
	public void setColumns(String[] columns) {
		DaoFactory factory = this.manager.getDaoFactory();
		try {
			factory.open();
			NodesViewDao dao = (NodesViewDao) factory
					.getDao(NodesViewDao.class);
			dao.deleteByName(this.name);
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		this.manager.createListView(this.name, columns);
		this.datas = null;
		this.loadDatas();

	}

	/**
	 * 设置列宽
	 * 
	 * @param column
	 *            String
	 * @param width
	 *            int
	 */
	public void setColumnWidth(String column, int width) {
		DaoFactory factory = this.manager.getDaoFactory();
		try {
			factory.open();
			NodesViewDao dao = (NodesViewDao) factory
					.getDao(NodesViewDao.class);
			NodesViewData data = new NodesViewData();
			data.setNodeDefinitionID(this.name);
			data.setProDefinitionID(column);
			data.setWidth(width);
			dao.update(data);
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		for (int i = 0; i < datas.length; i++) {
			if (datas[i].getProDefinitionID().equals(column))
				datas[i].setWidth(width);
		}

	}

}
