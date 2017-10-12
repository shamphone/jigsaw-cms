package com.fulong.cms;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;

/**
 * 删除复合属性节点
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
public class DelFixNodesAction extends CMSBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.CMSBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean ok = false;
		String[] IDs = request.getParameterValues("IDs");
		Writer writer = response.getWriter();
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        if(IDs!=null){
        	for(int i = 0; i<IDs.length; i++){
        		Node node = this.getRepository(request).getNode(IDs[i]);
        		if(node!=null){
        			this.getRepository(request).delete(node);
        			ok = true;
        		}
        	}
        }
        if(ok){
			writer.append("true");
		}else{
			writer.append("false");
		}
		writer.close();
		return null;
	}

}
