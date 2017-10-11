package com.fulong.portlet.cms;

import java.io.File;
import java.io.IOException;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.FileUtils;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;


/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author jiangqi
 * @version 2.0
 */
public abstract class RepeaterEditRender extends PortletRender {
    private static final String TEMPLATE_FILE = "/portlet/cms/xrepeater/blank.jsp";
    public abstract ActionForward editRender(NodeDefinition def,
                                             ActionMapping mapping,
                                             ActionForm form,
                                             RenderRequest request, RenderResponse response) throws Exception;

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request RenderRequest
     * @param response RenderResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward render(
            ActionMapping mapping,
            ActionForm form,
            RenderRequest request,
            RenderResponse response) throws Exception {
        PortletPreferences preferences = request.getPreferences();
        NodeDefinition category = lookUpDefinition(request);
        request.getPreferences().setValue("category", category.getID());
        request.setAttribute("category", category);
        preferences.setValue("category", category.getID());
        request.setAttribute("preferences", request.getPreferences());
        return editRender(category, mapping, form, request, response);

    }

    protected String iniJspf(RenderRequest request, RenderResponse response)
			throws IOException, ReadOnlyException {
    	return this.iniJspf(request,response,"");
    }

    protected String iniJspf(RenderRequest request, RenderResponse response, String suf)
			throws IOException, ReadOnlyException {
		SiteTemplate template =this.getCurrentSiteTemplate(request, response); 
		String clipPath = getClipPath(request, response, suf);
        String realPath = template.getRealPath(clipPath);
        File file = new File(realPath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            FileUtils.copyFile(this.getBlankTemplateFile(), file);
        }
        return clipPath;
	}

    protected File getBlankTemplateFile() {
        return new File(this.portletContext.getRealPath(TEMPLATE_FILE));
    }
}
