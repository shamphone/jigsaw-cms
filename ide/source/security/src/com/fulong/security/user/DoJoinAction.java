package com.fulong.security.user;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Value;
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
public class DoJoinAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 无
	 * @param request 包含如下参数：
	 *     1. toGroupID：为所选择的要转译到的组ID
	 *     2. userID：为所选择要转译的用户ID
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
        String toGroupID = request.getParameter("toGroupID");
        Node group = this.getRepository(request).getNode(toGroupID);
        String[] userIDs = request.getParameterValues("userID");        
        if(group!=null){
        	Property memberProp = group.getProperty("member");
        	if(memberProp!=null){        		
				Value[] nodes = memberProp.getValues();
				List<Value> list = new ArrayList<Value>();
				Collections.addAll(list, nodes);
				if(userIDs!=null){
					for(int i=0;i<userIDs.length;i++){
	            		Node user = this.getRepository(request).getNode(userIDs[i]);
	            		if(user!=null){
	            			Value userValue = this.getRepository(request).getValueFactory().createValue(user);
	            			if(!list.contains(userValue)){
	            				list.add(userValue);
	            			}
	            		}
	            	}
				}
        		Value[] values = (Value[])list.toArray(new Value[list.size()]);
        		if(values.length>nodes.length){
        			group.setProperty("member", values);
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
