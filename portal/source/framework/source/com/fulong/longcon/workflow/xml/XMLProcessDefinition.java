/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fulong.common.DomUtils;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.Parameters;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.Transition;
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
public class XMLProcessDefinition implements ProcessDefinition {
	public File file;
	private Element root;
	private Document document;
	private Map<String, Activity> activities;
	private Map<String, Transition> transitions;
	private WorkflowService service;
	private Parameters parameters;
	
//	private static Log log = LogFactory.getLog(XMLProcessDefinition.class);

	/**
	 * 加载并初始化过程定义
	 * 
	 * @param file
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public XMLProcessDefinition(File file, WorkflowService service)
			throws ParserConfigurationException, SAXException, IOException {
		this.file = file;
		this.service = service;
		this.processDocument();
		this.processActivities();
		this.processTransitions();
		this.processParameters();
		
	}
	
	private void processParameters(){
		String headerTag = "ProcessHeader";
		NodeList nodes = this.root.getElementsByTagName(headerTag);
		Element header = null;
		if (nodes.getLength() == 0){
			header = this.root.getOwnerDocument().createElement("ProcessHeader");
			this.root.appendChild(header);
		}else{
			header = (Element)nodes.item(0);
		}
		nodes = header.getElementsByTagName("FormalParameters");
		Element params = null;
		if (nodes.getLength() > 0)
			params = (Element) nodes.item(0);
		else {
			params = document.createElement("FormalParameters");
			header.appendChild(params);
		}
		this.parameters = new XMLFormalParameters(params);		
	}

	private void processDocument() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		document = db.parse(file);
		root = document.getDocumentElement();
		root = (Element) root.getElementsByTagName("WorkflowProcess").item(0);

	}

	private void processActivities() {
		NodeList nodes = root.getElementsByTagName("Activity");
		this.activities = Collections
				.synchronizedMap(new LinkedHashMap<String, Activity>());
		for (int i = 0; i < nodes.getLength(); i++) {
			Element actElem = (Element) nodes.item(i);
			Activity activity;
			if (actElem.getElementsByTagName("Route").getLength() > 0) {
				activity = new XMLRouteActivity(actElem, this, this.service);
			} else if (actElem.getElementsByTagName("Task").getLength() > 0) {
				activity = new XMLTaskActivity(actElem, this, this.service);
			} else if (actElem.getElementsByTagName("Implementation")
					.getLength() > 0) {
				activity = new XMLImpActivity(actElem, this, this.service);
			} else
				activity = new XMLActivity(actElem, this, this.service);
			this.activities.put(activity.getId(), activity);
		}

	}

	private void processTransitions() {
		NodeList nodes = root.getElementsByTagName("Transition");
		this.transitions = Collections
				.synchronizedMap(new LinkedHashMap<String, Transition>());
		for (int i = 0; i < nodes.getLength(); i++) {
			Element actElem = (Element) nodes.item(i);
			Transition transition = new XMLTransition(actElem, this);
			this.transitions.put(transition.getId(), transition);
		}
	}

	/**
	 * 持久化保存
	 * 
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public synchronized void save() throws IOException, ParserConfigurationException, SAXException {
		DomUtils.save(this.root.getOwnerDocument(), file);
		this.processDocument();
		this.processActivities();
		this.processTransitions();
	}

	/**
	 * 获取所有活动
	 */
	public Activity[] getActivities() {
		return this.activities.values().toArray(
				new Activity[this.activities.size()]);
	}

	/**
	 * 获取指定ID的活动
	 */
	public Activity getActivity(String ID) {
		if (ID == null) {
			return null;
		}
		return this.activities.get(ID);
	}

	/**
	 * 获取初始活动
	 */
	public Activity getBegin() {
		String id = this.root.getAttribute("DefaultStartActivityId");
		return this.getActivity(id);
	}

