package com.fulong.longcon.expression.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Value;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class YearDateParameterCalculator extends BasicCalculator {
	private static final String REQUEST_DATE_PARAMETER="com.fulong.longcon.dateParameter";
	/* (non-Javadoc)
	 * @see com.fulong.longcon.expression.Calculator#calc(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Value calc(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Calendar calendar =Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date date = null;
			String dateString = "";
			if(request.getAttribute(REQUEST_DATE_PARAMETER)!=null){     //在重复器中使用，接收日历类重复器返回到每个重复片段中的日期参数
				date = (Date)request.getAttribute(REQUEST_DATE_PARAMETER);
				calendar.setTime(date);
				dateString = dateFormat.format(date);
			}else{                     //接收通过日期域占位符的链接跳转过来所带的参数
				dateString = request.getParameter("dateParameter");
			}
			dateString = "yearPt" + dateString;
			return this.repository.getValueFactory().createValue(dateString);
		}catch(Exception e){
			return this.repository.getValueFactory().createValue("yearPt" + dateFormat.format(new Date()));
		}
	}

}
