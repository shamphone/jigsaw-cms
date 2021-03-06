///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#include "stdafx.h"
#include "NotifyMediaServerRemoveAudio.h"

string LyvcMessage::NotifyMediaServerRemoveAudio::toXML()
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
	xml = xml + "<fromUserId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->fromUserId);
	xml = xml + buffer;
	xml = xml + "</fromUserId>";
	xml = xml + "<toUserId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->toUserId);
	xml = xml + buffer;
	xml = xml + "</toUserId>";
	xml = xml + "</lyvcmessage>";
	return xml;
}

bool LyvcMessage::NotifyMediaServerRemoveAudio::fromXML(string& xmlString)
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
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "fromUserId");
	if(tagContent == ""){
		return false;
	}
	this->fromUserId = _atoi64(tagContent.c_str());
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "toUserId");
	if(tagContent == ""){
		return false;
	}
	this->toUserId = _atoi64(tagContent.c_str());
	return true;
}


