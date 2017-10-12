package com.fulong.portlet;

import org.apache.struts.validator.ValidatorActionForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
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
 * @author Lixf
 * @version 1.0
 */
public class PortletPreferencesForm extends ValidatorActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8065585694722959820L;
	private PortletPreferences preferences;

	public PortletPreferencesForm() {
		super();
	}

	/**
	 * 初始化，加载preference到参数中。
	 * 
	 * @param preferences
	 *            PortletPreferences
	 */
	public void bind(PortletPreferences preferences) {
		this.preferences = preferences;
	}

	/**
	 * 获取单值
	 * 
	 * @param name
	 *            String
	 * @return String
	 */
	public String getPreference(String name) {
		return this.preferences.getValue(name, "");
	}

	public void setPreference(String name, String value) throws ReadOnlyException {
		this.preferences.setValue(name, value);
	}

	/**
	 * 获取多值
	 * 
	 * @param name
	 *            String
	 * @return String[]
	 */
	public String[] getPreferences(String name) {
		return this.preferences.getValues(name, new String[0]);
	}

	public IndexedProperties getArrayPreference(String name) {
		return new IndexedProperties(this.preferences, name);
	}

	public void setPreferences(String name, String[] values) throws ReadOnlyException {
		this.preferences.setValues(name, values);
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		return null;
	}

}
