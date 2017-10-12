package com.fulong.cms.form;

import org.apache.struts.action.ActionForm;

/**
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 3.0
 */
public class FilterForm extends ActionForm{
    private String property;
    private String operation;
    private String constant;
    private String sysVariant;
    private String reference;
    private String searchPropValue;
    private int valueType;
    public FilterForm() {
        super();
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    public void setSysVariant(String sysVariant) {
        this.sysVariant = sysVariant;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }
    
    public void setSearchPropValue(String searchPropValue) {
        this.searchPropValue = searchPropValue;
    }

    public String getProperty() {
        return property;
    }

    public String getOperation() {
        return operation;
    }

    public String getConstant() {
        return constant;
    }

    public String getSysVariant() {
        return sysVariant;
    }

    public String getReference() {
        return reference;
    }

    public int getValueType() {
        return valueType;
    }
    
    public String getSearchPropValue() {
        return searchPropValue;
    }
}
