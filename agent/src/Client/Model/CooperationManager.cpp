#include "stdafx.h"
#include "FLVCC.h"
#include "CooperationManager.h"
#include "Common/Common/Log/Log.h"
#include "../Transfer/TransModelImpl.h"
#include "Audio\AudioCapture\AudioCaptureMgr.h"
#include "Video\VideoCapture\VideoCaptureMgr.h"
#include "Video/VideoConfig/VideoConfig.h"
#include "Video/VideoCapture/TklSdk.h"
#include "ui\AVWizard\VideoPP.h"

CooperationManager::CooperationManager()
{
	m_bVideoCaptureInitSuccess = FALSE;
	m_nTKLChannelNumber = 0;
    for( int i = 0; i < MAX_TKL_CHANNEL_NUM; i++ )
	{
		m_lTKLHandles[i] = 0;
		m_hTKLEvents[i] = 0;
		m_TKLHeads[i] = 0;
		m_hTKLDataRecvThreads[i] = 0;
		m_nTKLDataRecvThreadIds[i] = 0;
		m_bTKLExit[i] = false;
		m_CompressCount[i] = 0;
	}
}

CooperationManager::~CooperationManager()
{
}

void CooperationManager::create()
{
    // Get configuration file name
	char* iniFileName = ::GetApp()->getIniFilename();

    // 初始化音频捕捉
    char audioFilename[MAX_PATH];
	int useAudioFile = ::GetPrivateProfileInt("MEDIA_CONFIG", "use_audio_file", 0, iniFileName);
    if( useAudioFile )
    {
        ::GetPrivateProfileString(
                "MEDIA_CONFIG",
                "audio_file",
                "..\\AVClips\\example.wav",
                audioFilename,
                MAX_PATH,
                iniFileName);
        AudioCaptureMgr::createFromFile(CooperationManager::audioDataCallback, this, audioFilename);
    }
    else
    {
		int nDeviceId = ::GetPrivateProfileInt("MEDIA_CONFIG", "audio_record_device", WAVE_MAPPER, iniFileName);
        if( nDeviceId >= waveInGetNumDevs())
        {
            nDeviceId = WAVE_MAPPER;
        }
		AudioCaptureMgr::create(CooperationManager::audioDataCallback, this, nDeviceId);
    }

	// 读取视频数据参数
	VideoConfig::setWidth(::GetPrivateProfileInt("MEDIA_CONFIG", "video_width", 176, iniFileName));
	VideoConfig::setHeight(::GetPrivateProfileInt("MEDIA_CONFIG", "video_height", 144, iniFileName));
	VideoConfig::setSize(VideoConfig::getWidth() * VideoConfig::getHeight() * VideoConfig::DEFAULT_BIT / 8);
	VideoConfig::setBitmapInfoHeader();

    // 初始化视频捕捉设备
    char videoFilename[MAX_PATH];
	int useVideoFile = ::GetPrivateProfileInt("MEDIA_CONFIG", "use_video_file", 0, iniFileName);
    if( useVideoFile )
    {
        ::GetPrivateProfileString(
                "MEDIA_CONFIG",
                "video_file",
                "..\\AVClips\\video.dat",
                videoFilename,
                MAX_PATH,
                iniFileName);
        	m_bVideoCaptureInitSuccess = VideoCaptureMgr::createFromFile(CooperationManager::videoDataCallback, this, videoFilename);
    }
    else
    {
		int frameRate = ::GetPrivateProfileInt("MEDIA_CONFIG", "video_quality", VideoConfig::DEFAULT_FRAME_RATE, iniFileName);
		VideoConfig::setFrameRate(frameRate);
        char devicename[128];
        ::GetPrivateProfileString(
                "MEDIA_CONFIG",
                "video_device",
                "",
                devicename,
                128,
                iniFileName);
        if( devicename[0] == '\0')
        {
            m_bVideoCaptureInitSuccess = VideoCaptureMgr::create(CooperationManager::videoDataCallback, this);
        }
        else
        {
            m_bVideoCaptureInitSuccess = VideoCaptureMgr::create(CooperationManager::videoDataCallback, this, devicename);
        }
    }

	if(! this->m_bVideoCaptureInitSuccess)
	{
		FVS_DEBUG0("Fail to initialize the Video Capture Sub-System\n");
	}

	// 如果有视频压缩卡,初始化视频采集通道
	TKLInitCompressCard();
}

