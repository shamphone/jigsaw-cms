package com.fulong.service.property.deadline;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 最后期限服务
 * 计算距离最后的期限的剩余时间，配置参数：
 * 1．内容分类：用户当前有权限访问的分类。
 * 2．起点时间属性：计算时间的起点时间属性。
 * 3．终止时间属性：计算时间的终止时间属性。
 * 4．期限属性：用于显示现在距离终止时间的期限属性。
 * 5．刷新间隔（小时）。
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
public class DeadlineService extends NodeService{

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
		if (node != null) {
			String startpropID = parameters.getValue("startpropID");
			String deadlinepropID = parameters.getValue("deadlinepropID");
			String deadreduceID=parameters.getValue("deadreducepropID");
			Calendar startpropValue = node.getProperty(startpropID).getValue().getDate();
			long deadreduce=node.getProperty(deadreduceID).getValue().getLong();
			if(startpropValue==null){
				startpropValue = Calendar.getInstance();
			}
			Property deadlineprop = node.getProperty(deadlinepropID);
			
			long reducePropValue=deadreduce;
			
			if(startpropValue!=null&&deadlineprop!=null){
				Calendar c=Calendar.getInstance();
				c.setTime(new Date(startpropValue.getTimeInMillis()+reducePropValue*86400000));
				deadlineprop.setValue(c);
			}
		}
	}
}
