package com.fulong.lyvc.jcr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.User;
import com.fulong.lyvc.util.PropertyConstant;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * ConferenceRepository
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-09-08
 */

public class JCRConferenceManager implements ConferenceManager {

	private static final String ACCOUNTNAME = "user-username";		//用户名
	private static final String EMAIL = "mail"; 					//邮箱
	private static final String MESSAGE = "message";				//留言
	private static final String COMMONCONFERENCE = "CommonConference";			//创建的会议的节点名字
	private static final String INSTANTCONFERENCE = "InstantConference";			//创建的会议的节点名字
	private static final String USER = "content";
	
	private Repository repository;							//视频会议系统内容库
	private Node orgnization;
	private String leaserId;								//公共联系人组的id
	private Group commonContactGroup;						//公共联系人组
	private Map<String, Conference> commonConferences;		//用来保存所有正在进行的正式会议
	private Map<String, Conference> instantConferences;		//用来保存创建的所有即时会议
	
	public JCRConferenceManager(Repository repository, String leaserId) {
		this.repository = repository;
		this.leaserId = leaserId;
		
		init();
	}
	
	/**
	 * 初始化公共联系人组、普通会议等信息
	 */
	private void init(){
		commonConferences = new HashMap<String, Conference>();
		instantConferences = new HashMap<String, Conference>();
		
		orgnization = repository.getNode(leaserId);
		if(orgnization != null){
			//TODO 下面这段代码可能使系统变得不通用
			Node node = repository.getNode("root-group");
			if(node != null) {
				NodeIterator<Node> nodes = node.getNodes(PropertyConstant.groupName);
				if(nodes != null && nodes.hasNext()) {
					commonContactGroup = new SystemGroupNode(nodes.next());
				}
				else {
					nodes = orgnization.getNodes(PropertyConstant.groupName);
					if(nodes != null && nodes.hasNext()) 
						commonContactGroup = new SystemGroupNode(nodes.next());
					else
						commonContactGroup = new SystemGroupNode(orgnization);
				}
			}
			else {
				commonContactGroup = new SystemGroupNode(orgnization);
			}
			
			//初始化commonConferences
			initCommonConferences();
		}
	}
	
	/**
	 * 初始化所有正在进行的正式会议
	 */
	private void initCommonConferences() {
		NodeIterator<Node> iterator = orgnization.getNodes(COMMONCONFERENCE);
		while(iterator.hasNext()) {
			Node node = iterator.next();
			Conference con = new ConferenceNode(this, node);
			if(con.getStartTime().before( new Date()) && con.getEndTime().after(new Date())) {
				commonConferences.put(con.getId(), con);
				con.start();
			}
		}
	}
	
	/**
	 * 该方法用来创建一个即时会议
	 * 
	 * title可以为空和null，也可以为中文
	 * creator不能为null且必须存在
	 */
	public Conference createInstantConference(String title, User creator) {
		if(title == null)
			title = "";
		
		//若creator为null，或者creator不存在，则结果返回null
		if(creator == null || this.getUser(creator.getId()) == null)
			return null;
		
		//获取即时会议的节点类型的定义，然后创建一个该类型的节点
		Node ConferenceNode = orgnization.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.instantConferenceScheme), INSTANTCONFERENCE);
		ConferenceNode conference = new ConferenceNode(this, ConferenceNode);
		conference.setTitle(title);
		conference.setCreatorId(creator.getId());
		
		//将创建的即时会议保存到缓存中
		instantConferences.put(conference.getId(), conference);
		
		conference.start();
		
		return conference;
	}
	
	/**
	 * 该方法用来创建一个普通会议
	 *
	 * startTime和endTime不能为null
	 * creator不能为null且必须存在
	 */
	public Conference createNormalConference(String title, String creatorId, Date startTime, Date endTime, String desc) {
		//获取普通会议的节点类型的定义，然后创建一个该类型的节点
		Node ConferenceNode = orgnization.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.commonConferenceScheme), COMMONCONFERENCE);
		ConferenceNode conference = new ConferenceNode(this, ConferenceNode);
		conference.setTitle(title);
		conference.setCreatorId(creatorId);
		conference.setStartTime(startTime);
		conference.setEndTime(endTime);
		conference.setDesc(desc);
		
		return conference;
	}

	/**
	 * 该方法用来创建一个用户，用户信息保存在会员库中
	 *
	 * 各参数均可以为空，null，中文
	 * 
	 */
	public User createUser(String accountName, String password, String firstName, String lastName, String email) {
		//获取用户的节点类型的定义，然后创建一个该类型的节点
		Node userNode = orgnization.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), USER);
		UserNode user = new UserNode(userNode);
		user.setAccountName(accountName);
		user.setPassword(password);
