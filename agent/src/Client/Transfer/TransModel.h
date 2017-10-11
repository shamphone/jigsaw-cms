#pragma once

class ClientNotifier;

class TransModel
{
public:
	TransModel()
	{
	};

	virtual ~TransModel()
	{
	};

	virtual bool Create() = 0;
	virtual void Destroy() = 0;

// used by AVManager

public:
	// 白板
	virtual void addWhiteboard( __int64 confId, void* pVoid ) = 0;
	virtual void removeWhiteboard( __int64 confId ) = 0;
	virtual void sendWhiteboardData( __int64 confId, char* buffer, int len ) = 0;

	// 增加一路声音播放
    virtual bool addUserAudioPlay(__int64 userId) = 0;

	// 删除一路声音播放
    virtual bool removeUserAudioPlay(__int64 userId) = 0;

	// 开始发送声音
	virtual void startSendAudio() = 0;

	// 停止发送声音
	virtual void stopSendAudio() = 0;

	// 增加一路图像显示
    virtual bool addUserVideoPlay(__int64 userId, HWND hVideoWnd, __int64 conferenceId, int nwidth, int nheight) = 0;

	// 删除一路图像显示
    virtual bool removeUserVideoPlay(__int64 userId, __int64 conferenceId) = 0;

    // 增加一路本地视频显示
    virtual bool addLocalVideoWindow(HWND hWnd, __int64 conferenceId) = 0;

    // 删除一路本地视频显示
    virtual bool removeLocalVideoWindow(__int64 conferenceId) = 0;

	// 开始发送视频
	virtual void startSendVideo(__int64 userId) = 0;

	// 停止发送视频
	virtual void stopSendVideo(__int64 userId) = 0;

// used by RunningConference.cpp

	// 用户设置是否发送视频
	virtual void EnableSendVideo(BOOL bSend) = 0;

// used by DesktopManager

	// Set frame rate
	virtual void setFrameRateAndKeyFrameInterval(int frameRate, int keyFrameInterval) = 0;

public:

	// 开始发送桌面
	virtual void startSendDesktop() = 0;

	// 停止发送桌面
	virtual void stopSendDesktop() = 0;

	// 开始接受某个用户的桌面
	virtual void startReceiveUserDesktop(__int64 userId, string userName, ClientNotifier* pClientNotifier) = 0;

	// 停止接受某个用户的桌面
	virtual void stopReceiveUserDesktop(__int64 userId) = 0;

	// 开始控制某个用户的桌面
	virtual void startControlUserDesktop(__int64 userId) = 0;

	// 停止控制某个用户的桌面
	virtual void stopControlUserDesktop(__int64 userId) = 0;

	// 找出当前观看的桌面中最上层的那个
	virtual __int64 getTopDesktopClientId() = 0;

public:

// used by LServer

	//设置UDP服务器的ip和端口，并创建Socket和相应线程
	virtual BOOL setUdpServer(string IP, int port) = 0;

    //设置TCP服务器的ip和端口，并创建Socket和相应线程
    virtual BOOL setTcpServer(string IP, int port) = 0;

// used by self

	// 发送UDP数据, 返回发送的结果
	virtual int sendUDPData(char *buf, DWORD nlen) = 0;

	// 发送TCP数据，返回发送的结果
	virtual BOOL sendTCPData(char* buf, DWORD nlen) = 0;

	// 设置视频压缩卡头数据
	virtual void setTKLHead(__int64 userId, UCHAR* pHead, int nLen = 1024) = 0;
};

