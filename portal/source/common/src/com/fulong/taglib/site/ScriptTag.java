package com.fulong.taglib.site;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.TagUtils;

import com.fulong.portal.core.Constants;
import com.fulong.portal.model.ParameterConfig;
import com.fulong.portal.model.PortletDefinition;
import com.fulong.portal.model.impl.PortletContainerImpl;
import com.fulong.taglib.SpringTagSupport;

/**
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2010
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author luobin
 * @date 2010-6-7
 * @version 1.0.1
 */
public class ScriptTag extends SpringTagSupport {
	private static final long serialVersionUID = 265777267587950034L;
	public static final String RENDERED_PORTLET_SCRIPTS = "rendered.portlet.scripts";
	public static final String[] DEFAULT_PORTLET_SCRIPTS = {
			"/ide/common/script/common.js", "/ide/common/script/ajax.js",
			"/ide/common/script/dateFormat.js", "/ide/cms/classes/cmsdialog.js" };

	public ScriptTag() {
	}

	private String portlets;

	public void setPortlets(String portlets) {
		this.portlets = portlets;
	}
	
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
		String mode = this.getPortletMode(pageContext.getRequest());
		if(!Constants.PAGE_MODE_FINAL.equals(mode)){
			return EVAL_PAGE;
		}
		if (portlets != null) {
			ServletRequest request = getOriginalRequest(pageContext.getRequest());
			Set<String> renderedScripts = (Set<String>) request.getAttribute(RENDERED_PORTLET_SCRIPTS);
			if (renderedScripts == null) {
				renderedScripts = new HashSet<String>();
				request.setAttribute(RENDERED_PORTLET_SCRIPTS, renderedScripts);
			}

			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < DEFAULT_PORTLET_SCRIPTS.length; i++) {
				if (!renderedScripts.contains(DEFAULT_PORTLET_SCRIPTS[i])) {
					renderedScripts.add(DEFAULT_PORTLET_SCRIPTS[i]);
					buffer.append("<script type=\"text/javascript\" src=\""	+ DEFAULT_PORTLET_SCRIPTS[i] + "\"></script>\n");
				}
			}

			String[] portletTypes = portlets.split(",");
			for (int i = 0; i < portletTypes.length; i++) {
				if ("constant".equals(portletTypes[i])) {
					if (!renderedScripts.contains("/components/portlet/validator/constant/script.js.jsp")) {
						renderedScripts.add("/components/portlet/validator/constant/script.js.jsp");
						buffer.append("<script type=\"text/javascript\" src=\"/components/portlet/validator/constant/script.js.jsp\"></script>\n");
					}
					continue;
				}

				String scriptPath = getScriptPath(pageContext, portletTypes[i]);
				if (scriptPath != null && !renderedScripts.contains(scriptPath)) {
					renderedScripts.add(scriptPath);
					buffer.append("<script type=\"text/javascript\" src=\"" + scriptPath + "\"></script>\n");
				}
			}

			TagUtils.getInstance().write(pageContext, buffer.toString());
		}
		return EVAL_PAGE;
	}

	private ServletRequest getOriginalRequest(ServletRequest request) {
		while (request instanceof HttpServletRequestWrapper) {
			request = ((HttpServletRequestWrapper) request).getRequest();
		}
		return request;
	}

	private String getScriptPath(PageContext pageContext, String portletName) {
		PortletContainerImpl container = (PortletContainerImpl) pageContext.getServletContext().getAttribute(Constants.PORTLET_CONTAINER);
		PortletDefinition portletDefinition = container.getPortletDefinition(portletName);
		String scriptPath = null;
		if (portletDefinition != null) {
			ParameterConfig config = portletDefinition.getInitParameterConfig("script-file");
			if (config != null) {
				scriptPath = config.getValue();
			}
		}
		return scriptPath;
	}
	
	 /**
     * 获取当前页面模式，缺省模式为final；
     * @return PortletMode
     */
    public String getPortletMode(ServletRequest request) {
    	String mode = request.getParameter(Constants.REQUEST_PAGE_MODE);
        if (mode == null)
        	  mode = request.getParameter(Constants.REQUEST_PORTLET_MODE);
        if (mode == null)
            return Constants.PAGE_MODE_FINAL;
        else
            return mode;
    }
	
}
