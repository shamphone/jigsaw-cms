/**
 * 
 */
package com.fulong.longcon.workflow;

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
public class TransitionTest  extends ProcessDefinitionTest {

	public Transition transition;
	private String transID = "trans1";
	protected void setUp() throws Exception {
		super.setUp();
		this.transition= this.definition.getTransition(transID);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#getName()}.
	 */
	public void testName() throws Exception{
		String oldName = this.transition.getName();
		String newName = "new trans name";
		this.transition.setName(newName);
		this.transition.getDefinition().save();
		assertEquals(this.transition.getName(), newName);
		this.transition.setName(oldName);
		this.transition.getDefinition().save();		
		assertEquals(this.transition.getName(), oldName);
		
	}


	public void testDescription()  throws Exception{
		String oldName = this.transition.getDescription();
		String newName = "new trans name";
		this.transition.setDescription(newName);
		this.transition.getDefinition().save();
		assertEquals(this.transition.getDescription(), newName);
		this.transition.setDescription(oldName);
		this.transition.getDefinition().save();		
		assertEquals(this.transition.getDescription(), oldName);

	}


	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#getDefinition()}.
	 */
	public void testGetDefinition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#getFrom()}.
	 */
	public void testGetFrom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#setFrom(com.fulong.longcon.workflow.Activity)}.
	 */
	public void testSetFrom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#getTo()}.
	 */
	public void testGetTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#setTo(com.fulong.longcon.workflow.Activity)}.
	 */
	public void testSetTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#applyTo(com.fulong.longcon.repository.Node)}.
	 */
	public void testApplyTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#getConditions()}.
	 */
	public void testGetConditions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fulong.longcon.workflow.Transition#setConditions(java.lang.String[])}.
	 */
	public void testSetConditions() {
		fail("Not yet implemented");
	}

}
