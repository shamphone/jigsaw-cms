package com.fulong.common.servlet;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.DiskMultipartRequestHandler;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
@SuppressWarnings("deprecation")
public class MultipartRequestWrapper
    extends HttpServletRequestWrapper {
    @SuppressWarnings("unchecked")
	private Hashtable parameters;
    @SuppressWarnings({ "unchecked" })
	public MultipartRequestWrapper(HttpServletRequest request) throws
        FileUploadException, ServletException {
        super(request);
        if (request instanceof org.apache.struts.upload.MultipartRequestWrapper) {
            for(Enumeration names=request.getAttributeNames();names.hasMoreElements();){
                String name=(String)names.nextElement();
                Object obj=request.getAttribute(name);
                if(obj instanceof ActionForm){
                    ActionForm form=(ActionForm)obj;
                    MultipartRequestHandler handler=form.getMultipartRequestHandler();
                    this.parameters=handler.getAllElements();
                }
            }
        }else{
            org.apache.struts.upload.MultipartRequestWrapper wapper=new org.apache.struts.upload.MultipartRequestWrapper(request);
            DiskMultipartRequestHandler handler=new DiskMultipartRequestHandler();
            handler.handleRequest(wapper);
            this.parameters=handler.getAllElements();
        }
    }

    @SuppressWarnings("unchecked")
	public Map getParameterMap() {
        return this.parameters;
    }

    public String getParameter(String name) {
        String[] values = this.getParameterValues(name);
        if ( (values == null) || (values.length == 0))
            return null;
        return values[0];
    }

    public String[] getParameterValues(String name) {
        return (String[])this.parameters.get(name);
    }

}
