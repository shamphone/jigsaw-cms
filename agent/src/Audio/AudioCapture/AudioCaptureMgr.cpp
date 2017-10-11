#include "stdafx.h"
#include "AudioCaptureMgr.h"
#include "AudioCapture.h"
#include "../AudioCommon/AudioCommon.h"
#include "../../TimeFileReader/TimeFileReader.h"

static AudioCapture* pInstance = NULL;
static CRITICAL_SECTION m_cs;
static int m_count = 0;

static bool m_useFile = false;
static char m_filename[MAX_PATH];
static TimeFileReader* pTimeFileReader = NULL;
static AudioCaptureMgr::PIO_CALLBACK m_OriginalCallback = NULL;
static WAVEHDR waveHeader;

static void CallbackStub(void* pObject, char* pBuffer, int nLength)
{
    waveHeader.lpData = pBuffer;
    (*m_OriginalCallback)(pObject, &waveHeader);
}

bool AudioCaptureMgr::create( PIO_CALLBACK pCallback, void* pCallbackParameter, int nWaveInDeviceId)
{
    _ASSERTE(pCallback != NULL );

    m_useFile = false;

    if( pInstance != NULL )
    {
        return true;
    }
	
	// 检查指定的WaveInDevcieId是否有效，如果无效的话，我们将使用默认设备
    int audioDeviceCount = waveInGetNumDevs();
	if(nWaveInDeviceId >= audioDeviceCount)
	{
		nWaveInDeviceId = WAVE_MAPPER;
	}

    pInstance = new AudioCapture(pCallback, pCallbackParameter, nWaveInDeviceId);

    InitializeCriticalSection(&m_cs);
    m_count = 0;
	return true;
}

bool AudioCaptureMgr::createFromFile(PIO_CALLBACK pCallback, void* pCallbackParameter, char* filename)
{
    _ASSERTE(pCallback != NULL );
    _ASSERTE(filename != NULL );

    // Initialize wave Header
	waveHeader.dwBufferLength = 960;
	waveHeader.dwBytesRecorded = 960;
	waveHeader.dwFlags = 1;
	waveHeader.dwLoops = 0;
	waveHeader.dwUser = 0;
	waveHeader.lpData = 0;
	waveHeader.lpNext = 0;
	waveHeader.reserved = 0;

    // test if the file exist
    if(_access(filename, 0x04) == -1)
    {
        return false;
    }
    memset(m_filename, 0, MAX_PATH);
    strncpy(m_filename, filename, MAX_PATH-1);
    m_useFile = true;

    if( pTimeFileReader != NULL )
    {
        return true;
    }

    m_OriginalCallback = pCallback;
    pTimeFileReader = new TimeFileReader(CallbackStub, pCallbackParameter);

    InitializeCriticalSection(&m_cs);
    m_count = 0;
	return true;
}

void AudioCaptureMgr::destroy()
{
    if( m_useFile )
    {
        delete pTimeFileReader;
        pTimeFileReader = NULL;
    }
    else
    {
        delete pInstance;
        pInstance = NULL;
    }

    _ASSERTE(m_count == 0);
    DeleteCriticalSection(&m_cs);
}

bool AudioCaptureMgr::startCapture()
{
    if( m_useFile )
    {
        _ASSERTE(pTimeFileReader != NULL);
    }
    else
    {
        _ASSERTE(pInstance != NULL);
    }

    EnterCriticalSection(&m_cs);
    m_count ++;

    bool ret;
    if( m_count == 1 )
    {
        if( m_useFile )
        {
            ret = pTimeFileReader->start(
                    m_filename,                    // Test file name
                    44,                            // Wave fileheader length
                    AudioCommon::SIZE_AUDIO_FRAME, // Wave frame file length
                    60);                           // 60ms per frame
        }
        else
        {
            ret = pInstance->startCapture();
        }
    }
    else
    {
        ret = true;
    }
    LeaveCriticalSection(&m_cs);
    return ret;
}

bool AudioCaptureMgr::stopCapture()
{
    if( m_useFile )
    {
        _ASSERTE(pTimeFileReader != NULL);
    }
    else
    {
        _ASSERTE(pInstance != NULL);
    }

    EnterCriticalSection(&m_cs);
    m_count --;

    bool ret = true;
    if( m_count == 0 )
    {
        if( m_useFile )
        {
            pTimeFileReader->stop();
        }
        else
        {
            ret = pInstance->stopCapture();
        }
    }
    else if( m_count > 0 )
    {
        ret = true;
    }
    else
    {
        m_count = 0;
        return false;
    }
    LeaveCriticalSection(&m_cs);
    return ret;
}

