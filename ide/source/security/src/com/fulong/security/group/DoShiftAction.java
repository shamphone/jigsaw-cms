package com.fulong.security.group;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.security.SecurityBaseAction;

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
 * @author liuzijun
 * @version 3.1
 */
public class DoShiftAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 无
	 * @param request 包含如下参数：
	 *     1. groupID：为所选择的要进行转移组的ID
	 *     2. toGroupID：为所选择的要转移到的组的ID
	 * @param response 操作成功返回true，操作失败返回false
	 * @return 无
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        Writer writer = response.getWriter();
        boolean ok = false;
        String groupID = request.getParameter("groupID");
        if(groupID!=null&&!groupID.equals("")&&!groupID.equals("root-group")){   //根组不能被移动
        	Node group = this.getRepository(request).getNode(groupID);
            String toGroupID = request.getParameter("toGroupID");
            Node toGroup = this.getRepository(request).getNode(toGroupID);            
            if(group!=null&&toGroup!=null){
            	try{
    	        	group.setParent(toGroup);
    	        	ok = true;
            	}catch(Exception e){
            		ok = false;
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
