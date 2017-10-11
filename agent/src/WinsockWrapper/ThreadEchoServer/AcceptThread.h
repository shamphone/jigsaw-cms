#pragma once

class AcceptThread
{
private:

	// Listen socket
    SOCKET m_ListenSocket;

	// Accept thread
    HANDLE m_hThread;

	// Accept thread function
	static unsigned int __stdcall AcceptThreadProc(void* pObject);

    // Echo thread function
    static unsigned int __stdcall EchoThreadProc(void* pObject);

public:
	AcceptThread();
	~AcceptThread();

public:
	BOOL start(int port);
	void stop();
};

