package com.fulong.service.property.compare;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 属性值比较服务
 * 比较两个日期属性值，配置参数：
 * 1．内容分类：用户当前有权限访问的分类。
 * 2．属性1：用于比较的日期属性。
 * 3．属性2：用于比较的日期属性。
 * 4．比较结果属性：比较后显示结果的属性。
 * 5．结果显示属性1>属性2：当属性1>属性2时显示的结果。
 * 6．结果显示属性1<属性2：当属性1<属性2时显示的结果。
 * 7．差值属性：两个比较属性的差值属性。
 * 8．刷新间隔（小时）。
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
public class CompareService extends NodeService{

	private String editorPath;
	private CompareValue compareValue;
	public void init(){
		super.init();
		compareValue=new CompareValue(repository);
		compareValue.setDaemon(true);		
		compareValue.start();
	}
	
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
			String prop1ID = parameters.getValue("prop1ID");
			String prop2ID = parameters.getValue("prop2ID");
			String resultID = parameters.getValue("resultPropID");
			String reducePropID=parameters.getValue("reducePropID");
			Calendar prop1 = node.getProperty(prop1ID).getValue().getDate();
			if(prop1==null){
				prop1 = Calendar.getInstance();
			}
			Calendar prop2 = node.getProperty(prop2ID).getValue().getDate();
			Property resultProp = node.getProperty(resultID);
			String resultMore = parameters.getValue("resultMore");
			String resultLess = parameters.getValue("resultLess");
			if(prop1!=null&&prop2!=null){
				int i = prop1.compareTo(prop2);
				if (i == 1) {//prop1>prop2
					resultProp.setValue(resultMore);
				} else {
					resultProp.setValue(resultLess);
				}
				long day=(prop2.getTimeInMillis()-prop1.getTimeInMillis())/86400000;
				if(day<0){
					day=0;
				}
				node.getProperty(reducePropID).setValue(day);
			compareValue.setParameters(parameters);
			}
		}
	}

	
}
