package com.fulong.common.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.Serializable;

/**
 * 
 * <p>
 * Title: ClientPageViewHelper
 * </p>
 * <p>
 * Description: 将本地的Pageview总数发�?�到中心站点(www.sme.cn)
 * </p>
 * <p>
 * 下面是一段示例代码：</br>
 * 
 * <pre>
 * ClientPageViewHelper helper = new ClientPageViewHelper(&quot;中小在线&quot;);
 * helper.updateSite(2000);
 * </pre>
 * 
 * 构�?�函数中的参数表示本地站点的名称，取值范围见下面的说明，updateSite�?<br/>
 * 参数为本地站点的总点击数�?<br/>
 * 也可以使用异步的调用，这样将生成�?个独立线程完成工作�??</br>
 * 
 * <pre>
 * ClientPageViewHelper helper = new ClientPageViewHelper(&quot;中小在线&quot;);
 * helper.updateSiteAsync(2000);
 * </pre>
 * 
 *<p>
 * 在实践中，因为生成ClientPageViewHelper对象比较慢，我们可以象数据库连接一样，生成
 * 一个全局对象保存起来，然后在这个全局对象上重复调用updateSite方法。
 *</p>
 * 
 * 在构造函数中hostname可能的取值范围如下<br/>
 * <ul>
 * <li>中小在线
 * <li>银河培训
 * <li>视频会议
 * <li>在线招聘
 * <li>APEC会议
 * </ul>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: ZhongKe Fulong
 * </p>
 * 
 * @author: 张小潘
 * @version 1.0
 * 
 */
public class ClientPageViewHelper implements Serializable {
	
	private static final long serialVersionUID = -7329348230177164779L;
	
	private String hostname;
	private String websiteUrl;

	public ClientPageViewHelper(String websiteUrl, String hostname) {
		this.hostname = hostname;
		this.websiteUrl = websiteUrl;
	}

	public ClientPageViewHelper(String hostname) {
		this.hostname = hostname;
		this.websiteUrl = "http://192.168.0.81/cms/updateSiteCounter.do";
	}

	/**
	 * 更新中心站点数据库中纪录的本地站点的pageview数目, pageView参数为本站点的pageview数目
	 * 
	 * @param pageView
	 *            :本站点的pageview数目
	 * @throws RemoteException
	 */
	synchronized public void updateSite(long pageView)
			throws java.io.IOException {
		try {
			String url = websiteUrl + "?hostname="
					+ URLEncoder.encode(hostname, "GB2312") + "&pageView="
					+ pageView;
			URLConnection urlConnection = (new URL(url)).openConnection();
			urlConnection.getInputStream().close();
		} catch (UnsupportedEncodingException ex) {
			System.out.println("UnsupportedEncodingException");
			return;
		} catch (MalformedURLException ex) {
			System.out.println("MalformedURLException");
			return;
		}
		return;
	}

	/**
	 * 更新中心站点数据库中纪录的本地站点的pageview数目,启动�?个独立的线程完成这项工作 pageView参数为本站点的pageview数目
	 * 
	 * @param pageView
	 *            :本站点的pageview数目
	 * @throws RemoteException
	 */
	public void updateSiteAsync(long pageView) {
		new ThreadHelper(pageView);
	}

	class ThreadHelper extends Thread {
		long count;

		ThreadHelper(long count) {
			this.count = count;
			start();
		}

		public void run() {
			try {
				updateSite(this.count);
			} catch (java.io.IOException ex) {
				System.out.println("ioexception: " + ex);
			}
		}
	}
}
