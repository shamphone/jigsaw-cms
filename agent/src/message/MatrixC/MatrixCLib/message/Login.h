///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef Login_H
#define Login_H

#include "BaseMessage.h"

namespace LyvcMessage {

class Login : public BaseMessage{

	public:
		static const id = 35;
		__int32 avCompressionCardChannelNumber;
		string username;
		string password;
		string domain;	//����

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

