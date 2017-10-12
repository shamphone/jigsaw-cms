/**
 * 
 */
package com.fulong.longcon.chart;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * 存储模式二实现 by mali
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class ChartManager {
	private static ChartManager instance;
	private DataSource datasource;
	private static long KEY = System.currentTimeMillis();

	public final static ChartManager getInstance() {
		return instance;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	private Map<String, ChartMessage> generators;

	public ChartManager() {
		instance = this;
	}
	
	public void init(){
		this.generators = new Hashtable<String, ChartMessage>();		
	}

	public synchronized String register(ChartMessage generator) {
		String timer = "svg" + (KEY++);
		generators.put(timer, generator);
		return timer;
	}

	public synchronized ChartMessage get(String key) {
		return this.generators.remove(key);
	}
//	private static final String EnumPieDataset = "select sv.value, count(sv.node_id) ct from String_Value sv, Node_type n where n.definition=? and sv.node_id=n.pkid and sv.property_id=? group by sv.value";	
//	public PieDataset createEnumPieDataset(String definitionId, String propertyId)throws SQLException{
//		Connection con = null;
//		try {
//			con = datasource.getConnection();
//			PreparedJDBCPieDataset data = new PreparedJDBCPieDataset(con);
//			data.executeQuery(con, EnumPieDataset, new String[]{definitionId,propertyId});
//			return data;
//		} finally {
//			if (con != null)
//				con.close();
//		}		
//	}
	
	//根据property获得在新存储模式下的value表名,并返回
	private String getTableName(PropertyDefinition property){
		String tableName = null;
		String propId = property.getID().replaceAll("-", "_").replaceAll(" ", "");
		switch (property.getType()) {
		case PropertyType.LONG:
			tableName =" l_" + propId;
			break;
		case PropertyType.DOUBLE:
			tableName =" f_" + propId;
			break;
		case PropertyType.STRING:
			tableName =" s_" + propId;
			break;
		}
		if(tableName != null){
			return tableName;
		}else{
			return null;
		}
	}
	
	
	public PieDataset createEnumPieDataset(NodeIterator<Node> nodes,PropertyDefinition property)throws SQLException{
		if(nodes.getSize()==0){
			return null;
		}
		Connection con = null;
		try {
			String sql = "select sv.value, count(sv.node_id) ct from ";
			con = datasource.getConnection();
			List<String> parameter = new ArrayList<String>();
			//parameter.add(property.getID());
			/*switch (property.getType()) {
				case PropertyType.LONG:
					sql+=" LONG_VALUE sv";
					break;
				case PropertyType.DOUBLE:
					sql+=" DOUBLE_VALUE sv";
					break;
				case PropertyType.STRING:
					sql+=" STRING_VALUE sv";
					break;
			}*/
			//sql+=this.getTableName(property)+" sv, Node_type n where sv.node_id=n.pkid and sv.property_id=? ";
			sql+=this.getTableName(property)+" sv, node n where sv.node_id=n.pkid ";
			
			StringBuffer where = new StringBuffer("and sv.node_id in ( ");
			for(int j=0;nodes.hasNext();j++){
				if(j!=0){
					where.append(" ,");
				}
				Node node = nodes.next();
				where.append(" ? ");
				parameter.add(node.getID());
			}
			where.append(" )");
			sql += where.toString();
			sql += " group by sv.value";
			
			PreparedJDBCPieDataset data = new PreparedJDBCPieDataset(con);
			data.executeQuery(con, sql, parameter.toArray(new String[0]));
			return data;
		} finally {
			if (con != null)
				con.close();
		}		
	}
		
	
	public CategoryDataset createEnumCategoryDataset(NodeIterator<Node> nodes, PropertyDefinition property)throws SQLException{
		if(nodes.getSize()==0){
			return null;
		}
		Connection con = null;
		try {
			String sql = "select sv.value, count(sv.node_id) ct from ";
			con = datasource.getConnection();
			List<String> parameter = new ArrayList<String>();
			//parameter.add(property.getID());
			/*switch (property.getType()) {
				case PropertyType.LONG:
					sql+=" LONG_VALUE sv";
					break;
				case PropertyType.DOUBLE:
					sql+=" DOUBLE_VALUE sv";
					break;
				case PropertyType.STRING:
					sql+=" STRING_VALUE sv";
					break;
			}*/
			//sql+=this.getTableName(property) + " sv, Node_type n where sv.node_id=n.pkid and sv.property_id=? ";
			sql+=this.getTableName(property) + " sv, node n where sv.node_id=n.pkid ";
			
			StringBuffer where = new StringBuffer("and sv.node_id in ( ");
			for(int j=0;nodes.hasNext();j++){
				if(j!=0){
					where.append(" ,");
				}
				Node node = nodes.next();
				where.append(" ? ");
				parameter.add(node.getID());
			}
			where.append(" )");
			sql += where.toString();
			sql += " group by sv.value";
			
			PreparedJDBCCategoryDataset data = new PreparedJDBCCategoryDataset(con);
			data.executeQuery(con, sql, parameter.toArray(new String[0]));
			return data;
		} finally {
			if (con != null)
				con.close();
		}		
	}
	
	public CategoryDataset createEnumCategoryDataset(NodeIterator<Node> nodes,PropertyDefinition rowProp,PropertyDefinition properties[])throws SQLException{
		if(properties.length==0||nodes.getSize()==0){
			return null;
		}
		Connection con = null;
		try {
			con = datasource.getConnection();
			PreparedJDBCCategoryDataset data = new PreparedJDBCCategoryDataset(con);
			StringBuffer sql = new StringBuffer("select ");
			StringBuffer from =  new StringBuffer();
			StringBuffer where =  new StringBuffer();
			sql.append(" s.value ");
			from.append(" node n left join ");
			from.append(this.getTableName(rowProp)+" s");
			from.append(" on s.node_id = n.pkid ");
			
			List<String> parameter = new ArrayList<String>();
			for(int i=0;i<properties.length;i++){
				if(i==0){
				}else{
					
				}
				sql.append(",t"+i+".value "+properties[i].getName());
				from.append(" left join ");
				from.append(this.getTableName(properties[i])+" t"+i);
				from.append(" on t"+i+".node_id = n.pkid ");
			}
			
			where.append(" n.pkid in (");
			for(int j=0;nodes.hasNext();j++){
				if(j!=0){
					where.append(" ,");
				}
				Node node = nodes.next();
				where.append(" ? ");
				parameter.add(node.getID());
			}
			where.append(" )");
			sql.append(" from ");
			sql.append(from);
			sql.append(" where ");
			sql.append(where);
 			data.executeQueryDirect(con, sql.toString(), parameter.toArray(new String[0]));
			return data;
		} finally {
			if (con != null)
				con.close();
		}		
	}
	
	public PieDataset createEnumPieDataset(NodeIterator<Node> nodes,PropertyDefinition rowProp,PropertyDefinition property)throws SQLException{
		if(nodes.getSize()==0){
			return null;
		}
		Connection con = null;
		try {
			con = datasource.getConnection();
			PreparedJDBCPieDataset data = new PreparedJDBCPieDataset(con);
			StringBuffer sql = new StringBuffer();
			StringBuffer from =  new StringBuffer();
			StringBuffer where =  new StringBuffer("");
			List<String> parameter = new ArrayList<String>();
			sql.append("select r.value ");
			sql.append(", t.value "+property.getName());
			
			from.append("(Select * from");
			/*switch (rowProp.getType()) {
			case PropertyType.LONG:
				from.append(" LONG_VALUE s");
				break;
			case PropertyType.DOUBLE:
				from.append(" DOUBLE_VALUE s");
				break;
			case PropertyType.STRING:
				from.append(" STRING_VALUE s");
				break;
			}*/
			from.append(this.getTableName(rowProp)+" s");
			//from.append(" ,Node_type n where s.node_id=n.pkid and s.property_id=? ) r");
			from.append(" ,node n where s.node_id=n.pkid ) r");
			//parameter.add(rowProp.getID());
			
			from.append(",(Select * from ");
			/*switch (property.getType()) {
				case PropertyType.LONG:
					from.append(" LONG_VALUE sv");
					break;
				case PropertyType.DOUBLE:
					from.append(" DOUBLE_VALUE sv");
					break;
				case PropertyType.STRING:
					from.append(" STRING_VALUE sv");
					break;
			}*/
			from.append(this.getTableName(property)+" sv");
			//from.append(", Node_type n where sv.node_id=n.pkid and sv.property_id=?) t");
			from.append(", node n where sv.node_id=n.pkid) t");
			//parameter.add(property.getID());
			where.append(" r.Node_Id = t.Node_Id and  t.Node_Id in (");

			for(int j=0;nodes.hasNext();j++){
				if(j!=0){
					where.append(" ,");
				}
				Node node = nodes.next();
				where.append(" ? ");
				parameter.add(node.getID());
			}
			where.append(" )");
			sql.append(" from ");
			sql.append(from);
			sql.append(" where ");
			sql.append(where);
			data.executeQueryDirect(con, sql.toString(), parameter.toArray(new String[0]));
			return data;
		} finally {
			if (con != null)
				con.close();
		}		
	}
	
	public PieDataset createPieDataset(String sql, Object[] params) throws SQLException {

		Connection con = null;
		try {
			con = datasource.getConnection();
			PreparedJDBCPieDataset data = new PreparedJDBCPieDataset(con);
			data.executeQuery(con, sql, params);
			return data;
		} finally {
			if (con != null)
				con.close();
		}
	}
}
