package com.fulong.portlet.field.pagination;

import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 * 分页内容域占位符
 * 
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
 * @author lichengzhao
 * @version 3.1
 */
public class FinalRender extends PortletRender {

	protected static final Pattern PATTERN_PAGE_BREAK_AFTER = Pattern.compile(
			"<div[^>]+style=['\"][^'\">]*page-break-after\\s*:\\s*always[^>]+>.*?</div>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL | Pattern.MULTILINE);
	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = lookupNode(request, response);
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);

		String[] pages = null;
		if (node.getProperty(config.getField()) != null && node.getProperty(config.getField()).getString() != null)
			pages = PATTERN_PAGE_BREAK_AFTER.split(node.getProperty(config.getField()).getString());
		else 
			pages = new String[] { "" };

		// compute this pageNum
		String pageNum = request.getParameter("pageNo");
		int num = 0;
		try {
			num = Integer.parseInt(pageNum);
		} catch (NumberFormatException ex) {
			//just ignore
		}
		if(num<0)
			num=0;
		// 若页号越界，则页号改为最后一页的页号
		if (num >= pages.length) {
			num = pages.length-1;
		}
		request.setAttribute("pageNo", num);
		request.setAttribute("pageCount", pages.length);
		request.setAttribute("content", pages[num]);

		// 渲染内容域。如果preferences中定义了field和format，则使用这些参数，否则使用expression中
		// 定义的表达式来计算最终渲染的数值。
		request.setAttribute("preferences", preferences);
		String around = preferences.getValue("around", "");
		if (around.equals("")) {
			request.setAttribute("leftAround", "");
			request.setAttribute("rightAround", "");
		} else {
			String[] arounds = around.split("p");
			if ("a".equals(arounds[0])) {
				request.setAttribute("leftAround", "<");
				request.setAttribute("rightAround", ">");
			} else {
				request.setAttribute("leftAround", arounds[0]);
				request.setAttribute("rightAround", arounds[1]);
			}
		}
		return mapping.findForward("success");
	}
}
