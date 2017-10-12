package com.fulong.security.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
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
public class DeleteAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 无
	 * @param request 包含如下参数：
	 *     1. nodeID：为所选择用户所在组的ID
	 *     2. userID：为所选择要删除的用户ID
	 * @param response 无
	 * @return 无
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roleDefID = request.getParameter("nodeID");
		String[] userIDs = request.getParameterValues("userID");
		if(roleDefID!=null&&!roleDefID.equals("")){
			
			if(roleDefID.equals("principal-scheme")){  //若为所有用户下作删除，则删除此节点				
				NodeDefinition roleDef = this.getRepository(request).getDefinitionManager().getDefinition(roleDefID);
				if(roleDef!=null&&userIDs!=null){
					for(int i=0;i<userIDs.length;i++){
						Node user = this.getRepository(request).getNode(userIDs[i]);
						if(user!=null){
							this.getRepository(request).delete(user);
						}						
					}
				}
			}else{  //若不是在所有用户下作删除，则只删除此节点在当前组下的引用关系	
				Node group = this.getRepository(request).getNode(roleDefID);
				if(group!=null){
					Property memberProp = group.getProperty("member");
					if(memberProp!=null){
						Value[] nodes = memberProp.getValues();
						List<Value> list = new ArrayList<Value>();
						Collections.addAll(list, nodes);
						for(int i=0;i<userIDs.length;i++){
							Node user = this.getRepository(request).getNode(userIDs[i]);
							if(user!=null){
								Value userValue = this.getRepository(request).getValueFactory().createValue(user);
								if(list.contains(userValue)){
									list.remove(userValue);
								}
							}	
						}
						Value[] values = (Value[])list.toArray(new Value[list.size()]);
						group.setProperty("member", values);
					}
				}
			}
		}
		return null;
	}
}
