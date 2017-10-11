#pragma once

class ConferenceUserMgr;
class ConferenceUser;

#include "TransModel.h"
#include "common/common/MediaPacket/tcp_media_def.h"
#include "common/common/MediaPacket/udp_media_def.h"
#include "SplitUDPPack.h"

namespace LyvcMessage
{
	class MatrixC;
};

class TcpLink;
class UdpLink;
class MultiAudioPlay;
class MultiVideoPlay;
class AudioCodec;
class VideoEncoder;
class VideoDecoder;
class DesktopServer;
class DesktopClient;
class CWhiteboardDlg;

class TransModelImpl : public TransModel
{
public:
	TransModelImpl(__int64 userId, LyvcMessage::MatrixC* pMatrixC);
	~TransModelImpl();

	virtual bool Create();
	virtual void Destroy();

private:
	map<__int64, CWhiteboardDlg*> m_whiteboardMap;
	CRITICAL_SECTION m_WhiteboardCs;

public:
	virtual void addWhiteboard( __int64 confId, void* pVoid );
	virtual void removeWhiteboard( __int64 confId );
	virtual void sendWhiteboardData( __int64 confId, char* buffer, int len );

	// 增加一路声音播放
    virtual bool addUserAudioPlay(__int64 userId);

	// 删除一路声音播放
    virtual bool removeUserAudioPlay(__int64 userId);

	// 开始发送声音
	virtual void startSendAudio();

	// 停止发送声音
	virtual void stopSendAudio();

public:

	// 增加一路图像显示
    virtual bool addUserVideoPlay(__int64 userId, HWND hVideoWnd, __int64 conferenceId, int nwidth, int nheight);

	// 删除一路图像显示
    virtual bool removeUserVideoPlay(__int64 userId, __int64 conferenceId);

    // 增加一路本地视频显示
    virtual bool addLocalVideoWindow(HWND hWnd, __int64 conferenceId);

    // 删除一路本地视频显示
    virtual bool removeLocalVideoWindow(__int64 conferenceId);

	// 开始发送视频
	virtual void startSendVideo(__int64 userId);

	// 停止发送视频
	virtual void stopSendVideo(__int64 userId);

	// 用户设置是否发送视频
	virtual void EnableSendVideo(BOOL bSend);

	// Set frame rate
	virtual void setFrameRateAndKeyFrameInterval(int frameRate, int keyFrameInterval);

public:

	// 开始发送桌面
	virtual void startSendDesktop();

	// 停止发送桌面
	virtual void stopSendDesktop();

	// 开始接受某个用户的桌面
	virtual void startReceiveUserDesktop(__int64 userId, string userName, ClientNotifier* pClientNotifier);

	// 停止接受某个用户的桌面
	virtual void stopReceiveUserDesktop(__int64 userId);

	// 开始控制某个用户的桌面
	virtual void startControlUserDesktop(__int64 userId);

	// 停止控制某个用户的桌面
	virtual void stopControlUserDesktop(__int64 userId);

	// 找出当前观看的桌面中最上层的那个
	virtual __int64 getTopDesktopClientId();

public:

    //设置TCP服务器的ip和端口，并创建Socket和相应线程
    virtual BOOL setTcpServer(string IP, int port);

	// 发送TCP数据，返回发送的结果
	virtual BOOL sendTCPData(char* buf, DWORD nlen);

	//设置UDP服务器的ip和端口，并创建Socket和相应线程
	virtual BOOL setUdpServer(string IP, int port);

	// 发送UDP数据, 返回发送的结果
	virtual int sendUDPData(char *buf, DWORD nlen);

    // 采集声音数据的回调函数
    static void audioDataCallback(void* pObject, WAVEHDR* pWH);

    // 采集图像数据的回调函数
	static void videoDataCallback(void* pObject, LPVIDEOHDR lpVHdr);

private:

	// called periodically to send udp addr pack
    static void CALLBACK TimerFunction(UINT wTimerID, UINT msg, DWORD dwUser, DWORD dw1, DWORD dw2);

