/**
 * 
 */
package org.phoenix.dbimport;

import java.util.Map;

/**
 * @author lixf
 *
 */
public class DBMapping {
	private String tableName;
	private String nodeDefinition;
	private String idColumn;
	private Map<String, String> mapping;
	private String parentId="1000000000000";
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getNodeDefinition() {
		return nodeDefinition;
	}
	public void setNodeDefinition(String nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	public String getParentId() {
		return parentId;
	}
	public String getIdColumn() {
		return idColumn;
	}
	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

}
