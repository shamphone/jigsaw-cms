///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef RequestSendFile_H
#define RequestSendFile_H

#include "BaseMessage.h"

namespace LyvcMessage {

class RequestSendFile : public BaseMessage{

	public:
		static const id = 168;
		__int64 receiverId;
		string fullFileName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

