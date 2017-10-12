package com.fulong.update.portlet;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-10-9	
 * @version 1.0.1
 */
public class PortletInfo {
	private String prefix;
	private String orignalPath;
	private String newPath;
	
	public static final PortletInfo DEFAULT_PORTLET_INFO = new PortletInfo("","clip-path","clip-content");
	
	public PortletInfo(String prefix, String orignalPath, String newPath) {
		this.prefix = prefix;
		this.orignalPath = orignalPath;
		this.newPath = newPath;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public String getOrignalPath() {
		return orignalPath;
	}
	public String getNewPath() {
		return newPath;
	}
	
}
