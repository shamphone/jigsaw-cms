package com.fulong.portlet.field.image;


import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 * 图片内容域占位符
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
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		
		String domain = this.prepareDomain(request, response);
		
		Property  picProp = node.getProperty(config.getField());
		if(picProp!=null){
			String[] paths = picProp.getArray();
			if(paths!=null&&paths.length>0){
				boolean cacheImage = "true".equals(preferences.getValue(("cacheImage"),"false")); 
				if(cacheImage){
					for(int i=0;i<paths.length;i++){
						paths[i] = domain+"/cache"+paths[i];
					}
				}else{
					for(int i=0;i<paths.length;i++){
						paths[i] = domain + paths[i];
					}
				}
				request.setAttribute("paths", paths);
			}
		}
		if(node.getProperty(preferences.getValue("altfield", ""))!=null){
			request.setAttribute("alt", node.getProperty(preferences.getValue("altfield", "")).getString());
		}else{
			if(!preferences.getValue("width" , "").equals("")&&!preferences.getValue("height" , "").equals("")){
				request.setAttribute("alt", preferences.getValue("alt" ,  preferences.getValue("width" , "")+"*"+preferences.getValue("height" , "")));
			}else{
				request.setAttribute("alt", preferences.getValue("alt" , ""));
			}
		}
			
		// 渲染内容域。如果preferences中定义了field和format，则使用这些参数，否则使用expression中
		// 定义的表达式来计算最终渲染的数值。
		request.setAttribute("oNode", node);
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
