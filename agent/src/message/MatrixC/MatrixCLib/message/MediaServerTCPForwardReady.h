///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef MediaServerTCPForwardReady_H
#define MediaServerTCPForwardReady_H

#include "BaseMessage.h"

namespace LyvcMessage {

class MediaServerTCPForwardReady : public BaseMessage{

	public:
		static const id = 153;
		bool isAgreeInvite;
		__int64 fromUserId;
		__int64 toUserId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

