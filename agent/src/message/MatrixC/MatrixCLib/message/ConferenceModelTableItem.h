///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef ConferenceModelTableItem_H
#define ConferenceModelTableItem_H

#include "BaseMessage.h"

namespace LyvcMessage {

class ConferenceModelTableItem : public BaseMessage{

	public:
		static const id = 53;
		__int64 modelId;
		string modelName;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
