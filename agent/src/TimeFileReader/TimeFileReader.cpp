#include "stdafx.h"
#include "TimeFileReader.h"

TimeFileReader::TimeFileReader( TIMEFILEREADER_CALLBACK pCallback, void* pCallbackParameter)
{
    m_hStopEvent = 0;
    m_nFrameSize = 0;
    m_nInterval = 0;
    m_hThread = 0;
    m_pFileData = NULL;
    m_nFileDataLength = 0;

    _ASSERTE(pCallback != NULL);
    m_pCallback = pCallback;
    m_pCallbackParameter = pCallbackParameter;
}

TimeFileReader::~TimeFileReader()
{
}

bool TimeFileReader::start(
    char* filename, 
    int headerSize,
    int frameSize,
    int interval)
{
    _ASSERTE(headerSize >= 0);
    _ASSERTE(frameSize >= 0);
    _ASSERTE(interval > 0);

    m_nFrameSize = frameSize;
    m_nInterval = interval;

    // Open the file
    FILE* fp = fopen(filename, "rb");
    if( !fp )
    {
        return false;
    }

    // Make sure the file length equals to headerSize + n * frameSize
    long fileSize = _filelength(_fileno(fp));
    if( fileSize <= headerSize || ((fileSize - headerSize) % frameSize != 0) )
    {
        fclose(fp);
        return false;
    }

    // Read the file data into the buffer
    m_nFileDataLength = fileSize - headerSize;
    m_pFileData = new char[m_nFileDataLength];
    if( m_pFileData == NULL )
    {
        fclose(fp);
        return false;
    }
    fseek(fp, headerSize, SEEK_SET);
    fread(m_pFileData, m_nFileDataLength, 1, fp);
    fclose(fp);

    // Create the event
    m_hStopEvent = CreateEvent(NULL, TRUE, FALSE, NULL);
    if( m_hStopEvent == NULL )
    {
        delete[] m_pFileData;
        m_pFileData = NULL;
        return false;
    }

    // Start the thread
	unsigned int threadID;
	m_hThread=(HANDLE)::_beginthreadex(NULL, 0, TimeFileReader::ReadThreadProc, this, 0, &threadID);
	if (m_hThread == 0)
    {
        ::CloseHandle(m_hStopEvent);
        delete[] m_pFileData;
        m_pFileData = NULL;
        return false;
    }

    return true;
}

void TimeFileReader::stop()
{
    ::SetEvent(m_hStopEvent);

    WaitForSingleObject(m_hThread, INFINITE);

    ::CloseHandle(m_hStopEvent);

    delete[] m_pFileData;
    m_pFileData = NULL;

    return;
}

unsigned int WINAPI TimeFileReader::ReadThreadProc(void* lpParameter)
{
    int ret = -1;

    TimeFileReader* _this = (TimeFileReader*)lpParameter;

	HANDLE hTimer = CreateWaitableTimer(NULL, FALSE, NULL);
	if( hTimer == NULL )
	{
        goto end;
	}

	LARGE_INTEGER liDueTime;
    liDueTime.QuadPart=-10000000;  // Read after 1s
	if(!::SetWaitableTimer(hTimer, &liDueTime, _this->m_nInterval, NULL, NULL, FALSE))
    {
        goto end;
    }

    HANDLE handles[2];
    handles[0] = hTimer;
    handles[1] = _this->m_hStopEvent;

    size_t currentPosition = 0;

    while(true)
    {
        DWORD dwReturn = ::WaitForMultipleObjects(2, handles, FALSE, INFINITE);
        switch(dwReturn)
        {
            case WAIT_FAILED:
                goto end;

            case WAIT_OBJECT_0:

                (*_this->m_pCallback)(
                          _this->m_pCallbackParameter,
                          _this->m_pFileData + currentPosition,
                          _this->m_nFrameSize);

                currentPosition += _this->m_nFrameSize;
                if( currentPosition >= _this->m_nFileDataLength )
                {
                    currentPosition = 0;
                }
                break;

            case WAIT_OBJECT_0+1:
                ret = 0;
                goto end;

            default:
                goto end;
        }
    }

end:

    if( hTimer != NULL )
    {
        ::CloseHandle(hTimer);
    }

    return ret;
}