//		user.setFirstName(firstName);
//		user.setLastName(lastName);
		user.setEmail(email);
		user.setName(lastName+firstName);
		
		return user;
	}

	/**
	 * 该方法用来删除一个正式会议
	 */
	public void delete(Conference conference) {
		if(conference != null) {
			Node node = repository.getNode(conference.getId());
			repository.delete(node);
		}
	}

	/**
	 * 该方法用来删除一个组
	 */
	public void delete(Group group) {
		if(group != null) {
			Node node = repository.getNode(group.getId());
			repository.delete(node);
		}
	}

	/**
	 * 该方法用来把用户从其参与的会议中删除，并删除用户本身
	 * 
	 */
	public void delete(User user) {
		//TODO 该方法基本没有用到，需要修改
		
		if(user == null)
			return ;
		
		//把用户从其参与的会议中删除（离开会议）
		
		//把用户从正式会议中删除
		Collection<Conference> list = commonConferences.values();
		for(Conference con : list) {
			con.leave(user);
		}
		
		//把用户从即时会议中删除
		list = instantConferences.values();
		for(Conference con : list) {
			con.leave(user);
		}
		
		//TODO 可能还有其它的针对该用户的清理操作要进行处理
		
		//把用户本身删除（删除该用户节点）
		Node node = this.repository.getNode(user.getId());
		if(node != null)
			this.repository.delete(node);
	}

	/**
	 * 该方法用来判断某个用户是不是公共联系人组的根组
	 */
	public boolean isAdministrator(User user){
		return leaserId.equals(user.getId());
	}

	/**
	 * 根据会议的id，得到该会议
	 */
	public Conference getConference(String conId) {
		Node node = this.repository.getNode(conId);
		
		//若conId不存在，则返回结果为null
		if(node != null) {
			//判断这个会议节点是否是该租户的
			if(node.getParent().getID().equals(leaserId)) {
				//返回正式会议
				Conference con = commonConferences.get(conId);
				if(con != null)
					return con;
				
				//返回即时会议
				con = instantConferences.get(conId);
				if(con != null)
					return con;
				
				con = new ConferenceNode(this, node);
				con.start();
				return con;
			}
		}

		return null;
	}
	
	/**
	 * 该方法用来得到公共联系人组
	 */
	public Group getCommonContactGroup(){
		return this.commonContactGroup;
	}

	/**
	 * 根据组的id，得到该组
	 */
	public Group getGroup(String groupId) {
		Group group = null;
		
		Node node = this.repository.getNode(groupId);
		
		//如果groupId不存在，返回null
		if(node != null) {
			//判断该节点是哪种类型
			boolean flag = node.getDefinition().isNodeType(SchemeConstant.contactGroupScheme);
			if(flag)
				group = new ContactGroupNode(node);
			else 
				group = new SystemGroupNode(node);
		}
		
		return group;
	}

	/**
	 * 该方法用来得到所有正在召开的普通会议
	 */
	public Collection<Conference> getHoldingConferences() {
		Collection<Conference> list = new ArrayList<Conference>();
		
		if(orgnization != null) {
			NodeIterator<Node> iterator = orgnization.getNodes(COMMONCONFERENCE);
			while(iterator.hasNext()) {
				Node node = iterator.next();
				ConferenceNode conference = new ConferenceNode(this, node);
				String conId = conference.getId();
				if(conference.getStartTime().before( new Date()) && conference.getEndTime().after(new Date())) {
					list.add(conference);
					
					if(!commonConferences.containsKey(conId)) {
						commonConferences.put(conId, conference);
					}
				}
				else {
					if(commonConferences.containsKey(conId)) {
						Conference con = commonConferences.get(conId);
						Collection<User> participants = con.getParticipants();
						if(participants != null && participants.size() == 0) {
							commonConferences.remove(conId);
						}
					}
				}
			}
		}
		
		return list;
	}

	/**
	 * 该方法用来得到某个用户参与的所有正在召开的普通会议
	 */
	public Collection<Conference> getHoldingConferences(User participant) {
		Collection<Conference> list = new ArrayList<Conference>();
		
		Collection<Conference> conList = commonConferences.values();
		for(Conference con : conList) {
			Collection<User> participants = con.getParticipants();
			if(participants != null && participants.contains(participant)) {
				list.add(con);
			}
		}
		
		return list;
	}
	
	/**
	 * 该方法用来得到某个用户正在参与的即时会议
	 */
	public Collection<Conference> getHoldingInstantConferences(User participant) {
		Collection<Conference> list = new ArrayList<Conference>();
		
		Collection<Conference> conList = instantConferences.values();
		for(Conference con : conList) {
			Collection<User> participants = con.getParticipants();
			if(participants.size() > 0 && participants.contains(participant)) {
				list.add(con);
			}
		}
		
		return list;
	}
	
	/**
	 * 添加即时会议
	 */
	public void addInstantConference(Conference con) {
		instantConferences.put(con.getId(), con);
	}
	
	/**
	 * 结束即时会议
	 */
	public void finishInstantConference(String conId) {
		instantConferences.remove(conId);
	}
	
	/**
	 * 该方法用来得到某个用户可以参与的普通会议
	 */
	public Collection<Conference> getNormalConferences(User participant) {
		Collection<Conference> list = new ArrayList<Conference>();
		
		NodeIterator<Node> iterator = orgnization.getNodes(COMMONCONFERENCE);
		while(iterator.hasNext()) {
			Node node = iterator.next();
			ConferenceNode conference = new ConferenceNode(this, node);
			Collection<User> members = conference.getMembers();
			if(members.size() > 0 && members.contains(participant)) {
				list.add(conference);
			}
		}
		
		return list;
	}
	
	/**
	 * 该方法用来得到某个用户收到的所有留言
	 */
	public Queue<String> getMessageStore(User user) {
		Queue<String> queue = new LinkedList<String>();
		
		String[] messages = user.getMessages();
		if(messages != null) {
			for(String messageId : messages) {
				Node node = this.repository.getNode(messageId);
				MessageNode message = new MessageNode(node);
				queue.add(message.getContent());
			}
			
			//删除该用户留言属性下的所有留言id
			user.deleteMessages();
		}
		
		return queue;
	}

	/**
	 * 该方法用来通过会议模式id得到会议的模式
	 */
	public Mode getMode(String modeId) {
		return JCRConferenceRepository.modes.get(modeId);
	}

	/**
	 * 该方法用来得到所有的会议模式
	 */
	public Collection<Mode> getModes() {
		return JCRConferenceRepository.modes.values();
	}

	/**
	 * 该方法用来得到所有预定义的会议模式
	 */
	public Collection<Mode> getPredefinedModes() {
		return getModes();
	}

	/**
	 * 通过用户的id，得到某个用户
	 */
	public User getUser(String userId) {
		Node node = repository.getNode(userId);
		
		//若userId不存在，则返回结果为null
		if(node != null) {
			return new UserNode(node);
		}

		return null;
	}

	/**
	 * 通过用户的accountName，得到某个用户
	 */
	public User getUserByAccountName(String accountName) {
		//用户名全局唯一，即所有机构的用户名都不相同
		Query query = this.repository.getQueryManager().createQuery(repository.getDefinitionManager().getDefinition(SchemeConstant.pricipalScheme), Query.SQL);
		query.filterByProperty(ACCOUNTNAME, accountName);
		NodeIterator<Node> iterator = query.nodes();
		if(iterator.hasNext()) {
			Node node = iterator.next();
			
			return new UserNode(node);
		}
		
		return null;
	}
	
	/**
	 * 通过用户的email，来获取用户
	 */
	public User getUserByEmail(String email) {
		//邮箱全局唯一，即所有机构的邮箱都不相同
		Query query = this.repository.getQueryManager().createQuery(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), Query.SQL);
		query.filterByProperty(EMAIL, email);
		NodeIterator<Node> iterator = query.nodes();
		if(iterator.hasNext()) {
			Node node = iterator.next();
			
			return new UserNode(node);
		}
		
		return null;
	}

	/**
	 * 该方法用来得到所有的用户
	 */
	public Collection<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		
		NodeIterator<Node> iterator = orgnization.getNodes(USER);
		while(iterator.hasNext()) {
			Node node = iterator.next();
			UserNode user = new UserNode(node);
			users.add(user);
		}
		
		return users;
	}

	/**
	 * 该方法用来创建一条留言
	 */
	public Message createMessage(String senderId, String title, String content, Date saveDate) {
		Node messageNode = orgnization.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.messageScheme), MESSAGE);
		MessageNode message = new MessageNode(messageNode);
		message.setTitle(title);
		message.setContent(content);
		message.setSenderid(senderId);
		message.setSaveDate(saveDate);
		
		return message;
	}
	
	/**
	 * 得到所有租户的正在进行的会议
	 */
	public Collection<Conference> getAllHoldingConferences() {
		Collection<Conference> list = new ArrayList<Conference>();
		
		Query query = this.repository.getQueryManager().createQuery(repository.getDefinitionManager().getDefinition(SchemeConstant.commonConferenceScheme), Query.SQL);
		NodeIterator<Node> iterator = query.nodes();
		while(iterator.hasNext()) {
			Node node = iterator.next();
			ConferenceNode conference = new ConferenceNode(this, node);
			if(conference.getStartTime().before( new Date()) && conference.getEndTime().after(new Date())) {
				list.add(conference);
			}
		}
		
		return list;
	}
	
	/**
	 * 获取租户节点id
	 */
	public String getLeaserId() {
		return this.leaserId;
	}
	
}
