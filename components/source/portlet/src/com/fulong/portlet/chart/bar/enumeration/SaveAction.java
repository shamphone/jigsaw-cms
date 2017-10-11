package com.fulong.portlet.chart.bar.enumeration;

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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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
		//preferences.setValue("preview", request.getContextPath() + channel.getSiteTemplate().getContextPath(buffer.toString()));
		preferences.setValue("preview", "/"+ template.getName() + buffer.toString());
		preferences.store();
		//File file = channel.getSiteTemplate().getRealPath(buffer.toString());		
		File file = new File(template.getRealPath(buffer.toString()));
		if (!file.exists()){
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(1.0, "Row 1", "Column 1");
		dataset.addValue(5.0, "Row 1", "Column 2");
		dataset.addValue(3.0, "Row 1", "Column 3");
		dataset.addValue(2.0, "Row 2", "Column 1");
		dataset.addValue(3.0, "Row 2", "Column 2");
		dataset.addValue(2.0, "Row 2", "Column 3");
		PlotOrientation orietation = PlotOrientation.HORIZONTAL;
		if (preferences.getValue("orientation", "HORIZONTAL").trim().equalsIgnoreCase("VERTICAL"))
			orietation = PlotOrientation.VERTICAL;
		boolean legend = preferences.getValue("legend", "true").trim().equalsIgnoreCase("true");
		
		JFreeChart chart = ChartFactory.createBarChart(preferences.getValue("title", "title"), 
				"",
				"",
				dataset,
				orietation, 
				legend, 
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
