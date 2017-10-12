package com.fulong.cms.form;

import com.fulong.common.PagerForm;
/**
 *
 * <p>Title: 龙驭网站内容管理系统</p>
 *
 * <p>Description: 龙驭网站内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @author lixf
 * @version 2.0
 */

public class SchemaForm extends PagerForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5416949491182102403L;
	private String name;
    private String description;
    private String displayName;
    private String ID;
    private String template;
    private String copy;
    private String parent;
    private String type;
    private String parentID;
    private String root;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTemplate() {
        return template;
    }

    public String getCopy() {
        return copy;
    }

    public String getParent() {
        return parent;
    }

    public String getType() {
        return type;
    }

    public String getParentID() {
        return parentID;
    }

    public String getRoot() {
        return root;
    }

    public String getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
