#include "stdafx.h"
#include "TransModelImpl.h"
#include "FLVCC.h"
#include "transfer/UdpLink.h"
#include "transfer/TcpLink.h"
#include "AV\MultiVideoPlay.h"
#include "AV\MultiAudioPlay.h"
#include "Audio/AudioCodec/AudioCodec.h"
#include "Video/VideoCodecXvid/VideoCodec.h"
#include "Video/VideoCodecXvid/VideoEncoder.h"
#include "Video/VideoConfig/VideoConfig.h"
#include "Desktop/LyvcDesktopServer/DesktopServer.h"
#include "Desktop/LyvcDesktopClient/DesktopClient.h"
#include "Common/Common/Log/Log.h"
#include "Message/MatrixC/MatrixCLib/MatrixC.h"
#include "Model/Room/ConferenceUserMgr.h"
#include "Model/Room/ConferenceUser.h"
#include "Model/CooperationManager.h"
#include "UI\Whiteboard\WhiteboardDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

TransModelImpl::TransModelImpl(__int64 userId, LyvcMessage::MatrixC* pMatrixC)
{
    m_userId = userId;

	m_pMatrixC = pMatrixC;

	m_pServerUdpLink = NULL;
	m_pServerTcpLink = NULL;

	m_dwAudioSeqNo = 0;
	m_dwVideoSeqNo = 0;
	
	m_idTimerEvent = 0;

    m_pAudioEncoder = NULL;
    m_pVideoEncoder = NULL;

    m_splitKeyFrameFlag = false;

	m_bSendVideo = TRUE;

}

TransModelImpl::~TransModelImpl()
{
}

#pragma message ("Should destroy things when create failed.")
#pragma message ("See doc/design/C++对象的生命周期管理.doc")
bool TransModelImpl::Create()
{
    // Get configuration file name
	char* iniFileName = ::GetApp()->getIniFilename();

	//初始化各个视频通道的发送计数器
	int avcccn = ::GetApp()->getCooperationManager()->getTKLChannelNumber();
	for(int i = 0;i < avcccn;i++)
	{
		m_LocalTKLPlayer[i] = 0;
		m_RemotelTKLPlayer[i] = 0;
		m_dwTKLSeqNo[i] = 0;
	}

	m_nStartSendAudioCallCount = 0;

	m_nStartSendVideoCallCount = 0;

	// 创建声音编码器
	m_pAudioEncoder = new AudioCodec();

	// 初始化xvid, 创建图像编码器
    if(!VideoCodec::init())
    {
		FVS_DEBUG0("Fail to initialize the Xvid Video Codec Sub-System\n");
        return false;
    }

	m_nCurVideoBitRate = ::GetPrivateProfileInt("TRANSMODEL", "bitrate", 50000, iniFileName);
	m_nKeyFrameInterval = ::GetPrivateProfileInt("MEDIA_CONFIG", "max_key_interval", 20, iniFileName);
	m_pVideoEncoder = new VideoEncoder();
    if(!m_pVideoEncoder->create(
                VideoConfig::getWidth(),
                VideoConfig::getHeight(),
                VideoConfig::getFrameRate(),
                m_nCurVideoBitRate,
				m_nKeyFrameInterval))
    {
		FVS_DEBUG0("Fail to create Xvid encoder\n");
        return false;
    }
		
	// 生成桌面捕捉对象
	m_pDesktopServer = DesktopServer::getInstance();
    m_nStartSendDesktopCallCount = 0;

    // 初始化多进程声音播放对象
	::InitializeCriticalSection(&m_AudioPlayCs);
	m_pMultiAudioPlay = new MultiAudioPlay(this->m_userId);
    m_pMultiAudioPlay->create();

	// 生成桌面播放对象
	::InitializeCriticalSection(&m_DesktopClientCs);
	m_pDesktopClient = DesktopClient::getInstance();
	m_pDesktopClient->start(this->m_pMatrixC);

	// 初始化多窗口视频播放
	::InitializeCriticalSection(&m_VideoPlayCs);
    m_pMultiVideoPlay = new MultiVideoPlay();
	m_pMultiVideoPlay->create();


    // 读取配置文件中关于拆分关键帧的设置
	m_splitKeyFrameFlag = ::GetPrivateProfileInt("TRANSMODEL", "split_key_frame", 1, iniFileName);

	::InitializeCriticalSection(&m_WhiteboardCs);

	return true;
}

