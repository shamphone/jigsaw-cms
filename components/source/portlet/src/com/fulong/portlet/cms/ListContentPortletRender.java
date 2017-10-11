package com.fulong.portlet.cms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.ListRangeIterator;
import com.fulong.common.util.Pager;
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
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.cms.list.ContentListConfig;

/**
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @version 2.0
 */
public abstract class ListContentPortletRender extends PortletRender {

	/**
	 * 获取分类中的内容
	 * 
	 * @param category
	 *            ContentCategory
	 * @param filterField
	 *            String
	 * @param filterValue
	 *            String
	 * @param orderBy
	 *            String
	 * @param asc
	 *            boolean
	 * @param owner
	 *            Principal
	 * @return RangeIterator
	 */
	@SuppressWarnings("unchecked")
	public RangeIterator getContents(PortletRequest request,
			PortletResponse response, NodeDefinition category,
			String filterField, String filterValue, String orderBy,
			boolean asc, boolean isFilterByEnd, boolean global)
			throws Exception {
		Query query = this.getRepository().getQueryManager().createQuery(
				category, Query.SQL);

		// 过滤条件开始-------
		if (filterField != null && !filterField.equals("")) { //
			query.filterByProperty(filterField, filterValue);
			/*
			 * if (filterField.equals("creator")) { // if (filterValue != null
			 * && !filterValue.equals("")) { User user =
			 * this.getPassportProvider().getUser(filterValue); if (user !=
			 * null) { contents.filterByCreator(user); } } } else {
			 * PropertyDefinition propertyDefinition = category.
			 * getNodeDefinition().getPropertyDefinition( filterField); //
			 * String entrType = propertyDefinition.getEnumEntry(); int type =
			 * propertyDefinition.getType(); Value value =
			 * getRepository().getValueFactory(). createValue(filterValue,
			 * type);
			 * 
			 * if (entrType != null && !entrType.equals("")) { if
			 * (this.getDictManager().getRootEntry().getEntry(entrType) != null)
			 * { contents.filterByProperty(filterField, value, true); // } }
			 * else { if(filterField.equals("title")){ //
			 * contents.filterByTitle(filterValue); }else{
			 * contents.filterByProperty(filterField, value); // } } }
			 */
		}
		// 搜索条件结束-----------------

		if (orderBy == null) {
			orderBy = "startTime";
		}
		query.sortByProperty(orderBy, asc); /*
											 * contents.filterByExpiryDate(new
											 * Date()); if (orderBy == null)
											 * orderBy = "startTime"; if
											 * ("startTime"
											 * .equalsIgnoreCase(orderBy))
											 * contents.sortByStartTime(asc);
											 * else if
											 * ("createTime".equalsIgnoreCase
											 * (orderBy))
											 * contents.sortByCreatedTime(asc);
											 * else if
											 * ("title".equalsIgnoreCase(
											 * orderBy))
											 * contents.sortByTitle(asc); else
											 * if
											 * ("clickCount".equalsIgnoreCase(
											 * orderBy))
											 * contents.sortByClickCount(asc);
											 * else if
											 * ("ordinal".equalsIgnoreCase
											 * (orderBy))
											 * contents.sortByOrdinal(asc); else
											 * contents.sortByStartTime(asc); //
											 * 缺省的，按照发布时间排序
											 */
		/*
		 * if(isFilterByEnd){
		 * contents.filterByState(category.getProcessDefinition().getActivity(
		 * "end")); }
		 */
		if (!global) {
			if (this.getCurrentSite(request, response) != null) {
				if (this.getCurrentSite(request, response).getOwner() != null) {
					query.filterByParent((Node) this.getCurrentSite(request,
							response).getOwner(), true);
				}

			}
		}
		return query.nodes();
	}

	public long getContentsSize(PortletRequest request,
			PortletResponse response, NodeDefinition category,
			String[] filterPatterns, boolean global, boolean recursive,
			String language) throws Exception {
		return this.getContents(request, response, category, filterPatterns,
				null, true, global, recursive, language).getSize();
	}

