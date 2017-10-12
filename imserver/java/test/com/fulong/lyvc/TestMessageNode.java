package com.fulong.lyvc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.jcr.MessageNode;
import com.fulong.lyvc.jcr.UserNode;
import com.fulong.lyvc.util.SchemeConstant;

public class TestMessageNode {

	private static MessageNode message;
	private static Repository repository;
	
	@BeforeClass
	public static void init() throws Exception {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);	
		repository = (Repository) beanFactory.getBean("repository");
		
		String content = "abcdefg";
		String senderId = "1000000000000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date saveDate = sdf.parse("2010-07-26");
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.messageScheme), "message");
		message = new MessageNode(node);
		message.setContent(content);
		message.setSenderid(senderId);
		message.setSaveDate(saveDate);
	}
	
	@AfterClass
	public static void destory() {
		repository.delete(message);
		
		message = null;
		repository = null;
	}
	
	@Test
	public void testGetTitle() {
		String title = "123abc";
		message.setTitle(title);
		String result = message.getTitle();
		
		assertEquals(title, result);
	}
	
	@Test
	public void testGetContent() {
		String content = "abcdefg";
		String result = message.getContent();
		
		assertEquals(content, result);
	}

	@Test
	public void testGetSaveDate() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date saveDate = sdf.parse("2010-07-26");
		Date result = message.getSaveDate();
		
		assertEquals(saveDate, result);
	}

	@Test
	public void testGetSenderid() {
		String senderId = "1000000000000";
		String result = message.getSenderid();
		
		assertEquals(senderId, result);
	}
	
	@Test
	public void testSetTitle() {
		String title = "123456";
		message.setTitle(title);
		String result = message.getTitle();
		
		assertEquals(title, result);
	}

	@Test
	public void testSetContent() {
		String content = "123456";
		message.setContent(content);
		String result = message.getContent();
		
		assertEquals(content, result);
	}

	@Test
	public void testSetSaveDate() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date saveDate = sdf.parse("2010-07-23");
		message.setSaveDate(saveDate);
		Date result = message.getSaveDate();
		
		assertEquals(saveDate, result);
	}

	@Test
	public void testSetSenderid() throws SQLException {
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
		
		String senderId = user.getId();
		message.setSenderid(senderId);
		String result = message.getSenderid();
		
		assertEquals(senderId, result);
		
		repository.delete(user);
	}
}
