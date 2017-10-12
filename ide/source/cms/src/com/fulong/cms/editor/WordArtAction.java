package com.fulong.cms.editor;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.content.ContentBaseAction;
import com.fulong.cms.form.WordArtForm;
import com.fulong.longcon.repository.Node;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lichenghao
 * @version 3.1
 */
public class WordArtAction extends ContentBaseAction {

    protected Log log = LogFactory.getLog(this.getClass());

    public ActionForward doExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        WordArtForm myForm = (WordArtForm) form;
        String path = "";
        try {
            Node user = this.getCurrentOrg(request, response);
            String resourceName = this.generateWordArt(myForm.getText(), myForm.getEffect(), myForm.getFontFamily(), myForm.getFontSize(), myForm.isFontBold(), myForm.isFontItalic(), myForm.getLeft(), myForm.getTop(), user,request);
            path = request.getContextPath() + "/resources/" + user.getID() + "/" + resourceName;
        } catch (Exception ex) {
            log.error("Generate wordart failed", ex);
        } finally {
            response.getOutputStream().println ("<script>parent._Callback('" + path + "')</script>");
        }
        return null;
    }


    private String generateWordArt(String text, int msoPresetTextEffect, String fontName, float fontSize, boolean fontBold, boolean fontItalic, float left, float top, Node user,HttpServletRequest request) throws Exception {
    	File gif = this.getWordUtils(request).generateWordArtImage(text, msoPresetTextEffect, fontName, fontSize, fontBold, fontItalic, left, top);
        String newFileName = System.currentTimeMillis() + "" + (new Random()).nextInt(100) + ".gif";
        Node newResource = user.addNode(this.getRepository(request).getDefinitionManager().getDefinition("resource-scheme"), newFileName);
        newResource.setProperty("resource-content", new FileInputStream(gif));
        newResource.setProperty("mime", this.getServlet().getServletContext().getMimeType(newFileName));
        newResource.setProperty("createdTime", Calendar.getInstance());
        newResource.setProperty("length", newResource.getProperty("resource-content").getLength());
        return newResource.getName();
    }
}
