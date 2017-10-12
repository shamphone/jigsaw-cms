package com.fulong.security.form;

import com.fulong.common.PagerForm;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixiang
 * @version 3.1
 */
public class GroupForm extends PagerForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5747260068097931339L;
	//组ID
	private String nodeID;
	//组名称
	private String groupname;
	//父组ID
	private String parentNodeId;
	
    public String getNodeID() {
        return nodeID;
    }
    
    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }
    
    public String getParentNodeId() {
        return parentNodeId;
    }
    
    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
    
    public String getGroupname() {
        return groupname;
    }
    
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
