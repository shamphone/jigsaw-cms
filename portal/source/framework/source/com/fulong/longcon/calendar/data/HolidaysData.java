package com.fulong.longcon.calendar.data;

import java.io.*;

/**
 * <p>
 * Title:网上办事系统
 * </p>
 * 
 * <p>
 * Description: 网上办事系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lishaobo
 */
public class HolidaysData implements Serializable {

	private static final long serialVersionUID = 8194770374116215861L;
	
	private String id;
	private String displayName;
	private String parentId;

	public String getDisplayName() {
		return displayName;
	}

	public String getId() {
		return id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
