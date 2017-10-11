package com.fulong.portlet.count.complexNode;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.portlet.cms.ListContentPortletRender;


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
public class FinalRender extends ListContentPortletRender {

    /**
     * execute
     *
     * @param request RenderRequest
     * @param response RenderResponse
     * @throws Exception
     */
    public ActionForward render(ActionMapping mapping, ActionForm form,
                                RenderRequest request, RenderResponse response) throws
            Exception {
		PortletPreferences preferences = request.getPreferences();
		request.setAttribute("preferences", preferences);
		String prop = preferences.getValue("complexField", "");
		Node node = lookupNode(request, response);
		if(node==null){
			request.setAttribute("count", 0);
			return mapping.findForward("success");
		}
		NodeIterator<Node> children = node.getNodes(prop);
		/*PropertyDefinition pd = def.getPropertyDefinition(prop);
		//Query query = this.getRepository().getQueryManager().createQuery(pd.getReferenceDefinition(), Query.SQL);
		Query query = this.getRepository().getQueryManager().createQuery(def, Query.SQL);
		for(int i=0;i<pars.length;i++){
			FilterParser parser = this.getFilterParser(request, response);
			parser.parser(pars[i]);
			parser.addToQuery(query);
		}
		query.filterByKeywords(prop);
		//query.filterByRefed(prop, node.getID());
		RangeIterator<Node> contents = query.nodes();
		long size = contents.getSize();*/
		request.setAttribute("count", children.getSize());
		return mapping.findForward("success");

    }

}
