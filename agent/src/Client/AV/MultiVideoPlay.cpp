#include "stdafx.h"
#include "flvcc.h"
#include "MultiVideoPlay.h"
#include "VideoPlay.h"
#include "..\model\CooperationManager.h"
#include "common/common/MediaPacket/udp_media_def.h"
#include "Video/VideoCapture/TklSdk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

MultiVideoPlay::MultiVideoPlay()
{
	m_decodeBuffer = new unsigned char[VideoConfig::getMaxSize()];
}

MultiVideoPlay::~MultiVideoPlay()
{	
	delete m_decodeBuffer;
}

bool MultiVideoPlay::create()
{
    return true;
}

void MultiVideoPlay::destroy()
{
    _ASSERTE(m_localVideoWindowMap.size() == 0);

    // 关闭所有的VideoPlay
    map<__int64, VideoPlay*>::iterator it = m_remoteVideoPlayMap.begin();
    while( it != m_remoteVideoPlayMap.end())
    {
        VideoPlay* pVideoPlay = it->second;
        pVideoPlay->destroy();
        delete pVideoPlay;

        it++;
    }
    m_remoteVideoPlayMap.clear();
}

void MultiVideoPlay::OnNetworkVideoData(PACK_VIDEO *ppv, DWORD len)
{
    __int64 userId = ppv->userId;
    map<__int64, VideoPlay*>::iterator it = m_remoteVideoPlayMap.find(userId);

    // 可能是网络延迟带来的数据包
    if( it == m_remoteVideoPlayMap.end() )
    {
        return;
    }

    VideoPlay* pVideoPlay = it->second;
	//应该从一个关键帧开始解码
	if( ppv->key )
	{
		pVideoPlay->m_dwPrevKeyFrameNum = ppv->no;;
	}
	else if( pVideoPlay->m_dwPrevKeyFrameNum < 0 )
	{
		return;
	}
	else if( pVideoPlay->m_dwPrevKeyFrameNum + ppv->key_interval < ppv->no 
			|| ppv->no < pVideoPlay->m_dwPrevKeyFrameNum )
	{
		// 收到丢失的关键帧之后的非关键帧，为了防止出现马赛克，丢弃收到的数据
		return;
	}

	if (ppv->flag == FVS_MSG_COMPRESSION_VIDEO )
	{
		pVideoPlay->play( ((unsigned char*)ppv) + sizeof(PACK_VIDEO), ppv->data_size );
	}
	else
	{
		try{
			pVideoPlay->decode(ppv,m_decodeBuffer);
		}catch(...){
			return;
		}
	    pVideoPlay->play(m_decodeBuffer);
	}
}

void MultiVideoPlay::OnLocalVideoData(LPVIDEOHDR lpVHdr)
{
    VideoWindowMap::iterator it = m_localVideoWindowMap.begin();
    while( it != m_localVideoWindowMap.end())
    {
        VideoWindow* pVideoWindow = it->second;
        pVideoWindow->hDrawDIB.DrawVideo(
            pVideoWindow->hWnd,
            (char*)lpVHdr->lpData,
			const_cast<BITMAPINFOHEADER*>(&VideoConfig::getBitmapInfoHeader()));
        it++;
    }
}

void MultiVideoPlay::addUserVideoPlay(__int64 userId, HWND hWnd, __int64 conferenceId, int nwidth, int nheight, bool bTKLPlayer)
{
    VideoPlay* pVideoPlay = NULL;

    map<__int64, VideoPlay*>::iterator it = m_remoteVideoPlayMap.find(userId);
    if( it == m_remoteVideoPlayMap.end())
    {
        pVideoPlay = new VideoPlay();
        pVideoPlay->create(nwidth, nheight, bTKLPlayer);

        m_remoteVideoPlayMap.insert( map<__int64, VideoPlay*>::value_type(userId, pVideoPlay));
    }
    else
    {
        pVideoPlay = it->second;
    }

    pVideoPlay->addVideo(hWnd, conferenceId);
}

void MultiVideoPlay::removeUserVideoPlay(__int64 userId, __int64 conferenceId)
{
    map<__int64, VideoPlay*>::iterator it = m_remoteVideoPlayMap.find(userId);
    if( it == m_remoteVideoPlayMap.end() )
	{
		return;
	}
    VideoPlay* pVideoPlay = it->second;

    pVideoPlay->removeVideo(conferenceId);
    if( pVideoPlay->getVideoCount() == 0 )
    {
        pVideoPlay->destroy();
        delete pVideoPlay;
        m_remoteVideoPlayMap.erase(it);
    }

}

void MultiVideoPlay::addLocalVideoWindow(HWND hWnd, __int64 conferenceId)
{
    VideoWindow* pVideoWindow = new VideoWindow();
    pVideoWindow->hWnd = hWnd;

    VideoWindowMap::iterator it = m_localVideoWindowMap.find(conferenceId);
    _ASSERTE( it == m_localVideoWindowMap.end());
    m_localVideoWindowMap.insert(VideoWindowMap::value_type(conferenceId, pVideoWindow));
}

void MultiVideoPlay::removeLocalVideoWindow(__int64 conferenceId)
{
    VideoWindowMap::iterator it = m_localVideoWindowMap.find(conferenceId);
    _ASSERTE(it != m_localVideoWindowMap.end());

    delete it->second;
    m_localVideoWindowMap.erase(it);
}

void MultiVideoPlay::setTKLHead(UCHAR* pData, int nLen, __int64 userId)
{
    map<__int64, VideoPlay*>::iterator it = m_remoteVideoPlayMap.find(userId);
    if( it != m_remoteVideoPlayMap.end() )
	{
		VideoPlay* pVideoPlay = it->second;
	    pVideoPlay->TKLInit(pData, nLen);
    }
}