///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef AddContact_H
#define AddContact_H

#include "BaseMessage.h"

namespace LyvcMessage {

class AddContact : public BaseMessage{

	public:
		static const id = 121;
		bool isCommon;
		__int32 status;
		__int64 groupId;
		__int64 contactId;
		string name;
		string email;
		string firstName;
		string lastName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

