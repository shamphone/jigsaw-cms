///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#include "stdafx.h"
#include "AgreeAudio.h"

string LyvcMessage::AgreeAudio::toXML()
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
	xml = xml + "<conferenceId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->conferenceId);
	xml = xml + buffer;
	xml = xml + "</conferenceId>";
	xml = xml + "<senderId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->senderId);
	xml = xml + buffer;
	xml = xml + "</senderId>";
	xml = xml + "<receiverId>";
	_snprintf(buffer, BUFFERSIZE, "%I64d", this->receiverId);
	xml = xml + buffer;
	xml = xml + "</receiverId>";
	xml = xml + "</lyvcmessage>";
	return xml;
}

bool LyvcMessage::AgreeAudio::fromXML(string& xmlString)
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
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "conferenceId");
	if(tagContent == ""){
		return false;
	}
	this->conferenceId = _atoi64(tagContent.c_str());
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "senderId");
	if(tagContent == ""){
		return false;
	}
	this->senderId = _atoi64(tagContent.c_str());
	tagContent = BaseMessage::getStringBetweenTag(xmlString, "receiverId");
	if(tagContent == ""){
		return false;
	}
	this->receiverId = _atoi64(tagContent.c_str());
	return true;
}


