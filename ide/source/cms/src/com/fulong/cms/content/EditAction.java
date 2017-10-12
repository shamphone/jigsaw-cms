package com.fulong.cms.content;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.EditForm;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.ValueUtils;

/**
 *
 * <p>Title: 龙驭内容管理系统-插件</p>
 *
 * <p>Description: 主要包括工作流、编辑器、校验、格式化</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 姜崎
 * @version 2.0
 */
public class EditAction  extends ContentBaseAction {


	public ActionForward doExecute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nodeId = request.getParameter("contentID");
		Node node = this.getRepository(request).getNode(nodeId);
		NodeDefinition nodeDefinition = node.getDefinition();
		Map<String, PropertyDefinition> properties = new LinkedHashMap<String, PropertyDefinition>();
		EditForm form = (EditForm)aform;
		Iterator<PropertyDefinition> iterator = nodeDefinition.propertyDefinitions();
		while(iterator.hasNext()){
			PropertyDefinition property = iterator.next();
			if(property.getType()!=0){
				form.setValues(property.getID(), ValueUtils.toStringArray(node.getProperty(property.getID()).getValues()));
				properties.put(property.getID(), property);
			}
		}
		//modified by mali 2010-7-12
		for(int i=0;i<node.getMixinDefinitions().length;i++){
			nodeDefinition = node.getMixinDefinitions()[i];
			iterator = nodeDefinition.propertyDefinitions();
			while(iterator.hasNext()){
				PropertyDefinition property = iterator.next();
				if(property.getType()!=0){
					form.setValues(property.getID(), ValueUtils.toStringArray(node.getProperty(property.getID()).getValues()));
					properties.put(property.getID(), property);
				}
			}
			
		}
		form.setParentID(this.getCurrentUser(request, response).getID());
		form.setContentID(nodeId);       
        request.setAttribute("properties", properties.values());
        return mapping.findForward("success");
    }
}
