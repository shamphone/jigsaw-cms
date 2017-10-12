package com.fulong.taglib;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fulong.longcon.calendar.CalendarRepository;
import com.fulong.longcon.counter.AccessCounterRepository;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.report.ReportRepository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.resource.ResourceManager;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.security.User;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.system.Configuration;
import com.fulong.longcon.system.xml.XMLConfiguration;
import com.fulong.longcon.workflow.WorkflowService;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class SpringTagSupport extends BodyTagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2790646189508832113L;
	protected final Log log = LogFactory.getLog(this.getClass());
	protected WorkflowService getWorkflowService() {
        return (WorkflowService)this
                .getBeanFactory()
                .getBean("workflow", WorkflowService.class);
    }

    protected Repository getRepository() {
        return (Repository)this
                .getBeanFactory()
                .getBean("repository", Repository.class);
    }
    protected BeanFactory getContext() {
         return this.getBeanFactory();
    }
    protected BeanFactory getBeanFactory() {
        return WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
    }

    protected Configuration getConfiguration() {
        return (Configuration)this
                .getContext().getBean("configuration", Configuration.class);
    }

    protected PassportProvider getPassportProvider() {
        return (PassportProvider)this
                .getContext().getBean("passport", PassportProvider.class);
    }

    protected CalendarRepository getCalendarRepository() {
        return (CalendarRepository)this
                .getContext()
                .getBean("calendarRepository", CalendarRepository.class);
    }

    protected XMLConfiguration getXMLConfiguration() {
        return (XMLConfiguration)this
                .getContext().getBean("configuration", XMLConfiguration.class);
    }

    protected ReportRepository getReportRepository() {
        return (ReportRepository)this
                .getContext().getBean("reports", ReportRepository.class);
    }
    
    protected AccessCounterRepository getAccessCounterRepository() {
    	return (AccessCounterRepository)this
    	.getContext().getBean("acccessCounterRepository", AccessCounterRepository.class);
    }

    protected SiteFactory getSiteFactory() {
        return (SiteFactory)this.getContext().getBean("siteFactory",
                SiteFactory.class);
    }

    protected String getSystemName() {
        return this.getXMLConfiguration().getProperty("system.name");
    }

    protected ResourceManager getResourceManager(){
        return (ResourceManager)this.getContext().getBean("resourceManager");
    }
    protected FilterParser getFilterParser(HttpServletRequest request, HttpServletResponse response){
		return new ServletFilterParser(request,response);
	}
    protected String getCopyright() {
        return this.getXMLConfiguration().getProperty("copyright");
    }
    
    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    protected Node getCurrentUser(HttpServletRequest request) {
    	if (request.getUserPrincipal() == null) {
    		return null;
    	}
    	Node user = (Node) request.getSession().getAttribute(User.class.getName());
    	if (user == null) {
    		user = (Node) request.getUserPrincipal();
    		if (user != null) {
    			request.getSession().setAttribute(User.class.getName(), user);
    		}
    	}
    	return user;
    }
    /**
     * 获取当前网站
     * @param request
     * @return
     * @throws JspException 
     */
    protected Site getCurrentSite(ServletRequest  request) throws JspException{
    	String siteName = request.getServerName();
		int port = request.getServerPort();
		if(port!=80){
			siteName += (":"+port);
		}
		Site site = this.getSiteFactory().getSite(siteName);
		return site;
    }
	/**
	 * 获取当前栏目
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return Channel
	 * @throws Exception
	 */
	protected Channel getCurrentChannel(HttpServletRequest request) {
		Channel channel = (Channel) request.getAttribute(Channel.class.getName());
		if (channel == null) {
			HttpServletRequest req = request;
			if(req.getContextPath().split("/").length<1){
				return null;
			}
			String contextName = req.getContextPath().split("/")[1];
			if (this.getSiteFactory().getTemplate(contextName) == null) {
				return null;
			}			
			String servletPath = req.getServletPath();
			if(servletPath.endsWith(".bak.bak")){
				servletPath = servletPath.substring(0, servletPath.length()-8);
			}
			
			channel = this.getSiteFactory().getTemplate(contextName).getChannel(servletPath);
			if (channel == null) {
				return null;
			}
			if (channel != null) {
				request.setAttribute(Channel.class.getName(), channel);
			}
		}
		return channel;
	}
    /**
     *	从request中解析node
     * @param request
     * @return
     * @throws JspException
     */
	protected Node lookupNode(ServletRequest  request) throws JspException{
    	Node node = null;
    	String nodeId = request.getParameter("contentId");
		if(nodeId!=null){
			node = this.getRepository().getNode(nodeId);
			if(node==null){
				String msg = "Unable to find node for ID :" + nodeId +".";
				if(log.isWarnEnabled()){
					log.warn(msg);
				}
			}
		}
		return node;
    }
}
