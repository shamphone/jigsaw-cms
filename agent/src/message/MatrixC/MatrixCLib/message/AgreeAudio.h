///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef AgreeAudio_H
#define AgreeAudio_H

#include "BaseMessage.h"

namespace LyvcMessage {

class AgreeAudio : public BaseMessage{

	public:
		static const id = 73;
		__int64 conferenceId;
		__int64 senderId;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
