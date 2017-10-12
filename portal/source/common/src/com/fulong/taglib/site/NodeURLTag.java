package com.fulong.taglib.site;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.taglib.SpringTagSupport;

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
 * @author lixf
 * @version 1.0
 */
public class NodeURLTag extends SpringTagSupport {

	private static final long serialVersionUID = -868499002569874641L;

	private String name;
	private String property;
	private String scope;
	private String siteName;
	private String siteProperty;
	private String templateName;
	private String templateProperty;
	private String type;

	public NodeURLTag() {
		this.type = "content";
		this.name = null;
		this.property = null;
		this.scope = null;
		this.siteName = null;
		this.siteProperty = null;

	}

	public void release() {
		super.release();
		this.type = "content";
		this.name = null;
		this.property = null;
		this.scope = null;
		this.siteName = null;
		this.siteProperty = null;
	}

	public int doEndTag() throws JspException {
		StringBuffer buffer = new StringBuffer("http://");
		Node node = (Node) TagUtils.getInstance().lookup(this.pageContext,
				name, property, scope);
		if (node == null) {
			return EVAL_PAGE;
		}
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Node site = null;
		/*
		 * if (type.equals("principal")) { site =
		 * this.getSiteFactory().getOwnSite(node.getParent()); if (site != null)
		 * { buffer.append(site.getDomain()); }
		 * TagUtils.getInstance().write(pageContext, buffer.toString()); return
		 * this.EVAL_PAGE; }
		 */
		if ((siteName != null) && (siteName.length() > 0)) {
			site = (Node) TagUtils.getInstance().lookup(this.pageContext,
					siteName, siteProperty, scope);
		} else {
			NodeDefinition siteDef = this.getRepository()
					.getDefinitionManager().getDefinition("site-scheme");
			if (siteDef == null) {
				return EVAL_PAGE;
			}
			Query query = this.getRepository().getQueryManager().createQuery(
					siteDef, Query.SQL);
			query.filterByProperty("domain", request.getServerName());
			NodeIterator<Node> sites = query.nodes();
			if (!sites.hasNext()) {
				return EVAL_PAGE;
			}
			site = sites.nextNode();
		}
		if (site == null) {
			return EVAL_PAGE;
		}
		buffer.append(site.getProperty("domain").getString());
		if (request.getServerPort() != 80) {
			buffer.append(":" + request.getServerPort());
		}
		buffer.append(request.getContextPath());

		SiteTemplate template = null;
		if ((templateName != null) && (templateName.length() > 0)) {
			template = (SiteTemplate) TagUtils.getInstance().lookup(
					this.pageContext, templateName, templateProperty, scope);
		} else {
			if (request.getContextPath() == null
					|| request.getContextPath().equals("")) {
				template = this.getSiteFactory().getTemplate(
						request.getRequestURI().split("/")[0]);
			} else {
				template = this.getSiteFactory().getTemplate(
						request.getRequestURI().split("/")[1]);
			}
		}

		if (template != null) {
			NodeDefinition def = node.getDefinition();
			if (def == null) {
				return EVAL_PAGE;
			}
			Iterator<Channel> channels = template.getChannels();
			if (!channels.hasNext()) {
				return EVAL_PAGE;
			}
			/*
			 * Channel channel = null; while(channels.hasNext()){ channel =
			 * channels.next(); channel.getBindingNode(); }
			 * 
			 * template.getRootFolder().ge Channel channel = null; channel =
			 * template.getChannel("index.jsp"); if (channel !=
			 * null&&channel.getType().equals("content")) {
			 * buffer.append("/sites/")
			 * .append(channel.getSiteTemplate().getName()) .append("/")
			 * .append(channel.getName()) .append(".jsp");
			 * buffer.append("?contentId=" + content.getID()); }
			 */
		}
		TagUtils.getInstance().write(pageContext, buffer.toString());
		return EVAL_PAGE;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setSiteProperty(String siteProperty) {
		this.siteProperty = siteProperty;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateProperty() {
		return templateProperty;
	}

	public void setTemplateProperty(String templateProperty) {
		this.templateProperty = templateProperty;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getProperty() {
		return property;
	}

	public String getScope() {
		return scope;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getSiteProperty() {
		return siteProperty;
	}

	public String getType() {
		if ((type == null) || type.equalsIgnoreCase("link")) {
			return "content";
		}
		return type;
	}

}
