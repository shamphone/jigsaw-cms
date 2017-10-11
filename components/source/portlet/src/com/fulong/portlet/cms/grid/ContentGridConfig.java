package com.fulong.portlet.cms.grid;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import com.fulong.portlet.Constants;

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
public class ContentGridConfig implements Constants {

	private PortletPreferences preferences;

	public ContentGridConfig(PortletPreferences preferences) {
		this.preferences = preferences;
	}

	public void setShowHeader(boolean showHeader) throws ReadOnlyException {
		this.preferences.setValue("show-header", String.valueOf(showHeader));
	}

	public void setShowFooter(boolean showFooter) throws ReadOnlyException {
		this.preferences.setValue("show-header", String.valueOf(showFooter));
	}

	public void setShowPager(boolean showPager) throws ReadOnlyException {
		this.preferences.setValue("show-pager", String.valueOf(showPager));
	}

	public void setAddDelOption(boolean addDelOption) throws ReadOnlyException {
		this.preferences.setValue("delOption", String.valueOf(addDelOption));
	}

	public void setRepositoryID(String repositoryID) throws ReadOnlyException {
		this.preferences.setValue("repository-id", repositoryID);
	}

	private void addValue(String name, String newValue) throws ReadOnlyException {
		String[] orginal = this.preferences.getValues(name, new String[0]);
		String[] newValues = new String[orginal.length + 1];
		System.arraycopy(orginal, 0, newValues, 0, orginal.length);
		newValues[orginal.length] = newValue;
		this.preferences.setValues(name, newValues);

	}

	public void addFilterPatterns(String filterPattern) throws ReadOnlyException {
		this.addValue("filter-patterns", filterPattern);
	}

	public boolean isShowHeader() {
		return Boolean.getBoolean(this.preferences.getValue("show-header", String.valueOf(true)));
	}

	public boolean isShowFooter() {
		return Boolean.getBoolean(this.preferences.getValue("show-footer", String.valueOf(true)));
	}

	public boolean isAddDelOption() {
		return Boolean.getBoolean(this.preferences.getValue("delOption", String.valueOf(true)));
	}

	public boolean isFilterByEnd() {
		return "true".equalsIgnoreCase(this.preferences.getValue("filterByEnd", "true"));
	}

	public boolean isShowPager() {
		return Boolean.getBoolean(this.preferences.getValue("show-pager", String.valueOf(true)));
	}

	public String getRepositoryID() {
		String value = this.preferences.getValue("repository-id", "");
		if ((value == null) || (value.length() == 0))
			return null;
		return value;

	}

	public boolean getFilterCreator() {
		return this.preferences.getValue("filter-creator", "").equals("4");
	}

	public String[] getFilterPatterns() {
		return this.preferences.getValues("filter-patterns", new String[0]);
	}

	public String getState() {
		return this.preferences.getValue("state", "");
	}

	public void setTableStyle(String tableStyle) throws ReadOnlyException {
		this.preferences.setValue("table-style", tableStyle);
	}

	public void setHeaderStyle(String headerStyle) throws ReadOnlyException {
		this.preferences.setValue("header-style", headerStyle);
	}

	public void setFooterStyle(String footerStyle) throws ReadOnlyException {
		this.preferences.setValue("footer-style", footerStyle);
	}

	public void setPagerStyle(String pagerStyle) throws ReadOnlyException {
		this.preferences.setValue("pager-style", pagerStyle);
	}

	public void setItemStyle(String itemStyle) throws ReadOnlyException {
		this.preferences.setValue("item-style", itemStyle);
	}

	public String getTableStyle() {
		return this.preferences.getValue("table-style", null);
	}

	public String getHeaderStyle() {
		return this.preferences.getValue("header-style", null);
	}

	public String getFooterStyle() {
		return this.preferences.getValue("footer-style", null);
	}

	public String getPagerStyle() {
		return this.preferences.getValue("pager-style", null);
	}

	public String getItemStyle() {
		return this.preferences.getValue("item-style", null);
	}

	public String getColumnType(int index) {
		return this.preferences.getValues("column-types", new String[0])[index];
	}

	public String getColumnExpression(int index) {
		return this.preferences.getValues("column-expressions", new String[0])[index];
	}

