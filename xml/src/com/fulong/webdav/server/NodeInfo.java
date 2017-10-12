package com.fulong.webdav.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.DefaultXMLImporter;
import com.fulong.longcon.exchange.XMLExporter;
import com.fulong.longcon.exchange.XMLImporter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Repository;
/**
 * 
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
public class NodeInfo implements ResourceInfo {
	private Repository repository;
	private Node content;
	private NodeDefinition definition;
	private URL url;
	private Node parent;

	public NodeInfo(URL url, Repository repository) {
		this.url = url;
		this.repository = repository;
		
	}
	/* 
	 * 已存在的节点
	 * @param node
	 * @param definition
	 * @author lixf
	 * @lastupdate 2009-9-28下午09:55:07
	 */
	public void setNode(Node node, NodeDefinition definition){
		this.definition = definition;
		this.content = node;
		this.parent = node.getParent();
	}

	/* 
	 * 未存在的节点，需保留节点信息。还有一种情况是节点存在，但是不属于这个分类
	 * @param parent
	 * @param definition
	 * @author lixf
	 * @lastupdate 2009-9-28下午09:55:21
	 */
	public void setParent(Node parent, NodeDefinition definition){
		this.definition = definition;
		this.content = null;
		this.parent=parent;
	}

	/**
	 * 
	 * @param resource
	 *            ResourceInfo
	 * @throws IOException
	 */
	public void copy(ResourceInfo resource) throws IOException {

	}

	/**
	 * 
	 * @return Map <错误页面的path, 错误代码>
	 */
	public boolean delete() {
		this.repository.delete(this.content);
		return true;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean exists() {
		return (this.content != null);
	}

	/**
	 * 
	 * @return Iterator
	 */
	public Iterator<ResourceInfo> getChildren() {
		return new Vector<ResourceInfo>().iterator();
	}

	/**
	 * 
	 * @return String
	 */
	public String getContentType() {
		return "text/xml";
	}

	/**
	 * 
	 * @return Principal
	 */
	public Principal getCreator() {
		if (content.getProperty("creator") != null)
			return (Principal) content.getProperty("creator");
		return null;
	}

	/**
	 * 
	 * @return String
	 */
	public String getDisplayName() {
		return this.content.getID();
	}

	/**
	 * 将内容转成xml的流，发送到客户端
	 * 
	 * @return InputStream
	 * @throws IOException
	 */
	public InputStream read() throws IOException {
		try {
			XMLExporter exporter = new DefaultXMLExporter(this.repository, url);
			exporter.export(this.content);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			exporter.write(buffer);
			return new ByteArrayInputStream(buffer.toByteArray());			
		} catch (ParserConfigurationException ex) {
			throw new IOException(ex.getMessage());
		}
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getLastAccessed() {
		return null;
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getLastModified() {
		Property property = this.content.getProperty("updatedTime");
		if ((property != null) && (property.getDate() != null))
			return property.getDate().getTime();
		return new Date();
	}

	/**
	 * 
	 * @return long
	 */
	public long getLength() {
		try {
			XMLExporter exporter = new DefaultXMLExporter(this.repository, url);
			exporter.export(this.content);
			return exporter.getLength();
		} catch (Exception ex) {
			return 0l;
		}
	}

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return this.content.getID();
	}

	/**
	 * 
	 * @return ResourceInfo
	 */
	public ResourceInfo getParent() {
		return new NodeDefinitionInfo(this.url, repository, this.definition);
	}

	/**
	 * 
	 * @return String
	 */
	public String getPath() {
		if (this.content != null)
			return "/xml/" + this.content.getDefinition().getID() + "/" + this.content.getID() + ".xml";
		else
			return "/xml/" + this.content.getDefinition().getID() + "/" + System.currentTimeMillis() + ".xml";
	}

	/**
	 * 
	 * @return String
	 */
	public String getReadme() {
		return null;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isDirectory() {
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isFile() {
		return true;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isHidden() {
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isReadonly() {
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isReserved() {
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isRoot() {
		return false;
	}

	/**
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	public boolean mkdirs() throws IOException {
		return false;
	}

	/**
	 * 
	 * @param contentType
	 *            String
	 */
	public void setContentType(String contentType) {
	}

	/**
	 * 
	 * @param creator
	 *            Principal
	 */
	public void setCreator(Principal creator) {
		if (content.getProperty("creator") != null)
			content.setProperty("creator", creator.getName());
	}

	/**
	 * 
	 * @param is
	 *            InputStream
	 * @param start
	 *            long
	 * @param length
	 *            long
	 * @throws IOException
	 */
	public void write(InputStream is, long start, long length) throws IOException {
		write(is);
	}

	/**
	 * 将xml文件流is的内容更新到node节点中
	 * 
	 * @param is
	 *            InputStream
	 * @throws IOException
	 * @throws
	 */
	public void write(InputStream is) throws IOException {
		byte[] bytes = IOUtils.toByteArray(is); // 读到内存中，处理出错情况；
		if (bytes.length == 0)
			return;
		XMLImporter importer = new DefaultXMLImporter(this.repository, this.url);
		importer.setParent(this.parent);
		importer.setDestDefinition(this.definition);
		importer.setOverrideExists(true);
		try {
			importer.doImport(new ByteArrayInputStream(bytes));
			this.content = importer.getImportedNodes().iterator().next();
		} catch (ParserConfigurationException e) {
			throw new IOException(e.getMessage());
		} catch (SAXException e) {
			try {
				// 有时候会多出4个字节。原因未明
				importer.doImport(new ByteArrayInputStream(bytes, 0, bytes.length - 4));
				this.content = importer.getImportedNodes().iterator().next();
			} catch (Exception ex) {
				throw new IOException(ex.getMessage());
			}

		}
	}

	public Date getCreationDate() {
		Property property = this.content.getProperty("createdTime");
		if ((property != null) && (property.getDate() != null))
			return property.getDate().getTime();
		return new Date();
	}
	@Override
	public boolean move(ResourceInfo dest) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
