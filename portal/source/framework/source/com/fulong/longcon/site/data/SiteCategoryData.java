package com.fulong.longcon.site.data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公�? 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公�?</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lishaobo</a>
 * @version 2.0
 */
public class SiteCategoryData
    implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2240902082999221957L;
	private String pkid;
    private String name;
    private String displayName;
    private String description;
    private Date createDate;
    private Date startDate;
    private Date expiryDate;
    private String state;
    private String groupID;

    public String getPkid() {
        return pkid;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getState() {
        return state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
