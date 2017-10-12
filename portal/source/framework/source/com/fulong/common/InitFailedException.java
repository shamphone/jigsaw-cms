package com.fulong.common;

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
public class InitFailedException extends RuntimeException {
	
	private static final long serialVersionUID = -5053533648384085244L;

	public InitFailedException() {
		super();
	}

	public InitFailedException(String message) {
		super(message);
	}

	public InitFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitFailedException(Throwable cause) {
		super(cause);
	}
}
