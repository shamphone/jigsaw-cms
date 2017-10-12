/**
 * 
 */
package com.fulong.portlet.acegi;

import org.springframework.security.AccessDecisionManager;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.InsufficientAuthenticationException;

import com.fulong.longcon.security.PassportPrincipal;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 *
 * @version 2.0
 */
public class PortalAccessDecisionManager  implements AccessDecisionManager{


	public void decide(Authentication authentication, Object object,
			ConfigAttributeDefinition config) throws AccessDeniedException,
			InsufficientAuthenticationException {
		//FilterInvocation invocation= (FilterInvocation)object;
		//系统管理员
		for (int i = 0; i < authentication.getAuthorities().length; i++) {
			if(authentication.getAuthorities()[i].getAuthority()==PassportPrincipal.ROLE_ADMIN)
				return;
		}
		//处理栏目权限
		/*
		 * 在页面上通过标签来处理；
		Channel channel = (Channel)invocation.getHttpRequest().getAttribute(Channel.class.getName());
		if(channel==null)
			return;
		if(channel.isSecure()){
			if(!(authentication.getPrincipal() instanceof Principal)
			 || !channel.getAuthorization().isMember((Principal)authentication.getPrincipal()))
			throw new AccessDeniedException(authentication.getPrincipal().toString()+" is not allowed to access channel "+ channel.getDisplayName()+".");
		}		
		//处理节点权限
		//Node node = (Node)invocation.getHttpRequest().getAttribute("com.fulong.longcon.Content");
		 * 
		 */
	}

	public boolean supports(ConfigAttribute attribute) {
		 return true;
	}

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return true;
	}
	

}
