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
public class SiteTemplateData
    implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 684996992358133094L;
	private String pkid;
    private String name;
    private String displayName;
    private boolean delFlag;
    private String state;
    private Date createDate;
    private Date startDate;
    private Date expiryDate;
    private String categoryID;
    private String locale;
    private String resolution;

    public String getPkid() {
        return pkid;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isDelFlag() {
        return delFlag;
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

    public String getCategoryID() {
        return categoryID;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public String getResolution() {
        return resolution;
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

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
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

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
