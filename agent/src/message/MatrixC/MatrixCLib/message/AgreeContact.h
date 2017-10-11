///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef AgreeContact_H
#define AgreeContact_H

#include "BaseMessage.h"

namespace LyvcMessage {

class AgreeContact : public BaseMessage{

	public:
		static const id = 114;
		__int64 receiverId;
		string name;
		__int64 groupId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
