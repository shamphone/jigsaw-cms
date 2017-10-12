package com.fulong.lyvc.manage.conference;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.User;
import com.fulong.lyvc.handler.contact.LeavewordHandler;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.SendNoticeForm;
import com.fulong.lyvc.message.ConferenceNotice;
import com.fulong.lyvc.server.LyvcEventDispatcher;

/**
 * SendNoticeAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class SendNoticeAction extends BaseAction {

	/**
	 * 
	 * 发送会议通知
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SendNoticeForm form = (SendNoticeForm) actionForm;
		
		//TODO 获取会议id，以及会议通知的标题和内容
		String conferenceId = (String) request.getSession().getAttribute("conferenceId");
		String title = form.getTitle();
		String content = form.getContent();
		
		//获取会议及会议中用户信息
		this.setDomain(request);
		ConferenceManager manager = this.getConferenceManager();
		
		Conference conference = manager.getConference(conferenceId);
		Collection<User> members = conference.getMembers();
		
		//得到创建者id
		String leaserId = manager.getLeaserId();
		
		//获取bean，并进行初始化
		LyvcEventDispatcher dispatcher = (LyvcEventDispatcher) this.getWebApplicationContext().getBean("eventDispatcher");
		LeavewordHandler handler = (LeavewordHandler) dispatcher.eventHandlerMap.get(144);
		handler.setConferenceManager(this.domain);

		//创建会议通知
		ConferenceNotice message = new ConferenceNotice();
		message.setId("191");
		message.conferenceId = conference.getId();
		message.title = title;
		message.content = content;
		message.sendDate = new Date(System.currentTimeMillis());
		
		//发送会议通知
		for(User user: members) {
			boolean flag = handler.sendMessage(user, message);
			if (!flag) {
				title = "会议通知";
				content = message.toXML();
				Date saveDate = message.sendDate;
				
				Message leaveword = manager.createMessage(leaserId, title, content, saveDate);
				user.addMessage(leaveword.getId());
			}
		}
		
		//从session中删除保存的变量
		request.getSession().removeAttribute("conferenceId");
		
		return mapping.findForward("success");
	}
}
