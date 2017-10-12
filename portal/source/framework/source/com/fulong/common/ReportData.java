package com.fulong.common;

import java.io.Serializable;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 中科辅龙计算机技术有限公司 2004
 * </p>
 * <p>
 * Company: 中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href="mailto:lixf@fulong.com.cn">李雄锋</a>
 * @version 1.0
 */

public class ReportData implements Serializable {
	
	private static final long serialVersionUID = -1975691186164657082L;
	
	private String x;
	private String y;
	private int value;

	public ReportData() {
	}

	public ReportData(int value) {
		this.value = value;
	}

	public ReportData(String x, String y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public ReportData(String x, int value) {
		this.x = x;
		this.value = value;
	}

	public String getX() {
		if (x == null) {
			return "";
		}
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		if (y == null) {
			return "";
		}
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public int getIntValue() {
		return Float.valueOf(value).intValue();
	}

	public void setValue(int value) {
		this.value = value;
	}
}
