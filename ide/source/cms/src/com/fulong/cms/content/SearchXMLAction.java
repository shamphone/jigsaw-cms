/**
 * 
 */
package com.fulong.cms.content;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.RequestUtils;
import org.w3c.dom.Document;

import com.fulong.cms.AjaxAction;
import com.fulong.longcon.exchange.DefaultPropertyMap;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.PropertyMap;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;

/**
 * 生成搜索结果的XML 输入： 1. definition：分类库 2. pageNo: 分页号 3. pageSize：页面大小 4.
 * properties: 即将输出的属性，多值，缺省为输出全部 5. filters: 过滤条件 6. orders：排序条件
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
public class SearchXMLAction extends AjaxAction {
	@Override
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String definitionID = request.getParameter("definition");
		NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(definitionID);
		URL url = RequestUtils.serverURL(request);
		DefaultXMLExporter exporter=new DefaultXMLExporter(this.getRepository(request), url);
		exporter.setExportChildren(false);
		exporter.setExportPathes(false);
		exporter.setExportReferences(false);		
		String properties[] = request.getParameterValues("properties");		
		if(properties!=null){
			PropertyMap map = new DefaultPropertyMap(new String[0]);
			for(int i=0;i<properties.length;i++){
				map.addPropertyMap(properties[i], properties[i]);
			}
			exporter.setPropertyMap(map);
		}		
		
		Query query = this.getRepository(request).getQueryManager().createQuery(category, Query.SQL);

		/**
		 * 处理过滤条件
		 */
		String[] filters = request.getParameterValues("filters");
		if ((filters != null) && (filters.length > 0)) {
			for (int i = 0; i < filters.length; i++) {
				FilterParser parser = this.getFilterParser(request, response);
				parser.parser(filters[i]);
				parser.addToQuery(query);
			}
		}
		/**
		 * 处理排序条件
		 */
		String[] orders = request.getParameterValues("orders");
		if (orders != null)
			for (int i = 0; i < orders.length; i++) {
				String[] splits = orders[i].split("\\W");
				String property = null;
				boolean asc = true;
				if (splits.length > 0)
					property = splits[0];
				if (splits.length > 1)
					asc = ("ASC".equalsIgnoreCase(splits[1]));
				if (property != null)
					query.sortByProperty(property, asc);
			}		
		
		NodeIterator<Node> it = query.nodes();
		
		int pageSize = 20;
		int pageNo = 0;
		if(request.getParameter("pageSize")!=null)
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		if(request.getParameter("pageNo")!=null)
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		
		it.skip(pageSize*pageNo);
		int count = 0;
		while(it.hasNext() && count<pageSize ){
			exporter.export(it.nextNode());
			count++;
		}
		return exporter.getDocument();

	}
}
