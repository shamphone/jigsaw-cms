#include "StdAfx.h"
#include "TCPSocket.h"

CTCPSocket::CTCPSocket()
{
   m_Socket = 0;
}

CTCPSocket::~CTCPSocket()
{
}

BOOL CTCPSocket::Create()
{
    SOCKADDR_IN addr;
    addr.sin_family=AF_INET;
    m_Socket=::socket(AF_INET,SOCK_STREAM,0);
    if(m_Socket ==INVALID_SOCKET)
    {
        m_Socket = 0;
        return FALSE;
    }
    return TRUE;
}

BOOL CTCPSocket::Destroy()
{
    if(!m_Socket)
        return FALSE;

    int iRet = ::closesocket(m_Socket);
    m_Socket=0;
    return iRet;
}

BOOL CTCPSocket::IsSocket()
{
    return (BOOL)m_Socket;
}

SOCKET CTCPSocket::GetSocket()
{
	return this->m_Socket;
}

BOOL CTCPSocket::Connect(UINT uSocketPort, const char *pszSockAddr)
{
    if( m_Socket == 0 )
    {
        // Socket is not created!, call Create() first.
        _ASSERTE(FALSE);
		return FALSE;
    }
	//设置非阻塞方式连接
	unsigned long ul = 1;
	if( ioctlsocket( m_Socket, FIONBIO, (unsigned long*)&ul ) == SOCKET_ERROR ) 
	{
		return FALSE;
	}
	//连接
    SOCKADDR_IN addr;
    addr.sin_family=AF_INET;
    addr.sin_addr.s_addr=inet_addr(pszSockAddr);
    addr.sin_port=htons(uSocketPort);
    ::connect( m_Socket, (sockaddr *)&addr, sizeof(addr) );
	//设置连接超时时间
	struct timeval timeout;
	fd_set f;
	FD_ZERO( &f );
	FD_SET( m_Socket, &f );
	timeout.tv_sec = 3;
	timeout.tv_usec = 0;
	if( select( 0, 0, &f, 0, &timeout ) <= 0 )
	{
		return FALSE;
	}
	//再设回阻塞模式
	ul = 0 ;
	if( ioctlsocket( m_Socket, FIONBIO, (unsigned long*)&ul ) == SOCKET_ERROR )
	{
		return FALSE;
	}
	return TRUE;
}

BOOL CTCPSocket::Send(const char *pBuf, int iLen)const
{
	if( m_Socket == 0 )
	{
		return FALSE;
	}

	DWORD byteSent = 0;
	DWORD ret;

	while ( iLen - byteSent > 0 )
	{
		ret = ::send(m_Socket, pBuf + byteSent, iLen - byteSent, 0);
		if( ret == SOCKET_ERROR )
		{
			return FALSE;
		}
		byteSent += ret;
	}
	return TRUE;
}

int CTCPSocket::Receive(char* pBuf, int nLength)
{
    int ret = ::recv(m_Socket, pBuf, nLength, 0);
    if( ret == 0 || ret == SOCKET_ERROR)
    {
        return 0;
    }
    return ret;
}

BOOL CTCPSocket::ReceiveBlock(char* pBuf, int iLen)
{
    int byteReceived = 0;
    int ret;
    while( byteReceived < iLen )
    {
        ret = ::recv(m_Socket, pBuf + byteReceived, iLen - byteReceived, 0);
        if( ret == SOCKET_ERROR || ret == 0)
        {
            return FALSE;
        }
        byteReceived += ret;
    }

	return TRUE;
}
