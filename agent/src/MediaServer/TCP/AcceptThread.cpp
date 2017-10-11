#include "stdafx.h"
#include "AcceptThread.h"
#include "TCPForward.h"
#include "TCPUser.h"
#include "Common/Common/Log/Log.h"
#include "Common/Common/MediaPacket/tcp_media_def.h"

AcceptThread::AcceptThread(TCPForward* pTCPForward)
{
    m_hThread = 0;
    m_ListenSocket = INVALID_SOCKET;
	m_pTCPForward = pTCPForward;
}

AcceptThread::~AcceptThread()
{
}

BOOL AcceptThread::start(int port)
{
    // Init winsock
    WSADATA wsaData;
    if( ::WSAStartup(MAKEWORD(2, 2), &wsaData) != 0 )
    {
		FVS_DEBUG("WSAStartup error\n");
        return FALSE;
    }

    // Create the listen socket
    m_ListenSocket = socket(AF_INET, SOCK_STREAM, 0);
    if( m_ListenSocket == INVALID_SOCKET )
    {
		FVS_DEBUG1("socket error: %d\n", ::WSAGetLastError());
        ::WSACleanup();
        return FALSE;
    }

    // bind to local addr
    SOCKADDR_IN listenAddr;
    memset(&listenAddr, 0, sizeof(SOCKADDR_IN));
    listenAddr.sin_family = AF_INET;
    listenAddr.sin_port = htons(port);    
    listenAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    if( bind(m_ListenSocket, (SOCKADDR*)&listenAddr, sizeof(listenAddr)) != 0)
    {
		FVS_DEBUG1("bind error: %d\n", ::WSAGetLastError());
        closesocket(m_ListenSocket);
        m_ListenSocket = INVALID_SOCKET;
        ::WSACleanup();
        return FALSE;
    }

    // Listen on the socket
    if( listen(m_ListenSocket, 10) != 0 )
	{
		FVS_DEBUG1("listen error: %d\n", ::WSAGetLastError());
        closesocket(m_ListenSocket);
        m_ListenSocket = INVALID_SOCKET;
        ::WSACleanup();
        return FALSE;
	}

    // Start the accept thread
    unsigned int threadId;
    m_hThread = (HANDLE)_beginthreadex(NULL, 0, ThreadProc, this, 0, &threadId);
    if( m_hThread == 0 )
    {
		FVS_DEBUG("Fail to start accept thread\n");
        closesocket(m_ListenSocket);
        m_ListenSocket = INVALID_SOCKET;
        ::WSACleanup();
        return FALSE;
    }

    return TRUE;
}

void AcceptThread::stop()
{
    ::closesocket(m_ListenSocket);
    m_ListenSocket = 0;

    ::WaitForSingleObject(m_hThread, INFINITE);
    ::CloseHandle(m_hThread);
    m_hThread = 0;

    return;
}

unsigned int AcceptThread::ThreadProc(void* pObject)
{
    AcceptThread* _this = (AcceptThread*)pObject;

    // Try to accept new socket
    while( true )
    {
		SOCKADDR_IN peerAddr;
		int addrlen = sizeof(peerAddr);

        SOCKET newSocket = accept(_this->m_ListenSocket, (SOCKADDR*)&peerAddr, &addrlen);
#pragma message("能够区分关闭listensocket和接收错误吗?")
        if( newSocket == INVALID_SOCKET)
        {
			break;
        }

		__int64 userId = _this->authenticateConnection(newSocket);
		if( userId == 0 )
		{
			::closesocket(newSocket);
			FVS_DEBUG1("connection from %s not send data in due time, close it\n", inet_ntoa(peerAddr.sin_addr));
			continue;
		}

		::EnterCriticalSection(&_this->m_pTCPForward->m_cs);

		TCPForward::TCPUserMap::iterator itUser = _this->m_pTCPForward->m_users.find(userId);
		if( itUser == _this->m_pTCPForward->m_users.end() )
		{
			::closesocket(newSocket);
			FVS_DEBUG1("Receive unknown connection from %s, close it.\n", inet_ntoa(peerAddr.sin_addr));
		}
		else
		{
			TCPUser* pTCPUser = itUser->second;
			pTCPUser->startForward(newSocket);
		}

		::LeaveCriticalSection(&_this->m_pTCPForward->m_cs);
    }

    return 0;
}

