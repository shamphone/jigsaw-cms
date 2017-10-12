/**
 * 
 */
package com.fulong.longcon.workflow;

import java.io.File;
import java.util.Iterator;


import com.fulong.longcon.workflow.xml.XMLWorkflowService;

import junit.framework.TestCase;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class WorkflowServiceTest extends TestCase {
	protected WorkflowService service;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("D:/Longcon/coolink/deployment/WEB-INF/workflow");
		XMLWorkflowService aservice = new XMLWorkflowService();
		aservice.setFolder(file);
		aservice.init();
		this.service= aservice;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.WorkflowService#getDefinition(java.lang.String)}.
	 */
	public void testDefinitions() throws Exception{	
		String id= "test"+System.currentTimeMillis();
		ProcessDefinition definition = this.service.create(id);
		assertEquals(id, definition.getId());
		boolean found=false;
		for(Iterator<ProcessDefinition> iterator=this.service.definitions();iterator.hasNext();){
			ProcessDefinition def=iterator.next();
			if(def.equals(definition))
				found=true;
		}
		assertTrue(found);		
		this.service.delete(definition);
		found = false;
		assertNull(this.service.getDefinition(id));
		for(Iterator<ProcessDefinition> iterator=this.service.definitions();iterator.hasNext();){
			ProcessDefinition def=iterator.next();
			if(def.equals(definition))
				found=true;
		}
		assertFalse(found);		
		
		
		
		
		
	}

}
