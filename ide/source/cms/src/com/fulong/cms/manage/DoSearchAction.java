/**
 * 
 */
package com.fulong.cms.manage;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.IteratorUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.cms.form.SearchNodesForm;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;
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
public class DoSearchAction  extends CMSBaseAction {

	/**
	 * doPerform
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SearchNodesForm searchForm = (SearchNodesForm) form;
		String categoryID = searchForm.getDefinitionID();
		if (categoryID == null) {
			categoryID = request.getParameter("categoryID");
		}
		NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryID);

		if (category == null) {
			return mapping.findForward("noCategory");
		}
		
		List<PropertyDefinition> columns;
		String columnIDs[] = searchForm.getDisplayColumns();
		if(columnIDs == null)
			columns = IteratorUtils.toList(category.propertyDefinitions());
		else{
			columns = new Vector<PropertyDefinition>();
			for(int i=0;i<columnIDs.length;i++){
				PropertyDefinition property = category.getPropertyDefinition(columnIDs[i]);
				if(property!=null)
				columns.add(property);
			}
		}

		// ListView view = this.getListViewManager().getListView(categoryID);
		// view.setColumns(properties);
		
		Query query = this.getRepository(request).getQueryManager().createQuery(category, Query.SQL);

		/**
		 * 处理过滤条件
		 */
		String[] filters = searchForm.getFilters();
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
		String orderBy = searchForm.getOrderBy1();
		String asc = searchForm.getAsc1();
		if(asc == null)
			asc = "0";
		query.sortByProperty(orderBy, asc.equals("0") ? true : false);
		NodeIterator<Node> it = query.nodes();

		it.skip(this.getPager(request).getFromIndex());
		this.setPager(request, it.getSize());

		request.setAttribute("category", category);
		request.setAttribute("contents", it);

		if (category != null) {
			// 设置视图
			request.setAttribute("columns", columns);
			request.setAttribute("properties", IteratorUtils.toList(category.propertyDefinitions()));
			request.setAttribute("nodeDefinition", category);

			ActionForward forward = mapping.findForward(category.getID());
			if (forward == null) {
				forward = mapping.findForward("success");
			}
			return forward;
		}
		return mapping.findForward("success");
	}

}
