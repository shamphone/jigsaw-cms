package com.fulong.portlet.field.numeral;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;

import com.fulong.portlet.LongconPortlet;


/**
 * 数据内容域占位符
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
 * @author lixiang
 * @version 1.0
 */
public class NumberField extends LongconPortlet {
	
	private static final long serialVersionUID = 4459491942686458218L;

	public void initPortlet() throws PortletException {
		String separator = ",";
		String numberFormat = this.portletConfig.getInitParameter("numberFormat");
		String numberFormatKeys[] = numberFormat.split(separator);
		Map<String, String> numberFormats = this.buildFormatsMap(numberFormatKeys);
		this.getPortletContext().setAttribute("numberFormatKeys", numberFormatKeys);
		this.getPortletContext().setAttribute("numberFormats", numberFormats);
	}

	protected final Map<String, String> buildFormatsMap(String[] formatKeys) {
		Map<String, String> formats = new HashMap<String, String>();
		for (int i = 0; i < formatKeys.length; i++)
			formats.put(formatKeys[i], new DecimalFormat(formatKeys[i]).format(1234.456789));
		
		return formats;
	}
}
