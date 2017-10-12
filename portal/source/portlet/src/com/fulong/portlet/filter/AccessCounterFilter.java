/**
 * 
 */
package com.fulong.portlet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.counter.AccessCounterRepository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class AccessCounterFilter extends BasePortletFilter{
	private String[] properties = new String[]{"counter","accessCount"};	
	private AccessCounterRepository counterRepository;
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {	
		//系统计数
		this.counterRepository.increase("system");
		//网站计数
		Site site = this.getCurrentSite(request, response);
		if(site!=null){
			this.counterRepository.increase(site.getDomain());
		}
		//栏目计数
		Channel channel = this.getCurrentChannel(request, response);
		if(channel!=null){
			this.counterRepository.increase(channel.getSiteTemplate().getName()+channel.getContextPath());
		}
		//内容计数
		String id = request.getParameter("contentId");
		if(id!=null){
			Node node = this.repository.getNode(id);
			if(node!=null){
				this.counterRepository.increase(node.getID());
				for(int i=0;i<properties.length;i++)
				if(node.getProperty(properties[i])!=null)
					node.setProperty(properties[i], counterRepository.getCount(node.getID()));				
			}			
		}
		chain.doFilter(request, response);
	}
	
	public void setProperties(String properties){
		this.properties=properties.trim().split("\\W");
	}
	
	public void setCounterRepository(AccessCounterRepository repository) {
		this.counterRepository = repository;
	}		
}
