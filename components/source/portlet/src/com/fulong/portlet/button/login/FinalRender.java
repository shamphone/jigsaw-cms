package com.fulong.portlet.button.login;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.PortletRender;

/**
 * 保存按钮Final状态
 * 
 * Title: 龙驭门户引擎
 * 
 * Description: 龙驭网站内容管理系统核心引擎
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class FinalRender extends PortletRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath!=null&&!channelPath.equals("")&&channelPath.indexOf("/")>=0){
			Channel channel = this.parseChannel(channelPath);
			String channelCPath = channel.getContextPath();
			request.setAttribute("channel", channel);
			request.setAttribute("channelCPath", channelCPath);
		}
		
		request.setAttribute("preferences", request.getPreferences());
		
       	String processId=request.getPreferences().getValue("process", "blank");
		String activityId = request.getPreferences().getValue("activity", "begin");
		ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
		if(definition!=null){
			Activity activity = definition.getActivity(activityId);
			if(activity!=null){
				//将需要调用的活动保存在session中，在登入操作成功后将调用这个活动。
				//具体调用在PortalFilterSecurityInterceptor类中
				request.getPortletSession(true).setAttribute("com.fulong.login.activity", activity,PortletSession.APPLICATION_SCOPE);
			}
		}
				
		return mapping.findForward("success");
	}

}
