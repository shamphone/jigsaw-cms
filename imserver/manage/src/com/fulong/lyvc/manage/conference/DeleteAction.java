package com.fulong.lyvc.manage.conference;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.MediaServer;
import com.fulong.lyvc.TCPServer;
import com.fulong.lyvc.User;
import com.fulong.lyvc.handler.server.LoginHandler;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.message.DeleteConference;

/**
 * DeleteAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class DeleteAction extends BaseAction {

	/**
	 * 
	 * 初始化delete.jsp页面的部分内容（删除正式会议）
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		ConferenceManager manager = this.getConferenceManager();
		
		//获取会议id
		String conferenceId = request.getParameter("conferenceId");
		Conference conference = manager.getConference(conferenceId);
		
		//设置删除会议的信息
		DeleteConference msgToSend = new DeleteConference();
		msgToSend.conId = conference.getId();
		
		//获取会议中的所有用户，包括主持人和参会人员
		Collection<User> users = conference.getMembers();
		
		//获取bean，并进行初始化
		LoginHandler loginHandler = new LoginHandler();
		TCPServer server = (TCPServer) this.getWebApplicationContext().getBean("lyvcServer");
		MediaServer mediaServer = (MediaServer) this.getWebApplicationContext().getBean("mediaServer");
//		loginHandler.setConferenceManager(manager);
		loginHandler.setConferenceServer(server);
		loginHandler.setMediaServer(mediaServer);
		
		//发送删除会议信息
		for(User user : users) {
			loginHandler.sendMessage(user, msgToSend);
		}
		
		//删除会议
		manager.delete(conference);
		
		return mapping.findForward("success");
	}
}
