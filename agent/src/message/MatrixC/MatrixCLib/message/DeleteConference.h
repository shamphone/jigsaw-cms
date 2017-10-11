///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef DeleteConference_H
#define DeleteConference_H

#include "BaseMessage.h"

namespace LyvcMessage {

class DeleteConference : public BaseMessage{

	public:
		static const id = 61;
		__int64 conId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

