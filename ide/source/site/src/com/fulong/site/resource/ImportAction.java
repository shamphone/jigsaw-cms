package com.fulong.site.resource;

import java.io.File;
import java.util.zip.ZipException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.fulong.common.FileUtils;
import com.fulong.common.watermark.ImageWatermark;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.resource.form.ResourceForm;

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
public class ImportAction extends ResourceBaseAction {
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
        SiteTemplate template =this.getSiteFactory(request).getTemplate(resourceForm.getTemplate());
        File folder = new File(template.getRealPath(resourceForm.getFolder()));
        
        for (int i = 0; i < ResourceForm.size; i++) {
            FormFile file = resourceForm.getFile(i);
            if (exists(file)) {
            	File temp = new File(folder, file.getFileName());
                FileUtils.write(temp, file.getInputStream());
                String upload = resourceForm.getUpload();
                //是否上传水印图片
                if (upload != null) {
                	ImageWatermark mark = new ImageWatermark();
                	//需要加水印的图片
                	String path = temp.getAbsolutePath();
                	//需要加水印文件的格式
                	String tmpName = path.substring(path.lastIndexOf(".") + 1,path.length());
                	//图片格式
                	String imgeArray [] = {"bmp","dib","gif","jfif","jpe","jpeg","jpg","png","tif","tiff","ico"};
                	for(int j = 0; j<imgeArray.length;j++){
                		//判断文件是否为图片文件
                		if( imgeArray [j].equals(tmpName.toLowerCase()) || imgeArray [j].equals(tmpName)){
                             try{
                            	 //给图片加水印图片
                                 mark.pressImage(path,
                                		         this.getServletContext().getRealPath("")+"\\common\\images\\shuiyin.gif",
                                                 0, 0, 1.0, 0.3F);
                                }catch(Exception e){
                                }
                		}
                    }
                }
            }
        }
        FormFile zip = resourceForm.getZip();
        if (exists(zip)) {
            try {
				FileUtils.unzip(folder, zip.getInputStream());
			} catch (ZipException ex) {
	            log.error(ex.getMessage());
	            ActionMessages messages = new ActionMessages();
	            messages.add("zip.bad", new ActionMessage("zip.bad"));
	            this.saveErrors(request, messages);
	            return this.forward(mapping, "fail", resourceForm.getFolder());
			}
        }
        return this.forward(mapping, "success", resourceForm.getFolder());
    }
}
