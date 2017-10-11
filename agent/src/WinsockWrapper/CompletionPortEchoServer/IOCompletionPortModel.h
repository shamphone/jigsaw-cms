#pragma once

struct PER_HANDLE_DATA;
struct PER_IO_DATA;

class IOCompletionPortModel
{

private:
    HANDLE m_hIOCP;

    static const int MAX_WORKER_THREAD = 8;
    HANDLE m_hThreads[MAX_WORKER_THREAD];
    int m_threadCount;

	// Critical section for protection of the perHandleDataList and perIODataList
	CRITICAL_SECTION m_cs;

    // Per handle data list
    list<PER_HANDLE_DATA*> m_perHandleDataList;

    // Per Io data list
    list<PER_IO_DATA*> m_perIoDataList;
 
public:

    IOCompletionPortModel();
    ~IOCompletionPortModel();

    static unsigned int __stdcall ServerWorkerThread(void* pObject);

    BOOL start();

    void stop();

    void addSocket(SOCKET s);

private:
    
    void removeSocket(SOCKET s, PER_HANDLE_DATA* pPerHandleData, PER_IO_DATA* pPerIoData);
};

