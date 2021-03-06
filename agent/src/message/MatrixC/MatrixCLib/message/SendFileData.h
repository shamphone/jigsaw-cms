///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef SendFileData_H
#define SendFileData_H

#include "BaseMessage.h"

namespace LyvcMessage {

class SendFileData : public BaseMessage{

	public:
		static const id = 165;
		bool isFirstSend;
		bool isFinished;
		__int32 dataLength;
		__int32 compressedLength;
		__int64 receiverId;
		string fullFileName;
		string fileData;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

