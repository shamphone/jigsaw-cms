package com.fulong.update.portlet;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.util.StringUtils;

import com.fulong.common.FileUtils;

/**
 * java -classpath {coolink jar包路径} 主类 [模板全路径1 模板全路径2 ...]
 * 
 * java -classpath G:\apache-tomcat-5.5.28\common\lib\* com.fulong.update.portlet.PortletUpdater G:\workspace\coolink\studio\enterprise G:\workspace\coolink\studio\group
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-9-28
 * @version 1.0.1
 */
public class PortletUpdater {
	private static final String EXCLUDES[]=new String[]{"META-INF","WEB-INF","repeater","cache","clip"};
	
	public static void main(String[] args){
		args = new String[]{"G:\\zhengcefagui"};
		for(String arg:args){
			File root = new File(arg);
			Visitor visitor = new Visitor(root);
			Iterator<File> files = getFiles(root);
			while(files.hasNext()){
				File file = files.next();
				visitor.visit(file);
			}
		}
	}
	
	/**
	 * 找出所有jsp和jspf栏目文件
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<File> getFiles(File root){
		IOFileFilter directoryFilter = FileFilterUtils.andFileFilter(FileFilterUtils.notFileFilter((new RegexFileFilter("^"+StringUtils.arrayToDelimitedString(EXCLUDES, "|")+"$"))), new RegexFileFilter("[^\\._].+$"));
		Iterator<File> bakFiles = FileUtils.iterateFiles(root, new RegexFileFilter("[^\\._].+((\\.bak)|(\\.old))$"),directoryFilter);
		while(bakFiles.hasNext()){
			bakFiles.next().delete();
		}
		Iterator<File> files = FileUtils.iterateFiles(root, new RegexFileFilter("[^\\._].+((\\.jsp)|(\\.jspf))$"),directoryFilter);
		return files;
	}
}
