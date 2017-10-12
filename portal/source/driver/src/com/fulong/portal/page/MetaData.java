package com.fulong.portal.page;

import java.io.Serializable;

/**
 * 
 * <p>
 * Title: Longcon Portal
 * </p>
 * 
 * <p>
 * Description: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class MetaData implements Serializable {
	
	private static final long serialVersionUID = -3908227553239185156L;
	
	public static final String NAME_KEYWORDS = "keywords";
	public static final String NAME_AUTHOR = "author";
	public static final String NAME_ROBOTS = "robots";
	public static final String HTTP_EQUIV_REFRESH = "refresh";
	public static final String HTTP_EQUIV_EXPIRES = "expires";
	public static final String HTTP_EQUIV_PAGE_ENTER = "Page-Enter";
	public static final String HTTP_EQUIV_PAGE_EXIT = "Page-Exit";
	public static final String HTTP_EQUIV_PRAGMA = "pragma";

	private String name;
	private String equiv;
	private String content;
	private String schema;

	public String getName() {
		return name;
	}

	public String getEquiv() {
		return equiv;
	}

	public String getContent() {
		if ((content == null) || (content.length() == 0))
			this.content = null;
		return content;
	}

	public String getSchema() {
		if ((schema == null) || (schema.length() == 0))
			return null;
		return schema;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEquiv(String equiv) {
		this.equiv = equiv;
	}

	public void setContent(String content) {
		if ((content == null) || (content.length() == 0))
			this.content = null;
		this.content = content;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("<meta ");
		if (name != null) {
			buffer.append("name=\"" + name + "\" ");
		}
		if (content != null) {
			buffer.append("content=\"" + content + "\" ");
		}
		if (equiv != null) {
			buffer.append("http-equiv=\"" + equiv + "\" ");
		}
		if (schema != null) {
			buffer.append("schema=\"" + schema + "\" ");
		}
		buffer.append("/>\r\n");
		return buffer.toString();
	}

	public boolean equals(Object obj) {
		if (!obj.getClass().isInstance(this)) {
			return false;
		} else {
			MetaData another = (MetaData) obj;
			if (this.name != null)
				return this.name.equals(another.getName());
			if (this.equiv != null)
				return this.equiv.equals(another.equiv);
			return false;
		}
	}
}
