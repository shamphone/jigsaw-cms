
#include "stdafx.h"

int _tmain(int argc, _TCHAR* argv[])
{
    char* pHost = "127.0.0.1";
    short port = 5000;

    if( argc > 1 )
    {
        pHost = argv[1];
    }

    if( argc > 2 )
    {
        port = atoi(argv[2]);
    }

    if( argc > 3 )
    {
        printf("Usage: SocketClient [ip] [port]\n");
        return 0;
    }

	WSAData wsaData;
	::WSAStartup(MAKEWORD(2,2), &wsaData);

	SOCKET s = ::socket(AF_INET, SOCK_STREAM, 0);
	if( s == INVALID_SOCKET )
	{
		printf("Can't create socket.\n");
		goto end;
	}

	SOCKADDR_IN addr;
    addr.sin_family=AF_INET;
    addr.sin_addr.s_addr=inet_addr(pHost);
    addr.sin_port=htons(port);
	if( ::connect(s, (sockaddr*)&addr, sizeof(addr)) == SOCKET_ERROR) {
		printf("Can't connect server.\n");
		goto end;
	}

	const int bufferSize = 1024;
	const int count = 100000;
	char buffer[bufferSize];
    memset(buffer, 0, bufferSize);

	time_t time1 = time(NULL);
	for( int i=0; i<count; i++ )
	{
		send(s, buffer, bufferSize, 0);
		recv(s, buffer, bufferSize, 0);
	}
	time_t time2 = time(NULL);
	printf("IO finished in %d\n", time2-time1);
	printf("Io for %d times, each packet size is %d\n", count, bufferSize);

end:
	closesocket(s);
	::WSACleanup();
	return 0;
}

