package com.fulong.site.clip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;
import com.fulong.longcon.site.ChannelTreeBuilder;
import com.fulong.longcon.site.SiteTemplate;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public class ClipsAction  extends ClipBaseAction {

    protected static final int DEPTH_TEMPLATE = 2;
    protected static final String BlANK_JSPF = "/site/jspf/blank.jspf";
    /**
     * definitionPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward templateExecute(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
    	String id = request.getParameter("ID");
    	if(id == null)
    		id =  NodeDefinition.PRINCIPAL_SCHEME;

        String categories[] = request.getParameterValues("categories");
        NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(this.
                getRepository(request).getDefinitionManager().getDefinition("principal-scheme"));
        Tree tree = builder.build();
        if (categories != null) {
            tree.diableAll();
            for (int i = 0; i < categories.length; i++) {
                tree.enable(categories[i]);
            }
        }
        request.setAttribute("definitionTree", tree);
        SiteTemplate site = this.getSiteFactory(request).getTemplate(request.getParameter("definition"));
        ChannelTreeBuilder cbuilder=new ChannelTreeBuilder(site);
        request.setAttribute("channelTree", cbuilder.build());
        request.setAttribute("site", site);

        return mapping.findForward("success");
    }
}
