package com.fulong.lyvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.jcr.ContactGroupNode;
import com.fulong.lyvc.jcr.UserNode;
import com.fulong.lyvc.util.SchemeConstant;

public class TestContactGroupNode {

	private static UserNode user;
	private static ContactGroupNode contactGroup;
	private static Repository repository;
	
	@BeforeClass
	public static void init() throws Exception {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);	
		repository = (Repository) beanFactory.getBean("repository");
		beanFactory.getBean("queryManagers");
		beanFactory.getBean("sqlQuery");
		
		String accountName = "zhangsan";
		String firstName = "san";
		String lastName = "zhang";
		String password = "123456";
		String email = "zhangsan@fulong.com.cn";
		
		Node newNode = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		user = new UserNode(newNode);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		String name = "coolink";
		Node node = user.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.contactGroupScheme), "contactGroup");
		contactGroup = new ContactGroupNode(node);
		contactGroup.setName(name);
	}
	
	@AfterClass
	public static void destory() {
		repository.delete(contactGroup);
		repository.delete(user);
		
		user = null;
		contactGroup = null;
		repository = null;
	}
	
	@Test
	public void testAddChild() throws SQLException {
		Collection<? extends Group> groups = contactGroup.children();
		
		String name = "testGroup";
		String desc = "a child group";
		String creatorId = user.getId();
		
		//添加一个子联系人组进行测试
		Group temp1 = contactGroup.addChild(name, desc, creatorId);
		assertNotNull(temp1);
		
		Collection<? extends Group> newGroups = contactGroup.children();
		
		assertEquals(groups.size()+1, newGroups.size());
		
		//添加重名的子联系人组进行测试
		Group temp2 = contactGroup.addChild(name, desc, creatorId);
		assertNull(temp2);
		
		name = "test test";
		desc = "a child group";
		creatorId = user.getId();
		
		//添加不重名的子联系人组进行测试
		Group temp3 = contactGroup.addChild(name, desc, creatorId);
		assertNotNull(temp3);
		
		newGroups = contactGroup.children();
		assertEquals(groups.size()+2, newGroups.size());
		
		repository.delete((Node) temp1);
		repository.delete((Node) temp3);
	}

	@Test
	public void testCanAddMember() throws SQLException {
		//判断能否添加创建者
		boolean flag = contactGroup.canAddMember(user);
		assertFalse(flag);
		
		String accountName = "zhaoliu";
		String firstName = "liu";
		String lastName = "zhao";
		String password = "123456";
		String email = "zhaoliu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		//判断能否添加新的成员
		flag = contactGroup.canAddMember(user);
		assertTrue(flag);
		
		flag = contactGroup.addMember(user);
		assertTrue(flag);

		//判断能否添加已经添加的成员
		flag = contactGroup.canAddMember(user);
		assertFalse(flag);
		
		flag = contactGroup.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testChildren() throws SQLException {
		Collection<? extends Group> groups = contactGroup.children();
		
		String name = "developmentGroup";
		String desc = "a child group";
		String creatorId = user.getId();
		Group temp = contactGroup.addChild(name, desc, creatorId);
		
		Collection<? extends Group> newGroups = contactGroup.children();
		
		assertEquals(groups.size()+1, newGroups.size());
		
		//删除子联系人组
		repository.delete((Node) temp);
	}

	@Test
	public void testGetCreator() throws SQLException {
		User newUser = contactGroup.getCreator();
		
		assertEquals(user.getId(), newUser.getId());
	}

	@Test
	public void testGetManager() throws SQLException {
		User newUser = contactGroup.getManager();
		
		assertEquals(user.getId(), newUser.getId());
	}

	@Test
	public void testGetName() {
		String name = "coolink";
		String result = contactGroup.getName();
		
		assertEquals(name, result);
	}

	@Test
	public void testGetParentGroup() throws SQLException {
		String name = "ttGroup";
		String desc = "a child group";
		String creatorId = user.getId();
		
		Group child = contactGroup.addChild(name, desc, creatorId);
		assertNotNull(child);
		
		Group parent = child.getParentGroup();
		
		assertEquals(contactGroup, parent);
		
		repository.delete((Node) child);
	}

	@Test
	public void testSetName() {
		String name = "PDSoft";
		contactGroup.setName(name);
		String result = contactGroup.getName();
		
		assertEquals(name, result);
	}

	@Test
	public void testUsers() throws SQLException {
		Collection<? extends User> users = contactGroup.users();
		
		String accountName = "wangwu";
		String firstName = "wu";
		String lastName = "wang";
		String password = "123456";
		String email = "wangwu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		boolean flag = contactGroup.addMember(user);
		assertTrue(flag);
		
		Collection<? extends User> newUsers = contactGroup.users();
		
		assertEquals(users.size()+1, newUsers.size());
		
		flag = contactGroup.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testAddMember() throws SQLException {
		String accountName = "wangwu";
		String firstName = "wu";
		String lastName = "wang";
		String password = "123456";
		String email = "wangwu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		//添加不存在的用户进行测试
		boolean flag = contactGroup.addMember(user);
		assertTrue(flag);
		
		//判断添加的用户是否存在
		flag = contactGroup.isMember(user);
		assertTrue(flag);
		
		//重复添加同一用户进行测试
		flag = contactGroup.addMember(user);
		assertFalse(flag);
		
		//清理操作
		flag = contactGroup.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testIsMember() throws SQLException {
		String accountName = "zhaoliu";
		String firstName = "liu";
		String lastName = "zhao";
		String password = "123456";
		String email = "zhaoliu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		boolean flag = contactGroup.addMember(user);
		assertTrue(flag);
		
		flag = contactGroup.isMember(user);
		assertTrue(flag);
		
		//清理操作
		flag = contactGroup.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testMembers() throws SQLException {
		Enumeration<? extends Principal> enums = contactGroup.members();
		ArrayList<User> list = new ArrayList<User>();
		while(enums.hasMoreElements())
			list.add((User)enums.nextElement());
		
		String accountName = "wangwu";
		String firstName = "wu";
		String lastName = "wang";
		String password = "123456";
		String email = "wangwu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		boolean flag = contactGroup.addMember(user);
		assertTrue(flag);
		
		Enumeration<? extends Principal> newEmuns = contactGroup.members();
		ArrayList<User> newList = new ArrayList<User>();
		while(newEmuns.hasMoreElements())
			newList.add((User)newEmuns.nextElement());
		
		assertEquals(list.size()+1, newList.size());
		
		//清理操作
		flag = contactGroup.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testRemoveMember() throws SQLException {
		String accountName = "zhaoliu";
		String firstName = "liu";
		String lastName = "zhao";
		String password = "123456";
		String email = "zhaoliu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		contactGroup.addMember(user);
		
		boolean flag = contactGroup.isMember(user);
		assertTrue(flag);
		
		contactGroup.removeMember(user);
		
		flag = contactGroup.isMember(user);
		assertFalse(flag);
		
		//清理操作
		flag = contactGroup.removeMember(user);
		assertFalse(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}
}
