///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef AgreeDesktop_H
#define AgreeDesktop_H

#include "BaseMessage.h"

namespace LyvcMessage {

class AgreeDesktop : public BaseMessage{

	public:
		static const id = 99;
		__int64 conferenceId;
		__int64 senderId;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

