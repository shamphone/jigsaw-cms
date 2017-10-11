#pragma once

class AudioPlayback
{
public:

    // Constructor
    AudioPlayback();

    // Destructor
    ~AudioPlayback();

    // create/destroy
    bool create(int nDeviceId = WAVE_MAPPER);
    void destroy();

    // Play audio data
    void play(char* pAudioBuffer, int len);

	// Return buffer count which are being played.
	int getPlayingBufferCount();

private:

    //接收声音播放消息的线程函数
	static unsigned int WINAPI AudioPlaybackThreadProc(LPVOID lpParameter); 

    //线程句柄与ID
    HANDLE m_hAudioPlayback;
    unsigned int m_AudioPlaybackId;

    //播放设备的句柄
	HWAVEOUT m_hOut;

    // 正在播放的缓冲区数目
    LONG volatile m_nPlayingBufferCount;

public:

	// 播放的缓冲区数目限制
    static const int MAX_PLAYING_BUFFER = 6;

};
