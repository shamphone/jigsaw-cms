package com.fulong.portlet.common.url;

import java.util.Timer;

import javax.portlet.PortletException;

import com.fulong.portlet.LongconPortlet;

/**
 * 远程资源占位符.在页面上显示远程页面的内容. 可配置的参数包括: 1. url：url地址，也可以将查询参数都放置到URL中 2.
 * parameters：查询参数集 3. values:参数值集，和parameters相对应 3. method：方法，GET,PUT,POST 4.
 * timeout：时间 5. encoding:缺省编码
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
public class URLPortlet extends LongconPortlet {
	
	private static final long serialVersionUID = 2781128440929654986L;

	public void initPortlet() throws PortletException {
		/*String directory = this.portletConfig.getInitParameter("directory");
		directory = this.portletConfig.getPortletContext().getRealPath(directory);
		File file = new File(directory);
		if (!file.exists())
			file.mkdirs();*/
		int period = Integer.parseInt(this.portletConfig.getInitParameter("period"));
		URLCache cache = new URLCache();
		Timer timer = new Timer("url synchronization.");
		timer.schedule(cache, 0, period);
		//log.info("Use " + directory + " for url cache directory.");
		this.portletConfig.getPortletContext().setAttribute(URLCache.ATTRIBUTE_NAME, cache);
		this.portletConfig.getPortletContext().setAttribute(URLCache.DIRECTORY_NAME,
				this.portletConfig.getInitParameter("directory"));
	}
}
