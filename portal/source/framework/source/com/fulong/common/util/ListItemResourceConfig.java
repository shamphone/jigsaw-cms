package com.fulong.common.util;

import org.apache.struts.config.MessageResourcesConfig;

/**
 * 
 * <p>
 * Title: WebMaster sv3
 * </p>
 * 
 * <p>
 * Description: 内容管理系统中小企业�?
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公�? 2005
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公�?
 * </p>
 * 
 * @author 李雄�?
 * @version 1.0
 */

public class ListItemResourceConfig extends MessageResourcesConfig {
	
	private static final long serialVersionUID = 1221275203984838740L;

	public String getFactory() {
		return (ListItemResourcesFactory.class.getName());
	}

	public void setFactory(String factory) {
		if (configured) {
			throw new IllegalStateException("Configuration is frozen");
		}
		this.factory = factory;
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Freeze the configuration of this component.
	 */
	public void freeze() {

		configured = true;

	}

	/**
	 * Return a String representation of this object.
	 */
	public String toString() {

		StringBuffer sb = new StringBuffer("ListItemResourceConfig[");
		sb.append("factory=");
		sb.append(this.factory);
		sb.append(",null=");
		sb.append(this.nullValue);
		sb.append(",parameter=");
		sb.append(this.parameter);
		sb.append("]");
		return (sb.toString());

	}

}
