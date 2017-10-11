package com.fulong.portlet.chart.pie.enumeration;

import java.io.File;
import java.io.FileOutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletAction;

/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class SaveAction extends PortletAction {
	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward action(ActionMapping mapping, ActionForm form, ActionRequest request, ActionResponse response)
			throws Exception {
		SiteTemplate template = this.getCurrentChannel(request, response).getSiteTemplate();
		StringBuffer buffer=new StringBuffer("/images/");
		buffer.append(request.getAttribute("javax.portlet.id"));
		buffer.append(".png");
		PortletPreferences preferences = request.getPreferences();
		if(request.getParameter("preference(legend)")==null)
			preferences.setValue("legend", "false");
		preferences.setValue("preview","/"+template.getName()+buffer.toString());
		preferences.store();
		String path = template.getRealPath(buffer.toString());
		File file = new File(path);	
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		
		if (!file.exists()) 
			file.createNewFile();
			DefaultPieDataset data = new DefaultPieDataset();
			data.setValue("Category 1", new Double(43.2));
			data.setValue("Category 2", new Double(27.9));
			data.setValue("Category 3", new Double(79.5));
			JFreeChart chart = ChartFactory.createPieChart(preferences.getValue("title", "title"), 
					data, 
					preferences.getValue("legend", "true").equalsIgnoreCase("true"), 
					false, 
					false);			
			FileOutputStream fos = new FileOutputStream(file);
			try {
				ChartUtilities.writeChartAsPNG(fos, 
						chart, 
						Integer.parseInt(preferences.getValue("width", "100")), 
						Integer.parseInt(preferences.getValue("height", "100")));
			} finally {
				fos.close();
			}
			this.log.trace("save preview image to " + file.getAbsolutePath());			
		return null;
	}

}
