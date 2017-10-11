package com.fulong.portlet.field.download.multiple;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 * 下载列表内容域占位符
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
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		List<String> paths = null;
		if (node.getProperty(config.getField()) != null) {
			String domain = this.prepareDomain(request, response);
			Value[] values = node.getProperty(config.getField()).getValues();
			switch (values.length) {
				case 0:
					break;
				default:
				boolean cacheImage = "true".equals(preferences.getValue(("cacheImage"),"false")); 
				String pathContext = null;
				if(cacheImage){
					pathContext = domain+"/cache";
				}else{
					pathContext = domain;
				}
				paths = new ArrayList<String>(values.length);
				for (int i = 0; i < values.length; i++) {
					if (values[i] == null || values[i].getString() == null || values[i].getString().trim().length() == 0)
						paths.add("");
					else
						paths.add(pathContext + values[i].getString());
				}
			}
		}
		if (paths != null)
			request.setAttribute("paths", paths);
		String text = preferences.getValue("text", null);
		if (text != null)
			preferences.setValue("text", this.antiFilter(text));
		// 渲染内容域。如果preferences中定义了field和format，则使用这些参数，否则使用expression中
		// 定义的表达式来计算最终渲染的数值。
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
