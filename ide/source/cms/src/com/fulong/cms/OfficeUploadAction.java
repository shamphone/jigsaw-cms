package com.fulong.cms;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.ResourceForm;

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
 * @date 2010-8-18	
 * @version 1.0.1
 */
public class OfficeUploadAction extends CMSBaseAction {

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResourceForm resourceForm = (ResourceForm) form;	
		String path = this.uploadFile(resourceForm.getFile(0), request, response,true);
		if(path==null){
			return null;
		}
		response.setContentType("text/xml");
		response.setHeader("Content-Type", "text/xml; charset=UTF-8");
		Writer writer = response.getWriter();
		writer.append(path);
		writer.close();
		return null;
	}

}
