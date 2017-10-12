package com.fulong.site.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.ObjectTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.Channel;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: 显示模型管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @version 1.0.1
 */
public class ChannelPageSizeAction extends ChannelBaseAction {

	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm aform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String channelPath = request.getParameter("channelPath");
		Channel channel = this.parseChannel(channelPath,request);
		Repository repository = this.getRepository(request);
		ChannelPageAnalyzer analyzer = new ChannelPageAnalyzer(request,channel,repository);
		//		long HTMLSize = analyzer.analyzeHTMLSize();
		//		long StylesSize = analyzer.analyzeStylesSize();
		//		long ScriptsSize = analyzer.analyzeScriptsSize();
		//		long ImagesSize = analyzer.analyzeImagesSize();

		response.setContentType("text/html");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		Writer writer = response.getWriter();
		writer.append("" + (analyzer.analyzeHTMLSize()+analyzer.analyzeImagesSize()));
		writer.close();
		return null;
	}
	
	class ChannelPageAnalyzer {
		public  final NodeFilter STYLE_TAG_FILTER = new TagNameFilter("link");
		public  final NodeFilter SCRIPT_TAG_FILTER = new NodeClassFilter(ScriptTag.class);
		public  final NodeFilter IMAGE_TAG_FILTER = new NodeClassFilter(ImageTag.class);
		public  final NodeFilter MEDIA_TAG_FILTER = new NodeClassFilter(ObjectTag.class);
		private final String url;
		private final HttpServletRequest request;
		private NodeList lists = new NodeList();
		private long htmlSize = 0;

		public ChannelPageAnalyzer(HttpServletRequest request,Channel channel,Repository repository) throws Exception{
			super();
			this.request = request;
			String url = "/"+channel.getSiteTemplate().getName()+"/"+channel.getName()+".jsp.bak.bak";
			NodeDefinition definition = channel.getBindingNode();
			if(definition!=null){
				Query query = repository.getQueryManager().createQuery(definition, Query.SQL);
				Iterator<Node>  it =   query.nodes();
				if(it.hasNext()){
					Node node = it.next();
					url += "?contentId="+node.getID();
				}
			}
			this.url = RequestUtils.serverURL(request) + url;
			renderNodeList();
		}

		private void renderNodeList() throws Exception {
			String html = null;
			HttpClient client = new HttpClient();
			client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			client.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER,true);
			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,DEFAULT_ENCODING);

			GetMethod authget = null;
			authget = this.prepareGetMethod(this.url);
			authget.setRequestHeader("Cookie",request.getHeader("Cookie"));
			try {
				client.executeMethod(authget);
				InputStream in =  authget.getResponseBodyAsStream();
				String encoding = authget.getRequestCharSet();
				if(encoding==null){
					encoding = DEFAULT_ENCODING;
				}
				html = IOUtils.toString(in, encoding);
				htmlSize = html.getBytes(encoding).length;
			} finally{
				authget.releaseConnection();
			}
			Parser parser = Parser.createParser(html, DEFAULT_ENCODING);
			this.lists = parser.parse(null);
		}

		private Collection<Tag> extractStylesNodes(){
			Map<String,Tag> map = new HashMap<String,Tag>();
			NodeList tempLists = lists.extractAllNodesThatMatch(STYLE_TAG_FILTER,true);
			for(int i=0;i<tempLists.size();i++){
				Tag tag = (Tag) tempLists.elementAt(i);
				String href = tag.getAttribute("href");
				if(href!=null&&href.length()>0){
					map.put(href, tag);
				}
			}
			return map.values();
		}

		private Collection<Tag> extractScriptsNodes (){
			Map<String,Tag> map = new HashMap<String,Tag>();
			NodeList tempLists = lists.extractAllNodesThatMatch(SCRIPT_TAG_FILTER,true);
			for(int i=0;i<tempLists.size();i++){
				Tag tag = (Tag) tempLists.elementAt(i);
				String src = tag.getAttribute("src");
				if(src!=null&&src.length()>0){
					map.put(src, tag);
				}
			}
			return map.values();
		}

		private Collection<Tag> extractAllImageNodes (){
			Map<String,Tag> map = new HashMap<String,Tag>();
			NodeList tempLists = lists.extractAllNodesThatMatch(IMAGE_TAG_FILTER,true);
			for(int i=0;i<tempLists.size();i++){
				Tag tag = (Tag) tempLists.elementAt(i);
				String src = tag.getAttribute("src");
				if(src!=null&&src.length()>0){
					map.put(src, tag);
				}
			}
			return map.values();
		}

		private Collection<Tag> extractAllMediaNodes ()  {
			Map<String,Tag> map = new HashMap<String,Tag>();
			NodeList tempLists = lists.extractAllNodesThatMatch(MEDIA_TAG_FILTER,true);
			for(int i=0;i<tempLists.size();i++){
				ObjectTag tag = (ObjectTag) tempLists.elementAt(i);
				String src = tag.getAttribute("src");
				if(src!=null&&src.length()>0){
					map.put(src, tag);
				}
			}
			return map.values();
		}

		/**
		 * 计算页面html的大小
		 */
		public long analyzeHTMLSize(){
			return htmlSize;
		}

		/**
		 * 获取样式文件总大小
		 */
		public long analyzeStylesSize(){
			long stylesSize = 0;
			Collection<Tag> styles = this.extractStylesNodes();
			for(Iterator<Tag> it = styles.iterator();it.hasNext();){
				Tag style = it.next();
				String href = style.getAttribute("href");
				if(href!=null&&href.length()>0){
					stylesSize += this.calculateSize(href);
				}
			}
			return stylesSize;
		}


		/**
		 * 获取脚本文件总大小
		 */
		public long analyzeScriptsSize(){
			long scriptsSize = 0;
			Collection<Tag> scripts = this.extractScriptsNodes();
			for(Iterator<Tag> it = scripts.iterator();it.hasNext();){
				Tag style = it.next();
				String src = style.getAttribute("src");
				if(src!=null&&src.length()>0){
					scriptsSize += this.calculateSize(src);
				}
			}
			return scriptsSize;
		}

		/**
		 * 获取图片总大小
		 */
		public long analyzeImagesSize(){
			long imagesSize = 0;
			Collection<Tag> images = this.extractAllImageNodes();
			for(Iterator<Tag> it = images.iterator();it.hasNext();){
				Tag style = it.next();
				String href = style.getAttribute("src");
				if(href!=null&&href.length()>0){
					imagesSize += this.calculateSize(href);
				}
			}
			return imagesSize;
		}

		/**
		 * 获取页面总大小
		 */
		public long analyzePageSize(){
			long pageSize = 0;
			pageSize += this.analyzeHTMLSize();
			pageSize += this.analyzeImagesSize();
			pageSize += this.analyzeScriptsSize();
			pageSize += this.analyzeStylesSize();
			return pageSize;
		}

		private long calculateSize(String url){

			if(url==null){
				return 0;
			}
			HttpClient client = new HttpClient();
			GetMethod method = null;
			long htmlSize = 0;
			try {
				url = this.makeAbsoluteURL(new URL(this.url), url);
				method = prepareGetMethod(url);
				client.executeMethod(method);
				htmlSize = method.getResponseContentLength();
			} catch (IOException e) {
				return 0;
			} finally {
				if(method!=null){
					method.releaseConnection();
				}
			}
			return htmlSize;
		}

		/**
		 *方法说明：相对路径转绝对路径
		 *输入参数：strWeb 网页地址; innerURL 相对路径链接
		 *返回类型：绝对路径链接
		 */
		private String makeAbsoluteURL(URL pageURL, String innerURL) {
			//去除后缀
			int pos = innerURL.indexOf("?");
			if (pos != -1) {
				innerURL = innerURL.substring(0, pos);
			}
			if (innerURL != null&& innerURL.toLowerCase().indexOf("http") == 0) {
				return innerURL;
			}
			URL linkUri = null;
			try {
				linkUri = new URL(pageURL, innerURL);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
			String absURL = linkUri.toString();
			return absURL;
		}

		/**
		 * 处理url的中文问题
		 */
		private GetMethod prepareGetMethod(String url) throws URIException, NullPointerException{
			GetMethod method = new GetMethod();
			method.getParams().setUriCharset(DEFAULT_ENCODING);
			method.getParams().setContentCharset(DEFAULT_ENCODING);
			method.getParams().setCredentialCharset(DEFAULT_ENCODING);
			method.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
			method.setURI(new URI(url,false,DEFAULT_ENCODING));
			return method;
		}
	}


}

