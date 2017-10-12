/**
 * 
 */
package com.fulong.cms.editor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.content.ContentBaseAction;

/**
 * 显示状态变量。Action处理时把状态保存到session中，本Action负责从session中获取该记录得知并发送到客户端。
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
public class StatusAction 	  extends ContentBaseAction {
		    public ActionForward doExecute(ActionMapping mapping,
		                                 ActionForm form,
		                                 HttpServletRequest request,
		                                 HttpServletResponse response) throws Exception {
		    	String key=request.getParameter("key");
		    	Object status=request.getSession().getAttribute(key);
		    	if(status==null)
		    		response.getWriter().write("");
		    	else
			    	response.getWriter().write(status.toString());
		    	response.flushBuffer();
		    	return null;
		    }
	  }
