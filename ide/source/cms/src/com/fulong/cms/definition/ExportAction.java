/**
 * 
 */
package com.fulong.cms.definition;

import java.net.URL;
import java.util.Iterator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.XMLExporter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;

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
public class ExportAction extends DefinitionBaseAction {
	@Override
	public ActionForward definitionPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		URL baseURL = new URL(RequestUtils.serverURL(request), request.getContextPath());
		XMLExporter exporter = new DefaultXMLExporter(this.getRepository(request), baseURL);
		//String[] ids = request.getParameterValues("ID"); 
		String[] defID = request.getParameterValues("ID"); //获得内容分类的ID
		String exportDefinition = request.getParameter("exportDefinition");
		boolean exportAllNodes = (request.getParameter("exportAllNodes") != null&&request.getParameter("exportAllNodes").equals("true"));
		/**
		 * liulei modified in 2010-1-29
		 * 修改原因：处理导出情况的逻辑出现问题。在选择导出内容分类下全部信息的时候，导出的信息为空文件
		 */
		if(exportAllNodes) //导出内容分类下全部的节点
		{
			if(defID!=null)  //如果用户期望导出全部的节点，那么必须借助内容分类的ID，否则无法选择全部的节点
			{
				for (int i = 0; i < defID.length; i++) 
				{
					NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(defID[i]);
					if(exportDefinition!=null&&exportDefinition.equals("true")){
						exporter.export(definition);
					}
					Query query = this.getRepository(request).getQueryManager().createQuery(definition, Query.SQL); //进行一个查询，获得该内容分类下全部的节点
					for (Iterator<Node> iterator = query.nodes(); iterator.hasNext();)
					{
						exporter.export(iterator.next());
					}
				}
			}
		}
		else //不导出全部的节点，只导出部分选中的内容
		{
			String[] ids = request.getParameterValues("nodeID");		
			if(ids != null)
			{
				if(defID!=null)//在导出选中节点的同时，连同内容分类信息一并导出
				{
					for(int i=0;i<defID.length;i++)
					{
						NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(defID[i]);
						if(exportDefinition!=null&&exportDefinition.equals("true")){
							exporter.export(definition);
						}	
					}
				}
				for(int i = 0; i < ids.length; i++) 
				{
					Node node = this.getRepository(request).getNode(ids[i]);
					if (node != null)
						exporter.export(node);
				}
			}else{
				if(defID!=null)//没有选择任何节点，只导出内容分类的信息
				{
					for(int i=0;i<defID.length;i++)
					{
						NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(defID[i]);
						if(exportDefinition!=null&&exportDefinition.equals("true")){
							exporter.export(definition);
						}
					}
				}
			}
		}
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment; filename=definitions.xml");
		ServletOutputStream os = response.getOutputStream();
		exporter.write(os);
		os.close();
		return null;
	}

}