void CooperationManager::TKLInitCompressCard()
{
	int number = 0;
	if( !TKLSDK_GetTotalChannelNumber(AVE6800, &number) )
	{
		return;
	}
	if( number > MAX_TKL_CHANNEL_NUM )
	{
		this->m_nTKLChannelNumber = MAX_TKL_CHANNEL_NUM;
	}
	else
	{
		m_nTKLChannelNumber = number;
	}
	int nChannelType = AVE6800;//6800卡
    for( int i = 0; i < m_nTKLChannelNumber; i++)
	{
		// open channel
		LONG handle;
		if( !TKLSDK_OpenChannel( i + 1, &nChannelType, &handle ) )
		{
			m_nTKLChannelNumber = 0;
            break;
		}
		m_lTKLHandles[i] = handle;
	}
    for( int i = 0; i < m_nTKLChannelNumber; i++)
	{
		m_hTKLEvents[i] = ::CreateEvent(NULL, TRUE, FALSE, NULL);
		if( !m_hTKLEvents[i] )
		{
			TKLSDK_CloseChannel(m_lTKLHandles[i]);
			continue;
		}
		if( !TKLSDK_RegisterDataRcvEvent(m_lTKLHandles[i], m_hTKLEvents[i]) )
		{
			CloseHandle(m_hTKLEvents[i]);
			TKLSDK_CloseChannel(m_lTKLHandles[i]);
			continue;
		}
		TKLDataRecvThreadParam* param = new TKLDataRecvThreadParam;
		param->pObject = (void*) this;
		param->lHandle = m_lTKLHandles[i];
		m_hTKLDataRecvThreads[i] = (HANDLE)_beginthreadex(NULL, 0, TKLDataRecvProc, param, 0, &m_nTKLDataRecvThreadIds[i]);
		if( !m_hTKLDataRecvThreads[i] )
		{
			CloseHandle(m_hTKLEvents[i]);
			TKLSDK_CloseChannel(m_lTKLHandles[i]);
			continue;
		}
		TRACE1( "init tkl channel %d ok!\n", i+1 );
		TKLStartVideoCapture(m_lTKLHandles[i]);
	}
	::InitializeCriticalSection( &m_csTKL );
}

void CooperationManager::TKLUninitCompressCard()
{
	for( int i = 0; i < m_nTKLChannelNumber; i++ )
	{
		// close channel
		TKLSDK_StopCompress( m_lTKLHandles[i] );
		TKLSDK_CloseChannel( m_lTKLHandles[i] );
		m_bTKLExit[i] = true;
		if( m_hTKLDataRecvThreads[i] )
		{
			SetEvent(m_hTKLEvents[i]);
			WaitForSingleObject(m_hTKLDataRecvThreads[i], INFINITE);
		}
		CloseHandle(m_hTKLEvents[i]);
		delete m_TKLHeads[i];
		m_TKLCustomerMap[i].clear();
	}
	::DeleteCriticalSection( &m_csTKL );
}

void CooperationManager::destroy()
{
	this->m_AVCustomerMap.clear();
    // 关闭视频捕捉设备
	if( this->m_bVideoCaptureInitSuccess )
	{
		VideoCaptureMgr::destroy();
	}
    // 关闭音频捕捉
    AudioCaptureMgr::destroy();

	TKLUninitCompressCard();
}

void CooperationManager::videoDataCallback(void* pObject, LPVIDEOHDR lpVHdr)
{
	CooperationManager* _this = (CooperationManager*)pObject;

    //检测视音频向导是否请求了视频
	AVCustomerMap::iterator it = _this->m_AVCustomerMap.find( 0 );
    if( it != _this->m_AVCustomerMap.end() )
		CVideoPP::videoDataCallback(it->second, lpVHdr);

    it = _this->m_AVCustomerMap.begin();
    while( it != _this->m_AVCustomerMap.end())
    {
		if( it->first != 0 )
	 		TransModelImpl::videoDataCallback(it->second, lpVHdr);
        it++;
    }
}