	public long getRefContentsSize(RenderRequest request,
			RenderResponse response, NodeDefinition category,
			String[] filterPatterns) throws Exception {
		return this.getContents(request, response, category, filterPatterns,
				null, true, true, true, Query.SQL).getSize();
	}

	public RangeIterator<Node> getContents(PortletRequest request,
			PortletResponse response, NodeDefinition category,
			String[] filterPatterns, String orderBy, boolean asc,
			boolean global, boolean recursive, String language)
			throws Exception {
		Query query = this.getRepository().getQueryManager().createQuery(
				category, language);
		for (int i = 0; i < filterPatterns.length; i++) {
			FilterParser parser = this.getFilterParser(request, response);
			parser.parser(filterPatterns[i]);
			parser.addToQuery(query);
		}
		//添加对基于数据库的模糊搜索支持
		this.processFuzzyPropSearch((RenderRequest)request, query);
		//modified by mali 内容计数器不需要排序
		if (orderBy != null) {
			//orderBy = "startTime";
			query.sortByProperty(orderBy, asc);
		}
		
		if (!global) {
			if (this.getCurrentSite(request, response) != null) {
				if (this.getCurrentSite(request, response).getOwner() != null) {
					query.filterByParent((Node) this.getCurrentSite(request,
							response).getOwner(), false);
				}
			}
		}
		return query.nodes();
	}

	@SuppressWarnings("unchecked")
	public RangeIterator getContentsBySource(PortletRequest request,
			PortletResponse response, NodeDefinition category,
			String contentID, String filterField[], String filterValue[],
			String orderBy, boolean asc, boolean global, boolean recursive,
			boolean feiltByExpir, String state, String source) throws Exception { // /////
		// 为实现url多参数传入，修改了该方法

		Query query = this.getRepository().getQueryManager().createQuery(
				category, Query.SQL);
		// -----------------------------------开始过滤
		for (int i = 0; i < filterField.length; i++) {
			if (filterField[i] != null && !filterField[i].equals("")) { // ////
				query.filterByProperty(filterField[i], filterValue[i]);
				/*
				 * if (filterField[i].equals("creator")) { ///// if
				 * (filterValue[i] != null && !filterValue[i].equals("")) { Node
				 * user = this.getRepository().getNode( filterValue[i]); if
				 * (user != null) { query.filterByParent(user,false); } } } else
				 * { PropertyDefinition propertyDefinition =
				 * category.getPropertyDefinition( filterField[i]); ///// String
				 * entrType = propertyDefinition.getEnumEntry(); int type =
				 * propertyDefinition.getType(); Value value =
				 * getRepository().getValueFactory().
				 * createValue(filterValue[i], type);
				 * 
				 * if (entrType != null && !entrType.equals("")) { // if
				 * (this.getDictManager().getRootEntry().getEntry(entrType) !=
				 * null) { contents.filterByProperty(filterField[i], value,
				 * true); ////// } } else { if (filterField.equals("title")) {
				 * //// contents.filterByTitle(filterValue[i]); } else {
				 * contents.filterByProperty(filterField[i], value); //// } } }
				 */
			}
		}
		// ------------------结束过滤

		if (source != null && !source.equals("")) {
			query.filterByProperty("source", source);
		}
		if (contentID != null && !contentID.equals("")) {
			if (category.getPropertyDefinition("post-content-ref") != null) {
				query.filterByProperty("post-content-ref", contentID);
			}
		}
		/*
		 * if(feiltByExpir){ contents.filterByExpiryDate(new Date()); }
		 */
		if (orderBy == null) {
			orderBy = "startTime";
		}
		query.sortByProperty(orderBy, asc);
		/*
		 * if ("startTime".equalsIgnoreCase(orderBy))
		 * contents.sortByStartTime(asc); else if
		 * ("createTime".equalsIgnoreCase(orderBy))
		 * contents.sortByCreatedTime(asc); else if
		 * ("title".equalsIgnoreCase(orderBy)) contents.sortByTitle(asc); else
		 * if ("clickCount".equalsIgnoreCase(orderBy))
		 * contents.sortByClickCount(asc); else if
		 * ("ordinal".equalsIgnoreCase(orderBy)) contents.sortByOrdinal(asc);
		 * else if ("stuScore".equalsIgnoreCase(orderBy)) {
		 * contents.sortByProperty("stuScore", asc); } else
		 * contents.sortByStartTime(asc); // 缺省的，按照发布时间排序
		 */
		/*
		 * if(state!=null&&!state.equals("")){
		 * if(category.getProcessDefinition().getActivity(state)!=null){
		 * contents.filterByState(category.getProcessDefinition(). getActivity(
		 * state)); } }
		 */
		if (!global) {
			if (this.getCurrentSite(request, response) != null) {
				if (this.getCurrentSite(request, response).getOwner() != null) {
					query.filterByParent((Node) this.getCurrentSite(request,
							response).getOwner(), true);
				}
			}
		}
		return query.nodes();
	}

