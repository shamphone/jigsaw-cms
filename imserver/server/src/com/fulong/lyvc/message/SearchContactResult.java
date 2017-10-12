///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

package com.fulong.lyvc.message;

/**
 * SearchContactResult
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-10
 */

public class SearchContactResult extends BaseMessage{

	public static final int id = 146;

	public int status;
	public String contactId;
	public String email;
	public String firstName;
	public String lastName;

	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<lyvcmessage>");
		xml.append("<id>" + id + "</id>");
		xml.append("<_senderid>" + this.getSenderId() + "</_senderid>");
		xml.append("<_conferenceid>" + this.getConferenceId() + "</_conferenceid>");
		xml.append("<status>" + this.status + "</status>");
		xml.append("<contactId>" + this.contactId + "</contactId>");
		xml.append("<email>");
		if(this.email != null) {
			xml.append(encodeHtmlEscape(this.email));
		}
		xml.append("</email>");
		xml.append("<firstName>");
		if(this.firstName != null) {
			xml.append(encodeHtmlEscape(this.firstName));
		}
		xml.append("</firstName>");
		xml.append("<lastName>");
		if(this.lastName != null) {
			xml.append(encodeHtmlEscape(this.lastName));
		}
		xml.append("</lastName>");
		xml.append("</lyvcmessage>");
		
		return xml.toString();
	}

	public void fromXML(String xmlString) throws MessageFormatException {
		String tagContent;
		
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "_senderid");
		this.setSenderId(tagContent);
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "_conferenceid");
		this.setConferenceId(tagContent);
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "status");
		this.status = Integer.parseInt(tagContent);
		tagContent = BaseMessage.getStringBetweenTag(xmlString, "contactId");
		this.contactId = tagContent;
		this.email = BaseMessage.decodeHtmlEscape(BaseMessage.getStringBetweenTag(xmlString, "email"));
		this.firstName = BaseMessage.decodeHtmlEscape(BaseMessage.getStringBetweenTag(xmlString, "firstName"));
		this.lastName = BaseMessage.decodeHtmlEscape(BaseMessage.getStringBetweenTag(xmlString, "lastName"));
	}

}