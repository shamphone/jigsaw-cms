///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef UserQuitConference_H
#define UserQuitConference_H

#include "BaseMessage.h"

namespace LyvcMessage {

class UserQuitConference : public BaseMessage{

	public:
		static const id = 48;
		__int64 userId;
		__int64 conferenceId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

