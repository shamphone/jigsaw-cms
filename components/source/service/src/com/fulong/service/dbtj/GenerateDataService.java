package com.fulong.service.dbtj;

import java.util.Calendar;
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
 * <p>功能：当新建一个上报任务之后，通过该服务自动生成关于本任务的37条省级上报数据，以及一条汇总数据</p>
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
public class GenerateDataService extends NodeService {
	
	private String editorPath;
	private String surveyDefinitionId;
	private String gatherDefinitionId;
	private String organTypePropertyDefinitionId;
	private String provinceOrganTypePropertyId;
	private String ministryOrganId;
	private String orderPropertyDefinitionId;
	private String orderType;
	private String userDefinitionId;
	private String creatorPropertyDefinitionId;
	private String reportStatePropertyDefinitionId;
	private String reportStateId;
	private String createTimePropertyDefinitionId;
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

	public void setOrganTypePropertyDefinitionId(
			String organTypePropertyDefinitionId) {
		this.organTypePropertyDefinitionId = organTypePropertyDefinitionId;
	}

	public void setProvinceOrganTypePropertyId(String provinceOrganTypePropertyId) {
		this.provinceOrganTypePropertyId = provinceOrganTypePropertyId;
	}

	public void setMinistryOrganId(String ministryOrganId) {
		this.ministryOrganId = ministryOrganId;
	}

	public void setOrderPropertyDefinitionId(String orderPropertyDefinitionId) {
		this.orderPropertyDefinitionId = orderPropertyDefinitionId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setUserDefinitionId(String userDefinitionId) {
		this.userDefinitionId = userDefinitionId;
	}

	public void setCreatorPropertyDefinitionId(String creatorPropertyDefinitionId) {
		this.creatorPropertyDefinitionId = creatorPropertyDefinitionId;
	}

	public void setReportStatePropertyDefinitionId(
			String reportStatePropertyDefinitionId) {
		this.reportStatePropertyDefinitionId = reportStatePropertyDefinitionId;
	}

	public void setReportStateId(String reportStateId) {
		this.reportStateId = reportStateId;
	}

	public void setCreateTimePropertyDefinitionId(
			String createTimePropertyDefinitionId) {
		this.createTimePropertyDefinitionId = createTimePropertyDefinitionId;
	}

	public void setMissionPropertyDefinitionId(String missionPropertyDefinitionId) {
		this.missionPropertyDefinitionId = missionPropertyDefinitionId;
	}

	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		System.out.println("In " + this);
		NodeDefinition surveyDefinition = this.repository.getDefinitionManager().getDefinition(surveyDefinitionId);
		NodeDefinition gatherDefinition = this.repository.getDefinitionManager().getDefinition(gatherDefinitionId);
		NodeDefinition organDefinition = this.repository.getDefinitionManager().getDefinition(userDefinitionId);
		Query queryProvinceOrgan = this.repository.getQueryManager().createQuery(organDefinition, Query.SQL);
		queryProvinceOrgan.filterByProperty(organTypePropertyDefinitionId, provinceOrganTypePropertyId);
		queryProvinceOrgan.sortByProperty(orderPropertyDefinitionId, "desc".equals(orderType));
		Iterator<Node> provinces = queryProvinceOrgan.nodes();
		Node province = null;
		Node survey = null;
		// 遍历所有省级用户，针对每个省级用户创建一条新的上报记录
		while (provinces.hasNext()) {
			province = provinces.next();
			survey = this.repository.getRootNode().addNode(surveyDefinition, "content");
			// 设置创建者
			survey.setProperty(creatorPropertyDefinitionId, province.getID());
			// 设置创建时间
			survey.setProperty(createTimePropertyDefinitionId, Calendar.getInstance());
			// 设置默认上报状态
			survey.setProperty(reportStatePropertyDefinitionId, reportStateId);
			// 设置相关上报任务
			survey.setProperty(missionPropertyDefinitionId, node.getID());
		}
		// 创建一条新的汇总记录
		Node gather = this.repository.getRootNode().addNode(gatherDefinition, "content");
		// 设置创建者，创建者为唯一的部级用户
		gather.setProperty(creatorPropertyDefinitionId, ministryOrganId);
		// 设置创建时间
		gather.setProperty(createTimePropertyDefinitionId, Calendar.getInstance());
		// 设置相关上报任务
		gather.setProperty(missionPropertyDefinitionId, node.getID());
	}
}