void CooperationManager::audioDataCallback(void* pObject, WAVEHDR* pWH)
{
	CooperationManager* _this = (CooperationManager*)pObject;
    AVCustomerMap::iterator it = _this->m_AVCustomerMap.begin();
	AVCustomerMap::iterator it2 = _this->m_AVCustomerMap.find( 0 );
	
	//忽略视音频向导
	while( it != _this->m_AVCustomerMap.end() && it != it2)
    {
 		TransModelImpl::audioDataCallback(it->second, pWH);
        it++;
    }
}

void CooperationManager::addAVCustomer(UINT serverId, void* pObject)
{
    m_AVCustomerMap[serverId] = pObject;
}

void CooperationManager::removeAVCustomer(UINT serverId)
{
    AVCustomerMap::iterator it = m_AVCustomerMap.find( serverId );
    if( it != m_AVCustomerMap.end() )
		m_AVCustomerMap.erase(it);
}

void CooperationManager::stopVideoCapture()
{
	if( this->m_bVideoCaptureInitSuccess )
	{
		VideoCaptureMgr::stopCapture();
	}
}

BOOL CooperationManager::startVideoCapture()
{
	if( this->m_bVideoCaptureInitSuccess )
	{
		return VideoCaptureMgr::startCapture();
	}
	return FALSE;
}

void CooperationManager::startAudioCapture()
{
	AudioCaptureMgr::startCapture();
}

void CooperationManager::stopAudioCapture()
{
	AudioCaptureMgr::stopCapture();
}

void CooperationManager::TKLAddCustomer(UINT serverId, void* pObject)
{
	::EnterCriticalSection( &m_csTKL );
	for( int i = 0; i < m_nTKLChannelNumber; i++ )
	{
		m_TKLCustomerMap[i][serverId] = pObject;
	}
	::LeaveCriticalSection( &m_csTKL );
}

void CooperationManager::TKLRemoveCustomer(UINT serverId)
{
	::EnterCriticalSection( &m_csTKL );
	for( int i = 0; i < m_nTKLChannelNumber; i++ )
	{
		AVCustomerMap::iterator it = m_TKLCustomerMap[i].find( serverId );
	    if( it != m_TKLCustomerMap[i].end() )
			m_TKLCustomerMap[i].erase(it);
	}
	::LeaveCriticalSection( &m_csTKL );
}

BOOL CooperationManager::TKLStartVideoCapture(LONG lHandle)
{
	BOOL ret = TRUE;
	if( m_CompressCount[lHandle-1] == 0 )
	{
		//设置视频压缩的比特率
		ret = TKLSDK_SetVideoDataRate(lHandle, 50000);
		//设定视频压缩模式，可变码流(TKLSDK_RATEMODE_VBR)，恒定码流(TKLSDK_RATEMODE_CBR)
		ret = TKLSDK_SetRateMode(lHandle, TKLSDK_RATEMODE_CBR);
		//设定压缩尺寸Ave6800卡支持CIF(PAL 352*288/NTSC 352*240) ，Half D1(PAL 704*288/NTSC 704*480)
		ret = TKLSDK_SetCompressionSize(lHandle, 352, 288);
		//设定帧率
		ret = TKLSDK_SetFrameRate(lHandle, 10);
		//设定压缩质量
		ret = TKLSDK_SetCompressQuality(lHandle, 8, 9, 10);
		//设定流的压缩类型
		ret = TKLSDK_SetStreamType(lHandle, TKLSDK_STREAM_VIDEO);
		//设定关键帧周期
		ret = TKLSDK_SetKeyFramePeriod(lHandle, 20);
		// 开始压缩
		ret = TKLSDK_StartCompress(lHandle);
	}
	m_CompressCount[lHandle-1]++;
	TRACE2( "tkl start capture count: %d, handle: %d\n", m_CompressCount[lHandle-1], lHandle );
	return ret;
}

