package com.fulong.site.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.SiteForm;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lichengzhao
 * @author liuzijun
 * @version 2.0
 */
public class CreateAction extends ManagementBaseAction {
	
	private static final String FORMAT = "%1$03d";

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm aform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
    	SiteForm form = (SiteForm) aform;        
        SiteFactory factory = this.getSiteFactory(request);
        String templateID = factory.getDefaultTemplate().getName();
        // 生成默认域名和网站显示名称
//        String defaultDomain = factory.getDefaultDomain();
        String defaultDomain = ".coolink.cn";
        String domain = templateID + defaultDomain;
        Site site = factory.getSite(domain);
        if (site != null) {
	        int tempNo = 1;
	        String no = String.format(FORMAT, tempNo);
	        domain = templateID + no + defaultDomain;
	        site = factory.getSite(domain);
	        while (site != null) {
	            no = String.format(FORMAT, ++tempNo);
		        domain = templateID + no + defaultDomain;
	            site = factory.getSite(domain);
	        }
        }
        form.setDomain(domain);
        form.setDisplayName(request.getUserPrincipal().getName() + this.getResources(request).getMessage("site.whose.site")); 
        //
        Node node = (Node) request.getUserPrincipal();
        if (node.getProperty("user-commonname") != null) {
	        form.setOwnerID(node.getName());
	        form.setOwnerName(node.getProperty("user-commonname").getString());
	        form.setDisplayName(form.getOwnerName() + this.getResources(request).getMessage("site.whose.site")); 
        }
        //
        SiteTemplate template = factory.getTemplate(templateID);
        return mapping.findForward("success");
    }
}
