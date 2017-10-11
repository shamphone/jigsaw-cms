///////////////////////////////
// Message definition file
// Generated by lyvc Message
///////////////////////////////

#ifndef ServerInfo_H
#define ServerInfo_H

#include "BaseMessage.h"

namespace LyvcMessage {

class ServerInfo : public BaseMessage{

	public:
		static const id = 188;
		string DeleteConferenceURL;
		string CreateConferenceNoticeURL;
		string CreateBulletinURL;
		string EditConferenceURL;
		string CommonContactMgrURL;
		string ConferenceModeMgrURL;
		string SystemRoleMgrURL;
		string CreateFormalConferenceURL;
		string SelfInfoMgrURL;
		string UserRegisterURL;
		string ClientDownloadURL;
		string domain;

	public:
		virtual string toXML();
		virtual bool fromXML(string& xmlString);

}; // class

}; // namespace

#endif