	protected String antiFilter(String str) {
		return str.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&#39;", "\\").replaceAll("&lt;", "<").replaceAll(
						"&gt;", ">");
	}

	/**
	 * 和现有系统的兼容
	 * 
	 * @param request
	 * @throws ReadOnlyException
	 */
	public void processPath(RenderRequest request) throws ReadOnlyException {
		String path = request.getPreferences().getValue("clip-path", "");
		if (path.startsWith("/sites/")) {
			int pos = path.indexOf('/', 7);
			if (pos > 0) {
				path = path.substring(pos);
				request.getPreferences().setValue("clip-path", path);
			}
		}
	}

	/**
	 * 引用/复合属性处理
	 * 
	 * @param pd
	 * @param node
	 * @param refField
	 * @param preferences
	 * @param request
	 * @param mapping
	 * @param config
	 * @param pager
	 * @return
	 */
	public ActionForward processRefField(PropertyDefinition pd, Node node,
			String refField, PortletPreferences preferences,
			RenderRequest request, ActionMapping mapping,
			ContentListConfig config, Pager pager) {
		RangeIterator<Node> it;
		if (pd.getType() == 9) {
			Property refProperty = node.getProperty(refField);
			if(refProperty==null){
				return mapping.findForward(NODONE);
			}
			Value[] contents = refProperty.getValues();
			Node[] nodes = ValueUtils.toNodeArray(contents);
			List<Node> list = new ArrayList<Node>();
			Collections.addAll(list, nodes);
			it = new ListRangeIterator<Node>(list);
			if (it.getSize() == 0) {
				return mapping.findForward(NODONE);
			}
			setContents(it, preferences, config, request, pager);
		}
		if (pd.getType() == 0) {
			PropertyDefinition fh = node.getDefinition().getPropertyDefinition(
					refField);
			if (fh != null) {
				it = node.getNodes(fh.getID());
				if (it.getSize() == 0) {
					// return mapping.findForward("no.content");
					return mapping.findForward(NODONE);
				}
				this.setContents(it, preferences, config, request, pager);
			}
		}
		return mapping.findForward("success");
	}

	/**
	 * 处理排序
	 * 
	 * @param config
	 * @param request
	 * @param query
	 */
	public void processOrder(ContentListConfig config, PortletRequest request,
			Query query) {
		String orderBy = config.getOrderField();
		Boolean orderValue = config.getOrderValue();
		if (request.getPreferences().getValue("filterByParamet", "on").equals(
				"on")) {
			if (request.getParameter("orderBy") != null)
				orderBy = request.getParameter("orderBy");
			if (request.getParameter("orderValue") != null) {
				orderValue = request.getParameter("orderValue")
						.equalsIgnoreCase("asc");
			}
		}
		//if (orderBy != null && !orderBy.equals("") && orderValue != null)
		query.sortByProperty(orderBy, orderValue);
	}

	/**
	 * 是否从本系统所有网站的内容库中抽取
	 * 
	 * @throws Exception
	 */
	public void processGlobal(ContentListConfig config, PortletRequest request,
			PortletResponse response, Query query) throws Exception {
		boolean global = config.isGlobal();
		if (!global) {
			if (this.getCurrentSite(request, response) != null) {
				if (this.getCurrentSite(request, response).getOwner() != null) {
					query.filterByParent((Node) this.getCurrentSite(request,
							response).getOwner(), false);
				}
			}
		}
	}

	/**
	 * 属性匹配
	 * 
	 * @param def
	 * @param request
	 * @param query
	 */
	@SuppressWarnings("unchecked")
	public void processProp(NodeDefinition def, RenderRequest request,
			Query query) {
		Iterator<PropertyDefinition> properties = def.propertyDefinitions();
		while (properties.hasNext()) {
			PropertyDefinition propertyDefinition = (PropertyDefinition) properties
					.next();
			if (request.getParameter(propertyDefinition.getID()) != null && !request.getParameter(propertyDefinition.getID()).equals("")) {
				String pars[] = request.getParameterValues(propertyDefinition
						.getID());
				this.processPars(pars, propertyDefinition, query);
			} else {
				// 提取所有url参数 然后对map中的key进行判断，看是否是复合属性的属性 若是 则将条件添加入query
				int propertyType = propertyDefinition.getType();
				if (propertyType == 0 || propertyType == 9) {
					if (request.getParameter(propertyDefinition.getID()) == null) {
						Map parameterMap = request.getParameterMap();
						Set set = parameterMap.entrySet();
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
							Map.Entry mapentry = (Map.Entry) iterator.next();
							// 判断mapentry.getKey().toString()是否为该复合属性中的属性
							if (mapentry.getKey().toString().indexOf(propertyDefinition.getID()+".") != -1&& def.getPropertyDefinition(mapentry.getKey().toString()) != null) {
								String pars[] = request
										.getParameterValues(mapentry.getKey()
												.toString());
								this.processPars(pars, propertyDefinition,
										query);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 过滤属性处理
	 * 
	 * @param preferences
	 * @param request
	 * @param response
	 * @param config
	 * @param query
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public void processFilter(PortletPreferences preferences,
			RenderRequest request, RenderResponse response,
			ContentListConfig config, Query query) throws Exception {
		// 从搜索大纲的某条内容中提取搜索条件
		String isFromSearchDef = preferences.getValue("fromSearchDef", "false");
		if (isFromSearchDef != null && !isFromSearchDef.equals("false")) {
			Node node = lookupNode(request, response);
			if (node != null) {
				for (int i = 0; i < config.getFilterPatterns().length; i++) {
					FilterParser parser = this.getFilterParser(request,
							response);
					String patternTemp = config.getFilterPatterns()[i].trim();
					String[] splits = patternTemp.split("[(\\s+)(\\+)]");
					if ((splits != null) || (splits.length > 2)) {
						String PropIDTemp = splits[2].trim();
						if (PropIDTemp.startsWith("^")) { // 过滤条件是匹配的搜索大纲中的属性
							Property property = node.getProperty(PropIDTemp
									.substring(1));
							if (property != null
									&& property.getString() != null) {
								if (property.getType() == 9) { // 如果是引用属性
									parser.parser(splits[0] + " " + splits[1]
											+ " " + "#" + property.getString());
									parser.addToQuery(query);
								} else { // 非引用属性
									parser.parser(splits[0] + " " + splits[1]
											+ " " + property.getString());
									parser.setValue(property.getValue());
									parser.addToQuery(query);
								}
							}
						} else { // 过滤条件是匹配的一些常量或系统属性
							parser.parser(patternTemp);
							parser.addToQuery(query);
						}

					}
				}
			}
		} else {
			// 自动添加的过滤条件
			for (int i = 0; i < config.getFilterPatterns().length; i++) {
				String patternTemp = config.getFilterPatterns()[i].trim();
				String[] splits = patternTemp.split("[(\\s+)(\\+)]");
				if ((splits != null) || (splits.length > 2)) {
					if (!splits[2].startsWith("^")) {
						FilterParser parser = this.getFilterParser(request,
								response);
						parser.parser(config.getFilterPatterns()[i]);
						try {
							parser.addToQuery(query);
						} catch (IllegalArgumentException e) {
							log.warn(e.getMessage(), e);
						}
					}
				}
			}
		}
	}

	/**
	 * 关键字处理
	 * 
	 * @param request
	 * @param query
	 */
	public void processKeywords(RenderRequest request, Query query) {
		if (request.getParameter("keyword") != null
				&& request.getParameter("keyword").length() > 0) {
			String str = request.getParameter("keyword");
			String temp = str.replaceAll("[(]", "").replaceAll("\\-", "")
					.replaceAll("\\'", "").replaceAll("\\)", "");
			String[] strSplit = temp.split(" ");
			List<String> strList = new ArrayList<String>();
			for (int i = 0; i < strSplit.length; i++) {
				if (!strSplit[i].equals(""))
					strList.add(strSplit[i]);
			}
			query.filterByKeywords(strList.toArray(new String[strList.size()]));
		}
	}
	
	/**
	 * 基于数据库的属性模糊搜索处理
	 * 
	 * @param request
	 * @param query
	 */
	public void processFuzzyPropSearch(RenderRequest request, Query query) {
		Map<String, String> params = request.getParameterMap();
		Iterator<String> pars = params.keySet().iterator();
		while(pars.hasNext()){
			String prop = pars.next();
			if(prop.indexOf("coolinkKeyword_")!=-1){
				String realProp = prop.substring(15);
				query.filterByKeywords(realProp, request.getParameter(prop));
			}
		}
	}

	/**
	 * url过滤条件处理
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @throws Exception
	 */
	public void processConditions(RenderRequest request,
			RenderResponse response, Query query) throws Exception {
		if (request.getParameter("conditions") != null
				&& request.getParameter("conditions").length() > 0) {
			String conditions[] = request.getParameterValues("conditions");
			for (int i = 0; i < conditions.length; i++) {
				FilterParser parser = this.getFilterParser(request, response);
				parser.parser(conditions[i]);
				parser.addToQuery(query);

			}
		}
	}

	/**
	 * 多种过滤参数处理
	 * 
	 * @param pars
	 * @param propertyDefinition
	 * @param query
	 */
	public void processPars(String pars[],
			PropertyDefinition propertyDefinition, Query query) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		Date date = new Date();
		if (pars.length > 1) {
			if (propertyDefinition.getType() == 1) { // 处理多值属性的过滤
				query.filterByKeywords(pars);
				// query.filterByKeywords(propertyDefinition.getID(),pars);
			} else {
				if (pars[0].length() > 1) {
					//兼容搜索   date参数值为yyyy-MM-dd modified by mali 2010-8-25
					if(propertyDefinition.getType() == 5 && (pars[0].trim().indexOf(":")==-1)){
						try {
							date = dateFormat.parse(pars[0].trim());
							beginCalendar.setTime(date);
							query.filterByFromValue(propertyDefinition.getID(), this
									.getRepository().getValueFactory().createValue(
											dateFormat2.format(beginCalendar.getTime()), propertyDefinition.getType()));
						} catch (ParseException e) {
							log.error(e.getMessage(), e);
						}
					}else{
						query.filterByFromValue(propertyDefinition.getID(), this
								.getRepository().getValueFactory().createValue(
										pars[0], propertyDefinition.getType()));
					}
				}
				if (pars[1].length() > 1) {
					//兼容搜索   date参数值为yyyy-MM-dd modified by mali 2010-8-25
					if(propertyDefinition.getType() == 5 && (pars[0].trim().indexOf(":")==-1)){
						try {
							date = dateFormat.parse(pars[1].trim());
							endCalendar.setTime(date);
							endCalendar.add(Calendar.DATE, 1);
							endCalendar.add(Calendar.MILLISECOND, -1);
							query.filterByToValue(propertyDefinition.getID(), this
									.getRepository().getValueFactory().createValue(
											dateFormat2.format(endCalendar.getTime()), propertyDefinition.getType()));
						} catch (ParseException e) {
							log.error(e.getMessage(), e);
						}
					}else{
						query.filterByToValue(propertyDefinition.getID(), this
								.getRepository().getValueFactory().createValue(
										pars[1], propertyDefinition.getType()));		
					}	
				}
			}
		} else if (pars.length == 1 && pars[0].length() > 0) {
			//兼容搜索   date参数值为yyyy-MM-dd modified by mali 2010-8-25
			if(propertyDefinition.getType() == 5 && (pars[0].trim().indexOf(":")==-1) ){
				try {
					date = dateFormat.parse(pars[0].trim());
					beginCalendar.setTime(date);
					endCalendar.setTime(date);
					endCalendar.add(Calendar.DATE, 1);
					endCalendar.add(Calendar.MILLISECOND, -1);
					query.filterByProperty(propertyDefinition.getID(), beginCalendar,
							endCalendar);
				} catch (ParseException e) {
					log.error(e.getMessage(), e);
				}
			}else{
				query.filterByProperty(propertyDefinition.getID(), pars[0].trim());
			}
		}
	}

	/**
	 * 分页初始化
	 */
	public Pager processPage(RenderRequest request, ContentListConfig config) {
		// 分页处理
		String showPager = request.getPreferences().getValue("show-pager",
				"false");
		String pageNo;
		if (showPager.equals("false")) {
			pageNo = "0";
		} else {
			// 计算分页
			pageNo = request.getParameter("pageNo");
			if (pageNo == null) {
				pageNo = "0";
			}
		}
		Pager pager = new Pager();
		int intPageNo;
		try {
			intPageNo = Integer.parseInt(pageNo);
		} catch (NumberFormatException e) {
			intPageNo = 0;
		}
		pager.setPageNo(intPageNo);
		pager.setPageSize(config.getSize());
		// 兼容原有设置
		if (request.getPreferences().getValue("isGotoPager", "false").equals(
				"true"))
			try {
				request.getPreferences().setValue("show-pager", "isGotoPager");
			} catch (ReadOnlyException e) {
				log.error(e.getMessage(), e);
			}
		return pager;
	}

	//

	// pass contents to jsp
	public void setContents(RangeIterator<Node> contents,
			PortletPreferences preferences, ContentListConfig config,
			RenderRequest request, Pager pager) {
		String row = preferences.getValue("row", null);
		int startlog = config.getStartloc() - 1;
		if (startlog < 0) {
			startlog = 0;
		}
		if (row == null) {
			String columnStr = preferences.getValue("column", "1");
			int column = 1;
			try {
				column = Integer.parseInt(columnStr);
			} catch (Exception e) {
			}
			contents.skip(startlog);
			int rowTemp = (int) (contents.getSize());
			request.setAttribute("contents", RangeIteratorUtils.toSizedList(
					contents, rowTemp));
			request.setAttribute("rowTemp", (int) Math.ceil((1.0) * rowTemp
					/ column));
			pager.setCount(contents.getSize());
			pager.setPageSize((int) contents.getSize());
			request.setAttribute("pager", pager);
		} else {
			contents.skip(pager.getFromIndex() + startlog);
			pager.setCount(contents.getSize());
			request.setAttribute("contents", RangeIteratorUtils.toSizedList(
					contents, config.getSize()));
			request.setAttribute("pager", pager);
		}
	}
}
