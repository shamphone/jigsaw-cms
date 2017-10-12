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
import org.springframework.security.intercept.web.FilterInvocation;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.PassportPrincipal;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;

/**
 * 网站管理模块的权限控制， 仅允许系统管理员或者网站模板所有者访问
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
public class SMSAccessDecisionManager implements AccessDecisionManager{
	
	private SiteFactory siteFactory;
	
	public void setSiteFactory(SiteFactory siteFactory) {
		this.siteFactory = siteFactory;
	}
	
	public void setRepository(Repository repository) {
	}

	public void decide(Authentication authentication, Object object,
			ConfigAttributeDefinition config) throws AccessDeniedException,
			InsufficientAuthenticationException {
		//系统管理员
		for (int i = 0; i < authentication.getAuthorities().length; i++) {
			if(authentication.getAuthorities()[i].getAuthority()==PassportPrincipal.ROLE_ADMIN)
				return;
		}
		FilterInvocation invocation= (FilterInvocation)object;
		String server = invocation.getRequest().getServerName();
		Node owner = null;
		Site site =siteFactory.getSite(server);
		if(site!=null)
		owner = siteFactory.getSite(server).getOwner();
		
		if((owner!=null)&& owner.equals(authentication.getPrincipal()))
			return;
		throw new AccessDeniedException(authentication.getPrincipal().toString()+" is not owner of "+ server+" .");
		
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return true;
	}

}
