package com.fulong.cms.property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.PropertyForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public class EditAction extends PropertyBaseAction {

    /**
     * definitionPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward propertyPerform(ActionMapping mapping,
                                       ActionForm aform,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        PropertyForm form = (PropertyForm) aform;
        NodeDefinition definition =this.getRepository(request).getDefinitionManager().getDefinition(form.getDefinitionId());
        PropertyDefinition property =  definition.getPropertyDefinition(form.getID());
        
        form.setType(""+property.getType());
        form.setID(property.getID());
        form.setDefinitionId(definition.getID());
        form.setName(property.getName());
        if (property.isMultiple()) {
        	if(property.getMaxLength()==0 && property.getMinLength()==0)
        		form.setDataNum("0");
        	else{
        		form.setDataNum("n");        		
            form.setMaxLength("" + property.getMaxLength());
            form.setMinLength(""+ property.getMaxLength());
        	}
        }
        else
        	form.setDataNum("1");
        	
        request.setAttribute("definition", definition);
        request.setAttribute("property", property);
        request.setAttribute("referenceType",
                             this.getRepository(request).getDefinitionManager().
                             getDefinition(property.getReferenceType()));
        return mapping.findForward("success");
    }
}
