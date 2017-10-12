package com.fulong.lyvc;

/**
 * 
 * ConferenceDoc
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2009-3-11
 */
public interface Document {

	/**
	 * 获取文档id
	 * 
	 * @return
	 */
	public String getDocId();

	/**
	 * 文件路径
	 * 
	 * @return
	 */
	public String getDocURL();

	/**
	 * 文件名称
	 * 
	 * @return
	 */
	public String getFileName();

	/**
	 * 文件路径
	 * 
	 * @param url
	 */
	public void setDocURL(String url);

	/**
	 * 文件名称
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName);
}
