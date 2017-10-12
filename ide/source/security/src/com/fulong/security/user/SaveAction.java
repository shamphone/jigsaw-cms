package com.fulong.security.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.security.SecurityBaseAction;
import com.fulong.security.form.UserForm;

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
public class SaveAction extends SecurityBaseAction {
	protected static final String DES_KEY = "fulong";

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 参见UserForm
	 * @param request 包含如下参数：
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
		NodeDefinition definition = repository.getDefinitionManager().getDefinition("principal-scheme");
		UserForm userform = (UserForm)form;
		String userID = userform.getUserID();
		
		//密码加密
		String pw = userform.getPassword();
		String enPW = "";
		if(pw!=null&&!pw.equals("")){
			DesEncrypter encrypter = new DesEncrypter(DES_KEY);
			enPW = encrypter.encrypt(pw);	
		}
		
		String[] roleAuths = userform.getRoleDefs();
		Node user = null;
		if(definition!=null){
			if(userID!=null&&!userID.equals("")){   //用户修改
				user = repository.getNode(userID);				
			}else{   //用户新增				
				user = this.getPassportProvider(request).getDefaultOrganization().addNode(definition, "contents");
				if(user!=null){					
					if(userform.getUsername()!=null&&!userform.getUsername().equals("")){
						user.setProperty("user-username", userform.getUsername());
					}
					
					//加入组
					String nodeID = userform.getNodeID();
					if(nodeID!=null&&!nodeID.equals("")){
						Node group = repository.getNode(nodeID);
						if(group!=null){
							Property memberProp = group.getProperty("member");
							if(memberProp!=null){
								Value userValue = this.getRepository(request).getValueFactory().createValue(user);
								Value[] nodes = memberProp.getValues();
								List<Value> list = new ArrayList<Value>();
								Collections.addAll(list, nodes);
								if(!list.contains(userValue)){
									list.add(userValue);
									Value[] values = (Value[])list.toArray(new Value[list.size()]);
									group.setProperty("member", values);
								}
							}
						}
					}
				}
			}
			if(user!=null){
				if(userform.getCommonname()!=null&&!userform.getCommonname().equals("")){
					user.setProperty("user-commonname", userform.getCommonname());
				}
				if(enPW!=null&&!enPW.equals("")){
					user.setProperty("user-password", enPW);
				}
				setRoleDefs(roleAuths,user,request);
				user.setProperty("createdTime", Calendar.getInstance());
			}
		}		
		return mapping.findForward("success");
	}
	
	protected void setRoleDefs(String[] roleDefs,Node user,HttpServletRequest request){
		NodeDefinition cmsRole = this.getRepository(request).getDefinitionManager().getDefinition("cmsMgrs");
		NodeDefinition siteRole = this.getRepository(request).getDefinitionManager().getDefinition("siteMgrs");
		NodeDefinition serviceRole = this.getRepository(request).getDefinitionManager().getDefinition("serviceMgrs");
		NodeDefinition processRole = this.getRepository(request).getDefinitionManager().getDefinition("processMgrs");
		NodeDefinition userRole = this.getRepository(request).getDefinitionManager().getDefinition("roleMgrs");
		if(roleDefs!=null&&user!=null){  //先清空这个用户在五个模型权限组中的分类所属关系，再重新添加分类所属关系
			if(cmsRole!=null){
				if(user.isNodeType("cmsMgrs")){
					user.removeMixin(cmsRole);
				}
			}
			if(siteRole!=null){
				if(user.isNodeType("siteMgrs")){
					user.removeMixin(siteRole);
				}
			}
			if(serviceRole!=null){
				if(user.isNodeType("serviceMgrs")){
					user.removeMixin(serviceRole);
				}
			}
			if(processRole!=null){
				if(user.isNodeType("processMgrs")){
					user.removeMixin(processRole);
				}
			}
			if(userRole!=null){
				if(user.isNodeType("roleMgrs")){
					user.removeMixin(userRole);
				}
			}
			for(int i=0;i<roleDefs.length;i++){
				NodeDefinition roleDef = this.getRepository(request).getDefinitionManager().getDefinition(roleDefs[i]);
				if(roleDef!=null){
					user.addMixinDefinition(roleDef);
				}
			}
		}
	}

}
