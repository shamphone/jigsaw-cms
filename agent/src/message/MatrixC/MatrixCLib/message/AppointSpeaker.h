///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef AppointSpeaker_H
#define AppointSpeaker_H

#include "BaseMessage.h"

namespace LyvcMessage {

class AppointSpeaker : public BaseMessage{

	public:
		static const id = 172;
		__int64 speakerId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