void TransModelImpl::Destroy()
{
	// 清理刷新地址的Timer
	if (m_idTimerEvent != 0)
	{
		timeKillEvent(m_idTimerEvent);
		m_idTimerEvent = 0;
	}

    // 删除UDP连接
	if (m_pServerUdpLink)
	{		
		m_pServerUdpLink->Destroy();
		SAFE_DELETE(m_pServerUdpLink);
	}

    // 删除TCP连接
	if (m_pServerTcpLink)
	{		
		m_pServerTcpLink->Destroy();
		SAFE_DELETE(m_pServerTcpLink);
	}

	// 销毁桌面播放对象
    ::EnterCriticalSection(&m_DesktopClientCs);
	m_pDesktopClient->stop();
	delete m_pDesktopClient;
	m_pDesktopClient = NULL;
    ::LeaveCriticalSection(&m_DesktopClientCs);
	::DeleteCriticalSection(&m_DesktopClientCs);

	// 删除多窗口视频播放对象
	::EnterCriticalSection(&m_VideoPlayCs);
    m_pMultiVideoPlay->destroy();
    delete m_pMultiVideoPlay;
	m_pMultiVideoPlay = NULL;
	::LeaveCriticalSection(&m_VideoPlayCs);
	::DeleteCriticalSection(&m_VideoPlayCs);

    // 删除多进程声音播放对象
	::EnterCriticalSection(&m_AudioPlayCs);
    m_pMultiAudioPlay->destroy();
    delete m_pMultiAudioPlay;
	m_pMultiAudioPlay = NULL;
	::LeaveCriticalSection(&m_AudioPlayCs);
	::DeleteCriticalSection(&m_AudioPlayCs);

	// 删除桌面捕捉对象
	delete m_pDesktopServer;
	m_pDesktopServer = NULL;

	// 删除图像编码器
    m_pVideoEncoder->destroy();
    SAFE_DELETE(m_pVideoEncoder);

	// 删除声音编码器
	delete m_pAudioEncoder;
	m_pAudioEncoder = NULL;

	::DeleteCriticalSection(&m_WhiteboardCs);
}
void TransModelImpl::addWhiteboard( __int64 confId, void* pVoid )
{
	::EnterCriticalSection(&m_WhiteboardCs);
	m_whiteboardMap[confId] = (CWhiteboardDlg*)pVoid;
	::LeaveCriticalSection(&m_WhiteboardCs);
}

void TransModelImpl::removeWhiteboard( __int64 confId )
{
	::EnterCriticalSection(&m_WhiteboardCs);
    m_whiteboardMap.erase(confId);
	::LeaveCriticalSection(&m_WhiteboardCs);
}

void TransModelImpl::sendWhiteboardData( __int64 confId, char* buffer, int len )
{
	/*屏蔽白板功能
	PACK_TCP packTcp;
	packTcp.flag = FVS_TCP_WHITEBOARD;
	packTcp.userId = this->m_userId;
	packTcp.confId = confId;
	packTcp.data_size = len;

	sendTCPData((char*)&packTcp, sizeof(PACK_TCP));
	sendTCPData(buffer, len);
	*/
}

bool TransModelImpl::addUserAudioPlay(__int64 userId)
{
    ::EnterCriticalSection(&this->m_AudioPlayCs);
	bool ret = this->m_pMultiAudioPlay->createProcessForUser(userId);
    ::LeaveCriticalSection(&this->m_AudioPlayCs);
	return ret;
}

bool TransModelImpl::removeUserAudioPlay(__int64 userId)
{
    ::EnterCriticalSection(&this->m_AudioPlayCs);
	bool ret = this->m_pMultiAudioPlay->destroyProcessForUser(userId);
    ::LeaveCriticalSection(&this->m_AudioPlayCs);
	return ret;
}

void TransModelImpl::startSendAudio()
{
	m_nStartSendAudioCallCount++;
	if( m_nStartSendAudioCallCount == 1 )
	{
		::GetApp()->getCooperationManager()->startAudioCapture();
	}
}

void TransModelImpl::stopSendAudio()
{
	m_nStartSendAudioCallCount--;
	if( m_nStartSendAudioCallCount == 0 )
	{
		::GetApp()->getCooperationManager()->stopAudioCapture();
	}
}

