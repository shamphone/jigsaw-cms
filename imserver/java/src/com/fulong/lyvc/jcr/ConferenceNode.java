package com.fulong.lyvc.jcr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.QueryManager;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Document;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.ModeRole;
import com.fulong.lyvc.User;

/**
 * ConferenceNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-8
 */

public class ConferenceNode extends NodeWrapper implements Conference {
	
	private static final String TITLE = "title";						//会议名称
	private static final String CREATOR = "creator";					//创建者
	private static final String HOST = "5";								//主持人
	private static final String PARTICIPANT = "6";						//参与者
	private static final String STARTTIME = "startTime";				//开始时间
	private static final String ENDTIME = "endTime";					//结束时间
	private static final String DESC = "desc";							//会议描述
	private static final String DOCUMENT = "document";					//文档
	
	private ConferenceManager manager;
	private Vector<User> participants;		//会议中的临时用户（正在会议中的用户）

	public ConferenceNode(ConferenceManager manager, Node node) {
		super(node);
		this.manager = manager;
	}

	/**
	 * 该方法用来在会议中添加某种角色的用户
	 *
	 */
	public boolean addMember(String modeRoleID, String userID) {
		
		//该方法效率低点，但方法正确
//		boolean flag = true;
//		
//		String[] roles = this.getNode().getProperty(modeRoleID).getArray();
//		for(String role : roles) {
//			if(role.equals(userID)) {
//				flag = false;
//				break;
//			}
//		}
//		
//		if(flag) {
//			String[] newRoles = new String[roles.length+1];
//			System.arraycopy(roles, 0, newRoles, 0, roles.length);
//			newRoles[newRoles.length-1] = userID;
//			this.getNode().getProperty(modeRoleID).setValue(newRoles);
//		}
		
		
		//该方法效率高
		Repository repository = this.getNode().getRepository();
		QueryManager qm = repository.getQueryManager();
		Query query = qm.createQuery(this.getNode().getDefinition(), Query.SQL);
		query.filterByProperty(modeRoleID, userID);
		NodeIterator<Node> iterator = query.nodes();
		
		//先判断是否已经添加该用户
		boolean flag1 = false;
		String nodeId = this.getNode().getID();
		while(iterator.hasNext()){
			Node node = iterator.next();
			if(node != null && node.getID().equals(nodeId)) {
				flag1 = true;
				break;
			}
		}
		
		boolean flag2 = false;
		if(!flag1) {
			String[] roles = this.getNode().getProperty(modeRoleID).getArray();
			String[] newRoles = new String[roles.length+1];
			System.arraycopy(roles, 0, newRoles, 0, roles.length);
			newRoles[newRoles.length-1] = userID;
			this.getNode().getProperty(modeRoleID).setValue(newRoles);
			flag2 = true;
		}
		
		return flag2;
	}

	/**
	 * 该方法用来得到会议中所有的预先设定的成员，不包括创建者
	 *
	 */
	public Collection<User> getMembers() {
		Collection<User> collection = new ArrayList<User>();
		
		//获取参会人员
		Property property = this.getNode().getProperty(PARTICIPANT);
		if(property != null) {
			String[] participants = property.getArray();
			for(String participant : participants) {
				if(participant != null) {
					Node node = this.getNode().getRepository().getNode(participant);
					if(node != null) {
						User user = new UserNode(node);
						collection.add(user);
					}
				}
			}
		}
		
		//获取主持人
		property = this.getNode().getProperty(HOST);
		if(property != null) {
			String host = property.getString();
			if(host != null) {
				Node node = this.getNode().getRepository().getNode(host);
				if(node != null) {
					User user = new UserNode(node);
					//如果主持人也是参会人，只获取一次
					if(!collection.contains(user))
						collection.add(user);
				}
			}
		}
		
		
			
		return collection;
	}

