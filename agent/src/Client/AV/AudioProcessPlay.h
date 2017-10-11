#pragma once

#include "common/common/MediaPacket/udp_media_def.h"

class AudioProcessPlay
{

private:
	string              m_name;          // 进程名称
    PROCESS_INFORMATION m_processInfo;   // 声音播放进程信息
    HANDLE              m_hEvent;        // 通讯用的Event对象  
    PVOID               m_pFileMapView;  // 文件印射视图
    HANDLE              m_hFileMap;      // 文件印射句柄

public:

    AudioProcessPlay();
    ~AudioProcessPlay();

    // 创建对象
    BOOL create(string name);

    // 销毁对象
    void destroy();

    // 播放声音数据
    BOOL play(PACK_AUDIO *ppa);

};

