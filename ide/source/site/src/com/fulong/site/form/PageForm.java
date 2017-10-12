package com.fulong.site.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class PageForm extends ValidatorForm {
    protected String fileName;
    protected String name;
    protected String type;
    private String locale;
    private String displayName;
    private String siteID;
    private String path;
    private String channelID;
    private String contentCategory;
    private String defaultChannel;
    private Map metaNames;
    private Map httpEquiv;
    private String templateID;
    private String style;
    private String index;
    public PageForm(){
        metaNames=new HashMap();
        httpEquiv=new HashMap();
    }

    public Map getMetaNames(){
        return this.metaNames;
    }

    public Map getHttpEquivs(){
        return this.httpEquiv;
    }

    public void setMetaName(String name, String content){
        this.metaNames.put(name.toLowerCase(),content);
    }

    public String getMetaName(String name){
        return (String)this.metaNames.get(name.toLowerCase());
    }

    public void setHttpEquiv(String equiv,String content){
        this.httpEquiv.put(equiv.toLowerCase(),content);
    }
    public String getHttpEquiv(String equiv){
        return (String)this.httpEquiv.get(equiv.toLowerCase());
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return null;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public void setContentCategory(String contentCategory) {
        this.contentCategory = contentCategory;
    }

    public void setDefaultChannel(String defaultChannel) {
        this.defaultChannel = defaultChannel;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSiteID() {
        return siteID;
    }

    public String getPath() {
        return path;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getContentCategory() {
        return contentCategory;
    }

    public String getDefaultChannel() {
        return defaultChannel;
    }

    public String getTemplateID() {
        return templateID;
    }

    public String getStyle() {
        return style;
    }

    public String getIndex() {
        return index;
    }


}
