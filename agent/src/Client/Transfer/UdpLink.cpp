#include "stdafx.h"
#include "UdpLink.h"
#include "common/common/MediaPacket/udp_media_def.h"

UdpLink::UdpLink( UDPLINK_CALLBACK pCallback, void* pCallbackParameter)
{
    m_pCallback = pCallback;
    m_pCallbackParameter = pCallbackParameter;
	m_hUSockThread = 0;
}

UdpLink::~UdpLink()
{
}

BOOL UdpLink::Create(string IP, int port)
{
	m_sServerIP = IP;
	m_nPort = port;

    // Set remote address
	m_MediaUdpSocket.SetRemoteAddr(m_sServerIP.c_str(), m_nPort);

    // Create UDPSocket
    if (!m_MediaUdpSocket.Create())
    {
		return FALSE;
    }

    m_MediaUdpSocket.SetBuffer(MAX_UDP_SIZE);

	// 创建读取UDP的线程
    unsigned int threadID;
	m_hUSockThread=(HANDLE)::_beginthreadex(NULL, 0, USockThreadProc, this, 0, &threadID);
	if (!m_hUSockThread)
	{
        m_MediaUdpSocket.Destroy();
		return FALSE;
	}
	_RPTF1(_CRT_WARN, "TransModel udp read thread created, id: %#x \n",threadID);

	::SetThreadPriority(m_hUSockThread, THREAD_PRIORITY_ABOVE_NORMAL);

	return TRUE;
};

void UdpLink::Destroy()
{
	_ASSERTE (m_MediaUdpSocket.IsSocket());
    m_MediaUdpSocket.Destroy();

	_ASSERTE (m_hUSockThread);
    ::WaitForSingleObject(m_hUSockThread, INFINITE);
    ::CloseHandle(m_hUSockThread);
    m_hUSockThread = 0;

	return;
}

//处理从媒体服务器上接受到的数据包;
unsigned int WINAPI UdpLink::USockThreadProc(LPVOID lpParameter)
{
	UdpLink* pMgr = (UdpLink*)lpParameter;
	char buf[MAX_UDP_SIZE];
	DWORD dwLen=0;

	while(true)
	{
		SOCKADDR remoteAddress;
		dwLen = pMgr->m_MediaUdpSocket.RecvFrom(buf, MAX_UDP_SIZE, &remoteAddress);
		ASSERT(dwLen <= MAX_UDP_SIZE);
		if (dwLen > 0)
		{
            (*(pMgr->m_pCallback))(pMgr->m_pCallbackParameter, buf, dwLen);
		}
		else
		{
			// Quit the thread
			break;
		}		
	}

	return 0;
}

SOCKET UdpLink::getSocket()
{
	return m_MediaUdpSocket.GetSocket();
}

int UdpLink::send(char* buf, int dwLen)
{
	_ASSERTE(dwLen <= MAX_UDP_SIZE);
	return m_MediaUdpSocket.SendTo(buf, dwLen);
}

