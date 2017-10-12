/**
 * 
 */
package com.fulong.longcon.site.config;

import java.io.IOException;


/**
 * <p>Title: WebXMLParser</p>
 * <p>Description: WEB-INF/web.xml 分析器，负责读、写web.xml文件</p>
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p> 
 * @author    lixf
 * @date      2010-5-11
 */
public interface WebXMLParser {
	/**
	 * 获取display-name元素值
	 * @return
	 */
	public String getDisplayName();
	/**
	 * 设置display-name元素值
	 * @param name
	 */
	public void setDisplayName(String name);
	/**
	 * 获取description元素值
	 * @return
	 */
	public String getDescription();
	/**
	 * 设置description值
	 * @param value
	 */
	public void setDescription(String value);
	
	/**
	 * 获取param-name值为param的context-param元素的param-value子元素值；如果指定的param不存在，则返回null;
	 * @param param
	 */
	public String getContextParamValue(String param);
	
	/**
	 * 设置param，如果值已存在，则修改，如果不存在则插入一个新的context-param元素
	 * @param param
	 */
	public void setContextParamValue(String param, String value);
	
	/**
	 * 删除指定的context-param元素；如果该元素不存在，则不执行任何操作；
	 * @param param
	 */
	public void removeContextParam(String param);
	
	/**
	 * 获取webapp元素的ID属性；
	 */
	public String getID();
	
	/**
	 * 保存更改；
	 * @throws IOException
	 */
	public void save() throws IOException;
	
 
}
