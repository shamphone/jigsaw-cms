/**
 * 
 */
package com.fulong.longcon.site.channel;

import java.io.IOException;

import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.fulong.common.FileUtils;

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
 * @date 2010-10-12	
 * @version 1.0.1
 */
public class RssChannel extends GeneralChannel{

	@Override
	public String getType() {		
		return "rss";
	}

	@Override
	public String getDisplayName() {
		try {
			Document document = FileUtils.readXML(file);
			Element metadata = DomUtils.getChildElementByTagName(document.getDocumentElement(), "metadata");
			if(metadata!=null){
				String displayName = metadata.getAttribute("displayName");
				if(displayName!=null){
					return displayName;
				}
			}
		} catch (IOException e) {
			
		} catch (SAXException e) {
			
		}
		return this.getName();
	}
}