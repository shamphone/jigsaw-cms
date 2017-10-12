package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * 生成一个复合属性节点
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class InsertNodeAction extends BatchAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.dialog.DialogBaseAction#dialogPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doExecute(Node[] contents,
    		NodeDefinition category,
                                   ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
		String parentID = request.getParameter("parentID");
		String fixPropID = request.getParameter("fixPropID");
		String proID = request.getParameter("proID");
		String proValue = request.getParameter("prop");
		String childDef = request.getParameter("childDef");
		Node parent = this.getRepository(request).getNode(parentID);
		if(parent!=null){
			if(childDef!=null&&!childDef.equals("")){
				NodeDefinition def = this.getRepository(request).getDefinitionManager().getDefinition(childDef);
				if(def!=null){
					if(fixPropID!=null&&!fixPropID.equals("")){
						if(parent.getDefinition().getPropertyDefinition(fixPropID)!=null){							
							Node child = parent.addNode(def, fixPropID);
							if(proID!=null&&!proID.equals("")){
								if(child.getProperty(proID)!=null){
									child.setProperty(proID, proValue);
									request.setAttribute("oNode", child);
									request.setAttribute("proID", proID);
								}
							}						
						}
					}
				}
			}
		}
		return mapping.findForward("success");
	}

}
