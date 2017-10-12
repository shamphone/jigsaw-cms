package com.fulong.portlet;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * <p>
 * Title: Longcon Portal
 * </p>
 * 
 * <p>
 * Description: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class URLParser {
	private String siteTemplateID;
	private String channelID;
	private String nodeID;

	public URLParser(String requestURL) {
		URL url = null;
		try {
			url = new URL(requestURL);
		} catch (MalformedURLException ex) {
		}
		this.siteTemplateID = url.getHost();
		String[] splits = url.getPath().split("\\W");
		this.channelID = splits[0];
		this.nodeID = splits[1];
	}

	public URLParser(String server, String path) {
		this.siteTemplateID = server;
		String[] splits = path.split("\\W");
		if (splits.length > 1)
			this.siteTemplateID = splits[1];
		if (splits.length > 2)
			this.channelID = splits[2];
		if (splits.length > 3)
			this.nodeID = splits[3];
	}

	public String getSiteTemplateName() {
		return this.siteTemplateID;
	}

	public String getChannelID() {
		return this.channelID;
	}

	public String getNodeID() {
		return this.nodeID;
	}
}
