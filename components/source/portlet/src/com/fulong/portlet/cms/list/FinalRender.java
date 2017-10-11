package com.fulong.portlet.cms.list;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.Pager;
import com.fulong.common.util.RangeIterator;
import com.fulong.common.util.RangeIteratorUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.portlet.cms.ListContentPortletRender;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
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
	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		ContentListConfig config = new ContentListConfig(request.getPreferences());
		if (config.getCategory() == null) {
			return mapping.findForward("no.content");
		}

		//
		String filed[] = new String[10]; // 可存储多过滤条件
		String value[] = new String[10]; // 可存储多过滤条件
		if (config.getFilterAuto()) { // 如果config.getFilterAuto()为真，则不用占位符配置中的参数，用url中的参数
			if (request.getParameterValues("fileterField") != null && request.getParameterValues("filterValue") != null) {
				filed = request.getParameterValues("fileterField");
				value = request.getParameterValues("filterValue");
			}
		} else if (config.getFilterCreator()) { // 如果用户选择根据创建者过滤，则....
			filed[0] = "creator";
			Node user = this.getCurrentUser(request, response);
			if (user != null) {
				value[0] = user.getID();
			} else {
				value[0] = "";
			}
		} else {
			filed[0] = config.getFilterField(); // 从占位符配置传进来的过滤参数
			value[0] = config.getFilterValue(); // 从占位符配置传进来的过滤参数
		}

		request.setAttribute("preferences", request.getPreferences());
		// 计算分页
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null)
			pageNo = "0";
		Pager pager = new Pager();
		pager.setPageNo(Integer.parseInt(pageNo));
		pager.setPageSize(config.getSize());

		// 获取内容列表
		NodeDefinition def = lookUpDefinition(request);
		RangeIterator contents = this.getContents(request, response, def, config.getFilterPatterns(), config
				.getOrderField(), config.getOrderValue(), config.isGlobal(), config.isRecursive(), Query.SQL);

		String needAuth = request.getPreferences().getValue("needAuth", "");

		if (needAuth != null && needAuth.equals("true")) { // 判断内容的浏览是否增加权限限制
			ArrayList arr = new ArrayList(); // 存放新闻,有权限的新闻排在前边
			ArrayList noLinkArr = new ArrayList(); // 存放不带链接的新闻
			boolean hasPermition = false;
			Node user = this.getCurrentUser(request, response);
			if (user != null && !request.getUserPrincipal().getName().equals("1000000000000")) { // 如果不是系统管理员，根据其所在机构判断权限
			} else if (user != null) {
				hasPermition = true; // 如果是系统管理员，对所有的内容都有权限
			}
			while (contents.hasNext()) {
				Node content = (Node) contents.next();
				if (user != null) {
//					hasPermition = this.getPassportProvider().checkPermission(org, nodePermission); // 判断机构是否有读取改新闻的权限
				}
				if (hasPermition) {
					arr.add(content);
				} else {
					noLinkArr.add(content);
				}
			}
			int linkSize = arr.size(); // 有权限查看的新闻的个数
			linkSize = linkSize - pager.getPageNo() * config.getSize();
			for (int i = 0; i < noLinkArr.size(); i++) {
				arr.add(noLinkArr.get(i));
			}
			List list = null;
			int start = pager.getFromIndex() + config.getStartloc(); // 计算从第几个开始展示
			if (start > arr.size()) {
				list = arr.subList(arr.size(), arr.size());
			} else {
				list = arr.subList(start, arr.size()); // 截取要展示的内容，作用相当于skip
			}
			pager.setCount(contents.getSize());
			request.setAttribute("contents", RangeIteratorUtils.toSizedList(list.listIterator(), config.getSize()));
			request.setAttribute("linkNum", linkSize);
			request.setAttribute("pager", pager);
			return mapping.findForward("needAuth");
		}
		contents.skip(pager.getFromIndex() + config.getStartloc());
		if (contents.getSize() == 0)
			return mapping.findForward("no.content");
		else {
			pager.setCount(contents.getSize());
			request.setAttribute("contents", RangeIteratorUtils.toSizedList(contents, config.getSize()));
			request.setAttribute("pager", pager);

			String[] authGroups = request.getPreferences().getValues("authGroups", new String[0]);
			Node user = this.getCurrentUser(request, response);
			boolean isMember = false;/*
			if (user != null) {
				for (int i = 0; i < authGroups.length; i++) {
					if (authGroups[i].length() > 0) {
						Group group = this.getPassportProvider().getGroup(authGroups[i]);
						if (group.isMember(user)) {
							isMember = true;
							break;
						}
						if (org != null) {
							if (group.isMember(org)) {
								isMember = true;
								break;
							}
						}
					}
				}
			}*/
			// 没有设置权限组，则表示不对内容进行权限设置
			if (authGroups == null || authGroups.length < 1) {
				isMember = true;
			}
			if (authGroups.length == 1) {
				if (authGroups[0].length() <= 0) {
					isMember = true;
				}
			}

			// 对admin单独处理，admin不受权限设置的限制
			if (user != null && user.getID().equals("1000000000000")) {
				isMember = true;
			}
			request.setAttribute("isMember", "" + isMember);
			return mapping.findForward("success");
		}
	}
}
