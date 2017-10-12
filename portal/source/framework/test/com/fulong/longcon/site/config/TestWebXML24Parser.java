/**
 * 
 */
package com.fulong.longcon.site.config;

import java.io.IOException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;

import com.fulong.common.ResourceUtils;

/**
 * <p>
 * Title: WebXML24Parser
 * </p>
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * @author luobin
 * @date 2010-6-1
 */
public class TestWebXML24Parser extends TestCase {
	protected WebXMLParser parser;
	
	@Override
	protected void setUp() throws Exception {
		String path = ResourceUtils.getResource("test.web.xml");
		parser = new WebXML24Parser(path);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		parser = null;
		super.tearDown();
	}

	public void testSetContextParamValue(){
		parser.removeContextParam("route");
		assertNull(parser.getContextParamValue("route"));
		parser.setContextParamValue("route", "d://test");
		assertEquals(parser.getContextParamValue("route"), "d://test");
		parser.removeContextParam("route");
		assertNull(parser.getContextParamValue("route"));
	}

	public void testSetDisplayName() {
		parser.setDisplayName(null);
		assertNull(parser.getDisplayName());
		parser.setDisplayName("junit测试");
		assertEquals(parser.getDisplayName(), "junit测试");
		parser.setDisplayName(null);
		assertNull(parser.getDisplayName());
		parser.setDisplayName("junit测试用例");
		assertEquals(parser.getDisplayName(), "junit测试用例");
	}

	public void testRemoveContextParam()  {
		parser.removeContextParam("route");
		assertNull(parser.getContextParamValue("route"));
		parser.setContextParamValue("route", "d://test");
		assertEquals(parser.getContextParamValue("route"), "d://test");
		parser.removeContextParam("route");
		assertNull(parser.getContextParamValue("route"));
	}

	public void testSetDescription() {
		parser.setDescription(null);
		assertNull(parser.getDescription());
		parser.setDescription("这里是描述");
		assertEquals(parser.getDescription(), "这里是描述");
		parser.setDescription(null);
		assertNull(parser.getDescription());
		parser.setDescription("<这里是描述2");
		assertEquals(parser.getDescription(), "<这里是描述2");
	}
	
	public void testSave() throws IOException, SAXException{
		String path = ResourceUtils.getResource("test.web.xml");
		parser = new WebXML24Parser(path);
		parser.setDisplayName("junit测试用例");
		parser.save();
		parser = new WebXML24Parser(path);
		assertEquals(parser.getDisplayName(), "junit测试用例");
	}
	
	public void testGetId(){
		assertEquals(parser.getID(), "WebApp_ID");
	}

}
