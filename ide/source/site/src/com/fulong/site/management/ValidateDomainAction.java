package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.io.Writer;

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
 * @version 2.0
 */
public class ValidateDomainAction extends ManagementBaseAction {

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        String domain = request.getParameter("domain");
        if (this.getSiteFactory(request).getSite(domain) != null) {
            response.setContentType("text/xml");
            response.setHeader("Content-Type", "text/xml; charset=UTF-8");
            Writer writer = response.getWriter();
            writer.write("domainExits");
            writer.close();
        }
        return null;
    }
}
