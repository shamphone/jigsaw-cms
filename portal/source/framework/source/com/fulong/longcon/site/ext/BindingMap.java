/**
 * 
 */
package com.fulong.longcon.site.ext;


/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public interface BindingMap {
	/**
	 * 获取definitoin榜定的栏目
	 * @param definition
	 * @return
	 */
	public String getBinding(String definition);
	/**
	 * 如果definition已经有榜定，则先取消现有绑定
	 * @param definition
	 * @param channel
	 */
	public void setBinding(String definition, String channel);
	/**
	 * 获取channel对应的所有绑定
	 * @param channel
	 * @return
	 */
	public String[] getBindings(String channel);
	/**
	 * 清除channel对应的绑定
	 * @param channel
	 */
	public void clear(String channel);

}
