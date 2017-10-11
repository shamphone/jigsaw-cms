#pragma once

class TransModel;
class CMeetingRoomFrame;
class UserVideoList;
class ConferenceUserMgr;

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

class AVManager : public LyvcMessage::MessageTarget {

private:

    // 当前用户ID
	__int64 m_userId;

	// 当前会议ID
	__int64 m_conferenceId;

	// 会议室界面
    CMeetingRoomFrame* m_pRoom;

    // 传输模型
    TransModel* m_pTransModel;

    // 当前用户正在收听的人员ID列表
	list<USERID> m_AudioReceiveList;

    // 正在接收当前用户音频信号的人员ID列表
	list<USERID> m_AudioSendList;

    // 当前用户正在收看的人员ID列表
	list<USERID> m_VideoReceiveList;

    // 正在接收当前用户视频信号的人员ID列表
	list<USERID> m_VideoSendList;

	// 申请过声音的人员
	list<USERID> m_AudioApplyList;

	// 申请过图像的人员
	list<USERID> m_VideoApplyList;

	// 界面请求显示某个用户的视频, 但是需要经过
    // 协议传递才能最终确认显示,下面的数据结构存放
    // 用户ID和窗口句柄的对应表
    map<USERID, HWND> m_userVideoWindowMap;

	map<USERID, list<USERID>* > m_VideoSendListMap;

	//是否正在看本地压缩视频
	bool m_bTKLVideo[4];

public:

	AVManager(
		__int64 userId,                   // 当前用户ID
		__int64 conferenceId,             // 当前会议ID
		CMeetingRoomFrame* pRoom,         // 会议室界面
        LyvcMessage::MatrixC* pMatrixC,   // 消息系统
        TransModel* pTransModel);         // 媒体信号传输中心
	~AVManager();

	bool m_bIsInstantConf;

    bool create();
    void destroy();

	// 在压缩卡采集得到的视频数据和本地视频窗口之间建立关联
	void initCompressionCardVideoWindow();


	/*
	 * 因为图像要在本地窗口显示，所以它的控制分为两个部分，
	 * 是否发送和是否捕捉，这一点要比声音复杂一些。
	 */

    // 开始发送音频信号
	void StartAudioSend();

    // 停止发送音频信号
    void EndAudioSend();

    // 开始视频捕捉
    void StartVideoCapture();

    // 停止视频捕捉
    void EndVideoCapture();

    // 开始发送视频信号
	void StartVideoSend(__int64 videoSenderID);

    // 停止发送视频信号
	void EndVideoSend(__int64 videoSenderID);


    // 开始接收某个用户发送的音频信号
    void StartAudioRecv(USERID userId);

    // 停止接收某个用户发送的音频信号
    void EndAudioRecv(USERID userId);

    // 开始接收某个用户发送的视频信号
    void StartVideoRecv(USERID userId, HWND hVideoWnd, int nwidth, int nheight);

    // 停止接收某个用户发送的视频信号
    void EndVideoRecv(USERID userId);

public:
	void getApplyAndStopVideoList( list<__int64>& applyList, list<__int64>& stopList);
	void getApplyAndStopAudioList( list<__int64>& applyList, list<__int64>& stopList);

	// 开始观看某人，提供给高层对象的接口
    void StartReceiveVideoFromUser(USERID userId, HWND hWnd);

    // 停止观看某人，提供给高层对象的接口
    void StopReceiveVideoFromUser(USERID userId);

    // 开始收听某人，提供给高层对象的接口
    void StartReceiveAudioFromUser(USERID userId);

    // 停止收听某人，提供给高层对象的接口
    void StopReceiveAudioFromUser(USERID userId);

    // 是否在收听某人
    bool isReceiveAudio(USERID userId);

    // 是否在收看某人
    bool isReceiveVideo(USERID userId);

public:
    
    // Message Handler
    void ApplyAudio(LyvcMessage::BaseMessage* pBaseMessage);
    void RejectAudio(LyvcMessage::BaseMessage* pBaseMessage);
    void AgreeAudio(LyvcMessage::BaseMessage* pBaseMessage);
    void StopSendAudio(LyvcMessage::BaseMessage* pBaseMessage);
    void StopReceiveAudio(LyvcMessage::BaseMessage* pBaseMessage);
    void ApplyVideo(LyvcMessage::BaseMessage* pBaseMessage);
    void RejectVideo(LyvcMessage::BaseMessage* pBaseMessage);
    void AgreeVideo(LyvcMessage::BaseMessage* pBaseMessage);
    void StopSendVideo(LyvcMessage::BaseMessage* pBaseMessage);
    void StopReceiveVideo(LyvcMessage::BaseMessage* pBaseMessage);
    void UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage);
    void UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage);

private:

	void AVManager::ProcessApplyVideo(_int64 recevierId);

private:

    // 请求声音
    void cmdApplyAudio(USERID userId);

    // 同意声音
    void cmdAgreeAudio(USERID userId);

    // 拒绝声音
    void cmdRejectAudio(USERID userId);

    // 停止接收声音
    void cmdStopReceiveAudio(USERID userId);

    // 停止发送声音
    void cmdStopSendAudio(USERID userId);

    // 请求图像
    void cmdApplyVideo(USERID userId);

    // 同意图像
    void cmdAgreeVideo(USERID userId, USERID videoSenderId);

    // 拒绝图像
    void cmdRejectVideo(USERID userId);

    // 停止接收图像
    void cmdStopReceiveVideo(USERID userId);

    // 停止发送图像
    void cmdStopSendVideo(USERID videoSenderId, USERID receiverId);

public:
	static HWND localVideoWindow;

};

