package com.fulong.longcon.repository.data;

import java.util.*;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodeDefinitionData
    extends BasicData {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 939782964819188278L;
	private String ID;
    private String name;
    private String description;
    //private String category;
    private boolean delete_mark;
    private boolean _system;
    private String superID;
    private Date createTime;

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }




    public void setDescription(String description) {

        this.description = description;
    }

    public void setDelete_mark(boolean delete_mark) {
        this.delete_mark = delete_mark;
    }

    public void setSuperID(String superID) {
        this.superID = superID;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSystem(boolean is_system) {
        this._system = is_system;
    }

    public String getDescription() {

        return description;
    }

    public boolean isDelete_mark() {
        return delete_mark;
    }

    public boolean is_system() {
        return _system;
    }

    public String getSuperID() {
        return superID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getName() {
        return name;
    }

}
