///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef StartMarkWindow_H
#define StartMarkWindow_H

#include "BaseMessage.h"

namespace LyvcMessage {

class StartMarkWindow : public BaseMessage{

	public:
		static const id = 142;
		__int64 receiverId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

