package com.fulong.portal.core;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.utils.Enumerator;

/**
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
 * @author Lixf
 * @version 1.0
 */

public class LongconRenderRequest extends LongconPortletRequest implements RenderRequest {
	@SuppressWarnings("unchecked")
	private Map parameters;
	private boolean parsedParams;

	public LongconRenderRequest(PageContext context, PortletWindow window,
			PortletContainer container) {
		super(context, window, container);
		this.parsedParams = false;
	}

	/**
	 * 获取和这个占位符相关的所有参数，具体包括： 1. 本地参数，本地参数使用[portletid].[参数名称]的形式。
	 */

	@SuppressWarnings("unchecked")
	protected void parseParameters() {
		if (parsedParams) {
			return;
		}

		this.parameters = new HashMap();
		HttpServletRequest request = this.getHttpRequest();
		// boolean addAction = portletWindow.isFirer();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			if (NamespaceMapper.isGlobal(param))
				parameters.put(param, request.getParameterValues(param));
			else {
				String real = NamespaceMapper.decode(
						this.portletWindow.getId(), param);
				if (real != null) {
					parameters.put(real, request.getParameterValues(param));
				}
			}
		}
		this.parsedParams = true;
	}

	/**
	 * 获取缺省参数值
	 * 
	 * @param name
	 *            String
	 * @return String
	 */
	public String getParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Parameter name == null");
		}
		String[] values = this.getParameterValues(name);
		if (values == null) {
			return null;
		}
		if (values.length == 0) {
			return null;
		}
		return values[0];
	}

	/**
	 * 获取和这个占位符相关的所有参数，具体包括： 1. 全局性的参数，不带任何前缀。 2.
	 * 本地参数，本地参数使用[portletid].[参数名称]的形式。
	 * 
	 * @return Enumeration
	 * @todo ：如果该占位符不是当前活动占位符，就优先使用本地参数，否则 优先使用全局参数。
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		this.parseParameters();
		return new Enumerator(this.parameters.keySet());
	}

	/**
	 * 获取参数值。首先从全局参数中获取，如果没有，则从本地参数中获取。
	 * 
	 * @param name
	 *            String
	 * @return String[]
	 * @todo ：如果该占位符不是当前活动占位符，就优先使用本地参数，否则 优先使用全局参数。
	 */
	public String[] getParameterValues(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Parameter name == null");
		}
		parseParameters();
		Object value = parameters.get(name);
		if (value == null)
			return ((String[]) null);
		else if (value instanceof String[])
			return ((String[]) value);
		else if (value instanceof String) {
			String values[] = new String[1];
			values[0] = (String) value;
			return (values);
		} else {
			String values[] = new String[1];
			values[0] = value.toString();
			return (values);
		}

	}

	/**
	 * 获取参数集和
	 * 
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map getParameterMap() {
		this.parseParameters();
		return this.parameters;
	}

	// additional methods
	// -------------------------------------------------------------------------

}
