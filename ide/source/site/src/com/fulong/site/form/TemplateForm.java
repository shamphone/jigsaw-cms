package com.fulong.site.form;

import java.io.File;

import org.apache.struts.upload.FormFile;

import com.fulong.common.PagerForm;
/**
 *
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class TemplateForm extends PagerForm {
    private String name;
    private String category;
    private String description;
    private File designPackage;
    private String[] ids;
    private String displayName;
    private FormFile photo;
    private FormFile staticPage;
    private FormFile zipFile;
    private String id;
    private String advanced;
    private boolean createSite;
    private String domain;
    private String sourceDisplayName;
    private String sourceName;
    private String siteName;
    private String defaultDomain;
    private String categoryName;
    private String ownerID;
    private String language;
    private String resolution;
    
    public String getResolution() {
		return resolution;
	}

	public FormFile getZipFile() {
		return zipFile;
	}

	public void setZipFile(FormFile zipFile) {
		this.zipFile = zipFile;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	private String ownerName;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public File getDesignPackage() {
        return designPackage;
    }

    public String[] getIds() {
        return ids;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FormFile getPhoto() {
        return photo;
    }

    public FormFile getStaticPage() {
        return staticPage;
    }

    public String getId () {
        return id;
    }

    public String getAdvanced () {
        return advanced;
    }

    public boolean isCreateSite () {
        return createSite;
    }

    public String getDomain () {
        return domain;
    }

    public String getSourceDisplayName () {
        return sourceDisplayName;
    }

    public String getSourceName () {
        return sourceName;
    }

    public String getSiteName () {
        return siteName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDesignPackage(File designPackage) {
        this.designPackage = designPackage;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
    }

    public void setStaticPage(FormFile staticPage) {
        this.staticPage = staticPage;
    }

    public void setId (String id) {
        this.id = id;
    }

    public void setAdvanced (String advanced) {
        this.advanced = advanced;
    }

    public void setCreateSite (boolean createSite) {
        this.createSite = createSite;
    }

    public void setDomain (String domain) {
        this.domain = domain;
    }

    public void setSourceDisplayName (String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
    }

    public void setSourceName (String sourceName) {
        this.sourceName = sourceName;
    }

    public void setSiteName (String siteName) {
        this.siteName = siteName;
    }

	public void setDefaultDomain(String defaultDomain) {
		this.defaultDomain = defaultDomain;
	}

	public String getDefaultDomain() {
		return defaultDomain;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
}
