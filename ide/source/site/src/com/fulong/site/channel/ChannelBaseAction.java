package com.fulong.site.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.JspTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.ChannelType;
import com.fulong.longcon.site.SiteException;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.site.template.TemplateBaseAction;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public abstract class ChannelBaseAction extends TemplateBaseAction {
	 public static final String FORMAT = "%1$03d";
	/**
	 * 设置栏目channel的父文件夹为siteFolder
	 * 支持跨模板
	 * @param channel
	 * @param siteFolder
	 * @return
	 * @throws IOException
	 */
	protected boolean moveTo(Channel channel,SiteFolder siteFolder) throws IOException{
		File page = channel.getPage();
		File folder = siteFolder.getFile();
		FileUtils.copyFileToDirectory(page, folder, true);
		
		File clipFolder = getFolder(channel);
		if(clipFolder.exists()){
			FileUtils.copyDirectoryToDirectory(clipFolder, folder);
			FileUtils.deleteDirectory(clipFolder);
		}
		
		page.delete();
		
		File workingFile = new File(page.getPath()+".bak");
		if(workingFile.exists()){
			FileUtils.copyFileToDirectory(workingFile, folder, true);
			workingFile.delete();
		}
		
		File previewFile = new File(page.getPath()+".bak.bak");
		if(previewFile.exists()){
			previewFile.delete();
		}
		return true;
	}
	
	/**
	 * 设置栏目channel的父文件夹为siteFolder
	 * 支持跨模板
	 * @param channel
	 * @param siteFolder
	 * @return
	 * @throws IOException 
	 * @throws IOException
	 */
	protected boolean delete(Channel channel) throws IOException {
		clearClip(channel);
		File page = channel.getPage();
		page.delete();
		
		File workingFile = new File(page.getPath()+".bak");
		if(workingFile.exists()){
			workingFile.delete();
		}
		
		File previewFile = new File(page.getPath()+".bak.bak");
		if(previewFile.exists()){
			previewFile.delete();
		}
		
		return true;
	}
	
	/**
	 * 复制栏目，同时复制栏目用到的片段
	 * @param channel
	 * @param siteFolder
	 * @return
	 * @throws IOException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	protected boolean copyTo(Channel channel,SiteFolder siteFolder,String name,String displayName,HttpServletRequest request){
		
		File dest = new File(siteFolder.getFile(),name +"."+this.getSiteFactory(request).getChannelType(channel.getType()).getPostfix());
		if(dest.exists()){
			return false;
		}
		
		File source = channel.getPage();
		String content = null;
		try {
			content = IOUtils.toString(new FileInputStream(source),DEFAULT_ENCODING);
		} catch (FileNotFoundException e) {
			String msg = "Can't find channel for path : '"+source.getPath()+"'.";
			log.error(msg, e);
			throw new SiteException( msg, e );
		} catch (IOException e) {
			String msg = "IOException in read Channel page for path :'"+source.getPath()+"'.\n"+e.getMessage() ;
			log.error(msg,e);
			throw new SiteException( msg, e );
		}
		
		//修改显示名称
		content = content.replaceFirst("(<%@page(.*?)info=\")(.*?)(\"%>)", "$1"+displayName+"$4");
		
		try {
			FileUtils.write(dest, content);
		} catch (IOException e) {
			String msg = "IOException in write Channel page for path : '"+dest.getPath()+"'.\n"+e.getMessage();
			log.error(msg, e);
			throw new SiteException( msg, e );
		}
		
		File srcClipFolder = getFolder(channel);
		if(srcClipFolder.exists()){
			try {
				FileUtils.copyDirectory(srcClipFolder, getFolder(dest));
			} catch (IOException e) {
				String msg = "Copy clip folder of channel:"+channel.getContextPath()+" in error.\n"+e.getMessage();
				log.error(msg,e);
				throw new SiteException(msg , e);
			}
		}
		return true;
	}
	
    /**
     * 生成栏目的默认名称
     * @param siteFolder
     * @return
     */
    @SuppressWarnings("unchecked")
	protected String generateID(SiteFolder siteFolder,String channelType,HttpServletRequest request){
    	ChannelType type = this.getSiteFactory(request).getChannelType(channelType);
    	int tempNo = 1;
    	String no = String.format(FORMAT, tempNo);
    	Iterator<String> it = siteFolder.getChannelNames();
    	List<String> list =IteratorUtils.toList(it);
    	while (list.contains(type.getPrefix() + no+"."+type.getPostfix())) {
    		no = String.format(FORMAT, ++tempNo);
    	}
    	return no;
    }
    
    protected String getContentType(String channelType) {
		if("index".equals(channelType)||"clip".equals(channelType)){
			return "text/html; charset=utf-8";
		}else if("word".equals(channelType)){
			return "Application/msword; charset=utf-8";
		}else if("excel".equals(channelType)){
			return "application/vnd.ms-excel; charset=utf-8";
		}else if("pdf".equals(channelType)){
			return "Application/pdf; charset=utf-8";
		}
		return null;
	}
    
	/**
	 * 将流转换为utf-8的流
	 */
	protected static class JSPUTF8InputStream{
		protected static final Pattern PATTERN_ENCODING = Pattern.compile("(;\\s*charset=)([^\"|']+)([\"'])", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		protected static final Pattern PATTERN_CONTENTTYPE = Pattern.compile("(contentType\\s*=\\s*[\"'])([^\"|']+)([\"'])", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		
		private InputStream in ;
		private String contentType;
		private byte[] bs;
		private String encoding ;
		
		public JSPUTF8InputStream(InputStream in,String contentType) throws IOException{
			bs = IOUtils.toByteArray(in);
			this.contentType = contentType;
		}
		
		public String fixJsp(String data,String encoding){
			if(!encoding.equalsIgnoreCase("UTF-8")){
				data = PATTERN_ENCODING.matcher(data).replaceAll("$1UTF-8$3");
			}
			data = PATTERN_CONTENTTYPE.matcher(data).replaceAll("$1"+this.contentType+"$3");
			return data;
		}
		
		/**
		 * 从html源码或jsp源码中解析出编码
		 * @param data
		 * @param encoding
		 * @return
		 * @throws ParserException
		 */
		private String parseEncoding(String data, String encoding) throws ParserException {
			Parser parser = Parser.createParser(data, encoding);
			NodeList nodes = parser.parse(new NodeFilter() {
				public boolean accept(Node node) {
					return true;
				};
			});
			
			String charset = parseEncodingFromJSP(nodes);
			if(charset!=null){
				return charset;
			}
			return parseEncodingFromHTML(nodes);
		}
		
		private String parseEncodingFromJSP(NodeList nodes){
			final String[] encodings = new String[1];
			NodeFilter jspInstructionFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node instanceof JspTag) {
						JspTag tag = (JspTag) node;
						String text = tag.getText();
						if(text.indexOf("%@page")>=0&&text.indexOf("contentType")>=0){
							int start = text.indexOf("charset=",text.indexOf("contentType"));
							int end = text.indexOf("\"",start);
							encodings[0] = text.substring(start+8,end).trim();
							return true;
						}
					}
					return false;
				};
			};
//			优先取jsp指令的编码
			nodes.extractAllNodesThatMatch(jspInstructionFilter);
			if(encodings[0]!=null){
				return encodings[0];
			}
			return null;
		}
		
		private String parseEncodingFromHTML(NodeList nodes){
			// try to parser header from body;
			NodeFilter metaFilter = new NodeFilter() {
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
		
		public InputStream getInputStream() throws IOException, ParserException{
			if(in==null){
				convert();
			}
			return in;
		}
		
		private void convert() throws IOException, ParserException{
			String encoding = "UTF-8";
			String data = new String(bs,encoding);
			
			encoding = parseEncoding(data, encoding);
			if(encoding==null){
				encoding = "UTF-8";
			}
			data = new String(bs,encoding);
			
			data = fixJsp(data, encoding);
			
			this.in = IOUtils.toInputStream(data, "UTF-8");
			this.encoding = encoding;
		}
	}
}