bool TransModelImpl::addUserVideoPlay(__int64 userId, HWND hVideoWnd, __int64 conferenceId, int nwidth, int nheight)
{
	bool bTKLPlayer = false;
	LONG handle = 0;
	if( userId > 100000000000000000L )
	{
		bTKLPlayer = true;
		if( ConferenceUserMgr::getRealIdByVirtualId(userId) == this->m_userId )
		{
			handle = ConferenceUserMgr::getVirtualConferenceUserChannel(userId);
			this->m_LocalTKLPlayer[handle-1]++;
			if( this->m_LocalTKLPlayer[handle-1] + this->m_RemotelTKLPlayer[handle-1] == 1 )
			{
				GetApp()->getCooperationManager()->TKLStartVideoCapture(handle);
			}
		}
	}
	::EnterCriticalSection(&m_VideoPlayCs);
    m_pMultiVideoPlay->addUserVideoPlay(userId, hVideoWnd, conferenceId, nwidth, nheight, bTKLPlayer);
	if( handle > 0 )
	{
		UCHAR* pHead = GetApp()->getCooperationManager()->getTKLHead(handle);
		if( pHead )
		{
			this->m_pMultiVideoPlay->setTKLHead(pHead, 1024, userId);
		}
	}
	::LeaveCriticalSection(&m_VideoPlayCs);
    return true;
}

bool TransModelImpl::removeUserVideoPlay(__int64 userId, __int64 conferenceId)
{
	if( userId > 100000000000000000L )
	{
		if( ConferenceUserMgr::getRealIdByVirtualId(userId) == this->m_userId )
		{
			LONG handle = ConferenceUserMgr::getVirtualConferenceUserChannel(userId);
			this->m_LocalTKLPlayer[handle-1]--;
			if( this->m_LocalTKLPlayer[handle-1] + this->m_RemotelTKLPlayer[handle-1] == 0 )
			{
				GetApp()->getCooperationManager()->TKLStopVideoCapture(handle);
			}
		}
	}
	::EnterCriticalSection(&m_VideoPlayCs);
    m_pMultiVideoPlay->removeUserVideoPlay(userId, conferenceId);
	::LeaveCriticalSection(&m_VideoPlayCs);
    return true;
}

bool TransModelImpl::addLocalVideoWindow(HWND hWnd, __int64 conferenceId)
{
	::EnterCriticalSection(&m_VideoPlayCs);
    m_pMultiVideoPlay->addLocalVideoWindow(hWnd, conferenceId);
	::LeaveCriticalSection(&m_VideoPlayCs);
    return true;
}

bool TransModelImpl::removeLocalVideoWindow(__int64 conferenceId)
{
	::EnterCriticalSection(&m_VideoPlayCs);
    m_pMultiVideoPlay->removeLocalVideoWindow(conferenceId);
	::LeaveCriticalSection(&m_VideoPlayCs);
    return true;
}

void TransModelImpl::startSendVideo(__int64 userId)
{
	if(userId == this->m_userId){
		this->m_nStartSendVideoCallCount++;
	}else{
		if( ConferenceUserMgr::getRealIdByVirtualId(userId) == this->m_userId )
		{
			LONG handle = ConferenceUserMgr::getVirtualConferenceUserChannel(userId);
			this->m_RemotelTKLPlayer[handle - 1]++;
			if( this->m_LocalTKLPlayer[handle-1] + this->m_RemotelTKLPlayer[handle-1] == 1 )
			{
				GetApp()->getCooperationManager()->TKLStartVideoCapture(handle);
			}
		}
	}
}

void TransModelImpl::stopSendVideo(__int64 userId)
{
	if(userId == this->m_userId){
		this->m_nStartSendVideoCallCount--;
	}else{
		if( ConferenceUserMgr::getRealIdByVirtualId(userId) == this->m_userId )
		{
			LONG handle = ConferenceUserMgr::getVirtualConferenceUserChannel(userId);
			this->m_RemotelTKLPlayer[handle - 1]--;
			if( this->m_LocalTKLPlayer[handle-1] + this->m_RemotelTKLPlayer[handle-1] == 0 )
			{
				GetApp()->getCooperationManager()->TKLStopVideoCapture(handle);
			}
		}
	}
}

