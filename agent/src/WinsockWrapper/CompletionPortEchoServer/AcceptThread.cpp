#include "stdafx.h"
#include "AcceptThread.h"
#include "IOCompletionPortModel.h"

AcceptThread::AcceptThread()
{
    m_hThread = 0;
    m_pIOCP = 0;
    m_ListenSocket = INVALID_SOCKET;
}

AcceptThread::~AcceptThread()
{
}

BOOL AcceptThread::start(IOCompletionPortModel* pIOCP, int port)
{
    m_pIOCP = pIOCP;

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
    m_hThread = (HANDLE)_beginthreadex(NULL, 0, ThreadProc, this, 0, &threadId);
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

unsigned int AcceptThread::ThreadProc(void* pObject)
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

        _this->m_pIOCP->addSocket(newSocket);
    }

    return 0;
}
