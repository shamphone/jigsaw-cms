///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef RejectDesktop_H
#define RejectDesktop_H

#include "BaseMessage.h"

namespace LyvcMessage {

class RejectDesktop : public BaseMessage{

	public:
		static const id = 105;
		__int64 conferenceId;
		__int64 senderId;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