void TransModelImpl::setFrameRateAndKeyFrameInterval(int frameRate, int keyFrameInterval)
{
	::EnterCriticalSection(&this->m_VideoPlayCs);
    m_pVideoEncoder->destroy();
    SAFE_DELETE(m_pVideoEncoder);
	m_pVideoEncoder = new VideoEncoder();
	m_nKeyFrameInterval = keyFrameInterval;
    if(!m_pVideoEncoder->create(
                VideoConfig::getWidth(),
                VideoConfig::getHeight(),
                frameRate,
                m_nCurVideoBitRate,
				keyFrameInterval))
    {
		FVS_DEBUG0("Fail to create Xvid encoder\n");
    }		
	::LeaveCriticalSection(&this->m_VideoPlayCs);
}

void TransModelImpl::startSendDesktop()
{
    this->m_nStartSendDesktopCallCount ++;
    if( this->m_nStartSendDesktopCallCount == 1 )
    {
        m_pDesktopServer->start(this->m_pMatrixC, desktopServerCallback, this);
    }
	else
	{
		m_pDesktopServer->reInitialize();
	}
}

void TransModelImpl::stopSendDesktop()
{
    this->m_nStartSendDesktopCallCount --;
    if( this->m_nStartSendDesktopCallCount == 0 )
    {
        m_pDesktopServer->stop();
    }
}

void TransModelImpl::startReceiveUserDesktop(__int64 userId, string userName, ClientNotifier* pClientNotifier)
{
	::EnterCriticalSection(&m_DesktopClientCs);

	if( m_pDesktopClient->isWatchingUser(userId) )
	{
		m_pDesktopClient->addNotifier(userId, pClientNotifier);
	}
	else
	{
		m_pDesktopClient->addClient(userId, userName, pClientNotifier);
	}

	::LeaveCriticalSection(&m_DesktopClientCs);
}

void TransModelImpl::stopReceiveUserDesktop(__int64 userId)
{
	::EnterCriticalSection(&m_DesktopClientCs);

	_ASSERTE( m_pDesktopClient->isWatchingUser(userId) );
	m_pDesktopClient->remClient(userId);

	::LeaveCriticalSection(&m_DesktopClientCs);
}

void TransModelImpl::startControlUserDesktop(__int64 userId)
{
	::EnterCriticalSection(&m_DesktopClientCs);

	_ASSERTE( m_pDesktopClient->isWatchingUser(userId) );
	m_pDesktopClient->startControl(userId);

	::LeaveCriticalSection(&m_DesktopClientCs);
}

void TransModelImpl::stopControlUserDesktop(__int64 userId)
{
	::EnterCriticalSection(&m_DesktopClientCs);

	_ASSERTE( m_pDesktopClient->isWatchingUser(userId) );
	m_pDesktopClient->stopControl(userId);

	::LeaveCriticalSection(&m_DesktopClientCs);
}

__int64 TransModelImpl::getTopDesktopClientId()
{
    return m_pDesktopClient->getTopDesktopClientId();
}

BOOL TransModelImpl::setTcpServer(string IP, int port)
{
	_ASSERTE(m_pServerTcpLink == NULL);

	m_pServerTcpLink = new TcpLink(TransModelImpl::receiveTCPData, this);
	if(!m_pServerTcpLink->Create(IP, port))
	{
		return FALSE;
	}

	//发送登录媒体服务器的数据包，媒体服务器记录本连接的用户ID
	PACK_TCP userPack;
	userPack.flag      = FVS_TCP_USER;
    userPack.userId    = this->m_userId;
    userPack.data_size = 0;
    this->sendTCPData((char *)&userPack, sizeof(PACK_TCP));
 
	return TRUE;
}

BOOL TransModelImpl::sendTCPData(char* buf, DWORD nlen)
{
    return this->m_pServerTcpLink->send(buf, nlen);
}

