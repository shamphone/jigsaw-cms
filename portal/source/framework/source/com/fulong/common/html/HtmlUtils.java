package com.fulong.common.html;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ObjectTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
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
 * @author lichengzhao
 * @version 3.1
 */
public class HtmlUtils {

	public static final int SUCCESS = 0;
	public static final int MALFORMED_URL = 1;
	public static final int FAILED = 2;
	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String ENCODING_ISO88591 = "ISO-8859-1";
	// <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"
	// />
	public static final Pattern PATTERN_CONTENT_TYPE = Pattern
			.compile("text/html;\\s*charset=(\\S+)");
	public static final Pattern PATTERN_ENCODING = Pattern.compile(
			"<meta[^>]+http-equiv=['\"]?Content-Type['\"]?[^>]*>",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern PATTERN_CHARSET = Pattern.compile(
			"content=['\"]?text/html;\\s*charset=([^'\">\\s]+)['\"]?[^>]*>",
			Pattern.CASE_INSENSITIVE);
	// <BASE HREF="http://msdn.microsoft.com/workshop/author/dhtml/reference/"/>
	public static final Pattern PATTERN_BASE_TAG = Pattern.compile(
			"<\\s*base[^>]+href\\s*=\\s*['\"]\\s*([^\\s'\">]+)\\s*['\"][^>]*>",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern PATTERN_EMBED_IN_OBJECT_TAG = Pattern.compile(
			"<object.+?(<embed.+?</embed>).+?</object>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
	protected static Log log = LogFactory.getLog(HtmlUtils.class);

	public static String downloadHtmlWithResources(String srcUrl,
			File resourceFolder, String folderRelativePath, String destEncoding)
			throws ParserException, HttpException, IOException {
		StringBuilder url = new StringBuilder(srcUrl);
		String htmlText = HtmlUtils.getRemoteText(url);
		url = processBaseTag(htmlText, url);
		htmlText = repleaceObjectWithEmbed(htmlText);
		Parser parser = Parser.createParser(htmlText, null);
		NodeList nodes = parser.parse(null);
		nodes.extractAllNodesThatMatch(new MyNodeFilter(url.toString(),
				resourceFolder, folderRelativePath, destEncoding), true);
		htmlText = nodes.toHtml();
		// htmlText = new String(htmlText.getBytes(HtmlUtils.ENCODING_ISO88591),
		// HtmlUtils.ENCODING_UTF8);
		return htmlText;
	}

	protected static String repleaceObjectWithEmbed(String htmlText) {
		Matcher m = PATTERN_EMBED_IN_OBJECT_TAG.matcher(htmlText);
		StringBuffer ret = new StringBuffer();
		while (m.find())
			m.appendReplacement(ret, m.group(1));
		m.appendTail(ret);
		return ret.toString();
	}

	protected static StringBuilder processBaseTag(String htmlText,
			StringBuilder url) {
		Matcher m = PATTERN_BASE_TAG.matcher(htmlText);
		if (m.find())
			return new StringBuilder(m.group(1));
		return url;
	}

	public static String getRemoteText(StringBuilder url)
			throws UnknownHostException, HttpException, IOException {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url.toString());
		client.getParams().setConnectionManagerTimeout(0);
		int statusCode = 0;
		String data = null;
		String encoding = null;
		try {
			statusCode = client.executeMethod(method);

			if (statusCode == HttpStatus.SC_OK) {
				data = method.getResponseBodyAsString();
				encoding = HtmlUtils.getEncoding(data);
				if (encoding != null) {
					byte[] bytes = method.getResponseBody();
					data = new String(bytes, encoding);
				}
				url.append(method.getPath());
			} else {
				throw new IOException();
			}
		} finally {
			method.releaseConnection();
		}
		return data;
	}

	protected static String getEncoding(String data) {
		if (data == null)
			return null;
		String encoding = null;
		Matcher m = PATTERN_ENCODING.matcher(data);
		if (m.find()) {
			String s1 = m.group();
			m = PATTERN_CHARSET.matcher(s1);
			if (m.find())
				encoding = m.group(1);
		}
		return encoding;
	}

	public static byte[] getRemoteBytes(String url) throws HttpException,
			IOException {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		client.getParams().setConnectionManagerTimeout(0);
		int statusCode = 0;
		byte[] data = null;

		try {
			statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK)
				data = method.getResponseBody();
		} finally {
			method.releaseConnection();
		}
		return data;
	}

	private static class MyNodeFilter implements NodeFilter {
		private static final long serialVersionUID = -9168756808556676432L;

		private String url;
		private String destEncoding;
		private File rootFolder;
		private File imageFolder;
		private File cssFolder;
		private File scriptFolder;
		private String rootPathPrefix;
		private String imagePathPrefix;
		private String cssPathPrefix;
		private String scriptPathPrefix;
		private Map<String, File> imageFiles = new HashMap<String, File>();
		private Map<String, File> cssFiles = new HashMap<String, File>();
		private Map<String, File> jsFiles = new HashMap<String, File>();
		private Map<String, File> otherFiles = new HashMap<String, File>();

		MyNodeFilter(String url, File resourceFolder,
				String folderRelativePath, String destEncoding) {
			super();
			this.url = url;
			this.destEncoding = destEncoding;
			this.rootFolder = resourceFolder;
			this.imageFolder = new File(resourceFolder, "images");
			this.cssFolder = new File(resourceFolder, "style");
			this.scriptFolder = new File(resourceFolder, "script");

			this.imageFolder.mkdirs();
			this.cssFolder.mkdirs();
			this.scriptFolder.mkdirs();

			this.rootPathPrefix = folderRelativePath;
			this.imagePathPrefix = folderRelativePath + "/images/";
			this.cssPathPrefix = folderRelativePath + "/style/";
			this.scriptPathPrefix = folderRelativePath + "/script/";
		}

		public boolean accept(Node node) {
			if (node instanceof Tag) {
				try {
					Tag tag = (Tag) node;
					if ("IMG".equals(tag.getTagName()))
						processImgTag(tag);
					else if ("SCRIPT".equals(tag.getTagName()))
						processScriptRefTag(tag);
					else if (tag instanceof MetaTag)
						processMetaTag((MetaTag) tag);
					else if ("LINK".equals(tag.getTagName()))
						processLinkTag(tag);
					else if ("EMBED".equals(tag.getTagName()))
						processEmbedTag(tag);
					else if (tag instanceof ObjectTag)
						processObjectTag((ObjectTag) tag);
				} catch (MalformedURLException ex) {
					log.warn(this, ex);
				} catch (IOException ex) {
					log.warn(this, ex);
				} catch (URISyntaxException ex) {
					log.warn(this, ex);
				} catch (NullPointerException ex) {
					log.warn(this, ex);
				}
			}
			return true;
		}

		private void processMetaTag(MetaTag tag) {
			// <meta http-equiv="Content-Type"
			// content="text/html; charset=ISO-8859-1" />
			if (tag.getHttpEquiv() != null) {
				if (tag.getHttpEquiv().toLowerCase().equals("content-type"))
					processMetaContentType(tag);
			}
		}

		private void processMetaContentType(MetaTag tag) {
			// <meta http-equiv="Content-Type"
			// content="text/html; charset=ISO-8859-1" />
			String contentType = tag.getMetaContent();
			if (contentType == null)
				return;
			Matcher matcher = PATTERN_CONTENT_TYPE.matcher(contentType);
			if (matcher.find()) {
				contentType = contentType.replace(matcher.group(1),
						this.destEncoding);
				tag.setMetaTagContents(contentType);
			}
		}

		private void processImgTag(Tag tag) throws MalformedURLException,
				URISyntaxException, IOException {
			String src = tag.getAttribute("src");
			if (src == null)
				return;
			src = src.trim();
			if (src.endsWith("/"))
				src = src.substring(0, src.length() - 1);
			File file = imageFiles.get(src);
			if (file == null) {
				URL completeSrc = HtmlUtils.getResourceURL(this.url, src);
				// FileUtils.copyURLToFile(completeSrc, file);
				byte[] data = HtmlUtils.getRemoteBytes(completeSrc.toString());
				if (data == null)
					return;
				file = HtmlUtils.createNewFile(completeSrc.getFile(),
						this.imageFolder);
				FileUtils.writeByteArrayToFile(file, data);
				imageFiles.put(src, file);
			}
			String newSrc = null;
			if (this.imagePathPrefix.endsWith("\\")
					|| this.imagePathPrefix.endsWith("/")
					|| file.getName().startsWith("\\")
					|| file.getName().startsWith("/"))
				newSrc = this.imagePathPrefix + file.getName();
			else
				newSrc = this.imagePathPrefix + "/" + file.getName();
			tag.setAttribute("src", newSrc);
		}

		private void processLinkTag(Tag tag) throws IOException,
				URISyntaxException, MalformedURLException {
			if ("stylesheet".equals(tag.getAttribute("rel")))
				this.processLinkCssTag(tag);
			/** @todo developing... */
		}

		private void processLinkCssTag(Tag tag) throws MalformedURLException,
				URISyntaxException, IOException {
			// <link rel="stylesheet" type="text/css"
			// href="/ui/styles/topic.css" />
			String src = tag.getAttribute("href");
			if (src == null)
				return;
			src = src.trim();
			if (src.endsWith("/"))
				src = src.substring(0, src.length() - 1);
			File file = cssFiles.get(src);
			if (file == null) {
				URL completeSrc = HtmlUtils.getResourceURL(this.url, src);
				byte[] data = HtmlUtils.getRemoteBytes(completeSrc.toString());
				if (data == null)
					return;
				file = HtmlUtils.createNewFile(completeSrc.getFile(),
						this.cssFolder);
				FileUtils.writeByteArrayToFile(file, data);
				cssFiles.put(src, file);
			}
			String newSrc = null;
			if (this.cssPathPrefix.endsWith("\\")
					|| this.cssPathPrefix.endsWith("/")
					|| file.getName().startsWith("\\")
					|| file.getName().startsWith("/"))
				newSrc = this.cssPathPrefix + file.getName();
			else
				newSrc = this.cssPathPrefix + "/" + file.getName();
			tag.setAttribute("href", newSrc);
		}

		private void processScriptRefTag(Tag tag) throws MalformedURLException,
				URISyntaxException, IOException {
			// <script type="text/javascript"
			// src="/ui/scripts/Csdn/Topic.js"></script>
			String src = tag.getAttribute("src");
			if (src == null)
				return;
			src = src.trim();
			if (src.endsWith("/"))
				src = src.substring(0, src.length() - 1);
			File file = jsFiles.get(src);
			if (file == null) {
				URL completeSrc = HtmlUtils.getResourceURL(this.url, src);
				byte[] data = HtmlUtils.getRemoteBytes(completeSrc.toString());
				if (data == null)
					return;
				file = HtmlUtils.createNewFile(completeSrc.getFile(),
						this.scriptFolder);
				FileUtils.writeByteArrayToFile(file, data);
				jsFiles.put(src, file);
			}
			String newSrc = null;
			if (this.scriptPathPrefix.endsWith("\\")
					|| this.scriptPathPrefix.endsWith("/")
					|| file.getName().startsWith("\\")
					|| file.getName().startsWith("/"))
				newSrc = this.scriptPathPrefix + file.getName();
			else
				newSrc = this.scriptPathPrefix + "/" + file.getName();
			tag.setAttribute("src", newSrc);
		}

		private void processEmbedTag(Tag tag) throws IOException,
				URISyntaxException, MalformedURLException {
			String src = tag.getAttribute("src");
			if (src == null)
				return;
			src = src.trim();
			if (src.endsWith("/"))
				src = src.substring(0, src.length() - 1);
			File file = otherFiles.get(src);
			if (file == null) {
				URL completeSrc = HtmlUtils.getResourceURL(this.url, src);
				byte[] data = HtmlUtils.getRemoteBytes(completeSrc.toString());
				if (data == null)
					return;
				file = HtmlUtils.createNewFile(completeSrc.getFile(),
						this.rootFolder);
				FileUtils.writeByteArrayToFile(file, data);
				otherFiles.put(src, file);
			}
			String newSrc = null;
			if (this.rootPathPrefix.endsWith("\\")
					|| this.rootPathPrefix.endsWith("/")
					|| file.getName().startsWith("\\")
					|| file.getName().startsWith("/"))
				newSrc = this.rootPathPrefix + file.getName();
			else
				newSrc = this.rootPathPrefix + "/" + file.getName();
			tag.setAttribute("src", newSrc);
		}

		@SuppressWarnings("unchecked")
		private void processObjectTag(ObjectTag tag) throws IOException,
				URISyntaxException, MalformedURLException {
			Hashtable params = tag.getObjectParams();
			String src = (String) params.get("SRC");
			if (src == null)
				return;
			src = src.trim();
			if (src.endsWith("/"))
				src = src.substring(0, src.length() - 1);
			File file = otherFiles.get(src);
			if (file == null) {
				URL completeSrc = HtmlUtils.getResourceURL(this.url, src);
				byte[] data = HtmlUtils.getRemoteBytes(completeSrc.toString());
				if (data == null)
					return;
				file = HtmlUtils.createNewFile(completeSrc.getFile(),
						this.rootFolder);
				FileUtils.writeByteArrayToFile(file, data);
				otherFiles.put(src, file);
			}
			String newSrc = null;
			if (this.rootPathPrefix.endsWith("\\")
					|| this.rootPathPrefix.endsWith("/")
					|| file.getName().startsWith("\\")
					|| file.getName().startsWith("/"))
				newSrc = this.rootPathPrefix + file.getName();
			else
				newSrc = this.rootPathPrefix + "/" + file.getName();
			params.put("SRC", newSrc);
			tag.setObjectParams(params);
		}
	}

	private static URL getResourceURL(String baseUrl, String resourceSrc)
			throws URISyntaxException, MalformedURLException {
		URI baseUri = new URI(baseUrl.endsWith("/") ? baseUrl : baseUrl + "/");
		if (resourceSrc.startsWith(baseUri.getScheme()))
			return new URL(resourceSrc);
		URI uri = baseUri.resolve(new URI(resourceSrc));
		return uri.toURL();
	}

	private static File createNewFile(String baseName, File parent)
			throws IOException {
		// <img src="http://163.wrating.com/a.gif?a=&c=860010-0503010000"
		// width="1" height="1"/>
		if (baseName.contains("?"))
			baseName = baseName.substring(0, baseName.indexOf("?"));
		if (baseName.contains("/"))
			baseName = baseName.substring(baseName.lastIndexOf("/"));
		File file = new File(parent, baseName);
		if (!file.exists()) {
			if (!file.createNewFile())
				throw new IOException("Create File[" + file.getAbsolutePath()
						+ "] Failed.");
			return file;
		}
		String name = baseName;
		String ext = "";
		int pos = baseName.lastIndexOf(".");
		if (pos != -1) {
			name = baseName.substring(0, pos);
			ext = baseName.substring(pos);
		}
		do {
			file = new File(parent, name + new Random().nextInt(10000) + ext);
		} while (file.exists());
		file.createNewFile();
		return file;
	}
}
