#include <winsock2.h>
#include <windows.h>

int main()
{
    // Init winsock
    WSADATA wsaData;
    if( ::WSAStartup(MAKEWORD(2, 2), &wsaData) != 0 )
    {
        return FALSE;
    }

    // Create the listen socket
    SOCKET m_ListenSocket = socket(AF_INET, SOCK_STREAM, 0);
    if( m_ListenSocket == INVALID_SOCKET )
    {
        ::WSACleanup();
        return FALSE;
    }

    // bind to local addr
	int port = 5000;
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

    // accept
	SOCKET newSocket = accept(m_ListenSocket, 0, 0);
	::closesocket(m_ListenSocket);
	
	char buffer[1024];
	::ZeroMemory(buffer, 1024);
	::send(newSocket, buffer, 10, 0);
	::send(newSocket, buffer, 10, 0);
	::send(newSocket, buffer, 10, 0);
	::send(newSocket, buffer, 10, 0);
	::send(newSocket, buffer, 10, 0);
	::closesocket(newSocket);

	::WSACleanup();
    return TRUE;
}
