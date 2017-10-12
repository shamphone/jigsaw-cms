package com.fulong.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: Longcon Passport 2.0
 * </p>
 * 
 * <p>
 * Description: Longcon Passport core System
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

public class StatResult implements Serializable {
	
	private static final long serialVersionUID = -3431212404811831240L;
	
	String[] xrows;
	String[] yrows;
	ReportData[][] values;
	ReportData[] xsum;
	ReportData[] ysum;
	ReportData sum;

	public StatResult(String[] xrows, String[] yrows, ReportData[][] values) {
		this.xrows = xrows;
		this.yrows = yrows;
		this.values = values;
		List<ReportData> xList = new ArrayList<ReportData>();
		List<ReportData> yList = new ArrayList<ReportData>();
		for (int i = 0; i < values.length; i++) {
			int tmp = 0;
			ReportData tmpData = new ReportData();
			for (int j = 0; j < values[i].length; j++) {
				tmp += ((ReportData) values[i][j]).getValue();
				tmpData.setX(j + 1 + "");
			}
			tmpData.setValue(tmp);
			tmpData.setY(i + 1 + "");
			yList.add(i, tmpData);
		}
		for (int i = 0; i < values[0].length; i++) {
			int tmp = 0;
			ReportData tmpData = new ReportData();
			for (int j = 0; j < values.length; j++) {
				tmp += ((ReportData) values[j][i]).getValue();
				tmpData.setX(j + 1 + "");
			}
			tmpData.setValue(tmp);
			tmpData.setY(i + 1 + "");
			xList.add(i, tmpData);
		}

		xsum = new ReportData[xList.size()];
		for (int i = 0; i < xList.size(); i++) {
			xsum[i] = (ReportData) xList.get(i);
		}
		ysum = new ReportData[yList.size()];
		for (int i = 0; i < yList.size(); i++) {
			ysum[i] = (ReportData) yList.get(i);
		}

		int sumValue = 0;
		for (int i = 0; i < xsum.length; i++) {
			sumValue += xsum[i].getValue();
		}
		this.sum = new ReportData();
		this.sum.setX(xsum.length + 1 + "");
		this.sum.setY(ysum.length + 1 + "");
		this.sum.setValue(sumValue);
	}

	public ReportData getSum() {
		return this.sum;
	}

	public ReportData getSumX(int x) {
		return this.xsum[x];
	}

	public ReportData[] getSumX() {
		return this.xsum;
	}

	public ReportData[] getX(int x) {
		return this.values[x];
	}

	public ReportData getSumY(int y) {
		return this.ysum[y];
	}

	public String[] getXRows() {
		return xrows;
	}

	public String[] getYRows() {
		return yrows;
	}

	public String getYRow(int y) {
		return yrows[y];
	}

	public ReportData getValue(int x, int y) {
		return this.values[x][y];
	}

}
