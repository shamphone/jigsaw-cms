/**
 * 
 */
package com.fulong.longcon.exchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.DOMException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;

/**
 *   
 * 导出节点和定义到Excel。使用方法：
 * 1. 创建ExcelExporter对象exporter;
 * 2. 使用exportNodeDefinition来设置待导出的NodeDefinition，生成一张空的sheet。
 * 3. 通过exportAllColumns或者exportColumn来生成表头。
 * 4. 调用exportNode或者exportAllNode来生成列，后者将导出所有的节点。
 * 
 * Coolink协同工作框架模型 
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
public class ExcelExporter {
	private Repository repository;
	private Workbook workbook;  //当前workbook，一次导出仅产生一个workbook;
    private Sheet sheet; //当前sheet,每个nodedefinition仅产生一个sheet;
    private short rowCount; //当前sheet中正在渲染得行数；
    private Row row;//当前行；
    /**
	 * 待导出的列
	 */
    private List<String> columns; 
    private NodeDefinition definition; //当前正在渲染得definition，在渲染node之前必须先指定definition；


	/**
	 * 已经导出的节点集合
	 */
	private Vector<NodeDefinition> exportedDefinitions;

	private List<Integer> type;


	public ExcelExporter(Repository repository) throws ParserConfigurationException {
		this(repository,new HSSFWorkbook());
	}
	/**
	 * 导出到指定工作簿
	 * @param repository
	 * @param book
	 * @throws ParserConfigurationException
	 */
	public ExcelExporter(Repository repository,Workbook book) throws ParserConfigurationException {
		this.repository = repository;		
		this.exportedDefinitions = new Vector<NodeDefinition>();
		this.workbook = book;
		this.columns=null;	
	}



	/**
	 * 导出分类定义
	 */
	public void exportNodeDefinition(NodeDefinition definition) throws ParserConfigurationException, DOMException, IOException {
		if(this.exportedDefinitions.contains(definition))
			return;
		this.exportedDefinitions.add(definition);
		this.rowCount = 0;
		this.sheet = this.workbook.createSheet(definition.getID());
 		this.row = sheet.createRow(this.rowCount);
		this.definition = definition;
		this.columns = new ArrayList<String>();
		this.type=new ArrayList<Integer>();
	}	
	
	/**
	 * 按照xml的保存方法加入id,parentId,nodeName属性
	 */
	public void exportDefault(){
		if(rowCount != 0)
			throw new IllegalStateException("row count should be 0.");
		String[] def={"id", "parentId" , " nodeName"};
		for(int i=0;i<def.length;i++){
			Cell cell = row.createCell(this.columns.size());
			cell.setCellValue(def[i]);
			this.columns.add(def[i]);
		}	
	}
	
	/**
	 * 生成NodeDefinition对应的XML节点
	 */
	public void exportAllColumns(){
		if(rowCount != 0)
			throw new IllegalStateException("row count should be 0.");
		// Create a cell and put a value in it.
		for(Iterator<PropertyDefinition> iterator = definition.propertyDefinitions();iterator.hasNext();){
			PropertyDefinition property = iterator.next();			
			Cell cell = row.createCell(this.columns.size());			
			cell.setCellValue(property.getID());
			this.columns.add(property.getID());
			}
	}
	
	/**
	 * 不导出所有列，而是仅导出指定的列
	 */
	public void exportColumn(String propertyID){
		if(rowCount != 0)
			throw new IllegalStateException("row count should be 0.");
		PropertyDefinition definition =this.definition.getPropertyDefinition(propertyID);
		if(definition == null)
			throw new IllegalArgumentException("Unknow property id :"+ propertyID +" in definition "+ this.definition.getName()+".");
		Cell cell = row.createCell(this.columns.size());			
		cell.setCellValue(propertyID);
		this.columns.add(propertyID);		
	}
	
	/*
	 * 
	 * @param content
	 * 
	 * @throws ParserConfigurationException
	 * 
	 * @throws DOMException
	 * 
	 * @throws IOException
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午08:49:49
	 */
	public void exportNode(Node content) {
		this.rowCount++;
		Row row = sheet.createRow(this.rowCount);
		// Create a cell and put a value in it.
		int cellCount = 0;
		CellStyle cellStyle = this.workbook.createCellStyle();
		CreationHelper createHelper = this.workbook.getCreationHelper();
	    cellStyle.setDataFormat(
	        createHelper.createDataFormat().getFormat("yyyy-MM-dd h:mm"));

		for(String column:this.columns){
			Cell cell = row.createCell(cellCount++);
			PropertyDefinition propertyDef =this.definition.getPropertyDefinition(column);
			if(propertyDef!=null){
				if(this.rowCount==1){
					this.type.add(propertyDef.getType());//加入属性类型  @author songbo @lastupdate 2010-7-20下午5:54:00
				}
				Property property = content.getProperty(column);
				if(property!=null){
					switch(property.getDefinition().getType()){
					case PropertyType.BINARY:
						break;
					case PropertyType.BOOLEAN:
						cell.setCellValue(property.getBoolean());
						break;
					case PropertyType.DATE:
						cell.setCellStyle(cellStyle);
						if(property.getDate()!=null)					
						cell.setCellValue(property.getDate());
						break;
					case PropertyType.DOUBLE:
						cell.setCellValue(property.getDouble());
						break;
					case PropertyType.FIX:
						break;
					case PropertyType.LONG:
						cell.setCellValue(property.getLong());
						break;
					case PropertyType.TEXT:
						String value = property.getString();
						if(value!=null && value.length()>30000)
							value = value.substring(0, 30000);
						cell.setCellValue(value);
						break;
					default:
						cell.setCellValue(property.getString());	
				}
				}	
			}else{if(column == "id")
					cell.setCellValue(content.getID());
				  else{if(column == "parentId")
					  cell.setCellValue(content.getParent().getID());
				  	else{
				  		cell.setCellValue(content.getName());
				    }
				  }
			 }
		}
	}
	
	/* 
	 * 导出分类下所有的节点
	 * @author lixf
	 * @lastupdate 2009-10-21上午09:18:27
	 */
	public void exportAllNodes(){
		Query query = this.repository.getQueryManager().createQuery(this.definition, Query.SQL);
		for(Iterator<Node> iterator = query.nodes();iterator.hasNext();){
			Node node = iterator.next();
			this.exportNode(node);
		}
	}
	
	/**
	 * 导出分类下的属性类型
	 * @param content
	 * @author songbo
	 * @lastupdate 2010-7-20下午5:54:00
	 */
	public void exportPropertyType(Node content){
		this.rowCount++;
		Row row = sheet.createRow(this.rowCount);
		Iterator<Integer> it=this.type.iterator();
		int cellCount = 0;
		for(String column:this.columns){
			Cell cell = row.createCell(cellCount++);
			PropertyDefinition propertyDef =this.definition.getPropertyDefinition(column);
			if(propertyDef!=null){
				cell.setCellValue(it.next());
			}
		}
	}
	/* 
	 * 获取工作簿
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-21上午09:22:06
	 */
	public Workbook getWorkbook(){
		return this.workbook;
	}
	/**
	 * 输出工作簿
	 */
	public void write(OutputStream os) throws IOException{
		this.workbook.write(os);
	}

	
}
