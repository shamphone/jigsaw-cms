package com.fulong.longcon.resource.data;

import java.util.Date;

import com.fulong.common.dao.Data;

/**
 * <p>
 * Title: Longcon Passport System
 * </p>
 * 
 * <p>
 * Description: Longcon Passport
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD. *
 * 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author lishaobo
 * @version 2.0
 */
public class ResourceData implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8931315003640755825L;
	private String ownerID;
	private String path;
	private Date creationTime;
	private Date updateTime;
	private String creatorID;
	private boolean isFolder;
	private String name;
	private String mime;
	private boolean readOnly;
	private boolean hidden;
	private String subject;
	private String description;

	private long length;
	private String parentPath;
	private String pkid;

	public ResourceData() {
		this.creationTime = new Date();
		this.updateTime = new Date();
		this.hidden = false;
		this.readOnly = false;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public String getCreatorID() {
		return creatorID;
	}

	public String getDescription() {
		return description;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isIsFolder() {
		return isFolder;
	}

	public String getMime() {
		return mime;
	}

	public String getName() {
		return name;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public String getPath() {
		return path;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public String getSubject() {
		return subject;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public long getLength() {
		return length;
	}

	public String getParentPath() {
		return parentPath;
	}

	public String getPkid() {
		return pkid;
	}

}
