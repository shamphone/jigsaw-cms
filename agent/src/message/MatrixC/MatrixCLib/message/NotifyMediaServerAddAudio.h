///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef NotifyMediaServerAddAudio_H
#define NotifyMediaServerAddAudio_H

#include "BaseMessage.h"

namespace LyvcMessage {

class NotifyMediaServerAddAudio : public BaseMessage{

	public:
		static const id = 42;
		__int64 fromUserId;
		__int64 toUserId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

