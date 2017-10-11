#pragma once

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

class UDPForward;
class TCPForward;
class LyvcMessage::MatrixC;
class LyvcMessage::BaseMessage;

class ConferenceServerCommandTarget : public LyvcMessage::MessageTarget
{

public:	
	ConferenceServerCommandTarget(LyvcMessage::MatrixC* pMatrixC);
	~ConferenceServerCommandTarget();

    bool create();
    void destroy();

public:

    // MessageHandler
    void NotifyMediaServerAddAudio(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerRemoveAudio(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerAddVideo(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerRemoveVideo(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerAddDesktop(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerRemoveDesktop(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerUserExit(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerUserJoin(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerUserJoinConference(LyvcMessage::BaseMessage* pMessage);
    void NotifyMediaServerUserQuitConference(LyvcMessage::BaseMessage* pMessage);

    // Exception Hanlder
    void ExceptionHandler(LyvcMessage::BaseMessage* pMessage);

private:

    // UDP packet forward
    UDPForward* m_pUDPForward;

	// TCP packet forward
	TCPForward* m_pTCPForward;

	typedef std::pair<__int64, __int64> MediaRelation;
	typedef std::map<MediaRelation, int> MediaRelationCountMap;
	MediaRelationCountMap m_audioRelationCountMap;
	MediaRelationCountMap m_videoRelationCountMap;
	MediaRelationCountMap m_desktopRelationCountMap;

};

