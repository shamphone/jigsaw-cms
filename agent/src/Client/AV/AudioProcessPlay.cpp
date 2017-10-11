#include "stdafx.h"
#include "Flvcc.h"
#include "AudioProcessPlay.h"
#include "Audio/AudioCommon/AudioCommon.h"
#include "common/common/log/log.h"
#include "FvsAudioPlay/FileMappingFlag.h"

AudioProcessPlay::AudioProcessPlay()
{
    memset((char*)&m_processInfo, 0, sizeof(PROCESS_INFORMATION));
    m_hEvent = 0;
    m_pFileMapView = NULL;
    m_hFileMap = 0;
}

AudioProcessPlay::~AudioProcessPlay()
{
}

BOOL AudioProcessPlay::create(string name)
{
    this->m_name = name;

    // Create File mapping
    string fileMappingName = m_name + "_FileMapping";
    int fileMappingSize = AudioCommon::SIZE_AUDIO_FRAME + 16;
    m_hFileMap = CreateFileMapping(
            INVALID_HANDLE_VALUE, 
            NULL, 
            PAGE_READWRITE, 
            0, 
            fileMappingSize,
            fileMappingName.c_str());

    if (m_hFileMap == NULL)
    {
        return FALSE;        
    }

    m_pFileMapView = MapViewOfFile(
            m_hFileMap,
            FILE_MAP_READ | FILE_MAP_WRITE, 0, 0, 0);

    if (m_pFileMapView == NULL)
    {
        CloseHandle(m_hFileMap);
        m_hFileMap = 0;
        return FALSE;
    }

    // Create event
    string eventName = m_name + "_Event";
    m_hEvent = CreateEvent( NULL, TRUE, FALSE, eventName.c_str());
    if (m_hEvent == 0)
    {
        return FALSE;
    }

	// Read the AudioPlay Device index
	// 如果配置的设备下标无效，我们将使用默认设备
	const char* iniFilename = ::GetApp()->getIniFilename();
	int nDeviceId = ::GetPrivateProfileInt("MEDIA_CONFIG", "audio_play_device", WAVE_MAPPER, iniFilename);
    int audioDeviceCount = waveOutGetNumDevs();
	if( nDeviceId >= audioDeviceCount )
	{
		nDeviceId = WAVE_MAPPER;
	}

	//启动进程
    STARTUPINFO si = { sizeof(si) };            
    string cmdLine = "FvsAudioPlay.exe " + fileMappingName + " " + eventName;
    char buffer[1024];
    _snprintf(buffer, 1024, "%s %d %d %d", cmdLine.c_str(), fileMappingSize, ::GetCurrentProcessId(), nDeviceId);
    if (CreateProcess(NULL, buffer, NULL, NULL, FALSE, 0, NULL, NULL, &si, &this->m_processInfo)) 
    {
        CloseHandle(this->m_processInfo.hProcess);
        CloseHandle(this->m_processInfo.hThread);            
    }

    return TRUE;
}

void AudioProcessPlay::destroy()
{        
    //通知声音播放线程退出
    if( m_pFileMapView != 0 && m_hEvent !=0 )
    {
        DWORD* pFlag = (DWORD*)m_pFileMapView;
        *pFlag = FileMappingFlag::STOP;
        SetEvent(m_hEvent);
    }

    if (m_pFileMapView != NULL)
    {    
        UnmapViewOfFile(m_pFileMapView);
        m_pFileMapView = NULL;
    }

    if (m_hFileMap)
    {
        CloseHandle(m_hFileMap);
        m_hFileMap = 0;
    }

    if (m_hEvent)
    {            
        CloseHandle(m_hEvent);
        m_hEvent = 0;
    }
};

BOOL AudioProcessPlay::play(PACK_AUDIO *ppa)
{
    ASSERT(m_pFileMapView != NULL);

    // Put flag
    DWORD* pFlag = (DWORD*)m_pFileMapView;
    *pFlag = FileMappingFlag::NEW_DATA;

    //Put Encoded data to buffer
    char* pData = (char*)m_pFileMapView + sizeof(DWORD);
    memcpy(pData, ppa, sizeof(PACK_AUDIO) + ppa->data_size);

    // 通知播放进程
    SetEvent(m_hEvent);

    return TRUE;
}

