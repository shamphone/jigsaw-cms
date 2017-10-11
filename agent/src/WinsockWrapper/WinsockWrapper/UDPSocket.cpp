#include "stdafx.h"
#include "UDPSocket.h"

CUDPSocket::CUDPSocket()
{
	memset((void*)&m_LocalAddr,0,sizeof(m_LocalAddr));
	memset((void*)&m_RemoteAddr,0,sizeof(m_RemoteAddr));
	m_Socket=0;
}

CUDPSocket::~CUDPSocket()
{
	Destroy();
}

BOOL CUDPSocket::Create(UINT uSocketPort, char* pszSockAddr)
{	
	_ASSERTE(!m_Socket);
	
    //创建无连接数据报套接口
    m_Socket=socket(PF_INET,SOCK_DGRAM,IPPROTO_UDP);
    if( m_Socket == INVALID_SOCKET )
    {
        return FALSE;
    }

    // 如果指定了端口，那么绑定到该端口
    if( uSocketPort !=0)
    {
	    m_LocalAddr.sin_family=AF_INET;
	    m_LocalAddr.sin_port=htons(uSocketPort);
        if( pszSockAddr == NULL )
        {
            m_LocalAddr.sin_addr.s_addr = htonl(INADDR_ANY);
        }
        else
        {
            m_LocalAddr.sin_addr.s_addr=::inet_addr(pszSockAddr);
        }

        if(bind(m_Socket,(SOCKADDR*)&m_LocalAddr,sizeof(SOCKADDR_IN))==SOCKET_ERROR)
	    {
		    m_Socket = 0;
            return FALSE;
	    }
    }

	// 否则的话，请系统绑定一个端口，以便进行下一步的发送和接收
	// for bug 407
	else
	{
	    m_LocalAddr.sin_family=AF_INET;
	    m_LocalAddr.sin_port=htons(0);
        m_LocalAddr.sin_addr.s_addr = htonl(INADDR_ANY);

		if( bind(m_Socket, (SOCKADDR*)&m_LocalAddr, sizeof(SOCKADDR_IN)) == SOCKET_ERROR)
		{
			m_Socket = 0;
			return FALSE;
		}

		int namelen = sizeof(SOCKADDR_IN);
		::getsockname(m_Socket, (SOCKADDR*)&m_LocalAddr, &namelen);
		_RPT1(_CRT_WARN, "Local udp port = %d\n", ntohs(m_LocalAddr.sin_port));
	}

	return TRUE;
}

void CUDPSocket::SetBuffer(int bufferSize)
{
    int nSize = bufferSize;
	setsockopt(m_Socket,SOL_SOCKET,SO_SNDBUF,(char*)&nSize,sizeof(int));
	nSize = bufferSize;
	setsockopt(m_Socket,SOL_SOCKET,SO_RCVBUF,(char*)&nSize,sizeof(int));
    return;
}

int CUDPSocket::Destroy()
{
	int iRet;
	iRet=closesocket(m_Socket);
	memset((void*)&m_LocalAddr,0,sizeof(m_LocalAddr));
	memset((void*)&m_RemoteAddr,0,sizeof(m_RemoteAddr));
	m_Socket=0;
	return iRet;
}

SOCKET CUDPSocket::GetSocket() const
{
	return m_Socket;
}

SOCKADDR_IN CUDPSocket::GetLocalAddr() const
{
	return m_LocalAddr;
}


SOCKADDR_IN CUDPSocket::GetRemoteAddr() const
{
	return m_RemoteAddr;
}

BOOL CUDPSocket::IsSocket()
{
	return (BOOL)m_Socket;
}

void CUDPSocket::SetRemoteAddr(const char *pszRemoteSockAddr, unsigned short uPort)
{	
	m_RemoteAddr.sin_family=AF_INET;
    m_RemoteAddr.sin_addr.s_addr=::inet_addr(pszRemoteSockAddr);
	m_RemoteAddr.sin_port=htons(uPort);
}

BOOL CUDPSocket::SendTo(const char *pBuf, DWORD dwLen, SOCKADDR* pRemoteAddr)
{
    // If remote address is not set, use remote address
	if(!pRemoteAddr)
    {
		pRemoteAddr=(SOCKADDR*)&m_RemoteAddr;
    }

	DWORD byteSent = 0;
	DWORD ret;

	while ( dwLen - byteSent > 0 )
	{
		ret =  sendto(m_Socket, pBuf + byteSent, dwLen - byteSent, 0, pRemoteAddr, sizeof(SOCKADDR_IN));
		if( ret == SOCKET_ERROR )
		{
			int errorCode = ::WSAGetLastError();
			return FALSE;
		}
		byteSent += ret;
	}
	return TRUE;
}

DWORD CUDPSocket::RecvFrom(char *pBuf, DWORD dwLen, SOCKADDR* pRemoteAddr)
{
	int addrlen = sizeof(SOCKADDR);
	int received = recvfrom(m_Socket, pBuf, dwLen, 0, pRemoteAddr, &addrlen);
    if (received == SOCKET_ERROR)
    {
		int errorCode = ::WSAGetLastError();
		return 0;
    }

	return received;
}
