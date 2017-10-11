#include "stdafx.h"
#include "UDPForward.h"
#include "common/common/MediaPacket/udp_media_def.h"
#include "common/common/Log/Log.h"

UDPForward::UDPForward()
{
    m_hUSockThread = 0;
    m_bUDPRuningFlag = FALSE;

    // 初始化关键区
    ::InitializeCriticalSection (&m_cs);
}

UDPForward::~UDPForward()
{
    // 关闭关键区
    ::DeleteCriticalSection(&m_cs);
}

BOOL UDPForward::start(int port)
{
    // Create socket
	if (!m_udpSocket.Create(port, NULL))
    {
        FVS_DEBUG("Can't create UDP socket\n");
		return FALSE;
    }
    m_udpSocket.SetBuffer(MAX_UDP_SIZE);

    // Start socket read thread
    this->m_bUDPRuningFlag = TRUE;
    unsigned int dwThreadId;
	m_hUSockThread = (HANDLE)::_beginthreadex(NULL, 0, UdpSockThreadProc, this, 0, &dwThreadId);
	if (!m_hUSockThread)
	{
		FVS_DEBUG("Fail to start UDP socket thread\n");
        m_udpSocket.Destroy();
		return FALSE;
	}

	return TRUE;
}

void UDPForward::stop()
{
    // 关闭UDP服务线程
    m_bUDPRuningFlag = FALSE;
	if (m_udpSocket.IsSocket())
    {
		m_udpSocket.Destroy();
    }
    ::WaitForSingleObject(m_hUSockThread, INFINITE);
    ::CloseHandle(m_hUSockThread);
    m_hUSockThread = 0;

    // 清理用户
	FvsmUserMap::iterator i;
	for(i=m_allUsers.begin(); i!=m_allUsers.end(); i++)
	{
		_ASSERTE(i->second != NULL);
		delete (i->second);
	}
	m_allUsers.clear();

    return;
}

unsigned int WINAPI UDPForward::UdpSockThreadProc(LPVOID lpParameter)
{
	UDPForward* _this=(UDPForward*)lpParameter;
	
	char buf[MAX_UDP_SIZE];
	int iLen = 0;
	while(_this->m_bUDPRuningFlag)
	{
		SOCKADDR addr;
		iLen = _this->m_udpSocket.RecvFrom(buf, MAX_UDP_SIZE, &addr);
		if(iLen <= 0)
		{
            // For bug 379, If Windows Socket failed to send a udp packet
            // due to the peer not listening on the port, and ICMP message
            // will be sent back and translated into a 10054 error message
            // for the sender. 
            //
            // we won't care this bug anymore, for the simple reason that if 
            // peer does not exist anymore, the Conference Server will 
            // notify the Media Server, and it won't take a long time for the 
            // media server to remove the invalid client from the route table.

            DWORD dwError = ::WSAGetLastError();
            if( dwError != 10054)
            {
                FVS_DEBUG1("UDP Socket Receive error. %d\n", ::WSAGetLastError());
            }
			continue;
		}

        __int64 senderId = ((PACK_ADDR*)buf)->userId;
        BYTE flag = ((PACK_ADDR*)buf)->flag;

		EnterCriticalSection(&_this->m_cs);	

        FvsmUser* pUser = _this->getUser(senderId);
        if( pUser == NULL )
        {
            FVS_DEBUG1("Receive packet for user %I64d, but the user is not present.\n", senderId);
            LeaveCriticalSection(&_this->m_cs);
            continue;
        }

		switch(flag)
		{
            case FVS_MSG_AUDIO:
            case FVS_MSG_VIDEO:
            {
                _this->forwardData(pUser, buf, iLen, flag);
    			break;
            }

            case FVS_MSG_COMPRESSION_VIDEO:
            {
                _this->forwardData(pUser, buf, iLen, flag);
    			break;
            }

            case FVS_MSG_ADDR:
            {
                pUser->m_udpAddr = addr;
			    break;
            }

            default:
                _ASSERTE(FALSE);
                break;
		}
		LeaveCriticalSection(&_this->m_cs);
	}
	return 0;
}

void UDPForward::addUser(__int64 userId)
{
    ::EnterCriticalSection(&m_cs);

    // Assert user doesn't exist
	FvsmUser* pUser = getUser(userId);
	//_ASSERTE(pUser == NULL);
	
    // Add this user
    pUser = new FvsmUser();
    pUser->m_userId = userId;
	m_allUsers[userId] = pUser;

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG1("Add user %I64d\n", pUser->m_userId);
}

void UDPForward::removeUser(__int64 userId)
{
    ::EnterCriticalSection(&m_cs);

	FvsmUser* pUser = getUser(userId);
    _ASSERTE(pUser != NULL);

	FvsmUserMap::iterator i;
	for(i=m_allUsers.begin(); i!=m_allUsers.end(); i++)
	{
        FvsmUser* pPeer = i->second;
		if (pPeer->m_userId != userId)
		{
            if( pPeer->isReceiveAudioFromUser(userId) )
            {
                pPeer->removeReceiver(userId, FVS_MSG_AUDIO);
            }

            if( pPeer->isReceiveVideoFromUser(userId) )
            {
                pPeer->removeReceiver(userId, FVS_MSG_VIDEO);
            }
		}
	}

	m_allUsers.erase(userId);
    delete pUser;

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG1("Remove user %I64d\n", userId);
	return;
}

void UDPForward::addMediaRelation(__int64 from, __int64 to, int type)
{
    ::EnterCriticalSection(&m_cs);

	FvsmUser* pUser = getUser(from);
    _ASSERTE( pUser != NULL );
	pUser->addReceiver(to, type);

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG3("Add media relation from %I64d to %I64d. Type: %d\n", from, to, type);
}

void UDPForward::removeMediaRelation(__int64 from, __int64 to, int type)
{
    ::EnterCriticalSection(&m_cs);

	FvsmUser* pUser = getUser(from);
	_ASSERTE(pUser != NULL);
	pUser->removeReceiver(to, type);

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG3("Remove media relation from %I64d to %I64d. Type: %d\n", from, to, type);
}

FvsmUser* UDPForward::getUser(__int64 userid)
{
	FvsmUserMap::iterator i = m_allUsers.find(userid);
	if (i == m_allUsers.end())
    {
		return NULL;
    }
    else
    {
        return i->second;
    }
}

void UDPForward::forwardData(FvsmUser* pUser, char* buffer, int nLen, int dataType)
{
    list<__int64>* pReceivers = NULL;
	switch (dataType)
	{
        case FVS_MSG_AUDIO: 
            pReceivers = &pUser->m_audioReceivers;
            break;

        case FVS_MSG_VIDEO:
            pReceivers = &pUser->m_videoReceivers;
            break;

        case FVS_MSG_COMPRESSION_VIDEO:
            pReceivers = &pUser->m_videoReceivers;
            break;

        default:
            _ASSERTE(FALSE);
            break;
	}

    list<__int64>::iterator i;	
    for (i = pReceivers->begin(); i != pReceivers->end(); i++)
    {			
        FvsmUser* pReceiver = getUser(*i);

        // This user exist and the address is set
        if (pReceiver != NULL && pReceiver->m_udpAddr.sa_family != 0 )
        {
            this->m_udpSocket.SendTo(buffer, nLen, &pReceiver->m_udpAddr);
        }
    }
}

