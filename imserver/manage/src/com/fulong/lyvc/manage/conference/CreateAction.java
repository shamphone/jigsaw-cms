package com.fulong.lyvc.manage.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.CreateConferenceForm;
import com.fulong.lyvc.util.UserComparator;

/**
 * CreateAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-1
 */

public class CreateAction extends BaseAction {

	/**
	 * 
	 * 初始化create.jsp页面的部分内容（创建正式会议）
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		ConferenceManager manager = this.getConferenceManager();
		
		String groupId = request.getParameter("groupId");
		
		if(groupId == null || groupId.equals("")) {
			Group group = manager.getCommonContactGroup();
			
			//获取所有的公共联系人组
			Collection<Group> groups = new ArrayList<Group>();
//			groups.add(group);
			groups.addAll(getAllChildren(group));
			request.getSession().setAttribute("groups", groups);
			
			UserComparator comparator = new UserComparator();

			List<User> users = new ArrayList<User>();
			if(groups.size() > 0)
				users = (List<User>) groups.iterator().next().users();
			Collections.sort(users, comparator);
			
			request.getSession().setAttribute("users", users);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			long time1 = System.currentTimeMillis() + (long)(30*60*1000);
			long time2 = time1 + (long)(60*60*1000);
			Date startTime = new Date(time1);
			Date endTime = new Date(time2);
			
			CreateConferenceForm form = (CreateConferenceForm) actionForm;
			form.setStartTime(format.format(startTime));
			form.setEndTime(format.format(endTime));
			
			//获取所有的公共联系人
			List<User> members = new ArrayList<User>();
			for(Group temp : groups) {
				members.addAll(temp.users());
			}
			
//			UserComparator comparator = new UserComparator();
			Collections.sort(members, comparator);
			
			request.getSession().setAttribute("members", members);
		}
		else {
			//保存页面刷新时的信息
			String members = request.getParameter("participants");
			String[] participants = members.split(",");

			CreateConferenceForm form = (CreateConferenceForm) actionForm;
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
