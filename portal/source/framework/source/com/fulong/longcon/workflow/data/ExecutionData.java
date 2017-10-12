package com.fulong.longcon.workflow.data;

import java.util.Date;

/**
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class ExecutionData extends BasicData{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8010225938768118387L;
	private String instanceID;
    private String executorID;
    private Date executionTime;
    private String transitionID;
    public String getInstanceID() {
        return instanceID;
    }

    public String getExecutorID() {
        return executorID;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public String getTransitionID() {
        return transitionID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public void setExecutorID(String executorID) {
        this.executorID = executorID;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public void setTransitionID(String transitionID) {
        this.transitionID = transitionID;
    }
}