	/**
	 * 该方法用来得到会议中具有某种角色的所有预先设定的成员
	 *
	 */
	public Collection<User> getMembers(ModeRole role) {
		Collection<User> users = new ArrayList<User>();
		
		String[] roles = this.getNode().getProperty(role.getId()).getArray();
		for(String r : roles) 
			users.add(manager.getUser(r));
		
		return users;
	}
	
	/**
	 * 该方法用来得到会议中具有某种角色的所有预先设定的成员
	 *
	 */
	public Collection<User> getMembers(String roleId) {
		Collection<User> users = new ArrayList<User>();
		
		Property property = this.getNode().getProperty(roleId);
		if(property != null) {
			String[] roles = property.getArray();
			for(String r : roles) 
				users.add(manager.getUser(r));
		}
		
		return users;
	}

	/**
	 * 该方法用来判断某个用户是否是会议中的成员
	 *
	 */
	public boolean isMember(String userID) {
		boolean flag = false;
		
		Collection<User> users = getMembers();
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user != null && user.getId().equals(userID)) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}

	/**
	 * 该方法用来根据用户id获取其角色
	 * ModeRole是一个PropertyDefinition，而不是作为一个Node
	 *
	 */
	public ModeRole getMemberRole(String userID) {
		ModeRole role = null;
		String roleID = "";
		boolean flag = false;
		
		Mode mode = this.getMode();
		Collection<ModeRole> roles = mode.getRoles();
		Iterator<ModeRole> iterator = roles.iterator();
		while(iterator.hasNext()) {
			ModeRole temp = iterator.next();
			String id = temp.getId();
			String[] users = this.getNode().getProperty(id).getArray();
			for(String user : users) {
				if(user != null && user.equals(userID)) {
					flag = true;
					roleID = id;
					break;
				}
			}
			
			if(flag)
				break;
		}
		
		if(flag)
			role = mode.getRole(roleID);
		
		return role;
	}

	/**
	 * 该方法用来根据用户id删除会议中的某个用户（预先设定的用户）
	 *
	 */
	public void removeMember(String userID) {
		Property property = this.getNode().getProperty(HOST);
		if(property != null) {
			String host = property.getString();
			if(host!= null && host.equals(userID)) {
				property.setValue((String)null);
				
				return;
			}
		}
		
		property = this.getNode().getProperty(PARTICIPANT);
		if(property != null) {
			String[] users = property.getArray();
			
			if(users.length > 1) {
				String[] newUsers = new String[users.length-1];

				int i = 0;
				for(String temp : users) {
					if(temp != null && !temp.equals(userID)) 
						newUsers[i++] = temp;
				}
				
				property.setValue(newUsers);
			}
			else {
				property.setValue((String)null);
			}
		}
		
	}

	/**
	 * 该方法用来向会议中添加文档
	 *
	 */
	public Document addDocument(String path) {
		String[] docs = this.getNode().getProperty(DOCUMENT).getArray();
		String[] newdocs = new String[docs.length+1];
		System.arraycopy(docs, 0, newdocs, 0, docs.length);
		newdocs[newdocs.length-1] = path;
		
		this.getNode().setProperty(DOCUMENT, newdocs);
		
		DocumentNode doc = null;
		Node node = this.getNode(path);
		if(node != null)
			doc = new DocumentNode(node);
		
		return doc;
	}

	/**
	 * 该方法用来删除会议中的文档
	 *
	 */
	public void removeDocument(String path) {
		String[] docs = this.getNode().getProperty(DOCUMENT).getArray();
		
		String[] newdocs;
		if(docs.length > 1) {
			newdocs = new String[docs.length-1];
			int k = 0;
			for(String doc : docs) {
				if(doc != null && !doc.equals(path))
					newdocs[k++] = doc;
			}
			
			this.getNode().setProperty(DOCUMENT, newdocs);
		}
		else {
			this.getNode().setProperty(DOCUMENT, (String)null);
		}
	}

	public Mode getMode() {
		//TODO
		String modelId = this.getDefinition().getID();
		modelId = modelId.substring(modelId.length()-1);
		Mode mode = this.manager.getMode(modelId);
		
		return mode;
	}

	/**
	 * 该方法用来获取会议的id
	 *
	 */
	public String getId() {
		return this.getNode().getID();
	}
	
	/**
	 * 该方法用来获取会议的模式id
	 *
	 */
	public String getConferecneModelID() {
		//TODO
		return this.getMode().getId();
	}
	
	/**
	 * 该方法用来获取会议的描述
	 *
	 */
	public String getDesc() {
		String desc = null;
		
		Property property = this.getNode().getProperty(DESC);
		if(property != null) 
			desc = property.getString();
		
		return desc;
	}

	/**
	 * 该方法用来获取会议的结束时间
	 *
	 */
	public Date getEndTime() {
		Date endTime = null;
		
		Property property = this.getNode().getProperty(ENDTIME);
		if(property != null) {
			Calendar c = property.getDate();
			if(c != null)
				endTime = c.getTime();
		}
		
		return endTime;
	}

	/**
	 * 该方法用来获取会议的开始时间
	 *
	 */
	public Date getStartTime() {
		Date startTime = null;
		
		Property property = this.getNode().getProperty(STARTTIME);
		if(property != null) {
			Calendar c = property.getDate();
			if(c != null)
				startTime = c.getTime();
		}
		
		return startTime;
	}

	/**
	 * 该方法用来获取会议的标题
	 *
	 */
	public String getTitle() {
		String title = null;
		
		Property property = this.getNode().getProperty(TITLE);
		if(property != null) 
			title = property.getString();
		
		return title;
	}
	
	/**
	 * 该方法用来获取会议的创建者的id
	 *
	 */
	public String getCreatorId() {
		String creatorId = null; 
		
		Property property = this.getNode().getProperty(CREATOR);
		if(property != null) 
			creatorId = property.getString();
		
		return creatorId;
	}

	/**
	 * 该方法用来设置会议的描述
	 *
	 */
	public void setDesc(String desc) {
		Property property = this.getNode().getProperty(DESC);
		if(property != null)
			property.setValue(desc);
	}

	/**
	 * 该方法用来设置会议的结束时间
	 *
	 */
	public void setEndTime(Date endTime) {
		if(endTime == null)
			return ;
		
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		
		Property property = this.getNode().getProperty(ENDTIME);
		if(property != null)
			property.setValue(c);
	}

	/**
	 * 该方法用来设置会议的开始时间
	 *
	 */
	public void setStartTime(Date startTime) {
		if(startTime == null)
			return ;
		
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		
		Property property = this.getNode().getProperty(STARTTIME);
		if(property != null)
			property.setValue(c);
	}

	/**
	 * 该方法用来设置会议的标题
	 *
	 */
	public void setTitle(String title) {
		Property property = this.getNode().getProperty(TITLE);
		if(property != null)
			property.setValue(title);
	}
	
	/**
	 * 该方法用来设置会议的创建者
	 *
	 */
	public void setCreatorId(String id) {
		Property property = this.getNode().getProperty(CREATOR);
		if(property != null)
			property.setValue(id);
	}

	/**
	 * 该方法用来得到会议的创建者
	 *
	 */
	public User getCreator() {
		//创建者是会议的引用属性
		Property property = this.getNode().getProperty(CREATOR);
		
		if(property != null) {
			String creatorId = property.getString();
			Node creatorNode = this.getNode().getRepository().getNode(creatorId);
			
			if(creatorNode != null)
				return new UserNode(creatorNode);
		}
			
		return null;
	}

	/**
	 * 该方法用来获取会议中的一个文档，通过文档的id获取
	 *
	 */
	public Document getDocument(String path) {
		return documents().get(path);
	}

	/**
	 * 该方法用来获取会议中的所有文档
	 *
	 */
	public Collection<Document> getDocuments() {
		return documents().values();
	}

	/**
	 * 该方法用来得到会议中的所有文档
	 *
	 */
	private Map<String, Document> documents() {
		Map<String, Document> map = new HashMap<String, Document>();
		
		Property property = this.getNode().getProperty(DOCUMENT);
		if(property != null) {
			String[] documents = property.getArray();
			for(String doc : documents) {
				if(doc != null) {
					Node node = this.getNode(doc);
					if(node != null) {
						DocumentNode documentNode = new DocumentNode(node);
						map.put(node.getPath(), documentNode);
					}
				}
			}
		}
		
		return map;
	}

	/**
	 * 该方法用来获取会议的所有临时参与者
	 *
	 */
	public Collection<User> getParticipants() {
		return participants;
	}

	/**
	 * 该方法用来判断会议是否正在举行进行
	 *
	 */
	public boolean isHolding() {
		boolean flag = false;
		
		//开始时间在当前时间之前，结束时间在当前时间之后
		if ((getStartTime() != null) && getStartTime().before(new Date()) && getEndTime() != null && getEndTime().after(new Date()))
			flag = true;
		
		return flag;
	}
	
	/**
	 * 该方法用来判断会议是否已经开始
	 *
	 */
	public boolean isStarted() {
		boolean flag = false;
		
		//开始时间在当前时间之前
		if ((getStartTime() != null) && getStartTime().before(new Date()))
			flag = true;
		
		return flag;
	}
	
	/**
	 * 该方法用来判断会议是否已经结束
	 *
	 */
	public boolean isEnded() {
		boolean flag = false;
		
		//结束时间在当前时间之前
		if (getEndTime() != null && getEndTime().before(new Date()))
			flag = true;
		
		return flag;
	}

	/**
	 * 该方法用来向会议中添加一个临时参与者，不能重复添加同一个用户
	 * 创建正式会议时添加的用户，加入会议中时调用该方法
	 * 在正式会议中邀请其他用户（可以为正式会议中的用户，也可以为临时邀请的）加入会议，也调用该方法
	 * 创建即时会议，加入会议的用户也调用该方法
	 *
	 */
	public boolean join(User user) {
		boolean flag = false;
		
		if(!participants.contains(user)) {
			flag = true;
			participants.add(user);
		}
		
		return flag;
	}

	/**
	 * 该方法用来删除会议的一个临时参与者，同一个用户不能多次离开
	 * 正式会议和即时会议中的用户退出会议时，都会调用该方法
	 *
	 */
	public boolean leave(User user) {
		boolean flag = false;
		
		if(participants.contains(user)) {
			flag = true;
			participants.remove(user);
		}
			
		return flag; 
	}

	/**
	 * 该方法用来启动一个会议
	 * 创建一个会议后需要调用该方法，才能开始会议（作用：初始化变量participants）
	 * 现在的实现改为在构造方法中进行初始化
	 * 
	 */
	public void start() {
		participants = new Vector<User>();
	}

	/**
	 * 该方法用来结束会议
	 *
	 */
	public void terminate() {
		participants = new Vector<User>();
	}

	public boolean equals(Object another) {
		if (another == null)
			return false;
		
		if (another instanceof ConferenceNode)
			return ((ConferenceNode) another).getId() == this.getId();
		
		return false;
	}

	public boolean addMember(String userID) {
		Mode mode = this.getMode();
		String defaultRoleId = "";
		for (ModeRole role : mode.getRoles()) {
			if (role.isDefault()) {
				defaultRoleId = role.getId();
				break;
			}
		}
		
		//无需判断用户是否存在（必然不存在）
		boolean flag = this.addMember(defaultRoleId, userID);
			
		//判断该用户是否已经在会议中
//		boolean isInConference = false;
//		for (User user : this.getMembers()) {
//			if (userID.equals(user.getId())) {
//				isInConference = true;
//				this.addMember(defaultRoleId, userID);
//				
//				break;
//			}
//		}
		
		return flag;
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
		// TODO Auto-generated method stub
		
	}
}
