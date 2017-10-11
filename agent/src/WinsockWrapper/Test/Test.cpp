// Test.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "..\WinsockWrapper\UDPSocket.h"
#include "..\WinsockWrapper\TCPSocket.h"

void testUDPSocket()
{
	WSADATA wsaData;
	int err = ::WSAStartup(MAKEWORD(2,2), &wsaData);

	if ( err != 0 ) 
	{
		return;
	}
 
	CUDPSocket sock;
	sock.SetRemoteAddr("61.189.170.218", 5000);

    // Create UDPSocket
    if (!sock.Create())
    {
		return;
    }
    sock.SetBuffer(60000);

	char buf[60000];
	int nLen = 100;
	while(true)
	{
		for (int i=0; i<10; i++)
		{
			sock.SendTo(buf, nLen);
			//Sleep(1000);
		}
		nLen = nLen + 100;
	}
	sock.Destroy();

	::WSACleanup();
}

void testReceiveBlock()
{
	WSADATA wsaData;
	int err = ::WSAStartup(MAKEWORD(2,2), &wsaData);

	if ( err != 0 ) 
	{
		return;
	}

	CTCPSocket tcpSocket;
	tcpSocket.Create();
	tcpSocket.Connect(5000, "127.0.0.1");

	char buffer[1000];
	tcpSocket.ReceiveBlock(buffer, 40);
	tcpSocket.ReceiveBlock(buffer, 40);

	tcpSocket.Destroy();

	::WSACleanup();
	return;
}

int _tmain(int argc, _TCHAR* argv[])
{
	testReceiveBlock();
	return 0;
}

