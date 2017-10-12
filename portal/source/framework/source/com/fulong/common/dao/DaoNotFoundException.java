package com.fulong.common.dao;

/**
 * <p>
 * Title: LongCon WebMaster
 * </p>
 * 
 * <p>
 * Description: 龙驭内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 中科辅龙计算机技术有限公司 2005
 * </p>
 * 
 * <p>
 * Company: 中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public class DaoNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 2479044025345865619L;

	public DaoNotFoundException() {
		super();
	}

	public DaoNotFoundException(String message) {
		super(message);
	}

	public DaoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoNotFoundException(Throwable cause) {
		super(cause);
	}
}
