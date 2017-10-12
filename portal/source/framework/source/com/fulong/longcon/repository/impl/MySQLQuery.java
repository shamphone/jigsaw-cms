/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.repository.impl;

import java.sql.SQLException;
import java.sql.Types;

import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.dao.QName;

/**
 * MySQLQuery
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-9-11
 */
public class MySQLQuery extends SQLQuery{
	private boolean ifSort = false;//是否排序 用于调整from后的结构 代替外连接(+)

	public void filterByParent(Node parent, boolean recursive) {
		if (!recursive) {
			this.where.append(" AND n.PARENT_ID =? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		} else {
			this.where.append(" AND n.PARENT_ID in (select * from NODE t where t.pkid=c.parent_id and t.pkid in ");
			this.where.append(" ( "+new JdbcDao().getClauseForRecursive("node", "pkid", "parent_id", null, parent.getID(),"pkid")+" ) )");
		}
	}
	
	public void filterByParentAndName(String id,String name, boolean recursive) throws SQLException{
		if (!recursive) {
			this.where.append(" AND n.PARENT_ID =? and n.name =? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, id));
			this.parameters.add(new SQLParameter(Types.VARCHAR, name));
		} else {
			this.where.append(" AND n.PARENT_ID in (select * from NODE t where t.pkid=c.parent_id and t.pkid in ");
			this.where.append(" ( "+new JdbcDao().getClauseForRecursive("node", "pkid", "parent_id", null, id,"pkid")+" ) ) and n.name =?");
			this.parameters.add(new SQLParameter(Types.VARCHAR, name));
		}
	}
	
	public void filterByNotEqualParent(Node parent, boolean recursive) {
		if (!recursive) {
			this.where.append(" AND n.PARENT_ID <>? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		} else {
			this.where.append(" AND n.PARENT_ID not in (select * from NODE t where t.pkid=c.parent_id and t.pkid in ");
			this.where.append(" ( "+new JdbcDao().getClauseForRecursive("node", "pkid", "parent_id", null, parent.getID(),"pkid")+" ) )");
			//this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		}
	}
	
	public void sortByProperty(String field, boolean asc) {
		PropertyDefinition property = this.nodeDefinition.getPropertyDefinition(field);
		if (property == null) {
			log.warn("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
			return;
		}
		String table = QName.encode(property.getType(), property.getID());
		if (!this.tables.contains(table)) {
			this.tables.add(table+" right join node n on " + table + ".node_id=n.pkid");
		}else {
			this.tables.remove(table);
			this.tables.add(table+" right join node n on " + table + ".node_id=n.pkid");
		}
		this.selections.append("," + table + ".value " + table + "value " );
		if (this.orderBy.length() > 0)
			this.orderBy.append(",");
		if (property.getType()==3 || property.getType()==4 || property.getType()==5)
			this.orderBy.append("ifnull("+table + ".value,0) ");
		else
			this.orderBy.append("convert(ifnull("+table+".value,null) using gbk) collate gbk_chinese_ci ");	
		if (asc)
			this.orderBy.append("asc");
		else
			this.orderBy.append("desc");
		ifSort=true;
	}

	protected String tables() {
		if(ifSort){
			StringBuffer tables = new StringBuffer("");
			for (String table : this.tables) {
				tables.append("," + table);
			}
			return tables.toString().substring(1);
		}else{
			StringBuffer tables = new StringBuffer(" node n ");
			for (String table : this.tables) {
				tables.append("," + table);
			}
			return tables.toString();
		}
	}
}
