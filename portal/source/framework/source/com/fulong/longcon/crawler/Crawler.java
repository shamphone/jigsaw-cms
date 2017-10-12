package com.fulong.longcon.crawler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.SAXException;

import com.fulong.longcon.crawler.queue.FileListQueue;

/**
 * 
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
 * @author 马伟山
 * @author Lixf
 * @version 2.0
 */
public class Crawler {
	private String encoding = "UTF-8";
	private Queue<String> urls = null; // 待转换地址队列
	private ConvertRule rules;
	private String host; // 主机名称;
	private File folder; // 目标文件夹
	private static Log log = LogFactory.getLog(Crawler.class);

	/**
	 * 获取爬虫，使用缺省的地址队列管理器和编码
	 * 
	 * @param host
	 *            String 主机地址，如http://www.sme.gov.cn
	 * @param staticFolder
	 *            String 保存静态文件的目录， 如 c:\\sme
	 * @param rule
	 *            ConvertRule 转换规则，具体的规则实现在rule目录下
	 * @return Crawler
	 */
	public static Crawler getHostCrawler(String host, String staticFolder,
			ConvertRule rule) {
		Crawler crawler = new Crawler();
		crawler.setHost(host);
		crawler.setFolder(staticFolder);
		crawler.setConvertRule(rule);
		return crawler;
	}

	/**
	 * 获取爬虫
	 * 
	 * @param urlListFilePath
	 *            String url列表文件
	 * @param ruleFilePath
	 *            String 规则文件路径
	 * @param staticFolder
	 *            String 存放静态文件的文件夹
	 * @return Crawler
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Crawler getListCrawler(String urlListFilePath,
			String staticFolder, ConvertRule rule) throws IOException,
			SAXException {
		Crawler crawler = new Crawler();
		FileListQueue query = new FileListQueue();
		query.setFilePath(urlListFilePath);
		crawler.setConvertRule(rule);
		crawler.setQueue(query);
		crawler.setFolder(staticFolder);
		return crawler;
	}

	/**
	 * 
	 * @param pageURL
	 *            String
	 * @param queque
	 *            boolean
	 * @throws IOException
	 */
	private Collection<String> doCrawl(String pageURL) throws IOException {
		String url = URLUtils.calcPath(this.host, pageURL);
		log.trace("Begin crawl page url:" + url + ".");
		Parser parser = null;
		try {
			parser = new Parser(url.toString());
		} catch (ParserException ex) {
			throw new CrawlException(ex);
		}
		ParserNodeFilter filter = new ParserNodeFilter(this.rules);
		NodeList nodes;
		try {
			nodes = parser.parse(filter);
		} catch (ParserException ex1) {
			throw new CrawlException(ex1);
		}
		File file = new File(this.folder, this.rules.convert(new URL(url)));
		if (!file.exists()) {
			File parent = file.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			file.createNewFile();
		}
		String encoding = parser.getEncoding();
		if (encoding == null)
			encoding = this.encoding;
		FileUtils.writeStringToFile(file, nodes.toHtml(), encoding);
		return filter.getFindURLs();
	}

	/**
	 * 静态化单个页面
	 * 
	 * @param pageURL
	 *            String
	 *            页面地址，相对于主机的页面地址。如对于内容管理系统2.5，一般是web/assembly/action/browsePage
	 *            .do
	 * @throws IOException
	 */
	public void crawlPage(String pageURL) throws IOException {
		this.doCrawl(pageURL);
	}

	/**
	 * 静态化整个网站
	 * 
	 * @param startPage
	 *            String
	 *            起始页面地址，相对于主机的页面地址。如对于内容管理系统2.5，一般是web/assembly/action/browsePage
	 *            .do
	 * @throws IOException
	 */
	public void crawlSite(String startPage) throws IOException {
		String startURL = URLUtils.calcPath(this.host, startPage);
		this.urls.add(startURL);
		while (!this.urls.isEmpty()) {
			String pageURL = (String) this.urls.peek();
			Collection<String> urls = this.doCrawl(pageURL);
			for (String url : urls) {
				this.urls.add(URLUtils.calcPath(startURL, url));
			}
			this.urls.poll();
		}
	}

	/**
	 * 从队列中取出地址直接遍历
	 * 
	 * @throws IOException
	 */
	public void crawlSite() throws IOException {
		while (!this.urls.isEmpty()) {
			String pageURL = (String) this.urls.peek();
			Collection<String> urls = this.doCrawl(pageURL);
			for (String url : urls) {
				this.urls.add(url);
			}
			this.urls.poll();
		}
	}

	/**
	 * 缺省的，系统将自动获取页面编码，用户也可以手动设置缺省页面编码
	 * 
	 * @param encoding
	 *            String
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 设置静态页面保存路径
	 * 
	 * @param path
	 *            String
	 * @return String
	 */
	public void setFolder(String path) {
		this.folder = new File(path);
		if (!this.folder.exists())
			folder.mkdirs();
	}

	/**
	 * 设置转换规则
	 * 
	 * @param path
	 *            String
	 */
	public void setConvertRule(ConvertRule rules) {
		this.rules = rules;
	}

	/**
	 * 设置队列管理
	 * 
	 * @param className
	 *            String
	 */
	@SuppressWarnings("unchecked")
	public void setQueue(Queue queue) {
		this.urls = queue;
	}

	/**
	 * 设置主机地址
	 * 
	 * @param host
	 *            String
	 */
	public void setHost(String host) {
		this.host = host;
	}

}
