package com.fulong.site;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteCategory;
import com.fulong.longcon.site.SiteTemplate;

/**
 * 如果当前网站已经存在，则进入网站的编辑页面，否则提示用户网站不存在。
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * 
 * @author lixf
 * @version 1.0
 */
public class IndexAction extends SiteBaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*String domain = request.getServerName();
		Site site = this.getSiteFactory(request).getSite(domain);
		if (site == null) {
			site = this.getSiteFactory(request).getSite("localhost");
		}
		if(site == null){
			SiteTemplate template = this.getSiteFactory(request).getTemplate("root");
			site = this.getSiteFactory(request).createSite(domain, request.getUserPrincipal(), template);			
		}
		String path = "/sites/" + site.getTemplate().getName() + "/index.jsp";
		return this.forward(mapping, "success", path);*/
		return mapping.findForward("success");
	}

}
