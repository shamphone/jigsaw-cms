package com.fulong.lyvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.jcr.ConferenceNode;
import com.fulong.lyvc.jcr.JCRConferenceManager;

public class TestConferenceNode {

	private static JCRConferenceManager cr;
	private static ConferenceNode conference;
	private static Repository repository;
	
	@BeforeClass
	public static void init() throws Exception {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);
		cr = (JCRConferenceManager) beanFactory.getBean("conferenceManager");
		repository = (Repository) beanFactory.getBean("repository");
		beanFactory.getBean("queryManagers");
		beanFactory.getBean("sqlQuery");
		
		String title = "会议测试";
		//注意
		//如果定义String creatorId = "1000000000000l"; 则出错
		//可以定义long creatorId = 1000000000000l;来使用
		String creatorId = "1000000000000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-22");
		Date endTime = sdf.parse("2011-07-27");	//需要修改
		String desc = "创建一个普通会议";
		
		conference = (ConferenceNode) cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		conference.start();
	}
	
	@AfterClass
	public static void destory() throws Exception {
		cr.delete(conference);
		cr = null;
	}
	
	@Test
	public void testAddMember() {
		Collection<User> members = conference.getMembers();
		assertNotNull(members);
		
		String accountName = "liuli";
		String password = "123456";
		String firstName = "li";
		String lastName = "liu";
		String email = "liuli@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		assertNotNull(user);
		
		String modeRoleID = "participant";
		String userId = user.getId();
		conference.addMember(modeRoleID, userId);
		
		Collection<User> newMembers = conference.getMembers();
		assertNotNull(newMembers);
		
		assertEquals(newMembers.size(), members.size()+1);
		
		conference.removeMember(userId);
	}

	@Test
	public void testGetMembers() throws Exception {
		String accountName = "niuniu";
		String password = "ttttttt";
		String firstName = "niu";
		String lastName = "niu";
		String email = "niu@fulong.com.cn";
		
		ArrayList<String> list = new ArrayList<String>();
		
		String creatorId = "1000000000000";
		list.add(creatorId);
		
		User user1 = cr.createUser(accountName, password, firstName, lastName, email);
		
		String modeRoleId = "participant";
		String userId = user1.getId();
		conference.addMember(modeRoleId, userId);
		list.add(userId);
		
		User user2 = cr.createUser(accountName, password, firstName, lastName, email);
		userId = user2.getId();
		conference.addMember(modeRoleId, userId);
		list.add(userId);
		
		ArrayList<String> newList = new ArrayList<String>();

		Collection<User> users = conference.getMembers();
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			newList.add(iterator.next().getId());
		}
		
		assertEquals(list, newList);
		
		boolean flag = repository.delete((Node) user1);
		assertTrue(flag);
		
		flag = repository.delete((Node) user2);
		assertTrue(flag);
	}

	@Test
	public void testIsMember() throws SQLException, Exception {
		String accountName = "liuli";
		String password = "123456";
		String firstName = "li";
		String lastName = "liu";
		String email = "liuli@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		
		assertNotNull(user);
		
		String title = "会议测试";
		String creatorId = "1000000000000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-22");
		Date endTime = sdf.parse("2010-07-28");
		String desc = "创建一个普通会议";
		Conference con = (ConferenceNode) cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		con.start();
		
		assertNotNull(con);

		String id = user.getId();
		con.addMember("participant", id);
		
		boolean flag = con.isMember(id);
		assertTrue(flag);
		
		con.removeMember(user.getId());
		cr.delete(con);
		
		flag = repository.delete((Node) user);
		assertTrue(flag);
	}

	@Test
	public void testRemoveMember() throws Exception {
		String accountName = "lele";
		String password = "ttoopp";
		String firstName = "le";
		String lastName = "le";
		String email = "lele@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		String id = user.getId();
		
		assertNotNull(user);
		
		conference.addMember("participant", id);
		
		boolean flag = conference.isMember(id);
		assertTrue(flag);
		
		conference.removeMember(id);
		
		flag = conference.isMember(id);
		assertFalse(flag);
		
		flag = repository.delete((Node) user);
		assertTrue(flag);
	}

	@Test
	public void testAddDocument() throws SQLException {
		//TODO
		String url = "/resources/struts标签介绍.pdf";
		
		Document doc = conference.addDocument(url);
		
		assertEquals(url, doc.getDocURL());
		
	}

	@Test
	public void testRemoveDocument() throws SQLException {
		//TODO
		String url = "/resources/struts标签介绍.pdf";
		
		Document doc = conference.addDocument(url);
		
		conference.removeDocument(doc.getDocURL());
		
		assertEquals(conference.getDocuments().size(), 1);
	}

	@Test
	public void testGetConferecneModelID() throws SQLException {
		String modelId = "im-commonConference";
		String result = conference.getConferecneModelID();
		
		assertEquals(modelId, result);
	}

	@Test
	public void testGetDesc() {
		String desc = "创建一个普通会议";
		String result = conference.getDesc();
		
		assertEquals(desc, result);
	}

	@Test
	public void testGetEndTime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endTime = sdf.parse("2011-07-27");
		Date result = conference.getEndTime();
		
		assertEquals(endTime, result);
	}

	@Test
	public void testGetStartTime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-22");
		Date result = conference.getStartTime();
		
		assertEquals(startTime, result);
	}

	@Test
	public void testGetTitle() {
		String title = "会议测试";
		String result = conference.getTitle();
		
		assertEquals(title, result);
	}
	@Test
	public void testGetCreator() throws SQLException {
		String creatorId = "1000000000000";
		User creator = conference.getCreator();
		
		assertEquals(creatorId, creator.getId());
	}
	
	@Test
	public void testGetCreatorId() {
		String creatorId = "1000000000000";
		String result = conference.getCreatorId();
		
		assertEquals(creatorId, result);
	}

	@Test
	public void testSetDesc() throws SQLException {
		String desc = "abcdefg";
		conference.setDesc(desc);
		
		assertEquals(desc, conference.getDesc());
	}

	@Test
	public void testSetEndTime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endTime = sdf.parse("2011-07-28");
		conference.setEndTime(endTime);
		
		assertEquals(endTime, conference.getEndTime());
	}

	@Test
	public void testSetStartTime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-20");
		conference.setStartTime(startTime);
		
		assertEquals(startTime, conference.getStartTime());
	}

	@Test
	public void testSetTitle() throws SQLException {
		String title = "title修改了";
		conference.setTitle(title);
		
		assertEquals(title, conference.getTitle());
	}
	
	@Test
	public void testSetCreatorId() throws SQLException {
		String accountName = "lele";
		String password = "ttoopp";
		String firstName = "le";
		String lastName = "le";
		String email = "lele@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		
		String creatorId = user.getId();
		conference.setCreatorId(creatorId);
		
		assertEquals(creatorId, conference.getCreatorId());
		
		conference.setCreatorId("1000000000000");
		
		boolean flag = repository.delete((Node) user);
		assertTrue(flag);
	}

	@Test
	public void testGetParticipants() throws SQLException, Exception {
		String title = "会议测试";
		String creatorId = "1000000000000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = sdf.parse("2010-07-22");
		Date endTime = sdf.parse("2011-07-27");	//需要修改
		String desc = "创建一个普通会议";
		
		ConferenceNode con = (ConferenceNode) cr.createNormalConference(title, creatorId, startTime, endTime, desc);
		con.start();
		
		String accountName = "niuniu";
		String password = "ttttttt";
		String firstName = "niu";
		String lastName = "niu";
		String email = "niu@fulong.com.cn";
		
		Collection<User> c = new ArrayList<User>();
		
		User user1 = cr.createUser(accountName, password, firstName, lastName, email);
		con.join(user1);
		c.add(user1);
		
		User user2 = cr.createUser(accountName, password, firstName, lastName, email);
		con.join(user2);
		c.add(user2);
		
		Collection<User> users = con.getParticipants();
		
		assertEquals(c, users);
		
		cr.delete(con);
		
		boolean flag = repository.delete((Node) user1);
		assertTrue(flag);
		
		flag = repository.delete((Node) user2);
		assertTrue(flag);
	}

	@Test
	public void testIsHolding() {
		assertTrue(conference.isHolding());
	}
	
	@Test
	public void TestJoin() throws SQLException {
		String accountName = "niuniu";
		String password = "ttttttt";
		String firstName = "niu";
		String lastName = "niu";
		String email = "niu@fulong.com.cn";
		User user1 = cr.createUser(accountName, password, firstName, lastName, email);
		
		assertNotNull(user1);
		
		boolean flag = conference.join(user1);
		assertTrue(flag);
		
		//向会议中重复添加同一用户进行测试
		flag = conference.join(user1);
		assertFalse(flag);
		
		//向会议中添加一个新的用户进行测试
		User user2 = cr.createUser(accountName, password, firstName, lastName, email);
		flag = conference.join(user2);
		assertTrue(flag);
		
		flag = repository.delete((Node) user1);
		assertTrue(flag);
		
		flag = repository.delete((Node) user2);
		assertTrue(flag);
	}
	
	@Test
	public void testLeave() throws SQLException {
		String accountName = "lele";
		String password = "ttoopp";
		String firstName = "le";
		String lastName = "le";
		String email = "lele@fulong.com.cn";
		User user = cr.createUser(accountName, password, firstName, lastName, email);
		
		assertNotNull(user);
		assertTrue(conference.join(user));
		
		boolean flag = conference.leave(user);
		assertTrue(flag);
		
		//一个用户离开会议多次的测试
		flag = conference.leave(user);
		assertFalse(flag);
		
		flag = repository.delete((Node) user);
		assertTrue(flag);
	}
}
