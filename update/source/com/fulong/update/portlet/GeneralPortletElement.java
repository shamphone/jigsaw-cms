package com.fulong.update.portlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.xml.sax.SAXException;

import com.fulong.common.DomUtils;
import com.fulong.common.FileUtils;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-10-8	
 * @version 1.0.1
 */
public class GeneralPortletElement extends DefaultPortletElement implements PortletElement{
	private static final String JSP_HEADER = "<%@page contentType=\"text/html; charset=utf-8\" %><%@taglib uri=\"/WEB-INF/fulong-portal.tld\" prefix=\"fulong\"%><%@taglib uri=\"/WEB-INF/fulong-site.tld\" prefix=\"site\"%><%@taglib uri=\"/WEB-INF/struts-bean.tld\" prefix=\"bean\"%><%@taglib uri=\"/WEB-INF/struts-logic.tld\" prefix=\"logic\"%><%@taglib uri=\"/WEB-INF/struts-html.tld\" prefix=\"html\"%><%@taglib uri=\"/WEB-INF/struts-tiles.tld\" prefix=\"tiles\"%>";
	
	private static final Map<String,PortletInfo[]> PORTLETS_INFO = new HashMap<String, PortletInfo[]>();
	static {
		PORTLETS_INFO.put("content-xrepeater", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("content-listXrepeater", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("content-tableXrepeater", new PortletInfo[]{new PortletInfo("","clip-path",null),new PortletInfo("head",null,"clip-head"),new PortletInfo("body","null","clip-body"),new PortletInfo("foot",null,"clip-foot")});
		PORTLETS_INFO.put("nodeDefinition-xrepeater", new PortletInfo[]{new PortletInfo("child","clip-path","clip-content"),new PortletInfo("parent","clip-path","clip-content2")});
		PORTLETS_INFO.put("weekCalendar", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("monthCalendar", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("yearCalendar", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("mixNodeDefinition-list", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("conditions", new PortletInfo[]{new PortletInfo("true","underTrueValue","clip-true"),new PortletInfo("false","underFalseValue","clip-false")});
		PORTLETS_INFO.put("parent", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("child", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
		PORTLETS_INFO.put("reference", new PortletInfo[]{PortletInfo.DEFAULT_PORTLET_INFO});
	}
	
	
	public GeneralPortletElement(String xml,File file) throws SAXException, IOException{
		super(xml, file);
	}
	
	private PortletInfo[] getClipInfos(){
		return PORTLETS_INFO.get(getType());
	}
	
	public String accept(Visitor visitor) throws Exception{
		PortletInfo[] clipInfos = getClipInfos();
		if(clipInfos==null||clipInfos.length==0){
			return this.toXML();
		}
		if("content-tableXrepeater".equalsIgnoreCase(type)){
			return processTableXReapter(visitor);
		}
		for(int i=0;i<clipInfos.length;i++){
			String content;
			String[] clipContent = preferenceSet.getValues(clipInfos[i].getNewPath());
			if(clipContent!=null&&clipContent.length>0){
				content = DomUtils.antiFilter(clipContent[0]);
				//清空冗余
				preferenceSet.put(clipInfos[i].getNewPath(), (String)null);
			}else{
				String clipPath = clipInfos[i].getOrignalPath();
				String[] paths = preferenceSet.getValues(clipPath);
				if(paths!=null&&paths.length>0){
					try {
						content = FileUtils.readFileToString(new File(visitor.getRoot(),paths[0]), "UTF-8");
					} catch (IOException e) {
						log.error("IOException while reading File:"+paths[0]+".Current page is "+this.file.getPath()+".The id of current portlet is " + this.id,e);
						throw e;
					}
					//清空页面冗余
					preferenceSet.put(clipInfos[i].getOrignalPath(), (String)null);
				}else{
					log.warn("Can't find clip in portlet for id:"+id +".Current page is "+file.getPath());
					return this.toXML();
				}
			}
			String prefix = clipInfos[i].getPrefix();
			File file = this.getClipFile(prefix);
			com.fulong.common.FileUtils.write(file, content, "UTF-8");
			visitor.visit(file);
		}
		return this.toXML();
	}
	
	private File getClipFile(String prefix){
		String name = id;
		
		if(prefix!=null&&prefix.length()!=0){
			name += ("."+prefix);
		}
		name += ".jspf";
		File file = new File(getClipFolder(),name);
		return file;
	}
	
	private String processTableXReapter(Visitor visitor) throws IOException{
		String[] paths = preferenceSet.getValues("clip-path");
		if(paths!=null&&paths.length>0){
			return processOldTableXReapter(visitor);
		}else{
			return processNewTableXReapter(visitor);
		}
	}
	
	private String processOldTableXReapter(Visitor visitor) throws IOException{
		String[] path = preferenceSet.getValues("clip-path");
		String content = FileUtils.readFileToString(new File(visitor.getRoot(),path[0]), "UTF-8");
		File file = this.getClipFile("");
		FileUtils.write(file, content, "UTF-8");
		visitor.visit(file);
		
		String headPath = path[0].substring(0,path[0].length()-5)+".head.jspf";
		String headContent = FileUtils.readFileToString(new File(visitor.getRoot(),headPath), "UTF-8");
		File headFile = this.getClipFile("head");
		FileUtils.write(headFile, headContent, "UTF-8");
		visitor.visit(headFile);
		
		String bodyPath = path[0].substring(0,path[0].length()-5)+".body.jspf";
		String bodyContent = FileUtils.readFileToString(new File(visitor.getRoot(),bodyPath), "UTF-8");
		File bodyFile = this.getClipFile("body");
		FileUtils.write(bodyFile, bodyContent, "UTF-8");
		visitor.visit(bodyFile);
		
		String footPath = path[0].substring(0,path[0].length()-5)+".foot.jspf";
		String footContent = FileUtils.readFileToString(new File(visitor.getRoot(),footPath), "UTF-8");
		File footFile = this.getClipFile("foot");
		FileUtils.write(footFile, footContent, "UTF-8");
		visitor.visit(footFile);
		//清空冗余
		preferenceSet.put("clip-path", (String)null);
		return this.toXML();
	}
	
	
	private String processNewTableXReapter(Visitor visitor) throws IOException{
		String content = JSP_HEADER+"<table>";
		
		String[] clipHead = preferenceSet.getValues("clip-head");
		if(clipHead!=null&&clipHead.length!=0){
			String headContent = "<thead>"+normalize(DomUtils.antiFilter(clipHead[0]))+"</thead>";
			content += headContent;
			headContent = JSP_HEADER + headContent;
			File file = this.getClipFile("head");
			FileUtils.write(file, headContent, "UTF-8");
			visitor.visit(file);
		}
		
		String[] clipBody = preferenceSet.getValues("clip-body");
		if(clipBody!=null&&clipBody.length!=0){
			String bodyContent = DomUtils.antiFilter(clipBody[0]);
			content += ("<tbody>"+normalize(bodyContent)+"</tbody>");
			File file = this.getClipFile("body");
			FileUtils.write(file, bodyContent, "UTF-8");
			visitor.visit(file);
		}
		
		String[] clipFoot = preferenceSet.getValues("clip-foot");
		if(clipFoot!=null&&clipFoot.length!=0){
			String footContent = "<tfoot>"+normalize(DomUtils.antiFilter(clipFoot[0]))+"</tfoot>";
			content += footContent;
			footContent = JSP_HEADER + footContent;
			File file = this.getClipFile("foot");
			FileUtils.write(file, footContent, "UTF-8");
			visitor.visit(file);
		}
		content += "</table>";
		File file = this.getClipFile("");
		FileUtils.write(file, content, "UTF-8");
		visitor.visit(file);
		//清空冗余
		preferenceSet.put("clip-head", (String)null);
		preferenceSet.put("clip-body", (String)null);
		preferenceSet.put("clip-foot", (String)null);
		return this.toXML();
	}
	
	public File getClipFolder(){
		File folder = new File(file.getParentFile(),"_"+file.getName());
		if(!folder.exists()){
			folder.mkdirs();
		}
		return folder;
	}
	
	public String toXML(){
		return "<fulong:portlet id=\""+id+"\" type=\""+type+"\">"+preferenceSet.toXML()+"</fulong:portlet>";
	}
	
	public String normalize(String html){
		html = Pattern.compile("<%@page[\\s\\S]*?%>", Pattern.CASE_INSENSITIVE).matcher(html).replaceAll("");
		html = Pattern.compile("<%@taglib[\\s\\S]*?%>", Pattern.CASE_INSENSITIVE).matcher(html).replaceAll("");
		return html;
	}
	
}
