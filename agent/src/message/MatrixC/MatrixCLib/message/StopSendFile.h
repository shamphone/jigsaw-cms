///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef StopSendFile_H
#define StopSendFile_H

#include "BaseMessage.h"

namespace LyvcMessage {

class StopSendFile : public BaseMessage{

	public:
		static const id = 166;
		__int64 receiverId;
		string fullFileName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

