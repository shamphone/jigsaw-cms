package com.fulong.cms.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.ResourceForm;
import com.fulong.longcon.repository.Node;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class AddAsynFileAction extends ResourceBaseAction {

	protected ActionForward resourcePerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        ResourceForm resourceForm = (ResourceForm) form;		
		String portletID = request.getParameter("portletID");
		String ownerType = request.getParameter("owner");
		
		Node owner = null;
		//会员节点父节点都为root by mali 2010-9-8
		if ("site".equalsIgnoreCase(ownerType)) {
			if(this.getCurrentSite(request, response)!=null){
				owner = this.getCurrentSite(request, response).getOwner();
			}			
		} else {
			owner = this.getCurrentOrg(request, response);
		}
		if (owner == null) {
			owner = this.getPassportProvider(request).getDefaultOrganization();
		}
		String path = this.uploadFile(resourceForm.getFile(0), owner, request, response,false);
		if(path!=null){
			request.setAttribute("path", path);
			request.setAttribute("fileName", FilenameUtils.getName(path));
		}
		request.setAttribute("portletID", portletID);
		return mapping.findForward("success");
	}

}
