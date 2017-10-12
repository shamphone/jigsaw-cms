package com.fulong.lyvc.manage.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Document;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.EditConferenceForm;
import com.fulong.lyvc.util.UserComparator;

/**
 * EditAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class EditAction extends BaseAction {

	/**
	 * 
	 * 初始化edit.jsp页面的部分内容（修改正式会议）
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);
		
		ConferenceManager manager = this.getConferenceManager();

		String groupId = request.getParameter("groupId");
		
		if(groupId == null || groupId.equals("")) {
			//获取会议id并将会议id保存在session中
			String conferenceId = request.getParameter("conId");		//客户端参数为conId
			request.getSession().setAttribute("conferenceId", conferenceId);
			Conference conference = manager.getConference(conferenceId);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			//获取会议基本信息
			String title = conference.getTitle();
			String desc = conference.getDesc();
			String startTime = format.format(conference.getStartTime());
			String endTime = format.format(conference.getEndTime());
			
			//获取参会人员
			List<User> members = (List<User>) conference.getMembers("6");
			String[] participants = new String[members.size()];
			int i = 0;
			for(User user : members) {
				participants[i++] = user.getId();
			}
			request.getSession().setAttribute("participants", participants);
			
			//获取主持人
			Collection<User> hosts = conference.getMembers("5");
			String host = "";
			for(User temp : hosts)
				host = temp.getId();
			User user = manager.getUser(host);
			request.getSession().setAttribute("host", host);
			
			//获取公共联系人根组
			Group group = manager.getCommonContactGroup();
			
			//获取所有的公共联系人组
			Collection<Group> groups = new ArrayList<Group>();
//			groups.add(group);
			groups.addAll(getAllChildren(group));
			request.getSession().setAttribute("groups", groups);
			
			//获取主持人所在的公共联系人组
			for(Group temp : groups) {
				if(temp.isMember(user)) {
					group = temp;
					break;
				}
			}
			
			//获取会议中的文档
			Collection<Document> documents = conference.getDocuments();
			request.getSession().setAttribute("documents", documents);
			
			//设置EditConferenceForm，用来初始化页面
			EditConferenceForm form = (EditConferenceForm) actionForm;
			form.setGroupId(group.getId());
			form.setTitle(title);
			form.setDesc(desc);
			form.setStartTime(startTime);
			form.setEndTime(endTime);
			form.setUserId(user.getId());
			form.setParticipants(participants);
			
			UserComparator comparator = new UserComparator();

			List<User> users = (List<User>) group.users();
			Collections.sort(users, comparator);
			
			request.getSession().setAttribute("users", users);
			
			//获取所有公共联系人
			members = new ArrayList<User>();
			for(Group temp : groups)
				members.addAll(temp.users());
			
			Collections.sort(members, comparator);
			
			request.getSession().setAttribute("members", members);
		}
		else {
			//保存页面刷新时的信息
			String members = request.getParameter("participants");
			String[] participants = members.split(",");
			
			EditConferenceForm form = (EditConferenceForm) actionForm;
			form.setParticipants(participants);
			
			Group group = manager.getGroup(groupId);
			List<User> users = (List<User>) group.users();
			
			UserComparator comparator = new UserComparator();
			Collections.sort(users, comparator);
			
			request.getSession().setAttribute("users", users);
		}
		
		return mapping.findForward("success");
	}

}
