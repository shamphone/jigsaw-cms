#include "stdafx.h"
#include "AcceptThread.h"

AcceptThread::AcceptThread()
{
    m_hThread = 0;
    m_ListenSocket = INVALID_SOCKET;
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
        return FALSE;
    }

    // Create the listen socket
    m_ListenSocket = socket(AF_INET, SOCK_STREAM, 0);
    if( m_ListenSocket == INVALID_SOCKET )
    {
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
        closesocket(m_ListenSocket);
        m_ListenSocket = INVALID_SOCKET;
        ::WSACleanup();
        return FALSE;
    }

    // Listen on the socket
    listen(m_ListenSocket, 10);

    // Start the accept thread
    unsigned int threadId;
    m_hThread = (HANDLE)_beginthreadex(NULL, 0, AcceptThreadProc, this, 0, &threadId);
    if( m_hThread == 0 )
    {
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

unsigned int AcceptThread::AcceptThreadProc(void* pObject)
{
    AcceptThread* _this = (AcceptThread*)pObject;

    // Try to accept new socket
    while( true )
    {
        SOCKET newSocket = accept(_this->m_ListenSocket, 0, 0);
        if( newSocket == INVALID_SOCKET)
        {
			break;
        }

        unsigned int threadId;
        SOCKET* pSocket = new SOCKET();
        *pSocket = newSocket;
        HANDLE echoThread = (HANDLE)_beginthreadex(NULL, 0, EchoThreadProc, pSocket, 0, &threadId);
        ::CloseHandle(echoThread);
    }

    return 0;
}

unsigned int AcceptThread::EchoThreadProc(void* pObject)
{
    SOCKET s = *(SOCKET*)pObject;
    delete pObject;

    int ret;
    const int BUFFER_SIZE = 4096;
    char buffer[BUFFER_SIZE];
    while(true)
    {
        ret = recv(s, buffer, BUFFER_SIZE, 0);
        if( ret <= 0 )
        {
            break;
        }

        ret = send(s, buffer, ret, 0);
        if( ret <= 0 )
        {
            break;
        }
    }

    ::closesocket(s);
    return 0;
}

