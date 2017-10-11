///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef WebAddConference_H
#define WebAddConference_H

#include "BaseMessage.h"

namespace LyvcMessage {

class WebAddConference : public BaseMessage{

	public:
		static const id = 58;
		__int64 conId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

