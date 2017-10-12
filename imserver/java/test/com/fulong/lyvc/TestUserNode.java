package com.fulong.lyvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

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

public class TestUserNode {

	private static UserNode user;
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
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		user = new UserNode(node);
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
	}
	
	@AfterClass
	public static void destory() {
		repository.delete(user);
		user = null;
		repository = null;
	}
	
	@Test
	public void testGetAccountName() {
		String accountName = "zhangsan";
		String result = user.getAccountName();
		
		assertEquals(accountName, result);
	}

	@Test
	public void testGetFirstName() {
		String firstName = "san";
		String result = user.getFirstName();
		
		assertEquals(firstName, result);
	}

	@Test
	public void testGetLastName() {
		String lastName = "zhang";
		String result = user.getLastName();
		
		assertEquals(lastName, result);
	}

	@Test
	public void testGetPassword() {
		String password = "123456";
		String result = user.getPassword();
		
		assertEquals(password, result);
	}

	@Test
	public void testGetEmail() {
		String email = "zhangsan@fulong.com.cn";
		String result = user.getEmail();
		
		assertEquals(email, result);
	}

	@Test
	public void testSetAccountName() {
		String accountName = "lisi";
		user.setAccountName(accountName);
		String result = user.getAccountName();
		
		assertEquals(accountName, result);
	}

	@Test
	public void testSetFirstName() throws SQLException {
		String firstName = "si";
		user.setFirstName(firstName);
		String result = user.getFirstName();
		
		assertEquals(firstName, result);
	}

	@Test
	public void testSetLastName() throws SQLException {
		String lastName = "li";
		user.setLastName(lastName);
		String result = user.getLastName();
		
		assertEquals(lastName, result);
	}

	@Test
	public void testSetPassword() throws SQLException {
		String newPassword = "uuyyuu";
		user.setPassword(newPassword);
		String result = user.getPassword();
		
		assertEquals(newPassword, result);
	}
	
	@Test
	public void testSetEmail() throws SQLException {
		String email = "lisi@fulong.com.cn";
		user.setEmail(email);
		String result = user.getEmail();
		
		assertEquals(email, result);
	}

	@Test
	public void testCheckPassword() {
		String password = "uuyyuu";
		boolean flag = user.checkPassword(password);
		
		assertTrue(flag);
	}

	@Test
	public void testGetContactGroup() throws SQLException {
		String name = "coolink";
		Node node = user.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.contactGroupScheme), "contactGroup");
		ContactGroupNode contactGroupNode = new ContactGroupNode(node);
		contactGroupNode.setName(name);
		String id = contactGroupNode.getId();
		
		Group group = user.getContactGroup();
		
		assertEquals(name, group.getName());
		assertEquals(id, group.getId());
		
		repository.delete(contactGroupNode);
	}

	@Test
	public void testGetInContactGroup() throws SQLException {
		String accountName = "wangwu";
		String firstName = "wu";
		String lastName = "wang";
		String password = "123456";
		String email = "wangwu@fulong.com.cn";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.userScheme), "user");
		UserNode userNode = new UserNode(node);
		userNode.setAccountName(accountName);
		userNode.setFirstName(firstName);
		userNode.setLastName(lastName);
		userNode.setEmail(email);
		userNode.setPassword(password);
		
		String name = "pdsoft";
		Node newNode = user.addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.contactGroupScheme), "contactGroup");
		ContactGroupNode contactGroupNode = new ContactGroupNode(newNode);
		contactGroupNode.setName(name);
		
		contactGroupNode.addMember(userNode);
		String id = contactGroupNode.getId();
		
		Group group = user.getInContactGroup(userNode);
		
		assertNotNull(group);
		assertEquals(id, group.getId());
		
		repository.delete(userNode);
	}
}
