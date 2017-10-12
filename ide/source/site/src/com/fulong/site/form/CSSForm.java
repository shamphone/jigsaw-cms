package com.fulong.site.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 * <p>Title: Longcon WebMaster SV3</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD.</p>
 *
 * @author qmj
 * @author lixf
 * @version 1.0
 */
public class CSSForm extends ValidatorForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = 18989234878122L;
	private static int IDNum = 1;
    private String cssID;
    private String source;
    private FormFile file;
    private String path;
    private String siteID;
    private String templateID;
    public CSSForm() {
        cssID = "style" + (this.IDNum++) + ".css";
    }


    public void setCssID(String cssID) {
        this.cssID = cssID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }


    public String getCssID() {
        return cssID;
    }

    public String getSource() {
        return source;
    }

    public FormFile getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public String getSiteID() {
        return siteID;
    }

    public String getTemplateID() {
        return templateID;
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return null;
    }

}
