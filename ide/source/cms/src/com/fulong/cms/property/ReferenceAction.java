package com.fulong.cms.property;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.cms.CMSAjaxAction;
import com.fulong.common.AjaxAction;
import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
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
 * @author sunyuchao
 * @version 1.0
 */
public class ReferenceAction extends CMSAjaxAction {

	/**
	 * definitionPerform
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
	public Document renderXML(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("ID");
		NodeDefinition def = this.getRepository(request).getDefinitionManager()
				.getDefinition(id);
		String[] filterType = request.getParameterValues("exclude");
		String[] leftType = request.getParameterValues("include");
		ParameterSet set = new ParameterSet();
		// 优先处理脚本include数组中指定的属性类型，将符合include数组中指定类型的属性设置为enable
		if (leftType != null) {
			Iterator<PropertyDefinition> it1 = def.propertyDefinitions();
			while (it1.hasNext()) {
				PropertyDefinition pd = it1.next();
				String temp = null;
				temp = "" + pd.getType();
				boolean isEnable = false;
				for (int j = 0; j < leftType.length; j++) {
					if (leftType[j].equals(temp)) {
						isEnable = true;
					}
				}
				String[] strProperty = new String[3];
				strProperty[0] = pd.getName();
				strProperty[1] = "" + pd.getType();
				strProperty[2] = "" + isEnable;
				set.put(pd.getID(), strProperty);
			}
		}
		// 当include数组为空时，处理exclude中的属性，将脚本exclude数组中指定的属性类型设置为disable
		else {
			Iterator<PropertyDefinition> it2 = def.propertyDefinitions();
			while (it2.hasNext()) {
				PropertyDefinition pd = it2.next();
				String temp = null;
				temp = "" + pd.getType();
				boolean isEnable = true;
				for (int i = 0; filterType != null && i < filterType.length; i++) {
					if (filterType[i].equals(temp)) {
						isEnable = false;
					}
				}
				String[] strProperty = new String[3];
				strProperty[0] = pd.getName();
				strProperty[1] = "" + pd.getType();
				strProperty[2] = "" + isEnable;
				set.put(pd.getID(), strProperty);
			}
		}
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		return set.toDocument();

	}
}
