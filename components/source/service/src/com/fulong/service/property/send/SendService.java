package com.fulong.service.property.send;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 属性同步服务
 * 将内容属性值发送到引用属性对应节点，配置参数：
 * 1．内容分类：用户当前有权限访问的分类。
 * 2．引用属性：内容属性值发送到的引用属性。
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
public class SendService extends NodeService{
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
			String refPropID = parameters.getValue("refSendPropID");
			Property refProp=node.getProperty(refPropID);
			String nodeId=refProp.getValue().getString();
			Node n=this.repository.getNode(nodeId);
			Iterator<PropertyDefinition> props=node.getDefinition().propertyDefinitions(false);
			while(props.hasNext()){
				PropertyDefinition prop=props.next();
				Property p=n.getProperty(prop.getID());
				if(p!=null){
					p.setValue(node.getProperty(prop.getID()).getValue());
				}
			}
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
}
