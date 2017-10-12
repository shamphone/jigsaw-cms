package com.fulong.site.form;

import com.fulong.common.PagerForm;

public class TemplateCategoryForm extends PagerForm {
    private String name;
    private String membergroup;
    private String description;
    private String[] selectedGroups;
    private String groupName;
    private String radiovalue;
    private String displayName;
    private String id;

    public void setName(String name) {
        this.name = name;
    }

    public void setMembergroup(String membergroup) {
        this.membergroup = membergroup;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSelectedGroups(String[] selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setRadiovalue(String radiovalue) {
        this.radiovalue = radiovalue;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getMembergroup() {
        return membergroup;
    }

    public String getDescription() {
        return description;
    }

    public String[] getSelectedGroups() {
        return selectedGroups;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getRadiovalue() {
        return radiovalue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId () {
        return id;
    }

    private void jbInit() throws Exception {
    }

}
