#pragma once

#include "common/common/MediaPacket/udp_media_def.h"
#include "AudioProcessPlay.h"

class MultiAudioPlay {

public:    

	MultiAudioPlay(__int64 userId);
	~MultiAudioPlay();
    bool create();
    void destroy();

    // Create an audio play process for play
    bool createProcessForUser(__int64 userId);

    // Destroy user audio play process
    bool destroyProcessForUser(__int64 userId);

    // Play audio data packet, the packet will
    // be dispatched to proper audio process.
	void playAudioPack(PACK_AUDIO *ppa);

private:

    // 本机用户ID
    // 考虑到有可能运行多个客户端程序, 使用此名字作为
    // 声音播放程序的名字，将不会重名
    __int64 m_userId;

    // 声音播放进程的编号, 递增
	int m_nInstanceCount;

	// 所有的声音播放进程
	typedef std::map<__int64, AudioProcessPlay*> AudioProcessMap;
    AudioProcessMap m_audioProcesses;

	// 进程有可能被多个会议复用，此处为引用计数
	map<__int64, int> m_audioProcessUseCount;
};

