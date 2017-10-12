/**
 * 
 */
package com.fulong.cms.definition;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.RequestUtils;
import org.w3c.dom.Document;

import com.fulong.cms.CMSAjaxAction;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.XMLExporter;
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
public class DefinitionXMLAction extends CMSAjaxAction {

	/*
	 * 
	 * @see
	 * com.fulong.cms.definition.DefinitionBaseAction#definitionPerform(org.
	 * apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * @param mapping
	 * 
	 * @param form
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 * @author lixf
	 */
	@Override
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		URL baseURL = new URL(RequestUtils.serverURL(request), request.getContextPath());
		XMLExporter exporter = new DefaultXMLExporter(this.getRepository(request), baseURL);
		String[] ids = request.getParameterValues("ID");
		if (ids != null)
			for (int i = 0; i < ids.length; i++) {
				NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(ids[i]);
				if(definition!=null){
					exporter.export(definition);
				}
			}
		return exporter.getDocument();
	}

}
