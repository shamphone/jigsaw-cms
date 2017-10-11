///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef CameraMoveLeft_H
#define CameraMoveLeft_H

#include "BaseMessage.h"

namespace LyvcMessage {

class CameraMoveLeft : public BaseMessage{

	public:
		static const id = 174;
		__int32 commPort;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

