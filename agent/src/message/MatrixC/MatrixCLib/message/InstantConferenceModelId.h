///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef InstantConferenceModelId_H
#define InstantConferenceModelId_H

#include "BaseMessage.h"

namespace LyvcMessage {

class InstantConferenceModelId : public BaseMessage{

	public:
		static const id = 154;
		__int64 instantConferenceModelId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

