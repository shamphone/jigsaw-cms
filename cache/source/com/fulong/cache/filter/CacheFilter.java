package com.fulong.cache.filter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fulong.common.FileUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
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
 * @date 2010-8-10	
 * @version 1.0.1
 */
public class CacheFilter implements Filter{
	private static final Log log = LogFactory.getLog(CacheFilter.class);
	private ServletContext context;
	//private String[] patterns;
	//private PathMatcher resolver;

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
		//resolver = new AntPathMatcher();
		//patterns = config.getInitParameter("patterns").split(";");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		String resourcePath = httpReq.getServletPath();
		
		/*if(!isCachablePath(resourcePath)){
			chain.doFilter(request, response);
			return;
		}*/
		
		String cachePath = getCachePath(httpReq, resourcePath);
		File file = new File(context.getRealPath(cachePath));
		if(!file.exists()){
			
			InputStream in= getResourceAsStream(cachePath);
			
			if(in!=null){
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				
				try {
					FileUtils.write(file, in);
					log.info("cache repository resource: "+resourcePath+" into file :"+file.getPath());
				} catch (IOException e) {
					log.error("cache repository resource: "+resourcePath+" into file :"+file.getPath()+" failed.",e);
				} finally{
					in.close();
				}
			}
		}
		
		RequestDispatcher dispatcher =  httpReq.getRequestDispatcher(cachePath);
		response.setContentLength((int)file.length());
		dispatcher.include(request, response);
	}
	
	/**
	 * 判断是否缓存
	 * @param path
	 * @return
	 
	private boolean isCachablePath(String path){
		for(int i=0;i<patterns.length;i++){
			if(patterns[i].trim().length()>0&&resolver.match(patterns[i].trim(), path)){
				return true;
			}
		}
		return false;
	}*/

	public BeanFactory getBeanFactory(){
		return WebApplicationContextUtils.getWebApplicationContext(context);
	}

	public Repository getRepository(){
		return (Repository) getBeanFactory().getBean("repository", Repository.class);
	}

	protected SiteFactory getSiteFactory() {
		return (SiteFactory)getBeanFactory().getBean("siteFactory",SiteFactory.class);
	}

	/**
	 * 从内容库中取出流
	 * @param siteOwner		  网站所有者
	 * @param resourcePath	  内容库中存储的资源路径    比如 /resources/48461354135/456.png
	 * @return
	 * @throws IOException
	 */
	private InputStream getResourceAsStream(Node siteOwner,String resourcePath) throws IOException{
		resourcePath = resourcePath.substring(1);
		Node node = siteOwner.getNode(resourcePath);
		if(node!=null){
			return node.getProperty("resource-content").getStream();
		}
		return null;
	}
	
	/**
	 * 路径  /www.apple.cn/resources/48461354135/456.png  对应内容库中的节点路径   网站www.apple.cn的所有者/resources/48461354135/456.png
	 * @param servletPath		
	 * @return
	 * @throws IOException
	 */
	private InputStream getResourceAsStream(String path) throws IOException{
		path = StringUtils.cleanPath(path);
		int index = path.indexOf("/", 1);
		String siteDomain = path.substring(1, index);
		Site site = this.getSiteFactory().getSite(siteDomain);
		if(site!=null){
			Node siteOwner = site.getOwner();
			if(siteOwner!=null){
				String resourcePath = path.substring(index);
				return getResourceAsStream(siteOwner, resourcePath);
			}
		}
		return null;
	}
	
	/**
	 * 获取路径在cache应用中对应的缓存路径
	 * 比如 在域名 www.apple.cn下 路径/resources/48461354135/456.png 
	 * 对应cache文件下的路径  /www.apple.cn/resources/48461354135/456.png
	 * @param request
	 * @param resourcePath
	 * @return
	 */
	protected String getCachePath(HttpServletRequest request,String resourcePath){
		String path = "/"+request.getServerName();
		if(request.getServerPort()!=80){
			path += request.getServerPort();
		}
		path += resourcePath;
		return StringUtils.cleanPath(path);
	}

	@Override
	public void destroy() {

	}
}
