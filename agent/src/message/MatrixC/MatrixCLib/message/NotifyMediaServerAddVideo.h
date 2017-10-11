///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef NotifyMediaServerAddVideo_H
#define NotifyMediaServerAddVideo_H

#include "BaseMessage.h"

namespace LyvcMessage {

class NotifyMediaServerAddVideo : public BaseMessage{

	public:
		static const id = 44;
		__int64 fromUserId;
		__int64 toUserId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

