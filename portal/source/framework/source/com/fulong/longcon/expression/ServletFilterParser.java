package com.fulong.longcon.expression;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.value.StringValue;

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
public class ServletFilterParser extends DefaultParser{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Repository repository;
	private VariableManager manager;

	public ServletFilterParser(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		BeanFactory context =WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		this.repository = (Repository)context.getBean("repository");
		this.manager = (VariableManager)context.getBean("variableManager");		
	}
	 

	public void parser(String pattern) throws Exception 
	{
		String orginal = pattern.trim();
		String[] splits = orginal.split("[(\\s+)(\\+)]");	
		if((splits==null)||(splits.length<3))
			throw new IllegalArgumentException("Unknow pattern:"+pattern);
		this.propertyDefinition = splits[0];
		this.operation = splits[1];
		this.expression = splits [2];
		expression = expression.trim();
		
		/**
		 * liulei modified
		 * reason: problem of year-month-day 00:00:00 can not be used as search condition,
		 *         
		 */
		if(splits.length==4)
		{
			expression = expression +" "+ splits [3];
		}	
		if (expression.equalsIgnoreCase("null"))
		{
			this.expression = null;
			this.valueType = NULL;
			this.value = null;
		} 
		else if (expression.startsWith("$"))
	    {
			this.valueType = SYSVARIANT;
			this.expression = expression.substring(1);
			Calculator calc=this.manager.getCalculator(expression);
			if(calc!=null)
				this.value = calc.calc(request, response);
		} 
		else if (expression.startsWith("#"))
		{
			this.valueType = REFERENCE;
			this.expression = expression.substring(1);
			StringValue v = new StringValue();
			v.setValue(this.expression);
			this.value = v;
		} 
		else if (expression.startsWith("^"))
		{
			this.valueType = SEARCHDEFINITION;
			this.expression = expression.substring(1);
			StringValue v = new StringValue();
			v.setValue(this.expression);
			this.value = v;
		} 
		else 
		{
			this.valueType = CONSTANT;
			this.value = repository.getValueFactory().createValue(this.expression);
		}
	}
}
