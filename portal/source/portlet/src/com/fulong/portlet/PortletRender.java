package com.fulong.portlet;

import java.sql.SQLException;

import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.Channel;


/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public abstract class PortletRender extends PorletController {
	public static final String NODONE = "noNode";
	 public static final String INCLUDE_SERVLET_PATH_ATTR =
	        "javax.servlet.include.servlet_path";
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
	public abstract ActionForward render(ActionMapping mapping,
			ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			PortletRequest prequest, PortletResponse presponse)
			throws Exception {
		RenderRequest request = (RenderRequest) prequest;
		RenderResponse response = (RenderResponse) presponse;

		// PortletMode mode = (PortletMode) request.getPortletMode();
		PortletPreferences preference = request.getPreferences();

		request.setAttribute(Constants.KEY_PREFERENCES, preference);
		request.setAttribute(Constants.KEY_ACTION_URL, response
				.createActionURL());
		request.setAttribute(Constants.KEY_PORTLET_CONFIG, this.portlet
				.getPortletConfig());
		try {
			return render(mapping, form, request, response);
		} finally {
			request.removeAttribute(Constants.KEY_PREFERENCES);
			request.removeAttribute(Constants.KEY_ACTION_URL);
			request.removeAttribute(Constants.KEY_PORTLET_CONFIG);
		}

	}

	/**
	 * getServletContext
	 * 
	 * @return Object
	 */
	protected PortletContext getPortletContext(RenderRequest request) {
		return request.getPortletSession().getPortletContext();
	}

	protected String lookup(RenderRequest request, String name) {
		if (name == null) {
			throw new IllegalArgumentException("The name should not be null.");
		}
		String value = request.getParameter(name);
		if (value == null) {
			return request.getPreferences().getValue(name, null);
		} else {
			return value;
		}
	}
	
	protected String antiFilter(String str) {
		return str.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&#39;", "'").replaceAll("&lt;", "<").replaceAll(
						"&gt;", ">");
	}

	protected String[] lookupValues(RenderRequest request, String name) {
		if (name == null) {
			throw new IllegalArgumentException("The name should not be null.");
		}
		String[] value = request.getParameterValues(name);
		if (value == null) {
			return request.getPreferences().getValues(name, null);
		} else {
			return value;
		}
	}

	protected Node lookupNode(String prefName, String prefType,
			RenderRequest request, RenderResponse response) throws Exception {

		String id = request.getPreferences().getValue(prefName, null);
		String contentType = request.getPreferences().getValue(prefType,
				"default");
		Node node = null;
		// 获取内容对象.如果定义了内容库,则从preferences中获取当前内容，否则从request中获取
		if (id != null) {
			node = this.getRepository().getNode(id);
			return node;
		}
		if (contentType.equals("user"))
			return (Node) request.getUserPrincipal();
		if (contentType.equals("site")){
			if(this.getCurrentSite(request, response)==null){
				return null;
			}
			return (Node) this.getCurrentSite(request, response).getOwner();
		}
		if (contentType.equals("parameter"))
			return (Node) request.getAttribute(Constants.REQUEST_PARAMETER);

		node = (Node) request.getAttribute(Constants.REQUEST_CONTENT);
		if (node == null) {
			node = this.getRepository().getNode(
					request.getParameter("contentId"));
			// 保存到页面的request而不是占位符的request中
			this.getServletRequest(request).setAttribute(
					Constants.REQUEST_CONTENT, node);
		}
		return node;
	}

	protected Node lookupNode(RenderRequest request, RenderResponse response)
			throws Exception {
		return this.lookupNode("content-id", "contentType", request, response);
	}

	protected NodeDefinition lookUpDefinition(RenderRequest request) {
		// PortletConfig config = new PortletConfig(request.getPreferences());
		PortletPreferences preferences = request.getPreferences();
		NodeDefinition definition = null;
		if (preferences.getValue("category", "") != null
				&& !preferences.getValue("category", "").equals("")) {
			definition = this.getRepository().getDefinitionManager()
					.getDefinition(preferences.getValue("category", ""));
		}
		if ((definition == null) && request.getParameter("definition") != null) {
			definition = this.getRepository().getDefinitionManager()
					.getDefinition(request.getParameter("definition"));
		}
		if (definition == null) {
			Channel channel = this.getCurrentChannel(request, null);
			if (channel != null) {
				// definition = channel.getBindingNode();
			}
		}
		if (definition == null) {
			definition = this.getRepository().getDefinitionManager()
					.getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
		}
		return definition;
	}
	
	protected String getClipPath(RenderRequest request, RenderResponse response,String suf) {
		String path = (String) request.getAttribute(INCLUDE_SERVLET_PATH_ATTR);
		if(path==null){
			path = this.getCurrentChannel(request, response).getContextPath();
		}
		int index = path.lastIndexOf("/");
		String clipPath = path.substring(0,index+1)+"_"+path.substring(index+1);
        clipPath += "/"+ request.getAttribute("javax.portlet.id");
        if(suf!=null&&suf.length()!=0){
        	 clipPath += "."+suf;
        }
        clipPath += ".jspf";
        return clipPath;
	}
	
	protected String prepareDomain(RenderRequest request, RenderResponse response) throws Exception{
		PortletPreferences preferences = request.getPreferences();
		String siteType = preferences.getValue("siteType", "default");
		String domain = "";
		if("default".equals(siteType)){
			Node node = lookupNode(request, response);
			Node site = this.getSite(node);
			if(site!=null){
				domain = site.getProperty("domain").getString();
			}
		}else if("site".equals(siteType)){
			domain = preferences.getValue("specifySite", "");
		}else if("user".equals(siteType)){
			Node user = this.getCurrentUser(request, response);
			if(user!=null){
				Node site = this.getOwnerSite(user);
				if(site!=null){
					domain = site.getProperty("domain").getValue().getString();
				}
			}
		}else if("custom".equals(siteType)){
			domain = preferences.getValue("customValue", "");
		}
		if(domain.length()!=0&&!domain.startsWith("http:")){
			domain = "http://"+domain;
		}
		return domain;
	}
	
	private Node getOwnerSite(Node user) throws SQLException{
		NodeDefinition siteScheme = this.getRepository().getDefinitionManager().getDefinition("site-scheme");
		Query query = this.getRepository().getQueryManager().createQuery(siteScheme,Query.SQL);
		query.filterByParent(user, false);
		NodeIterator<Node> it = query.nodes();
		if(it.hasNext()){
			return it.next();
		}
		return null;
	}
	
	/**
	 * 获取节点所在网站
	 * @param node
	 * @return
	 * @throws SQLException
	 */
	private Node getSite(Node node) throws SQLException{
		Node parent = node;
		while(!parent.getParent().getID().equals("root")){
			parent = parent.getParent();
		}
		return getOwnerSite(parent);
	}
	    
}
