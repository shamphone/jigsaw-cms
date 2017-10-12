/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.repository.impl;

import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.dao.QName;

/**
 * SQLServerQuery
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-9-11
 */
public class SQLServerQuery extends MySQLQuery {

	private boolean ifSort = false;// 是否排序 用于调整from后的结构 代替外连接(+)

	
	public void sortByProperty(String field, boolean asc) {
		StringBuffer col = new StringBuffer("");
		PropertyDefinition property = this.nodeDefinition
				.getPropertyDefinition(field);
		if (property == null) {
			log.warn("No property with name " + field + " in definition "
					+ this.nodeDefinition.getID() + ".");
			return;
		}
		String table = QName.encode(property.getType(), property.getID());
		if (!this.tables.contains(table)) {
			this.tables.add(table + " right join node n on " + table
					+ ".node_id=n.pkid");
		} else {
			this.tables.remove(table);
			this.tables.add(table + " right join node n on " + table
					+ ".node_id=n.pkid");
		}
		this.selections.append("," + table + ".value " + table + "value ");
		if (this.orderBy.length() > 0)
			this.orderBy.append(",");
		if (property.getType() == 1)
			col.append("isnull(" + table
					+ ".value,null) COLLATE Chinese_PRC_CS_AS_KS_WS ");
		else if (property.getType() == 3 || property.getType() == 4)
			col.append("isnull(" + table + ".value,0) ");
		else
			col.append("isnull(" + table + ".value,null) ");
		if (asc)
			this.orderBy.append(" case when " + col
					+ " is null then 1 else 0 end ," + col + "asc");
		else
			this.orderBy.append(" " + col + "desc");
		ifSort = true;
	}

	protected String tables() {
		if (ifSort) {
			StringBuffer tables = new StringBuffer("");
			for (String table : this.tables) {
				tables.append("," + table);
			}
			return tables.toString().substring(1);
		} else {
			StringBuffer tables = new StringBuffer(" node n ");
			for (String table : this.tables) {
				tables.append("," + table);
			}
			return tables.toString();
		}
	}

	protected String getQuerySQL() {
		StringBuffer query = null;
		/*
		 * if(ifSort) query = new StringBuffer("SELECT top 100 percent "); else
		 */
		query = new StringBuffer("SELECT top 100 percent ");

		if (ifDistinct)
			query.append("distinct ");
		query.append(this.selections.toString());
		query.append(" FROM ");
		query.append(this.tables());
		query.append(" WHERE ");
		query.append(this.where);
		if (this.orderBy.length() > 0) {
			query.append(" ORDER BY ");
			query.append(this.orderBy);
		}
		return query.toString();
	}
}
