package com.fulong.portlet.common.url;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.portlet.PortletPreferences;

import com.fulong.longcon.site.SiteTemplate;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class URLConfig {
	private PortletPreferences preferences;
	private SiteTemplate template;
	private URL url;

	public URLConfig(PortletPreferences preferences,SiteTemplate template) {
		this.preferences = preferences;
		this.template = template;
		try {
			this.url = new URL(this.getURL());
		} catch (MalformedURLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	public String getID() {
		return "url" + url.hashCode();
	}

	public String getURL() {
		String url = this.preferences.getValue("url", "#");
		if (!url.toLowerCase().startsWith("http")) {
			
				url = "http://" + url ;
		}
		return url;
	}

	public String getURI() {
		StringBuffer buffer = new StringBuffer(this.getURL());
		String query = this.getQueryString();
		if ((query != null) && (query.length() > 0)) {
			buffer.append("?");
			buffer.append(query);
		}
		return buffer.toString();
	}

	public String getQueryString() {
		StringBuffer buffer = new StringBuffer();
		String[] params = this.preferences.getValues("parameters", new String[0]);
		String[] values = this.preferences.getValues("values", new String[0]);
		if (params.length == 0)
			return null;
		for (int i = 0; i < params.length; i++) {
			if (params[i].length() > 0) {
				if (buffer.length() > 0)
					buffer.append("&");
				else
					buffer.append("?");
				buffer.append(params[i]);
				buffer.append("=");
				buffer.append(values[i]);
			}
		}
		return buffer.toString();
	}

	public String getMethod() {
		return this.preferences.getValue("method", "GET");
	}

	public int getTimeout() {
		return Integer.parseInt(this.preferences.getValue("timeout", "1")) * 1000;
	}
	
	public File getCacheDirectory() throws IOException {
		File directory = new File(template.getRealPath("cache"));
		if(!directory.exists()){
			directory.mkdir();
		}
		return directory;
	}
	
	public File getFile() throws IOException {
		File file = new File(this.getCacheDirectory(),this.getID() + ".jsp");
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
	public File getImageFolder() throws IOException {
		File imageFolder = new File(this.getCacheDirectory(),this.getID()+"/images");
		if(!imageFolder.exists()){
			imageFolder.mkdirs();
		}
		return imageFolder;
	}
	
	public String getPath(String absolutePath) throws IOException {
		return "/"+template.getName()+template.getContextPath(absolutePath);
	}

	public String getEncoding() {
		return this.preferences.getValue("encoding", null);
	}

}
