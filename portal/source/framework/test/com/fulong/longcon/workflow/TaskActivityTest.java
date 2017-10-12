package com.fulong.longcon.workflow;

/**
 * 任务活动测试
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
public class TaskActivityTest extends ProcessDefinitionTest {
	private TaskActivity task;
	protected Activity activity;
	protected String actID;

	protected void setUp() throws Exception {
		super.setUp();
		this.actID = "syn";
		this.activity = this.definition.getActivity(this.actID);
		this.task = (TaskActivity) activity;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetAppID() throws Exception {
		assertEquals("synchro", this.task.getAppId());
	}

	public void testParameter() throws Exception {
		String name = "test";
		String value = this.task.getValue(name);
		value = "new value";
		this.task.setValue(name, value);
		this.task.getDefinition().save();
		assertEquals(task.getValue(name), value);
		this.task.setValue(name, null);
		this.task.getDefinition().save();
		assertNull(task.getValue(name));

	}

}
