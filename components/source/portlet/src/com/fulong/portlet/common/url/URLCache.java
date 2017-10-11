package com.fulong.portlet.common.url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import javax.portlet.PortletPreferences;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.fulong.common.DomUtils;
import com.fulong.common.FileUtils;
import com.fulong.longcon.site.SiteTemplate;

/**
 * URL内容缓冲管理器。定时更新URL的内容。
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
public class URLCache extends TimerTask {
	private Map<String, URLConfig> configs;
	private Log log = LogFactory.getLog(URLCache.class);
	public static final String ATTRIBUTE_NAME = "com.fulong.URLCache";
	public static final String DIRECTORY_NAME = "com.fulong.URLCache.directory";

	public URLCache() {
		configs = Collections.synchronizedMap(new HashMap<String, URLConfig>());
	}

	public String add(PortletPreferences preferences,SiteTemplate template) throws IOException {
		URLConfig config = new URLConfig(preferences,template);
		String ID = config.getID();
		configs.put(ID, config); 
		return template.getContextPath(config.getFile().getPath());
	}

	@SuppressWarnings("deprecation")
	public void run() {
		Iterator<String> ids = configs.keySet().iterator();
		synchronized (configs) {
			while (ids.hasNext()) {
				String id = ids.next();
				URLConfig config = configs.get(id);
				HttpClient client = new HttpClient();
				HttpMethod method = this.prepareMethod(config);

				// Create a method instance.

				// Provide custom retry handler is necessary
				// method.get().setParameter(HttpMethodBase.RETRY_HANDLER, new
				// DefaultMethodRetryHandler(3, false));

				try {
					log.info("Begin update url:" + config.getURI() + " to " + config.getFile().getPath());

					client.setTimeout(config.getTimeout());
					// Execute the method.
					int statusCode = client.executeMethod(method);

					if (statusCode != HttpStatus.SC_OK) {
						log.error("Method failed: " + method.getStatusLine());
					} else {
						String source = null;
						String encoding = config.getEncoding();
						if(encoding==null){
							encoding = this.parseEncoding(method);
						}
						if(encoding==null){
							encoding = System.getProperty("file.encoding", "UTF-8");
						}
						InputStream is = method.getResponseBodyAsStream();
						source = IOUtils.toString(is, encoding);
						
						HtmlParser parser = new HtmlParser(source,encoding);
						NodeList images = parser.extractAllImages();
						this.fixAllImages(images,config);
						String body = parser.extractBodyContent().toHtml();

						this.writeJsp(config.getFile(), body, encoding);
					}
				} catch (HttpException e) {
					log.error("Fatal protocol violation: " + e.getMessage());
				} catch (IOException e) {
					log.error("Fatal transport error: " + e.getMessage());
				} catch (Throwable e) {
					log.error("Error: " + e.getMessage());
				} finally {
					// Release the connection.
					method.releaseConnection();
				}

			}
		}
	}

	private HttpMethod prepareMethod(URLConfig config) {
		String url = config.getURI();
		if (url.indexOf("?") > 0)
			url = url + "&cachertime=" + System.currentTimeMillis();
		else
			url = url + "?cachertime=" + System.currentTimeMillis();
		// url =
		// "http://www.smezcfg.com/longcon/sites/zhengcefagui/indexTemp.jsp"+"?cachertime="+System.currentTimeMillis();
		HttpMethod method = null;
		if (config.getMethod().equals("GET"))
			method = new GetMethod(url);
		else if (config.getMethod().equals("POST"))
			method = new PostMethod(url);
		else if (config.getMethod().equals("PUT"))
			method = new PutMethod(url);
		else
			throw new IllegalArgumentException("Unknown method:" + config.getMethod());

		if (config.getQueryString() != null)
			method.setQueryString(config.getQueryString());
		return method;
	}

	public void writeJsp(File file, String contents, String encoding) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<%@ page contentType='text/html; charset=" + encoding + "'%>");
		sb.append("\r\n");

		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream writer = new FileOutputStream(file);
		writer.write(sb.toString().getBytes(encoding));
		writer.write(contents.getBytes(encoding));
		writer.close();

	}

	@SuppressWarnings("deprecation")
	protected String parseEncoding(HttpMethod method) throws HttpException, ParserException {
		Header header = method.getResponseHeader("content-type");
		// try to parser encoding from response header;
		if (header != null) {
			HeaderElement values[] = header.getValues();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					return param.getValue();
				}
			}
		}
		return null;
	}

	private void fixAllImages(NodeList list,URLConfig config) throws ParserException, IOException{
		File imagesFolder = config.getImageFolder();
		for(int i=0;i<list.size();i++){
			ImageTag node = (ImageTag)list.elementAt(i);
			String imageURL = node.getImageURL();
			//对于特殊字符&和'，htmlparser会转换成xml实体
			imageURL = DomUtils.antiFilter(imageURL);
			try {
				URL absoluteURL = this.makeAbsoluteURL(new URL(config.getURL()), imageURL);
				String imageName = FilenameUtils.getName(imageURL);
				File imageFile = new File(imagesFolder,imageName);
				if(!imageFile.exists()){
					FileUtils.copyURLToFile(absoluteURL,imageFile );
				}
				node.setImageURL(config.getPath(imageFile.getPath()));
			} catch (Exception e) {
				log.error("error in update image:"+imageURL,e);
			}
		}
	}
	
	 /**
	 * 类似于js中的encodeURI 对特殊字符进行处理
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String encode(String str) throws UnsupportedEncodingException{   
        String isoStr = new String(str.getBytes("UTF8"), "ISO-8859-1");        
        char[] chars = isoStr.toCharArray();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < chars.length; i++) {   
            if ((chars[i] <= 'z' && chars[i] >= 'a')   
                    || (chars[i] <= 'Z' && chars[i] >= 'A') || chars[i] == '-'  
                    || chars[i] == '_' || chars[i] == '.' || chars[i] == '!'  
                    || chars[i] == '~' || chars[i] == '*' || chars[i] == '\''  
                    || chars[i] == '(' || chars[i] == ')' || chars[i] == ';'  
                    || chars[i] == '/' || chars[i] == '?' || chars[i] == ':'  
                    || chars[i] == '@' || chars[i] == '&' || chars[i] == '='  
                    || chars[i] == '+' || chars[i] == '$' || chars[i] == ','  
                    || chars[i] == '#') {   
                sb.append(chars[i]);   
            } else {   
                sb.append("%");   
                sb.append(Integer.toHexString(chars[i]));   
            }   
        }   
        return sb.toString();   
    }

	/**
	 *方法说明：相对路径转绝对路径
	 *输入参数：strWeb 网页地址; innerURL 相对路径链接
	 *返回类型：绝对路径链接
	 * @throws MalformedURLException 
	 * @throws UnsupportedEncodingException 
	 */
	private URL makeAbsoluteURL(URL pageURL, String innerURL) throws MalformedURLException, UnsupportedEncodingException {
		//去除后缀
		int pos = innerURL.indexOf("?");
		if (pos != -1) {
			innerURL = innerURL.substring(0, pos);
		}
		if (innerURL != null&& innerURL.toLowerCase().indexOf("http") == 0) {
			return new URL(innerURL);
		}
		URL linkUri = new URL(pageURL, innerURL);
		return new URL(encode(linkUri.toString()));
	}
	
	private class HtmlParser{
		private NodeList nodes;
		public HtmlParser(String html,String charset) throws ParserException{
			Parser parser = Parser.createParser(html, charset);
			this.nodes = parser.parse(new NodeFilter() {
				private static final long serialVersionUID = -5120288323648804850L;
				public boolean accept(Node node) {
					return true;
				};
			});
		}
		public HtmlParser(String html) throws ParserException{
			this(html, "UTF-8");
		}
		
		public NodeList extractAllImages(){
			NodeList list = nodes.extractAllNodesThatMatch(new NodeClassFilter(ImageTag.class));
			return list;
		}
		
		public String parseEncoding(String html, String encoding) throws ParserException {
			// try to parser header from body;
			NodeFilter metaFilter = new NodeFilter() {
				private static final long serialVersionUID = 1L;

				public boolean accept(Node node) {
					if (node instanceof MetaTag) {
						MetaTag tag = (MetaTag) node;
						if ("content-type".equalsIgnoreCase(tag.getHttpEquiv()))
							return true;
					}
					return false;
				};
			};
			NodeList list = nodes.extractAllNodesThatMatch(metaFilter);
			if (list.size() == 1) {
				MetaTag tag = (MetaTag) list.elementAt(0);
				String content = tag.getMetaContent();
				String contents[] = content.split("[;=]");
				if (contents.length == 3) {
					return contents[2];

				}
			}
			return null;
		}
		
		protected NodeList extractBodyContent() throws ParserException {
			NodeList body = nodes.extractAllNodesThatMatch(new NodeClassFilter(BodyTag.class));
			if (body.size() == 0)
				return nodes;
			return body.elementAt(0).getChildren();
		}
		
		public String toHtml(){
			return nodes.toHtml();
		}
	}
}
