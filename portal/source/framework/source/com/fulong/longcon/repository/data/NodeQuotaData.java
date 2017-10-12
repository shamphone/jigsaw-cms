package com.fulong.longcon.repository.data;

import com.fulong.common.dao.Data;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author LJY
 * @version 2.0
 */


public class NodeQuotaData
    implements Data {

    /**
	 * 
	 */
	private static final long serialVersionUID = -519303766996645725L;
	private String unit;
    private String nodeID;
    private String principalID;
    private int principalType;
    private int quota;

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

    public String getUnit() {
        return unit;
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
