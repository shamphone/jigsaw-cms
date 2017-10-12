/**
 * 
 */
package com.fulong.lyvc.data;

/**
 * ConferenceDocumentData
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class DocumentData {
	private long docId;
	private String docName;
	private String docURL;
	private String fileName;
	private long conferenceId;

	public long getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(long conferenceId) {
		this.conferenceId = conferenceId;
	}

	public long getDocId() {
		return docId;
	}

	public String getDocName() {
		return docName;
	}

	public String getDocURL() {
		return docURL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public void setDocURL(String docURL) {
		this.docURL = docURL;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
