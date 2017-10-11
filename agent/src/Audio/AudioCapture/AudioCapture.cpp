#include "stdafx.h"
#include "AudioCapture.h"

AudioCapture::AudioCapture(
        AudioCaptureMgr::PIO_CALLBACK pCallback,
		void* pCallbackParameter,
		int nWaveInDeviceId)
{
    m_pCallback = pCallback;
    m_pCallbackParameter = pCallbackParameter;
	m_nWaveInDeviceId = nWaveInDeviceId;

    m_hAudioIn = 0;
    m_threadId = 0;
    m_hIn = 0;
    m_pHdr = 0;
}

AudioCapture:: ~AudioCapture()
{
    // Test if audio thread stoped
    _ASSERTE( m_hAudioIn == 0 );

    // Test if wavein device closed
    _ASSERTE( m_hIn == 0);

    // Test if buffer is cleared
    _ASSERTE( m_pHdr == 0 );

}

unsigned int WINAPI AudioCapture::AudioInThreadProc(LPVOID lpParameter)
{	
	AudioCapture* _this=(AudioCapture*)lpParameter;	

	MSG msg;
	while(GetMessage(&msg,0,0,0))
	{
		switch(msg.message)
		{
            case WIM_DATA:
                // Unprepare the buffer
                WAVEHDR* pWH=(WAVEHDR*)msg.lParam;
                waveInUnprepareHeader((HWAVEIN)msg.wParam, pWH, sizeof(WAVEHDR));

                // Check the buffer and call callback
                if ( pWH->dwBytesRecorded == AudioCommon::SIZE_AUDIO_FRAME)
                {
                    (*_this->m_pCallback)(_this->m_pCallbackParameter, pWH);
                }

                // Add Buffer again
                waveInPrepareHeader((HWAVEIN)msg.wParam,pWH,sizeof(WAVEHDR));
                waveInAddBuffer((HWAVEIN)msg.wParam,pWH,sizeof(WAVEHDR));

                break;
		}
	}

	return 0;
}

bool AudioCapture::startCapture()
{
	//创建接收声音数据的线程
	m_hAudioIn = (HANDLE)::_beginthreadex(0, 0, AudioInThreadProc, this, 0, &m_threadId);
    if (!m_hAudioIn)
    {
        _RPTF0(_CRT_WARN, "Can't create audio thread.\n");
        return false;
    }
	::SetThreadPriority(m_hAudioIn, THREAD_PRIORITY_HIGHEST);

    // 打开声音设备
	MMRESULT mmr;
    mmr=waveInOpen(0, m_nWaveInDeviceId, &AudioCommon::format, 0, 0, WAVE_FORMAT_QUERY);
	if (mmr)
	{
        _RPTF0(_CRT_WARN, "Unsupported format for waveIn device\n");
        return false;
	}

    mmr=waveInOpen(&m_hIn, m_nWaveInDeviceId, &AudioCommon::format, m_threadId, 0, CALLBACK_THREAD);
	if (mmr)
	{		
        _RPTF0(_CRT_WARN, "waveInOpen failed.\n");
        return false;
	}

    // 准备缓冲区
    if( !this->allocBuffer() )
    {
        _RPTF0(_CRT_WARN, "allocBuffer failed.\n");
        return false;
    }

    // 开始声音捕捉
	mmr = waveInStart(m_hIn);
	if (mmr)
    {
        _RPTF0(_CRT_WARN, "waveInStart failed.\n");
        return false;
    }

    return true;
}

bool AudioCapture::stopCapture()
{
	// 退出声音播放线程
	if (m_hAudioIn)
	{
        PostThreadMessage( m_threadId, WM_QUIT, 0, 0);
        ::WaitForSingleObject(m_hAudioIn, INFINITE);
		m_hAudioIn = 0;
        m_threadId = 0;
	}

    // 标记所有的缓冲区已经就绪
   	waveInReset(m_hIn);

    // 释放缓冲区
    this->freeBuffer();    

    // 关闭设备
	waveInClose(m_hIn);
    m_hIn = 0;

    return true;
}

bool AudioCapture::allocBuffer()
{			
	if (m_pHdr == NULL)
	{
		m_pHdr = new WAVEHDR[NUM_BUF];
	}	
		
	MMRESULT mmr;
	for(UINT i=0;i<NUM_BUF;i++)
	{
		ZeroMemory(&m_pHdr[i], sizeof(WAVEHDR));
        m_pHdr[i].lpData = new char[AudioCommon::SIZE_AUDIO_FRAME];
		m_pHdr[i].dwBufferLength = AudioCommon::SIZE_AUDIO_FRAME;
		mmr = waveInPrepareHeader(m_hIn, &m_pHdr[i], sizeof(WAVEHDR));
		if (mmr)		
			return false;
		
		mmr = waveInAddBuffer(m_hIn, &m_pHdr[i], sizeof(WAVEHDR));
		if (mmr)
			return false;
	}
	
	return true;
}

bool AudioCapture::freeBuffer()
{	
	if (!m_pHdr)
		return FALSE;

	for(UINT i=0; i < NUM_BUF; i++)
	{
		waveInUnprepareHeader(m_hIn, &m_pHdr[i], sizeof(WAVEHDR));
		if (m_pHdr[i].lpData)
		{
			delete []m_pHdr[i].lpData;
			m_pHdr[i].lpData = NULL;
		}
	}
	delete []m_pHdr;
	m_pHdr = NULL;
	
	return true;
}

