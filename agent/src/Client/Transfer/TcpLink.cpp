#include "stdafx.h"
#include "TcpLink.h"
#include "Common/Common/MediaPacket/tcp_media_def.h"
#include "Common/Common/Log/Log.h"

TcpLink::TcpLink( TCPLINK_CALLBACK pCallback, void* pCallbackParameter)
{
    m_pCallback = pCallback;
    m_pCallbackParameter = pCallbackParameter;
	m_hSocketThread = 0;
}

TcpLink::~TcpLink()
{
}

BOOL TcpLink::Create(string IP, int port)
{
	m_sServerIP = IP;
	m_nPort = port;

    // Create socket and connect
	if( !m_Socket.Create() )
	{
		return FALSE;
	}

    if (!m_Socket.Connect(m_nPort, m_sServerIP.c_str()))
	{
		m_Socket.Destroy();
		return FALSE;
	}

    // Run Socket read thread.
	unsigned int threadID;
	m_hSocketThread=(HANDLE)::_beginthreadex(NULL, 0, SocketThreadProc, this, 0, &threadID);
	_RPTF1(_CRT_WARN, "TransModel tcp read thread created, id: %#x \n",threadID);
	if (m_hSocketThread == 0)
	{
		m_Socket.Destroy();
		return FALSE;
	}

	return TRUE;
}

void TcpLink::Destroy()
{
	_ASSERTE(m_Socket.IsSocket());
    m_Socket.Destroy();

	_ASSERTE(m_hSocketThread);
    ::WaitForSingleObject(m_hSocketThread, INFINITE);
    ::CloseHandle(m_hSocketThread);
    m_hSocketThread = 0;

	return;
}


//处理从媒体服务器上接受到的数据包;
unsigned int WINAPI TcpLink::SocketThreadProc(LPVOID lpParameter)
{
    TcpLink* _this = (TcpLink*)lpParameter;

    char* pBuffer = new char[MAX_TCP_SIZE + sizeof(PACK_TCP)];

    while(true)
    {
        if( !_this->m_Socket.ReceiveBlock(pBuffer, sizeof(PACK_TCP)))
        {
            FVS_DEBUG1("TcpLink ReceiveBlock for header error. %d", ::WSAGetLastError());
            break;
        }

        int len = ((PACK_TCP*)pBuffer)->data_size;
        _ASSERTE(len < MAX_TCP_SIZE);

        if( !_this->m_Socket.ReceiveBlock(pBuffer+sizeof(PACK_TCP), len) )
        {
            FVS_DEBUG1("TcpLink ReceiveBlock for data error. %d", ::WSAGetLastError());
            break;
        }

        (*(_this->m_pCallback))(_this->m_pCallbackParameter, pBuffer, len+sizeof(PACK_TCP));
    }

    delete pBuffer;
	return 0;
}

SOCKET TcpLink::getSocket()
{
	return m_Socket.GetSocket();
}

int TcpLink::send(char* buf, int dwLen)
{
	_ASSERTE(dwLen <= MAX_TCP_SIZE);
    return m_Socket.Send(buf, dwLen);
}

