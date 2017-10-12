package com.fulong.longcon.site;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public interface SiteObject {
	public class State {
		/**
		 * 已发布状态
		 */
		public static final String PUBLISHED = "published";
		/**
		 * 关闭状态，可以维护，但是无法浏览
		 */
		public static final String EXPIRIED = "expired";

		/**
		 * 删除状态
		 */
		public static final String DELETED = "deleted";

		/**
		 * 创建状态
		 */
		public static final String CREATED = "created";
		/**
		 * 停用
		 */
		public static final String STOP = "stop";
		/**
		 * 正在编辑
		 */
		public static final String EDITING = "editing";
	};

	public String getID();

	/**
	 * 对象状态，刚的对象处于CREATED状态。
	 * 
	 * @return String
	 */
	public String getState();

}
