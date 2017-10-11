///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#include "stdafx.h"
#include "NotifyMediaServerUserQuitConference.h"

string LyvcMessage::NotifyMediaServerUserQuitConference::toXML()
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
	xml = xml + "<userId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->userId);
	xml = xml + buffer;
	xml = xml + "</userId>";
	xml = xml + "<confId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->confId);
	xml = xml + buffer;
	xml = xml + "</confId>";
	xml = xml + "</lyvcmessage>";
	return xml;
}

bool LyvcMessage::NotifyMediaServerUserQuitConference::fromXML(string& xmlString)
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
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "userId");
	if(tagContent == ""){
		return false;
	}
	this->userId = _atoi64(tagContent.c_str());
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "confId");
	if(tagContent == ""){
		return false;
	}
	this->confId = _atoi64(tagContent.c_str());
	return true;
}


