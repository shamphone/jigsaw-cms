/**
 * 
 */
package com.fulong.longcon.exchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;

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
public interface XMLImporter {
	/* 
	 * 如果节点已经存在，是否覆盖已有的节点
	 * @param overrideExists
	 * @author lixf
	 * @lastupdate 2009-10-24下午08:27:08
	 */
	public void setOverrideExists(boolean overrideExists) ;


	/* 
	 * 设置目标节点定义。如果已经设置，则将内容放置到这个定义下
	 * @param destDefinition
	 * @author lixf
	 * @lastupdate 2009-10-24下午08:27:42
	 */
	public void setDestDefinition(NodeDefinition destDefinition);


	/* 
	 * 设置父结点。如果有父结点，则将内容放在该父结点下。
	 * @param parent
	 * @author lixf
	 * @lastupdate 2009-10-24下午08:28:24
	 */
	public void setParent(Node parent);
	
	/**
	 * 属性映射设置
	 * 
	 * @param propertyMap
	 * @author lixf
	 * @lastupdate 2009-10-25下午01:51:16
	 */
	public void setPropertyMap(PropertyMap propertyMap) ;
	
	/* 
	 * 设置导入事件侦听器
	 * @param eventListener
	 * @author lixf
	 * @lastupdate 2009-10-24下午08:30:20
	 */
	public void setEventListener(ImporterEventListener eventListener) ;


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
	public void doImport(URL url) throws IOException, SAXException, ParserConfigurationException ;

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
	public void doImport(InputStream is) throws IOException, SAXException, ParserConfigurationException ;
	/*
	 * 导入document中包含的节点
	 * 
	 * @param document
	 * 
	 * @author lixf
	 * 
	 * @lastupdate 2009-9-26下午10:17:09
	 */
	public void doImport(Document document) ;


	/* 
	 * 已经导入成功的节点，包括子节点和可能的引用节点
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-24下午08:29:09
	 */
	public Collection<Node> getImportedNodes();

}
