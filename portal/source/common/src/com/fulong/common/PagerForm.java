package com.fulong.common;

import org.apache.struts.action.ActionForm;
/**
 * 
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
public class PagerForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6600764869717970606L;
	private int pageNo = 1;
	private int pageSize = 20;
	private String orderBy = "";
	private long size;

	public PagerForm() {
		this.orderBy = "";
		this.pageNo = 1;
		this.pageSize = 20;
		this.size = 0;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public long getSize() {
		return size;
	}

	public long getPageCount() {
		return (size - 1) / pageSize + 1;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getNextPageNo() {
		return this.pageNo + 1;
	}

	public int getPrevPageNo() {
		return this.pageNo - 1;
	}

	public boolean isFirstPage() {
		return this.pageNo <= 1;
	}

	public boolean isLastPage() {
		return this.getPageCount() <= this.pageNo;
	}

	public int getFromIndex() {
		return (this.pageNo - 1) * this.pageSize;
	}

	public int getToIndex() {
		return ((this.pageNo * this.pageSize > this.size) ? (int) this.size : this.pageNo * this.pageSize) - 1;
	}
}
