package com.fulong.longcon.security;

/**
 * <p>
 * Title: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Description: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixf
 * @version 2.0
 */
public class PassportException extends RuntimeException {
	
	private static final long serialVersionUID = -6447108139777719641L;

	public PassportException() {
		super();
	}

	public PassportException(String message) {
		super(message);
	}

	public PassportException(String message, Throwable cause) {
		super(message, cause);
	}

	public PassportException(Throwable cause) {
		super(cause);
	}
}