    // 接收从媒体服务器传来的UDP数据回调函数
	static void receiveUDPData(void* pObject, char* buf, int len);

    // 接收从媒体服务器传来的TCP数据回调函数
	static void receiveTCPData(void* pObject, char* buf, int len);

	// 桌面捕捉的回调函数
	static void desktopServerCallback(void* pObject, char* buffer, int len);

    // 本地用户的ID
	__int64 m_userId;

	// 服务器消息系统
	LyvcMessage::MatrixC* m_pMatrixC;

	// Object to capture the desktop
	DesktopServer* m_pDesktopServer;

	// Object to display and manager peer's desktops
	DesktopClient* m_pDesktopClient;
	CRITICAL_SECTION m_DesktopClientCs;

    // 到服务器的UDP连接
	UdpLink* m_pServerUdpLink;

    // 维持UDP NAT Session的定时器
	UINT m_idTimerEvent;

    // 到媒体服务器的TCP连接
	TcpLink* m_pServerTcpLink;

	//多路声音播放控制器
	MultiAudioPlay*	m_pMultiAudioPlay;
	CRITICAL_SECTION m_AudioPlayCs;

	//多路图像播放控制器
	MultiVideoPlay* m_pMultiVideoPlay;
	CRITICAL_SECTION m_VideoPlayCs;

    //声音发送的编码器
	AudioCodec* m_pAudioEncoder;

	//视频发送的编码器
    VideoEncoder* m_pVideoEncoder;

	int				m_nCurVideoBitRate;	//当前实际使用的比特率
	int				m_nKeyFrameInterval;//当前关键帧间隔

    DWORD			m_dwAudioSeqNo;
    char			m_audioPack[MAX_UDP_SIZE];

    DWORD			m_dwVideoSeqNo;
    char			m_videoPack[MAX_UDP_SIZE];

    // 是否根据以太网的MTU对视频关键帧进行拆分，默认拆分
    bool m_splitKeyFrameFlag;

	// startSendAudio被调用的次数
	// startSendAudio增加该数字， stopSendAudio减少该数字
	// 如果这个数字不为0，则开始发送
	// 如果这个数字为0，则停止发送
	LONG volatile m_nStartSendAudioCallCount;

	// startSendVideo被调用的次数
	// startSendVideo增加该数字， stopSendVideo减少该数字
	// 如果这个数字不为0，则开始发送
	// 如果这个数字为0，则停止发送
	LONG volatile m_nStartSendVideoCallCount;

	// startSendDesktop被调用的次数
	// startSendDesktop增加该数字， stopSendDesktop减少该数字
	// 如果这个数字不为0，则开始发送
	// 如果这个数字为0，则停止发送
	LONG volatile m_nStartSendDesktopCallCount;

    // 发送视频数据
	void SendEncodedVideoData(DWORD encodedLen, bool bKeyFrame);

private:

	// 用户设置是否发送视频
	BOOL m_bSendVideo;

public:
    // 视频压缩卡采集图像数据的回调函数
	static void videoCompressDataCallback(void* pObject, LONG handle, UCHAR* pData, int nLen, bool bKeyFrame);

	// 设置接收视频压缩卡数据的头数据
	virtual void setTKLHead(__int64 userId, UCHAR* pHead, int nLen = 1024);

private:
	// 视频压缩卡每一路采集通道的本地播放器数目
	LONG m_LocalTKLPlayer[4];
	// 视频压缩卡每一路采集通道的远程播放器数目
	LONG m_RemotelTKLPlayer[4];

	// 为视频压缩卡每一路采集通道准备一个帧序号
	DWORD m_dwTKLSeqNo[4];

	/*
	 *关键帧拆分与组合
	*/
private:
	// 拆分后的数据块
	char partialBlocks[PARTIAL_BLOCK_COUNT][MAX_ETHERNET_UDP + sizeof(PACK_VIDEO)];
	// 每块大小
	int partialBlockSize[PARTIAL_BLOCK_COUNT];
	// 重新组合数据的缓冲区
	BufferedKeyFrame bufferedKeyFrames[BUFFERED_KEYFRAME_NUMBER];

};

