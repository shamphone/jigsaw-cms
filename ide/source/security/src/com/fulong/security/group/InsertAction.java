package com.fulong.security.group;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.security.SecurityBaseAction;
import com.fulong.security.form.GroupForm;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixiang
 * @version 3.1
 */
public class InsertAction extends SecurityBaseAction {
	protected static final String DES_KEY = "fulong";

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 参见GroupForm
	 * @param request 无
	 * @param response 无
	 * @return 无
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Repository repository = this.getRepository(request);
		NodeDefinition definition = repository.getDefinitionManager().getDefinition("group-scheme");
		GroupForm groupform = (GroupForm)form;
		String nodeID = groupform.getNodeID();
		Node group = null;
		if(definition!=null){
			if (nodeID!=null&&!nodeID.equals("null")&&!(nodeID).equals("")) {
				Node parent = this.getRepository(request).getNode(nodeID);
				if(parent!=null){
					//新增子组
					group = parent.addNode(definition, "childGroup");
				}
			}else{
		        //新增组
				Node parent = this.getRepository(request).getNode("root-group");
				if(parent!=null){
					group = parent.addNode(definition, "childGroup");
				}
			}
			if(group!=null){
				if(groupform.getGroupname()!=null&&!groupform.getGroupname().equals("")){
					group.setProperty("title", groupform.getGroupname().trim());
				}
			}	
		}
		request.setAttribute("group", group);
		return mapping.findForward("success");
	}
}