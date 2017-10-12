/**
 * 
 */
package com.fulong.longcon.site.channel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * <p>Title: JsChannel</p>
 * <p>Description: Coolink协同工作支撑平台</p>
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p> 
 * @author    lixf
 * @date      2010-5-13
 */
public class JsChannel extends GeneralChannel{

	@Override
	public String getDisplayName() {
		String line = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file),DEFAULT_ENCODING));
			line = reader.readLine();
			reader.close();		
		} catch (IOException ex) {
			return this.getName();
		}
		if(line == null)
			return this.getName();
		int start = line.toLowerCase().indexOf("displayname");
		if(start>0){
			start += 12;
			int end = line.indexOf("*",start);
			if(end>0)
				return line.substring(start,end).trim();
		}
		return this.getName();				
	}


	@Override
	public String getType() {	
		return "script";
	}



}
