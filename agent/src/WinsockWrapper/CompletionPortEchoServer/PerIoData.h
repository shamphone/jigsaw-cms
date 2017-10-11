#pragma once

#define BUFFER_SIZE 4096

enum IO_OPERATION 
{
    IoRead,    //WSARecv/recv/ReadFile
    IoWrite,   //WSASend/send/WriteFile
	IoAccept,  //Accept
};

struct PER_IO_DATA
{
	WSAOVERLAPPED              ol;
	char                       szBuffer[BUFFER_SIZE];
	WSABUF                     wsaBuffer;
	IO_OPERATION               IoOperation;

    PER_IO_DATA()
    {
        memset(this, 0, sizeof(PER_IO_DATA));
        wsaBuffer.buf = szBuffer;
        wsaBuffer.len = BUFFER_SIZE;
    }
};

