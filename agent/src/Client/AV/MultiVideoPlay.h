#pragma once

#include "Video/VideoConfig/VideoConfig.h"
#include "VideoWindow.h"

class VideoPlay;
struct PACK_VIDEO;

class MultiVideoPlay
{
public:	

	MultiVideoPlay();
	virtual ~MultiVideoPlay(); 
    bool create();
    void destroy();
  
	// 接收到视频信号，发送到对应的窗口
	void OnNetworkVideoData(PACK_VIDEO *ppv, DWORD len);

	//捕捉到视频数据,发送到会议室本地视频窗口
	void OnLocalVideoData(LPVIDEOHDR lpVHdr);

    // 增加一路视频播放
    void addUserVideoPlay(__int64 userId, HWND hWnd, __int64 conferenceId, int nwidth, int nheight, bool bTKLPlayer = false);

    // 删除一路视频播放
    void removeUserVideoPlay(__int64 userId, __int64 conferenceId);

    // 增加一路本地视频
    void addLocalVideoWindow(HWND hWnd, __int64 conferenceId);

    // 删除一路本地视频
    void removeLocalVideoWindow(__int64 conferenceId);

	void setTKLHead(UCHAR* pData, int nLen, __int64 userId);

private:

    // Local video windows
    VideoWindowMap m_localVideoWindowMap;

    // Decode buffer for all decoder
    unsigned char* m_decodeBuffer;

    // VideoPlay list
    map<__int64, VideoPlay*> m_remoteVideoPlayMap;
};

