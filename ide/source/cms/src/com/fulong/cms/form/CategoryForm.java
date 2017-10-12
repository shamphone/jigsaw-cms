package com.fulong.cms.form;

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
public class CategoryForm extends PagerForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3026666079603477636L;
	private String identify;
    private String name;
    private String type;
    private String nodeDefinition;
    private String workflow;
    private String description;
    private String categoryID;
    private String[] IDs;
    private boolean childrenCategory;
    private String otherCategoryID;
    private String postNodeDefinition;


    public String getIdentify() {
        return identify;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNodeDefinition() {
        return nodeDefinition;
    }

    public String getWorkflow() {
        return workflow;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public boolean isChildrenCategory() {
        return childrenCategory;
    }

    public String getOtherCategoryID() {
        return otherCategoryID;
    }

    public String getPostNodeDefinition() {
        return postNodeDefinition;
    }

    public String[] getIDs() {
        return IDs;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNodeDefinition(String nodeDefinition) {
        this.nodeDefinition = nodeDefinition;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setChildrenCategory(boolean childrenCategory) {
        this.childrenCategory = childrenCategory;
    }

    public void setOtherCategoryID(String otherCategoryID) {
        this.otherCategoryID = otherCategoryID;
    }

    public void setPostNodeDefinition(String postNodeDefinition) {
        this.postNodeDefinition = postNodeDefinition;
    }

    public void setIDs(String[] IDs) {
        this.IDs = IDs;
    }
}