BOOL TransModelImpl::setUdpServer(string IP, int port)
{
	_ASSERTE(m_pServerUdpLink == NULL);

	m_pServerUdpLink = new UdpLink(TransModelImpl::receiveUDPData, this);
	if(!m_pServerUdpLink->Create(IP, port))
	{
		return FALSE;
	}

	//发送登录媒体服务器的数据包，媒体服务器记录本地UDP地址
	PACK_ADDR addrPack;
	addrPack.flag = FVS_MSG_ADDR;
    addrPack.userId = this->m_userId;
    this->sendUDPData((char *)&addrPack, sizeof(PACK_ADDR));
 
    // 设置定时器，刷新UDP地址，防止防火墙释放Session
	// 默认刷新间隔是5秒钟，具体可以在配置文件中配置
	char* g_IniFileName = ::GetApp()->getIniFilename();
	int UdpKeepaliveInterval = ::GetPrivateProfileInt("TRANSMODEL", "udp_keepalive_interval", 5, g_IniFileName);
	m_idTimerEvent = timeSetEvent(
                             UdpKeepaliveInterval * 1000,
							 1000, 
							 TimerFunction,
							 (DWORD)this,
							 TIME_PERIODIC);
	if(m_idTimerEvent == NULL)
	{
		FVS_DEBUG("Can't set the timer for udp keep alive\n");
		return FALSE;
	}

	return TRUE;
}

int TransModelImpl::sendUDPData(char *buf, DWORD nlen)
{
	ASSERT(m_pServerUdpLink != NULL);
	if (m_pServerUdpLink == NULL)
	{
		return 0;
	}

	return m_pServerUdpLink->send(buf, nlen);
}

void CALLBACK TransModelImpl::TimerFunction(UINT wTimerID, UINT msg, DWORD dwUser, DWORD dw1, DWORD dw2) 
{
	TransModelImpl* _this = (TransModelImpl*) dwUser;

	PACK_ADDR addrPack;
	addrPack.flag = FVS_MSG_ADDR;
    addrPack.userId = _this->m_userId;

	_ASSERTE(_this->m_pServerUdpLink != NULL);
    _ASSERTE( _this->m_pServerUdpLink->getSocket() != 0 );

    _this->sendUDPData((char *)&addrPack, sizeof(PACK_ADDR));

	PACK_TCP tcpPack;
	tcpPack.flag = FVS_TCP_USER;
	tcpPack.userId = _this->m_userId;
	tcpPack.data_size = 0;
	_this->sendTCPData((char *)&tcpPack, sizeof(PACK_TCP));
}

void TransModelImpl::receiveUDPData(void* pObject, char* buf, int len)
{
	TransModelImpl* _this = (TransModelImpl*)pObject;

	ASSERT(buf != NULL);
	ASSERT(len > sizeof(PACK_ADDR) + 12);
	
    BYTE packetType = ((PACK_ADDR*)buf)->flag;
	switch(packetType)
	{
        //音频数据
        case FVS_MSG_AUDIO:
        {
            ::EnterCriticalSection(&_this->m_AudioPlayCs);
	        PACK_AUDIO *ppa = (PACK_AUDIO*)buf;
			_this->m_pMultiAudioPlay->playAudioPack(ppa);
			::LeaveCriticalSection(&_this->m_AudioPlayCs);
    		break;
        }

        //视频数据
        case FVS_MSG_VIDEO:
        case FVS_MSG_COMPRESSION_VIDEO:
        {
			PACK_VIDEO* ppv = (PACK_VIDEO*)buf;

            // 如果是关键帧，而且已经作过了拆分，那么就将其组装
			if( ppv->key && (ppv->data_size != (len-sizeof(PACK_VIDEO))) )
			{
				int result = fillKeyBuffer(ppv, len, _this->bufferedKeyFrames);
				if( result == -1 )
				{
					return;
				}
				else
				{
					memcpy(buf + sizeof(PACK_VIDEO), _this->bufferedKeyFrames[result].buffer, _this->bufferedKeyFrames[result].nReceivedBytes);
					len = _this->bufferedKeyFrames[result].nReceivedBytes + sizeof(PACK_VIDEO);
					_this->bufferedKeyFrames[result].nReceivedBytes = 0;
				}
			}

            ::EnterCriticalSection(&_this->m_VideoPlayCs);
            _this->m_pMultiVideoPlay->OnNetworkVideoData((PACK_VIDEO*)buf, len);
			::LeaveCriticalSection(&_this->m_VideoPlayCs);
		    break;
        }
        default:
            ASSERT(FALSE);
            return;
	}

	return;
}

