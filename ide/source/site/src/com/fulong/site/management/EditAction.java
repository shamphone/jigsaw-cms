package com.fulong.site.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.Site;
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
 * @author lixf
 * @author liuzijun
 * @version 1.0
 */
public class EditAction extends ManagementBaseAction {
    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm aform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        SiteForm form=(SiteForm)aform;
        Node site = null;
        String siteID = request.getParameter("siteID");
        NodeDefinition siteDef = this.getRepository(request).getDefinitionManager().getDefinition("site-scheme");
        if(siteID!=null)
        	site = this.getRepository(request).getNode(siteID);
        if(site == null){
        	Query query = this.getRepository(request).getQueryManager().createQuery(siteDef, Query.SQL);
			query.filterByProperty("domain", request.getServerName());
			NodeIterator<Node> sites = query.nodes();
			if(sites.getSize() == 1)
				site = sites.nextNode();
        }
        if(site == null)
            return mapping.findForward("noSite");
        Node owner = site.getParent();
        if(owner != null) {
            form.setOwnerID(owner.getName());
            Property username = owner.getProperty("user-commonname");
            if (username != null)
            	form.setOwnerName(username.getString());
            else
            	form.setOwnerName(owner.getName());
        }
        form.setDisplayName(site.getProperty("displayName").getString());
        form.setDomain(site.getProperty("domain").getString());
        form.setID(site.getID());
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
        request.setAttribute("site", site);
        return mapping.findForward("success");
    }
}
