/**
 * 
 */
package com.fulong.longcon.site.channel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fulong.longcon.site.SiteException;

/**
 * CssChannel css栏目，关于css的信息都记录在文件第一行；如果没有则系统会自动插入第一行内容；格式如下：
 * 
 * //fileinfo:displayname=公用脚本文件;date=2010-5-16;author=张三
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-5-26
 */
public class CssChannel extends GeneralChannel {
	
	/*
	 * 读取文件第一行，如果是一/*fileinfo开始，则为系统注释，读出displayname的字段；否则使用文件名；
	 * 
	 * @see com.fulong.longcon.site.Channel#getDisplayName()
	 */
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
		return "style";
	}
	
	@Deprecated 
	public void setDisplayName(String name) {
		try {
			@SuppressWarnings("unchecked")
			List<String> lines = IOUtils.readLines(new FileInputStream(file), "UTF-8");
			if (lines.size() > 0) {
				String line1 = lines.get(0);
				if (line1.trim().startsWith("//displayname")) {
					line1 = "//displayname=" + name;
					lines.set(0, line1);
				} else {
					line1 = "//displayname=" + name;
					lines.add(0, line1);
				}
			}else {
				String line1 = "//displayname=" + name;
				lines.add(0, line1);
			}
			IOUtils.writeLines(lines, "\r\n", new FileOutputStream(file));
		} catch (IOException ioe) {
			throw new SiteException("error in set display name for " + this.file.getPath() + ".", ioe);
		}

	}

}
