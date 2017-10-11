///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef Leaveword_H
#define Leaveword_H

#include "BaseMessage.h"

namespace LyvcMessage {

class Leaveword : public BaseMessage{

	public:
		static const id = 144;
		__time64_t sendDate;
		__int64 receiverId;
		string content;
		string senderName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

