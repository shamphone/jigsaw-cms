package com.fulong.site.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
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
public class CSSRuleForm extends ValidatorForm {
    private static int IDNum = 1;
    private Map properties = new HashMap();
    private String selector;
    private String type;
    private String cssID;
    private String ruleSource;
    public CSSRuleForm() {
        cssID = "style" + (this.IDNum++) + ".css";
    }

    public String getProperty(String name) {

        return (String) properties.get(name);

    }

    public void setProperty(String name, String value) {
        this.properties.put(name, value);
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCssID(String cssID) {
        this.cssID = cssID;
    }

    public void setRuleSource(String ruleSource) {

        this.ruleSource = ruleSource;
    }

    public String getSelector() {
        return selector;
    }

    public String getType() {
        return type;
    }

    public String getCssID() {
        return cssID;
    }

    public String getRuleSource() {

        return ruleSource;
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return null;
    }

}
