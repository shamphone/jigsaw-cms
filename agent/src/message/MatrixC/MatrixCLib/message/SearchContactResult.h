///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef SearchContactResult_H
#define SearchContactResult_H

#include "BaseMessage.h"

namespace LyvcMessage {

class SearchContactResult : public BaseMessage{

	public:
		static const id = 146;
		__int32 status;
		__int64 contactId;
		string email;
		string firstName;
		string lastName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

