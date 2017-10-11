package com.fulong.portlet.site.visitor;

import javax.portlet.PortletPreferences;

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
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2008
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */
public class PortletConfig {
	private PortletPreferences preferences;

	public PortletConfig(PortletPreferences preferences) {
		this.preferences = preferences;
	}

	public String getExpression() {
		return this.preferences.getValue("block-expressions", "");
	}

	public String[] getFilterPatterns() {
		return this.preferences.getValues("filter-patterns", new String[0]);
	}

	public String getOrderField() {
		return this.preferences.getValue("order-field", "");
	}

	public String getOrderStyle() {
		return this.preferences.getValue("order-style", "");
	}

	public String[] getOrderPatterns() {
		return this.preferences.getValues("order-patterns", new String[0]);
	}

	public int getRow() {
		return Integer.parseInt(this.preferences.getValue("row", "0"));
	}

	public int getColumn() {
		return Integer.parseInt(this.preferences.getValue("column", "0"));
	}

	public String getBlockType(int index) {
		return this.preferences.getValues("block-types", new String[0])[index];
	}

	public String getBlockFormat(int index) {
		String expression = this.preferences.getValues("block-formats", new String[0])[index];
		if ((expression == null) || (expression.length() == 0)) {
			return null;
		}
		return expression;
	}

	public String getBlockField(int index) {
		String field = this.preferences.getValues("block-fields", new String[0])[index];
		if ((field == null) || (field.length() == 0)) {
			return null;
		}
		return field;
	}

	public String getBlockExpression(int index) {
		String value = this.preferences.getValues("block-expressions", new String[0])[index];
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		return value;

	}

	public String getBlockStyle(int index) {
		String value = this.preferences.getValues("block-styles", new String[0])[index];
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		return value;

	}

	public int getBlockCount() {
		return Integer.parseInt(this.preferences.getValue("block", "1"));
	}

}
