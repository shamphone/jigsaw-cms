package com.fulong.site.management;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.security.User;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteExistsException;
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
public class InsertAction extends ManagementBaseAction {

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm sform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        SiteForm form = (SiteForm) sform;
        User user = this.getPassportProvider(request).getUser(form.getOwnerID());
        String domain = form.getDomain();
        NodeDefinition siteDef = this.getRepository(request).getDefinitionManager().getDefinition("site-scheme");
        Query query = this.getRepository(request).getQueryManager().createQuery(siteDef, Query.SQL);
        query.filterByProperty("domain", domain);
        NodeIterator<Node> existSites = query.nodes();
        if(existSites.hasNext()){
        	ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("site.domain.existent"));
			this.saveErrors(request, errors);
			return mapping.findForward("failed");
        }
        Node site = user.addNode(siteDef, "site");
        site.setProperty("domain", domain);
        site.setProperty("displayName", form.getDisplayName());
        SiteFactory factory = this.getSiteFactory(request);
		String[] templateIds = form.getTemplateIDs();
		site.setProperty("templates", templateIds);
		site.setProperty("navigateTemplates", form.getNavigateTemplateIDs());
        request.setAttribute("site", site);
        return mapping.findForward("success");
    }
}
