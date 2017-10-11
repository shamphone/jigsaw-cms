#include "stdafx.h"
#include "SocketUtil.h"

BOOL sendBlock(SOCKET sockSend, const char* buffer, int bytes)
{
	DWORD byteSent = 0;
	DWORD ret;

	while ( bytes - byteSent > 0 )
	{
		ret = ::send(sockSend, buffer + byteSent, bytes - byteSent, 0);
		if( ret == SOCKET_ERROR )
		{
            printf("send error. %d\n", ::WSAGetLastError());
			return FALSE;
		}
		byteSent += ret;
	}
	return TRUE;
}
