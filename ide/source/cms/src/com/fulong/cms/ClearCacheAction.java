/**
 * 
 */
package com.fulong.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.cache.Cache;
import com.fulong.common.cache.CacheFactory;
import com.fulong.longcon.repository.Node;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class ClearCacheAction   extends CMSBaseAction  {

	
	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String className = request.getParameter("class");
		Class obj = Node.class;
		if(className!=null)
			obj = Class.forName(className);
		CacheFactory factory = (CacheFactory)this.getBeanFactory().getBean("cacheFactory");
		Cache cache = factory.getCache(obj);
		if(cache!=null)
			cache.clear();		
		return mapping.findForward("success");
	}

}
