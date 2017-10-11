package com.fulong.service.dbtj;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.service.ServiceParameters;

/**
 * <p>中小司担保机构统计调查系统--汇总服务</p>
 * 
 * <p>功能：汇总全国数据服务</p>
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
public class GatherService extends GenerateDataService {
	
	private String editorPath;
	private String surveyDefinitionId;
	private String gatherDefinitionId;
	private String missionPropertyDefinitionId;

	public void setEditorPath(String editorPath) {
		this.editorPath = editorPath;
	}

	public void setSurveyDefinitionId(String surveyDefinitionId) {
		this.surveyDefinitionId = surveyDefinitionId;
	}

	public void setGatherDefinitionId(String gatherDefinitionId) {
		this.gatherDefinitionId = gatherDefinitionId;
	}

	public void setMissionPropertyDefinitionId(String missionPropertyDefinitionId) {
		this.missionPropertyDefinitionId = missionPropertyDefinitionId;
	}

	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}

	public void doProcess(Node node, ServiceParameters parameters) throws Exception {
		// 获取当前上报任务的ID
		String currentMissionId = node.getProperty(missionPropertyDefinitionId).getReference().getID();
		Node currnetMissionNode = this.repository.getNode(currentMissionId);
		// 获取当前上报任务对应的汇总记录
		NodeDefinition gatherDefinition = this.repository.getDefinitionManager().getDefinition(gatherDefinitionId);
		Query queryGather = this.repository.getQueryManager().createQuery(gatherDefinition, Query.SQL);
		queryGather.filterByRefed(gatherDefinitionId,missionPropertyDefinitionId, currnetMissionNode);
		Node gather = queryGather.nodes().nextNode();
		// 获取当前上报任务对应的各省已上报的数据
		NodeDefinition surveyDefinition = this.repository.getDefinitionManager().getDefinition(surveyDefinitionId);
		Query querySurveies = this.repository.getQueryManager().createQuery(surveyDefinition, Query.SQL);
		querySurveies.filterByRefed(gatherDefinitionId,missionPropertyDefinitionId, currnetMissionNode);
		Iterator<Node> surveies = querySurveies.nodes();
		Node survey = null;
		long[] items = new long [152];
		String baseName = "prop";
		long data = 0;
		while (surveies.hasNext()) {
			survey = surveies.next();
			for (int i = 0; i < items.length; i+=2) {
				data = survey.getProperty(baseName + (i+1)).getLong();
				items[i] += data != 0 ? data : survey.getProperty(baseName + i).getLong();
			}
		}
		for (int i = 0; i < items.length; i+=2)
			gather.setProperty(baseName + i, items[i]);
	}
}
