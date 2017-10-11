#pragma once

class IOCompletionPortModel;

class AcceptThread
{
private:

	// Listen socket
    SOCKET m_ListenSocket;

	// Accept thread
    HANDLE m_hThread;

	// Accept thread function
	static unsigned int __stdcall ThreadProc(void* pObject);

	// IOCompletionPort model
	IOCompletionPortModel* m_pIOCP;

public:
	AcceptThread();
	~AcceptThread();

public:
	BOOL start(IOCompletionPortModel* pIOCP, int port);
	void stop();
};
