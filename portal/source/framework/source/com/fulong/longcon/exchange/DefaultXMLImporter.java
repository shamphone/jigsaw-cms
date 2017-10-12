/**
 * 
 */
package com.fulong.longcon.exchange;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.Iterator;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionManager;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;

/**
 * 
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
public class DefaultXMLImporter implements XMLImporter {
	private Repository repository;
	private URL baseURL;
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
	 * 
	 * @param repository
	 * @param baseURL
	 */
	public DefaultXMLImporter(Repository repository, URL baseURL) {
		this.repository = repository;
		this.baseURL = baseURL;
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

	public URL getBaseURL() {
		return this.baseURL;
	}

	/*
	 * 导入url指定的xml文件中的节点
	 * 
	 * @param url 由coolink产生的xml文件
	 * 
	 * @throws IOException
	 * 
	 * @throws SAXException
	 * 
	 * @throws ParserConfigurationException
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-10-20上午11:17:52
	 */
	public void doImport(URL url) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document document = dbf.newDocumentBuilder().parse(url.toString());
		this.doImport(document);
	}

	/*
	 * 
	 * @param is
	 * 
	 * @throws IOException
	 * 
	 * @throws SAXException
	 * 
	 * @throws ParserConfigurationException
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午10:53:07
	 */
	public void doImport(InputStream is) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		Document document = dbf.newDocumentBuilder().parse(is);
		this.doImport(document);

	}

	/*
	 * 导入document中包含的节点
	 * 
	 * @param document
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午10:17:09
	 */
	public void doImport(Document document) {
		Element root = document.getDocumentElement();
		if (root == null) {
			return;
		}
		try {
			this.baseURL = new URL(root.getAttribute("base"));
		} catch (MalformedURLException e) {
			this.eventListener.error("the base url is illegal. " + root.getAttribute("base"));
		}
		NodeList nodes = root.getElementsByTagName("definition");
		for (int i = 0; i < nodes.getLength(); i++)
			importNodeDefinition((Element) nodes.item(i));

		nodes = root.getElementsByTagName("node");
		for (int i = 0; i < nodes.getLength(); i++)
			importNode((Element) nodes.item(i));
	}

	/*
	 * 导入节点定义
	 * 
	 * @param root
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-10-20上午11:45:19
	 */
	private void importNodeDefinition(Element root) {
		String id = root.getAttribute("id");
		NodeDefinitionManager manager = this.repository.getDefinitionManager();
		// 已经存在，不导入；
		if (manager.getDefinition(id) != null) {
			this.eventListener.info("nodeDefinition with id " + id + " exists, ignore.");
			return;
		}
		NodeDefinition superDef = manager.getDefinition(root.getAttribute("superId"));
		if (superDef == null)
			superDef = manager.getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinition definition = this.repository.getDefinitionManager().createDefinition(root.getAttribute("id"), root.getAttribute("name"), superDef);
		NodeList nodes = root.getElementsByTagName("property");
		for (int i = 0; i < nodes.getLength(); i++)
			importPropertyDefinition(definition, (Element) nodes.item(i));
		this.eventListener.nodeDefinitionImported(definition);
	}

	/*
	 * 
	 * @param definition
	 * 
	 * @param element
	 * 
	 * @todo 如何保证首先导入子节点和引用节点类型
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-10-20上午11:45:08
	 */
	private void importPropertyDefinition(NodeDefinition definition, Element element) {
		int type = Integer.parseInt(element.getAttribute("type"));
		String id = element.getAttribute("id");
		if (definition.getPropertyDefinition(id) != null) {
			this.eventListener.info("property with id " + id + " exists. ");
			return;
		}

		PropertyDefinition property = null;
		switch (type) {
		case PropertyType.FIX:
			String childType = element.getAttribute("childType");
			NodeDefinition child = this.repository.getDefinitionManager().getDefinition(childType);
			if (child != null)
				property = definition.addChildNodeDefinition(child, id);
			break;
		default:
			property = definition.addPropertyDefinition(type, id);
		}
		if (property == null)
			return;
		property.setName(element.getAttribute("name"));
		property.setLength(Integer.parseInt(element.getAttribute("minLength")), Integer.parseInt(element.getAttribute("maxLength")));
		this.eventListener.propertyDefinitionImported(property);
	}

	/*
	 * 导入节点
	 * 
	 * @param root
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午10:16:50
	 */
	private void importNode(Element root) {
		if (root == null) {
			return;
		}
		String parentId = root.getAttribute("parentId");
		Node parent = null;
		if (parentId != null)
			parent = this.repository.getNode(parentId);
		if (parent == null)
			parent = this.parent;
		if (parent == null) {
			this.eventListener.error("Error in import node, unable to find parent node :" + root.toString());
			return;
		}

		String name = root.getAttribute("name");
		String id = root.getAttribute("id");
		String definitionId = root.getAttribute("definitionId");
		boolean primary = (root.getAttribute("primary") != null);

		NodeDefinition definition = null;

		if (primary && (this.destDefinition != null))
			definition = this.destDefinition;
		else
			definition = this.repository.getDefinitionManager().getDefinition(definitionId);

		if (definition == null) {
			this.eventListener.error("Error in import node ,unable to find definition with id " + definitionId);
			return;
		}

		Node node = repository.getNode(id);
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

		NodeList properties = root.getElementsByTagName("property");
		for (int i = 0; i < properties.getLength(); i++) {
			this.importProperty(node, (Element) properties.item(i));
		}
		if (this.propertyMap != null) {
			for (Iterator<String> iterator = this.propertyMap.keys(); iterator.hasNext();) {
				String propID = iterator.next();
				if (this.propertyMap.mapToValue(propID))
					node.setProperty(propID, this.propertyMap.getMappedValue(propID));
			}
		}
		if (primary)
			this.imported.add(node);
		this.eventListener.nodeImported(node);
	}

	/*
	 * 导入属性
	 * 
	 * @param node
	 * 
	 * @param element
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午10:16:15
	 */
	private void importProperty(Node node, Element element) {
		String name = element.getAttribute("name");
		Property property = null;
		if (this.propertyMap == null)
			property = node.getProperty(name);
		else{
			name = this.propertyMap.getKey(name);
			if(name == null)
				return;
			property = node.getProperty(name);
		}
		if (property == null)
			return;
		NodeList nodes = element.getElementsByTagName("value");
		Vector<Value> values = new Vector<Value>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Value value = this.importValue((Element) nodes.item(i), property.getType());
			if (value != null)
				values.add(value);
		}
		node.setProperty(name, values.toArray(new Value[values.size()]));
	}

	/*
	 * 导入属性值
	 * 
	 * @param element
	 * 
	 * @param type
	 * 
	 * @return
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午10:08:09
	 * 
	 * @todo 引用节点处理：这时候被引用的节点可能还没有导入；
	 */
	private Value importValue(Element element, int type) {
		NodeList nodes = element.getChildNodes();
		if (nodes.getLength() == 0)
			return null;
		String value = nodes.item(0).getNodeValue();
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
