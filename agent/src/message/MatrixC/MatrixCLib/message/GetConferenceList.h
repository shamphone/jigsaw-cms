///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef GetConferenceList_H
#define GetConferenceList_H

#include "BaseMessage.h"

namespace LyvcMessage {

class GetConferenceList : public BaseMessage{

	public:
		static const id = 51;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

