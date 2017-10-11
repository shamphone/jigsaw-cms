///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef CameraMoveDown_H
#define CameraMoveDown_H

#include "BaseMessage.h"

namespace LyvcMessage {

class CameraMoveDown : public BaseMessage{

	public:
		static const id = 177;
		__int32 commPort;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
