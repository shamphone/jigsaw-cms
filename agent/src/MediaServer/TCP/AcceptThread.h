#pragma once

class TCPForward;

class AcceptThread
{
private:

	// Listen socket
    SOCKET m_ListenSocket;

	// Accept thread
    HANDLE m_hThread;

	// Accept thread function
	static unsigned int __stdcall ThreadProc(void* pObject);

	// TCP Forward object
	TCPForward* m_pTCPForward;

	// Authenticate connection
	// A connection should send his userId in 5 seconds, otherwise
	// it will be closed.
	static __int64 authenticateConnection(SOCKET s);

public:
	AcceptThread(TCPForward* pTCPForward);
	~AcceptThread();

public:
	BOOL start(int port);
	void stop();
};
