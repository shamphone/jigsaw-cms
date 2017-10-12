package com.fulong.site.css;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @version 1.0
 */
public class CSSManagerAction extends CSSBaseAction {

    /**
     * cssPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.site.css.CSSBaseAction method
     */
    public ActionForward cssPerform(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws
            Exception {
    	String cssPath = request.getParameter("cssPath");
    	if(cssPath!=null&&cssPath.trim().length()!=0){
			File directory = new File(this.getServletContext().getRealPath(cssPath));
			if(directory.exists()){
    	        IOFileFilter filter = FileFilterUtils.andFileFilter(FileFilterUtils.makeSVNAware(null), FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".delete")));
    	        request.setAttribute("files", FileUtils.iterateFiles(directory, filter, null));
			}
    		request.setAttribute("cssPath", cssPath);
    	}
    	String cssName = request.getParameter("cssName");
    	if(cssName!=null&&cssName.trim().length()!=0){
    		request.setAttribute("fileName", cssName);
    	}
        return mapping.findForward("success");
    }
}
