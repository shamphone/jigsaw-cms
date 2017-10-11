package com.fulong.service.content.deliver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 内容投递服务
 * <p>
 * Title: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author sunyuchao
 * @version 3.1
 */
public class DeliverService extends NodeService {
	
	private Repository repository;
	private String editorPath;
	
	public void setEditorPath(String path){
		this.editorPath = path;
	}


	@Override
	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}


	@Override
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		super.doUpdate(request, response, parameters);
	}


	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		NodeDefinition nd = this.repository.getDefinitionManager().getDefinition(parameters.getValue("deliverCategoryID"));
		node.setDefinition(nd);
	}


	public Repository getRepositiory() {
		return repository;
	}


	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
}
