package com.fulong.portal.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.utils.ParametersCipher;
import javax.servlet.jsp.PageContext;
import javax.portlet.PortletRequest;

/**
 * 门户URL的构造器。 系统使用QueryString来保存portlet的状态。在QueryString 中，以pt.开始的表示占位符局部变量，
 * 以portlet开始的为系统预定义的全局变量。 各参数的意义： 1. pt.[占位符].portlet.mode
 * :占位符模式，值为PortletMode的枚举值 2. pt.[占位符].portlet.state: 占位符的Window
 * State，值为WindowState的枚举值 3. pt.[占位符].[参数名称]:占位符局部参数 4. portlet.action：当前触发
 * ActionRender的占位符的ID。 5. jportal:当前状态的MD5串
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
public class LongconPortletURL implements PortletURL {

	protected Map<String, Object> parameters;
	private PortletWindow window;
	protected boolean isAction;
	protected boolean secure;
	protected PageContext context;

	@SuppressWarnings("unchecked")
	public LongconPortletURL(PageContext context, PortletWindow window, boolean isAction) {
		this.window = (PortletWindow) window;
		this.isAction = isAction;
		this.context = context;
		this.parameters = new HashMap<String, Object>(context.getRequest().getParameterMap());
		this.removeParameters(window.getId());
		this.removeSystemParameters();
		if (isAction)
			this.parameters.put(Constants.REQUESTS_ACTION_FIRER, window.getId());
		this.parameters.put(Constants.REQUEST_WINDOW_OWNER, window.getId());
		this.parameters.put(Constants.REQUEST_PORTLET_TYPE, window.getType());
	}

	/**
	 * 删除和这个占位符相关的所有参数.
	 * 
	 * @param portletId
	 *            String
	 */
	private void removeParameters(String portletId) {
		for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (NamespaceMapper.decode(window.getId(), key) != null)
				iter.remove();
		}
	}

	private void removeSystemParameters() {
		for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (NamespaceMapper.isReserved(key))
				iter.remove();
		}

	}

	// javax.window.PortletURL
	// -------------------------------------------------------------------
	/**
	 * 设置窗口样式(目前默认normal),指定窗口的所有者
	 * 
	 * @param windowState
	 *            WindowState
	 * @throws javax.portlet.WindowStateException
	 */
	public void setWindowState(WindowState windowState)
			throws WindowStateException {
		String[] value = new String[] { windowState.toString() };
		this.parameters.put(NamespaceMapper.encode(window.getId(),
				Constants.REQUEST_WINDOW_STATE), value);
		if (windowState == WindowState.MAXIMIZED)
			this.parameters.put(Constants.REQUEST_WINDOW_OWNER,
					new String[] { window.getId() });
	}

	/**
	 * 设置窗口类型(view,edit,help)
	 * 
	 * @param <any>
	 *            view
	 */

	public void setPortletMode(PortletMode portletMode)
			throws PortletModeException {
		String[] value = new String[] { portletMode.toString() };
		this.parameters.put(NamespaceMapper.encode(window.getId(),
				Constants.REQUEST_WINDOW_STATE), value);
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 *            String
	 * @param value
	 *            String
	 */
	public void setParameter(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null");
		}
		if (value == null || value == "null") {
			parameters.remove(NamespaceMapper.encode(window.getId(), name));
			parameters.remove(name);
		} else {
			parameters.put(NamespaceMapper.encode(window.getId(), name),
					new String[] { value });
		}
	}

	/**
	 * 添加参数(多个参数)
	 * 
	 * @param name
	 *            String
	 * @param values
	 *            String[]
	 */
	public void setParameter(String name, String[] values) {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null");
		}
		if (values == null || values.length == 0) {
			parameters.remove(NamespaceMapper.encode(window.getId(), name));
			parameters.remove(name);
		} else {
			parameters
					.put(NamespaceMapper.encode(window.getId(), name), values);
		}
	}

	/**
	 * 添加参数(多个参数)
	 * 
	 * @todo fixme
	 * @param parameters
	 *            Map
	 */
	@SuppressWarnings("unchecked")
	public void setParameters(Map parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException("Parameters must not be null.");
		}
		for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (!(entry.getKey() instanceof String)) {
				throw new IllegalArgumentException(
						"Key must not be null and of type java.lang.String.");
			}
			if (!(entry.getValue() instanceof String[])) {
				throw new IllegalArgumentException(
						"Value must not be null and of type java.lang.String[].");
			}
		}
		this.parameters = new HashMap(parameters);
	}

	/**
	 * 设置请求的安全
	 * 
	 * @param secure
	 *            boolean
	 * @throws javax.portlet.PortletSecurityException
	 */
	public void setSecure(boolean secure) throws PortletSecurityException {
		this.secure = secure;
	}

	/**
	 * 生成URL地址
	 * 
	 * @return String
	 */
	private String calcURL() {
		HttpServletRequest request = (HttpServletRequest) this.context
				.getRequest();
		StringBuffer url = new StringBuffer(request.getContextPath());
		url.append(this.getServletPath());
		url.append("?");
		// url.append("?" + Constants.REQUEST_PORTAL_STATE + "=");
		// url.append(ParametersCipher.getInstance().encode(this.parameters));
		url.append(ParametersCipher.toQueryString(this.parameters));
		return url.toString();
	}

	/**
	 * 获取用户请求的url地址
	 * 
	 * @return String
	 */
	private String getServletPath() {
		HttpServletRequest request = (HttpServletRequest) this.context
				.getRequest();
		// 如果是Action，而且是被嵌套的页面,直接返回当前占位符所在的页面的路径。
		if (this.isAction) {
			PortletRequest renderRequest = (PortletRequest) request
					.getAttribute(Constants.PORTLET_REQUEST);
			String includedPath = (String) renderRequest
					.getAttribute(Constants.INCLUDE_PORTLET_PATH_ATTRIBUTE);
			if (includedPath != null)
				return includedPath;
			// return request.getServletPath();
		}
		// 如果不是Action，返回用户请求的原始页面路径。
		while (request instanceof HttpServletRequestWrapper) {
			request = (HttpServletRequest) ((HttpServletRequestWrapper) request)
					.getRequest();
		}
		return request.getServletPath();
	}

	public String toString() {

		return this.calcURL();

	}

}
