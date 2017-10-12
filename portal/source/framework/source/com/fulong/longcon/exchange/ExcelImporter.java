package com.fulong.longcon.exchange;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64;

import jxl.*;
import jxl.read.biff.BiffException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionManager;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;

/**
 *   
 * 通过Excel导入节点和定义
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author songbo
 *
 * @version 2.0
 *
 */

public class ExcelImporter {
	private Repository repository;
	/**
	 * 已经导入的节点集合
	 */
	private Vector<Node> imported;

	private boolean overrideExists = true;

	/**
	 * 父结点。如果未找到节点对应的父结点，则使用这个缺省的父结点。
	 */
	private Node parent;

	/**
	 * 目标分类，如果设定，则缺省的采用这个分类为节点的新分类
	 */
	private NodeDefinition destDefinition;
	
	/**
	 * 事件侦听器
	 */
	private ImporterEventListener eventListener = new DefaultImporterEventListener();
	
	/**
	 * 属性映射
	 */
	private PropertyMap propertyMap;

	/**
	 * 初始化函数
	 * @param repository
	 */
	public ExcelImporter(Repository repository) {
		this.repository = repository;
		this.imported = new Vector<Node>();
	}

	public boolean isOverrideExists() {
		return overrideExists;
	}

	public void setOverrideExists(boolean overrideExists) {
		this.overrideExists = overrideExists;
	}

	public NodeDefinition getDestDefinition() {
		return destDefinition;
	}

	public void setDestDefinition(NodeDefinition destDefinition) {
		this.destDefinition = destDefinition;
	}

	public void setPropertyMap(PropertyMap propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Collection<Node> getImportedNodes() {
		return this.imported;
	}

	/*
	 * 
	 * @param is
	 * 
	 * @throws IOException
	 * 
	 * @throws BiffException
	 * 
	 * @author songbo
	 * 
	 * @lastupdate 2010-7-20下午4:30:00
	 */
	public void doImport(InputStream is) throws IOException, BiffException {
		Workbook wb=Workbook.getWorkbook(is);
		this.doImport(wb);
	}

	/*
	 * 导入workbook中包含的节点
	 * 
	 * @param workbook
	 * 
	 * @author songbo
	 * 
	 * @lastupdate 2010-7-20下午4:30:00
	 */
	public void doImport(Workbook workbook) {
		if (workbook== null) {
			return;
		}
		Sheet[] st= workbook.getSheets();
		for (int i = 0; i < st.length; i++){
			if(checkNodeDefinition(st[i])==false)
				return;
			}
		for (int i = 0; i < st.length; i++)
			importNode(st[i]);
	}

	/*
	 * 检查大纲是否存在
	 * 
	 * @param sheet
	 * 
	 * @author songbo
	 * 
	 * @lastupdate 2010-7-20下午4:30:00
	 */
	private boolean checkNodeDefinition(Sheet sheet) {
		String id = sheet.getName();
		NodeDefinitionManager manager = this.repository.getDefinitionManager();
		// 不存在要先导入
		if (manager.getDefinition(id) == null) {
			this.eventListener.error("nodeDefinition with id " + id + " not exists, please import at first.");
			return false;
		}
		return true;
	}
	
	/*
	 * 导入节点
	 * 
	 * @param sheet
	 * 
	 * @author songbo
	 * 
	 * @lastupdate 2010-7-20下午4:30:00
	 */
	private void importNode(Sheet sheet) {
		int stRows = sheet.getRows();
		int stCols = sheet.getColumns();
		if (stRows <=1) {
			return;
		}
		String definitionId = sheet.getName();
		Cell[] cellpropId = sheet.getRow(0);
		for(int i=1;i<stRows-1;i++){
			Cell[] cell = sheet.getRow(i);
			String parentId = cell[1].getContents();
			Node parent = null;
			if (parentId != null)
				parent = this.repository.getNode(parentId);
			if (parent == null)
				parent = this.parent;
			if (parent == null) {
				this.eventListener.error("Error in import node, unable to find parent node :" + parentId);
				return;
			}
			String name = cell[2].getContents();
			String id = cell[0].getContents();
			NodeDefinition definition = null;
			if (this.destDefinition != null)
				definition = this.destDefinition;
			else
				definition = this.repository.getDefinitionManager().getDefinition(definitionId);
			if (definition == null) {
				this.eventListener.error("Error in import node ,unable to find definition with id " + definitionId);
				return;
			}
			Node node = this.repository.getNode(id);
			if (!this.overrideExists && node != null) {
				this.eventListener.info("Node with id " + id + " exists, not import.");
				return;
			}
			if (node == null)
				node = parent.addNode(definition, name, id);
			else {
				if (!node.isNodeType(definition.getID()))
					node.addMixinDefinition(definition); // 已经存在的节点添加到辅定义。
			}

			for(int j=3;j<stCols;j++){
				this.importProperty(node, cellpropId[j].getContents(),cell[j].getContents());
			}
			if (this.propertyMap != null) {
				for (Iterator<String> iterator = this.propertyMap.keys(); iterator.hasNext();) {
					String propID = iterator.next();
					if (this.propertyMap.mapToValue(propID))
						node.setProperty(propID, this.propertyMap.getMappedValue(propID));
	        	}
			}
			this.eventListener.nodeImported(node);
		}

	}

	/*
	 * 导入属性
	 * 
	 * @param node
	 * 
	 * @param propId
	 * 
	 * @param val
	 * 
	 * @author songbo
	 * 
	 * @lastupdate 2010-7-20下午4:30:00
	 */
	private void importProperty(Node node, String propId, String val) {
		String name = propId;
		Property property = null;
		if (this.propertyMap == null)
			property = node.getProperty(propId);
		else{
			name = this.propertyMap.getKey(propId);
			if(name == null)
				return;
			property = node.getProperty(propId);
		}
		if (property == null)
			return;
		Vector<Value> values = new Vector<Value>();
			Value value = this.importValue(val, property.getType());
			if (value != null)
				values.add(value);
			node.setProperty(name, values.toArray(new Value[values.size()]));
		}

	/*
	 * 导入属性值
	 * 
	 * @param value
	 * 
	 * @param type
	 * 
	 * @return
	 * 
	 * @author songbo
	 * 
	 * @lastupdate 2010-7-20下午4:30:00
	 * 
	 */
	private Value importValue(String value, int type) {
		Value result;
		try {
			switch (type) {
			// 二进制属性，将二进制内容转成Base64编码保存
			case PropertyType.BINARY:
				Base64 decoder = new Base64();
				byte[] data = decoder.decode(value);
				InputStream is = new ByteArrayInputStream(data);
				result = this.repository.getValueFactory().createValue(is);
				break;
			case PropertyType.REFERENCE:
				Node reference = this.repository.getNode(value);
				result = this.repository.getValueFactory().createValue(reference);
				break;
			default:
				result = this.repository.getValueFactory().createValue(value, type);
			}
			return result;
		} catch (ValueFormatException vfe) {
			this.eventListener.error("Unable to convert value " + value + " to " + type + " format");
			return null;
		}
	}

	public void setEventListener(ImporterEventListener eventListener) {
		this.eventListener = eventListener;
	}

}
