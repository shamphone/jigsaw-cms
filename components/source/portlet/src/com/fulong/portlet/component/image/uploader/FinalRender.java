package com.fulong.portlet.component.image.uploader;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.Node;
import com.fulong.portlet.component.PortletConfig;
import javax.portlet.PortletPreferences;
import com.fulong.portlet.PortletRender;

/**
 * 图片上载表单域占位符
 * 
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lichengzhao
 * @version 3.1
 */
public class FinalRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		if (node != null) {
			String propertyId = config.getPropertyId();
			if (node.getProperty(propertyId) != null && node.getProperty(propertyId).getString()!=null && node.getProperty(propertyId).getString().length()>0){ 
				String domain = this.prepareDomain(request, response);
				request.setAttribute("pathValue", domain+node.getProperty(propertyId).getString());
			}
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
