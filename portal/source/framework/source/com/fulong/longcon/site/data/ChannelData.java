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
public class ChannelData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5503472314415631667L;
	private String pkid;
    private String name;
    private String displayName;
    private String state;
    private Date createDate;
    private Date startDate;
    private Date expiryDate;
    private String nodeID;
    private String templatePath;
//    private String locale;
    private String parentID;
    private String templateID;
    private String type;
    private String secure;        

    
    public String getSecure() {
		return secure;
	}

	public void setSecure(String secure) {
		this.secure = secure;
	}

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

    public String getNodeID() {
        return nodeID;
    }

    public String getTemplatePath() {
        return templatePath;
    }
/*    
    public String getLocale() {
        return locale;
    }
*/
    public String getParentID() {
        return parentID;
    }

    public String getTemplateID() {
        return templateID;
    }

    public String getType() {
        return type;
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

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
/*
    public void setLocale(String locale) {
        this.locale = locale;
    }
*/
    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object obj) {
        if(obj instanceof ChannelData)
            return false;
        return
            this.getPkid().equalsIgnoreCase(((ChannelData)obj).getPkid());
     }


}
