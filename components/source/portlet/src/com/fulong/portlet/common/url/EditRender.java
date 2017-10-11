package com.fulong.portlet.common.url;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.PortletRender;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * 远程资源占位符编辑模式
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
 * @author Lixf
 * @version 1.0
 */
public class EditRender extends PortletRender {

	/**
	 * execute
	 * 
	 * @param config
	 *            PortletConfig
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return String
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		Iterator<?> iterator = Charset.availableCharsets().values().iterator();
		while (iterator.hasNext()) {
			Charset set = (Charset) iterator.next();
			list.add(new LabelValueBean(set.name(), set.displayName(new Locale("zh"))));
		}

		request.setAttribute("charsets", list);
		return mapping.findForward("success");
	}
}
