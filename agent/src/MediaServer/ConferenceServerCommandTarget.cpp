#include "stdafx.h"
#include "ConferenceServerCommandTarget.h"
#include "UDP/UDPForward.h"
#include "TCP/TCPForward.h"
#include "common/common/MediaPacket/udp_media_def.h"
#include "common/common/Log/Log.h"
#include "common/common/PathHelper/PathHelper.h"

#include "message/MatrixC/MatrixCLib/MatrixC.h"
#include "message/MatrixC/MatrixCLib/message/MediaServerLogin.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerAddAudio.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerRemoveAudio.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerAddVideo.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerRemoveVideo.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerAddDesktop.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerRemoveDesktop.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerUserJoin.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerUserExit.h"
#include "message/MatrixC/MatrixCLib/message/MediaServerTCPForwardReady.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerUserJoinConference.h"
#include "message/MatrixC/MatrixCLib/message/NotifyMediaServerUserQuitConference.h"

// This function returns the local ip of machine
// The implementation is problematic, it only return
// on ip address, on modern machine, there are usually
// several network interfaces configured.
static char* GetLocalIPAddress()
{
	char szHostname[256];
	static char local_ip[256];

	WSADATA wsaData;
	::WSAStartup(MAKEWORD(2,2), &wsaData);

	if (gethostname(szHostname, sizeof(szHostname)))
	{
		return NULL;
	}

	HOSTENT* pHostEnt = gethostbyname(szHostname);
	if (pHostEnt == NULL)
	{
		return NULL;
	}

	if (pHostEnt->h_length != 4)
	{
		return NULL;
	}

	strcpy(local_ip, inet_ntoa(*(struct in_addr *)*pHostEnt->h_addr_list));
	::WSACleanup();

	return local_ip;
}

ConferenceServerCommandTarget::ConferenceServerCommandTarget(LyvcMessage::MatrixC* pMatrixC) : MessageTarget(pMatrixC)
{
    m_pUDPForward = NULL;
	m_pTCPForward = NULL;

    // Register Exception handler
    m_pMatrixC->registerExceptionHandler(this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::ExceptionHandler));

    // Register message
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerAddAudio::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerAddAudio));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerRemoveAudio::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerRemoveAudio));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerAddVideo::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerAddVideo));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerRemoveVideo::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerRemoveVideo));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerAddDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerAddDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerRemoveDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerRemoveDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerUserJoin::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerUserJoin));
    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerUserExit::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerUserExit));
//    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerUserJoinConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerUserJoinConference));
//    m_pMatrixC->registerMessageHandler(LyvcMessage::NotifyMediaServerUserQuitConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceServerCommandTarget::NotifyMediaServerUserQuitConference));
}

ConferenceServerCommandTarget::~ConferenceServerCommandTarget()
{
    // Remove message handler
    m_pMatrixC->removeObjectMessageHandler(this);

    // Remove Exception handler
    m_pMatrixC->removeExceptionHandler();
}

