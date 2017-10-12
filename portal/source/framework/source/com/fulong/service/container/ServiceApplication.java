/**
 * 
 */
package com.fulong.service.container;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.workflow.Application;
import com.fulong.longcon.workflow.Parameters;
import com.fulong.longcon.workflow.WorkItem;
import com.fulong.service.NodeObject;
import com.fulong.service.NodeWorkItem;
import com.fulong.service.Service;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class ServiceApplication implements Application {
	private Service service;
	public ServiceApplication(Service service){
		this.service = service;
	}

	public void execute(WorkItem item, Parameters parameters) throws Exception {
		Node node = ((NodeWorkItem)item).getNode();
		ServiceParameterAdapter adapter = new ServiceParameterAdapter(parameters);
		NodeObject event = new NodeObject(node);
		service.process(event, adapter,((NodeWorkItem)item).getHttpRequest());
		
	}

}
