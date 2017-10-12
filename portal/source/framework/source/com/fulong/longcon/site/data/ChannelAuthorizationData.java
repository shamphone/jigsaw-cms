package com.fulong.longcon.site.data;

/**
 * 
 * <p>
 * Title: 龙驭建站系统核心引擎－栏目授权数据模型类
 * </p>
 * 
 * <p>
 * Description: 龙驭建站系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author sunyuchao
 */
public class ChannelAuthorizationData {
	private String principalID;
	private int principalType;
	private String channelID;
	private String templateID;
	
	/**
	 * 获取授权对象ID
	 * @return
	 */
	public String getPrincipalID() {
		return principalID;
	}
	
	/**
	 * 设置授权对象ID
	 * @param principalID
	 */
	public void setPrincipalID(String principalID) {
		this.principalID = principalID;
	}
	
	/**
	 * 获取授权对象类型
	 * @return
	 */
	public int getPrincipalType() {
		return principalType;
	}
	
	/**
	 * 设置授权对象类型
	 * @param principalType
	 */
	public void setPrincipalType(int principalType) {
		this.principalType = principalType;
	}
	
	/**
	 * 获取栏目ID
	 * @return
	 */
	public String getChannelID() {
		return channelID;
	}
	/**
	 * 设置该栏目ID
	 * @param channelID
	 */
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	
	/**
	 * 获取该栏目模板ID
	 * @return
	 */
	
	public String getTemplateID() {
		return templateID;
	}
	
	/**
	 * 设置该栏目模板ID
	 * @param templateID
	 */
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	
	
}
