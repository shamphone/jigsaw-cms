package com.fulong.cms;

import java.awt.GraphicsEnvironment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import com.fulong.common.BaseAction;
import com.fulong.common.WordUtils;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.security.User;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.view.ListViewManager;
import com.fulong.longcon.workflow.WorkflowService;
import com.fulong.service.ServiceManager;
/**
 * 
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

public abstract class CMSBaseAction extends BaseAction {

	protected Log log = LogFactory.getLog(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date begin = null;
		if (log.isTraceEnabled()) {
			begin = new Date();
		}

			ActionForward forward = this.doPerform(mapping, form, request, response);
			if (log.isTraceEnabled()) {
				log.trace("execution " + (new Date().getTime() - begin.getTime()));
			}
			return forward;
	}

	protected LabelValueBean[] getFonts(HttpServletRequest request) {
		String[] family = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(request.getLocale());
		LabelValueBean[] fonts = new LabelValueBean[family.length];
		for (int i = 0; i < fonts.length; i++) {
			fonts[i] = new LabelValueBean(family[i], family[i]);
		}
		return fonts;
	}

	protected abstract ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected String uploadFile(FormFile values, HttpServletRequest request, HttpServletResponse response,boolean override) throws Exception {
		Node owner = null;
		if(this.getCurrentSite(request, response)!=null){
			owner = this.getCurrentSite(request, response).getOwner();
		}else {
			owner = this.getCurrentOrg(request, response);
		}
		if (owner == null) {
			owner = this.getPassportProvider(request).getDefaultOrganization();
		}
		return uploadFile(values, owner, request, response, override);
	}
	
	protected String uploadFile(FormFile values, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return uploadFile(values, request, response,false);
	}

	protected String uploadFile(FormFile values, Node owner, HttpServletRequest request, HttpServletResponse response,boolean override) throws Exception {
		if (values == null || values.getFileSize() == 0){
			return null;
		}
		String fileName = FilenameUtils.getName(values.getFileName());	
		Node root = owner.getNode("resources");
		if(root==null){
			root = owner.addNode(this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme"),"resources");
		}
		
		Node resource = null;
		if(override&&root.getNodes(fileName).getSize() > 0){
			
			resource = root.getNodes(fileName).next();
		}else{
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			while (root.getNodes(fileName).getSize() > 0) {		
				int pos = fileName.lastIndexOf(".");
				if (pos > 0)
					fileName = fileName.substring(0, pos) +  "_" + df.format(System.currentTimeMillis()) + fileName.substring(pos);
				else
					fileName = fileName + df.format(System.currentTimeMillis());
			}
			resource = root.addNode(this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme"), fileName);
		}
		
		resource.setProperty("resource-content", values.getInputStream());
		resource.setProperty("mime", this.getServlet().getServletContext().getMimeType(fileName.toLowerCase()));
		resource.setProperty("createdTime", Calendar.getInstance());
		resource.setProperty("length", resource.getProperty("resource-content").getLength());
		return resource.getPath();
	}

	
	
   /**
    *
    * @return ListViewManager
    */
   protected ListViewManager getListViewManager(HttpServletRequest request) {
       return (ListViewManager)this.getBeanFactory().getBean("viewManager");
   }

   /**
    *
    * @return WorkflowService
    */
   public WorkflowService getWorkflowService(HttpServletRequest request) {
       return (WorkflowService)this.getBeanFactory()
               .getBean("workflow");
   }
   /**
    *
    * @return Repository
    */

   protected Repository getRepository(HttpServletRequest request) {
       return (Repository)getBeanFactory().getBean("repository", Repository.class);
   }

    
   /**
    *
    * @return RemoteManager
    */
   /*protected RemoteManager getRemoteManager() {
       return (RemoteManager)getBeanFactory().getBean("remoteManager");
   }
*/
 

   /**
    *
    * @return SiteFactory
    */
   protected SiteFactory getSiteFactory(HttpServletRequest request) {
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
             site = this.getSiteFactory(request).getSite(siteId);
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
       return  this.getCurrentUser(request, response);
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
   protected PassportProvider getPassportProvider(HttpServletRequest request) {
       return ((PassportProvider)this.getBeanFactory().getBean("passport"));
   }

   /**
	 *
	 * @return ServiceManager
	 */
	protected ServiceManager getServiceManager(HttpServletRequest request) {
		return (ServiceManager)getBeanFactory().getBean("serviceManager", ServiceManager.class);
	}
 
	protected WordUtils getWordUtils(HttpServletRequest request) {
		return (WordUtils) getBeanFactory().getBean("wordUtils", WordUtils.class);
	}
	
	protected FilterParser getFilterParser(HttpServletRequest request, HttpServletResponse response){
		return new ServletFilterParser(request,response);
	}	
}
