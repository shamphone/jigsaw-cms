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
public class IllegalIDException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 8011892030868902282L;

	public IllegalIDException() {
		super();
	}

	public IllegalIDException(String s) {
		super(s);
	}

	public IllegalIDException(int num, String object, String function) {
		super("There is " + num + " object " + " find for " + function);
	}
}
