package com.fulong.portlet.chart.bar.enumeration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import com.fulong.longcon.chart.ChartMessage;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Query;
import com.fulong.portlet.chart.ChartRender;

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
public class FinalRender extends ChartRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		request.setAttribute("preferences", preferences);
		// create a chart
		String definitionId = preferences.getValue("definition", NodeDefinition.NO_PROPERTIES_SCHEME);
		String[] propertyIds = preferences.getValues("property", new String[0]);
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		
		if(definition!=null){
			Query query = this.getRepository().getQueryManager().createQuery(definition, Query.SQL);
			//是否从本系统所有网站的内容库中抽取
			boolean global = "true".equals(preferences.getValue("global", ""));
			if (!global) {
				if (this.getCurrentSite(request, response) != null) {
					if (this.getCurrentSite(request, response).getOwner() != null) {
						query.filterByParent(this.getCurrentSite(request, response).getOwner(), false);
					}
				}
			}
			//是否包含子分类
			boolean recursive = "true".equals(preferences.getValue("recursive", ""));
			
			//要展示的属性
			PropertyDefinition[] props = new PropertyDefinition[propertyIds.length];
			for(int i=0;i<props.length;i++){
				props[i] = definition.getPropertyDefinition(propertyIds[i]);
			}
			//自动接收url参数
			boolean filterByParamet = "on".equals(preferences.getValue("filterByParamet", ""));
			if(filterByParamet){
				Iterator<PropertyDefinition> properties = definition.propertyDefinitions();
				while (properties.hasNext()) {
					PropertyDefinition propertyDefinition = (PropertyDefinition) properties.next();
					if (request.getParameter(propertyDefinition.getID()) != null) {
						String pars[] = request.getParameterValues(propertyDefinition.getID());
						if (pars.length > 1) {
							if(propertyDefinition.getType()==1){   //处理多值属性的过滤
								query.filterByKeywords(pars);
								//query.filterByKeywords(propertyDefinition.getID(),pars);
							}else{
								if (pars[0].length() > 1) {
									query.filterByFromValue(propertyDefinition.getID(),  this.getRepository().getValueFactory().createValue(pars[0], propertyDefinition.getType()));
								}
								if (pars[1].length() > 1) {
									query.filterByToValue(propertyDefinition.getID(),  this.getRepository().getValueFactory().createValue(pars[1], propertyDefinition.getType()));

								}
							}						
						} else if (pars.length == 1 && pars[0].length() > 0) {
							query.filterByProperty(propertyDefinition.getID(), pars[0].trim());
						}
					}
				}
				if (request.getParameter("keyword") != null && request.getParameter("keyword").length() > 0) {
					String str = request.getParameter("keyword");
		        	String temp = str.replaceAll("[(]", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\)", "");
					String[] strSplit = temp.split(" ");
					List<String> strList = new ArrayList<String>();
					for (int i=0;i<strSplit.length;i++) {
						if (!strSplit[i].equals("")) 
							strList.add(strSplit[i]);
					}
					query.filterByKeywords(strList.toArray(new String[strList.size()]));
				}
				if (request.getParameter("conditions") != null && request.getParameter("conditions").length() > 0) {
					String conditions[] = request.getParameterValues("conditions");
					for (int i = 0; i < conditions.length; i++) {
						FilterParser parser = this.getFilterParser(request, response);
						parser.parser(conditions[i]);
						parser.addToQuery(query);

					}
				}
			}
			
			String[] filterPatterns = preferences.getValues("filter-patterns", new String[0]);
			String isFromSearchDef = preferences.getValue("fromSearchDef", "false");
			if(isFromSearchDef!=null&&!isFromSearchDef.equals("false")){   //从搜索大纲的某条内容中提取搜索条件
				Node node = lookupNode(request, response);
				if(node!=null){
					for(int i=0;i<filterPatterns.length;i++){
						FilterParser parser = this.getFilterParser(request, response);
						String patternTemp = filterPatterns[i].trim();
						String[] splits = patternTemp.split("[(\\s+)(\\+)]");
						if((splits!=null)&&(splits.length>2)){
							String PropIDTemp = splits[2].trim();
							if(PropIDTemp.startsWith("^")){  //过滤条件是匹配的搜索大纲中的属性								
								Property property = node.getProperty(PropIDTemp.substring(1));
								if(property!=null&&property.getString()!=null){
									if(property.getType()==PropertyType.REFERENCE){  //如果是引用属性
										parser.parser(splits[0]+ " "+splits[1]+" "+ "#" + property.getString());
										parser.addToQuery(query);
									}else{  //非引用属性
										parser.parser(splits[0]+ " "+splits[1]+" "+ property.getString());
										parser.setValue(property.getValue());
										parser.addToQuery(query);								
									}
								}
							}else{  //过滤条件是匹配的一些常量或系统属性
								parser.parser(patternTemp);
								parser.addToQuery(query);
							}
						}
					}
				}
			}else{  //不从搜索大纲的某条内容中提取搜索条件
				for(int i=0;i<filterPatterns.length;i++){
					String patternTemp = filterPatterns[i].trim();
					String[] splits = patternTemp.split("[(\\s+)(\\+)]");
					if((splits!=null)&&(splits.length>2)){
						if(!splits[2].startsWith("^")){
							FilterParser parser = this.getFilterParser(request, response);
							parser.parser(filterPatterns[i]);
							parser.addToQuery(query);
						}
					}		
				}
			}			
			
			NodeIterator<Node> nodes = query.nodes(recursive);
			
			//柱图的分类标签
			String row = preferences.getValue("row","");
			PropertyDefinition rowProp = definition.getPropertyDefinition(row);
			
			//同值属性累计分组
			boolean accumulate = "true".equals(preferences.getValue("direct", null));
			CategoryDataset data = null;
			if(!accumulate){
				if(propertyIds.length>0&&!propertyIds[0].equals("")){
					data = this.getChartManager().createEnumCategoryDataset(nodes,rowProp, props);
				}
			}else{	//累计分组统计
				if(propertyIds.length>0&&!propertyIds[0].equals("")){
					data = this.getChartManager().createEnumCategoryDataset(nodes, props[0]);
				}
			}
			//设置柱图的标题
			String title = preferences.getValue("title", definition.getName());
			//设置柱图是水平显示还是垂直显示
			PlotOrientation orietation = PlotOrientation.HORIZONTAL;
			if (preferences.getValue("orientation", "HORIZONTAL").trim().equalsIgnoreCase("VERTICAL"))
				orietation = PlotOrientation.VERTICAL;
			//是否显示图例
			boolean legend = preferences.getValue("legend", "true").trim().equalsIgnoreCase("true");
			
			JFreeChart chart = ChartFactory.createBarChart(title, preferences.getValue("xlabel", ""), preferences.getValue("ylabel", ""), data, orietation, legend, false, false);
			BarRenderer renderer = (BarRenderer)chart.getCategoryPlot().getRenderer();
			//设置标签格式
			String labelCode = preferences.getValue("labelCode", "");
			if (labelCode.length() > 0) {
				String labelFormat = preferences.getValue("labelFormat", "#");
				CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(labelCode,new DecimalFormat(labelFormat));
				renderer.setItemLabelGenerator(generator);
				renderer.setItemLabelsVisible(true);
			}
			//设置bar之间的距离
			String distanceStr = preferences.getValue("distance", "");
			if(distanceStr!=null&&distanceStr.length()>0){
				distanceStr = "0";
			}
			double distance = 0;
			try {
				distance = Double.parseDouble(distanceStr);
			} catch (Exception e) {
				//吃掉错误 ，如果不能转换默认为0
				distance = 0;
			}
			renderer.setItemMargin(distance);

			ChartMessage message = new ChartMessage();
			message.setChart(chart);
			message.setWidth(Integer.parseInt(preferences.getValue("width", "400")));
			message.setHeight(Integer.parseInt(preferences.getValue("height", "300")));
			String key = this.getChartManager().register(message);
			request.setAttribute("key", key);
			return mapping.findForward("success");
		}
		return null;
	}
}