bool ConferenceServerCommandTarget::create()
{
    const char* iniFileName = PathHelper::getIniFileFullName();

	//启动UDP数据转发
    int udpPort = ::GetPrivateProfileInt("Forward", "UDPPort", 4005, iniFileName);
	FVS_DEBUG1("Starting udp forward in port %i\n", udpPort);
    m_pUDPForward = new UDPForward();
    if( !m_pUDPForward->start(udpPort))
	{
		FVS_DEBUG0("\nCan't start udp forwarding");
		return false;
	}

	//启动TCP数据转发
    int tcpPort = ::GetPrivateProfileInt("Forward", "TCPPort", 4006, iniFileName);
	FVS_DEBUG1("Starting tcp forward in port %i\n", tcpPort);
    m_pTCPForward = new TCPForward();
    if( !m_pTCPForward->start(tcpPort))
	{
		FVS_DEBUG0("\nCan't start tcp forwarding");
		return false;
	}

	// 向会议服务器发送信息，包括媒体服务器的IP地址和侦听端口。
	// 因为媒体服务器有可能位于NAT设备之后，所以我们采用如下的策略：
    //
    // 如果会议服务器的IP为127.0.0.1，系统将自动使用会议服务器的地址
    // 作为媒体服务器的地址。
    // 
    // 如果会议服务器的IP地址不是127.0.0.1，但是配置了RealIP参数，那么
    // 使用该参数作为媒体服务器的地址。
    //
    // 如果会议服务器的IP地址不是127.0.0.1，而且没有配置RealIP参数，
    // 那么媒体服务器将自动探测本机的IP地址。在多网络界面的机器上或者
    // NAT环境下，探测并不一定能够获得正确的结果。

    LyvcMessage::MediaServerLogin login;
	login.udpPort = udpPort;
	login.tcpPort = tcpPort;

    char buffer[64];
    ::GetPrivateProfileString("LinkServer", "IPAddress", "127.0.0.1", buffer, sizeof(buffer), iniFileName);
    if( strcmp(buffer, "127.0.0.1") != 0 )
    {
        memset(buffer, 0, 64);
        ::GetPrivateProfileString("NAT", "RealIP", "", buffer, sizeof(buffer), iniFileName);
        if( strcmp(buffer, "") == 0 )
        {
            char* local_ip = ::GetLocalIPAddress();
            if( local_ip == NULL)
            {
                FVS_DEBUG0("\nCan't get local ip");
                return false;
            }
            strcpy(buffer, local_ip);
        }
    }

    login.ip = buffer;
    m_pMatrixC->sendMessage(&login);
    return TRUE;
}

void ConferenceServerCommandTarget::destroy()
{
    // Stop UDP forwarding
    if( m_pUDPForward != NULL )
    {
        m_pUDPForward->stop();
        delete m_pUDPForward;
        m_pUDPForward = NULL;
    }

    // Stop TCP forwarding
    if( m_pTCPForward != NULL )
    {
        m_pTCPForward->stop();
        delete m_pTCPForward;
        m_pTCPForward = NULL;
    }
}

