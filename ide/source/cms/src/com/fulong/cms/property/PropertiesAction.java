package com.fulong.cms.property;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
public class PropertiesAction extends PropertyBaseAction {

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
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        String id = request.getParameter("ID");
        NodeDefinition def = this.getRepository(request).getDefinitionManager().
                             getDefinition(id);
        String[] filterType = request.getParameterValues("exclude");
        Iterator<PropertyDefinition> it = def.propertyDefinitions();
        ArrayList<PropertyDefinition> l=new ArrayList<PropertyDefinition>();
        while(it.hasNext()){
            PropertyDefinition pd=(PropertyDefinition)it.next();
            boolean flag=true;
            for(int i=0;i<filterType.length;i++){
                if((pd.getType()==(Integer.parseInt(filterType[i])))){
                    flag=false;
                }
            }
            if(flag==true){
                l.add(pd);
            }
        }
        request.setAttribute("propertylist", l);
        request.setAttribute("definitionId", id);
        //  request.setAttribute("filterType",filterType);
        return mapping.findForward("success");
    }
}
