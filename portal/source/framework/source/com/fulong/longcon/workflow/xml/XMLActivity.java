/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ExecutionMode;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.Transition;
import com.fulong.longcon.workflow.WorkItem;
import com.fulong.longcon.workflow.WorkflowService;

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
public class XMLActivity implements Activity {
	protected Element element;
	protected Document document;
	protected ProcessDefinition definition;
	protected WorkflowService service;
	private static Log log=LogFactory.getLog(XMLActivity.class);
	public XMLActivity(Element element, ProcessDefinition definition,WorkflowService service) {
		this.element = element;
		this.definition = definition;
		this.service = service;
		this.document = element.getOwnerDocument();

	}

	protected Element getElement() {
		return this.element;
	}

	public ProcessDefinition getDefinition() {
		return this.definition;
	}

	public String getDescription() {
		NodeList nodes = this.element.getElementsByTagName("Description");
		if (nodes.getLength() == 0)
			return null;
		Element desc = (Element) nodes.item(0);
		nodes = desc.getChildNodes();
		if (nodes.getLength() == 0)
			return null;
		return nodes.item(0).getNodeValue();
	}

	public void setDescription(String description) {
		if (description == null)
			description = "";
		NodeList nodes = this.element.getElementsByTagName("Description");
		Element desc;
		if (nodes.getLength() == 0) {
			desc = document.createElement("Description");
			this.element.appendChild(desc);
		} else
			desc = (Element) nodes.item(0);
		while (desc.hasChildNodes())
			desc.removeChild(desc.getFirstChild());
		desc.appendChild(document.createCDATASection(description));

	}

	public ExecutionMode getFinishMode() {
		return new ExecutionMode(this.element.getAttribute("FinishMode"));
	}

	public String getId() {
		return this.element.getAttribute("Id");
	}

	public Transition[] getIn() {
		Vector<Transition> outs = new Vector<Transition>();
		Transition[] all=this.definition.getTransitions();
		for(int i=0;i<all.length;i++)
			if(all[i].getTo().equals(this))
				outs.add(all[i]);
		return outs.toArray(new Transition[outs.size()]);
	}

	public String getName() {
		return this.element.getAttribute("Name");
	}

	public void setName(String name) {
		this.element.setAttribute("Name", name);
	}

	public Transition[] getOut() {
		Vector<Transition> outs = new Vector<Transition>();
		Transition[] all=this.definition.getTransitions();
		for(int i=0;i<all.length;i++)
			if(all[i].getFrom().equals(this))
				outs.add(all[i]);
		return outs.toArray(new Transition[outs.size()]);
	}

	public ExecutionMode getStartMode() {
		return new ExecutionMode(this.element.getAttribute("StartMode"));

	}

	public boolean isEnd() {
		return false;
	}

	public boolean isMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStart() {
		return this.definition.getBegin().equals(this);
	}

	public void setFinishMode(ExecutionMode mode) {
		this.element.setAttribute("FinishMode", mode.toString());

	}

	public void setStartMode(ExecutionMode mode) {
		this.element.setAttribute("StartMode", mode.toString());

	}

	public boolean equals(Object another) {
		Activity activity = (Activity) another;
		return activity.getDefinition().equals(this.definition) && activity.getId().equals(this.getId());
	}

	public int getType() {
		return INTERACTION;
	}

	/**
	 * 进入活动，对于客户交互活动，进入活动既挂起，等待用户输入后执行execute活动；
	 * 
	 * @param node
	 * @return
	 */
	public void enter(WorkItem node) throws Exception {
		log.debug("Enter Activity ("+this.definition.getName()+","+this.getName()+")");
	}

	/**
	 * 子类使用这个方法来完成执行操作，缺省的交互活动，操作为空
	 * 
	 * @param node
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void doExecute(WorkItem node) throws Exception {

	}



	/**
	 * 执行完成之后，进入后续活动
	 */
	public void execute(WorkItem node) throws Exception {
		Transition[] trans = this.getOut();

		try {
			this.doExecute(node);
		} catch (Exception ex) {
			log.error("Error in execute activity("+this.definition.getName()+","+this.getName()+", go to exception branches.", ex);
			// 处理异常；
			boolean processed = false;
			for (int i = 0; i < trans.length; i++) {
				if (trans[i].getType() == Transition.Condition.EXCEPTION) {
					String condition = trans[i].getCondition();
					if (Class.forName(condition).isInstance(ex)) {
						Activity next = trans[i].getTo();
						next.enter(node);
						processed = true;
					}
				}
			}
			//处理缺省异常
			for (int i = 0; i < trans.length && !processed; i++) {
				if (trans[i].getType() == Transition.Condition.DEFAULTEXCEPTION) {
					String condition = trans[i].getCondition();
					if (Class.forName(condition).isInstance(ex)) {
						Activity next = trans[i].getTo();
						next.enter(node);
						processed = true;
					}
				}

			}
			return;
		}
		log.debug("Activity("+this.definition.getName()+","+this.getName()+") execute successfully.");		
		// 处理绝对转移；
		for (int i = 0; i < trans.length; i++) {
			if (trans[i].getType() == Transition.Condition.NONE) {
				Activity next = trans[i].getTo();
				next.enter(node);
			}
		}
		// 处理条件转移：
		boolean executed = false;
		for (int i = 0; i < trans.length; i++) {
			if (trans[i].getType() == Transition.Condition.CONDITION) {
				if (node.validate(trans[i].getCondition())) {
					executed = true;
					Activity next = trans[i].getTo();
					next.enter(node);
				}
			}
		}
		// 所有条件得不到满足，进入otherwise分支
		for (int i = 0; i < trans.length && !executed; i++) {
			if (trans[i].getType() == Transition.Condition.OTHERWISE) {
					executed = true;
					Activity next = trans[i].getTo();
					next.enter(node);
			}
		}
	}
	
	
	public String getService(){
		Element e=(Element)this.element.getElementsByTagName("TaskApplication");
		String serviceName=null;
		if(e!=null){
			serviceName=e.getAttribute("Id");
		}
		return serviceName;
	}

}
