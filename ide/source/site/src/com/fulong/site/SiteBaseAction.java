package com.fulong.site;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.BaseAction;
import com.fulong.common.WordUtils;
import com.fulong.common.util.ParameterString;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.security.User;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteException;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.view.ListViewManager;
import com.fulong.longcon.workflow.WorkflowService;

/**
 * <p>Title: 龙驭网站设计</p>
 *
 * <p>Description: 龙驭网站设计</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author zhanglun
 * @version 3.0
 */
public abstract class SiteBaseAction extends BaseAction {
	protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 覆盖实现Action基类中的Action
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ActionForward forward = null;
        long start = 0;
        if (log.isTraceEnabled())
            start = System.currentTimeMillis();

        if (this.checkRole(mapping, form, request, response)) {
            forward = doPerform(mapping, form, request, response);
        } else {
            forward = mapping.findForward("forbidden");
        }
        /*
                 if (request.getUserPrincipal() != null) {
            request.setAttribute("isAdmin",
                                 request.getUserPrincipal().getName().
                                 equals("1000000000000"));
            request.setAttribute("isOrg", "" + (
                    this.getCurrentOrg(request, response) != null));
                 }
         */
        if (log.isTraceEnabled()) {
            log.trace("[" + (System.currentTimeMillis() - start) + "]");
        }
        return forward;

    }

    /**
     * 角色控制，如果需要确认当前用户是否有执行这个Action的权限。
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param connection Connection
     * @return boolean
     */
    protected boolean checkRole(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        return true;
    }

    /**
     * 所有的子类应该实现这个方法，进行页面初始化和用户事件处理
     * @throws java.lang.Exception ：在这个方法的实现中，原则上不进行任何的异常处理，仅抛出异常
     */

    protected abstract ActionForward doPerform(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    protected Date string2Date(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    protected ActionForward forward(ActionMapping mapping, String name
                                    , Object arg0) {
        return forward(mapping, name, new Object[] {arg0});
    }

    protected ActionForward forward(ActionMapping mapping,
                                    String name,
                                    Object[] args) {
        ActionForward source = mapping.findForward(name);
        String path = source.getPath();
        ParameterString ps = new ParameterString(path);
        ps.replace(args);
        ActionForward dest = new ActionForward();
        dest.setPath(ps.toString());
        dest.setRedirect(source.getRedirect());
        dest.setName(source.getName());
        return dest;
    }

    protected PassportProvider getPassportProvider(HttpServletRequest request) {
        return ((PassportProvider)this.getBeanFactory().getBean("passport"));
    }

    /**
     * 从相对路径中解析出所包含的栏目
     * @param path String 格式：/[网站模板的名称]/[栏目路径]
     * @return Channel
     */
    protected Channel parseChannel(String path,HttpServletRequest request) {
        if (path == null)
            return null;
        //兼容已有系统，去掉/sites；
        if(path.startsWith("/sites/"))
        	path = path.substring("/sites".length());             
        String str[] = path.split("[/\\\\]");
        if ((str.length < 2) || (str[1] == null) || (str[2] == null))
            return null;
        SiteTemplate template = this.getSiteFactory(request).getTemplate(str[1]);
        if (template == null)
            return null;
        return template.getChannel(path.substring(str[1].length()+1));
    }
    
    /**
     * 从相对路径中解析出所包含的模板
     * @param path String 格式：/[网站模板的名称]/[文件夹路径]
     * @return Channel
     */
    protected SiteFolder parseFolder(String path,HttpServletRequest request) {
    	if (path == null)
    		return null;
    	String str[] = path.split("[/\\\\]");
    	if ((str.length < 2) || (str[1] == null))
    		return null;
    	SiteTemplate template = this.getSiteFactory(request).getTemplate(str[1]);
    	if (template == null)
    		return null;
    	return template.getFolder(path.substring(str[1].length()+1));
    }

    /**
     * 从相对路径中解析出所包含的栏目
     * @param path String 格式：/sites/[网站模板的名称]/[栏目名称].jsp
     * @return Channel
     */
    protected SiteTemplate parseTemplate(String path,HttpServletRequest request) {
        if (path == null)
            return null;
        if(path.startsWith("/sites/"))
        	path = path.substring("/sites".length());
        String str[] = path.split("[/\\.]");
        if ((str.length < 3) || (str[1] == null) || (str[2] == null))
            return null;
        String templateID = str[1];
        return this.getSiteFactory(request).getTemplate(templateID);
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
 
	protected WordUtils getWordUtils(HttpServletRequest request) {
		return (WordUtils) getBeanFactory().getBean("wordUtils", WordUtils.class);
	}
	
	protected FilterParser getFilterParser(HttpServletRequest request, HttpServletResponse response){
		return new ServletFilterParser(request,response);
	}
}
