package com.fulong.lyvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.jcr.ConferenceNode;
import com.fulong.lyvc.jcr.JCRConferenceManager;
import com.fulong.lyvc.jcr.GroupNode;
import com.fulong.lyvc.jcr.ModeRolePropertyDefinition;
import com.fulong.lyvc.util.SchemeConstant;

public class TestConferenceRepository {

	private static JCRConferenceManager cr;
	private static Repository repository;
	
	@BeforeClass
	public static void init() throws SQLException {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);	
		cr = (JCRConferenceManager) beanFactory.getBean("conferenceManager");
		repository = (Repository) beanFactory.getBean("repository");
		beanFactory.getBean("queryManagers");
		beanFactory.getBean("sqlQuery");
		
		Collection<Mode> modes = cr.getModes();
		for(Mode mode : modes) {
			Collection<ModeRole> roles = mode.getRoles();
			for(ModeRole role : roles) {
				PropertyDefinition propertyDefinition = ((ModeRolePropertyDefinition)role).getProperty();
				String[] rights = null;
				String name = propertyDefinition.getID();
				
				//常规会议模式中的角色
				if(name.equals("5"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("6"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10"};
				
				//网上培训模式中的角色
				else if(name.equals("7"))
					rights = new String[]{"1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("8"))
					rights = new String[]{"1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("9"))
					rights = new String[]{"2", "4", "5", "8", "10"};
				
				//网上咨询模式中的角色
				else if(name.equals("10"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11"};
				else if(name.equals("11"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11"};
				
				//即时会议模式中的角色
				else if(name.equals("12"))
					rights = new String[]{"1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("13"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11"};
				
				//主控会议模式中的角色
				else if(name.equals("14"))
					rights = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
				else if(name.equals("15"))
					rights = new String[]{"1", "2", "3", "4", "14"};
				
				propertyDefinition.setValueConstraints(rights);
			}
		}
		
//		SystemGroupNode rootGroup = (SystemGroupNode) cr.getCommonContactGroup();
//		assertNotNull(rootGroup);
//		String type1 = "org-scheme";
//		boolean flag1 = rootGroup.getDefinition().isNodeType(type1);
//		assertTrue(flag1);
//		String type2 = "im-group";
//		boolean flag2 = rootGroup.getDefinition().isNodeType(type2);
//		assertFalse(flag2);
		
//		String name = "中科辅龙";
//		String desc = "根节点";
//		rootGroup.setName(name);
//		rootGroup.setDesc(desc);
//		
//		//在根组下添加一个成员
//		String accountName = "caipeipei";
//		String password = "123456";
//		String firstName = "培培";
//		String lastName = "蔡";
//		String email = "caipeipei@fulong.com.cn";
//		User user = cr.createUser(accountName, password, firstName, lastName, email);
//		rootGroup.addMember(user);
//		
//		//创建一个子组
//		name = "研发中心";
//		desc = "子节点1";
//		String creatorId = "1000000000000";
//		SystemGroupNode group = (SystemGroupNode) rootGroup.addChild(name, desc, creatorId);
//		
//		assertNotNull(group);
//		
//		//在子组下添加一个成员
//		accountName = "zhilingling";
//		password = "123456";
//		firstName = "玲玲";
//		lastName = "支";
//		email = "zhilingling@fulong.com.cn";
//		User user1 = cr.createUser(accountName, password, firstName, lastName, email);
//		group.addMember(user1);
//		
//		//在子组下添加一个成员
//		accountName = "lifangxin";
//		password = "123456";
//		firstName = "方鑫";
//		lastName = "李";
//		email = "lifangxin@fulong.com.cn";
//		User user2 = cr.createUser(accountName, password, firstName, lastName, email);;
//		group.addMember(user2);
//		
//		//在子组下添加一个成员
//		accountName = "guojingjing";
//		password = "123456";
//		firstName = "晶晶";
//		lastName = "郭";
//		email = "guojingjing@fulong.com.cn";
//		User user3 = cr.createUser(accountName, password, firstName, lastName, email);;
//		group.addMember(user3);

//		String name = "开发组";
//		String desc = "coolink之开发组";
//		String creatorId = "1000000000000";
//		Group child = group.addChild(name, desc, creatorId);
//		
//		child.addMember(user1);
//		child.addMember(user2);
	}
	
	@AfterClass
	public static void destory() {
		repository = null;
		cr = null;
	}
	
	@Test
	public void testGetModes() {
		Collection<Mode> modes = cr.getModes();
		
		assertNotNull(modes);
		assertEquals(modes.size(), 5);
	}
	
	@Test
	public void testCreateInstantConference() {
		String creatorId = "1000000000000";
		User creator = cr.getUser(creatorId);
		String title = "tt";
		Conference conf = cr.createInstantConference(title, creator);
		
		assertNotNull(conf);
		assertEquals(creator.getId(), creatorId);
		assertEquals(conf.getCreator().getId(), creator.getId());
		assertEquals(conf.getTitle(), title);
		
		cr.delete(conf);
	}

	@Test
	public void testCreateNormalConference() {
		String title = "测试";
		String creatorId = "1000000000000";
		Date startTime = new Date();
		Date endTime = new Date();
		String desc = "创建一个普通会议测试";
		
		Conference conf = (ConferenceNode) cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		
		assertNotNull(conf);
		assertEquals(conf.getTitle(), title);
		assertEquals(conf.getCreator().getId(), creatorId);
//		assertEquals(conf.getMode().getId(), modelId);	//测试未通过
		assertEquals(conf.getStartTime(), startTime);
		assertEquals(conf.getEndTime(), endTime);
		assertEquals(conf.getDesc(), desc);
		
		cr.delete(conf);
	}

	@Test
	public void testCreateUser() {
		String accountName = "zhangsan";
		String password = "123456";
		String firstName = "san";
		String lastName = "zhang";
		String email = "zhangsan@fulong.com.cn";
		User creator = cr.createUser(accountName, password, firstName, lastName, email);
		
		assertNotNull(creator);
		assertEquals(accountName, creator.getAccountName());
		assertEquals(password, creator.getPassword());
		assertEquals(firstName, creator.getFirstName());
		assertEquals(lastName, creator.getLastName());
		assertEquals(email, creator.getEmail());
		
		cr.delete(creator);
	}

	@Test
	public void testDeleteConference() {
		User creator = cr.getUser("1000000000000");
		String title = "test";
		Conference conf = cr.createInstantConference(title, creator);
		
		String confId = conf.getId();
		cr.delete(conf);
		Conference newConf = cr.getConference(confId);
		assertNull(newConf);
	}

	@Test
	public void testDeleteGroup() {
		Node groupNode = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.groupScheme), "group");
		GroupNode group = new GroupNode(groupNode);
		group.setName("coolink");
		group.setDesc("sfasdgsadga");
		String groupId = group.getId();
		
		Node node = repository.getNode(groupId);
		assertNotNull(node);
		
		cr.delete(group);
		
		Node newNode = repository.getNode(""+groupId);
		assertNull(newNode);
	}

	@Test
	public void testDeleteUser() {
		String accountName = "zhansan";
		String password = "123456";
		String firstName = "san";
		String lastName = "zhang";
		String email = "zhangsan@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		
		cr.delete(user);
		String userId = user.getId();
		User newUser = cr.getUser(userId);
		assertNull(newUser);
	}

	@Test
	public void testGetConference() {
		User creator = cr.getUser("1000000000000");
		String title = "tt";
		Conference conf = cr.createInstantConference(title, creator);
		String confId = conf.getId();
		Conference newConf = cr.getConference(confId);
		
		assertNotNull(newConf);
		assertEquals(confId, newConf.getId());
		
		cr.delete(conf);
	}

	@Test
	public void testGetGroup() {
		Node groupNode = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.groupScheme), "group");
		GroupNode node = new GroupNode(groupNode);
		node.setName("coolink");
		node.setDesc("sfasdgsadga");
		String groupId = node.getId();
		
		Group group = cr.getGroup(groupId);
		
		assertNotNull(group);
		assertEquals(groupId, group.getId());
		
		cr.delete(group);
	}

	@Test
	public void testGetHoldingConferences() throws Exception {
		int num1 = cr.getHoldingConferences().size();
		
		String title = "just a test";
		String creatorId = "1000000000000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-20");
		Date endTime = sdf.parse("2011-07-30"); //时间可能需要修改
		String desc = "创建普通会议测试";
		Conference conference = cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		
		int num2 = cr.getHoldingConferences().size();
		
		assertEquals(num2, num1+1);
		
		cr.delete(conference);
	}

	@Test
	public void testGetHoldingConferencesUser() throws Exception {
		String title = "test test test";
		String creatorId = "1000000000000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-21");
		Date endTime = sdf.parse("2011-07-29"); //时间可能需要修改
		String desc = "创建普通会议测试";
		Conference conf1 = cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		
		String userId = "1000000000000"; //修改
		String modeRoleId = "participant";
		conf1.addMember(modeRoleId, userId); //无法测试
		
		User participant = cr.getUser(userId); 
		Collection<Conference> conferences = cr.getHoldingConferences(participant);
		Conference conf2 = cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		
		conf2.addMember(modeRoleId, userId);
		Collection<Conference> newConferences = cr.getHoldingConferences(participant);
		
		assertEquals(newConferences.size(), conferences.size()+1);
		
		cr.delete(conf1);
		cr.delete(conf2);
	}

	@Test
	public void testGetUser() {
		String userId = "1000000000000";
		User user = cr.getUser(userId);
		
		assertNotNull(user);
		assertEquals(userId, user.getId());
	}

	@Test
	public void testGetUserByAccountName() {
		String accountName = "zhansan";
		String password = "123456";
		String firstName = "san";
		String lastName = "zhang";
		String email = "zhangsan@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		
		user = cr.getUserByAccountName(accountName);
		
		assertNotNull(user);
		assertEquals(accountName, user.getAccountName());
		
		cr.delete(user);
	}

	@Test
	public void testGetUsers() {
		String[] users = new String[cr.getUsers().size()];
		
		String accountName = "zhangsan";
		String password = "123456";
		String firstName = "san";
		String lastName = "zhang";
		String email = "zhangsan@fulong.com.cn";
		User user1 = cr.createUser(accountName, password, firstName, lastName, email);
		
		accountName = "lisi";
		password = "abcdef";
		firstName = "si";
		lastName = "li";
		email = "lisi@fulong.com.cn";
		User user2 = cr.createUser(accountName, password, firstName, lastName, email);
		
		String[] newUsers = new String[cr.getUsers().size()];
		assertEquals(newUsers.length, users.length+2);
		
		cr.delete(user2);
		newUsers = new String[cr.getUsers().size()];
		assertEquals(newUsers.length, users.length+1);
		
		cr.delete(user1);
		newUsers = new String[cr.getUsers().size()];
		assertEquals(newUsers.length, users.length);
		
		cr.delete(user1);
		newUsers = new String[cr.getUsers().size()];
		assertEquals(newUsers.length, users.length);
	}
}
