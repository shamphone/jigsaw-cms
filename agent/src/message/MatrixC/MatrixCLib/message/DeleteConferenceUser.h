///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef DeleteConferenceUser_H
#define DeleteConferenceUser_H

#include "BaseMessage.h"

namespace LyvcMessage {

class DeleteConferenceUser : public BaseMessage{

	public:
		static const id = 190;
		__int64 userId;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

