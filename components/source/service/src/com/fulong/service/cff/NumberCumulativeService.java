package com.fulong.service.cff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 推荐次数累加
 * <p>
 * Title: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2010
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixiang
 * @version 3.1
 */

public class NumberCumulativeService extends NodeService{

	private String editorPath;
	
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
		String[] nodeIds = node.getProperty("content").getArray();
		String propID = parameters.getValue("propID");
		Node content = null;
		for (int i = 0; i < nodeIds.length; i++) {
			content = this.repository.getNode(nodeIds[i]);
			int propValue = (int) content.getProperty(propID).getLong();
			
			//属性值加1
			propValue=propValue+1;
			Property prop = content.getProperty(propID);
			prop.setValue(propValue);
		}
	}
}
