/**
 * 
 */
package com.fulong.service;

import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.service.container.ServiceForm;

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
public class NodeService implements Service {
	private State state;
	protected Repository repository;	
	protected SiteFactory siteFactory;
	private String editorPath;
	protected ServiceConfig config;
	
	public void setEditorPath(String path){
		this.editorPath = path;
	}

	public NodeService(){
		this.state=State.NEW;
	}
	public void setRepository(Repository repository){
		this.repository= repository;
	}	
	public void setSiteFactory(SiteFactory siteFactory){
		this.siteFactory= siteFactory;
	}
	/**
	 * just do nothing in base.
	 */
	public void init(ServiceConfig config) {
		this.config=config;
		this.init();
		this.state = State.RUNNABLE;
	}
	/**
	 * 子服务继承这个类
	 */
	public void init(){
		
	}

	public void onEdit(ServiceRequest request, ServiceResponse response) throws Exception {
		ServiceParameters parameters=request.getServiceParameters();
		String path = this.doEdit((HttpServletRequest)request, (HttpServletResponse)response, parameters);
		ServiceForm form = (ServiceForm) request.getAttribute(ServiceForm.class.getName());
		for (Enumeration<String> names = parameters.getNames(); names.hasMoreElements();) {
			String name = names.nextElement();
			form.setValues(name, parameters.getValues(name));
		}
		if(path==null){
			path="/service/noSuchService.jsp";
		}
		ServletContext context = (ServletContext)this.config.getServiceContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	public void onUpdate(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		ServiceForm form = (ServiceForm) request.getAttribute(ServiceForm.class.getName());
		for (String name : form.getNames()) {
			parameters.setValues(name, form.getValues(name));
		}
		this.doUpdate(request, response, parameters);
	}

	public void process(ServiceObject event, ServiceParameters parameters ,HttpServletRequest request) throws Exception {
		if(this.state!=State.RUNNABLE)
			throw new IllegalStateException("Service "+ this.config.getName()+" stopped.");
		switch(event.getType()){
		case NodeObject.Type:
			NodeObject nodeEvent = (NodeObject) event;
			this.doProcess(nodeEvent.getNode(), parameters, request );
			break;
		case NodeDefinitionObject.Type:
			this.doProcess(((NodeDefinitionObject)event).getNodeDefinition(), parameters ,request);
			break;
			
		}
	}

	/**
	 * 子类实现这个方法，父类已经缺省的实现对服务参数的填充。大部分子类是不需要进行额外处理的，除非有使用checkbox的，需要对相应的值就行处理
	 * 
	 * @param request
	 * @param response
	 * @param parameters
	 * @throws Exception
	 */
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {

	}
	
	

	/**
	 * 子类通过这个方法来初始化一些页面上的Bean。
	 * 
	 * @param request
	 * @param response
	 * @param parameters
	 * @return 如果要导航到Jsp页面，则通过这个方法来实现；
	 * @throws Exception
	 */
	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}

	/**
	 * 执行服务；
	 * 
	 * @param node
	 * @param parameters
	 * @param request TODO
	 * @throws Exception
	 */
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		doProcess(node, parameters);
	}
	
	@Deprecated
	public void doProcess(Node node, ServiceParameters parameters) throws Exception {
	}

	/* 
	 * 如果是针对NodeDefinition的服务，则通过这个方法来执行
	 * @param node
	 * @param parameters
	 * @throws Exception
	 * @author lixf
	 * @lastupdate 2009-9-29上午10:44:30
	 */
	public void doProcess(NodeDefinition node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		doProcess(node, parameters);
	}
	
	@Deprecated
	public void doProcess(NodeDefinition node, ServiceParameters parameters ) throws Exception {
	}
	
	/* (non-Javadoc)
	 * @see com.fulong.service.Service#stop()
	 */
	public void stop() {
		this.state = State.TERMINATED;
		
	}

	public State getState() {
		return this.state;
	}

	@Override
	public void activate() {
		this.state = State.RUNNABLE;
		
	}

}
