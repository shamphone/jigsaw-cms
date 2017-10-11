#include "stdafx.h"
#include "VideoPlay.h"
#include "Video\VideoConfig\VideoConfig.h"
#include "Video\VideoCodecXvid\VideoDecoder.h"
#include "common/common/MediaPacket/udp_media_def.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

VideoPlay::VideoPlay()
{
    m_pDecoder = NULL;
	m_dwPrevKeyFrameNum = -1;
	m_bTKLPlayer = false;
	m_bTKLInit = false;
}

VideoPlay::~VideoPlay()
{
    _ASSERTE(m_pDecoder == NULL);
}

bool VideoPlay::create(int nwidth, int nheight, bool bTKLPlayer)
{
	m_bTKLPlayer = bTKLPlayer;
	if( m_bTKLPlayer )
	{
		return true;
	}

	m_nWidth = nwidth;
	m_nHeight = nheight;
    m_pDecoder = new VideoDecoder();
    return m_pDecoder->create(m_nWidth, m_nHeight);
}

void VideoPlay::destroy()
{
	if( m_bTKLPlayer )
	{
		TKLDestroy();
		return;
	}

    VideoWindowMap::iterator it = m_videoWindowMap.begin();
    while(it != m_videoWindowMap.end())
    {
        delete it->second;
        it++;
    }
    m_videoWindowMap.clear();

    m_pDecoder->destroy();
    delete m_pDecoder;
    m_pDecoder = NULL;
}

bool VideoPlay::addVideo(HWND hWnd, __int64 conferenceId)
{
	if( m_bTKLPlayer )
	{
		return this->TKLAddPlayer(hWnd, conferenceId);
	}

    VideoWindowMap::iterator it = m_videoWindowMap.find(conferenceId);
    _ASSERTE(it == m_videoWindowMap.end());

    VideoWindow* pVideoWindow = new VideoWindow();
    pVideoWindow->hWnd = hWnd;

    m_videoWindowMap.insert(VideoWindowMap::value_type(conferenceId, pVideoWindow));
    return true;
}

bool VideoPlay::removeVideo(__int64 conferenceId)
{
	if( m_bTKLPlayer )
	{
		return this->TKLRemovePlayer(conferenceId);
	}

	VideoWindowMap::iterator it = m_videoWindowMap.find(conferenceId);
    _ASSERTE(it != m_videoWindowMap.end());

    delete it->second;
    m_videoWindowMap.erase(it);
    return true;
}

int VideoPlay::getVideoCount()
{
	if( m_bTKLPlayer )
	{
		return this->m_TKLPlayerMap.size();
	}

    return m_videoWindowMap.size();
}

void VideoPlay::decode(PACK_VIDEO *ppv, unsigned char* pDst)
{
	if( m_bTKLPlayer )
	{
		return;
	}
    m_pDecoder->decode(
            ((unsigned char*)ppv) + sizeof(PACK_VIDEO),
            ppv->data_size,
            pDst);
}

void VideoPlay::play(unsigned char* pData, int nLen)
{
	if( m_bTKLPlayer )
	{
		this->TKLPlay(pData, nLen);
		return;
	}

	BITMAPINFOHEADER bmiHeader = 
	{
		sizeof(BITMAPINFOHEADER),     // biSize
		m_nWidth,                // biWidth
		m_nHeight,               // biHeight
		1,                            // biPlanes
		VideoConfig::DEFAULT_BIT,                  // bitCount
		BI_RGB,                       // biCompression
		m_nWidth * m_nHeight * VideoConfig::DEFAULT_BIT / 8,                 // biSizeImage
		0,                            // biXPelsPerMeter
		0,                            // biYPelsPerMeter
		0,                            // biClrUsed
		0,                            // biImportant
	};

    VideoWindowMap::iterator it = m_videoWindowMap.begin();
    while( it != m_videoWindowMap.end())
    {
        VideoWindow* pVideoWindow = it->second;
        pVideoWindow->hDrawDIB.DrawVideo(
            pVideoWindow->hWnd,
            (char*)pData,
			&bmiHeader);
        it++;
    }
}

bool VideoPlay::TKLInit(BYTE* pBuf, int nLen)
{
	if( !this->m_bTKLPlayer )
	{
		ASSERT(FALSE);
		return false;
	}
	if( m_bTKLInit )
	{
		return true;
	}
	map<__int64, ULONG>::iterator iter = m_TKLPlayerMap.begin();
	while( iter != m_TKLPlayerMap.end() )
	{
		ULONG lPlayerId = iter->second;
		if( !TKLPLAYER_StreamOpen(lPlayerId, pBuf, nLen, 9999) )
		{
			TKLPLAYER_ReleasePlayer(lPlayerId);
			TRACE0( "Open stream failed!\n" );
			iter++;
			continue;
		}
		TKLPLAYER_SetPlaySpeed(lPlayerId, 1.0);
		TKLPLAYER_StreamSetDelayFrames(lPlayerId, 1);
		TKLPLAYER_Play(lPlayerId);
		iter++;
	}
	memcpy(this->m_TKLHead, pBuf, nLen);
	this->m_bTKLInit = true;
	return true;
}

void VideoPlay::TKLDestroy()
{
	map<__int64, ULONG>::iterator iter = m_TKLPlayerMap.begin();
	while( iter != m_TKLPlayerMap.end() )
	{
		ULONG lPlayerId = iter->second;
		TKLPLAYER_ReleasePlayer(lPlayerId);
		iter++;
	}
	m_TKLPlayerMap.clear();
}

bool VideoPlay::TKLPlay(BYTE* pBuf, int nLen)
{
	if( !this->m_bTKLInit )
	{
		return false;
	}
	map<__int64, ULONG>::iterator iter = m_TKLPlayerMap.begin();
	while( iter != m_TKLPlayerMap.end() )
	{
		ULONG lPlayerId = iter->second;
		TKLPLAYER_StreamWriteToBuf(lPlayerId, pBuf, nLen, 1, 500, TRUE);
		iter++;
	}
	return true;
}

bool VideoPlay::TKLAddPlayer(HWND hWnd, __int64 confId)
{
	BOOL bRet = FALSE;
	ULONG lPlayerId = TKLPLAYER_CreatePlayer(AVE6000_P, NULL);
	if ( lPlayerId == 0xFFFFFFFF )
	{
		TRACE0( "Create player failed!\n" );
		return false;
	}
	CWnd* pWnd = CWnd::FromHandle(hWnd);
	CRect rc;
	pWnd->GetClientRect( &rc );
	TKLPLAYER_SetVideoWindow(lPlayerId, pWnd->GetSafeHwnd(), &rc);
	TKLPLAYER_SetDisplayMode(lPlayerId, primarySurface, NULL);

	if( this->m_bTKLInit )
	{
		TKLPLAYER_StreamOpen(lPlayerId, m_TKLHead, 1024, 9999);
		TKLPLAYER_SetPlaySpeed(lPlayerId, 1.0);
		TKLPLAYER_StreamSetDelayFrames(lPlayerId, 1);
		TKLPLAYER_Play(lPlayerId);
	}
	m_TKLPlayerMap[confId] = lPlayerId;

	return true;
}

bool VideoPlay::TKLRemovePlayer(__int64 confId)
{
	map<__int64, ULONG>::iterator iter = m_TKLPlayerMap.find(confId);
	if( iter != m_TKLPlayerMap.end() )
	{
		TKLPLAYER_ReleasePlayer(iter->second);
		m_TKLPlayerMap.erase( iter );
		return true;
	}
	return false;
}

