package com.fulong.portlet.button.save.multiple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.PortletNodeWorkItem;
import com.fulong.portlet.button.ButtionPortletAction;
import com.fulong.portlet.button.submit.PortletConfig;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public class SaveNodeAction extends ButtionPortletAction {
    protected final Log log = LogFactory.getLog(this.getClass());
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request ActionRequest
     * @param response ActionResponse
     * @return ActionForward
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public ActionForward action(
            ActionMapping mapping,
            ActionForm form,
            ActionRequest request,
            ActionResponse response) throws Exception {
        PortletConfig config = new PortletConfig(request.getPreferences());
        String definitionId = request.getParameter("definition");
        NodeDefinition nodeDefinition = this.getRepository().
                                        getDefinitionManager().getDefinition(definitionId);
        Iterator<PropertyDefinition> propertyDefinitions = nodeDefinition.propertyDefinitions();
        Node owner = null;
        //会员节点父节点都为root by mali 2010-9-8
		if(nodeDefinition.isNodeType("principal-scheme")){
			owner = this.getRepository().getRootNode();
		}else if (request.getPreferences().getValue("owner", "site").equalsIgnoreCase("site")) {
        	if(this.getCurrentSite(request, response)!=null){
        		owner = this.getCurrentSite(request, response).getOwner();
        	}            
        } else {
            owner = this.getCurrentOrg(request, response);
        }
        if (owner == null) {
            owner = this.getPassportProvider().getDefaultOrganization();
        }

        String IDs[] = request.getParameterValues("node");
        for (int j = 0; IDs != null && j < IDs.length; j++) {
            Node node = this.getRepository().getNode(IDs[j]);
            if (node != null) {
                /**
                 * 处理默认值
                 */
                String dValues[] = config.getDefaultValues();
                for (int i = 0; i < dValues.length; i++) {
                    FilterParser parser = this.getFilterParser(request, response);
                    parser.parser(dValues[i]);
                    node.setProperty(parser.getPropertyDefinition(), parser.getValue());
                }

                /**
                 * 填充表单值
                 */
                String ignoreRequest = request.getPreferences().getValue("ignoreRequest", "false");
                if(!ignoreRequest.equals("false")){
                	Map<String ,Object> parameters = request.getParameterMap();
                    while (propertyDefinitions.hasNext()) {
                        PropertyDefinition propertyDefinition = (PropertyDefinition) propertyDefinitions.next();
                        String propName = propertyDefinition.getID();
                        Object value = parameters.get(propName);
                        if (value == null) {
                            continue;
                        } else if (value instanceof FileItem) {
                            FileItem values = (FileItem) value;
                            String path = null;
                            try {
                            	if(nodeDefinition.isNodeType("principal-scheme"))
        							path = this.upload(values, node, (HttpServletRequest) request);
        						else
        							path = this.upload(values, owner, (HttpServletRequest) request);
                                node.setProperty(propName, path);
                            } catch (Exception ex) {
                                log.error("Upload " + values.getName() + " failed.", ex);
                            }
                        } else if (value instanceof FileItem[]) {
                            FileItem[] values = (FileItem[]) value;
                            String path = null;
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < values.length; i++) {
                                try {
                                	if(nodeDefinition.isNodeType("principal-scheme"))
                                		path = this.upload(values[i], node, (HttpServletRequest) request);
                                	else
                                		path = this.upload(values[i], owner, (HttpServletRequest) request);
                                } catch (Exception ex) {
                                    log.error("Upload " + values[i].getName() + " failed.", ex);
                                    continue;
                                }
                                list.add(path);
                            }
                            if (list.size() > 0) {
                                node.setProperty(propName, list.toArray(new String[list.size()]));
                            }
                        } else {
                            String[] values = request.getParameterValues(propName);
                            //兼容异步上传占位符置空处理    by mali 2010-9-8
        					if(values.length == 1 && values[0].equals("clear") && propertyDefinition.getType() == 8)
        						values[0] = null;
                            node.setProperty(propName, values);
                        }
                    }
                }
        		//执行活动，下面为范例代码，未和具体数据相关联		
            	String processId=request.getPreferences().getValue("process", "blank");
        		String activityId = request.getPreferences().getValue("activity", "begin");
        		ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
        		if(definition!=null){
        			Activity activity = definition.getActivity(activityId);
        			if(activity!=null){
        				activity.execute(new PortletNodeWorkItem(node, request, response));
        			}
        		}
            }
        }
        String forwardSelf = request.getPreferences().getValue("forwardSelf", "false");
		if(forwardSelf.equals("false")){
			String path = request.getPreferences().getValue("channel", "");
			if(path!=null&&!path.equals("")){
				response.sendRedirect(path);
			}else{
				this.redirect(request, response, "index.jsp");
			}
		}else{
			response.sendRedirect(request.getParameter("selfURL"));
		}
        return null;
    }
}
