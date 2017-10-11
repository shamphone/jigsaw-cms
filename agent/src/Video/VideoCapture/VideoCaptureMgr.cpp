#include "stdafx.h"
#include "VideoCaptureMgr.h"
#include "VideoCapture.h"
#include "../../Common/Common/Log/Log.h"
#include "../../TimeFileReader/TimeFileReader.h"
#include "..\VideoConfig\VideoConfig.h"

// Video Device
CVideoCapture* VideoCaptureMgr::m_pVideoDevice = NULL;

// Callback data
VideoCaptureMgr::PIO_CALLBACK VideoCaptureMgr::m_pCallback;

// Call count control
static CRITICAL_SECTION m_cs;
static int m_count = 0;

// Event
const char* VideoCaptureMgr::m_eventName = "FlvccVideoCaptureMgrEvent";
HANDLE VideoCaptureMgr::m_hEvent = NULL;

// Default Video Header
static VIDEOHDR m_VideoHeader;

// Data for file video simulation
static bool m_useFile = false;
static char m_filename[MAX_PATH];
static TimeFileReader* m_pTimeFileReader = NULL;

void VideoCaptureMgr::initCommon()
{
    // Initialize the default header
	m_VideoHeader.dwBufferLength = (VideoConfig::getWidth() * VideoConfig::getHeight() * VideoConfig::DEFAULT_BIT)/8;
	m_VideoHeader.dwBytesUsed = (VideoConfig::getWidth() * VideoConfig::getHeight() * VideoConfig::DEFAULT_BIT)/8;
	m_VideoHeader.dwFlags = 0x03;
	m_VideoHeader.dwTimeCaptured = 0;
	m_VideoHeader.dwUser = 0;
	m_VideoHeader.lpData = NULL;

    // Init critical section
	InitializeCriticalSection(&m_cs);
}

BOOL VideoCaptureMgr::create( PIO_CALLBACK pCallback, void* pCallbackParameter, const char* devicename)
{
    _ASSERTE(pCallback != NULL);
    _ASSERTE(m_pVideoDevice == NULL);

    m_useFile = false;

	// Test if another videoCaptureMgr is running
	// If OpenEvent successful, another process has created it,
	// So we return. Otherwise we create this event
	m_hEvent = ::OpenEvent(EVENT_ALL_ACCESS, FALSE, m_eventName);
	if( m_hEvent != NULL )
	{
		::CloseHandle(m_hEvent);
		m_hEvent = NULL;
		return FALSE;
	}
	else
	{
		m_hEvent = ::CreateEvent(NULL, TRUE, TRUE, VideoCaptureMgr::m_eventName);
	}

	m_pVideoDevice = new CVideoCapture(VideoCaptureMgr::CallbackStub, pCallbackParameter);
    if(!m_pVideoDevice->Create(devicename))
    {
        delete m_pVideoDevice;
        m_pVideoDevice = NULL;

		::CloseHandle(m_hEvent);
		m_hEvent = NULL;
        return FALSE;
    }

    // Save call back
	// We don't save parameter, it is saved in the down layer.
    m_pCallback = pCallback;

    // Init Common
    initCommon();

    return TRUE;
}

BOOL VideoCaptureMgr::createFromFile( PIO_CALLBACK pCallback, void* pCallbackParameter, const char* filename)
{
    _ASSERTE(filename != NULL);
    _ASSERTE(pCallback != NULL);
    _ASSERTE(m_pTimeFileReader == NULL);

    // test if the file exist
    if(_access(filename, 0x04) == -1)
    {
        return false;
    }
    memset(m_filename, 0, MAX_PATH);
    strncpy(m_filename, filename, MAX_PATH-1);
    m_useFile = true;

    m_pTimeFileReader = new TimeFileReader(VideoCaptureMgr::CallbackStub, pCallbackParameter);

    // Save call back
	// We don't save parameter, it is saved in the down layer.
    m_pCallback = pCallback;

    // Init Common
    initCommon();

    // Default frame rate for file data is 5frame/s
    VideoConfig::setFrameRate(5);

	return TRUE;
}

void VideoCaptureMgr::destroy()
{
    if( m_useFile )
    {
        delete m_pTimeFileReader;
        m_pTimeFileReader = NULL;
    }
    else
    {
		::CloseHandle(m_hEvent);
		m_hEvent = NULL;

        m_pVideoDevice->Destroy();
        delete m_pVideoDevice;
        m_pVideoDevice = NULL;
    }

	DeleteCriticalSection(&m_cs);
}

void VideoCaptureMgr::CallbackStub(void* pObject, char* pBuffer, int nLength)
{
    m_VideoHeader.lpData = (unsigned char*)pBuffer;
    (*m_pCallback)(pObject, &m_VideoHeader);
}

BOOL VideoCaptureMgr::startCapture()
{
	EnterCriticalSection(&m_cs);	
    m_count++;
	LeaveCriticalSection(&m_cs);

    if( m_useFile )
    {
        _ASSERTE(m_pTimeFileReader != NULL);
        if( m_count == 1 )
        {
            return m_pTimeFileReader->start(
                    m_filename,                    // Test file name
                    0,                             // video fileheader length
                    76032,                         // video frame length ( 176*144*3 )
                    200);                          // 200ms per frame
        }
        return TRUE;
    }
    else
    {
        _ASSERTE(m_pVideoDevice != NULL);
        if( m_count == 1 )
        {
            return m_pVideoDevice->Start();
        }
        return TRUE;
    }

}

void VideoCaptureMgr::stopCapture()
{
	EnterCriticalSection(&m_cs);	
    m_count--;
	LeaveCriticalSection(&m_cs);	

    if( m_useFile )
    {
        _ASSERTE(m_pTimeFileReader != NULL);
        if( m_count == 0 )
        {
            m_pTimeFileReader->stop();
        }
    }
    else
    {
        _ASSERTE(m_pVideoDevice != NULL);
        if( m_count == 0 )
        {
            return m_pVideoDevice->Stop();
        }
    }

}

void VideoCaptureMgr::showDialog(HWND hParentWnd)
{
	if(m_useFile)
	{
		return;
	}

	m_pVideoDevice->ShowDialog(hParentWnd);
}

bool VideoCaptureMgr::getDevices(list<CVideoCaptureDevice>& deviceList)
{
    return CVideoCapture::getDevices(deviceList);
}
