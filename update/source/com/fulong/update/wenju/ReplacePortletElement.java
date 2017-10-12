package com.fulong.update.wenju;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.fulong.update.portlet.DefaultPortletElement;
import com.fulong.update.portlet.Visitor;

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
 * @date 2010-11-10	
 * @version 1.0.1
 */

public class ReplacePortletElement extends DefaultPortletElement{
	
	public ReplacePortletElement(String xml,File file) throws SAXException, IOException{
		super(xml, file);
	}
	
	protected void doProcess(Visitor visitor) throws Exception{
		if("singleSavebutton".equalsIgnoreCase(getType())||"savebutton".equalsIgnoreCase(getType())){
			String[] values = preferenceSet.getValues("default-values");
			if(values!=null){
				for(int i=0;i<values.length;i++){
					values[i] = values[i].replaceAll("待提交", "待发布");
				}
				preferenceSet.put("default-values", values);
			}
		}
		if("savebutton".equalsIgnoreCase(getType())){
			String[] values = preferenceSet.getValues("value");
			if(values!=null&&values.length!=0){
				String value = values[0].replaceAll("提交", "发布");
				preferenceSet.put("value", value);
			}
		}
	}

}
