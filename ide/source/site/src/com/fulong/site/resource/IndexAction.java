package com.fulong.site.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.common.FileWrapper;


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
 * @author jiangqi
 * @author liuzijun
 * @version 1.0
 */
public class IndexAction extends ResourceBaseAction {
    public ActionForward resourcePerform(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
    	String templateId= request.getParameter("templateID");
    	String type = request.getParameter("type");
        request.setAttribute("template",templateId);
        request.setAttribute("type", type);
        return this.setForwardPath(mapping, "success", templateId+"&type="+type);
    }
}
