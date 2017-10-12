/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.site;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;


/**
 * IndexServlet
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-5-26
 */
public class IndexServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6685609503067791255L;
	private static final Log log = LogFactory.getLog(IndexServlet.class);
	private Repository repository;
	private SiteFactory siteFactory;
	@Override
	public void init() throws ServletException {
		super.init();
		String jndiName = this.getInitParameter("jndi");
		if (jndiName == null)
			jndiName = "java:comp/env/coolink/root";
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(jndiName);
		try {
			bean.afterPropertiesSet();
		} catch (Exception e) {
			throw new UnavailableException(e.getMessage());
		}
		AbstractRefreshableConfigApplicationContext parent = (AbstractRefreshableConfigApplicationContext) bean.getObject();
		if(!parent.isActive())
			parent.refresh();
		this.repository= (Repository)parent.getBean("repository");
		this.siteFactory = (SiteFactory)parent.getBean("siteFactory");
		if(this.repository==null)
			throw new UnavailableException("Unable to get repository bean from config file.");
	
		log.info("root servlet startup.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String domain  = req.getServerName();
		Node site = null;
		int port = req.getServerPort();
		//if(port!=80)
			//domain = domain+":"+port;
		NodeDefinition siteDef = this.repository.getDefinitionManager().getDefinition("site-scheme");
		Query query = this.repository.getQueryManager().createQuery(siteDef,  Query.SQL);
		query.filterByProperty("domain", domain);
		NodeIterator<Node> sites = query.nodes();
		if(sites.getSize() == 1)
			site = sites.nextNode();
		//没找到域名对应的网站；
		if(site == null){
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		//转到对应的模板上去；
		String widthStr = req.getParameter("width");
		String heightStr = req.getParameter("height");
		if(widthStr!=null&&heightStr!=null){
			int width = Integer.parseInt(widthStr);
			int height = Integer.parseInt(heightStr);
			if(site!=null){
				SiteTemplate template = null;
				if(isPC(width, height)){
					template = this.getNavigateTemplateByResolution(site,SiteTemplate.DEFAULT_RESOLUTION);
					if(template==null){
						template = this.autoDetectTemplate(site, width, height);
					}
				}else{
					String resolution = height + "*" + width;
					template = this.getNavigateTemplateByResolution(site,resolution);
					if(template==null){
						template = this.autoDetectTemplate(site, width, height);
					}
				}
				if(template!=null){
					req.setAttribute(SiteTemplate.class.getName(), template);
					resp.sendRedirect("/" + template.getName()+"/index.jsp");
				}else{
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
		}
	}
	
    /**
     * 判断是电脑还是手机
     * @param width
     * @param height
     * @return
     */
    private boolean isPC(int width,int height){
    	if(width>=800&&height>=600){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 获取指定分辨率的导航模板
     * @param site
     * @param resolution
     * @return
     */
    private SiteTemplate getNavigateTemplateByResolution(Node site,String resolution){
    	String[] templatesNames = site.getProperty("navigateTemplates").getArray();
    	for(String templateName:templatesNames){
    		SiteTemplate template = siteFactory.getTemplate(templateName);
    		if(template!=null&&resolution.equals(template.getResolution())){
    			return template;
    		}
    	}
    	return null;
    }
    
    /**
     * 自动识别分辨率最接近的模板
     * @param site
     * @param width
     * @param height
     * @return
     */
    private SiteTemplate autoDetectTemplate(Node site,int width,int height){
    	String resolution  = null;
    	int diff = Integer.MAX_VALUE;
    	String[] templatesNames = site.getProperty("navigateTemplates").getArray();
    	for(String templateName:templatesNames){
    		SiteTemplate template = this.siteFactory.getTemplate(templateName);
    		if(template==null){
    			continue;
    		}
    		String tempResolution = template.getResolution();
    		int tempWidth = 0;
    		int tempHeight = 0;
    		if(SiteTemplate.DEFAULT_RESOLUTION.equals(tempResolution)){
    			tempWidth = 1024;
        		tempHeight = 768;
    		}else{
    			String[] strs = tempResolution.split("\\*");
    			if(strs.length>1){
    				tempWidth = Integer.parseInt(strs[0]);
        			tempHeight = Integer.parseInt(strs[1]);
    			}else{
    				continue;
    			}
    		}
			int tempDiff = (tempWidth-width)*(tempWidth-width)+(tempHeight-height)*(tempHeight-height);
			if(tempDiff<diff){
				diff = tempDiff;
				resolution = tempResolution;
			}
    	}
    	if(resolution!=null){
    		return this.getNavigateTemplateByResolution(site,resolution);
    	}else{
    		return null;
    	}
    }

	
}
