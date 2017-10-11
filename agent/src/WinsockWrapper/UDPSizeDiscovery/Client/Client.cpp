// Client.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "getpot.h"

#define MAXIMUM_PACKET_SIZE 64*1024

char serverIp[64];           // Server IP
int serverPort = 0;          // Server Port
int timeInterval = 0;        // Packet interval, in milli-seconds
int initialPacketSize = 0;   // Initial Packet size
int packetSizeIncrement = 0; // Packet size increment
int maximumPacketSize = 0;   // Maximum packet size

void printUsage()
{
	printf("UDPClient [-h host] [-p port] [-t time] [--initial-packet-size size] [--packet-size-increment inc-size] [--maximum-packet-size max-size] [--help]\n");
    printf("  -h host: server ip address, default to 127.0.0.1.\n");
    printf("  -p port: server port, default to 4000.\n");
    printf("  -t time: packet interval in milli-seconds. default to 100.\n");
    printf("  --initial-packet-size size: initial packet size in bytes. default to 100.\n");
    printf("  --packet-size-increment inc-size: packet size increment in bytes. default to 100.\n");
    printf("  --maximum-packet-size max-size: maximum packet size in bytes. default to 64k.\n");
    printf("  --help: print this message.\n");
    return;
}

bool parseCommandLine(int argc, char** argv)
{
    GetPot cl(argc, argv);

    // help?
    if( cl.search("--help"))
    {
        return false;
    }

    // get host
    const char* pServerAddr = cl.follow("127.0.0.1", "-h");
	strncpy(serverIp, pServerAddr, sizeof(serverIp));

    // get port
    serverPort = cl.follow(4000, "-p");

    // get time interval
    timeInterval = cl.follow(100, "-t");

    // get inital-packet-size
    initialPacketSize = cl.follow(100, "--initial-packet-size");

    // get packet-size-increment
    packetSizeIncrement = cl.follow(100, "--packet-size-increment");

    // get maximum packet size
    maximumPacketSize = cl.follow(MAXIMUM_PACKET_SIZE, "maximum-packet-size");
    if( maximumPacketSize <=0 || maximumPacketSize > MAXIMUM_PACKET_SIZE )
    {
        fprintf(stderr, "Invalid maximum packet size.\n");
        return false;
    }

    printf("serverIp = %s\n", serverIp);
    printf("serverPort = %i\n", serverPort);
    printf("timeInterval = %i\n", timeInterval);
    printf("initialPacketSize = %i\n", initialPacketSize);
    printf("packetSizeIncrement = %i\n", packetSizeIncrement);
    printf("maximumPacketSize = %i\n", maximumPacketSize);
    return true;
}

int _tmain(int argc, _TCHAR* argv[])
{
    char buffer[MAXIMUM_PACKET_SIZE];

    if( !parseCommandLine(argc, argv) )
    {
        printUsage();
        return 0;
    }

	WSADATA wsaData;
	::WSAStartup(MAKEWORD(2,2), &wsaData);

	SOCKADDR_IN serverAddr;
	::memset(&serverAddr, 0, sizeof(SOCKADDR_IN));
	serverAddr.sin_family=AF_INET;
    serverAddr.sin_addr.s_addr=::inet_addr(serverIp);
	serverAddr.sin_port=htons(serverPort);

	SOCKET  s = socket(AF_INET, SOCK_DGRAM, 0);
    if( s < 0 )
    {
        fprintf(stderr, "socket error: %i\n", ::WSAGetLastError());
        return -1;
    }

	int packetCount = 1;
	int packetSize = initialPacketSize;
	while(packetSize < maximumPacketSize)
	{
		fprintf(stdout, "%10i: Sending packet size = %i\n", packetCount, packetSize);
		int ret = ::sendto(s, buffer, packetSize, 0, (SOCKADDR*)&serverAddr, sizeof(serverAddr));
		if( ret == SOCKET_ERROR )
		{
			fprintf(stderr, "sendto error: %i\n", ::WSAGetLastError());
			break;
		}
		::Sleep(timeInterval);
		packetCount ++;
		packetSize += packetSizeIncrement;
	}

	::closesocket(s);
	::WSACleanup();
	return 0;
}

