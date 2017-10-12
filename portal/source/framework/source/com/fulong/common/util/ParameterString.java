package com.fulong.common.util;

/**
 * 
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
public class ParameterString {
	private String source;
	private String nullValue;

	public ParameterString(String source) {
		this(source, "");
	}

	public ParameterString(String source, String nullValue) {
		this.source = source;
		this.nullValue = nullValue;
	}

	public void replace(Object arg1) {
		replace(new Object[] { arg1 });
	}

	public void replace(Object arg1, Object arg2) {
		replace(new Object[] { arg1, arg2 });
	}

	public void replace(Object[] args) {
		for (int i = 0; i < args.length; i++) {
			String regex = "\\x7B" + i + "\\x7D";
			if (args[i] == null) {
				source = source.replaceAll(regex, nullValue);
			} else {
				source = source.replaceAll(regex, args[i].toString());
			}
		}
	}

	public String toString() {
		return source;
	}
}
