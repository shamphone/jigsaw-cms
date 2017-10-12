package com.fulong.update.wenju;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReplaceWenjuTitle {
	private static final Log log = LogFactory.getLog(ReplaceWenjuTitle.class);
	private static final String TITLE_PATTERN = "<site:title2(\\s)+format(.*)?</site:title2>";
	public static void main(String[] args){
		if(args.length!=1){
			String msg = "the length of args must equals 1.";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		File ecommercePath = new File(args[0]);
		
		process(ecommercePath, "webManage", "\\$T-企业后台");
		process(ecommercePath, "salesWebSite", "\\$T-经销商后台");
		process(ecommercePath, "academician", "\\$T-协会管理员后台");
		process(ecommercePath, "newsManage", "\\$T-编辑后台");
	}
	
	private static void process(File ecommercePath,String moduleName,String title){
		Iterator<File> files = getChannelFiles(new File (ecommercePath,moduleName));
		while(files.hasNext()){
			File file = files.next();
			try {
				String content = FileUtils.readFileToString(file,"UTF-8");
				content = content.replaceAll(TITLE_PATTERN, "<site:title2 format=\""+title+"\"></site:title2>");
				FileUtils.writeStringToFile(file, content,"UTF-8");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		log.info("finish update module:"+moduleName);
	}
	
	@SuppressWarnings("unchecked")
	private static Iterator<File> getChannelFiles(File root){
		Iterator<File> files = FileUtils.iterateFiles(root, new RegexFileFilter("^.+((\\.jsp)|(\\.jspf)|(\\.xml)|(\\.tmp)|(\\.bak)|(\\.old)|(\\.css)|(\\.js))$"),FileFilterUtils.directoryFileFilter());
		return files;
	}
}
