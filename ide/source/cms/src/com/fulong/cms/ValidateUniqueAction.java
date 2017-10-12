package com.fulong.cms;

import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.cms.content.ContentBaseAction;
import com.fulong.common.BaseAction;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.NodeDefinition;
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
public class ValidateUniqueAction extends ContentBaseAction {
    public ActionForward doExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            Exception {
        response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        Writer writer = response.getWriter();
        String categoryID = request.getParameter("categoryID");
        String contentID = request.getParameter("contentID");
        NodeDefinition def = this.getRepository(request).getDefinitionManager().
                             getDefinition(categoryID);
        Node content = this.getRepository(request).getNode(contentID);
        String property = request.getParameter("property");
        String value = request.getParameter("value");
        Query query = this.getRepository(request).getQueryManager().createQuery(def,
                Query.SQL);
        
        query.filterByProperty(property, value);
        NodeIterator it = query.nodes();
        if (content == null) {
            if (it.hasNext()) {
                writer.append("false");
            } else {
                writer.append("true");
            }
        } else {
            if (!it.hasNext()) {
                writer.append("true");
            } else {
                String temp = "false";
                while (it.hasNext()) {
                    Node nd = it.nextNode();
                    if (!nd.getID().equals(content.getID())) {
                        temp = "false";
                        break;
                    }
                    temp = "true";
                }
                writer.append(temp);
            }
        }
        writer.close();
        return null;
    }
}
