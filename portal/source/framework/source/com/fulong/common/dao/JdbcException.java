package com.fulong.common.dao;

import com.fulong.common.dao.DaoException;

/**
 * <p>
 * Title: Longcon Passport System
 * </p>
 * 
 * <p>
 * Description: Longcon Passport
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD. 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author Lixf
 * @version 2.0.0
 */
public class JdbcException extends DaoException {
	
	private static final long serialVersionUID = -7751676217638633629L;

	public JdbcException() {
		super();
	}

	public JdbcException(String message) {
		super(message);
	}

	public JdbcException(String message, Throwable cause) {
		super(message, cause);
	}

	public JdbcException(Throwable cause) {
		super(cause);
	}
}
