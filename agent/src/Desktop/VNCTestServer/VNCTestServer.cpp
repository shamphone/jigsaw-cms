// VNCTestServer.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "SocketUtil.h"

static const short clientPort = 2000;
static const short serverPort = 2001;

struct ClientThreadParameter
{
    SOCKET me;
    SOCKET peer;
};

struct ServerThreadParameter
{
    SOCKET me;
    list<SOCKET> clients;
};

CRITICAL_SECTION g_cs;
ServerThreadParameter serverThreadParameter;

unsigned int WINAPI ServerThreadProc(LPVOID pParameter)
{
    while(true)
    {
        char buffer[1024];
        int ret = ::recv( serverThreadParameter.me, buffer, 1024, 0);
        if( ret <= 0 )
        {
            printf("recv error. %d\n", ::WSAGetLastError());
            break;
        }

        ::EnterCriticalSection(&g_cs);
        list<SOCKET>::iterator it = serverThreadParameter.clients.begin();
        while( it != serverThreadParameter.clients.end() )
        {
            if( ::sendBlock( *it, buffer, ret) == FALSE)
            {
                printf("send error to client. %d\n", ::WSAGetLastError());
                ::closesocket(*it);
                it = serverThreadParameter.clients.erase(it);
            }
            else
            {
                it++;
            }
        }
        ::LeaveCriticalSection(&g_cs);
    }

    printf("Server thread quit.\n");
    return 0;
}

unsigned int WINAPI ClientThreadProc(LPVOID lpParameter)
{
    ClientThreadParameter* pThreadParameter = (ClientThreadParameter*)lpParameter;

    while(true)
    {
        char buffer[1024];
        int ret = ::recv( pThreadParameter->me, buffer, 1024, 0);
        if( ret <= 0 )
        {
            printf("recv error. %d\n", ::WSAGetLastError());
            break;
        }

        if( ::sendBlock( pThreadParameter->peer, buffer, ret) == FALSE)
        {
            break;
        }
    }

    // Release memory
    delete pThreadParameter;

    printf("Client thread quit.\n");
    return 0;
}

unsigned int WINAPI AcceptThreadProc(LPVOID lpParameter)
{
	SOCKET clientListenSocket = *((SOCKET*)lpParameter);
	::listen(clientListenSocket, 5);

    int clientCount = 0;
    while(true)
    {
        SOCKET s = ::accept(clientListenSocket, NULL, NULL);
        if( s == INVALID_SOCKET )
        {
            break;
        }

        // Add this socket to server's client list
        ::EnterCriticalSection(&g_cs);
        serverThreadParameter.clients.push_back(s);
        ::LeaveCriticalSection(&g_cs);

        // Start client socket thread
        unsigned int threadID;
        ClientThreadParameter* pClientParameter = new ClientThreadParameter();
        pClientParameter->me   = s;
        pClientParameter->peer = serverThreadParameter.me;
	    HANDLE thread = (HANDLE)::_beginthreadex(NULL, 0, ClientThreadProc, pClientParameter, 0, &threadID);

        clientCount++;
        printf("Client %d thread start.\n", clientCount);
    }
    return 0;
}

int _tmain(int argc, _TCHAR* argv[])
{
    WSADATA wsaData;
    ::WSAStartup(MAKEWORD(2,2), &wsaData);
    ::InitializeCriticalSection(&g_cs);

    // Create server listen socket
    static SOCKET serverListenSocket = ::socket (AF_INET, SOCK_STREAM, IPPROTO_TCP);
    SOCKADDR_IN InternetAddr;
	memset(&InternetAddr, 0, sizeof(InternetAddr));
    InternetAddr.sin_family = AF_INET;
    InternetAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    InternetAddr.sin_port = htons(serverPort);
    if(bind(serverListenSocket, (PSOCKADDR) &InternetAddr, sizeof(InternetAddr)) == SOCKET_ERROR)
	{
        printf("bind error. %d\n", ::WSAGetLastError());
        return false;
	}
	::listen(serverListenSocket, 5);
  
    // Accept server and start server thread
    unsigned int threadID;
    SOCKET serverSocket = ::accept(serverListenSocket, NULL, NULL);
    ::closesocket(serverListenSocket);
    serverThreadParameter.me = serverSocket;
    HANDLE serverThread = (HANDLE)::_beginthreadex(NULL, 0, ServerThreadProc, NULL, 0, &threadID);
    printf("Server thread started.\n");

    // Start accept thread
	SOCKET clientListenSocket = ::socket (AF_INET, SOCK_STREAM, IPPROTO_TCP);
	memset(&InternetAddr, 0, sizeof(InternetAddr));
    InternetAddr.sin_family = AF_INET;
    InternetAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    InternetAddr.sin_port = htons(clientPort);
    if(bind(clientListenSocket, (PSOCKADDR) &InternetAddr, sizeof(InternetAddr)) == SOCKET_ERROR)
	{
        printf("bind error. %d\n", ::WSAGetLastError());
        return false;
	}
    HANDLE acceptThread = (HANDLE)::_beginthreadex(NULL, 0, AcceptThreadProc, &clientListenSocket, 0, &threadID);
    printf("Client connection accept thread started.\n");

    // Wait for server thread
    ::WaitForSingleObject(serverThread, INFINITE);

    // Close accept thread
    ::closesocket(clientListenSocket);
    ::WaitForSingleObject(acceptThread, INFINITE);

    // Close all connected socket
    ::closesocket(serverThreadParameter.me);
    for( list<SOCKET>::iterator it = serverThreadParameter.clients.begin();
            it != serverThreadParameter.clients.end();
            ++it )
    {
        ::closesocket(*it);
    }

    ::DeleteCriticalSection(&g_cs);
    ::WSACleanup();
	return 0;
}

