#pragma once

class FvsmUser
{
public:
	FvsmUser();
	~FvsmUser();

	SOCKADDR		m_udpAddr;	        // 用户的UDP地址
	__int64			m_userId;           // 用户ID

    // 增加一个媒体信号接收者
	BOOL addReceiver(__int64 userId, int dataType);	

    // 删除一个媒体信号接收者
	BOOL removeReceiver(__int64 userId, int dataType);
	
    //接受自己声音的人
	list<__int64> m_audioReceivers;

    //接受自己图像的人
	list<__int64> m_videoReceivers;

    // 是否正在接受参数中指定用户的声音
    BOOL isReceiveAudioFromUser(__int64 userId);

    // 是否正在接受参数中指定用户的图像
    BOOL isReceiveVideoFromUser(__int64 userId);
};

