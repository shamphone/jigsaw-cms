package com.fulong.longcon.crawler.queue;

import java.io.File;
import java.io.IOException;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 从一个文件中把所有待转换的地址全读出来，将文件中的地址按照转换规则逐个转换成静态地址。
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class FileListQueue extends AbstractQueue<Object> {
	
	private List<?> pathes;

	public void setFilePath(String filePath) throws IOException {
		this.pathes = FileUtils.readLines(new File(filePath), "UTF-8");
	}

	@SuppressWarnings("unchecked")
	public Iterator iterator() {
		return pathes.iterator();
	}

	public int size() {
		return pathes.size();
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return pathes.hashCode();
	}

	/**
	 * just do nothing
	 * 
	 * @param o
	 *            Object
	 * @return boolean
	 */
	public boolean offer(Object o) {
		return false;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object poll() {
		if (pathes.size() == 0)
			return null;
		Object path = pathes.get(0);
		pathes.remove(0);
		return path;
	}

	public Object peek() {
		return pathes.get(0);
	}

}
