package com.fulong.portlet;

import javax.portlet.PortletException;
import javax.servlet.ServletException;

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
public class PortletServiceException extends PortletException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4512325175751477604L;

	public PortletServiceException() {
		super();
	}

	public PortletServiceException(String string) {
		super(string);
	}

	public PortletServiceException(String string, Throwable throwable) {
		super(string, throwable);
	}

	public PortletServiceException(Throwable throwable) {
		super(throwable);
	}

	public PortletServiceException(String string, ServletException throwable) {
		super(string, throwable.getRootCause());
	}

	public PortletServiceException(ServletException throwable) {
		super(throwable.getRootCause());
	}

}
