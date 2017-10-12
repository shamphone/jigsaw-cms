package com.fulong.update.portlet;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
 * @date 2010-10-9
 * @version 1.0.1
 */
public class Visitor {
	private static final Log log = LogFactory.getLog(Visitor.class);
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Pattern PORTLET_PATTERN = Pattern.compile("<fulong:portlet[\\s\\S]*?</fulong:portlet>", Pattern.CASE_INSENSITIVE);
	private final File root;
	
	public Visitor(File root){
		this.root = root;
	}
	
	public void visit(File file){
		try {
			String content = FileUtils.readFileToString(file,DEFAULT_ENCODING);
			StringBuffer buffer = new StringBuffer();
			Matcher matcher = PORTLET_PATTERN.matcher(content);
			while(matcher.find()){
				try {
					String xml = matcher.group();
					PortletElement portlet = PortletElementFactory.getPortletElement(xml, file);
					String newXml = portlet.accept(this);
					//转义\
					newXml = newXml.replace("\\", "\\\\");
					//转义$
					newXml = newXml.replace("$", "\\$");
					matcher.appendReplacement(buffer, newXml);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			matcher.appendTail(buffer);
			FileUtils.write(file, buffer.toString(), DEFAULT_ENCODING);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public File getRoot(){
		return root;
	}
	
}
