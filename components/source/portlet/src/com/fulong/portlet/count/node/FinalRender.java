package com.fulong.portlet.count.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.Constants;
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
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		ContentListConfig config = new ContentListConfig(request.getPreferences());

		request.setAttribute("preferences", request.getPreferences());
		// 获取内容列表
		List<String> patterns = new ArrayList<String>();
		NodeDefinition def = lookUpDefinition(request);
		if (!request.getPreferences().getValue("filterByParamet", "on").equals("false")) {
			if(request.getAttribute(Constants.REQUEST_NODEDEFINITION) != null)
				def = (NodeDefinition) request.getAttribute(Constants.REQUEST_NODEDEFINITION);
			else if(request.getParameter("definition")!=null&&!request.getParameter("definition").equals("")){
				def = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
			}
			Iterator<PropertyDefinition> properties = def.propertyDefinitions();
			while (properties.hasNext()) {
				PropertyDefinition propertyDefinition = (PropertyDefinition) properties.next();
				if (request.getParameter(propertyDefinition.getID()) != null) {
					String pars[] = request.getParameterValues(propertyDefinition.getID());
					if (pars.length > 1) {
						if (pars[0].length() > 1) {
							patterns.add(propertyDefinition.getID() + " " + FilterParser.LESS + " " + pars[0]);
						}
						if (pars[1].length() > 1) {
							patterns.add(propertyDefinition.getID() + " " + FilterParser.MORE + " " + pars[1]);
						}
					} else if (pars.length == 1 && pars[0].length() > 1) {
						patterns.add(propertyDefinition.getID() + " " + FilterParser.EQUAL + " " + pars[0]);
					}
				}
			}
		}
		if (request.getParameter("keyword") != null && request.getParameter("keyword").length() > 0) {
			patterns.add("keyword equal " + request.getParameter("keyword"));
		}
		String[] pars1 = (String[]) patterns.toArray(new String[patterns.size()]);
		String[] pars2 = config.getFilterPatterns();
		String pars[] = new String[pars1.length + pars2.length];
		System.arraycopy(pars1, 0, pars, 0, pars1.length);
		System.arraycopy(pars2, 0, pars, pars1.length, pars2.length);
		long size = this.getContentsSize(request, response, def, pars, config.isGlobal(), config.isRecursive(), config
				.getLanguage());

		request.setAttribute("count", size);
		return mapping.findForward("success");

	}

}
