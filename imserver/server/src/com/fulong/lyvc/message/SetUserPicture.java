///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

package com.fulong.lyvc.message;

/**
 * SetUserPicture
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-10
 */

public class SetUserPicture extends BaseMessage{

	public static final int id = 184;

	public int fileLength;
	public String receiverId;
	public String extendFileName;
	public String fileData;

	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<lyvcmessage>");
		xml.append("<id>" + id + "</id>");
		xml.append("<_senderid>" + this.getSenderId() + "</_senderid>");
		xml.append("<_conferenceid>" + this.getConferenceId() + "</_conferenceid>");
		xml.append("<fileLength>" + this.fileLength + "</fileLength>");
		xml.append("<receiverId>" + this.receiverId + "</receiverId>");
		xml.append("<extendFileName>");
		if(this.extendFileName != null) {
			xml.append(encodeHtmlEscape(this.extendFileName));
		}
		xml.append("</extendFileName>");
		xml.append("<fileData>");
		if(this.fileData != null) {
			xml.append(encodeHtmlEscape(this.fileData));
		}
		xml.append("</fileData>");
		xml.append("</lyvcmessage>");
		
		return xml.toString();
	}

	public void fromXML(String xmlString) throws MessageFormatException {
		String tagContent;
		
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "_senderid");
		this.setSenderId(tagContent);
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "_conferenceid");
		this.setConferenceId(tagContent);
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "fileLength");
		this.fileLength = Integer.parseInt(tagContent);
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "receiverId");
		this.receiverId = tagContent;
		this.extendFileName = BaseMessage.decodeHtmlEscape(BaseMessage.getStringBetweenTag(xmlString, "extendFileName"));
		this.fileData = BaseMessage.decodeHtmlEscape(BaseMessage.getStringBetweenTag(xmlString, "fileData"));
	}

}