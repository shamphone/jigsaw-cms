package com.fulong.common.util;

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
public class Pager {
	public static final String BEAN_PAGER = "com.fulong.pager";
	private long count;
	private int pageSize;
	private int pageNo;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getPageCount() {
		return (count + pageSize - 1) / pageSize;

	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPrevPage() {
		return pageNo - 1;
	}

	public int getNextPage() {
		return pageNo + 1;
	}

	public long getLastPage() {
		return this.getPageCount();
	}

	public int getFirstPage() {
		return 0;
	}

	public boolean isHasNextPage() {
		return pageNo < this.getPageCount() - 1;
	}

	public boolean isHasPrevPage() {
		return pageNo > 0;
	}

	public int getFromIndex() {
		return pageNo * pageSize;
	}

}
