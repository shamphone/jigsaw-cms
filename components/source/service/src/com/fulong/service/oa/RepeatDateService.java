package com.fulong.service.oa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.service.NodeObject;
import com.fulong.service.NodeService;

/**
 * 
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
 * @author jiangqi
 * @version 2.0
 */
public class RepeatDateService extends NodeService {
	private String property1;
	private String property2;
	private String property3;
	private String property4;
	private String property5;
	private String property6;
	private String property7;
	
	public void nodeCreated(NodeObject event) throws ServletException {
		Node node = event.getNode();
		if(node!=null){
			NodeDefinition def = node.getDefinition();
			if(!def.isNodeType("2472259648280")){
				return;
			}else{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String type = node.getProperty(this.property1).getString();// 重复类型
				String beginDateString = node.getProperty(this.property2).getString();// 开始日期
				String endDateString = node.getProperty(this.property3).getString();// 结束日期
				Date beginDate, endDate;
				try {
					beginDate = dateFormat.parse(beginDateString);
				} catch (Exception e) {
					return;
				}
				try {
					endDate = dateFormat.parse(endDateString);
				} catch (Exception e) {
					return;
				}

				Calendar b = Calendar.getInstance();
				b.setTime(beginDate);
				Calendar e = Calendar.getInstance();
				e.setTime(endDate);

				List<String> value = new ArrayList<String>();
				if (type.equals("daily")) {// 每天
					value.add(beginDateString);
					while (b.getTime().before(e.getTime())) {
						b.add(Calendar.DAY_OF_YEAR, 1);
						value.add(dateFormat.format(b.getTime()));
					}
				} else if (type.equals("workDay")) {// 工作日
					if (b.get(Calendar.DAY_OF_WEEK) != 0 && b.get(Calendar.DAY_OF_WEEK) != 6) {
						value.add(beginDateString);
					}
					while (b.getTime().before(e.getTime())) {
						b.add(Calendar.DAY_OF_YEAR, 1);
						if (b.get(Calendar.DAY_OF_WEEK) != 0 && b.get(Calendar.DAY_OF_WEEK) != 6) {
							value.add(dateFormat.format(b.getTime()));
						}
					}
				} else if (type.equals("date")) {// 每周星期几
					String weekType = node.getProperty(this.property4).getString();// 周重复类别
					int week = Integer.parseInt(node.getProperty(this.property5).getString());// 周重复星期几
					if (weekType.equals("0")) {// 每周
						if (b.get(Calendar.DAY_OF_WEEK) == week) {
							value.add(beginDateString);
						}
						while (b.getTime().before(e.getTime())) {
							b.add(Calendar.DAY_OF_YEAR, 1);
							if (b.get(Calendar.DAY_OF_WEEK) == week) {
								value.add(dateFormat.format(b.getTime()));
							}
						}
					} else if (weekType.equals("1")) {// 每月第一个
						setWeekDate(dateFormat, beginDateString, b, e, value, week, 1);
					} else if (weekType.equals("2")) {// 每月第二个
						setWeekDate(dateFormat, beginDateString, b, e, value, week, 2);
					} else if (weekType.equals("3")) {// 每月第三个
						setWeekDate(dateFormat, beginDateString, b, e, value, week, 3);
					} else if (weekType.equals("4")) {// 每月第四个
						setWeekDate(dateFormat, beginDateString, b, e, value, week, 4);
					} else if (weekType.equals("5")) {// 每月第五个
						setWeekDate(dateFormat, beginDateString, b, e, value, week, 5);
					}
				} else if (type.equals("day")) {// 每月几号
					int day = Integer.parseInt(node.getProperty(this.property6).getString());// 日期
					if (day != 0) {
						if (b.get(Calendar.DAY_OF_MONTH) == day) {
							value.add(beginDateString);
						}
						while (b.getTime().before(e.getTime())) {
							b.add(Calendar.DAY_OF_YEAR, 1);
							if (b.get(Calendar.DAY_OF_MONTH) == day) {
								value.add(dateFormat.format(b.getTime()));
							}
						}
					} else {// 最后一天
						if (b.get(Calendar.DAY_OF_MONTH) == b.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)) {
							value.add(beginDateString);
						}
						while (b.getTime().before(e.getTime())) {
							b.add(Calendar.DAY_OF_YEAR, 1);
							if (b.get(Calendar.DAY_OF_MONTH) == b.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)) {
								value.add(dateFormat.format(b.getTime()));
							}
						}
					}
				}
				node.setProperty(this.property7, value.toArray(new String[value.size()]));// 设置实际计划日期

				if (value.size() == 0) {// 判断是否存在计划实际发生日期，如果没有跳转到异常页面
					throw new ServletException("no activity date");
				}
			}
		}
	}

	private void setWeekDate(DateFormat dateFormat, String beginDateString, Calendar b, Calendar e, List<String> value,
			int week, int week_in_month) {
		if (b.get(Calendar.DAY_OF_WEEK) == week && b.get(Calendar.DAY_OF_WEEK_IN_MONTH) == week_in_month) {
			value.add(beginDateString);
		}
		while (b.getTime().before(e.getTime())) {
			b.add(Calendar.DAY_OF_YEAR, 1);
			if (b.get(Calendar.DAY_OF_WEEK) == week && b.get(Calendar.DAY_OF_WEEK_IN_MONTH) == week_in_month) {
				value.add(dateFormat.format(b.getTime()));
			}
		}
	}
	
	public void setProperty1(String property1) {

		this.property1 = property1;
	}

	public String getProperty1() {
		return property1;
	}
	public void setProperty2(String property2) {

		this.property2 = property2;
	}

	public String getProperty2() {
		return property2;
	}
	public void setProperty3(String property3) {

		this.property3 = property3;
	}

	public String getProperty3() {
		return property3;
	}
	public void setProperty4(String property4) {

		this.property4 = property4;
	}

	public String getProperty4() {
		return property4;
	}
	public void setProperty5(String property5) {

		this.property5 = property5;
	}

	public String getProperty5() {
		return property5;
	}
	public void setProperty6(String property6) {

		this.property6 = property6;
	}

	public String getProperty6() {
		return property6;
	}
	public void setProperty7(String property7) {

		this.property7 = property7;
	}

	public String getProperty7() {
		return property7;
	}
}
