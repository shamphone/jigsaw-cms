package com.fulong.site.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.TbodyTag;
import com.fulong.site.TfootTag;
import com.fulong.site.TheadTag;

/**
 * 保持源文件。注意，如果是临时文件，则应该输入临时文件的路径。
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李响
 * @author luobin
 * @version 1.0
 */
public class SaveSourceTableAction extends ChannelBaseAction {
	private static final Pattern PORTLET_PATTERN = Pattern.compile("<fulong:portlet[\\s\\S]*?id=\"(.*?)\"[\\s\\S]*?</fulong:portlet>", Pattern.CASE_INSENSITIVE);
	public static final String JspHeader = "<%@page contentType=\"text/html; charset=utf-8\" %>\n<%@taglib uri=\"/WEB-INF/fulong-portal.tld\" prefix=\"fulong\"%>\n<%@taglib uri=\"/WEB-INF/fulong-site.tld\" prefix=\"site\"%>\n<%@taglib uri=\"/WEB-INF/struts-bean.tld\" prefix=\"bean\"%>\n<%@taglib uri=\"/WEB-INF/struts-logic.tld\" prefix=\"logic\"%>\n<%@taglib uri=\"/WEB-INF/struts-html.tld\" prefix=\"html\"%>\n<%@taglib uri=\"/WEB-INF/struts-tiles.tld\" prefix=\"tiles\"%>\n";

	public ActionForward templateExecute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			Exception {
		InputStream is = request.getInputStream();
		String path = request.getParameter("path");
		if(path.indexOf('?')>0)
			path=path.substring(0,path.indexOf('?'));
		Channel channel=this.parseChannel(path,request);
		String contextPath = channel.getContextPath();;
		SiteTemplate template = channel.getSiteTemplate();
		String pathHead = contextPath.substring(0,contextPath.length()-5)+".head.jspf";
		String pathBody = contextPath.substring(0,contextPath.length()-5)+".body.jspf";
		String pathFoot = contextPath.substring(0,contextPath.length()-5)+".foot.jspf";
		
		File filehead = new File(template.getRootFolder().getFile(),pathHead);
		File filebody = new File(template.getRootFolder().getFile(),pathBody);
		File filefoot = new File(template.getRootFolder().getFile(),pathFoot);
		if (!filehead.exists())
			filehead.createNewFile();
		if (!filebody.exists())
			filebody.createNewFile();
		if (!filefoot.exists())
			filefoot.createNewFile();
		Channel headChannel=template.getChannel(pathHead);
		Channel bodyChannel=template.getChannel(pathBody);
		Channel footChannel=template.getChannel(pathFoot);
		
		String htmlstr = "<html><head><title></title></head><body>"+IOUtils.toString(is,"UTF-8")+"</body></html>";
		Parser parser = Parser.createParser(htmlstr, DEFAULT_ENCODING);
		//注册新的结点解析器
		PrototypicalNodeFactory factory = new PrototypicalNodeFactory ();
		factory.registerTag( new TheadTag ());
		parser.setNodeFactory(factory);
		factory.registerTag( new TbodyTag ());
		parser.setNodeFactory(factory);
		factory.registerTag( new TfootTag ());
		parser.setNodeFactory(factory);
		
		NodeFilter filter = new NodeClassFilter(TableTag.class);
		NodeList nodes = parser.parse(filter);
		//判断是否有table标签
		if (nodes.size() != 0) {
			TableTag tableTag = (TableTag)(nodes.elementAt(0));
			writeChannelContent(channel, tableTag.toHtml());
			
			process(tableTag, channel, headChannel, TheadTag.class);
			process(tableTag, channel, bodyChannel, TbodyTag.class);
			process(tableTag, channel, footChannel, TfootTag.class);
		}
		//将文件清空
		else {
			FileUtils.write(channel.getPage(),"");
			clearClip(channel);
		}
		is.close();
		return null;
	}
	
	/**
	 * 从表格重复器的整体片段复制片段到对应的部分
	 * @param source						
	 * @param partContent
	 * @throws IOException 
	 */
	private void copyClip(Channel source,Channel dest,String partContent) throws IOException{
		Matcher matcher = PORTLET_PATTERN.matcher(partContent);
		while(matcher.find()){
			String portletId = matcher.group(1);
			copyClip(source, portletId, dest, portletId);
		}
	}
	
	private void writeChannelContent (Channel channel,String content) throws IOException{
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(channel.getPage());
			IOUtils.copy(new StringReader(JspHeader), out, DEFAULT_ENCODING);
			IOUtils.copy(new StringReader(content), out, DEFAULT_ENCODING);
		}finally{
			IOUtils.closeQuietly(out);
		}
	}
	
	private void process(TableTag tableTag,Channel channel,Channel partChannel,Class<? extends CompositeTag> partClass) throws IOException{
		NodeList nodes = tableTag.searchFor(partClass, false);
		if (nodes.size() != 0) {
			String content = null;
			if(partClass==TbodyTag.class){
				content = nodes.elementAt(0).getChildren().toHtml();
			}else {
				content = nodes.elementAt(0).toHtml().trim();
			}
			writeChannelContent(partChannel, content);
			copyClip(channel, partChannel, content);
		//将文件清空
		} else {
			FileUtils.write(partChannel.getPage(),"");
			clearClip(partChannel);
		}
	}
	
}
