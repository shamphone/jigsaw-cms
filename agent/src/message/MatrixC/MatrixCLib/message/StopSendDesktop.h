///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef StopSendDesktop_H
#define StopSendDesktop_H

#include "BaseMessage.h"

namespace LyvcMessage {

class StopSendDesktop : public BaseMessage{

	public:
		static const id = 110;
		__int64 conferenceId;
		__int64 senderId;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

