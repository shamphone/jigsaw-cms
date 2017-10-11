#pragma once

class TCPForward;

class TCPUser
{
private:
	static const int BUFFER_SIZE = 8192;

	__int64 m_userId;    // User id
	SOCKET m_socket;     // Socket

	TCPForward* m_pTCPForward;

	// socket read thread handle and function
	HANDLE m_hThread;
	static unsigned int __stdcall ThreadProc(void* pObject);

public:

    TCPUser(__int64 userId, TCPForward* pTCPForward);
    ~TCPUser();

	bool Create();
	void Destroy();

	// 开始 TCP数据的转发
	bool startForward(SOCKET s);

	// 发送数据
	bool sendData(char* buffer, int iLen);
};