	public String getDescription() {
		String headerTag = "ProcessHeader";
		NodeList nodes = this.root.getElementsByTagName(headerTag);
		if (nodes.getLength() == 0)
			return null;
		Element header = (Element) nodes.item(0);
		nodes = header.getElementsByTagName("Description");
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
		String headerTag = "ProcessHeader";
		Document document = this.root.getOwnerDocument();
		NodeList nodes = this.root.getElementsByTagName(headerTag);
		Element header;
		if (nodes.getLength() == 0) {
			header = document.createElement(headerTag);
			root.insertBefore(header, root.getFirstChild());
		} else
			header = (Element) nodes.item(0);
		nodes = header.getElementsByTagName("Description");
		Element desc;
		if (nodes.getLength() == 0) {
			desc = document.createElement("Description");
			header.appendChild(desc);
		} else
			desc = (Element) nodes.item(0);
		while (desc.hasChildNodes())
			desc.removeChild(desc.getFirstChild());
		desc.appendChild(document.createCDATASection(description));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.workflow.ProcessDefinition#getEnd()
	 */
	public Activity getEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取ID
	 */
	public String getId() {
		return this.root.getAttribute("Id");
	}

	/**
	 * 修改ID
	 * 
	 * @param id
	 */
	public void setId(String id) {
		root.setAttribute("Id", id);

	}

	/**
	 * 名称
	 */
	public String getName() {
		return this.root.getAttribute("Name");
	}

	/**
	 * 设置名称
	 */
	public void setName(String name) {
		this.root.setAttribute("Name", name);
	}

	/**
	 * 获取转移
	 */
	public Transition getTransition(String ID) {
		if (ID == null) {
			return null;
		}
		return this.transitions.get(ID);
	}

	/**
 * 
 */
	public Transition[] getTransitions() {
		return this.transitions.values().toArray(
				new Transition[this.transitions.size()]);
	}

	/**
	 * 等值判断
	 */
	public boolean equals(Object another) {
		ProcessDefinition definition = (ProcessDefinition) another;
		return definition.getId().equals(this.getId());
	}

	/**
	 * 产生活动的ID
	 * 
	 * @param prefix
	 * @return
	 */
	private String genActivityId(String prefix) {
		int count = 0;
		while (this.getActivity(prefix + count) != null)
			count++;
		return prefix + count;
	}

	/**
	 * 创建活动，产生必要的XML片段 <Activity Id="signin" Name="登入活动" StartMode="Automatic">
	 * <Description> 登入活动的描述</Description> </Activity>
	 */
	public Activity createActivity(String ID, String name) {
		String id = null;
		if (ID != null) {
			id = ID;
		} else {
			id = this.genActivityId("activity");
		}
		Element activity = this.document.createElement("Activity");
		activity.setAttribute("Id", id);
		activity.setAttribute("Name", name);
		activity.setAttribute("StartMode", "Manual");
		Element activities = DomUtils.getElement(this.root,
				"WorkflowProcess/Activities");
		activities.appendChild(activity);
		Activity result = new XMLActivity(activity, this, this.service);
		this.activities.put(id, result);
		return result;

	}

	public Activity createRouteActivity(String ID, String name) {
		String id = null;
		if (ID != null) {
			id = ID;
		} else {
			id = this.genActivityId("route");
		}
		Element activity = this.document.createElement("Activity");
		activity.setAttribute("Id", id);
		activity.setAttribute("Name", name);
		Element route = this.document.createElement("Route");
		activity.appendChild(route);
		Element activities = DomUtils.getElement(this.root,
				"WorkflowProcess/Activities");
		activities.appendChild(activity);
		Activity result = new XMLRouteActivity(activity, this, this.service);
		this.activities.put(id, result);
		return result;

	}

	public Activity createTaskActivity(String ID, String service, String name) {
		String id = null;
		if (ID != null) {
			id = ID;
		} else {
			id = this.genActivityId("task");
		}
		Element activities = DomUtils.getElement(this.root,
				"WorkflowProcess/Activities");
		Element activity = this.document.createElement("Activity");
		activity.setAttribute("Id", id);
		activity.setAttribute("Name", name);
		activities.appendChild(activity);
		Element impl = this.document.createElement("Implementation");
		activity.appendChild(impl);
		Element task = this.document.createElement("Task");
		impl.appendChild(task);
		Element app = this.document.createElement("TaskApplication");
		app.setAttribute("Id", service);
		task.appendChild(app);
		Element params = this.document.createElement("ActualParameters");
		app.appendChild(params);
		Activity result = new XMLTaskActivity(activity, this, this.service);
		this.activities.put(id, result);
		return result;
	}

	public void delete(Activity activity) {
		this.activities.remove(activity.getId());
		XMLActivity xml = (XMLActivity) activity;
		Element elem = xml.getElement();
		elem.getParentNode().removeChild(elem);

	}

	/**
	 * 产生转移的ID
	 * 
	 * @param prefix
	 * @return
	 */
	private String genTransitionId(String prefix) {
		int count = 0;
		while (this.getTransition(prefix + count) != null)
			count++;
		return prefix + count;
	}

	public Transition createTransition(String ID, Activity begin, Activity end) {
		String id = null;
		if (ID != null) {
			id = ID;
		} else {
			id = this.genTransitionId("transition");
		}
		Element transition = this.document.createElement("Transition");
		transition.setAttribute("Id", id);
		transition.setAttribute("Name", id);
		transition.setAttribute("From", begin.getId());
		transition.setAttribute("To", end.getId());
		Element transitions = DomUtils.getElement(this.root,
				"WorkflowProcess/Transitions");
		transitions.appendChild(transition);
		Transition result = new XMLTransition(transition, this);
		this.transitions.put(id, result);
		return result;
	}

	public void delete(Transition transition) {
		this.transitions.remove(transition.getId());
		XMLTransition xml = (XMLTransition) transition;
		Element elem = xml.getElement();
		elem.getParentNode().removeChild(elem);

	}

	public String getXML() throws TransformerException {

            Source source = new DOMSource(document);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
    }



	public void setXML(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		document = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		root = document.getDocumentElement();
		root = (Element) root.getElementsByTagName("WorkflowProcess").item(0);
	}

	public Parameters getFormalParameters() {
		return this.parameters;
	}
	
	public String getModule(){
		String module = this.root.getAttribute("Module");
		if(module==null||module.equals("")){
			//流程设计问题,修改于2011年8月9日by梁树辉
			String subStr = "\\WEB-INF\\workflow";
			subStr = FilenameUtils.normalize(subStr);
			int index =  file.getPath().lastIndexOf(subStr);
			String path = file.getPath().substring(0,index);
			System.out.println("xxxx:"+path);
			module = new File(path).getName();
			System.out.println("module:..."+module);
			if(module.equals("web")){
				
				module = new File(path).getParentFile().getName();
			}
		}
		return module;
	}
	
	public void setModule(String module){
		this.root.setAttribute("Module", module);
	}

}
