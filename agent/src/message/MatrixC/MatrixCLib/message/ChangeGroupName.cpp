///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#include "stdafx.h"
#include "ChangeGroupName.h"

string LyvcMessage::ChangeGroupName::toXML()
{
	const int BUFFERSIZE=64;
	char buffer[BUFFERSIZE];
	string xml;
	xml = xml + "<lyvcmessage>";
	xml = xml + "<id>";
	_snprintf(buffer, BUFFERSIZE, "%I32d", id);
	xml = xml + buffer;
	xml = xml + "</id>";
	xml = xml + "<_senderid>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->_senderId);
	xml = xml + buffer;
	xml = xml + "</_senderid>";
	xml = xml + "<_conferenceid>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->_conferenceId);
	xml = xml + buffer;
	xml = xml + "</_conferenceid>";
	xml = xml + "<groupId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->groupId);
	xml = xml + buffer;
	xml = xml + "</groupId>";
	xml = xml + "<newName>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->newName);
	xml = xml + "</newName>";
	xml = xml + "</lyvcmessage>";
	return xml;
}

bool LyvcMessage::ChangeGroupName::fromXML(string& xmlString)
{
	string tagContent;
	
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "_senderid");
	if(tagContent == ""){
		return false;
	}
	this->_senderId = _atoi64(tagContent.c_str());
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "_conferenceid");
	if(tagContent == ""){
		return false;
	}
	this->_conferenceId = _atoi64(tagContent.c_str());
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "groupId");
	if(tagContent == ""){
		return false;
	}
	this->groupId = _atoi64(tagContent.c_str());
	this->newName = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "newName"));
	return true;
}


