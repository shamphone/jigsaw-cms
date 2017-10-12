/**
 * 
 */
package com.fulong.longcon.site.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.fulong.common.FileUtils;
import com.fulong.common.ResourceUtils;

/**
 * <p>
 * Title: WebXML24Parser
 * </p>
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * @author lixf
 * @date 2010-5-11 
 * @author luobin
 * @date 2010-6-2
 */
public class WebXML24Parser implements WebXMLParser {
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String DEFAULT_WEB_XML = "web.xml";
	private File file;
	private Document document;
	private Element root;
	
	/**
	 * @param path
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public WebXML24Parser(String path) throws IOException, SAXException {
		this(new File(path));
	}
	/**
	 * @param file
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public WebXML24Parser(File file) throws IOException, SAXException {
		if(!file.exists()){
			InputStream is = ResourceUtils.getResourceAsStream(DEFAULT_WEB_XML);		
			file.createNewFile();
			FileUtils.write(file, is);
		}
		this.file = file;
		// 加载文件内容到Dom树中
		this.document = FileUtils.readXML(this.file);
		this.root = this.document.getDocumentElement();
	}

	/**
	 * 获取param-name值为param的context-param元素的param-value子元素值；如果指定的param不存在，则返回null;
	 * @param param
	 */
	@Override
	public String getContextParamValue(String param) {
		Element contextParam =  getValueNode(param);
		return contextParam==null?null:contextParam.getTextContent();
	}
	
	/**
	 * 获取param-name等于param的元素
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Element getValueNode(String param){
		List<Element> paramList =  DomUtils.getChildElementsByTagName(this.root, "context-param");
		for(int i=0;i<paramList.size();i++){
			Element nameElement =  DomUtils.getChildElementByTagName(paramList.get(i), "param-name");
			if(nameElement!=null&&nameElement.getTextContent().equals(param)){
				Element valueElement = DomUtils.getChildElementByTagName(paramList.get(i), "param-value");
				return valueElement;
			}
		}
		return null;
	}

	/**
	 * 设置display-name元素值
	 * @param name
	 */
	@Override
	public String getDisplayName() {
		Element displayName =  DomUtils.getChildElementByTagName(this.root, "display-name");
		if(displayName!=null){
			return displayName.getTextContent();
		}
		return null;
	}

	/**
	 * 设置param，如果值已存在，则修改，如果不存在则插入一个新的context-param元素
	 * @param param
	 */
	@Override
	public void setContextParamValue(String param, String value){
		if(value==null){
			this.removeContextParam(param);
			return;
		}
		Element contextParam = this.getValueNode(param);
		if(contextParam==null){
			contextParam = this.document.createElement("context-param");
			Element paramName = this.document.createElement("param-name");
			paramName.setTextContent(param);
			Element paramValue = this.document.createElement("param-value");
			paramValue.setTextContent(value);
			contextParam.appendChild(paramName);
			contextParam.appendChild(paramValue);
			this.root.appendChild(contextParam);
		}else{
			contextParam.setTextContent(value);
		}
	}

	/**
	 * 设置display-name元素值
	 * @param name
	 */
	@Override
	public void setDisplayName(String name)   {
		if(name==null){
			removeDisplayName();
			return;
		}
		Element displayName = DomUtils.getChildElementByTagName(this.root, "display-name");
		if(displayName==null){
			displayName = this.document.createElement("display-name");
			displayName.setTextContent(name);
			this.root.appendChild(displayName);
		}else{
			displayName.setTextContent(name);
		}
	}
	/**
	 * 删除display-name元素
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	private void removeDisplayName(){
		List<Element> displayNames = DomUtils.getChildElementsByTagName(this.root, "display-name");
		for(int i=0;i<displayNames.size();i++){
			this.root.removeChild(displayNames.get(i));
		}
	}
	/**
	 * 获取description元素值
	 * @return
	 */
	@Override
	public String getDescription() {
		Element description = DomUtils.getChildElementByTagName(this.root, "description");
		if(description!=null){
			return description.getTextContent();
		}
		return null;
	}
	/**
	 * 设置description值
	 * @param value
	 */
	@Override
	public void setDescription(String value){
		if(value==null){
			removeDescription();
			return;
		}
		Element description = DomUtils.getChildElementByTagName(this.root, "description");
		if(description==null){
			description = this.document.createElement("description");
			description.setTextContent(value);
			this.root.appendChild(description);
		}else{
			description.setTextContent(value);
		}
	}
	/**
	 * 删除description元素
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	private void removeDescription(){
		List<Element> descriptions = DomUtils.getChildElementsByTagName(this.root, "description");
		for(int i=0;i<descriptions.size();i++){
			this.root.removeChild(descriptions.get(i));
		}
	}
	/**
	 * 保存更改；
	 * @throws IOException
	 */
	@Override
	public void save() throws IOException{
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(this.file);
			OutputFormat format = new OutputFormat(Method.XML,DEFAULT_ENCODING,true);
			XMLSerializer serializer = new XMLSerializer(out,format);
			serializer.serialize(this.document);
		} finally{
			if(out!=null){
				out.close();
			}
		}
	}
	/**
	 * 删除指定的context-param元素；如果该元素不存在，则不执行任何操作；
	 * @param param
	 * @throws IOException 
	 */
	@Override
	public void removeContextParam(String param){
		Element valueNode = this.getValueNode(param);
		if(valueNode!=null){
			this.root.removeChild(valueNode.getParentNode());
		}
	}
	/**
	 * 获取webapp元素的ID属性；
	 */
	@Override
	public String getID() {
		return this.root.getAttribute("id");
	}
}