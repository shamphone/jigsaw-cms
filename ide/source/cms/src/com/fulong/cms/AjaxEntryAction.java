package com.fulong.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * <p>Title: Longcon Passport 2.0</p>
 *
 * <p>Description: Longcon Passport core System</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author JiangQi
 * @version 1.0
 */
public class AjaxEntryAction
    extends CMSAjaxAction {
    public Document renderXML(HttpServletRequest request,
                              HttpServletResponse response) throws
        Exception {
        String code = request.getParameter("parent_code");
        String type = request.getParameter("type");
        if (code != null && !code.equals("")) {
            Node entry = this.getRepository(request).getNode(code);
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            NodeIterator it = entry.getNodes();
            ParameterSet set = new ParameterSet();
            while (it!=null&&it.hasNext()) {
                Node child = (Node) it.next();
                if(child.getProperty("title")!=null&&child.getProperty("title").getString()!=null&&!child.getProperty("title").getString().equals("null")){
                	set.put(child.getProperty("title").getString(), child.getID());
                }                
            }
            return set.toDocument();
        }
        else {
            return null;
        }

    }
}
