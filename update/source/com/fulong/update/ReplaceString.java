package com.fulong.update;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * arg[0]	模板全路径
 * arg[1]	被替换的字符串
 * arg[2]	替换成的字符串
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
 * @date 2010-10-18	
 * @version 1.0.1
 */
public class ReplaceString {
	private static final Log log = LogFactory.getLog(ReplaceString.class);
	public static void main(String[] args){
		if(args.length!=3){
			String msg = "the length of args must equals 3.";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		File root = new File(args[0]);
		Iterator<File> files = getChannelFiles(root);
		while(files.hasNext()){
			File file = files.next();
			try {
				String content = FileUtils.readFileToString(file,"UTF-8");
				content = content.replace(args[1], args[2]);
				FileUtils.writeStringToFile(file, content,"UTF-8");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		log.info("finish update template:"+root.getName());
	}
	
	@SuppressWarnings("unchecked")
	private static Iterator<File> getChannelFiles(File root){
		Iterator<File> files = FileUtils.iterateFiles(root, new RegexFileFilter("^.+((\\.jsp)|(\\.jspf)|(\\.xml)|(\\.tmp)|(\\.bak)|(\\.old)|(\\.css)|(\\.js))$"),FileFilterUtils.directoryFileFilter());
		return files;
	}
}
