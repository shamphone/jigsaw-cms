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
import com.fulong.lyvc.jcr.SystemGroupNode;
import com.fulong.lyvc.jcr.UserNode;
import com.fulong.lyvc.util.SchemeConstant;

public class TestGroupNode {

	private static SystemGroupNode group;
	private static Repository repository;
	
	@BeforeClass
	public static void init() throws Exception {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);	
		repository = (Repository) beanFactory.getBean("repository");
		beanFactory.getBean("queryManagers");
		beanFactory.getBean("sqlQuery");
		
		String name = "FuLong";
		String desc = "zhongke fulong";
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.groupScheme), "group");
		group = new SystemGroupNode(node);
		group.setName(name);
		group.setDesc(desc);
	}
	
	@AfterClass
	public static void destory() {
		repository.delete(group);
		
		group = null;
		repository = null;
	}
	
	@Test
	public void testCanAddMember() throws SQLException {
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
		boolean flag = group.canAddMember(user);
		assertTrue(flag);
		
		flag = group.addMember(user);
		assertTrue(flag);
		
		//判断能否添加已经添加的成员
		flag = group.canAddMember(user);
		assertFalse(flag);
		
		flag = group.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testGetDesc() {
		String desc = "zhongke fulong";
		String result = group.getDesc();
		
		assertEquals(desc, result);
	}

	@Test
	public void testGetName() {
		String name = "FuLong";
		String result = group.getName();
		
		assertEquals(name, result);
	}

	@Test
	public void testSetDesc() throws SQLException {
		String desc = "zhongkefulonggongsi";
		group.setDesc(desc);
		String result = group.getDesc();
		
		assertEquals(desc, result);
	}

	@Test
	public void testSetName() {
		String name = "zkfl";
		group.setName(name);
		String result = group.getName();
		
		assertEquals(name, result);
	}

	@Test
	public void testAddMember() throws SQLException {
		String accountName = "zhangsan";
		String firstName = "san";
		String lastName = "zhang";
		String password = "123456";
		String email = "zhangsan@fulong.com.cn";
		
		Node newNode = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(newNode);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		boolean flag = group.isMember(user);
		assertFalse(flag);
		
		flag = group.addMember(user);
		assertTrue(flag);
		
		flag = group.isMember(user);
		assertTrue(flag);
		
		flag = group.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testIsMember() throws SQLException {
		String accountName = "zhangsan";
		String firstName = "san";
		String lastName = "zhang";
		String password = "123456";
		String email = "zhangsan@fulong.com.cn";
		
		Node newNode = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(newNode);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		boolean flag = group.addMember(user);
		assertTrue(flag);
		
		flag = group.isMember(user);
		assertTrue(flag);
		
		flag = group.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testRemoveMember() throws SQLException {
		String accountName = "zhangsan";
		String firstName = "san";
		String lastName = "zhang";
		String password = "123456";
		String email = "zhangsan@fulong.com.cn";
		
		Node newNode = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode user = new UserNode(newNode);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		
		boolean flag = group.addMember(user);
		assertTrue(flag);
		
		flag = group.isMember(user);
		assertTrue(flag);
		
		//删除测试（清理操作）
		flag = group.removeMember(user);
		assertTrue(flag);
		
		//重复删除的测试
		flag = group.removeMember(user);
		assertFalse(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testMembers() throws SQLException {
		Enumeration<? extends Principal> enums = group.members();
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
		
		boolean flag = group.addMember(user);
		assertTrue(flag);
		
		Enumeration<? extends Principal> newEmuns = group.members();
		ArrayList<User> newList = new ArrayList<User>();
		while(newEmuns.hasMoreElements())
			newList.add((User)newEmuns.nextElement());
		
		assertEquals(list.size()+1, newList.size());
		
		flag = group.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testChildren() throws SQLException {
		Collection<? extends Group> groups = group.children();
		
		String name = "PDSoft";
		String desc = "a child group";
		String creatorId = "1000000000000";
		Group temp = group.addChild(name, desc, creatorId);
		
		Collection<? extends Group> newGroups = group.children();
		
		assertEquals(groups.size()+1, newGroups.size());
		
		repository.delete((Node) temp);
	}

	@Test
	public void testUsers() throws SQLException {
		Collection<? extends User> users = group.users();
		
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
		
		boolean flag = group.addMember(user);
		assertTrue(flag);
		
		flag = group.isMember(user);
		assertTrue(flag);
		
		Collection<? extends User> newUsers = group.users();
		
		assertEquals(users.size()+1, newUsers.size());
		
		flag = group.removeMember(user);
		assertTrue(flag);
		
		flag = repository.delete(user);
		assertTrue(flag);
	}

	@Test
	public void testAddChild() throws SQLException {
		Collection<? extends Group> groups = group.children();
		
		String name = "rr";
		String desc = "a child group";
		String creatorId = "1000000000000";
		
		//添加一个子组进行测试
		Group temp1 = group.addChild(name, desc, creatorId);
		assertNotNull(temp1);
		
		Collection<? extends Group> newGroups = group.children();
		
		assertEquals(groups.size()+1, newGroups.size());
		
		//添加一个重名的子组进行测试
		Group temp2 = group.addChild(name, desc, creatorId);
		assertNull(temp2);
		
		//添加一个不重名的子组进行测试
		name = "abc";
		Group temp3 = group.addChild(name, desc, creatorId);
		assertNotNull(temp3);
		
		newGroups = group.children();
		assertEquals(groups.size()+2, newGroups.size());
		
		repository.delete((Node) temp1);
		repository.delete((Node) temp3);
	}

	@Test
	public void testGetParent() throws SQLException {
		String name = "yy";
		String desc = "a child group";
		String creatorId = "1000000000000";
		
		//添加一个子组进行测试
		SystemGroupNode temp = (SystemGroupNode) group.addChild(name, desc, creatorId);
		Node parent = temp.getParent();
		
		assertEquals(group, parent);
		
		repository.delete((Node) temp);
	}

	@Test
	public void testGetParentGroup() throws SQLException {
		String name = "uu";
		String desc = "a child group";
		String creatorId = "1000000000000";
		Group child = group.addChild(name, desc, creatorId);
		
		Group parent = child.getParentGroup();
		
		assertEquals(group, parent);
		
		repository.delete((Node) child);
	}
}
