package com.fulong.cms;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;

/**
 * 获取内容分类下的内容，在占位符中使用
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @version 2.0
 */
public class ContentsAction extends AjaxAction {
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String categoryId = request.getParameter("category");
		String countStr = request.getParameter("count");
		String title = request.getParameter("property");
		int count = 10;
		if ((countStr != null) && (countStr.length() > 0))
			count = Integer.parseInt(countStr);
		NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryId);

		Query query = this.getRepository(request).getQueryManager().createQuery(category, Query.SQL);
		ParameterSet set = new ParameterSet();
		Iterator<Node> iterator = query.nodes();
		for (int i = 0; (i < count) && iterator.hasNext(); i++) {
			Node content = (Node) iterator.next();
			String name = content.getProperty(title).getValue().getString();
			set.add(name == null ? "" : name, content.getID());
		}
		request.setAttribute("categoryID", categoryId);
		return set.toDocument();
	}

}
