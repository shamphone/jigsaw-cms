///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#include "stdafx.h"
#include "RegisterUser.h"

string LyvcMessage::RegisterUser::toXML()
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
	xml = xml + "<userName>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->userName);
	xml = xml + "</userName>";
	xml = xml + "<password>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->password);
	xml = xml + "</password>";
	xml = xml + "<lastName>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->lastName);
	xml = xml + "</lastName>";
	xml = xml + "<firstName>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->firstName);
	xml = xml + "</firstName>";
	xml = xml + "<email>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->email);
	xml = xml + "</email>";
	xml = xml + "<domain>";
	xml = xml + BaseMessage::encodeHtmlEscape(this->domain);
	xml = xml + "</domain>";
	xml = xml + "</lyvcmessage>";
	return xml;
}

bool LyvcMessage::RegisterUser::fromXML(string& xmlString)
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
	this->userName = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "userName"));
	this->password = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "password"));
	this->lastName = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "lastName"));
	this->firstName = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "firstName"));
	this->email = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "email"));
	this->email = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, "domain"));
	return true;
}


