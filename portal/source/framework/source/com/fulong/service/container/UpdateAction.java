/**
 * 
 */
package com.fulong.service.container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.BaseAction;
import com.fulong.longcon.workflow.WorkflowService;
import com.fulong.service.ServiceManager;

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
public class UpdateAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServiceForm form = (ServiceForm)aform;
		/*ProcessDefinition definition = getWorkflowService().getDefinition(form.getProcessID());
		TaskActivity activity = (TaskActivity)definition.getActivity(form.getActivityID());
		Parameters orginal = activity.getParameters();
		ServiceParameterAdapter parameters = new ServiceParameterAdapter(orginal);
		Service service = this.getServiceManager().getService(form.getServiceID());
		request.setAttribute(ServiceForm.class.getName(), form);
		service.onUpdate(request, response, parameters);
		definition.save();*/
		StringBuilder serviceParam =new StringBuilder();
		serviceParam.append("<ActualParameters>");
		for (String name : form.getNames()) {
			serviceParam.append("<ActualParameter><name>"); 
			serviceParam.append(name);
			serviceParam.append("</name>");
			for(String value:form.getValues(name)){
				serviceParam.append("<value>");
				serviceParam.append(value);
				serviceParam.append("</value>");
			}
			serviceParam.append("</ActualParameter>");
		}
		serviceParam.append("</ActualParameters>");
		request.setAttribute("serviceParam", serviceParam.toString());
		return mapping.findForward("success");
	}
	 /**
    *
    * @return WorkflowService
    */
   protected WorkflowService getWorkflowService() {
       return (WorkflowService)this.getBeanFactory()
               .getBean("workflow");
   }
   /**
	 *
	 * @return ServiceManager
	 */
	protected ServiceManager getServiceManager() {
		return (ServiceManager)getBeanFactory().getBean("serviceManager");
	}	
}
