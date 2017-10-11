///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef ApplySendFile_H
#define ApplySendFile_H

#include "BaseMessage.h"

namespace LyvcMessage {

class ApplySendFile : public BaseMessage{

	public:
		static const id = 164;
		__int64 receiverId;
		__int64 fileLength;
		string fullFileName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

