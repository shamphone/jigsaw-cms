package com.fulong.lyvc.manage.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.ConferenceRepository;
import com.fulong.lyvc.EventDispatcher;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPEvent;

/**
 * BaseAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public abstract class BaseAction extends ActionSupport {
	
	protected String userId = "";
	protected String userName = "";
	protected String domain = "";
	private ConferenceManager manager;
	
	private static Log logger = LogFactory.getLog(BaseAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("userId") == null) {
			session.setAttribute("userId", "");
			session.setAttribute("userName", "");
			session.setAttribute("roleCode", "");
		}
		
		userId = (String) session.getAttribute("userId");
		userName = (String) session.getAttribute("userName");
		
		try {
			return doPerform(mapping, form, request, response);
			
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			
			return mapping.findForward("error");
		}
	}

	protected ConferenceManager getConferenceManager() {
		return this.manager;
	}
	

	public void setDomain(HttpServletRequest request) {
		StringBuffer baseURL = request.getRequestURL();
		String baseURI = request.getRequestURI();
		int index = baseURL.indexOf(baseURI);
		this.domain = baseURL.substring(7, index);
		
		ConferenceRepository conferenceRepository = (ConferenceRepository) this.getWebApplicationContext().getBean("conferenceRepository");
		this.manager = conferenceRepository.getConferenceManager(domain);
	}

	protected static final ActionForward forward(ActionMapping mapping, String name, Object[] objects) {
		ActionForward source = mapping.findForward(name);
		
		return new ActionForward(name, getRealPath(source.getPath(), objects), source.getRedirect());
	}

	private static final String getRealPath(String source, Object[] objects, String nullValue) {
		String realPath = source;
		
		int length = objects.length;
		for (int i = 0; i < length; i++) {
			String regex = "\\x7B" + i + "\\x7D";
			if (objects[i] == null) {
				realPath = source.replaceFirst(regex, nullValue);
			} else {
				realPath = source.replaceFirst(regex, objects[i].toString());
			}
		}
		
		return realPath;
	}

	private static final String getRealPath(String source, Object[] objects) {
		return getRealPath(source, objects, "");
	}
	
	/**
	 * 
	 * 获取一个组下的所有子组（包括每一层的子组）
	 */
	public static Collection<Group> getAllChildren(Group group) {
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.addAll(group.children());
		
		for(int i=0; i<groups.size(); i++) {
			groups.addAll(groups.get(i).children());
		}
		
		return groups;
	}

	//以下方法暂时没用：定义的局部变量并未使用
	public void notifyListenerRemoveUser(long id) throws Exception {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>removeUser</messagetype>");
		message.append("<userid>");
		message.append(id);
		message.append("</userid>");
		message.append("</message>");
	}

	public void notifyListenerAddUser(long id, String name, String passwd, String fname, String lname, String email) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>addUser</messagetype>");
		message.append("<userid>");
		message.append(id);
		message.append("</userid>");
		message.append("<accountname>");
		message.append(name);
		message.append("</accountname>");
		message.append("<password>");
		message.append(passwd);
		message.append("</password>");
		message.append("<firstname>");
		message.append(fname);
		message.append("</firstname>");
		message.append("<lastname>");
		message.append(lname);
		message.append("</lastname>");
		message.append("<email>");
		message.append(email);
		message.append("</email>");
		message.append("</message>");
	}

	public void notifyListenerSetUserPassword(long userId, String password) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>setUserPassword</messagetype>");
		message.append("<userid>");
		message.append(userId);
		message.append("</userid>");
		message.append("<password>");
		message.append(password);
		message.append("</password>");
		message.append("</message>");
	}

	public void notifyListenerAddGroup(long parentGroupId, long groupId, String groupName) throws SQLException, IOException {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>addGroup</messagetype>");
		message.append("<parentgroupid>");
		message.append(parentGroupId);
		message.append("</parentgroupid>");
		message.append("<groupid>");
		message.append(groupId);
		message.append("</groupid>");
		message.append("<groupname>");
		message.append(groupName);
		message.append("</groupname>");
		message.append("</message>");
	}

	public void notifyListenerModifyGroup(long groupId, String groupName, String groupDesc) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>modifyGroup</messagetype>");
		message.append("<groupid>");
		message.append(groupId);
		message.append("</groupid>");
		message.append("<groupname>");
		message.append(groupName);
		message.append("</groupname>");
		message.append("</message>");
	}

	public void notifyListenerRemoveGroup(long groupId) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>removeGroup</messagetype>");
		message.append("<groupid>");
		message.append(groupId);
		message.append("</groupid>");
		message.append("</message>");
	}

	public void notifyListenerAddGroupMember(long groupId, long userId) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>addGroupMember</messagetype>");
		message.append("<userid>");
		message.append(userId);
		message.append("</userid>");
		message.append("<groupid>");
		message.append(groupId);
		message.append("</groupid>");
		message.append("</message>");
	}

	public void notifyListenerDelGroupMember(long groupId, long memberId) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>delGroupMember</messagetype>");
		message.append("<userid>");
		message.append(memberId);
		message.append("</userid>");
		message.append("<groupid>");
		message.append(groupId);
		message.append("</groupid>");
		message.append("</message>");
	}

	public void notifyListenerMoveGroupMember(long fromGroupId, long toGroupId, long memberId) {
		StringBuffer message = new StringBuffer();
		
		message.append("<message>");
		message.append("<messagetype>moveGroupMember</messagetype>");
		message.append("<fromgroupid>");
		message.append(fromGroupId);
		message.append("</fromgroupid>");
		message.append("<togroupid>");
		message.append(toGroupId);
		message.append("</togroupid>");
		message.append("<memberid>");
		message.append(memberId);
		message.append("</memberid>");
		message.append("</message>");
	}

	protected void broadcast(String message) {
		EventDispatcher.getInstance().addEvent(TCPEvent.conferenceEvent(message));
	}

	protected abstract ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
