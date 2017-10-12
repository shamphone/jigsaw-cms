package com.fulong.cms.content;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

public class BatEditAction extends ContentBaseAction
{
	public ActionForward doExecute(ActionMapping mapping, ActionForm aform, 
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String[] nodeIds = request.getParameterValues("contentID");
		Node node = this.getRepository(request).getNode(nodeIds[0]);
		NodeDefinition nodeDefinition = node.getDefinition();
		//获得大纲的属性
		Map<String, PropertyDefinition> properties = new LinkedHashMap<String, PropertyDefinition>();
		Iterator<PropertyDefinition> iterator = nodeDefinition.propertyDefinitions();
		while(iterator.hasNext())
		{
			PropertyDefinition property = iterator.next();
			if(property.getType()!=0)
			{
				properties.put(property.getID(), property);
			}
		}
		for(int i=0;i<node.getMixinDefinitions().length;i++)
		{
			nodeDefinition = node.getMixinDefinitions()[i];
			iterator = nodeDefinition.propertyDefinitions();
			while(iterator.hasNext())
			{
				PropertyDefinition property = iterator.next();
				if(property.getType()!=0)
				{
					properties.put(property.getID(), property);
				}
			}
		}
        request.setAttribute("properties", properties.values());  
        request.setAttribute("nodes",nodeIds);//需要记录所有待更新的节点的ID
        return mapping.findForward("success");
	}
}
