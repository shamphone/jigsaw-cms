package com.fulong.portlet.field.parentNodes;

import java.util.Vector;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author mali
 * @version 1.0
 */
public class FinalRender extends PortletRender {
	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		String categoryID = preferences.getValue("category", "");
		String refField = preferences.getValue("refField", null);
		String showField = preferences.getValue("showField", "");
		boolean isShowRoot = "true".equalsIgnoreCase(preferences.getValue(
				"isShowRoot", "true"));

		Vector<Node> list = new Vector<Node>();
		Node node = lookupNode(request, response);
		
		if(refField != null){
			NodeDefinition def = this.getRepository().getDefinitionManager().getDefinition(categoryID);
			PropertyDefinition pd = def.getPropertyDefinition(refField);
			if (pd.getType() == 9) {
				Value contents = node.getProperty(refField).getValue();
				if(contents != null)
					node = this.getRepository().getNode(contents.getString());
				if (node == null) {
					return mapping.findForward(NODONE);
				}else{
					if(showField!=null){
						showField = showField.substring(showField.indexOf(".")+1);
						preferences.setValue("showField", showField);
					}
				}
			}
		}
		
		if (node == null) {
			return mapping.findForward(NODONE);
		}

		
		list.add(0, node);
		do {
			if (node.getParent() == null)
				break;
			node = node.getParent();
			if(!node.getID().equals("root") && !node.getID().equals("1000000000000"))
				list.add(0, node);
		} while (node.getDefinition().getID().equals(node.getDefinition().getID()));
		
		if (!isShowRoot) {
			if (list.size() > 0) {
				list.remove(0);
			}
		}
		String seperator = preferences.getValue("seperator", null);
		if (seperator != null)
			preferences.setValue("seperator", this.antiFilter(seperator));
		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("categories", list.iterator());
        request.setAttribute("size", list.size());
        return mapping.findForward("success");
	}
}
