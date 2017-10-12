package com.fulong.longcon.repository.data;

import com.fulong.common.dao.Data;

/**
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport</p>
 *
 * <p>Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD.
 * 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author Lixf
 * @version 2.0.0
 */
public class NodeAuthorizationData
    implements Data {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5310085810185773239L;
	private String action;
    private String nodeID;
    private String principalID;
    private int principalType;
    private int quota;
    public String getAction() {
        return action;
    }

    public String getNodeID() {
        return nodeID;
    }

    public String getPrincipalID() {
        return principalID;
    }

    public int getPrincipalType() {
        return principalType;
    }

    public int getQuota() {
        return quota;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public void setPrincipalID(String principalID) {
        this.principalID = principalID;
    }

    public void setPrincipalType(int principalType) {
        this.principalType = principalType;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }


}
