package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * 
 * @author lishaobo
 * @version 2.0
 */
public abstract class ContentBaseAction extends CMSBaseAction  {
	private Log log = LogFactory.getLog(this.getClass());
    
    public abstract ActionForward doExecute(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
        Exception;

    public ActionForward doPerform(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception{
    	long timer = 0;
    	if(log.isTraceEnabled())
    		timer= System.currentTimeMillis();
    	ActionForward forward = doExecute(mapping, form, request, response);
    	if(log.isTraceEnabled())
    		log.trace("timer ["+(System.currentTimeMillis()-timer) + "]");
    	return forward;

    }

 

}
