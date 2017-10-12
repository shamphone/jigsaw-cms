/**
 * 
 */
package com.fulong.cms.definition;

import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.exchange.ExcelExporter;
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
public class ExportExcelAction extends DefinitionBaseAction {

	@Override
	public ActionForward definitionPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExcelExporter exporter = new ExcelExporter(this.getRepository(request));
		String definitionID = request.getParameter("definitionID");
		String selectedNodes =  request.getParameter("selectedNodes");
		NodeDefinition definition =this.getRepository(request).getDefinitionManager().getDefinition(definitionID);
		exporter.exportNodeDefinition(definition);
		String[] columns = request.getParameterValues("column");
		exporter.exportDefault();//添加三个基本属性
		if(columns!=null)
		for(int i=0;i<columns.length;i++)
			exporter.exportColumn(columns[i]);
		else
			exporter.exportAllColumns();
		String[] ids = request.getParameterValues("ID");
		String aid = null;
		if(selectedNodes.equals("false")){
			exporter.exportAllNodes();
			Query query = this.getRepository(request).getQueryManager().createQuery(definition, Query.SQL);
			Iterator<Node> iterator = query.nodes();
			Node node = iterator.next();
			aid= node.getID();	
			exporter.exportPropertyType(this.getRepository(request).getNode(aid));//根据第一个节点判断各属性的类型
		}else{
			if (ids != null){
				for (int i = 0; i < ids.length; i++) {
					Node  node= this.getRepository(request).getNode(ids[i]);
					exporter.exportNode(node);
				}
			   exporter.exportPropertyType(this.getRepository(request).getNode(ids[0]));
			}
		}
		response.setContentType("application/octet-stream");   
	    response.addHeader("Content-Disposition","attachment;filename=data.xls");
	    ServletOutputStream os =  response.getOutputStream();
	    exporter.write(os);
	    os.close();  		
		return null;
	}

}

