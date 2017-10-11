// Server.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"

const int BUFFER_SIZE = 64 * 1024;
char buffer[BUFFER_SIZE];

int _tmain(int argc, _TCHAR* argv[])
{
	if( argc != 1 && argc != 2)
	{
		fprintf(stderr, "Usage: %s [listen-port]\n", argv[0]);
		return 0;
	}

	int listenPort = 4000;
	if( argc == 2 )
	{
		int tempPort = atoi(argv[1]);
		if( tempPort != 0 )
		{
			listenPort = tempPort;
		}
		else
		{
			fprintf(stderr, "Invalid port number %s\n", argv[1]);
			return 0;
		}
	}

	WSADATA wsaData;
	::WSAStartup(MAKEWORD(2,2), &wsaData);

	SOCKADDR_IN serverAddr;
	::memset(&serverAddr, 0, sizeof(SOCKADDR_IN));
	serverAddr.sin_family=AF_INET;
    serverAddr.sin_addr.s_addr=::htonl(INADDR_ANY);
	serverAddr.sin_port=htons(listenPort);

	SOCKET  s = socket(AF_INET, SOCK_DGRAM, 0);
	int ret = ::bind(s, (SOCKADDR*)&serverAddr, sizeof(serverAddr));
	if( ret == SOCKET_ERROR)
	{
		fprintf(stderr, "bind error: %d\n", ::WSAGetLastError());
		::closesocket(s);
		::WSACleanup();
		return 0;
	}

	SOCKADDR_IN clientAddr;
	::memset(&clientAddr, 0, sizeof(SOCKADDR_IN));
	int clientAddrLen = sizeof(SOCKADDR_IN);

	int packetCount = 0;
	while(true)
	{
		ret = ::recvfrom(s, buffer, BUFFER_SIZE, 0, (SOCKADDR*)&clientAddr, &clientAddrLen);
		if( ret == 0 || ret == SOCKET_ERROR)
		{
			fprintf(stderr, "recvfrom error: %d\n", ::WSAGetLastError());
			break;
		}

		packetCount ++;
		fprintf(stdout, "%10i: Receive %i bytes from %s\n", packetCount, ret, inet_ntoa(clientAddr.sin_addr));
	}

	::WSACleanup();
	return 0;
}

