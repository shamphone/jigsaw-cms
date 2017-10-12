package com.fulong.site.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @version 1.0.1
 */
public class RSSForm extends ValidatorForm {
	private static final long serialVersionUID = 5556111350064000447L;
	private String title;
	private String link;
	private String language;
	private String description;
	private String copyright;
	private String managingEditor;
	private String webMaster;
	private String category;
	private String generator;
	private String docs;
	private int ttl;
	private String templateID;
	private String style;
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getTemplateID() {
		return templateID;
	}
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getManagingEditor() {
		return managingEditor;
	}
	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}
	public String getWebMaster() {
		return webMaster;
	}
	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getDocs() {
		return docs;
	}
	public void setDocs(String docs) {
		this.docs = docs;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
}
