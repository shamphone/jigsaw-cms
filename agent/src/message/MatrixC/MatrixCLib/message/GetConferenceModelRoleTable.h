///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef GetConferenceModelRoleTable_H
#define GetConferenceModelRoleTable_H

#include "BaseMessage.h"

namespace LyvcMessage {

class GetConferenceModelRoleTable : public BaseMessage{

	public:
		static const id = 55;
		__int64 modelId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

