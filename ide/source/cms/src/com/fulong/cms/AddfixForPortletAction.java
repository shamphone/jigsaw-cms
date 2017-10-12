package com.fulong.cms;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;

/**
 * 添加一个复合属性，占位符中用
 * 
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
public class AddfixForPortletAction extends CMSBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.CMSBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean ok = false;
		Writer writer = response.getWriter();
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
		
		String parentID = request.getParameter("parentID");
		String fixProID = request.getParameter("fixProID");
		String childID = request.getParameter("childID");
		if(parentID!=null&&!parentID.equals("")){
			Node parent = this.getRepository(request).getNode(parentID);
			if(parent!=null){
				if(childID!=null&&!childID.equals("")){
					Node child = this.getRepository(request).getNode(childID);
					if(child!=null){
						if(fixProID!=null&&!fixProID.equals("")){
							if(parent.getProperty(fixProID)!=null){
								child.setParent(parent);
								//child.setName(fixProID);
								ok = true;
							}
						}
					}
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
