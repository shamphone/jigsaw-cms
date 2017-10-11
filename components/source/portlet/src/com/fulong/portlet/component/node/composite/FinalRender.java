package com.fulong.portlet.component.node.composite;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.Node;
import com.fulong.portlet.component.PortletConfig;
import javax.portlet.PortletPreferences;
import com.fulong.portlet.PortletRender;
import com.fulong.longcon.repository.NodeIterator;
import java.util.List;
import java.util.ArrayList;

/**
 * 复合节点选择占位符
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author liuzijun
 * @version 3.1
 */
public class FinalRender extends PortletRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		Node nodes[] = new Node[0];
		if (node != null) {
			NodeIterator<?> childs = node.getNodes(config.getPropertyId());
			List<Node> list = new ArrayList<Node>();
			while (childs.hasNext()) {
				Node tmp = childs.nextNode();
				if (tmp != null)
					list.add(tmp);
			}
			nodes = list.toArray(new Node[list.size()]);
			request.setAttribute("parentNode", node);
		}else{
			return mapping.findForward(NODONE);
		}
		request.setAttribute("preferences", preferences);
		request.setAttribute("values", nodes);
		return mapping.findForward("success");
	}
}
