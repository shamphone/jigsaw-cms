/**
 * 
 */
package com.fulong.longcon.workflow;


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
public class ProcessDefinitionTest extends WorkflowServiceTest {
	protected ProcessDefinition definition;
	private String id = "basiclogin";

	protected void setUp() throws Exception {
		super.setUp();
		this.definition = super.service.getDefinition(id);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testGetId() throws Exception {
		assertEquals(id, this.definition.getId());
	}

	public void testName() throws Exception {
		String oldName = this.definition.getName();
		String newName = "test name";
		this.definition.setName(newName);
		this.definition.save();
		assertEquals(this.definition.getName(), newName);
		
		this.definition.setName(oldName);
		this.definition.save();		
		assertEquals(this.definition.getName(), oldName);
		

	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.ProcessDefinition#getDescription()}.
	 */
	public void testDescription() throws Exception{
		String oldValue = this.definition.getDescription();
		String newValue = "new description";
		this.definition.setDescription(newValue);
		this.definition.save();
		assertEquals(newValue, this.definition.getDescription());
		this.definition.setDescription(oldValue);
		this.definition.save();		
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.ProcessDefinition#getBegin()}.
	 */
	public void testGetBegin() {
		 assertEquals(this.definition.getBegin().getId(), "signin");
	}


	/**
	 * Test method for {@link com.fulong.longcon.workflow.ProcessDefinition#getTransition(java.lang.String)}.
	 */
	public void testTransition() {
		Transition[] trans = this.definition.getTransitions();
		for(int i=0;i<trans.length;i++)
			assertEquals(this.definition.getTransition(trans[i].getId()), trans[i]);
	}

	
	/**
	 * Test method for {@link com.fulong.longcon.workflow.ProcessDefinition#getActivities()}.
	 */
	public void testActivity() throws Exception{
		Activity activity= this.definition.createActivity("测试活动");
		
		this.definition.save();		
		boolean found = false;
		Activity[] activities = this.definition.getActivities();
		for(int i=0;i<activities.length;i++){
			assertEquals(activities[i].getDefinition(),this.definition);
			assertEquals(activities[i],this.definition.getActivity(activities[i].getId()));
			if(activities[i].equals(activity))
				found= true;
		}
		assertTrue(found);
		String id= activity.getId();
		this.definition.delete(activity);
		this.definition.save();		
		assertNull(this.definition.getActivity(id));
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.ProcessDefinition#getActivities()}.
	 */
	public void testTaskActivity() throws Exception{
		Activity activity= this.definition.createTaskActivity("syn","测试任务活动");
		
		this.definition.save();		
		boolean found = false;
		Activity[] activities = this.definition.getActivities();
		for(int i=0;i<activities.length;i++){
			assertEquals(activities[i].getDefinition(),this.definition);
			assertEquals(activities[i],this.definition.getActivity(activities[i].getId()));
			if(activities[i].equals(activity))
				found= true;
		}
		assertTrue(found);
		String id= activity.getId();
		this.definition.delete(activity);
		this.definition.save();		
		assertNull(this.definition.getActivity(id));
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.ProcessDefinition#getActivities()}.
	 */
	public void testRouteActivity() throws Exception{
		Activity activity= this.definition.createRouteActivity("测试任务活动");
		
		this.definition.save();		
		boolean found = false;
		Activity[] activities = this.definition.getActivities();
		for(int i=0;i<activities.length;i++){
			assertEquals(activities[i].getDefinition(),this.definition);
			assertEquals(activities[i],this.definition.getActivity(activities[i].getId()));
			if(activities[i].equals(activity))
				found= true;
		}
		assertTrue(found);
		String id= activity.getId();
		this.definition.delete(activity);
		this.definition.save();		
		assertNull(this.definition.getActivity(id));
	}



}
