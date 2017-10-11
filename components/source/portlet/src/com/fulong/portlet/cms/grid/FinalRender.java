package com.fulong.portlet.cms.grid;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.Pager;
import com.fulong.common.util.RangeIterator;
import com.fulong.common.util.RangeIteratorUtils;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.portlet.cms.ListContentPortletRender;

/**
 * 
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

		ContentGridConfig config = new ContentGridConfig(request.getPreferences());
		request.setAttribute("preferences", request.getPreferences());
		// 计算分页
		String pageNo = request.getParameter("pageNo");
		// 河北版本合并 ZhangMH 2008-7-28 start
		int row = Integer.parseInt(request.getPreferences().getValue("row", "5"));
		int table = Integer.parseInt(request.getPreferences().getValue("table", "1"));
		int pageSize = row * table;
		// 河北版本合并 ZhangMH 2008-7-28 end
		if (pageNo == null)
			pageNo = "0";
		Pager pager = new Pager();
		pager.setPageNo(Integer.parseInt(pageNo));
		pager.setPageSize(pageSize);
		// 获取内容列表
		// request.setAttribute("pageNo", pageNo);
		NodeDefinition def = lookUpDefinition(request);
		RangeIterator iterator = this.getContents(request, response, def, config.getFilterPatterns(), config
				.getOrderField(), config.getOrderValue(), config.isGlobal(), config.isRecursive(), Query.SQL);

		iterator.skip(pager.getFromIndex());
		if (iterator.getSize() == 0)
			return mapping.findForward("no.content");
		else {
			pager.setCount(iterator.getSize());
			request.setAttribute("contents", RangeIteratorUtils.toSizedList(iterator, config.getSize()));
			request.setAttribute("pager", pager);
			return mapping.findForward("success");

		}
	}
}
