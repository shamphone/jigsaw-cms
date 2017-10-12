package com.fulong.portlet;

import java.io.Serializable;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public class IndexedProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3053253410754928523L;
	private static final int MAX_SIZE = 16;
	private String[] values;
	private PortletPreferences preferences;
	private String name;

	public IndexedProperties(PortletPreferences preferences, String name) {
		this.preferences = preferences;
		this.name = name;
		String[] values = preferences.getValues(name, new String[0]);
		this.values = new String[MAX_SIZE];
		if (values != null)
			System.arraycopy(values, 0, this.values, 0, values.length);
	}

	public String getValue(int index) {
		return values[index];
	}

	public void setValue(int index, String value) throws ReadOnlyException {
		this.values[index] = value;
		this.preferences.setValues(name, values);
	}

}
