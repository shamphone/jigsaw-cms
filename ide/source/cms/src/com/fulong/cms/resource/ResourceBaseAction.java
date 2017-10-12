package com.fulong.cms.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.longcon.resource.ResourceManager;

/**
 *
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public abstract class ResourceBaseAction extends CMSBaseAction {
    /**
     * doPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
        return this.resourcePerform(mapping, form, request, response);
    }

    protected abstract ActionForward resourcePerform(ActionMapping mapping,
                                                  ActionForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws
            Exception;

    protected ResourceWrapper getRootFolder(HttpServletRequest request) throws
            IOException {
        /*
         Principal owner = this.getPassportProvider().getDefaultOrganization();
            Site site = this.getSiteFactory(request).getSite(request.getServerName());
            if (site != null)
                owner = site.getOwner();
         */
        return new ResourceWrapper(this.getRepository(request), this.getCurrentOrg(request, null));
    }
    public ResourceManager getResourceManager() {
        return (ResourceManager)this.getBeanFactory().getBean("resourceManager");
    }    
}
