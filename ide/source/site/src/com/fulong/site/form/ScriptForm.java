package com.fulong.site.form;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: Longcon WebMaster SV3</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD.</p>
 *
 * @author lichengzhao
 * @version 3.1
 */

public class ScriptForm extends ValidatorForm {
    private static int IDNum = 1;
    private String scriptID;
    private String source;
    private FormFile file;
    private String path;
    private String siteID;
    private String templateID;

    public ScriptForm() {
        scriptID = "script" + (this.IDNum++) + ".js";
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return null;
    }

    public FormFile getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public String getScriptID() {
        return scriptID;
    }

    public String getSiteID() {
        return siteID;
    }

    public String getSource() {
        return source;
    }

    public String getTemplateID() {
        return templateID;
    }

    public int getIDNum() {
        return IDNum;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setScriptID(String scriptID) {
        this.scriptID = scriptID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public void setIDNum(int IDNum) {
        this.IDNum = IDNum;
    }
}
