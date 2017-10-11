package com.fulong.portlet.cms.list;

import javax.portlet.PortletPreferences;

import com.fulong.portlet.Constants;
import com.fulong.longcon.repository.Query;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class ContentListConfig implements Constants {

	private PortletPreferences preferences;

	public ContentListConfig(PortletPreferences preferences) {
		this.preferences = preferences;
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

	public boolean isFilterByEnd() {
		return "true".equalsIgnoreCase(this.preferences.getValue("filterByEnd", "true"));
	}

	public boolean isFilterBySource() {
		return "true".equalsIgnoreCase(this.preferences.getValue("isFilterSource", "false"));
	}

	public String[] getBlockFields() {
		return this.preferences.getValues("block-fields", new String[0]);
	}

	public String getBlockField(int index) {
		String field = this.preferences.getValues("block-fields", new String[0])[index];
		if ((field == null) || (field.length() == 0)) {
			return null;
		}
		return field;
	}

	public String[] getCustomalValues() {
		return this.preferences.getValues("customalValues", new String[0]);
	}

	public String getCustomalValue(int index) {
		String field = this.preferences.getValues("customalValues", new String[0])[index];
		if ((field == null) || (field.length() == 0)) {
			return null;
		}
		return field;
	}

	public boolean isAddDelOption() {
		return Boolean.getBoolean(this.preferences.getValue("delOption", String.valueOf(true)));
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

	public String getRepositoryID() {
		String value = this.preferences.getValue("repository-id", "");
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		return value;
	}

	public String getLanguage() {
		String value = this.preferences.getValue("lucene", "");
		if ((value.equals("false")) || (value.length() == 0)) {
			return Query.SQL;
		}
		return Query.LUCENE;
	}

	public String[] getOrders() {
		return this.preferences.getValues("orders", new String[0]);
	}

	public String[] getFilterFields() {
		return this.preferences.getValues("filter-fields", new String[0]);
	}

	public String[] getFilterValues() {
		return this.preferences.getValues("filter-values", new String[0]);
	}

	public String getFilterField() {
		String[] values = this.preferences.getValues("filter-fields", new String[0]);
		if (values.length == 0) {
			return null;
		}
		if ((values[0] == null) || (values[0].length() == 0)) {
			return null;
		}
		return values[0];
	}

	public String getFilterValue() {
		String[] values = this.preferences.getValues("filter-values", new String[0]);
		if (values.length == 0) {
			return null;
		}
		if ((values[0] == null) || (values[0].length() == 0)) {
			return null;
		}
		return values[0];
	}

	public String getOrderField() {
		return this.preferences.getValue("order-field", "");
	}

	public boolean getFilterAuto() {
		return this.preferences.getValue("filter-auto", "").equals("auto");
	}

	public boolean getFilterCreator() {
		return this.preferences.getValue("filter-creator", "").equals("4");
	}

	public boolean getOrderValue() {
		return this.preferences.getValue("order-style", "").equalsIgnoreCase("asc");
	}

	public String[] getFilterPatterns() {
		return this.preferences.getValues("filter-patterns", new String[0]);
	}

	public int getColumn() {
		String column = this.preferences.getValue("column", "1");
		return Integer.parseInt(column);

	}

	public String getState() {
		return this.preferences.getValue("state", "");
	}

	public int getRow() {
		String row = this.preferences.getValue("row", "1");
		return Integer.parseInt(row);
	}

	public int getStartloc() {
		String row = this.preferences.getValue("startloc", "0");
		return Integer.parseInt(row);
	}

	public int getSize() {
		return this.getColumn() * this.getRow();
	}

	public int getLength(int index) {
		String expression = this.preferences.getValues("length", new String[0])[index];
		if ((expression == null) || (expression.length() == 0)) {
			return 0;
		}
		return Integer.parseInt(expression);
	}

	public String getSeperator() {
		return this.preferences.getValue("seperator", "");
	}

	public boolean isGlobal() {
		return "true".equals(this.preferences.getValue("global", "false"));
	}

	public boolean isRecursive() {
		return "true".equals(this.preferences.getValue("recursive", "false"));
	}

	public String getCategory() {
		return this.preferences.getValue("category", "content-root");
	}

	public boolean isFieltByExpir() {
		return "true".equals(this.preferences.getValue("fieltByExpir", "false"));
	}

	public String getSource() {
		return this.preferences.getValue("remotesource", "");
	}
}
