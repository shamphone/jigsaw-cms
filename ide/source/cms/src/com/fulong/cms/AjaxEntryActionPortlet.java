package com.fulong.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.common.util.PortletParameterSet;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * <p>Title: Coolink 1.0</p>
 *
 * <p>Description: Coolink</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author liuzijun
 * @version 1.0
 */
public class AjaxEntryActionPortlet
    extends CMSAjaxAction {
    public Document renderXML(HttpServletRequest request,
                              HttpServletResponse response) throws
        Exception {
        String code = request.getParameter("parent_code");
        String displayProp = request.getParameter("displayProp");
        String valueProp = request.getParameter("valueProp");
        if (code != null && !code.equals("")) {
            Node entry = this.getRepository(request).getNode(code);
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            NodeIterator it = entry.getNodes();
            PortletParameterSet set = new PortletParameterSet();
            while (it!=null&&it.hasNext()) {
                Node child = (Node) it.next();
                if(child.getProperty("title")!=null&&child.getProperty("title").getString()!=null&&!child.getProperty("title").getString().equals("null")){
                	try{
                		set.put(child.getProperty(displayProp).getString(), child.getProperty(valueProp).getString(), child.getID());
                	}catch (Exception ex){
                		set.put(child.getProperty("title").getString(), child.getID(),child.getID());
                	}                	
                }                
            }
            return set.toDocument();
        }
        else {
            return null;
        }

    }
}
