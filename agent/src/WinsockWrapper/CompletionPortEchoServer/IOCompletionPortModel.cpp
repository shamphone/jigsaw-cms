#include "stdafx.h"
#include "IOCompletionPortModel.h"
#include "PerHandleData.h"
#include "PerIoData.h"

IOCompletionPortModel::IOCompletionPortModel()
{
    m_hIOCP = NULL;
    m_threadCount = 0;
	::InitializeCriticalSection(&m_cs);
}

IOCompletionPortModel:: ~IOCompletionPortModel()
{
	::DeleteCriticalSection(&m_cs);
}

unsigned int IOCompletionPortModel::ServerWorkerThread(void* pObject)
{
	IOCompletionPortModel* _this = (IOCompletionPortModel*)pObject;

    int ret;
	DWORD dwNumberBytes;
	PER_HANDLE_DATA* pPerHandleData = NULL;
	WSAOVERLAPPED* pOverlapped = NULL;
	BOOL bSuccess;

    while(true)
    {
        bSuccess = GetQueuedCompletionStatus(
            _this->m_hIOCP,
            &dwNumberBytes,
            (PULONG_PTR )&pPerHandleData,
            &pOverlapped,
            INFINITE);

        _RPT1(_CRT_WARN, "Get a completion pack. bSuccess = %d\n", bSuccess);

        // The socket is closed locally or error occured during the IO operation
        if( bSuccess == FALSE )
        {
            _RPT0(_CRT_WARN, "bSuccess == FALSE, removeSocket\n");
            _this->removeSocket(pPerHandleData->m_socket, pPerHandleData, (PER_IO_DATA*)pOverlapped);
            continue;
        }

        // If pPerHandleData is null, so we should quit
        if( pPerHandleData == NULL )
        {
            _RPT0(_CRT_WARN, "pPerHandleData == NULL, break\n");
            break;
        }

        PER_IO_DATA* pPerIoData = (PER_IO_DATA*)pOverlapped;

		// If the tranfer byte count is 0 and last operation is I/O, peer close the socket,
        // so we close the socket, too.
        if( dwNumberBytes == 0 && pPerIoData->IoOperation != IoAccept)
        {
            _RPT0(_CRT_WARN, "dwNumberBytes == 0 && pPerIoData->IoOperation != IoAccept, removeSocket\n");
            _this->removeSocket(pPerHandleData->m_socket, pPerHandleData, (PER_IO_DATA*)pOverlapped);
            continue;
        }

        switch( pPerIoData->IoOperation )
        {
            // If last operation is read, we post a WSASend to the socket, echo the data back
            case IoRead:
                _RPT0(_CRT_WARN, "Get a read completion pack\n");

                pPerIoData->IoOperation = IoWrite;
                pPerIoData->wsaBuffer.len = dwNumberBytes;

                ret = ::WSASend(
                            pPerHandleData->m_socket, 
                            &pPerIoData->wsaBuffer,
                            1,
                            &dwNumberBytes,
                            0,
                            &pPerIoData->ol,
                            NULL);
                if( (ret == SOCKET_ERROR) && (::WSAGetLastError() != WSA_IO_PENDING))
                {
                    _RPT1(_CRT_WARN, "Post WSASend error. %d\n", WSAGetLastError());
                    _this->removeSocket(pPerHandleData->m_socket, pPerHandleData, pPerIoData);
                }
                break;

            // If last operation is write, or this is a new accept socket
            // we post a WSARecv to the socket, read more data
            case IoWrite:
            case IoAccept:
			{
                if( pPerIoData->IoOperation == IoWrite )
                {
                    _RPT0(_CRT_WARN, "Get a write completion pack\n");
                }
                else
                {
                    _RPT0(_CRT_WARN, "Get an accept completion pack\n");
                }

                pPerIoData->IoOperation = IoRead;
                pPerIoData->wsaBuffer.len = BUFFER_SIZE;

                DWORD NumberOfBytesRecvd = 0;
                DWORD flag = 0;
                ret = ::WSARecv(
                        pPerHandleData->m_socket,
                        &pPerIoData->wsaBuffer,
                        1,
                        &NumberOfBytesRecvd,
                        &flag,
                        &pPerIoData->ol,
                        NULL);
                if( (ret == SOCKET_ERROR) && (::WSAGetLastError() != WSA_IO_PENDING))
                {
                    _RPT1(_CRT_WARN, "Post WSARecv error. %d\n", WSAGetLastError());
                    _this->removeSocket(pPerHandleData->m_socket, pPerHandleData, pPerIoData);
                }
                break;
			}

            // Unknow operation code?
            default:
                _RPT0(_CRT_WARN, "Get into hole\n");
                _ASSERTE(FALSE);
                break;
        }

    }

    return 0;
}

