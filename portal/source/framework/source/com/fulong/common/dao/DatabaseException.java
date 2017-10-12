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
public class DatabaseException extends DaoException {
	
	private static final long serialVersionUID = 5612297439272123852L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}
}
