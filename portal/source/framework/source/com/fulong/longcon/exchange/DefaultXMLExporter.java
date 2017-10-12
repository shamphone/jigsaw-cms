/**
 * 
 */
package com.fulong.longcon.exchange;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;

/**
 * 导出节点到XML。按照如下规则来转换： <repository base="url">
 * 这是根，指明导出的基础url,这个url用来处理大文本中的图片连接（未实现）的地址转换。 <node id="" name="" parentId=""
 * primary=""> 节点的id, name,
 * parentId意义自明，primary指要导出的主节点，相对于被引用或者子节点而言。引用节点和子节点没有primary标记
 * 。注意被引用节点导出时，位置在引用节点之前。 <property name="" type="">
 * 每个基本属性都对应一个节点，复合属性被导出成node节点，且放在repository节点下，在父结点之后。 <value>值</value>
 * 每个值对应一个value标签，多个值对应多个value标签。 </property> </node> </repository>
 * 
 * Coolink协同工作框架模型
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
public class DefaultXMLExporter implements XMLExporter{
	private static final Log log = LogFactory.getLog(XMLExporter.class);
	private Repository repository;
	private URL baseURL;
	private Document document;
	private Element root;
	private OutputFormat outputFormat;

	/**
	 * 已经导出的节点集合
	 */
	private Vector<Node> exportedNodes;

	/**
	 * 已经导出的节点集合
	 */
	private Vector<NodeDefinition> exportedDefinitions;
	/**
	 * 是否导出引用属性值对应的节点
	 */
	private boolean exportReferences = false;
	/**
	 * 是否导出路径属性值对应的节点
	 */
	private boolean exportPathes = false;
	/**
	 * 是否导出子节点
	 */
	private boolean exportChildren = false;
	
	/**
	 * 属性映射
	 */
	private PropertyMap propertyMap = null;

	public DefaultXMLExporter(Repository repository, URL baseURL) throws ParserConfigurationException {
		this.repository = repository;
		this.baseURL = baseURL;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.newDocument();
		root = document.createElement("repository");
		document.appendChild(root);
		root.setAttribute("base", this.baseURL.toString());
		this.exportedNodes = new Vector<Node>();
		this.exportedDefinitions = new Vector<NodeDefinition>();
		this.outputFormat = new OutputFormat();
	}

	/**
	 * 得到最终的Document
	 */
	public Document getDocument() {
		return this.document;
	}

	/**
	 * 获取导出的XML数据流
	 */
	public void write(OutputStream os) throws IOException {		
		XMLSerializer xs = new XMLSerializer(os, this.outputFormat);
		xs.serialize(this.getDocument());
	}
	/**
	 * 获取导出的XML数据流
	 */
	public InputStream getStream() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		this.write(buffer);
		return new ByteArrayInputStream(buffer.toByteArray());
	}
	/**
	 * 获取文件大小
	 */
	public int getLength() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		OutputFormat format = null;
		format = new OutputFormat();
		XMLSerializer xs = new XMLSerializer(buffer, format);
		xs.serialize(this.getDocument());
		return buffer.size();
	}

	/**
	 * 获取导出的主节点。
	 */
	public Collection<Node> getExportedNodes() {
		return this.exportedNodes;
	}

	/**
	 * 设置是否道出引用节点
	 */
	public boolean isExportReferences() {
		return exportReferences;
	}

	public void setExportReferences(boolean exportReferences) {
		this.exportReferences = exportReferences;
	}

	/**
	 * 是否导出路径节点
	 */
	public boolean isExportPathes() {
		return exportPathes;
	}

	public void setExportPathes(boolean exportPathes) {
		this.exportPathes = exportPathes;
	}

	/**
	 * 是否导出子节点
	 */
	public boolean isExportChildren() {
		return exportChildren;
	}

	public void setExportChildren(boolean exportChildren) {
		this.exportChildren = exportChildren;
	}
	
	
	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(PropertyMap propertyMap) {
		this.propertyMap = propertyMap;
	}

	/**
	 * 导出分类定义
	 */
	public void export(NodeDefinition definition) throws ParserConfigurationException, DOMException, IOException {
		if(this.exportedDefinitions.contains(definition))
			return;
		this.exportedDefinitions.add(definition);
		this.root.appendChild(this.renderNodeDefinition(definition));
	}
	/**
	 * 生成NodeDefinition对应的XML节点
	 */
	private Element renderNodeDefinition(NodeDefinition definition){
		Element root = document.createElement("definition");
		root.setAttribute("id", definition.getID());
		root.setAttribute("name", definition.getName());
		root.setAttribute("children", ""+definition.getInheritDefinitions(false).getSize());
		if(definition.getSuperDefinition()!=null)
			root.setAttribute("superId", definition.getSuperDefinition().getID());
	
		for(Iterator<PropertyDefinition> iterator = definition.propertyDefinitions();iterator.hasNext();){
			PropertyDefinition property = iterator.next();
			root.appendChild(this.renderPropertyDefinition(property));			
			}
		return root;
	}
	
	/* 
	 * 节点定义对应的元素
	 * @param property
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-20上午10:39:00
	 */
	private Element renderPropertyDefinition(PropertyDefinition property){
		Element child = document.createElement("property");
		child.setAttribute("id" , property.getID());
		child.setAttribute("name" , property.getName());
		child.setAttribute("type" , ""+property.getType());
		child.setAttribute("minLength" , ""+property.getMinLength());
		child.setAttribute("maxLength" , ""+property.getMaxLength());
		child.setAttribute("orderNo" , ""+property.getOrderNo());
		if(property.getReferenceDefinition()!=null)
		child.setAttribute("referenceType" , property.getReferenceType());
		if(property.isMultiple())
			child.setAttribute("multiple", "true");
		if(property.isProtected())
			child.setAttribute("protected", "true");
		if(property.isReadonly())
			child.setAttribute("readonly", "true");
		if(property.getType() == 0){
			ChildNodeDefinition childProperty = (ChildNodeDefinition)property;
			NodeDefinition definition = childProperty.getNodeDefinition();
			if(definition!=null){
				child.setAttribute("childType", definition.getID());
			} else {
				child.setAttribute("childType", "");
			}
		}
		return child;
		
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
	public void export(Node content) throws ParserConfigurationException, DOMException, IOException {
		// 如果已经导出，则不再导，避免重复导出；
		if (this.exportedNodes.contains(content))
			return;

		Element element = this.renderNode(content, this.exportChildren);
		// 要导出的主节点
		element.setAttribute("primary", "true");
		root.appendChild(element);		
		this.exportedNodes.add(content);
		if (exportChildren) {
			NodeIterator<Node> nodes = content.getNodes();
			while (nodes.hasNext()) {
				this.root.appendChild(this.renderNode(nodes.nextNode(),false));
			}
		}		

	}

	/*
	 * 
	 * @param node
	 * 
	 * @return
	 * 
	 * @throws DOMException
	 * 
	 * @throws IOException
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午08:50:02
	 */
	protected Element renderNode(Node node, boolean exportChildren) throws DOMException, IOException {
		log.trace("Begin export node " + node.getID());
		Element root = document.createElement("node");
		root.setAttribute("name", node.getName());
		root.setAttribute("id", node.getID());
		if(node.getParent()!=null){
			root.setAttribute("parentId", node.getParent().getID());
		}else{
			root.setAttribute("parentId", "");
		}
		root.setAttribute("definitionId", node.getDefinition().getID());
		root.setAttribute("children", ""+node.getNodes().getSize());
		// 渲染属性
		if(this.propertyMap == null){ //未定义属性映射，直接输出所有属性
			for (Iterator<Property> iterator = node.properties(); iterator.hasNext();) {
				Property property = (Property) iterator.next();
				root.appendChild(this.renderProperty(property));
			}
		}else{ //已经定义了属性映射，按照属性映射的要求输出；
			for(Iterator<String>keys = propertyMap.keys();keys.hasNext();){
				String key = keys.next();
				if(this.propertyMap.mapToValue(key))
					root.appendChild(this.renderProperty(key, this.propertyMap.getMappedValue(key)));
				else 
					root.appendChild(this.renderProperty(node.getProperty(this.propertyMap.getMappedProperty(key))));
			}
		}
		
		log.trace("Finished export node " + node.getID());
		return root;
	}
	/**
	 * 
	 */
	protected Element renderProperty(String key, String value) throws DOMException, IOException {
		Element element = document.createElement("property");
		element.setAttribute("name", key);
		element.setAttribute("type", "String");
		Element valueElem = document.createElement("value");
		valueElem.appendChild(this.document.createTextNode(value));
		element.appendChild(valueElem);
		return element;
	}


	/*
	 * 渲染属性
	 * 
	 * @param property
	 * 
	 * @return
	 * 
	 * @throws DOMException
	 * 
	 * @throws IOException
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午08:50:13
	 */
	protected Element renderProperty(Property property) throws DOMException, IOException {
		Element element = document.createElement("property");
		String name = property.getID();
		//如果定义了属性映射，则按照缺省的映射名字
		if(this.propertyMap!=null && this.propertyMap.getMappedProperty(name)!=null)
			name = this.propertyMap.getMappedProperty(name);
		element.setAttribute("name", name);
		element.setAttribute("type", PropertyType.nameFromValue(property.getType()));
		Value[] values = property.getValues();
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null)
				element.appendChild(this.renderValue(values[i]));
		}
		return element;
	}

	/*
	 * 渲染值
	 * 
	 * @param value
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午08:50:30
	 */
	protected Element renderValue(Value value) throws IOException {
		Element valueElem = document.createElement("value");
		switch (value.getType()) {
		// 二进制属性，将二进制内容转成Base64编码保存
		case PropertyType.BINARY:
			Base64 encoder = new Base64();
			InputStream is = value.getStream();
			DataInputStream dis = new DataInputStream(is);
			byte[] imageBuffer = new byte[dis.available()];
			dis.read(imageBuffer);
			String textString = encoder.encodeToString(imageBuffer);
			is.close();
			valueElem.appendChild(document.createTextNode(textString));
			break;
		// 字符串和长文本中可能有非法字符，需要用CData字段来保存
		case PropertyType.STRING:
		case PropertyType.TEXT:
			valueElem.appendChild(document.createCDATASection(value.getString()));
			break;
		// 路径根据需要导出对应的节点，而且该节点必须在当前导入节点之前；
		case PropertyType.PATH:
			valueElem.appendChild(document.createTextNode(value.getString()));
			if (this.exportPathes) {
				Node path = this.repository.getNodeByPath(value.getString());
				if (path != null)
					this.root.insertBefore(this.renderNode(path, false), this.root.getFirstChild());
			}
			break;
		// 引用节点根据需要导出对应的节点，而且该节点必须在当前导入节点之前；
		case PropertyType.REFERENCE:
			valueElem.appendChild(document.createTextNode(value.getString()));
			if (this.exportReferences) {
				Node reference = this.repository.getNode(value.getString());
				if (reference != null)
					this.root.insertBefore(this.renderNode(reference, false), this.root.getFirstChild());
			}
			break;
		default:
			valueElem.appendChild(document.createTextNode(value.getString()));
		}
		return valueElem;
	}
}
