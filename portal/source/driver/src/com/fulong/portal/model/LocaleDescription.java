package com.fulong.portal.model;

import java.util.Locale;
import java.io.Serializable;

/**
 * 
 * <p>
 * Title: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class LocaleDescription implements Serializable {
	
	private static final long serialVersionUID = 6971940842166359804L;
	
	private String language;
	private String value;

	public LocaleDescription() {
		Locale locale = Locale.getDefault();
		language = locale.getLanguage();
		value = "";
	}

	public void setLanguage(String language) {
		Locale locale = Locale.getDefault();
		if (language != null)
			locale = new Locale(language);

		this.language = locale.getLanguage();
	}

	public void setValue(String value) {
		if (value != null)
			this.value = value;
		else
			this.value = "";
	}

	public String getLanguage() {
		return language;
	}

	public String getValue() {
		return value;
	}
}
