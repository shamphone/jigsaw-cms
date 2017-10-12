package com.fulong.lyvc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.jcr.DocumentNode;
import com.fulong.lyvc.util.SchemeConstant;

public class TestDocumentNode {

	private static DocumentNode doc;
	private static Repository repository;
	
	@BeforeClass
	public static void init() throws Exception {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);	
		repository = (Repository) beanFactory.getBean("repository");
		
		String url = "/resources/JSR 170 White Paper.pdf";
		String fileName = "JSR 170 White Paper.pdf";
		
		Node node = repository.getRootNode().addNode(repository.getDefinitionManager().getDefinition(SchemeConstant.documentScheme), "message");
		doc = new DocumentNode(node);
		doc.setDocURL(url);
		doc.setFileName(fileName);
	}
	
	@AfterClass
	public static void destory() {
		repository.delete(doc);
		
		doc = null;
		repository = null;
	}
	
	@Test
	public void testGetDocURL() {
		String url = "/resources/JSR 170 White Paper.pdf";
		String result = doc.getDocURL();
		
		assertEquals(url, result);
	}

	@Test
	public void testGetFileName() {
		String fileName = "JSR 170 White Paper.pdf";
		String result = doc.getFileName();
		
		assertEquals(fileName, result);
	}

	@Test
	public void testSetDocURL() throws SQLException {
		String url = "/resources/JSR 170.pdf";
		doc.setDocURL(url);
		String result = doc.getDocURL();
		
		assertEquals(url, result);
	}

	@Test
	public void testSetFileName() throws SQLException {
		String fileName = "JSR 170.pdf";
		doc.setFileName(fileName);
		String result = doc.getFileName();
		
		assertEquals(fileName, result);
	}
}
