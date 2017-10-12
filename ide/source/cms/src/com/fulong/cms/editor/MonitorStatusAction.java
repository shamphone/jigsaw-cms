package com.fulong.cms.editor;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.content.ContentBaseAction;
import com.fulong.common.BaseAction;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lichengzhao
 * @version 2.0
 */
public class MonitorStatusAction extends ContentBaseAction {

	protected Log log = LogFactory.getLog(this.getClass());
	private static final String STATUS_KEY = "importword";

	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String status = (String) request.getSession().getAttribute(STATUS_KEY);
		if (status != null) {
	        response.setContentType("text/html");
	        response.setHeader("Content-Type", "text/html; charset=UTF-8");
	        Writer writer = response.getWriter();
	        writer.append(status);
	        writer.close();
		}
        return null;
	}
}