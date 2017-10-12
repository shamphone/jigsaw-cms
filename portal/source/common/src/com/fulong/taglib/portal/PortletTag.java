package com.fulong.taglib.portal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.fulong.portal.core.Constants;
import com.fulong.portal.core.LongconActionRequest;
import com.fulong.portal.core.LongconActionResponse;
import com.fulong.portal.core.LongconPortletURL;
import com.fulong.portal.core.LongconRenderRequest;
import com.fulong.portal.core.LongconRenderResponse;
import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.model.Preference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 *门户标签负责渲染占位符，每一个占位符实例对应一个门户标签。他实现如下功能： 1．
 * 如果是Action状态，当前占位符是相应Action的占位符，则调用占位符processAction方法作处理
 * 。否则不做任何操作。在Action状态时，输出流被关闭，不能向用户请求Response通道中输入任何字符。 2．
 * 如果是Render状态，而且当前占位符窗口模式处于MAXMIZE状态，则调用当前占位符的render方法进行渲染，否则不处理，即关闭输出流通道。 3．
 * 如果是Render状态，而且没有窗口处于最大化模式，如果当前占位符是最小化状态，则关闭输出流通道，否则正常渲染。
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
 * @author 李雄锋
 * @version 1.0
 */
public class PortletTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2378103913889509827L;
	private String type;
	private PortletWindow portletWindow;
	private PortletContainer container;
	public static final String PORTLET_TAG = "com.fulong.portal.portlet";
	private static final Log log = LogFactory.getLog(PortletTag.class);
	private boolean isFirer;
	private boolean isAction;
	private Object reservedMessages;
	private long timer;
	private ServletContext portalContext;

	public PortletTag() {
		super();
		type = null;
		portletWindow = null;
		id = null;
		isFirer = false;
		isAction = false;
	}

	public int doStartTag() throws JspException {
		if (log.isDebugEnabled()) {
			timer = System.currentTimeMillis();
		}
		this.portalContext = pageContext.getServletContext().getContext(
				"/portal");
		isFirer = false;
		isAction = false;
		this.createPortletWindow();
		if (portletWindow == null) {
			log.error("Unable to find portlet for type :" + type);
			return SKIP_BODY;
		}
		// save message resource to request for struts tags.
		this.processMessageResources();

		this.preProcessPreferences();

		// 如果当前是action请求，而且请求的不是当前占位符，则不再继续处理。
		String firer = pageContext.getRequest().getParameter(
				Constants.REQUESTS_ACTION_FIRER);
		String firerType = pageContext.getRequest().getParameter(
				Constants.REQUESTS_ACTION_FIRER_TYPE);
		if ((firer != null) || (firerType != null)) {
			this.pageContext.getResponse().resetBuffer();
			isAction = true;
			if (this.pageContext.getResponse().isCommitted()) {
				throw new JspException("Error: response is committed by "
						+ type + ".");
			}
			if (id.equals(firer) || type.equals(firerType)) {
				isFirer = true;
				return EVAL_BODY_INCLUDE;
			} else {
				return SKIP_BODY;
			}
		}
		// 如果有一个窗口处于最大化状态,而且不是当前窗口,则不再继续处理.
		/*
		 * String windowOwner = pageContext.getRequest().getParameter(Constants.
		 * REQUEST_WINDOW_OWNER); if ((windowOwner != null) &&
		 * (!windowOwner.equals(this.id))) return this.SKIP_BODY;
		 */

		return EVAL_BODY_INCLUDE;
	}

	/**
	 * 创建PortletWindow对象
	 */
	private void createPortletWindow() {
		this.container = (PortletContainer) this.pageContext
				.getServletContext().getAttribute(Constants.PORTLET_CONTAINER);
		portletWindow = container.createWindow(id, type);
		this.pageContext.getRequest().setAttribute(
				Constants.ATTRIBUTE_PORTLET_ID, id);

	}

	/**
	 * 提供给preference标签调用的用来添加Preferences的方法
	 * 
	 * @param preference
	 *            Preference
	 */
	public void addPreference(Preference preference) {
		this.portletWindow.getTemplatePreferenceSet().put(preference);
	}

	/**
	 * 准备接受preferences参数
	 */
	private void preProcessPreferences() {
		this.pageContext.setAttribute(PORTLET_TAG, this);
	}

	/**
	 * 接收完Preferences后的处理
	 */
	private void postProcessPreferences() throws JspException {
		this.pageContext.removeAttribute(PORTLET_TAG);
	}

	public int doEndTag() throws JspException {
		if (this.portletWindow == null) {
			return EVAL_PAGE;
		}
		this.postProcessPreferences();
		int result = EVAL_PAGE;

		if (this.isAction) {
			if (this.isFirer) {
				try {
					this.processAction();
				} catch (Exception ex) {
					log.error("Error in process action.", ex);
				}
				result = SKIP_PAGE;
			} else {
				result = EVAL_PAGE;
			}
		} else {
			try {
				result = this.render();
			} catch (Exception ex) {
				log.error("Error in render portlet [" + portletWindow.getType()
						+ "," + portletWindow.getId() + "]", ex);
			}
		}

		// do clean up;
		// restore messages;
		this.restoreMessageResources();
		this.pageContext.getRequest().setAttribute(Constants.ATTRIBUTE_PORTLET_ID, null);
		this.isAction = false;
		this.isFirer = false;
		return result;
	}

	protected HttpServletRequest getRequest() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		while (request instanceof HttpServletRequestWrapper) {
			request = (HttpServletRequest) ((HttpServletRequestWrapper) request)
					.getRequest();
		}
		return request;

	}

	protected HttpServletResponse getResponse() {
		HttpServletResponse response = (HttpServletResponse) this.pageContext
				.getResponse();
		while (response instanceof HttpServletResponseWrapper) {
			response = (HttpServletResponse) ((HttpServletResponseWrapper) response)
					.getResponse();
		}
		return response;

	}

	/**
	 * 处理渲染
	 * 
	 * @return int
	 * @throws JspException
	 */
	private int render() throws JspException, IOException, ServletException,
			PortletException {
		int result = EVAL_PAGE;
		// 如果有一个窗口处于最大化状态,而且不是当前窗口,则不再继续处理.
		/*
		 * String windowOwner = pageContext.getRequest().getParameter(Constants.
		 * REQUEST_WINDOW_OWNER); if ((windowOwner != null) &&
		 * (!windowOwner.equals(this.id))) return this.EVAL_PAGE; boolean opened
		 * = false;
		 */

		ServletRequest servletRequest = this.getRequest();

		RenderRequest renderRequest = this.prepareRenderRequest();
		RenderResponse renderResponse = this.prepareRenderResponse();
		if (log.isDebugEnabled())
			log.debug("Begin rendering [" + portletWindow.getType() + ","
					+ portletWindow.getId() + ","
					+ renderRequest.getPortletMode() + "]");

		servletRequest.setAttribute(Constants.PORTLET_RESPONSE, renderResponse);
		servletRequest.setAttribute(Constants.PORTLET_REQUEST, renderRequest);

		servletRequest.setAttribute(Constants.PORTLET_CONFIG,
				this.portletWindow.getPortletConfig());
		pageContext.getOut().flush();
		
		String headerPath = "/"
				+ renderRequest.getPortletMode().toString() + ".header.jsp";
		RequestDispatcher dispatcher = this.portalContext
				.getRequestDispatcher(headerPath);
		dispatcher.include(pageContext.getRequest(), pageContext.getResponse());
		pageContext.getOut().flush();
		try {
			this.portletWindow.getPortlet().render(renderRequest,
					renderResponse);
		} catch (Exception ex) {
			String errPath = "/"
					+ renderRequest.getPortletMode().toString() + ".err.jsp";
			dispatcher = this.portalContext.getRequestDispatcher(errPath);
			dispatcher.include(pageContext.getRequest(), pageContext
					.getResponse());
			log.error("Error in render portlet [" + portletWindow.getType()
					+ "," + portletWindow.getId() + "]", ex);
		}
		pageContext.getOut().flush();
		String footerPath = "/"
				+ renderRequest.getPortletMode().toString() + ".footer.jsp";
		dispatcher = this.portalContext.getRequestDispatcher(footerPath);
		dispatcher.include(pageContext.getRequest(), pageContext.getResponse());
		pageContext.getOut().flush();
		servletRequest.removeAttribute(Constants.PORTLET_RESPONSE);
		servletRequest.removeAttribute(Constants.PORTLET_REQUEST);
		servletRequest.removeAttribute(Constants.PORTLET_CONFIG);
		if (log.isDebugEnabled()) {
			log.debug("[" + (System.currentTimeMillis() - this.timer)
					+ "] Finished rendering [" + portletWindow.getType() + ","
					+ portletWindow.getId() + ","
					+ renderRequest.getPortletMode() + "]");
		}
		return result;
	}

	/**
	 * 
	 * @return RenderRequest
	 */
	private RenderRequest prepareRenderRequest() {
		RenderRequest renderRequest = new LongconRenderRequest(
				this.pageContext, this.portletWindow, container);
		// 如果当前占位符是被嵌套在某一个页面中，则保存这个嵌套地址
		renderRequest
				.setAttribute(
						Constants.INCLUDE_PORTLET_PATH_ATTRIBUTE,
						renderRequest
								.getAttribute(Constants.INCLUDE_SERVLET_PATH_ATTRIBUTE));
		return renderRequest;
	}

	/**
	 * 
	 * @return RenderRequest
	 */
	private RenderResponse prepareRenderResponse() {
		RenderResponse renderResponse = new LongconRenderResponse(
				this.pageContext, this.portletWindow, container);
		return renderResponse;

	}

	/**
	 * 处理action请求
	 * 
	 * @return int
	 * @throws PortletException
	 * @throws IOException
	 */
	private int processAction() throws PortletException, IOException,
			ServletException {
		HttpServletResponse response = this.getResponse();
		LongconActionRequest actionReq = new LongconActionRequest(
				this.pageContext, this.portletWindow, this.container);
		LongconActionResponse actionResp = new LongconActionResponse(
				this.pageContext, this.portletWindow, this.container);
		log.debug("Begin process action [" + portletWindow.getType() + ","
				+ portletWindow.getId() + "]");
		portletWindow.getPortlet().processAction(actionReq, actionResp);
		String redirectLocation = "";

		if (actionResp.isRedirected()) {
			redirectLocation = actionResp.getRedirectLocation();
			response.sendRedirect(response.encodeRedirectURL(redirectLocation));
		} else if (actionResp.getPortletMode() != null) {
			actionResp.setPortletMode(Constants.PORTLET_MODE_VIEW);
			LongconPortletURL redirectURL = new LongconPortletURL(
					this.pageContext, portletWindow, false);
			redirectURL.setParameters(actionResp.getRenderParameterMap());
			if (actionResp.getPortletMode() != null) {
				redirectURL.setPortletMode(actionResp.getPortletMode());
			}
			redirectLocation = redirectURL.toString();
			response.sendRedirect(response.encodeRedirectURL(redirectLocation));
		}
		if (log.isDebugEnabled())
			log.debug("Finish call action on[" + portletWindow.getType() + ","
					+ portletWindow.getId() + "], redirect to "
					+ redirectLocation);
		return SKIP_PAGE;
	}

	public void release() {
		super.release();
		id = null;
		portletWindow = null;
		type = null;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 提供和struts兼容的message resource处理。
	 */
	private void processMessageResources() {
		if (this.portletWindow.getDefinition().getMessageResources() != null) {
			this.reservedMessages = this.pageContext.getRequest().getAttribute(
					Globals.MESSAGES_KEY);
			this.pageContext.getRequest().setAttribute(Globals.MESSAGES_KEY,
					this.portletWindow.getDefinition().getMessageResources());
		}
	}

	/**
	 * 恢复message resources;
	 */
	private void restoreMessageResources() {
		this.pageContext.getRequest().setAttribute(Globals.MESSAGES_KEY,
				this.reservedMessages);
	}
}
