package com.fulong.site.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.SiteForm;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: 显示模型管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @author liuzijun
 * @version 1.0.1
 */
public class ToChangeNavigateAction extends ManagementBaseAction {

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm aform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
    	String siteID = request.getParameter("siteID");
    	if(siteID!=null){
    		Node site = this.getRepository(request).getNode(siteID);
    		if(site!=null){
    			SiteForm form =  (SiteForm) aform;
    			List<SiteTemplate> templates = new ArrayList<SiteTemplate>();
    	        String[] templateNames = site.getProperty("templates").getArray();
    	        for(String tName:templateNames){
    	        	SiteTemplate template = this.getSiteFactory(request).getTemplate(tName);
    	        	if(template!=null){
    	        		templates.add(template);
    	        	}
    	        }
    	        request.setAttribute("templates", templates);
    	        List<SiteTemplate> navigateTemplates = new ArrayList<SiteTemplate>();
    	        String[] ntemplateNames = site.getProperty("navigateTemplates").getArray();
    	        for(String ntName:ntemplateNames){
    	        	SiteTemplate template = this.getSiteFactory(request).getTemplate(ntName);
    	        	if(template!=null){
    	        		navigateTemplates.add(template);
    	        	}
    	        }
    			request.setAttribute("navigateTemplates", navigateTemplates);
    			form.setID(siteID);
    		}
    	}
		return mapping.findForward("success");
    }
}
