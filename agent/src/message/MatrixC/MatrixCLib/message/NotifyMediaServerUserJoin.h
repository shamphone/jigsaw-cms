///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef NotifyMediaServerUserJoin_H
#define NotifyMediaServerUserJoin_H

#include "BaseMessage.h"

namespace LyvcMessage {

class NotifyMediaServerUserJoin : public BaseMessage{

	public:
		static const id = 133;
		__int64 userId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

