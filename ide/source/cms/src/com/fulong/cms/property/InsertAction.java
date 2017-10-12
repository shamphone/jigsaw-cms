package com.fulong.cms.property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.cms.form.PropertyForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;

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
public class InsertAction extends PropertyBaseAction {
    /**
     * dictPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.dict.DictBaseAction method
     */
    public ActionForward propertyPerform(ActionMapping mapping,
                                           ActionForm aform,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws
            Exception {
       PropertyForm form = (PropertyForm) aform;
   	 NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(form.getDefinitionId());
  
        if (definition.getPropertyDefinition(form.getID()) != null) {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE,
                       new ActionMessage("errors.property.exist"));
            this.saveErrors(request.getSession(), errors);
            request.setAttribute("definition", definition);
            return mapping.findForward("propertyExist");
        }
        PropertyDefinition property = null;
        int type = Integer.parseInt(form.getType());
        if (type == PropertyType.FIX) {
            String schemeID = form.getReferenceType();
            NodeDefinition nodeDefinition = this.getRepository(request).
                                            getDefinitionManager().
                                            getDefinition(schemeID);
            property = definition.addChildNodeDefinition(nodeDefinition, form.getID());
        } else {
            property = definition.addPropertyDefinition(type, form.getID());
         }
        if (form.getDataNum().equals("1")) {
            property.setMultiple(false);
        } else if (!form.getDataNum().equals("1")) {
            property.setMultiple(true);
            if (form.getDataNum().equals("n")) {
                int min = 0, max = 0;
                if (form.getMinLength().length() > 0) {
                    min = Integer.parseInt(form.getMinLength());
                }
                if (form.getMaxLength().length() > 0) {
                    max = Integer.parseInt(form.getMaxLength());
                }
                property.setLength(min, max);
            }
        }
        property.setName(form.getName());     
        property.setDescription(form.getTips());
        property.setReferenceType(form.getReferenceType());
        request.setAttribute("property", property);
        return mapping.findForward("success");

    }
}
