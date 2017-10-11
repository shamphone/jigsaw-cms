///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef Broadcast_H
#define Broadcast_H

#include "BaseMessage.h"

namespace LyvcMessage {

class Broadcast : public BaseMessage{

	public:
		static const id = 68;
		__int64 senderId;
		__int64 conId;
		string content;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

