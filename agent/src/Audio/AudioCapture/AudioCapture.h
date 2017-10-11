/*
 * 声音采集的实现类
 */

#ifndef _AudioCapture_H_
#define _AudioCapture_H_

#include "../AudioCommon/AudioCommon.h"
#include "AudioCaptureMgr.h"

class AudioCapture
{
public:
    AudioCapture(
        AudioCaptureMgr::PIO_CALLBACK pCallback,       // Callback function
		void* pCallbackParameter,                      // Callback parameter
		int nWaveInDeviceId);                          // WaveIn Device Id
    ~AudioCapture();

public:

    // 开始捕捉
	bool startCapture();

    // 停止捕捉
	bool stopCapture();

private:

    //接收声音捕捉消息的线程函数
	static unsigned int WINAPI AudioInThreadProc(LPVOID lpParameter); 

    // 线程句柄与ID
	int m_nWaveInDeviceId;
    HANDLE m_hAudioIn;
    unsigned int m_threadId;

private:

    // 声音缓冲区的个数
    static const NUM_BUF = 8;

    // 声音设备的句柄
	HWAVEIN m_hIn;	

    // 声音录制的缓冲区
	WAVEHDR* m_pHdr;
	bool freeBuffer();
	bool allocBuffer();

   // 回调函数与参数
    void* m_pCallbackParameter;
    AudioCaptureMgr::PIO_CALLBACK m_pCallback;

};

#endif 

