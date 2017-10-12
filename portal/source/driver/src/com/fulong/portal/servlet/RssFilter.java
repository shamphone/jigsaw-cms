package com.fulong.portal.servlet;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.xml.DomUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fulong.common.FileUtils;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-10-26	
 * @version 1.0.1
 */

public class RssFilter implements Filter{
	private static final Log log = LogFactory.getLog(RssFilter.class);
	private ServletContext context;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		String path = httpReq.getServletPath();
		if(!isRssPath(path)){
			chain.doFilter(request, response);
			return;
		}
		String file = context.getRealPath(path);
		RssParser parser = null;
		try {
			parser = new RssParser(new File(file),this.getRepository());
		} catch (SAXException e) {
			log.error(e.getMessage(),e);
		}
		if(parser!=null){
			String rss = null;
			try {
				rss = parser.toRss(httpReq,(HttpServletResponse)response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("text/xml;charset=utf-8");
			Writer writer = response.getWriter();
			writer.append(rss);
			writer.flush();
			writer.close();
		}
	}

	private boolean isRssPath(String path){
		return path.endsWith(".rss.bak")||path.endsWith(".rss");
	}

	@Override
	public void destroy() {

	}

	public BeanFactory getBeanFactory(){
		return WebApplicationContextUtils.getWebApplicationContext(context);
	}

	public Repository getRepository(){
		return (Repository) getBeanFactory().getBean("repository", Repository.class);
	}

	protected SiteFactory getSiteFactory() {
		return (SiteFactory)getBeanFactory().getBean("siteFactory",SiteFactory.class);
	}
	
	protected FilterParser getFilterParser(HttpServletRequest request, HttpServletResponse response){
		return new ServletFilterParser(request,response);
	}

	/**
	 * 获取当前请求的网站
	 * 
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return Site
	 * @throws Exception
	 */
	protected Site getCurrentSite(HttpServletRequest request) {
		Site site = (Site) request.getAttribute(Site.class.getName());
		if (site == null) {
			String siteId = request.getParameter("siteId");
			if (siteId == null) {
				siteId = request.getServerName();
			}
			site = this.getSiteFactory().getSite(siteId);
			if (site != null) {
				request.setAttribute(Site.class.getName(), site);
			}
		}
		return site;
	}
	
	class RssParser {
		private final Element root;
		private final Repository repository;

		public RssParser(File file,Repository repository) throws IOException, SAXException{
			root = FileUtils.readXML(file).getDocumentElement();
			this.repository = repository;
		}

		public String toRss(HttpServletRequest request,HttpServletResponse response) throws SQLException{
			StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
			buffer.append(this.renderStyle());
			buffer.append(this.renderChannel(getCurrentSite(request).getOwner(),request,response));
			return buffer.toString();
		}

		private StringBuffer renderStyle(){
			NodeList styles = root.getElementsByTagName("style");
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<styles.getLength();i++){
				String src = ((Element)styles.item(i)).getAttribute("src");
				buffer.append("<?xml-stylesheet type=\"text/css\" href=\""+src+"\"?>\n");
			}
			return buffer;
		}

		private StringBuffer renderChannel(Node siteOwmer,HttpServletRequest request,HttpServletResponse response) throws SQLException{
			StringBuffer buffer = new StringBuffer("<rss version=\"2.0\"><channel>\n");
			buffer.append(element2Rss(root,false));
			buffer.append(renderImages());
			buffer.append(renderItems(siteOwmer,request,response));
			buffer.append("</channel>\n</rss>");
			return buffer;
		}

		@SuppressWarnings("unchecked")
		private StringBuffer renderImages(){
			StringBuffer buffer = new StringBuffer();
			List<Element> list = DomUtils.getChildElementsByTagName(root, "image");
			for(int i=0;i<list.size();i++){
				buffer.append(element2Rss(list.get(i),true));
			}
			return buffer;
		}

		private StringBuffer renderItems(Node siteOwmer,HttpServletRequest request,HttpServletResponse response) throws SQLException{
			StringBuffer buffer = new StringBuffer();
			Element item = DomUtils.getChildElementByTagName(root, "item");
			Element metadata = DomUtils.getChildElementByTagName(root, "metadata");
			String definitionID = metadata.getAttribute("definitionID");
			String orderField = metadata.getAttribute("orderField");
			boolean recursive = "true".equalsIgnoreCase(metadata.getAttribute("recursive"));
			boolean global = "true".equalsIgnoreCase(metadata.getAttribute("global"));
			boolean asc = "asc".equalsIgnoreCase(metadata.getAttribute("orderStyle"));
			String itemNumStr = metadata.getAttribute("itemNum");
			int itemNum = 20;
			try {
				itemNum = Integer.parseInt(itemNumStr);
			} catch (NumberFormatException e) {
				
			}
			NodeDefinition definition = this.repository.getDefinitionManager().getDefinition(definitionID);
			if(item!=null&&definition!=null){
				Query query = this.repository.getQueryManager().createQuery(definition, Query.SQL);
				if(!global){
					query.filterByParent(siteOwmer, false);
				}
				processFilter(query, request, response);
				if(orderField==null||orderField.trim().length()==0){
					query.sortByOrdinal(asc);
				}else{
					query.sortByProperty(orderField, asc);
				}
				NodeIterator<Node> nodes =  query.nodes(recursive);
				while(nodes.hasNext()){
					if(nodes.getPosition()>=itemNum){
						break;
					}
					buffer.append(element2Rss(item,nodes.next(),true));
				}
			}
			return buffer;
		}

		private StringBuffer element2Rss(Element root,boolean includeRoot){
			return element2Rss(root, null,includeRoot);
		}

		private StringBuffer element2Rss(Element item,Node node,boolean includeRoot){
			StringBuffer buffer = new StringBuffer();
			if(includeRoot){
				buffer.append("<"+item.getNodeName()+">\n");
			}
			NamedNodeMap map = item.getAttributes();
			for(int i=0;i<map.getLength();i++){
				org.w3c.dom.Node element = map.item(i);
				if(element.getNodeType()==org.w3c.dom.Node.ATTRIBUTE_NODE){
					String value = element.getNodeValue();
					if(value!=null&&value.trim().length()!=0){
						if(node!=null){
							if(element.getNodeName().equals("link")){
								value += "?contentId="+node.getID();
							}else{
								Value v = node.getProperty(value.trim()).getValue();
								if(v!=null){
									value = v.getString();
								}else{
									value = "";
								}
							}
						}
						buffer.append("<"+element.getNodeName()+"><![CDATA["+value+"]]></"+element.getNodeName()+">\n");
					}
				}
			}
			if(includeRoot){
				buffer.append("</"+item.getNodeName()+">\n");
			}
			return buffer;
		}
		
		private void processFilter(Query query,HttpServletRequest request,HttpServletResponse response){
			NodeList filters = root.getElementsByTagName("filter");
			FilterParser filterParser = getFilterParser(request, response);
			for(int i=0;i<filters.getLength();i++){
				try {
					String pattern = ((Element)filters.item(i)).getAttribute("pattern");
					filterParser.parser(pattern);
					filterParser.addToQuery(query);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}

	}
	
}


