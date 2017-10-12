package com.fulong.portal.model;

import java.util.Locale;
import java.io.Serializable;

/**
 * 
 * <p>
 * Title: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class SecurityRoleRefConfig implements Serializable {

	private static final long serialVersionUID = 1756148031804046087L;

	private String roleLink;
	private String roleName;

	public SecurityRoleRefConfig() {

	}

	public SecurityRoleRefConfig(String name, String link) {
		this.roleName = name;
		this.roleLink = link;
	}

	/**
	 * Returns the role_link
	 * 
	 * @return the role_link
	 */
	public String getRoleLink() {
		return roleLink;
	}

	/**
	 * Returns the role_name
	 * 
	 * @return the role_name
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Returns the description for the given locale.
	 * 
	 * @return the localized description
	 */
	public String getDescription(Locale locale) {
		return null;
	}

	public void setRoleLink(String roleLink) {
		this.roleLink = roleLink;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
