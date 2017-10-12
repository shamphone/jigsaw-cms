/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.common.DomUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.Transition;

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
public class XMLTransition implements Transition {

	private Element element;
	private Document document;
	private ProcessDefinition definition;
	

	public XMLTransition(Element element, ProcessDefinition definition) {
		this.element = element;
		this.document = this.element.getOwnerDocument();
		this.definition = definition;
	}

	protected Element getElement() {
		return this.element;
	}

	/**
	 * 
	 */
	public boolean applyTo(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 获取对应的definition
	 */
	public ProcessDefinition getDefinition() {
		return this.definition;
	}

	/**
	 * 描述放在description标签中
	 */
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

	/**
	 * 起始活动存放在属性From中
	 */
	public Activity getFrom() {
		String id = this.element.getAttribute("From");
		return this.definition.getActivity(id);
	}

	/**
	 * 起始活动存放在属性From中
	 */
	public void setFrom(Activity activity) {
		this.element.setAttribute("From", activity.getId());
	}

	/**
	 * 对应的转移ID
	 */
	public String getId() {
		return this.element.getAttribute("Id");
	}

	/**
	 * 获取名称
	 */
	public String getName() {
		return this.element.getAttribute("Name");
	}

	/**
	 * 修改名称
	 */
	public void setName(String name) {
		if (name == null)
			throw new IllegalArgumentException("transition name should not be null");
		this.element.setAttribute("Name", name);

	}

	/**
	 * 获取终止属性
	 */
	public Activity getTo() {
		String id = this.element.getAttribute("To");
		return this.definition.getActivity(id);
	}

	public void setTo(Activity activity) {
		this.element.setAttribute("To", activity.getId());

	}

	/**
	 * 获取所有转移条件
	 */
	public String getCondition() {
		return DomUtils.getValue(this.element, "Transition/Condition");
	}

	/**
	 * 设置转移条件
	 */
	public void setCondition(String conditions) {
		Element elem = DomUtils.getElement(this.element, "Transition/Condition");
		if (conditions != null) {
			if (elem == null) {
				elem = document.createElement("Condition");
				element.appendChild(elem);
			}
			element.appendChild(document.createTextNode(conditions));
		} else {
			if (elem != null)
				this.element.removeChild(elem);
		}
	}

	/**
	 * 转移类型
	 * 
	 * @return
	 */
	public int getType() {
		Element elem = DomUtils.getElement(this.element, "Transition/Condition");
		if(elem == null)
			return Condition.NONE;
		String name = elem.getAttribute("Type");
		if(name == null)
			return Condition.NONE;
		return Condition.valueFromName(name);
		
	}

	public void setType(int type) {
		Element elem = DomUtils.getElement(this.element, "Transition/Condition");
		if(elem == null){
			if(type== Condition.NONE)
				return;
			elem = document.createElement("Condition");
			element.appendChild(elem);
		}
		elem.setAttribute("Type", Condition.nameFromValue(type));
		
	}

}
