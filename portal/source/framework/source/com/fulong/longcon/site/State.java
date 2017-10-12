/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.site;

/**
 * State
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-15
 */
public enum State {
	/**
	 * 已发布状态
	 */

	PUBLISHED("published"),

	/**
	 * 关闭状态，可以维护，但是无法浏览
	 */
	EXPIRIED("expired"),

	/**
	 * 删除状态
	 */
	DELETED("deleted"),

	/**
	 * 创建状态
	 */
	CREATED("created"),
	/**
	 * 停用
	 */
	STOP("stop"),
	/**
	 * 正在编辑
	 */
	EDITING("editing");

	private final String value;
	/**
	 * @param value
	 */
	private State(String value){
		this.value=value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return this.value;
	}
	

}
