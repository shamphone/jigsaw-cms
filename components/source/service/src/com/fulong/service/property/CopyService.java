package com.fulong.service.property;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 同大纲下属性拷贝服务
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
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
public class CopyService extends NodeService {

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			String sourcePropID=parameters.getValue("sourcePropID");
			String destinationPropID=parameters.getValue("destinationPropID");
			Property source=node.getProperty(sourcePropID);
			Property destination=node.getProperty(destinationPropID);
			if(source==null){
				destination.setValue(node.getID());
			}
			else{
				destination.setValue(source.getValue());
			}
		}
	}
}
