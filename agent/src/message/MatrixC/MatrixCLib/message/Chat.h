///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef Chat_H
#define Chat_H

#include "BaseMessage.h"

namespace LyvcMessage {

class Chat : public BaseMessage{

	public:
		static const id = 67;
		__int64 senderId;
		__int64 receiverId;
		__int64 conId;
		string content;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

