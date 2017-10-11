///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef ListenToUser_H
#define ListenToUser_H

#include "BaseMessage.h"

namespace LyvcMessage {

class ListenToUser : public BaseMessage{

	public:
		static const id = 170;
		__int64 receiverId;
		string beListenedIds;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
