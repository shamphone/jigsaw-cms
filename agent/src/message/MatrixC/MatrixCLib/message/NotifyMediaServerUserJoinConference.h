///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef NotifyMediaServerUserJoinConference_H
#define NotifyMediaServerUserJoinConference_H

#include "BaseMessage.h"

namespace LyvcMessage {

class NotifyMediaServerUserJoinConference : public BaseMessage{

	public:
		static const id = 196;
		__int64 userId;
		__int64 confId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

