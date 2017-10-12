package com.fulong.update.portlet;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.fulong.portal.model.PreferenceParser;
import com.fulong.portal.model.PreferenceSet;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-10-8	
 * @version 1.0.1
 */
public class DefaultPortletElement implements PortletElement{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	protected final String type;
	protected final String id;
	protected final PreferenceSet preferenceSet;
	protected final File file;
	
	public DefaultPortletElement(String xml,File file) throws SAXException, IOException{
		PreferenceParser parser = new PreferenceParser();
		this.preferenceSet = parser.parse(xml);
		this.type = parser.getType();
		this.file = file;
		this.id = parser.getId();
	}
	
	public String getType() {
		return type;
	}
	
	public String accept(Visitor visitor) throws Exception{
		doProcess(visitor);
		return this.toXML();
	}
	
	protected void doProcess(Visitor visitor)throws Exception{
		
	}
	
	public String toXML(){
		return "<fulong:portlet id=\""+id+"\" type=\""+type+"\">"+preferenceSet.toXML()+"</fulong:portlet>";
	}
	
}
