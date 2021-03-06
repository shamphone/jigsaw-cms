///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef RejectVideo_H
#define RejectVideo_H

#include "BaseMessage.h"

namespace LyvcMessage {

class RejectVideo : public BaseMessage{

	public:
		static const id = 79;
		__int64 conferenceId;
		__int64 senderId;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