void ConferenceServerCommandTarget::NotifyMediaServerAddAudio(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerAddAudio* pMediaRelation = (LyvcMessage::NotifyMediaServerAddAudio*)pMessage;
    _ASSERTE(pMediaRelation->fromUserId != pMediaRelation->toUserId);

    // 判断是否已经有这个关系，如果没有增加一个，否则只是增加引用计数
    MediaRelation relation = std::make_pair(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    MediaRelationCountMap::iterator it = m_audioRelationCountMap.find(relation);
    if( it == m_audioRelationCountMap.end() )
    {
        m_audioRelationCountMap.insert(MediaRelationCountMap::value_type(relation, 1));
        m_pUDPForward->addMediaRelation(pMediaRelation->fromUserId, pMediaRelation->toUserId, FVS_MSG_AUDIO);
    }
    else
    {
        int newCount = it->second + 1;
        m_audioRelationCountMap.erase(it);
        m_audioRelationCountMap.insert(MediaRelationCountMap::value_type(relation, newCount));
    }
}

void ConferenceServerCommandTarget::NotifyMediaServerRemoveAudio(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerRemoveAudio* pMediaRelation = (LyvcMessage::NotifyMediaServerRemoveAudio*)pMessage;
    _ASSERTE(pMediaRelation->fromUserId != pMediaRelation->toUserId);

    // 判断这个关系的引用计数，如果减去后为0，那么就删除该关系，否则的话只是引用计数减一
    MediaRelation relation = std::make_pair(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    MediaRelationCountMap::iterator it = m_audioRelationCountMap.find(relation);

    // For bug 1168
    if( it == m_audioRelationCountMap.end() )
    {
        return;
    }

    int newCount = it->second - 1;
    _ASSERTE( newCount >= 0);
    if( newCount == 0 )
    {
        m_audioRelationCountMap.erase(it);
        m_pUDPForward->removeMediaRelation(pMediaRelation->fromUserId, pMediaRelation->toUserId, FVS_MSG_AUDIO);
    }
    else
    {
        m_audioRelationCountMap.erase(it);
        m_audioRelationCountMap.insert(MediaRelationCountMap::value_type(relation, newCount));
    }
}

void ConferenceServerCommandTarget::NotifyMediaServerAddVideo(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerAddVideo* pMediaRelation = (LyvcMessage::NotifyMediaServerAddVideo*)pMessage;
    _ASSERTE(pMediaRelation->fromUserId != pMediaRelation->toUserId);

    // 判断是否已经有这个关系，如果没有增加一个，否则只是增加引用计数
    MediaRelation relation = std::make_pair(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    MediaRelationCountMap::iterator it = m_videoRelationCountMap.find(relation);
    if( it == m_videoRelationCountMap.end() )
    {
        m_videoRelationCountMap.insert(MediaRelationCountMap::value_type(relation, 1));
        m_pUDPForward->addMediaRelation(pMediaRelation->fromUserId, pMediaRelation->toUserId, FVS_MSG_VIDEO);
    }
    else
    {
        int newCount = it->second + 1;
        m_videoRelationCountMap.erase(it);
        m_videoRelationCountMap.insert(MediaRelationCountMap::value_type(relation, newCount));
    }
}

void ConferenceServerCommandTarget::NotifyMediaServerRemoveVideo(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerRemoveVideo* pMediaRelation = (LyvcMessage::NotifyMediaServerRemoveVideo*)pMessage;
    _ASSERTE(pMediaRelation->fromUserId != pMediaRelation->toUserId);

    // 判断这个关系的引用计数，如果减去后为0，那么就删除该关系，否则的话只是引用计数减一
    MediaRelation relation = std::make_pair(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    MediaRelationCountMap::iterator it = m_videoRelationCountMap.find(relation);

    // For bug 1168
    if( it == m_videoRelationCountMap.end() )
    {
        return;
    }


    int newCount = it->second - 1;
    _ASSERTE( newCount >= 0);
    if( newCount == 0 )
    {
        m_videoRelationCountMap.erase(it);
        m_pUDPForward->removeMediaRelation(pMediaRelation->fromUserId, pMediaRelation->toUserId, FVS_MSG_VIDEO);
    }
    else
    {
        m_videoRelationCountMap.erase(it);
        m_videoRelationCountMap.insert(MediaRelationCountMap::value_type(relation, newCount));
    }
}

void ConferenceServerCommandTarget::NotifyMediaServerAddDesktop(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerAddDesktop* pMediaRelation = (LyvcMessage::NotifyMediaServerAddDesktop*)pMessage;
    _ASSERTE(pMediaRelation->fromUserId != pMediaRelation->toUserId);

    // 判断是否已经有这个关系，如果没有增加一个，否则只是增加引用计数
    MediaRelation relation = std::make_pair(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    MediaRelationCountMap::iterator it = m_desktopRelationCountMap.find(relation);
    if( it == m_desktopRelationCountMap.end() )
    {
        m_desktopRelationCountMap.insert(MediaRelationCountMap::value_type(relation, 1));
        m_pTCPForward->addDesktopRelation(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    }
    else
    {
        int newCount = it->second + 1;
        m_desktopRelationCountMap.erase(it);
        m_desktopRelationCountMap.insert(MediaRelationCountMap::value_type(relation, newCount));
    }

	// Inform the conference server that media server tcp forward ready
	LyvcMessage::MediaServerTCPForwardReady ready;
	ready._conferenceId = pMessage->_conferenceId;
	ready.fromUserId = pMediaRelation->fromUserId;
	ready.toUserId = pMediaRelation->toUserId;
	ready.isAgreeInvite = pMediaRelation->isAgreeInvite;
	this->m_pMatrixC->sendMessage(&ready);
}

void ConferenceServerCommandTarget::NotifyMediaServerRemoveDesktop(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerRemoveDesktop* pMediaRelation = (LyvcMessage::NotifyMediaServerRemoveDesktop*)pMessage;
    _ASSERTE(pMediaRelation->fromUserId != pMediaRelation->toUserId);

    // 判断这个关系的引用计数，如果减去后为0，那么就删除该关系，否则的话只是引用计数减一
    MediaRelation relation = std::make_pair(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    MediaRelationCountMap::iterator it = m_desktopRelationCountMap.find(relation);

    // For bug 1168
    if( it == m_desktopRelationCountMap.end() )
    {
        return;
    }


    int newCount = it->second - 1;
    _ASSERTE( newCount >= 0);
    if( newCount == 0 )
    {
        m_desktopRelationCountMap.erase(it);
        m_pTCPForward->removeDesktopRelation(pMediaRelation->fromUserId, pMediaRelation->toUserId);
    }
    else
    {
        m_desktopRelationCountMap.erase(it);
        m_desktopRelationCountMap.insert(MediaRelationCountMap::value_type(relation, newCount));
    }
}

void ConferenceServerCommandTarget::NotifyMediaServerUserJoin(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerUserJoin* pUserJoin = (LyvcMessage::NotifyMediaServerUserJoin*)pMessage;
    m_pUDPForward->addUser(pUserJoin->userId);
	m_pTCPForward->addUser(pUserJoin->userId);
}

void ConferenceServerCommandTarget::NotifyMediaServerUserExit(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerUserExit* pUserExit = (LyvcMessage::NotifyMediaServerUserExit*)pMessage;
    m_pUDPForward->removeUser(pUserExit->userId);
	m_pTCPForward->removeUser(pUserExit->userId);

    // 清除该用户所有的声音关系计数
    MediaRelationCountMap::iterator it = m_audioRelationCountMap.begin();
    while(it != m_audioRelationCountMap.end())
    {
        MediaRelation relation = it->first;
        if( relation.first == pUserExit->userId || relation.second == pUserExit->userId)
        {
            m_audioRelationCountMap.erase( it++);
        }
        else
        {
            ++it;
        }
    }

    // 清除该用户所有的图像关系计数
    it = m_videoRelationCountMap.begin();
    while(it != m_videoRelationCountMap.end())
    {
        MediaRelation relation = it->first;
        if( relation.first == pUserExit->userId || relation.second == pUserExit->userId)
        {
            m_videoRelationCountMap.erase( it++);
        }
        else
        {
            ++it;
        }
    }

    // 清除该用户所有的桌面关系计数
    it = m_desktopRelationCountMap.begin();
    while(it != m_desktopRelationCountMap.end())
    {
        MediaRelation relation = it->first;
        if( relation.first == pUserExit->userId || relation.second == pUserExit->userId)
        {
            m_desktopRelationCountMap.erase( it++);
        }
        else
        {
            ++it;
        }
    }
}

void ConferenceServerCommandTarget::ExceptionHandler(LyvcMessage::BaseMessage* pMessage)
{
    // Login this event
    FVS_DEBUG("ExceptionHandler in MediaServer\n");

    // Post quit message to main thread
    ::PostQuitMessage(0);
}

void ConferenceServerCommandTarget::NotifyMediaServerUserJoinConference(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerUserJoinConference* pMsg = (LyvcMessage::NotifyMediaServerUserJoinConference*)pMessage;
	m_pTCPForward->addWhiteboardRelation( pMsg->userId, pMsg->confId );
}

void ConferenceServerCommandTarget::NotifyMediaServerUserQuitConference(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::NotifyMediaServerUserQuitConference* pMsg = (LyvcMessage::NotifyMediaServerUserQuitConference*)pMessage;
	m_pTCPForward->removeWhiteboardRelation( pMsg->userId, pMsg->confId );
}