	public String getColumnField(int index) {
		String[] values = this.preferences.getValues("column-fields", new String[0]);
		if (index >= values.length)
			return null;
		String value = values[index];
		if ((value == null) || (value.length() == 0))
			return null;
		return value;
	}

	public String[] getColumnFields() {
		return this.preferences.getValues("column-fields", new String[0]);
	}

	public String getColumnFormat(int index) {
		String format = this.preferences.getValues("column-formats", new String[0])[index];
		if (format == null)
			return null;
		if (format.length() == 0)
			return null;
		return format;
	}

	public String getColumnHeader(int index) {
		return this.preferences.getValues("column-headers", new String[0])[index];
	}

	public String[] getColumnHeaders() {
		return this.preferences.getValues("column-headers", new String[0]);
	}

	public String getColumnFooter(int index) {
		return this.preferences.getValues("column-footers", new String[0])[index];
	}

	public String[] getColumnFooters() {
		return this.preferences.getValues("column-footers", new String[0]);
	}

	public String getColumnStyles(int index) {
		return this.preferences.getValues("column-styles", new String[0])[index];
	}

	public void addItemColumn(String field, String format, String header, String footer, String style)
			throws ReadOnlyException {
		this.addValue("column-types", ItemColumn);
		this.addValue("column-fields", field);
		this.addValue("column-formats", format);
		this.addValue("column-expressions", "");
		this.addValue("column-headers", header);
		this.addValue("column-footers", footer);
		this.addValue("column-styles", style);
	}

	public void addHyperLinkColumn(String field, String format, String header, String footer, String style)
			throws ReadOnlyException {
		this.addValue("column-fields", field);
		this.addValue("column-formats", format);
		this.addValue("column-types", HyperLinkColumn);
		this.addValue("column-expressions", "");
		this.addValue("column-headers", header);
		this.addValue("column-footers", footer);
		this.addValue("column-styles", style);
	}

	public void addTemplateColumn(String expression, String header, String footer, String style)
			throws ReadOnlyException {
		this.addValue("column-types", TemplateColumn);
		this.addValue("column-expressions", expression);
		this.addValue("column-headers", header);
		this.addValue("column-footers", footer);
		this.addValue("column-styles", style);
	}

	public int getTableCount() {
		return Integer.parseInt(this.preferences.getValue("table", "1"));
	}

	public String[] getOrders() {
		return this.preferences.getValues("orders", new String[0]);
	}

	public int getSize() {
		return this.getRow() * this.getColumn();
	}

	public void setRow(int row) throws ReadOnlyException {
		this.preferences.setValue("row", "" + row);
	}

	public void setColumn(int column) throws ReadOnlyException {
		this.preferences.setValue("column", "" + column);
	}

	public int getRow() {
		return Integer.parseInt(this.preferences.getValue("row", "0"));
	}

	public int getColumn() {
		return Integer.parseInt(this.preferences.getValue("column", "1"));
	}

	public void setFilterField(String filterField) throws ReadOnlyException {
		this.preferences.setValue("filter-field", filterField);
	}

	public void setFilterValue(String fieldValue) throws ReadOnlyException {
		this.preferences.setValue("filter-value", fieldValue);
	}

	public String getFilterField() {
		String value = this.preferences.getValue("filter-field", "");
		if (value.length() == 0)
			return null;
		return value;
	}

	public String getFilterValue() {
		return this.preferences.getValue("filter-value", null);
	}

	public void setOrderField(String filterField) throws ReadOnlyException {
		this.preferences.setValue("order-field", filterField);
	}

	public void setOrderValue(String fieldValue) throws ReadOnlyException {
		this.preferences.setValue("order-style", fieldValue);
	}

	public String getOrderField() {
		return this.preferences.getValue("order-field", "");
	}

	public boolean getOrderValue() {
		return this.preferences.getValue("order-value", "").equalsIgnoreCase("asc");
	}

	public boolean isGlobal() {
		return "true".equals(this.preferences.getValue("global", "false"));
	}

	public boolean isRecursive() {
		return "true".equals(this.preferences.getValue("recursive", "false"));
	}

	public boolean isFieltByExpir() {
		return "true".equals(this.preferences.getValue("fieltByExpir", "false"));
	}

	public String getCategory() {
		return this.preferences.getValue("category", "content-root");
	}

}
