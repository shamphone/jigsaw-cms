package com.fulong.service.property.reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Repository;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 将引用属性对应大纲名称填入某指定属性服务
 * 提取引用属性的大纲名称，配置参数：
 * 1．内容分类：用户当前有权限访问的分类。
 * 2．引用属性：所要提取大纲名称的引用属性。
 * 3．目标属性：大纲名称提取后所存入的属性。
 * 
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
public class ReferenceService extends NodeService{
	private String editorPath;
	private Repository repository;

	public void setEditorPath(String path) {
		this.editorPath = path;
	}

	@Override
	public String doEdit(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters)
			throws Exception {
		return this.editorPath;
	}

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			String refPropID = parameters.getValue("refPropID");
			String stringPropID =parameters.getValue("stringPropID");
			Property refProp=node.getProperty(refPropID);
			Property stringProp=node.getProperty(stringPropID);
			String nodeId=refProp.getValue().getString();
			Node n=this.repository.getNode(nodeId);
			stringProp.setValue(n.getDefinition().getName());
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
