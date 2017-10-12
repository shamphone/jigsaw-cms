package com.fulong.site.form;

import org.apache.struts.upload.FormFile;

import com.fulong.common.PagerForm;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lichengzhao
 * @version 1.0.1
 */
public class ChannelForm extends PagerForm {
	private static final long serialVersionUID = -1069768839066279515L;
    private String name;
    private String type;
    private String folderPath;
	private FormFile templateFile;
    private String siteID;
    private String displayName;
    private String path;
    private String templateName;
    private boolean repleaceIndexPage;
    private String url;
    private String parentDisplayName;
    private String locale;
    private String[] scripts;
	private String[] styles;
    private String advanced;
    private String from;
	private String definitionID;
	private String definitionName;
	private String propertyID;
	private String propertyName;
    
    private String keywords;
    private String author;
    private String robots;
    private String refresh;
    private String expires;
    private String pragma;
    private String pageEnter;
    private String pageExit;
    private boolean index;
    
    public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	  public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}
	
	public String[] getScripts() {
		return scripts;
	}

	public void setScripts(String[] scripts) {
		this.scripts = scripts;
	}

	public String[] getStyles() {
		return styles;
	}

	public void setStyles(String[] styles) {
		this.styles = styles;
	}

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRepleaceIndexPage(boolean repleaceIndexPage) {
        this.repleaceIndexPage = repleaceIndexPage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParentDisplayName (String parentDisplayName) {
        this.parentDisplayName = parentDisplayName;
    }

    public void setLocale (String locale) {
        this.locale = locale;
    }

	public String getDefinitionID() {
		return definitionID;
	}

	public void setDefinitionID(String definitionID) {
		this.definitionID = definitionID;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

    public void setAdvanced (String advanced) {
        this.advanced = advanced;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public FormFile getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(FormFile templateFile) {
		this.templateFile = templateFile;
	}

	public String getSiteID() {
        return siteID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPath() {
        return path;
    }

    public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isRepleaceIndexPage() {
        return repleaceIndexPage;
    }

    public String getUrl() {
        return url;
    }

    public String getParentDisplayName () {
        return parentDisplayName;
    }

    public String getLocale () {
        return locale;
    }

    public String getAdvanced () {
        return advanced;
    }

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setRobots(String robots) {
		this.robots = robots;
	}

	public String getRobots() {
		return robots;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getExpires() {
		return expires;
	}

	public void setPragma(String pragma) {
		this.pragma = pragma;
	}

	public String getPragma() {
		return pragma;
	}

	public void setPageExit(String pageExit) {
		this.pageExit = pageExit;
	}

	public String getPageExit() {
		return pageExit;
	}

	public void setPageEnter(String pageEnter) {
		this.pageEnter = pageEnter;
	}

	public String getPageEnter() {
		return pageEnter;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public boolean isIndex() {
		return index;
	}
}
