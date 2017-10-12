package com.fulong.longcon.site.data;

import java.io.Serializable;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎--栏目绑定内容分类数据模型类</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公�? 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公�?</p>
 *
 * @author sunyuchao
 */

public class ChannelNodeDefinitionData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -165937044048827264L;
	private String templateID;
	private String channelID;
	private String nodeDdefinitionID;
	public String getTemplateID() {
		return templateID;
	}
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public String getNodeDdefinitionID() {
		return nodeDdefinitionID;
	}
	public void setNodeDdefinitionID(String nodeDdefinitionID) {
		this.nodeDdefinitionID = nodeDdefinitionID;
	}
	
	
	
}
