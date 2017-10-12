package com.fulong.longcon.workflow;

/**
 * 基本活动测试
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
public class ActivityTest extends ProcessDefinitionTest {
protected Activity activity;
protected String actID;


	protected void setUp() throws Exception {
		super.setUp();
		this.actID = "signin";
		this.activity= this.definition.getActivity(this.actID);
	}
	
	public void testGetId(){
		assertEquals(actID, this.activity.getId());
	}
	
	public void testGetDescription(){
		assertTrue(this.activity.getDescription().length()>5);
	}
	
	public void testGetStartMode(){
		assertEquals(this.activity.getStartMode(), ExecutionMode.Automatic);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
