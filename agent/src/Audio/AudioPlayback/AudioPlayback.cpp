#include "stdafx.h"
#include "AudioPlayback.h"
#include "../AudioCommon/AudioCommon.h"
#include "../../Common/Common/Log/Log.h"

AudioPlayback::AudioPlayback()
{
    this->m_hOut = 0;
    this->m_AudioPlaybackId = 0;
    this->m_hAudioPlayback = 0;
}

AudioPlayback::~AudioPlayback()
{
    _ASSERTE(m_hOut == 0);
    _ASSERTE(m_AudioPlaybackId == 0);
    _ASSERTE(m_hAudioPlayback == 0);
}

bool AudioPlayback::create(int nDeviceId)
{
    // Create thread
	m_hAudioPlayback = (HANDLE)::_beginthreadex(0, 0, AudioPlaybackThreadProc, this, 0, &m_AudioPlaybackId);
    if (!m_hAudioPlayback)
    {
        _RPTF0(_CRT_WARN, "Can't create audio thread.\n");
        return false;
    }

    // Open the device
	MMRESULT mmr;
    mmr=waveOutOpen(0, nDeviceId, &AudioCommon::format, 0, 0, WAVE_FORMAT_QUERY);
	if (mmr)
	{
        _RPTF0(_CRT_WARN, "Unsupported format for waveOut device. \n");
        return false;
	}
	mmr=waveOutOpen(&m_hOut, nDeviceId, &AudioCommon::format, m_AudioPlaybackId, 0, CALLBACK_THREAD);
	if (mmr)
    {
        _RPTF0(_CRT_WARN, "waveOutOpen failed. \n");
        return false;
    }

    m_nPlayingBufferCount = 0;

    return true;
}

void AudioPlayback::destroy()
{
    // Windows wave API are problematic, althouth we use thread, but
    // if there are many FvsAudioPlay.exe running, deadlock happens
    // occasionally, I use a Sleep call to give windows time to 
	// correct the internal status, and won't worry this problem anymore.

    // Close the device
    if( this->m_hOut)
    {
        ::waveOutReset(this->m_hOut);
    }

    // Stop the thread
	if (m_hAudioPlayback)
	{
        PostThreadMessage( m_AudioPlaybackId, WM_QUIT, 0, 0);
        ::WaitForSingleObject(m_hAudioPlayback, INFINITE);
		m_hAudioPlayback = 0;
        m_AudioPlaybackId = 0;
	}

	if( this->m_hOut)
	{
		::Sleep(1000);
        ::waveOutClose(this->m_hOut);
        this->m_hOut = 0;
	}

}

int AudioPlayback::getPlayingBufferCount()
{
	return this->m_nPlayingBufferCount;
}

void AudioPlayback::play(char* pAudioBuffer, int len)
{
	LPWAVEHDR pwh=new WAVEHDR();
	if(!pwh)
    {
        return;
    }

	char* p=new char[len];
	if(!p)
    {
        delete pwh;
        return;
    }

    ::CopyMemory(p, pAudioBuffer, len);
    ::ZeroMemory(pwh, sizeof(WAVEHDR));
	pwh->dwBufferLength = len;
	pwh->lpData=p;

	MMRESULT mmr;
	mmr=waveOutPrepareHeader(m_hOut, pwh, sizeof(WAVEHDR));
    if(mmr)
    {
        delete[] p;
        delete pwh;
        return;
    }

	mmr=waveOutWrite(m_hOut,pwh,sizeof(WAVEHDR));
	if(mmr)
    {
        delete[] p;
        delete pwh;
    }

    // 增加正在播放的缓冲区块数
    ::InterlockedIncrement(&(this->m_nPlayingBufferCount));

    return;
}

unsigned int WINAPI AudioPlayback::AudioPlaybackThreadProc(LPVOID lpParameter)
{	
	AudioPlayback* _this=(AudioPlayback*)lpParameter;	

	MSG msg;
	while(GetMessage(&msg,0,0,0))
	{
		switch(msg.message)
		{
            case WOM_DONE:

                // 减少正在播放的缓冲数量
                ::InterlockedDecrement( &(_this->m_nPlayingBufferCount));

                // Unprepare the buffer
                WAVEHDR* pWH=(WAVEHDR*)msg.lParam;
                waveOutUnprepareHeader((HWAVEOUT)msg.wParam, pWH, sizeof(WAVEHDR));

                // 释放内存
                delete[] pWH->lpData;
                delete pWH;

                break;
		}
	}

	return 0;
}
