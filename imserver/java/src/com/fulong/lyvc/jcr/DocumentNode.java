/**
 * 
 */
package com.fulong.lyvc.jcr;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.repository.Property;
import com.fulong.lyvc.Document;

/**
 * DocumentNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class DocumentNode extends NodeWrapper implements Document {
	
	private static final String DOCURL = "path";				//文档的路径
	private static final String FILENAME = "title";			//文档名称
	
	public DocumentNode(Node node) {
		super(node);
	}

	/**
	 * 该方法用来获取文件的id
	 *
	 */
	public String getDocId() {
		return this.getNode().getID();
	}

	/**
	 * 该方法用来获取文件路径
	 *
	 */
	public String getDocURL() {
		return this.getNode().getPath();
	}

	/**
	 * 该方法用来获取文件名称
	 *
	 */
	public String getFileName() {
		String fileName = "";
		Property property = this.getNode().getProperty(FILENAME);
		if(property != null)
			fileName = property.getString();
		
		return fileName;
	}

	/**
	 * 该方法用来设置文件路径
	 *
	 */
	public void setDocURL(String url) {
		Property property = this.getNode().getProperty(DOCURL);
		if(property != null)
			property.setValue(url);
	}

	/**
	 * 该方法用来设置文件名称
	 *
	 */
	public void setFileName(String fileName) {
		Property property = this.getNode().getProperty(FILENAME);
		if(property != null)
			property.setValue(fileName);
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
		// TODO Auto-generated method stub
		
	}
}
