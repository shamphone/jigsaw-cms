package com.fulong.longcon.crawler;

import java.net.URI;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>
 * Title: 龙驭网页爬虫系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网页爬虫系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 2.0
 */
public class URLUtils {
	/**
	 * 处理查询参数，虽然存在Bug,但是可以满足这个系统的应用了
	 * 
	 * @param url
	 *            URL
	 * @return Map
	 */
	public static Map<String, String> parseQuery(URL url) {
		Map<String, String> result = new Hashtable<String, String>();
		String query = url.getQuery();
		if ((query == null) || (query.length() == 0))
			return result;
		query = decode(query);
		String[] pairs = query.split("&");
		for (int i = 0; i < pairs.length; i++) {
			String[] splits = pairs[i].split("=");
			if (splits.length == 1)
				result.put(pairs[0], "");
			else if (splits.length == 2)
				result.put(splits[0], splits[1]);
		}
		return result;
	}

	/**
	 * 解码url中的特殊字符
	 * 
	 * @param url
	 *            String
	 * @return String
	 */
	public static String decode(String url) {
		if (url == null)
			return url;
		return url.replaceAll("\\&amp\\;", "&");
	}

	/**
	 * 计算绝对地址
	 * 
	 * @param host
	 *            String 主机
	 * @param startURL
	 *            String 起始地址
	 * @return String 如果是非法地址，则返回空
	 */
	public static String calcPath(String host, String startURL) {
		return calcURL(host, startURL).toString();
	}

	/**
	 * 计算绝对地址
	 * 
	 * @param host
	 *            String 主机
	 * @param startURL
	 *            String 起始地址
	 * @return URL 如果是非法地址，则返回空
	 * @throws CrawlException
	 */
	public static URL calcURL(String host, String startURL)
			throws CrawlException {
		if ((startURL == null) || startURL.startsWith("#"))
			return null;
		try {
			return new URL(startURL);
		} catch (Exception ex) {
			try {
				URI hostURI = new URI(host);
				URI startURI = hostURI.resolve(startURL.trim());
				return startURI.toURL();
			} catch (Exception ex2) {
				return null;
			}
		}
	}

}
