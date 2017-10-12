package com.fulong.portlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Channel;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
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
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public abstract class PortletAction extends PorletController {
	/**
	 * @deprecated 不再使用这个方法,用action(ActionMapping mapping,ActionForm
	 *             form,ActionRequest request, ActionResponse response) throws
	 *             Exception;
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @throws Exception
	 */
	public void execute(ActionRequest request, ActionResponse response) throws Exception {

	}

	/**
	 * 派生类实现这个方法
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward action(ActionMapping mapping, ActionForm form, ActionRequest request, ActionResponse response)
			throws Exception {
		this.execute(request, response);
		return null;
	}

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, PortletRequest request,
			PortletResponse response) throws Exception {
		ActionResponse actResp = (ActionResponse) response;
		ActionRequest actReq = (ActionRequest) request;
		ActionForward forward = action(mapping, form, actReq, actResp);
		// 为了处理修改占位符而做的特殊设置，在com.fulong.site.editor.EditPortletAction中使用
		this.getServletRequest(request).setAttribute("javax.portlet.preferences", request.getPreferences());
		return forward;
	}

	/**
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @param destMode
	 *            PortletMode
	 * @throws PortletException
	 */
	@SuppressWarnings("unchecked")
	protected void forward(ActionRequest request, ActionResponse response, PortletMode destMode)
			throws PortletException {
		for (Enumeration<String> names = request.getParameterNames(); names.hasMoreElements();) {
			String name = (String) names.nextElement();
			String[] values = request.getParameterValues(name);
			response.setRenderParameter(name, values);
		}
		response.setPortletMode(destMode);
	}

	/**
	 * 重定向到指定栏目
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @param channel
	 *            Channel
	 * @throws PortletException
	 */
	protected void redirect(ActionRequest request, ActionResponse response, Channel channel, String contentId)
			throws IOException {
		StringBuffer url = new StringBuffer(request.getContextPath());
		url.append("/sites/").append(channel.getSiteTemplate().getName()).append("/").append(channel.getName()).append(
				".jsp");
		if (contentId != null) {
			url.append("?contentId=" + contentId);
		}
		response.sendRedirect(url.toString());

	}

	/**
	 * 重定向到指定栏目
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @param channel
	 *            Channel
	 * @throws PortletException
	 */
	protected void redirect(ActionRequest request, ActionResponse response, Channel channel) throws IOException {
		this.redirect(request, response, channel, null);
	}

	/**
	 * 重定向到指定栏目
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @param channel
	 *            Channel
	 * @throws PortletException
	 */
	protected void redirect(ActionRequest request, ActionResponse response, String channelName, String contentId)
			throws Exception {
		Channel channel = this.getCurrentSiteTemplate(request, response).getChannel(channelName);
		if (channel == null) {
			channel = this.getCurrentChannel(request, response);
		}

		this.redirect(request, response, channel, contentId);
	}

	/**
	 * 重定向到指定栏目
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @param channel
	 *            Channel
	 * @throws PortletException
	 */
	protected void redirect(ActionRequest request, ActionResponse response, String channelName) throws Exception {
		Channel channel = this.getCurrentSiteTemplate(request, response).getChannel(channelName);
		if (channel == null) {
			channel = this.getCurrentChannel(request, response);
		}

		this.redirect(request, response, channel, null);
	}

	protected Node lookupNode(String prefName, String prefType, ActionRequest request, ActionResponse response)
			throws Exception {

		String id = request.getPreferences().getValue(prefName, null);
		String contentType = request.getPreferences().getValue(prefType, null);
		Node node = null;
		// 获取内容对象.如果定义了内容库,则从preferences中获取当前内容，否则从request中获取
		if (id != null) {
			node = this.getRepository().getNode(id);
		} else if (contentType != null && !contentType.equals("default")) {
			if (contentType.equals("user")) {
				node = (Node) request.getUserPrincipal();
			} else if (contentType.equals("site")) {
				node = (Node) this.getCurrentSite(request, response).getOwner();
			}
		} else {
			node = (Node) request.getAttribute(Constants.REQUEST_CONTENT);
			if (node == null) {
				node = this.getRepository().getNode(request.getParameter("contentId"));
			}
		}
		return node;
	}

	protected Node lookupNode(ActionRequest request, ActionResponse response) throws Exception {
		return this.lookupNode("content-id", "contentType", request, response);
	}

}
