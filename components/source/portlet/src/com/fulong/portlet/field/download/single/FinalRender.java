package com.fulong.portlet.field.download.single;

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
 * 下载内容域占位符
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
		
		String echo = preferences.getValue("echo", "nodeEcho");
		String path="";
		if(echo.equals("nodeEcho")){  //用于编辑回显
			if (node.getProperty(config.getField()).getString() != null){
				path = node.getProperty(config.getField()).getString();
				boolean cacheImage = "true".equals(preferences.getValue(("cacheImage"),"false"));
				String domain = this.prepareDomain(request, response);
				if(cacheImage){
					path = domain + "/cache" + path;
				}else{
					path = domain + path;
				}
				request.setAttribute("pathValue",path);
			}
		}else{
			path = request.getParameter(preferences.getValue("field", ""));
			if(path!=null && path.length()>0){
				request.setAttribute("pathValue",path);
			}
		}
		String text = preferences.getValue("text", null);
		if (text != null)
			preferences.setValue("text", this.antiFilter(text));
		// 渲染内容域。如果preferences中定义了field和format，则使用这些参数，否则使用expression中
		// 定义的表达式来计算最终渲染的数值。
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
