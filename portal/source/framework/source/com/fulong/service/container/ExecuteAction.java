/**
 * 
 */
package com.fulong.service.container;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.service.NodeDefinitionObject;
import com.fulong.service.NodeObject;
import com.fulong.service.Service;
import com.fulong.service.ServiceManager;
import com.fulong.service.ServiceParameters;

/**
 * 
 * 执行一个服务，同时将服务执行情况通过ActionMessage发送给客户端，客户端可以配合MessagesAction来获取执行情况
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
public class ExecuteAction extends ContainerBaseAction {
	private static final int THREADHOLD = 2048;
	private Log log = LogFactory.getLog(ExecuteAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String serviceId = request.getParameter("serviceID"); //服务ID
		String parameters = request.getParameter("parameters"); //服务参数，是一个xml字符串，参见workflow里面的TaskActivity的xml结构
		String msgCode = request.getParameter("msgCode"); //执行信息代码，可以通过这个代码来得到执行的实时情况；
		String[] ids = request.getParameterValues("contentID"); //被操作的内容id，是一个数组
		String definitionID = request.getParameter("categoryID"); //被操作的内容库定义，如果定义这个操作，则执行针对这个内容分类的服务；
		Service service = this.getServiceManager().getService(serviceId);
		String serviceName = this.getServiceManager().getServiceConfig(serviceId).getName();
		ServiceParameters serviceParameters = new ServiceParameterAdapter(parameters);
		Vector<ActionMessage> messages = new Vector<ActionMessage>();
		if(msgCode!=null){
			request.getSession().setAttribute(msgCode, messages);
			ActionMessage message =new ActionMessage("service.log.begin",serviceName);
			messages.add(message);			
		}
		if(definitionID!=null){
			NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionID);
			NodeDefinitionObject event = new NodeDefinitionObject(definition);
			service.process(event, serviceParameters,request);
			if(msgCode!=null){
				ActionMessage message =new ActionMessage("service.definition.log.success",serviceName, definition.getName());
				messages.add(message);
			}
		}
		if (ids != null)
			for (int i = 0; i < ids.length; i++) {
				Node node = this.getRepository().getNode(ids[i]);
				if (node != null) {
					NodeObject event = new NodeObject(node);
					if(messages.size()> THREADHOLD)//防止侵占太多内存；
						messages.clear();
					try{
						service.process(event, serviceParameters,request);
						if(msgCode!=null){
							ActionMessage message =new ActionMessage("service.log.success",serviceName, node.getID());
							messages.add(message);
						}
					} catch(Exception ex){
						if(msgCode!=null){
							ActionMessage message =new ActionMessage("service.log.failed",serviceName, node.getID(), ex.getMessage());
							messages.add(message);							
						}
						log.error(ex.getMessage(),ex);
						
					}
				}
			}
		if(msgCode!=null){
			ActionMessage message =new ActionMessage("service.log.end",serviceName);
			messages.add(message);			
		}
		
		return null;
	}

	/**
	 * 
	 * @return ServiceManager
	 */
	protected ServiceManager getServiceManager() {
		return (ServiceManager) getBeanFactory().getBean("serviceManager");
	}
}