void TransModelImpl::receiveTCPData(void* pObject, char* buf, int len)
{
	TransModelImpl* _this = (TransModelImpl*)pObject;

	PACK_TCP* pPackTCP = (PACK_TCP*)buf;
	switch(pPackTCP->flag)
	{
	case FVS_TCP_DESKTOP:
        ::EnterCriticalSection(&_this->m_DesktopClientCs);
		_this->m_pDesktopClient->receiveDesktopPacket(buf + sizeof(PACK_TCP), len - sizeof(PACK_TCP), pPackTCP->userId);
        ::LeaveCriticalSection(&_this->m_DesktopClientCs);
		break;
	case FVS_TCP_WHITEBOARD:
		/*屏蔽白板功能
        ::EnterCriticalSection(&_this->m_WhiteboardCs);
		if( _this->m_whiteboardMap.find( pPackTCP->confId ) != _this->m_whiteboardMap.end() )
		{
			_this->m_whiteboardMap[pPackTCP->confId]->recvWhiteboardData(buf + sizeof(PACK_TCP), len - sizeof(PACK_TCP) );
		}
		::LeaveCriticalSection(&_this->m_WhiteboardCs);
		*/
		break;
	case FVS_TCP_USER:
		TRACE0( "recv tcp packet\n" );
		break;
	default:
		_ASSERTE(FALSE);
		break;
	}
	return;
}

void TransModelImpl::audioDataCallback(void* pObject, WAVEHDR* pWH)
{
    TransModelImpl* _this = (TransModelImpl*)pObject;

	int rlen;
	if (_this->m_pAudioEncoder->EncodeAudioData(
                (char*)(pWH->lpData),
                pWH->dwBytesRecorded,
                _this->m_audioPack + sizeof(PACK_AUDIO),
                &rlen))
	{			
		((PACK_AUDIO *)_this->m_audioPack)->flag      = FVS_MSG_AUDIO;
		((PACK_AUDIO *)_this->m_audioPack)->userId    = _this->m_userId;
		((PACK_AUDIO *)_this->m_audioPack)->data_size = rlen;
		((PACK_AUDIO *)_this->m_audioPack)->no        = _this->m_dwAudioSeqNo++;
		
		_this->sendUDPData(_this->m_audioPack, rlen + sizeof(PACK_AUDIO));
	}
}

void TransModelImpl::videoDataCallback(void* pObject, LPVIDEOHDR lpVHdr)
{
	TransModelImpl* _this = (TransModelImpl*)pObject;

	// 检查用户是否设置为不发送自己的视频数据
	if(!_this->m_bSendVideo)
	{
		return;
	}

	// 将视频数据发送到所有本地窗口
	::EnterCriticalSection(&_this->m_VideoPlayCs);
    _this->m_pMultiVideoPlay->OnLocalVideoData(lpVHdr);
	::LeaveCriticalSection(&_this->m_VideoPlayCs);

	// 检验是否应该向网络发送视频数据
	if( _this->m_nStartSendVideoCallCount == 0 )
	{
		return;
	}

	//编码压缩并发送视频数据
	int encodedLen;
	bool bKeyFrame;
	::EnterCriticalSection(&_this->m_VideoPlayCs);
	if (_this->m_pVideoEncoder->encode(
                lpVHdr->lpData,
                (int)lpVHdr->dwBytesUsed,
				(unsigned char*)(_this->m_videoPack+sizeof(PACK_VIDEO)),
                &encodedLen,
                &bKeyFrame))
	{
		((PACK_VIDEO*)_this->m_videoPack)->userId = _this->m_userId;
		((PACK_VIDEO*)_this->m_videoPack)->flag = FVS_MSG_VIDEO;
		_this->SendEncodedVideoData(encodedLen, bKeyFrame);
	}
	::LeaveCriticalSection(&_this->m_VideoPlayCs);
}

