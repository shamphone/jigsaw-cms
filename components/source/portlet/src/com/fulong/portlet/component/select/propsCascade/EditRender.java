package com.fulong.portlet.component.select.propsCascade;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.FormEditRender;
import com.fulong.portlet.PortletPreferencesForm;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-9-8	
 * @version 1.0.1
 */
public class EditRender extends FormEditRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		//关联属性
        String[] propertyIds = request.getPreferences().getValues("propertyIds", new String[0]);
        PropertyDefinition[] properties = new PropertyDefinition[propertyIds.length];
        for(int i=0;i<propertyIds.length;i++){
        	properties[i] = definition.getPropertyDefinition(propertyIds[i]);
        }
        request.setAttribute("properties", properties);
        
        //字典大纲
        String category = request.getPreferences().getValue("category", null);
    	if (category != null && category.length() > 0) {
    		NodeDefinition nodeDefinition = this.getRepository().getDefinitionManager().getDefinition(category);
    		request.setAttribute("category",nodeDefinition);
    		
    		//复合属性
    		String compositeProp = request.getPreferences().getValue("compositeProp", null);
    		if (nodeDefinition!=null&&compositeProp != null && compositeProp.length() > 0) {
    			request.setAttribute("composite", nodeDefinition.getPropertyDefinition(compositeProp));
    		}
    		
    		//显示属性
            String propertyId = request.getPreferences().getValue("propertyId", null);
            if (nodeDefinition!=null&&propertyId != null && propertyId.length() > 0) {
        		request.setAttribute("property", nodeDefinition.getPropertyDefinition(propertyId));
            }
            
            //选项值
            String value = request.getPreferences().getValue("value", null);
            if (nodeDefinition!=null&&value != null && value.length() > 0) {
            	request.setAttribute("value", nodeDefinition.getPropertyDefinition(value));
            }
        }
    	
        PortletPreferencesForm aForm = (PortletPreferencesForm) form;
        aForm.setPreference("definitionId", definition.getID());
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
