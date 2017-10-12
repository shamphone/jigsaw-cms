package com.fulong.update;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
 * @date 2010-9-29	
 * @version 1.0.1
 */
public class RenameTemplate {
	private static final Log log = LogFactory.getLog(RenameTemplate.class);
	public static void main(String[] args){
		if(args.length!=2){
			String msg = "the length of args must equals 2.";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		File root = new File(args[0]);
		Iterator<File> files = getChannelFiles(root);
		while(files.hasNext()){
			File file = files.next();
			try {
				String content = FileUtils.readFileToString(file,"UTF-8");
				content = content.replace(root.getName(), args[1]);
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
