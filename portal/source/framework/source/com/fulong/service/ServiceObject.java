package com.fulong.service;

import java.util.EventObject;

/**
 * 服务操作对象
 * 
 * <p>
 * Title: Coolink平台服务模型管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台服务管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author liuzijun
 * @author lixf
 * @version 3.1
 */

public class ServiceObject extends EventObject {
	private ServiceLog log = new BlankServiceLog();
	/**
	 * 
	 */
	private static final long serialVersionUID = -6278084595913474509L;
	private int type;

	public ServiceObject(Object object, int type) {
		super(object);
		this.type=type;
	}

	public ServiceLog getLog() {
		return this.log;
	}

	public void setLog(ServiceLog log) {
		this.log = log;
	}

	/**
	 * 获取事件类型
	 */
	public int getType() {
		return this.type;
	}
}
