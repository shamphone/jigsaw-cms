/**
 * 
 */
package com.fulong.longcon.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.fulong.common.FileUtils;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class ChannelPageParser {
	private Parser parser;
	private NodeList nodes;
//	private String html;
	private File jspFile;
//	private String encoding;
	protected static final Pattern PATTERN_ENCODING = Pattern.compile("[^\"|']+;\\s*charset=([^\"|']+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
	private static final String DEFAULT_ENCODING = "UTF-8";

	public static ChannelPageParser createParser(File file) throws ParserException, IOException {
		return new ChannelPageParser(file, DEFAULT_ENCODING);
	}

	private ChannelPageParser(File file, String charset) throws ParserException, IOException {
		this.jspFile=file;
//		this.encoding=charset;
		String html = FileUtils.readFileToString(file, charset);
		parser = Parser.createParser(html, charset);
		this.nodes = parser.parse(new NodeFilter() {
			private static final long serialVersionUID = 8081693127898613904L;

			public boolean accept(Node node) {
				return true;
			};
		});
	}
	
	public String getTitle(){
		NodeList nodes=this.nodes.extractAllNodesThatMatch(new NodeFilter(){
			
			private static final long serialVersionUID = 2623286778305758005L;

			public boolean accept(Node node) {
				return (node instanceof TitleTag);
			}}, true);
		for(int i=0;i<nodes.size();i++){
			String title= ((TitleTag)nodes.elementAt(i)).getTitle();
			if((title!=null)&&(title.length()>0))
				return title;
		}
		return null;
		
	}
	
	/**
	 * 	替换<title>标签中的文本
	 */
	public void replaceTitleContent(String title){
		NodeList nodes=this.nodes.extractAllNodesThatMatch(new NodeFilter(){
			
			private static final long serialVersionUID = 2623286778305758005L;
			
			public boolean accept(Node node) {
				return (node instanceof TitleTag);
			}}, true);
		TextNode textNode = new TextNode(title);
		for(int i=0;i<nodes.size();i++){
			nodes.elementAt(i).getChildren().removeAll();
			nodes.elementAt(i).getChildren().add(textNode);
		}
		
	}

	
	/**
	 * 获取html代码的编码
	 * @param html
	 * @return
	 * @throws ParserException 
	 */
	private String parserEncoding(String html) throws ParserException{
		Parser parser = Parser.createParser(html, null);
		// 检查编码
		NodeList nodes = parser.parse(null);
		NodeList metas = nodes.extractAllNodesThatMatch(new NodeClassFilter(MetaTag.class), true);
		for (int i = 0; i < metas.size(); i++) {
			MetaTag meta = (MetaTag) metas.elementAt(i);
			if ("content-type".equalsIgnoreCase(meta.getHttpEquiv())) {
				String mc = meta.getMetaContent();
				Matcher matcher = PATTERN_ENCODING.matcher(mc);
				if (matcher.find()) {
					return matcher.group(1).trim();
			}
			}
		}
		return DEFAULT_ENCODING;
		
	}

	public void addScript(String path) {
		NodeList heads = nodes.extractAllNodesThatMatch(new NodeClassFilter(HeadTag.class), true);
		HeadTag head = (HeadTag) heads.elementAt(0);
		SimpleNodeIterator iter = head.children();
		while (iter.hasMoreNodes()) {
			Node node = iter.nextNode();
			if (node instanceof Tag) {
				Tag tag = (Tag) node;
				if (tag.getTagName().equalsIgnoreCase("script")) {
					String src = tag.getAttribute("src");
					if (src != null && src.equalsIgnoreCase(path))
						return;
				}
			}
		}
		
		Tag endTag = new TagNode();
		endTag.setTagName("/script");
		ScriptTag script = new ScriptTag();
		script.setType("text/javascript");
		script.setLanguage("javascript");
		script.setAttribute("src", path, '"');
		script.setEndTag(endTag);
		head.getChildren().add(script);
	}

	public void addCSS(String css) {
		NodeList heads = nodes.extractAllNodesThatMatch(new NodeClassFilter(HeadTag.class), true);
		HeadTag head = (HeadTag) heads.elementAt(0);
			
		SimpleNodeIterator iter =  head.children();
		while (iter.hasMoreNodes()) {
			Node node = iter.nextNode();
			if (node instanceof Tag) {
				Tag tag = (Tag) node;
				if (tag.getTagName().equalsIgnoreCase("link")) {
					String href = tag.getAttribute("href");
					if (href != null && href.equalsIgnoreCase(css))
						return;
				}
			}
		}
		Tag style = new TagNode();
		style.setTagName("link");
		style.setAttribute("rel", "stylesheet", '"');
		style.setAttribute("type", "text/css", '"');
		style.setAttribute("href", css, '"');
		style.setEmptyXmlTag(true);
		head.getChildren().add(style);
	}

	/**
	 * 修改html内容，仅保持jsp标签不变
	 * 
	 * @param html
	 * @throws ParserException
	 * @throws IOException
	 */
	public void importHtml(String html) throws ParserException, IOException {		
		this.importHtml(html, this.parserEncoding(html));
	}

	public void importHtml(InputStream htmlFile) throws ParserException, IOException {
		byte[] bytes = IOUtils.toByteArray(htmlFile);		
		String encoding= this.parserEncoding(new String(bytes));
		this.importHtml(new String(bytes,encoding),encoding);
	}

	public void importHtml(InputStream htmlFile, String encoding) throws IOException, ParserException {
		this.importHtml(IOUtils.toString(htmlFile, encoding), encoding);

	}

	/**
	 * 修改html内容，仅保持jsp标签不变
	 * 
	 * @param html
	 * @throws IOException
	 * @throws ParserException
	 */
	public void importHtml(File htmlFile, String encoding) throws IOException, ParserException {
		String html = FileUtils.readFileToString(htmlFile, encoding);
		this.importHtml(html, encoding);
	}

	public void importHtml(String html, String encoding) throws IOException, ParserException {
		Parser parser = Parser.createParser(html, encoding);
		NodeList htmlNodes = this.nodes.extractAllNodesThatMatch(new HtmlFilter());
		for (int i = 0; i < htmlNodes.size(); i++)
			this.nodes.remove(htmlNodes.elementAt(i));
		this.nodes.add(parser.parse(null));
	}

	public String toHtml() throws IOException {
		return this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 8081693127898613904L;

			public boolean accept(Node node) {
				return node.getParent() == null;
			};
		}).toHtml();
	}

	public void save() throws IOException {
		FileUtils.write(this.jspFile, toHtml(), "UTF-8");
	}

}

class HtmlFilter implements NodeFilter {

	private static final long serialVersionUID = 1539447150161245641L;

	public boolean accept(Node node) {
		return node instanceof Html;
	}

}