package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.site.form.SiteForm;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: 显示模型管理</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @author liuzijun
 * @version 1.0.1
 */
public class ChangeNavigateAction extends ManagementBaseAction {

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm aform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
    	SiteForm form =  (SiteForm) aform;
    	String siteID = form.getID();
        if(siteID!=null){
        	Node site = this.getRepository(request).getNode(siteID);
        	if(site!=null){
        		if(form.getNavigateTemplateIDs()!=null){
        			site.setProperty("navigateTemplates", form.getNavigateTemplateIDs());
                	request.setAttribute("site", site);
                }
        	}
        }
    	return mapping.findForward("success");
    }
}
