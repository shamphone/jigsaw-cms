package com.fulong.cms.resource;

import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.Site;
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
 * @author lichengzhao
 * @version 1.0
 */
public class ViewAction extends ResourceBaseAction {

    public ActionForward resourcePerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
    	String resourceType = request.getParameter("resourceType");
    	String temp = request.getParameter("day");
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(Long.parseLong(temp));
//        Node user = this.getCurrentOrg(request, response);
        Site site = this.getCurrentSite(request, response);
        NodeDefinition nd =  this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme");
        Query query = this.getRepository(request).getQueryManager().createQuery(nd, Query.SQL);
        if(site!=null){
        	query.filterByParent(site.getOwner().getNode("resources"), false);
        }
        query.filterBySpecifiedDate("createdTime", day.getTime());
        query.filterByNotEqualValue("length", null);
		
        if ("image".equals(resourceType))
            query.filterByKeywords("mime", new String[]{"image/%"});
        else if ("media".equals(resourceType))
            query.filterByKeywords("mime", new String[]{"video/%", "audio/%"});
        else if ("flash".equals(resourceType))
            query.filterByKeywords("mime", new String[]{"application/x-shockwave-flash"});
         
        Iterator<Node> resources = query.nodes();
        request.setAttribute("resources", resources);
        return mapping.findForward("success");
    }
}
