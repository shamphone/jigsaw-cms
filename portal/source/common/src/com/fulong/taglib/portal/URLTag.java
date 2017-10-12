package com.fulong.taglib.portal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * 门户引擎URL相对地址格式为：
 * /portlet/[类型]/[页面地址]/主占位符/占位符1/占位符的Mode/占位符1的参数列表/占位符2/占位符2的Mode
 * /占位符2的参数列表?[参数1]=[参数1的值]&[参数2]=[参数2的值]... 其中: 类型: render或者action。
 * 页面地址：即包含占位符的jsp页面的地址，这是已经经过Base64编码的地址。 主占位符：即用来接收参数的占位符，如果没有值，则为空。
 * 占位符1：每个占位符的模式和请求参数将通过后续的字段来传递。 占位符Mode：占位符模式，缺省的为final
 * 占位符参数列表：占位符的request参数列表，已经经过Base64编码的参数列表。
 * <p>
 * Title: Longcon Portal
 * </p>
 * 
 * <p>
 * Description: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */

public class URLTag extends TagSupport {

	private static final long serialVersionUID = 5627915901485414793L;

	public static class TEI extends TagExtraInfo {
		public final static Hashtable<String, Object> definedWindowStates = getDefinedWindowStates();
		public final static Hashtable<String, Object> portletModes = getDefinedPortletModes();

		/**
		 * Provides a list of all static PortletMode available in the
		 * specifications by using introspection
		 * 
		 * @return Hashtable
		 */
		private static Hashtable<String, Object> getDefinedPortletModes() {
			Hashtable<String, Object> portletModes = new Hashtable<String, Object>();
			Field[] f = PortletMode.class.getDeclaredFields();

			for (int i = 0; i < f.length; i++) {
				if (f[i].getType().isAssignableFrom(
						javax.portlet.PortletMode.class)) {
					try {
						portletModes.put(f[i].get(f[i]).toString()
								.toUpperCase(), f[i].get(f[i]));
					} catch (IllegalAccessException e) {
					}
				}
			}

			return portletModes;
		}

		/**
		 * Provides a list of all static WindowsStates available in the
		 * specifications by using introspection
		 * 
		 * @return Hashtable
		 */
		private static Hashtable<String, Object> getDefinedWindowStates() {
			Hashtable<String, Object> definedWindowStates = new Hashtable<String, Object>();
			Field[] f = WindowState.class.getDeclaredFields();

			for (int i = 0; i < f.length; i++) {
				if (f[i].getType().isAssignableFrom(
						javax.portlet.WindowState.class)) {
					try {
						definedWindowStates.put(f[i].get(f[i]).toString()
								.toUpperCase(), f[i].get(f[i]));
					} catch (IllegalAccessException e) {

					}
				}
			}
			return definedWindowStates;
		}

		public VariableInfo[] getVariableInfo(TagData tagData) {
			VariableInfo vi[] = null;
			String var = tagData.getAttributeString("var");
			if (var != null) {
				vi = new VariableInfo[1];
				vi[0] = new VariableInfo(var, "java.lang.String", true,
						VariableInfo.AT_BEGIN);
			}
			return vi;
		}

	}

	protected String portletMode;
	protected String secure;
	protected Boolean secureBoolean;
	protected String windowState;
	protected PortletURL url;
	protected String var;

	/**
	 * Processes the <CODE>actionURL</CODE> or <CODE>renderURL</CODE> tag.
	 * 
	 * @return int
	 */
	public int doStartTag() throws JspException {
		return 0;
	}

	/**
	 * 
	 * @return int
	 */
	public int doEndTag() throws JspException {
		if (var == null) {
			try {
				JspWriter writer = pageContext.getOut();
				writer.print(url);
				writer.flush();
			} catch (IOException ioe) {
				throw new JspException(
						"actionURL/renderURL Tag Exception: cannot write to the output writer.");
			}
		} else {
			pageContext.setAttribute(var, url.toString(),
					PageContext.PAGE_SCOPE);
		}
		return EVAL_PAGE;
	}

	/**
	 * Returns the portletMode.
	 * 
	 * @return String
	 */
	public String getPortletMode() {
		return portletMode;
	}

	/**
	 * @return secure as String
	 */
	public String getSecure() {
		return secure;
	}

	/**
	 * @return secure as Boolean
	 */
	public boolean getSecureBoolean() {
		return this.secureBoolean.booleanValue();
	}

	/**
	 * Returns the windowState.
	 * 
	 * @return String
	 */
	public String getWindowState() {
		return windowState;
	}

	/**
	 * @return PortletURL
	 */
	public PortletURL getUrl() {
		return url;
	}

	/**
	 * Returns the var.
	 * 
	 * @return String
	 */
	public String getVar() {
		return var;
	}

	/**
	 * Sets the portletMode.
	 * 
	 * @param portletMode
	 *            The portletMode to set
	 */
	public void setPortletMode(String portletMode) {
		this.portletMode = portletMode;
	}

	/**
	 * Sets secure to boolean value of the string
	 * 
	 * @param secure
	 */
	public void setSecure(String secure) {
		this.secure = secure;
		this.secureBoolean = new Boolean(secure);
	}

	/**
	 * Sets the windowState.
	 * 
	 * @param windowState
	 *            The windowState to set
	 */
	public void setWindowState(String windowState) {
		this.windowState = windowState;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            The url to set
	 */
	public void setUrl(PortletURL url) {
		this.url = url;
	}

	/**
	 * Sets the var.
	 * 
	 * @param var
	 *            The var to set
	 */
	public void setVar(String var) {
		this.var = var;
	}

	public void addParameter(String name, String value) {
		this.url.setParameter(name, value);
	}

	public void release() {
		this.secure = null;
		this.var = null;
		this.secureBoolean = null;
		this.portletMode = null;
		this.windowState = null;
	}
}
