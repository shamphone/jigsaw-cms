package com.fulong.service.dbtj;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * <p>中小司担保机构统计调查系统--生成初始填报数据服务</p>
 * 
 * <p>功能：当激活一个上报任务之后，通过该服务自动将其他所有任务的激活状态置为停止</p>
 * 
 * <p>Title: Coolink平台协同服务管理系统</p>
 * 
 * <p>Description: Coolink平台协同服务管理系统</p>
 * 
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 * 
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 * 
 * @author lichengzhao
 * 
 * @version 1.0
 */
public class StopActivationtService extends NodeService {
	
	private String editorPath;
	private String missionDefinitionId;
	private String activationStatePropertyDefinitionId;
	private String activeStateId;
	private String stopStateId;
	
	public void setMissionDefinitionId(String missionDefinitionId) {
		this.missionDefinitionId = missionDefinitionId;
	}

	public void setActivationStatePropertyDefinitionId(
			String activationStatePropertyDefinitionId) {
		this.activationStatePropertyDefinitionId = activationStatePropertyDefinitionId;
	}

	public void setActiveStateId(String activeStateId) {
		this.activeStateId = activeStateId;
	}

	public void setStopStateId(String stopStateId) {
		this.stopStateId = stopStateId;
	}

	public void setEditorPath(String editorPath) {
		this.editorPath = editorPath;
	}

	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		System.out.println("In " + this);
		// 如果node的激活状态为停止，则不进行任何操作
		if (stopStateId.equals(node.getProperty(activationStatePropertyDefinitionId).getReference().getID()))
			return;
		// 查找状态为激活的任务
		NodeDefinition missionDefinition = this.repository.getDefinitionManager().getDefinition(missionDefinitionId);
		Query query = this.repository.getQueryManager().createQuery(missionDefinition, Query.SQL);
		query.filterByProperty(activationStatePropertyDefinitionId, activeStateId);
		Iterator<Node> nodes = query.nodes();
		// 将找到的任务的状态置为停止
		while (nodes.hasNext()) {
			Node temp = nodes.next();
			// 如果temp和node是一个任务则不改变他的激活状态
			if (temp.getID().equals(node.getID()))
				continue;
			temp.setProperty(activationStatePropertyDefinitionId, stopStateId);
		}
	}
}
