package com.fulong.site.template;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @version 1.0.1
 */
public class SelectTemplateAction extends TemplateBaseAction {
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm aform,
			HttpServletRequest request,
			HttpServletResponse response) throws
			Exception {
			String type = request.getParameter("type");
			SiteFactory factory = this.getSiteFactory(request);
			if(type!=null&&type.equals("selectNavigateTemplate")){
				String[] templateIds = request.getParameterValues("templateId");
				Set<SiteTemplate> templates = new HashSet<SiteTemplate>();
				Set<String> resolutions = new HashSet<String>();
				if(templateIds!=null&&templateIds.length>0){
					for(int i=0;i<templateIds.length;i++){
						SiteTemplate template = factory.getTemplate(templateIds[i]);
						if(template!=null&&!template.getName().equals("default")){
							templates.add(template);
							resolutions.add(template.getResolution());
						}
					}
				}
				request.setAttribute("templates", templates);
				request.setAttribute("resolutions", resolutions);
				String[] defaultTemplateIds = request.getParameterValues("defaultTemplateId");
				if(defaultTemplateIds!=null&&defaultTemplateIds.length>0){
					request.setAttribute("defaultTemplateIds", defaultTemplateIds);
				}
				return mapping.findForward("selectNavigateTemplate");
			}else if(type!=null&&type.equals("selectTemplate")){
				List<SiteTemplate> templates = this.getTemplates(request); 
				request.setAttribute("templates", templates);
				String[] defaultTemplateIds = request.getParameterValues("defaultTemplateId");
				if(defaultTemplateIds!=null){
					request.setAttribute("defaultTemplateIds", defaultTemplateIds);
				}
				return mapping.findForward("selectTemplate");
			}else if(type!=null&&type.equals("selectTemplateBySite")){
				String domain = request.getParameter("domain");
				if(domain==null){
					domain = request.getServerName();
				}
				Site site = this.getSiteFactory(request).getSite(domain);
				if(site!=null){
					Set<SiteTemplate> templates = new HashSet<SiteTemplate>();
					String[] templateNames = site.getTemplates();
					for(int i=0;i<templateNames.length;i++){
						SiteTemplate template = this.getSiteFactory(request).getTemplate(templateNames[i]);
		  				if(template!=null){
		  					templates.add(template);
		  				}
					}
					request.setAttribute("templates", templates);	
				}
				return mapping.findForward("selectTemplateBySite");
			}
			return null;
	}
}
