/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.site.resource;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.resource.form.ResourceForm;

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
 * @date 2010-11-30	
 * @version 1.0.1
 */
public class UploadLocalAction extends ResourceBaseAction {
    /**
     * resourcePerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward resourcePerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        ResourceForm resourceForm = (ResourceForm) form;
        String type = resourceForm.getUpload();
        if(ResourceForm.size>0){
        	FormFile file = resourceForm.getFile(0);
        	if (exists(file)) {
        		if("DB".equalsIgnoreCase(type)){
        			try {
						String path = this.uploadFile(file, request, response);
						request.setAttribute("filePath", path);
					} catch (Exception e) {
						return mapping.findForward("error");
					}
        		}else {
        			SiteTemplate template =this.getSiteFactory(request).getTemplate(resourceForm.getTemplate());
        			File folder = new File(template.getRealPath(resourceForm.getFolder()));
        			if(!folder.exists()){
        				folder.mkdir();
        			}
        			try {
        				File temp = new File(folder, file.getFileName());
                		FileUtils.write(temp, file.getInputStream());
                		request.setAttribute("filePath", "/"+template.getName()+resourceForm.getFolder()+"/"+file.getFileName());
        			} catch (Exception e) {
        				log.error(e.getMessage(), e);
        				return mapping.findForward("error");
        			}
        		}

        		
        	}
        }
        return mapping.findForward("success");
    }
}
