#pragma once

#include "VideoWindow.h"
#include "TKL_Player.h"

class VideoDecoder;
struct PACK_VIDEO;

class VideoPlay
{
public:
    VideoPlay();
    ~VideoPlay();
    bool create(int nwidth, int nheight, bool bTKLPlayer = false);
    void destroy();

    bool addVideo(HWND hWnd, __int64 conferenceId);
    bool removeVideo(__int64 conferenceId);
    int getVideoCount();

    void decode(PACK_VIDEO *ppv, unsigned char* pDst);
    void play(unsigned char* pData, int nLen = 0);
	DWORD m_dwPrevKeyFrameNum; // previous key frame number

private:

    VideoDecoder* m_pDecoder;        // Video Decoder
    VideoWindowMap m_videoWindowMap; // All video windows
	int m_nWidth;
	int m_nHeight;

public:
	// 初始化tklplayer
	bool TKLInit(BYTE* pBuf, int nLen = 1024);

private:
	// 释放tklplayer资源
	void TKLDestroy();
	// 播放视频数据
	bool TKLPlay(BYTE* pBuf, int nLen);
	// 添加播放器
	bool TKLAddPlayer(HWND hWnd, __int64 confId);
	// 删除播放器
	bool TKLRemovePlayer(__int64 confId);

	// 播放器列表
	map<__int64, ULONG> m_TKLPlayerMap;
	// 初始化数据
	UCHAR m_TKLHead[1024];

	// 是否是tklplayer
	bool m_bTKLPlayer;
    // 是否已初始化
	bool m_bTKLInit;

};