__int64 AcceptThread::authenticateConnection(SOCKET s)
{
	// Return user id, 0 for failed.
	__int64 userId = 0;

	// We use event-select model for the socket.
	HANDLE event = WSA_INVALID_EVENT;

	// We set a timer for read.
	HANDLE timer = NULL;

	// Create an WSA Event for the socket
	event = WSACreateEvent();
	if(event == WSA_INVALID_EVENT)
	{
		FVS_DEBUG1("WSACreateEvent failed. %d", WSAGetLastError());
		goto end;
	}

	// Associate the socket with the event.
	if( WSAEventSelect(s, event, FD_READ | FD_CLOSE ) == SOCKET_ERROR)
	{
		FVS_DEBUG1("WSAEventSelect failed. %d", WSAGetLastError());
		goto end;
	}

	// Create a waitable timer
	timer = ::CreateWaitableTimer(NULL, FALSE, NULL);
	if( timer == NULL )
	{
		FVS_DEBUG1("CreateWaitableTimer failed. %d", GetLastError());
		goto end;
	}
	
	// Set the timer in 60 seconds
	LARGE_INTEGER li;
	const int nTimerUnitsPerSecond = 10000000;
	li.QuadPart = -(60 * nTimerUnitsPerSecond);
	if( SetWaitableTimer(timer, &li, 6 * 60 * 60 * 1000, NULL, NULL, FALSE) == 0)
	{
		FVS_DEBUG1("SetWaitableTimer failed. %d", GetLastError());
		goto end;
	}

	// Store the authenticate code
    PACK_TCP tcpPack;
    char* buffer = (char*)&tcpPack;
	int authCodePosition = 0;

	// Read auth code, Wait for the objects
	DWORD waitResult;
	HANDLE handles[] = {timer, event};
	while( authCodePosition < sizeof(PACK_TCP) )
	{
		waitResult = WaitForMultipleObjects(2, handles, FALSE, INFINITE);
		if( waitResult == WAIT_FAILED)
		{
			FVS_DEBUG1("WaitForMultipleObjects failed. %d", GetLastError());
			goto end;
		}

		// Timer is due
		if( waitResult - WAIT_OBJECT_0 == 0 )
		{
			FVS_DEBUG("Fail to read authcode in due time.");
			goto end;
		}

		// Socket is ready
		else if( waitResult - WAIT_OBJECT_0 == 1)
		{
			WSANETWORKEVENTS events;
			if( WSAEnumNetworkEvents( s, event, &events ) == SOCKET_ERROR)
			{
				FVS_DEBUG1("WSAEnumNetworkEvents failed. %d", WSAGetLastError());
				goto end;
			}

			// Check the network event
			if( events.lNetworkEvents & FD_READ )
			{
				int readCount = recv(s, buffer + authCodePosition, sizeof(PACK_TCP) - authCodePosition, 0);
				if( readCount == SOCKET_ERROR )
				{
					FVS_DEBUG1("recv failed. %d", WSAGetLastError());
					goto end;
				}
				if( readCount == 0 )
				{
					FVS_DEBUG0("connection closed by peer");
					goto end;
				}
				authCodePosition += readCount;
			}

			else if( events.lNetworkEvents & FD_CLOSE )
			{
				FVS_DEBUG("connection closed by peer");
				goto end;
			}
		}
	}

	if( tcpPack.flag != FVS_TCP_USER)
	{
		FVS_DEBUG("Invalid authenticate pack.");
		goto end;
	}
	userId = tcpPack.userId;

end:
	if( timer != NULL )
	{
		CancelWaitableTimer(timer);
		CloseHandle(timer);
	}

	if( event != WSA_INVALID_EVENT )
	{
		WSAEventSelect(s, event, 0);
		WSACloseEvent(event);
	}

	// Set this socket to non-block mode
	u_long arg = 0;
	::ioctlsocket(s, FIONBIO, &arg);

	return userId;
}

