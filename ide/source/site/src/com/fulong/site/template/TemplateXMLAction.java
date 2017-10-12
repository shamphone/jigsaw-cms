/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.site.template;

import java.beans.PropertyDescriptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Document;

import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.SiteAjaxAction;

/**
 * TemplateXMLAction
 * 获取模版信息的
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-21
 */
public class TemplateXMLAction  extends SiteAjaxAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.fulong.site.SiteAjaxAction#renderXML(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String templateName = request.getParameter("template");
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
		if(template==null){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "unknown template name:'"+templateName+"'.");
			return null;
		}
		PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(template);
		ParameterSet set= new ParameterSet();
		for(int i=0;i<properties.length;i++){
			String name =properties[i].getName(); 
			Object value = PropertyUtils.getProperty(template, name);
			if(value != null)
				set.add(name, value.toString());
			else
				set.add(name, "");
		}
		return set.toDocument();
	}


}
