package com.fulong.portlet.button.nextNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.RangeIterator;
import com.fulong.common.util.RangeIteratorUtils;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.portlet.cms.ListContentPortletRender;
import com.fulong.portlet.cms.list.ContentListConfig;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author mali
 * @version 2.0
 */
public class FinalRender extends ListContentPortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		//获得当前页面显示node
		Node node = this.lookupNode(request, response);
		
		ContentListConfig config = new ContentListConfig(request.getPreferences());
	    String categoryId = request.getPreferences().getValue("category", "");
	    
	    //获取内容分类名称占位符链接传递过来的参数（一般用于内容分类重复器中）
	    if(request.getPreferences().getValue("filterByParamet","false").equals("on")){
	    	if(request.getParameter("definition")!=null&&!request.getParameter("definition").equals("")){
		    	categoryId = request.getParameter("definition");
		    }
	    }
	    
	    NodeDefinition def = this.getRepository().getDefinitionManager().getDefinition(categoryId);

		request.setAttribute("preferences", request.getPreferences());
		
		PortletPreferences preferences = request.getPreferences();
		String refField = preferences.getValue("refField", null);
		List<Node> list = new ArrayList<Node>();
		if (refField != null) {
			if (node == null) {
				return mapping.findForward(NODONE);
			}
			PropertyDefinition pd = def.getPropertyDefinition(refField);
			if (pd.getType() == 9) {
				Value[] contents = node.getProperty(refField).getValues();
				Node[] nodes = ValueUtils.toNodeArray(contents);
				Collections.addAll(list, nodes);
				if (list.size() == 0) {
					//return mapping.findForward("no.content");
					return mapping.findForward(NODONE);
				}
				}
			if (pd.getType() == 0) {
				PropertyDefinition fh = node.getDefinition().getPropertyDefinition(refField);
				if (fh != null) {
					RangeIterator<Node> contents = node.getNodes(fh.getID());
					if (contents.getSize() == 0) {
						//return mapping.findForward("no.content");
						return mapping.findForward(NODONE);
					}
					list = RangeIteratorUtils.toSizedList(contents, (int)contents.getSize());
			}
		}
			//将下一条内容ID传到页面
			if(node!=null)
				request.setAttribute("nodeID", this.getNextNode(list, node.getID()).getID());
			return mapping.findForward("success");
		}

		else {
			if (def == null) {
				def = lookUpDefinition(request);
			}
			//************构建query********************************
			Query query = null;
			if(preferences.getValue("lucene", "false").equals("on"))
				query = this.getRepository().getQueryManager().createQuery(def, Query.LUCENE);
			else
				query = this.getRepository().getQueryManager().createQuery(def, Query.SQL);
			//******************************************************
			if (request.getPreferences().getValue("filterByParamet", "false").equals("on")) {
				Iterator<PropertyDefinition> properties = def.propertyDefinitions();
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
					}else{
						//提取所有url参数 然后对map中的key进行判断，看是否是复合属性的属性 若是 则将条件添加入query
						int propertyType = propertyDefinition.getType();
						if(propertyType == 0 || propertyType == 9){
							if(request.getParameter(propertyDefinition.getID()) ==  null){
								Map parameterMap = request.getParameterMap();
								Set set = parameterMap.entrySet();
								Iterator iterator = set.iterator(); 
								while (iterator.hasNext()){
									Map.Entry mapentry = (Map.Entry) iterator.next(); 
									//判断mapentry.getKey().toString()是否为该复合属性中的属性
									if(mapentry.getKey().toString().indexOf(propertyDefinition.getID())!=-1 && def.getPropertyDefinition(mapentry.getKey().toString())!=null){
										String pars[] = request.getParameterValues(mapentry.getKey().toString());
										if (pars.length > 1) {
											if(propertyDefinition.getType()==1){   //处理多值属性的过滤
												query.filterByKeywords(pars);
											}else{
												if (pars[0].length() > 1) {
													query.filterByFromValue(mapentry.getKey().toString(),  this.getRepository().getValueFactory().createValue(pars[0], propertyDefinition.getType()));
												}
												if (pars[1].length() > 1) {
													query.filterByToValue(mapentry.getKey().toString(),  this.getRepository().getValueFactory().createValue(pars[1], propertyDefinition.getType()));
		
												}
											}						
										} else if (pars.length == 1 && pars[0].length() > 0) {
											query.filterByProperty(mapentry.getKey().toString(), pars[0].trim());
										}
									}
								}
							} 
						}
					}
				}
				if (request.getParameter("keyword") != null && request.getParameter("keyword").length() > 0) {
					String str = request.getParameter("keyword");
		        	String temp = str.replaceAll("[(]", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\)", "");
					String[] strSplit = temp.split(" ");
					List<String> strList = new ArrayList();
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
			
			String isFromSearchDef = preferences.getValue("fromSearchDef", "false");
			if(isFromSearchDef!=null&&!isFromSearchDef.equals("false")){   //从搜索大纲的某条内容中提取搜索条件
				if(node!=null){
					for(int i=0;i<config.getFilterPatterns().length;i++){
						FilterParser parser = this.getFilterParser(request, response);
						String patternTemp = config.getFilterPatterns()[i].trim();
						String[] splits = patternTemp.split("[(\\s+)(\\+)]");
						if((splits!=null)||(splits.length>2)){
							String PropIDTemp = splits[2].trim();
							if(PropIDTemp.startsWith("^")){  //过滤条件是匹配的搜索大纲中的属性								
								Property property = node.getProperty(PropIDTemp.substring(1));
								if(property!=null&&property.getString()!=null){
									if(property.getType()==9){  //如果是引用属性
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
			}else{
				for(int i=0;i<config.getFilterPatterns().length;i++){
					String patternTemp = config.getFilterPatterns()[i].trim();
					String[] splits = patternTemp.split("[(\\s+)(\\+)]");
					if((splits!=null)||(splits.length>2)){
						if(!splits[2].startsWith("^")){
							FilterParser parser = this.getFilterParser(request, response);
							parser.parser(config.getFilterPatterns()[i]);
							parser.addToQuery(query);
						}
					}
				}
			}			
			
			String orderBy= config.getOrderField();
			Boolean orderValue = config.getOrderValue();
			if(request.getPreferences().getValue("filterByParamet","on").equals("on")){
				orderBy = request.getParameter("orderBy");
				if(request.getParameter("orderValue")!=null){
					orderValue = request.getParameter("orderValue").equalsIgnoreCase("asc");
				}
			}
			if (orderBy != null && !orderBy.equals("") && orderValue != null) 
				query.sortByProperty(orderBy, orderValue);
			
			boolean global =  config.isGlobal();
			if (!global) {
				if (this.getCurrentSite(request, response) != null) {
					if (this.getCurrentSite(request, response).getOwner() != null) {
						query.filterByParent((Node) this.getCurrentSite(request, response).getOwner(), false);
					}
				}
			}
			boolean recursive = preferences.getValue("recursive", "").equals("true")?true:false;
			RangeIterator<Node> contents = query.nodes(recursive);
			if (contents.getSize() == 0) {
				//return mapping.findForward("no.content");
				return mapping.findForward(NODONE);
			}
			
			//将下一条内容ID传到页面
			list = RangeIteratorUtils.toSizedList(contents, (int)contents.getSize());
			if(node!=null)
				request.setAttribute("nodeID", this.getNextNode(list, node.getID()).getID());
			return mapping.findForward("success");
		}
	}
	
	private Node getNextNode(List<Node> contents,String nodeId){
		Node node = null;
		for (int i = 0; i < contents.size(); i++) {
			// 此段为主要的业务逻辑代码
			if(contents.get(i).getID().equals(nodeId)){
				node = contents.get(i+1);
			}
		}
		return node;
	}
}