void TransModelImpl::videoCompressDataCallback(void* pObject, LONG handle, UCHAR* pData, int nLen, bool bKeyFrame)
{
	TransModelImpl* _this = (TransModelImpl*)pObject;

	if(!_this->m_bSendVideo)
	{
		return;
	}
	char videoPack[MAX_UDP_SIZE];

	// 将视频数据发送到所有本地窗口
	::EnterCriticalSection(&_this->m_VideoPlayCs);
	if ((nLen > 0)  && ((nLen+sizeof(PACK_VIDEO)) < MAX_UDP_SIZE))
	{
		((PACK_VIDEO*)videoPack)->flag = FVS_MSG_COMPRESSION_VIDEO;
		((PACK_VIDEO*)videoPack)->userId = ConferenceUserMgr::getVirtualUserId(_this->m_userId, handle);
		((PACK_VIDEO*)videoPack)->no = _this->m_dwTKLSeqNo[handle-1]++;
		((PACK_VIDEO*)videoPack)->width = 0;
		((PACK_VIDEO*)videoPack)->height = 0;
		((PACK_VIDEO*)videoPack)->band = 0;
		((PACK_VIDEO*)videoPack)->frameRate = 10;
		((PACK_VIDEO*)videoPack)->key = bKeyFrame;
		((PACK_VIDEO*)videoPack)->key_interval = 20;
		((PACK_VIDEO*)videoPack)->data_size= nLen;
		memcpy(videoPack+sizeof(PACK_VIDEO), pData, nLen);
		_this->m_pMultiVideoPlay->OnNetworkVideoData((PACK_VIDEO*)videoPack, nLen);
	}

	// 检验是否应该向网络发送视频数据
	if( _this->m_RemotelTKLPlayer[handle - 1] == 0 )
	{
		::LeaveCriticalSection(&_this->m_VideoPlayCs);
		return;
	}

    // 如果是关键帧，而且配置文件要求我们对其进行分片
	if( bKeyFrame && _this->m_splitKeyFrameFlag)
	{
		int nBlockCount = splitVideoPack(videoPack, nLen, _this->partialBlocks, _this->partialBlockSize);
		for(int i=0; i<nBlockCount; i++)
		{
			_this->sendUDPData(_this->partialBlocks[i], _this->partialBlockSize[i]);
		}
	}
	else
	{
		_this->sendUDPData(videoPack, nLen+sizeof(PACK_VIDEO));
	}

	::LeaveCriticalSection(&_this->m_VideoPlayCs);
}

void TransModelImpl::setTKLHead(__int64 userId, UCHAR* pHead, int nLen)
{
	::EnterCriticalSection(&this->m_VideoPlayCs);
	this->m_pMultiVideoPlay->setTKLHead(pHead, nLen, userId);
	::LeaveCriticalSection(&this->m_VideoPlayCs);
}

void TransModelImpl::desktopServerCallback(void* pObject, char* buffer, int len)
{
	TransModelImpl* _this = (TransModelImpl*)pObject;

	PACK_TCP packTcp;
	packTcp.flag = FVS_TCP_DESKTOP;
	packTcp.userId = _this->m_userId;
	packTcp.data_size = len;

	_this->sendTCPData((char*)&packTcp, sizeof(PACK_TCP));
	_this->sendTCPData(buffer, len);
}

void TransModelImpl::SendEncodedVideoData(DWORD encodedLen, bool bKeyFrame)
{	
	if ((encodedLen > 0)  && ((encodedLen+sizeof(PACK_VIDEO)) < MAX_UDP_SIZE))
	{
		((PACK_VIDEO*)m_videoPack)->no = m_dwVideoSeqNo++;
		((PACK_VIDEO*)m_videoPack)->width = VideoConfig::getWidth();
		((PACK_VIDEO*)m_videoPack)->height = VideoConfig::getHeight();
		((PACK_VIDEO*)m_videoPack)->band = m_nCurVideoBitRate;
		((PACK_VIDEO*)m_videoPack)->frameRate = VideoConfig::framerate;
		((PACK_VIDEO*)m_videoPack)->key = bKeyFrame;
		((PACK_VIDEO*)m_videoPack)->key_interval = this->m_nKeyFrameInterval;
		((PACK_VIDEO*)m_videoPack)->data_size= encodedLen;

        // 如果是关键帧，而且配置文件要求我们对其进行分片
		if( bKeyFrame && m_splitKeyFrameFlag)
		{
			int nBlockCount = splitVideoPack(m_videoPack, encodedLen, this->partialBlocks, this->partialBlockSize);
			for(int i=0; i<nBlockCount; i++)
			{
				this->sendUDPData(partialBlocks[i], partialBlockSize[i]);
			}
		}

		else
		{
			this->sendUDPData(m_videoPack, encodedLen+sizeof(PACK_VIDEO));
		}
	}
}

void TransModelImpl::EnableSendVideo(BOOL bSend)
{
	this->m_bSendVideo = bSend;
}