BOOL IOCompletionPortModel::start()
{
    // Step 1:
    // Create an I/O completion port
    m_hIOCP = CreateIoCompletionPort(INVALID_HANDLE_VALUE, NULL, 0, 0);
    if( m_hIOCP == NULL ) {
        return FALSE;
    }

    // Step 2:
    // Determine how many processors are on the system
    SYSTEM_INFO SystemInfo;
    GetSystemInfo(&SystemInfo);
    if( SystemInfo.dwNumberOfProcessors > MAX_WORKER_THREAD )
    {
        m_threadCount = MAX_WORKER_THREAD;
    }
    else
    {
        m_threadCount = SystemInfo.dwNumberOfProcessors;
    }

    // Step 3:
    // Create worker threads based on the number of
    // processors available on the system. For this
    // simple case, we create one worker thread for each
    // processor.
    for(int i = 0; i < m_threadCount; i++)
    {
        // Create a server worker thread, and pass the
        // completion port to the thread.
        unsigned int threadId;
        m_hThreads[i] = (HANDLE)_beginthreadex(NULL, 0, ServerWorkerThread, this, 0, &threadId);
    }

    // Return the handle
    return TRUE;
}

void IOCompletionPortModel::stop()
{
    // Stop every worker thread
	// Place this step before the next, so the workthread won't take action to locally close socket.
    PostQueuedCompletionStatus(m_hIOCP, 0, NULL, NULL);
    for( int i=0; i<m_threadCount; i++)
    {
        WaitForSingleObject(m_hThreads[i], INFINITE);
        CloseHandle(m_hThreads[i]);
    }

    // Close every socket
    list<PER_HANDLE_DATA*>::iterator it;
    for(it = m_perHandleDataList.begin(); it != m_perHandleDataList.end(); it++ )
    {
        ::closesocket((*it)->m_socket);
        delete (*it);
    }
    m_perHandleDataList.clear();

    // Clear per io data list
    list<PER_IO_DATA*>::iterator it2;
    for( it2 = m_perIoDataList.begin(); it2 != m_perIoDataList.end(); it2++)
    {
        delete (*it2);
    }
    m_perIoDataList.clear();

    ::CloseHandle(m_hIOCP);
}

void IOCompletionPortModel::addSocket(SOCKET s)
{
    _RPT0(_CRT_WARN, "AddSocket\n");

    // Allocate per handle data for this socket
    PER_HANDLE_DATA* pPerHandleData = new PER_HANDLE_DATA();
    pPerHandleData->m_socket = s;

    // Associate the accepted socket with the completion port
    if( CreateIoCompletionPort((HANDLE)s, m_hIOCP, (ULONG_PTR)pPerHandleData, 0) == NULL )
    {
        delete pPerHandleData;
        ::closesocket(s);
        return;
    }

    // Allocate per io data for this socket
    PER_IO_DATA* pPerIoData = new PER_IO_DATA();
    pPerIoData->IoOperation = IoAccept;

	// Add data for this socket to list
	::EnterCriticalSection(&m_cs);
    m_perHandleDataList.push_back(pPerHandleData);
    m_perIoDataList.push_back(pPerIoData);
	::LeaveCriticalSection(&m_cs);

    // Post a completion pack to the IOCP, so the workthread will post a 
    // WSARecv call on the socket.
    if(!::PostQueuedCompletionStatus(m_hIOCP, 0, (ULONG_PTR)pPerHandleData, &pPerIoData->ol))
    {
        this->removeSocket(pPerHandleData->m_socket, pPerHandleData, pPerIoData);
    }
}

void IOCompletionPortModel::removeSocket(SOCKET s, PER_HANDLE_DATA* pPerHandleData, PER_IO_DATA* pPerIoData)
{
	::EnterCriticalSection(&m_cs);
    m_perHandleDataList.remove(pPerHandleData);
    m_perIoDataList.remove(pPerIoData);
	::LeaveCriticalSection(&m_cs);

	::closesocket(s);
    delete pPerHandleData;
    delete pPerIoData;
}

