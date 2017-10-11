package com.fulong.service.sysp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.value.ReferenceValue;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 沈阳行政审批--新增项目服务
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
 * @author lichengzhao
 * @version 3.1
 */
public class InsertItemService extends NodeService {
	
	private String editorPath;
	
	public void setEditorPath(String path){
		this.editorPath = path;
	}

	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}
	@Override
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		Node user = node.getProperty("creator").getReference();
		Node project = node.getProperty("project").getReference();
		Property prop = null;
		Node refer = null;
		// 办件类型
		if (project.getProperty("type") != null)
			node.setProperty("type", project.getProperty("type").getString());
		// 办理单位
		if (project.getProperty("organization") != null)
			node.setProperty("organization", project.getProperty("organization").getString());
		// 用户名称
		if (user.getProperty("title") != null)
			node.setProperty("title", user.getProperty("title").getString());
		// match_id
		if (user.getProperty("match_id") != null)
			node.setProperty("match_id", user.getProperty("match_id").getString());
		// 组织机构代码
		if (user.getProperty("org-code") != null)
			node.setProperty("org-code", user.getProperty("org-code").getString());
		// 工商注册号
		if (user.getProperty("prop640") != null)
			node.setProperty("prop640", user.getProperty("prop640").getString());
		// 税务登记号
		if (user.getProperty("prop343") != null)
			node.setProperty("prop343", user.getProperty("prop343").getString());
		// --------单位性质
		prop = user.getProperty("org-type");
		if (prop != null) {
			refer = prop.getReference();
			prop = refer.getProperty("value");
			if (prop != null)
				node.setProperty("org-type", prop.getString());
		}
		// 注册资金
		if (user.getProperty("prop937") != null)
			node.setProperty("prop937", user.getProperty("prop937").getString());
		// --------资金类型
		prop = user.getProperty("bankroll");
		if (prop != null) {
			refer = prop.getReference();
			prop = refer.getProperty("value");
			if (prop != null)
				node.setProperty("bankroll", prop.getString());
		}
		// --------登记注册类型
		prop = user.getProperty("prop500");
		if (prop != null) {
			refer = prop.getReference();
			prop = refer.getProperty("value");
			if (prop != null)
				node.setProperty("prop500", prop.getString());
		}
		// 联系人
		if (user.getProperty("prop703") != null)
			node.setProperty("prop703", user.getProperty("prop703").getString());
		// 企业电话
		if (user.getProperty("prop546") != null)
			node.setProperty("prop546", user.getProperty("prop546").getString());
		// 企业手机
		if (user.getProperty("prop0") != null)
			node.setProperty("prop0", user.getProperty("prop0").getString());
		// 企业电邮
		if (user.getProperty("Email") != null)
			node.setProperty("Email", user.getProperty("Email").getString());
		// 法人代表
		if (user.getProperty("org-representive") != null)
			node.setProperty("org-representive", user.getProperty("org-representive").getString());
		// --------法人证件名称
		prop = user.getProperty("prop328");
		if (prop != null) {
			refer = prop.getReference();
			prop = refer.getProperty("value");
			if (prop != null)
				node.setProperty("prop328", prop.getString());
		}
		// 法人证件号码
		if (user.getProperty("prop687") != null)
			node.setProperty("prop687", user.getProperty("prop687").getString());
		// 注册地址
		if (user.getProperty("prop31") != null)
			node.setProperty("prop31", user.getProperty("prop31").getString());
		// 邮编
		if (user.getProperty("zipcode") != null)
			node.setProperty("zipcode", user.getProperty("zipcode").getString());
		
		// 所需材料
		prop = project.getProperty("required-information");
		if (prop == null)
			return;
		Value[] materials = prop.getValues();
		if (materials == null || materials.length == 0)
			return;
		Value[] values = node.getProperty("declare-table").getValues();
		if (values == null || values.length == 0)
			return;
		Node material = null;
		String path = null;
		String materialName = null;
		for (int i = 0; i < values.length && i < materials.length; i++) {
			if (values[i] == null || materials[i] == null)
				continue;
			material = ((ReferenceValue) materials[i]).getReference();
			path = values[i].getString(); 
			if (path == null || material == null)
				continue;
			// path eg: /1000000000000/91w4D.tmp.jpg
			Node resource = this.repository.getNodeByPath(path);
			if (resource == null)
				continue;
			materialName = material.getProperty("title").getString();
			if (materialName != null)
				resource.setProperty("title", materialName.trim());
		}
	}
}
