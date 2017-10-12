package com.fulong.site.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.RangeIterator;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.site.SiteTemplateCollection;

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
 * @author 马伟山
 * @author lixf
 * @author liuzijun
 * @version 1.0
 */
public class ListAction extends TemplateBaseAction {
    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
	            Exception {
	        response.setHeader("Cache-Control", "no-cache");
	        String isDialog = request.getParameter("isDialog");
			if(isDialog!=null&&isDialog.equals("dialog")){
				request.setAttribute("templates", this.getTemplates(request));
				return mapping.findForward("dialogList");
			}else{
				request.setAttribute("templates", this.getTemplates(request));
				return mapping.findForward("success");
			}
    }
}
