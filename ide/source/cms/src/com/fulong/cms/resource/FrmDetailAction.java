package com.fulong.cms.resource;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.content.ContentBaseAction;
import com.fulong.common.BaseAction;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
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
 * @author lixf,lichengzhao
 *
 * @version 2.0
 *
 */
public class FrmDetailAction extends ContentBaseAction {

    public ActionForward doExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Node user = null;
        if(this.getCurrentSite(request, response)!=null)
        	user = this.getCurrentSite(request, response).getOwner();
        if(user == null)
        	user = this.getPassportProvider(request).getDefaultOrganization();
        String resourceType = request.getParameter("resourceType");
        String timeDiff = request.getParameter("timeDiff");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(timeDiff));
        NodeDefinition nd =  this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme");
        Query query = this.getRepository(request).getQueryManager().createQuery(nd, Query.SQL);
        query.filterByParent(user, false);
        query.filterBySpecifiedDate("createdTime", cal.getTime());
        query.filterByNotEqualValue("length", null);

        if ("image".equals(resourceType)) {
            query.filterByKeywords("mime", new String[]{"image/%"});
        } else if ("media".equals(resourceType)) {
            query.filterByKeywords("mime", new String[]{"video/%", "audio/%"});
        } else if ("flash".equals(resourceType)) {
            query.filterByKeywords("mime", new String[]{"application/x-shockwave-flash"});
        }

        NodeIterator resources = query.nodes();
        request.setAttribute("resources", resources);
        return mapping.findForward("success");
    }
}
