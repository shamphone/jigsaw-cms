/**
 * 
 */
package com.fulong.longcon.site.channel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>
 * Title: ClipChannel，页面片断。页面片断是生成不包含HTML/Body标签的Jspf片断。文件结构为 <%@page
 * contentType="text/html; charset=utf-8" info="栏目名称"%>
 * </p>
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @date 2010-5-13
 */
public class ClipChannel extends GeneralChannel {

	@Override
	public String getDisplayName() {
		String name = null;
		try {
			name = this.parseIsPage();
		} catch (IOException ex) {
			// just ignore;
		}

		if ((name == null) || (name.length() == 0))
			name = this.getName();
		return name;
	}

	private String parseIsPage() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file),DEFAULT_ENCODING));
		String line = reader.readLine();
		reader.close();
		if(line == null || line.length()==0)
			return null;
		int start = line.indexOf("info=");
		if (start < 0)
			return null;
		start += 6;
		int end = line.indexOf("\"", start);
		return line.substring(start, end);
	}

	@Override
	public String getType() {
		return "clip";
	}

}
