/**
 * 
 */
package com.fulong.longcon.exchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * 
 * 导出节点到XML。按照如下规则来转换： <repository base="url">
 * 这是根，指明导出的基础url,这个url用来处理大文本中的图片连接（未实现）的地址转换。 <node id="" name="" parentId=""
 * primary=""> 节点的id, name,
 * parentId意义自明，primary指要导出的主节点，相对于被引用或者子节点而言。引用节点和子节点没有primary标记
 * 。注意被引用节点导出时，位置在引用节点之前。 <property name="" type="">
 * 每个基本属性都对应一个节点，复合属性被导出成node节点，且放在repository节点下，在父结点之后。 <value>值</value>
 * 每个值对应一个value标签，多个值对应多个value标签。 </property> </node> </repository>
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
public interface XMLExporter {

	/**
	 * 得到最终的Document
	 */
	public Document getDocument() ;

	/**
	 * 获取导出的XML数据流
	 */
	public void write(OutputStream os) throws IOException ;
	/**
	 * 获取导出的XML数据流
	 */
	public InputStream getStream() throws IOException ;
	/**
	 * 获取文件大小
	 */
	public int getLength() throws IOException ;
	/**
	 * 获取导出的主节点。
	 */
	public Collection<Node> getExportedNodes() ;

	/**
	 * 设置是否道出引用节点
	 */
	public void setExportReferences(boolean exportReferences) ;
	/**
	 * 是否导出路径节点
	 */
	public void setExportPathes(boolean exportPathes) ;

	/**
	 * 是否导出子节点
	 */
	public void setExportChildren(boolean exportChildren) ;
	
	/**
	 * 属性映射设置
	 */
	public void setPropertyMap(PropertyMap propertyMap) ;

	/**
	 * 导出分类定义
	 */
	public void export(NodeDefinition definition) throws ParserConfigurationException, DOMException, IOException ;
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
	public void export(Node content) throws ParserConfigurationException, DOMException, IOException;

}
