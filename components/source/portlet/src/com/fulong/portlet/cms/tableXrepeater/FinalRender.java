package com.fulong.portlet.cms.tableXrepeater;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.Pager;
import com.fulong.common.util.RangeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.portlet.cms.ListContentPortletRender;
import com.fulong.portlet.cms.list.ContentListConfig;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @author mali
 * @version 2.0
 */
public class FinalRender extends ListContentPortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		//兼容老版本片段路径
		this.processPath(request);
		//片段的路径
		request.setAttribute("path", getClipPath(request, response, ""));
		
		PortletPreferences preferences = request.getPreferences();
		ContentListConfig config = new ContentListConfig(request
				.getPreferences());
		String categoryId = request.getPreferences().getValue("category", "");

		// 获取内容分类名称占位符链接传递过来的参数（一般用于内容分类重复器中）
		if (preferences.getValue("filterByParamet", "false").equals("on")) {
			if (request.getParameter("definition") != null
					&& !request.getParameter("definition").equals("")) {
				categoryId = request.getParameter("definition");
			}
		}

		NodeDefinition def = this.getRepository().getDefinitionManager()
				.getDefinition(categoryId);

		request.setAttribute("preferences", request.getPreferences());

		// 处理分页
		Pager pager = this.processPage(request, config);

		// 处理引用/复合属性
		String refField = preferences.getValue("refField", null);
		if (refField != null) {
			Node node = lookupNode(request, response);
			if (node == null) {
				return mapping.findForward(NODONE);
			}
			PropertyDefinition pd = def.getPropertyDefinition(refField);
			return this.processRefField(pd, node, refField, preferences,
					request, mapping, config, pager);
		} else {
			// 获取内容列表
			if (def == null) {
				def = lookUpDefinition(request);
			}
			
			//************构建query***************
			Query query = null;
			if(preferences.getValue("lucene", "false").equals("on"))
				query = this.getRepository().getQueryManager().createQuery(def, Query.LUCENE);
			else
				query = this.getRepository().getQueryManager().createQuery(def, Query.SQL);
			
			if (request.getPreferences().getValue("filterByParamet", "false")
					.equals("on")) {
				
				//属性匹配
				this.processProp(def, request, query);
				
				//处理需要基于数据库的属性模糊搜索
				this.processFuzzyPropSearch(request, query);
				
				// 从url中取keyword
				this.processKeywords(request, query);

				// 从url中提取过滤条件
				this.processConditions(request, response, query);
			}
			// 过滤属性处理
			this.processFilter(preferences, request, response, config, query);

			// 排序
			this.processOrder(config, request, query);

			// 从本系统所有网站的内容库中抽取
			this.processGlobal(config, request, response, query);

			// 包含子分类
			boolean recursive = preferences.getValue("recursive", "").equals("true") ? true : false;
			RangeIterator<Node> contents = query.nodes(recursive);
			
			/*
			 * 当无结果时显示 判断
			 */
			String isShowCharactor = preferences.getValue("nonode", "character");
			if (contents.getSize() == 0 && isShowCharactor.equalsIgnoreCase("character")) {
				 return mapping.findForward("no.content");
				//return mapping.findForward(this.NODONE);
			}
			if(contents.getSize() == 0 && isShowCharactor.equalsIgnoreCase("table")){
				request.setAttribute("showBlankTable", "showBlankTable");
			}
			this.setContents(contents, preferences, config, request, pager);
			return mapping.findForward("success");
		}
	}
}
