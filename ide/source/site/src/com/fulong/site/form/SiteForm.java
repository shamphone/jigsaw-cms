package com.fulong.site.form;

import com.fulong.common.PagerForm;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.0
 */
public class SiteForm extends PagerForm {
    private String displayName;
    private String domain;
    private String ownerID;
    private String state;
    private String expiryDate;
	private String[] templateIDs;
	private String[] navigateTemplateIDs;
	
	private String ID;
    private String startDate;
    private String ownerName;
	
    public String[] getTemplateIDs() {
    	return templateIDs;
    }

    public void setTemplateIDs(String[] templateIDs) {
    	this.templateIDs = templateIDs;
    }

    public String[] getNavigateTemplateIDs() {
		return navigateTemplateIDs;
	}

	public void setNavigateTemplateIDs(String[] navigateTemplateIDs) {
		this.navigateTemplateIDs = navigateTemplateIDs;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getDisplayName() {

        return displayName;
    }

    public String getDomain() {

        return domain;
    }
    
    public String getOwnerID() {

        return ownerID;
    }

    public String getState() {
        return state;
    }

    public String getExpiryDate() {

        return expiryDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getID() {
        return ID;
    }

    public void setDisplayName(String displayName) {

        this.displayName = displayName;
    }

    public void setDomain(String domain) {

        this.domain = domain;
    }

    public void setOwnerID(String ownerID) {

        this.ownerID = ownerID;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setExpiryDate(String expiryDate) {

        this.expiryDate = expiryDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

}
