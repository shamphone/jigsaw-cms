package com.fulong.cms.definition;

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
 * @author liuzijun
 * @author jiangqi
 * @version 1.0
 */
public class DeleteAction extends DefinitionBaseAction {

    public ActionForward definitionPerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        String categoryID = request.getParameter("categoryID");
        NodeDefinition categoryToDel = this.getRepository(request).
        getDefinitionManager().getDefinition(
                categoryID);
        this.getRepository(request).getDefinitionManager().delete(categoryToDel);
		Iterator<PropertyDefinition> it = categoryToDel.propertyDefinitions();
		//删除引用属性
		while (it.hasNext()) {
			PropertyDefinition propertyDefinition = it.next();
			if(propertyDefinition.getType()==9)
				categoryToDel.delete(propertyDefinition);
		}
        return null;
    }

}