void CooperationManager::TKLStopVideoCapture(LONG lHandle)
{
	m_CompressCount[lHandle-1]--;
	if( m_CompressCount[lHandle-1] == 0 )
	{
		TKLSDK_StopCompress(lHandle);
	}
	TRACE2( "tkl start capture count: %d, handle: %d\n", m_CompressCount[lHandle-1], lHandle );
}

int CooperationManager::getTKLChannelNumber()
{
	return this->m_nTKLChannelNumber;
}

LONG CooperationManager::getTKLHandle( int nIndex )
{
	if( nIndex < 0 || nIndex >= MAX_TKL_CHANNEL_NUM )
	{
		ASSERT(FALSE);
		return 0;
	}
	return this->m_lTKLHandles[nIndex];
}

UCHAR* CooperationManager::getTKLHead(LONG lHandle)
{
	return this->m_TKLHeads[lHandle-1];
}

UINT WINAPI CooperationManager::TKLDataRecvProc(void* pObject)
{
	TRACE0( "tkl read data thread start\n" );
	TKLDataRecvThreadParam* pParam = (TKLDataRecvThreadParam*) pObject;
	CooperationManager* _this = (CooperationManager*) pParam->pObject;
	LONG lHandle = pParam->lHandle;
	delete pObject;

	long nDataLen = 30000;
	UCHAR buffer[30000];
	BOOL bResult;
	bool bKeyFrame = false;
	while( !_this->m_bTKLExit[lHandle-1] )
	{
		::WaitForSingleObject( _this->m_hTKLEvents[lHandle-1], INFINITE );
		ResetEvent( _this->m_hTKLEvents[lHandle-1] );
		bResult = TKLSDK_GetData( lHandle, buffer, NULL, &nDataLen );
		if( bResult )//读到了数据
		{
			if( _this->m_TKLHeads[lHandle-1] == 0 )
			{
				UCHAR* pHead = new UCHAR[1024];
				memcpy(pHead, buffer, 1024);
				_this->m_TKLHeads[lHandle-1] = pHead;
				_this->TKLStopVideoCapture(lHandle);
				TRACE2("tkl get head data: handle: %d, data len: %d\n", lHandle, nDataLen);
/*				::EnterCriticalSection( &_this->m_csTKL );
				AVCustomerMap::iterator iter = _this->m_TKLCustomerMap[lHandle-1].begin();
				while( iter != _this->m_TKLCustomerMap[lHandle-1].end() )
				{
					TransModelImpl::videoCompressDataCallback(iter->second, lHandle, buffer, 1024, false, true);
					iter++;
				}
				::LeaveCriticalSection( &_this->m_csTKL );
*/				continue;
			}
			// nDataLen现在表示实际读到的长度
			TRACE2( "handle: %d, datalen: %d, ", lHandle, nDataLen );
			int nFrameType = 0;
			if( TKLSDK_CheckFrameType(lHandle, buffer, &nDataLen, &nFrameType) )
			{
				TRACE1( "type: %d\n", nFrameType );
			}
			else
			{
				TRACE0( "type: Unknown\n" );
			}
			bKeyFrame = ( nFrameType == TKLSDK_FRAME_TYPE_I );
			::EnterCriticalSection( &_this->m_csTKL );
			AVCustomerMap::iterator iter = _this->m_TKLCustomerMap[lHandle-1].begin();
			while( iter != _this->m_TKLCustomerMap[lHandle-1].end() )
			{
				TransModelImpl::videoCompressDataCallback(iter->second, lHandle, buffer, nDataLen, bKeyFrame);
				iter++;
			}
			::LeaveCriticalSection( &_this->m_csTKL );
			//_this->m_pTKLPlayer->play( buffer, nDataLen );
			//m_fileData.Write(m_pDataBuffer, nDataLen);
		}
		else
		{
			Sleep(2);
		}
	}
	TRACE0( "tkl read data thread exit\n" );
	return 0;
}
