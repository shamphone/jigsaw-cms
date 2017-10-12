package com.fulong.portlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.MessageResources;

import com.fulong.common.BaseAction;
import com.fulong.common.WordUtils;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.PortletFilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.security.User;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.view.ListViewManager;
import com.fulong.longcon.workflow.WorkflowService;

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
public class PorletController extends BaseAction {
	protected LongconPortlet portlet;
	protected PortletConfig portletConfig;
	protected PortletContext portletContext;
	
	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);
		this.portlet = (LongconPortlet) servlet;
		this.portletConfig = portlet.getPortletConfig();
		this.portletContext = this.portletConfig.getPortletContext();
	}

	/**
	 * 基类覆盖实现这个方法即可
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

		return null;

	}

	/**
	 * 覆盖父类的实现
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			return execute(mapping, form, (PortletRequest) request, (PortletResponse) response);

	}

	protected void addMessages(PortletRequest request, ActionMessages messages) {
		addMessages((HttpServletRequest) request, messages);
	}

	protected void addErrors(PortletRequest request, ActionMessages errors) {
		super.addErrors((HttpServletRequest) request, errors);
	}

	protected String generateToken(PortletRequest request) {
		return super.generateToken((HttpServletRequest) request);
	}

	protected DataSource getDataSource(PortletRequest request) {

		return (getDataSource(request, Globals.DATA_SOURCE_KEY));

	}

	protected DataSource getDataSource(PortletRequest request, String key) {

		return super.getDataSource((HttpServletRequest) request, key);
	}

	protected ActionMessages getErrors(PortletRequest request) {
		return super.getErrors((HttpServletRequest) request);
	}

	protected Locale getLocale(PortletRequest request) {

		return super.getLocale((HttpServletRequest) request);

	}

	protected ActionMessages getMessages(PortletRequest request) {
		return super.getMessages((HttpServletRequest) request);
	}

	protected MessageResources getResources(PortletRequest request) {

		return super.getResources((HttpServletRequest) request);

	}

	protected MessageResources getResources(PortletRequest request, String key) {

		return super.getResources((HttpServletRequest) request);

	}

	protected boolean isCancelled(PortletRequest request) {

		return super.isCancelled((HttpServletRequest) request);
	}

	protected boolean isTokenValid(PortletRequest request) {

		return super.isTokenValid((HttpServletRequest) request, false);

	}

	protected boolean isTokenValid(PortletRequest request, boolean reset) {

		return super.isTokenValid((HttpServletRequest) request, reset);

	}

	/**
	 * <p>
	 * Reset the saved transaction token in the user's session. This indicates
	 * that transactional token checking will not be needed on the next request
	 * that is submitted.
	 * </p>
	 * 
	 * @param request
	 *            The servlet request we are processing
	 */
	protected void resetToken(PortletRequest request) {

		super.resetToken((HttpServletRequest) request);

	}

	protected void saveErrors(PortletRequest request, ActionMessages errors) {
		super.saveErrors((HttpServletRequest) request, errors);

	}

	protected void saveMessages(PortletRequest request, ActionMessages messages) {
		// Remove any messages attribute if none are required
		if ((messages == null) || messages.isEmpty()) {
			((HttpServletRequest) request).getSession().removeAttribute(Globals.MESSAGE_KEY);
			return;
		}

		// Save the messages we need
		((HttpServletRequest) request).getSession().setAttribute(Globals.MESSAGE_KEY, messages);
	}

	protected void saveMessages(PortletSession session, ActionMessages messages) {
		super.saveMessages((HttpSession) session, messages);
	}

	protected void saveErrors(PortletSession session, ActionMessages errors) {
		super.saveErrors((HttpSession) session, errors);
	}

	/**
	 * <p>
	 * Save a new transaction token in the user's current session, creating a
	 * new session if necessary.
	 * </p>
	 * 
	 * @param request
	 *            The servlet request we are processing
	 */
	protected void saveToken(PortletRequest request) {
		super.saveToken((HttpServletRequest) request);

	}

	/**
	 * <p>
	 * Set the user's currently selected <code>Locale</code> into their
	 * <code>HttpSession</code>.
	 * </p>
	 * 
	 * @param request
	 *            The request we are processing
	 * @param locale
	 *            The user's selected Locale to be set, or null to select the
	 *            server's default Locale
	 */
	protected void setLocale(PortletRequest request, Locale locale) {
		super.setLocale((HttpServletRequest) request, locale);

	}

	/**
	 * 
	 * @param request
	 *            PortletRequest
	 * @deprecated
	 * @return Channel
	 */
	protected Channel getRequestChannel(PortletRequest request) {
		return this.getCurrentChannel(request, null);
	}

	/**
	 * 
	 * @param request
	 *            PortletRequest
	 * @return Node
	 */
	protected Node getRequestNode(PortletRequest request) {
		Node node = (Node) request.getAttribute(Node.class.getName());
		if (node == null) {
			String id = request.getParameter("contentId");
			if (id != null) {
				node = this.getRepository().getNode(id);
				if (node != null) {
					request.setAttribute(Node.class.getName(), node);
				}
			}
		}
		return node;
	}

	/**
	 * 获取原始的HttpServletRequest。当需要在同一个页面的占位符间传递属性时，可以使用这个方法
	 * 
	 * @param request
	 *            PortletRequest
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getServletRequest(PortletRequest request) {
		HttpServletRequest wrapper = (HttpServletRequest) request;
		while (wrapper instanceof HttpServletRequestWrapper) {
			wrapper = (HttpServletRequest) ((HttpServletRequestWrapper) wrapper).getRequest();
		}
		return wrapper;
	}

	/**
	 * 获取原始的HttpServletResponse
	 * 
	 * @param request
	 *            PortletResponse
	 * @return HttpServletResponse
	 */
	protected HttpServletResponse getServletResponse(PortletResponse request) {
		HttpServletResponse wrapper = (HttpServletResponse) request;
		while (wrapper instanceof HttpServletResponseWrapper) {
			wrapper = (HttpServletResponse) ((HttpServletResponseWrapper) wrapper).getResponse();
		}
		return wrapper;
	}

	/**
	 * 
	 * @param request
	 *            PortletRequest
	 * @param relativePath
	 *            String
	 * @return String
	 */
	protected String toContextPath(PortletRequest request, String relativePath) {
		if (relativePath == null) {
			return null;
		}
		String context = request.getContextPath();
		if (context.length() > 0) {
			if (relativePath.startsWith(context)) {
				return relativePath.substring(context.length());
			}
		}
		return relativePath;
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
	protected Site getCurrentSite(PortletRequest request, PortletResponse response) throws Exception {
		Site site = (Site) request.getAttribute(Site.class.getName());
		if (site == null) {
			String siteId = request.getParameter("siteId");
			if (siteId == null) {
				siteId = request.getServerName();
				if(request.getServerPort()!=80){
					siteId += (":"+80);
				}
			}
			site = this.getSiteFactory().getSite(siteId);
			if (site != null) {
				request.setAttribute(Site.class.getName(), site);
			}
		}
		return site;
	}

	/**
	 * 获取当前网站模板
	 * 
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return SiteTemplate
	 * @throws Exception
	 */
	protected SiteTemplate getCurrentSiteTemplate(PortletRequest request, PortletResponse response) {
		SiteTemplate template = (SiteTemplate) request.getAttribute(SiteTemplate.class.getName());
		if (template == null) {
			HttpServletRequest req = (HttpServletRequest) request;
			String path = req.getContextPath();
			template = this.getSiteFactory().getTemplate(path.substring(1));
			if (template != null) {
				request.setAttribute(SiteTemplate.class.getName(), template);
			}
		}
		return template;
	}

	/**
	 * 获取当前栏目
	 * 
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return Channel
	 * @throws Exception
	 */
	protected Channel getCurrentChannel(PortletRequest request, PortletResponse response) {
		Channel channel = null;
		if (request.getAttribute(Channel.class.getName()) != null)
			channel = (Channel) request.getAttribute(Channel.class.getName());
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		if (channel == null) {
			/*
			String channelId = req.getParameter("channel");
			if (channelId != null && !channelId.equals(""))
				channel = this.getSiteFactory().getChannel(channelId);
			*/
		}
		
		if (channel == null) {			
			SiteTemplate template =  this.getCurrentSiteTemplate(request, response);
			String servletPath = req.getServletPath();
			if(servletPath.endsWith(".bak.bak")){
				servletPath = servletPath.substring(0, servletPath.length()-8);
			}
			channel = template.getChannel(servletPath);
			if (channel != null) {
				request.setAttribute(Channel.class.getName(), channel);
			}
		}
		return channel;
	}

	/**
	 * 获取当前登入用户
	 * 
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return User
	 */
	protected Node getCurrentUser(PortletRequest request, PortletResponse response) {
		return this.getCurrentUser((HttpServletRequest) request, (HttpServletResponse) response);

	}

	/**
	 * 获取当前登入机构
	 * 
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return User
	 */
	protected Node getCurrentOrg(PortletRequest request, PortletResponse response) {
		return this.getCurrentOrg((HttpServletRequest) request, (HttpServletResponse) response);

	}


	
	protected FilterParser getFilterParser(PortletRequest request, PortletResponse response){
		return new PortletFilterParser(request,response);
	}
	/**
	 *  上传文件
	 *  by lichengzhao,lixf
	 */
	protected String upload(FileItem file, Node owner, HttpServletRequest request) throws FileNotFoundException,
			IOException, ValueFormatException {
		if (file == null || file.getSize() == 0)
			return null;
		String baseName = FilenameUtils.getName(file.getName());
		String fileName = baseName;
		Node root = owner.getNode("resources");
		if(root == null){
			root =owner.addNode(this.getRepository().getDefinitionManager().getDefinition("resource-scheme"),"resources");
		}
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		if(root.getNodes(fileName).getSize()>0){
			do{
				int pos = fileName.lastIndexOf(".");
				if (pos > 0)
					fileName = fileName.substring(0, pos) +  "_" + df.format(System.currentTimeMillis()) + fileName.substring(pos);
				else
					fileName = fileName + df.format(System.currentTimeMillis());
			}while (root.getNodes(fileName).getSize() > 0) ;
		}
		
		Node newResource = root.addNode(this.getRepository().getDefinitionManager().getDefinition("resource-scheme"), fileName);
		newResource.setProperty("resource-content", file.getInputStream());
		newResource.setProperty("mime", this.getServlet().getServletContext().getMimeType(fileName));
		newResource.setProperty("createdTime", Calendar.getInstance());
		//newResource.setProperty("title", baseName);
		newResource.setProperty("length", newResource.getProperty("resource-content").getLength());
		return newResource.getPath();
	}
	
	  
  
   /**
    *
    * @return ListViewManager
    */
   protected ListViewManager getListViewManager() {
       return (ListViewManager)this.getBeanFactory().getBean("viewManager");
   }

   /**
    *
    * @return WorkflowService
    */
   public WorkflowService getWorkflowService() {
       return (WorkflowService)this.getBeanFactory()
               .getBean("workflow");
   }
   /**
   *
   * @return Repository
   */

  protected Repository getRepository() {
      return (Repository)getBeanFactory().getBean("repository", Repository.class);
  }

  /**
   *
   * @return SiteFactory
   */
  protected SiteFactory getSiteFactory() {
      return (SiteFactory)getBeanFactory().getBean("siteFactory");
  }
  

   
  
 
   /**
    * 获取当前登入用户
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return User
    */

   protected Node getCurrentUser(HttpServletRequest request,
                                 HttpServletResponse response) {
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
      * 获取当前请求的网站
      * @param request PortletRequest
      * @param response PortletResponse
      * @return Site
      * @throws Exception
      */
     protected Site getCurrentSite(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
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
   /**
    * 获取当前登入的机构
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return Organization
    */
   protected Node getCurrentOrg(HttpServletRequest request,
                                        HttpServletResponse response) {
       return this.getCurrentUser(request, response);
   }
   /**
    * 当前用户是否是系统管理员
    * @param request HttpServletRequest
    * @return boolean
    */
   protected boolean isAdministrator(HttpServletRequest request) {
       Node org = this.getCurrentOrg(request, null);
       if (org != null)
           return org.getName().equals("1000000000000");
       else
           return request.getUserPrincipal().getName().
                   equals("1000000000000");

   }
   protected PassportProvider getPassportProvider() {
       return ((PassportProvider)this.getBeanFactory().getBean("passport"));
   }

  
 
	protected WordUtils getWordUtils() {
		return (WordUtils) getBeanFactory().getBean("wordUtils", WordUtils.class);
	}
	
	protected FilterParser getFilterParser(HttpServletRequest request, HttpServletResponse response){
		return new ServletFilterParser(request,response);
	}	
	 /**
     * 从相对路径中解析出所包含的栏目
     * @param path String 格式：/sites/[网站模板的名称]/[栏目名称].jsp
     * @return Channel
     */
    protected Channel parseChannel(String path) {
        if (path == null)
            return null;
        //兼容已有系统，去掉/sites；
        if(path.startsWith("/sites/"))
        	path = path.substring("/sites".length());             
        String str[] = path.split("[/\\\\]");
        if ((str.length < 2) || (str[1] == null) || (str[2] == null))
            return null;
        SiteTemplate template = this.getSiteFactory().getTemplate(str[1]);
        if (template == null)
            return null;
        return template.getChannel(path.substring(path.indexOf(str[2])-1, path.length()));
    }

    /**
     * 从相对路径中解析出所包含的栏目
     * @param path String 格式：/sites/[网站模板的名称]/[栏目名称].jsp
     * @return Channel
     */
    protected SiteTemplate parseTemplate(String path) {
        if (path == null)
            return null;
        if(path.startsWith("/sites/"))
        	path = path.substring("/sites".length());
        String str[] = path.split("[/\\.]");
        if ((str.length < 3) || (str[1] == null) || (str[2] == null))
            return null;
        String templateID = str[1];
        return this.getSiteFactory().getTemplate(templateID);
    }

	
}
