///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef ModifyConference_H
#define ModifyConference_H

#include "BaseMessage.h"

namespace LyvcMessage {

class ModifyConference : public BaseMessage{

	public:
		static const id = 156;
		__time64_t startTime;
		__time64_t endTime;
		__int64 conId;
		__int64 conModelId;
		string conName;
		string conDesc;
		string conFileDesc;
		string conFileURL;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
