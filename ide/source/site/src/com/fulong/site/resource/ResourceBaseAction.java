package com.fulong.site.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.site.SiteBaseAction;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


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
 * @author jiangqi
 * @version 1.0
 */
public abstract class ResourceBaseAction extends SiteBaseAction {
    /**
     * doPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
        return this.resourcePerform(mapping, form, request, response);
    }

    public abstract ActionForward resourcePerform(ActionMapping mapping,
                                                  ActionForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws
            Exception;



    protected boolean exists(FormFile file) {
        return (file != null)
                && (file.getFileName() != null)
                && (file.getFileName().length() > 0);
    }
    
	protected String uploadFile(FormFile values, HttpServletRequest request, HttpServletResponse response,boolean override) throws Exception {
		Node owner = null;
		if(this.getCurrentSite(request, response)!=null){
			owner = this.getCurrentSite(request, response).getOwner();
		}else {
			owner = this.getCurrentOrg(request, response);
		}
		if (owner == null) {
			owner = this.getPassportProvider(request).getDefaultOrganization();
		}
		return uploadFile(values, owner, request, response, override);
	}
	
	protected String uploadFile(FormFile values, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return uploadFile(values, request, response,false);
	}

	protected String uploadFile(FormFile values, Node owner, HttpServletRequest request, HttpServletResponse response,boolean override) throws Exception {
		if (values == null || values.getFileSize() == 0){
			return null;
		}
		String fileName = FilenameUtils.getName(values.getFileName());	
		Node root = owner.getNode("resources");
		if(root==null){
			root = owner.addNode(this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme"),"resources");
		}
		
		Node resource = null;
		if(override&&root.getNodes(fileName).getSize() > 0){
			
			resource = root.getNodes(fileName).next();
		}else{
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			while (root.getNodes(fileName).getSize() > 0) {		
				int pos = fileName.lastIndexOf(".");
				if (pos > 0)
					fileName = fileName.substring(0, pos) +  "_" + df.format(System.currentTimeMillis()) + fileName.substring(pos);
				else
					fileName = fileName + df.format(System.currentTimeMillis());
			}
			resource = root.addNode(this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme"), fileName);
		}
		
		resource.setProperty("resource-content", values.getInputStream());
		resource.setProperty("mime", this.getServlet().getServletContext().getMimeType(fileName.toLowerCase()));
		resource.setProperty("createdTime", Calendar.getInstance());
		resource.setProperty("length", resource.getProperty("resource-content").getLength());
		return resource.getPath();
	}
    
}
