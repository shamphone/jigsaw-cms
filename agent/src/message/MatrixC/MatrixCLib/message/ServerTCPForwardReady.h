///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef ServerTCPForwardReady_H
#define ServerTCPForwardReady_H

#include "BaseMessage.h"

namespace LyvcMessage {

class ServerTCPForwardReady : public BaseMessage{

	public:
		static const id = 152;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

