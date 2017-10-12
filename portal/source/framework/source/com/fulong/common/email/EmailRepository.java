package com.fulong.common.email;

/**
 * <p>
 * Title: Passport Client
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author JiangQi
 * @version 1.0
 */
public abstract class EmailRepository {
	private static EmailRepository instance;

	public EmailRepository getInstance() {
		return instance;
	}

	public EmailRepository() {
		instance = this;
	}

	/**
	 * 创建一个简单的邮件
	 * 
	 * @return Email
	 */
	public abstract Email createSimpleEmail() throws EmailException;

}
