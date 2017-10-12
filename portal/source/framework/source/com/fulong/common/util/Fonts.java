package com.fulong.common.util;

/**
 * <p>Title: ����������</p>
 * <p>Description: ����������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: �пƸ������������޹�˾</p>
 * @author <a href="mailto:zhongzl@fulong.com.cn">������</a>
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import java.awt.GraphicsEnvironment;

public class Fonts {
	private HttpServletRequest request;

	public Fonts(HttpServletRequest request) {
		this.request = request;
	}

	public String[] getFontFamilyNames() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(request.getLocale());
	}
}
