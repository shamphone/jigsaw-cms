/**
 * 
 */
package com.fulong.longcon.expression.impl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Value;

/**
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
public class TomorrowCalculator extends BasicCalculator {

	public Value calc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 Calendar calendar =Calendar.getInstance();
		 calendar.set(Calendar.HOUR_OF_DAY, 0);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 0);
		 calendar.add(Calendar.DATE, 1);	
		 return this.getRepository().getValueFactory().createValue(calendar);
	}

}
