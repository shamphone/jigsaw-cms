package com.fulong.site.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.site.SiteTemplate;
import java.io.Writer;

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
 * @version 3.1
 */
public class CheckAction extends TemplateBaseAction {

	protected ActionForward templateExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        SiteTemplate template = this.getSiteFactory(request).getTemplate(name); 
        response.setContentType("text/html");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("" + (template == null));
        writer.close();
        return null;
	}
}
