package com.fulong.portlet.field.numeral;

import java.text.DecimalFormat;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;


/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author liulei
 * @version 1.0
 */
public class FinalRender extends PortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, 
			RenderRequest request, RenderResponse response)
			throws Exception
	{
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		Node node = lookupNode(request, response);
		if (node == null)
		{
			return mapping.findForward(NODONE);
		}
		Property property = node.getProperty(config.getField());
		if (property != null)
		{
			Value value = property.getValue();
			int flag_type = property.getType();
			if(value != null)
			{
				String numberFormatter = preferences.getValue("numberFormats", null);
				if(numberFormatter != null) {
				switch(flag_type)
				{
				   case PropertyType.LONG:
					      Long value_Long = value.getLong();
					      String longString = new DecimalFormat(numberFormatter).format(value_Long);
					      if(longString.indexOf(".")==0){
					    	  longString = "0"+longString;
					      }
						  request.setAttribute("value", longString);
					      break;
				   case PropertyType.DOUBLE:
					      Double value_Double = value.getDouble();
					      String doubleString = new DecimalFormat(numberFormatter).format(value_Double);
					      if(doubleString.indexOf(".")==0){
					    	  doubleString = "0"+doubleString;
					      }
				          request.setAttribute("value", doubleString);
				}
				}
				else {
					switch(flag_type)
					{
					   case PropertyType.LONG:
						      Long value_Long = value.getLong();
							  request.setAttribute("value", value_Long);
						      break;
					   case PropertyType.DOUBLE:
						      Double value_Double = value.getDouble();
					          request.setAttribute("value", value_Double);
					}
					}
			}
		}

		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("node", node);
		return mapping.findForward("success");
	}
}
