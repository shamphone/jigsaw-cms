///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef NotifyMediaServerRemoveAudio_H
#define NotifyMediaServerRemoveAudio_H

#include "BaseMessage.h"

namespace LyvcMessage {

class NotifyMediaServerRemoveAudio : public BaseMessage{

	public:
		static const id = 43;
		__int64 fromUserId;
		__int64 toUserId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

