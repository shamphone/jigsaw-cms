package com.fulong.longcon.workflow.data;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>Title:网上办事系统</p>
 *
 * <p>Description: 网上办事系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司  2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司 </p>
 *
 * @author lishaobo
 */
public class TaskData
    implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3090557304229661894L;
	private String pkid;
    private String activity;
    private Date beginDate;
    private Date deadline;
    private String executorID;
    private boolean executed;
    private Date executeDate;
    private String message="";
    private String contentID;
    private String prevID="0";
    private String nextID="0";
    private String assigneeID;
    private String categoryID;

    public String getPkid() {
        return pkid;
    }

    public String getActivity() {
        return activity;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getExecutorID() {
        return executorID;
    }

    public boolean getExecuted() {
        return executed;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public String getMessage() {
        return message;
    }

    public String getContentID() {
        return contentID;
    }

    public String getPrevID() {
        return prevID;
    }

    public String getNextID() {
        return nextID;
    }

    public String getAssigneeID() {
        return assigneeID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setExecutorID(String executorID) {
        this.executorID = executorID;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public void setPrevID(String prevID) {
        this.prevID = prevID;
    }

    public void setNextID(String nextID) {
        this.nextID = nextID;
    }

    public void setAssigneeID(String assigneeID) {
        this.assigneeID = assigneeID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

}
