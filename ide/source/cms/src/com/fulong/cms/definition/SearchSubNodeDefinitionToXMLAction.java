package com.fulong.cms.definition;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.cms.CMSAjaxAction;
import com.fulong.cms.form.SchemaForm;
import com.fulong.common.util.ParameterSet;
import com.fulong.common.util.Tree;
import com.fulong.common.util.TreeNode;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0.1
 */
public class SearchSubNodeDefinitionToXMLAction extends CMSAjaxAction {

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
    public Document renderXML(HttpServletRequest request,
                              HttpServletResponse response) throws
            Exception {
        SchemaForm schemaForm = new SchemaForm();
        schemaForm.setParentID(request.getParameter("ID") == null ?
                               NodeDefinition.DICT_SCHEME : request.getParameter("ID"));
        String categories[] = request.getParameterValues("categories");
        NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(this.
                getRepository(request).getDefinitionManager().getDefinition(schemaForm.
                getParentID()));
        Tree tree = builder.buildPartTree();
        if (categories != null) {
            tree.diableAll();
            for (int i = 0; i < categories.length; i++) {
                tree.enable(categories[i]);
            }
        }
        Iterator it=tree.getNodes().iterator();
        ParameterSet set = new ParameterSet();
        while(it.hasNext()){
            NodeDefinition nd=(NodeDefinition)(((TreeNode)it.next()).getNode());
            if(!nd.getID().equals(schemaForm.getParentID())){
                set.put(nd.getID(), nd.getName());
            }
        }
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        return set.toDocument();

    }
}
