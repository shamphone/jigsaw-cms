///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef UserNotFoundByEmail_H
#define UserNotFoundByEmail_H

#include "BaseMessage.h"

namespace LyvcMessage {

class UserNotFoundByEmail : public BaseMessage{

	public:
		static const id = 122;
		string email;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif
