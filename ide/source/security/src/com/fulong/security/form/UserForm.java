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
 * @author liuzijun
 * @version 3.1
 */
public class UserForm extends PagerForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4228653098462889803L;
	//多个用户ID
	private String[] IDs;
	//组ID
	private String nodeID;
	//用户名
	private String username;
	//用户密码
	private String password;
	//真实姓名
	private String commonname;
	//系统权限
	private String[] roleDefs;
	//用户ID
	private String userID;
	
	public String[] getIDs() {
        return IDs;
    }

    public void setIDs(String[] IDs) {
        this.IDs = IDs;
    }
    
    public String getNodeID() {
        return nodeID;
    }
    
    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCommonname() {
        return commonname;
    }
    
    public void setCommonname(String commonname) {
        this.commonname = commonname;
    }
    
    public String[] getRoleDefs() {
        return roleDefs;
    }

    public void setRoleDefs(String[] roleDefs) {
        this.roleDefs = roleDefs;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
}
