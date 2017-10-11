#ifndef RUNNING_CONFERENCE_H
#define RUNNING_CONFERENCE_H

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

struct DBUser;
class CMeetingRoomFrame;
class AVManager;
class TransModel;
class ConferenceUser;
class Conference;
class ConferenceModelMgr;
class ConferenceUserMgr;
class DesktopManager;
class FileTransferMgr;
class YuntaiMgr;

class RunningConference : public LyvcMessage::MessageTarget {

private:
	vector<LyvcMessage::BaseMessage*> m_delaySendMsgs;
	__int64 m_idLastConfUser;

	// 指向会议的指针
	Conference* m_pConference;

	// 指向会议室界面的指针
	CMeetingRoomFrame* m_pRoom;

	// 当前用户的指针
	ConferenceUser* m_pUser;

	// 会议模式管理器的指针
	ConferenceModelMgr* m_pConferenceModeMgr;

    // 会议人员的管理器
    ConferenceUserMgr* m_pConferenceUserMgr;

    // 视音频管理器
    AVManager*	m_pAVManager;

	//桌面共享管理器
	DesktopManager* m_pDesktopMgr;

	// 文件传输管理器
	FileTransferMgr* m_pFileTransferMgr;

	YuntaiMgr* m_pYuntaiMgr;

public:
	Conference* getConference() const
	{
		return m_pConference;
	}
	ConferenceUserMgr* getConferenceUserMgr() const
	{
		return m_pConferenceUserMgr;
	}

	//判断用户是否在会议中
	bool isUserInConference(USERID uid);

	//是否是临时会议
	BOOL isInstantConference();
	/*
	 *判断是否具有各种权限
	 */
	// 别人是否有发送视频的权限
	BOOL canSendVideo( __int64 uid );
	// 自己是否有主动观看视频的权限
	BOOL canReceiveVideo();

	// 别人是否有发送音频的权限
	BOOL canSendAudio( __int64 uid );
	// 自己是否有主动收听声音的权限
	BOOL canReceiveAudio();
	
	// 自己是否有申请发言的权限
	BOOL canApplySpeak();
	// uid=0表示自己是否有控制发言的权限，否则表示别人是否能控制发言
	BOOL canControlSpeak( __int64 uid = 0 );
	
	// 自己是否能发送文件
	BOOL canSendFile();
	// 别人是否能接收文件
	BOOL canReceiveFile( __int64 uid );

    // uid=0表示自己是否能发送桌面，否则表示别人是否能发送桌面
	BOOL canSendDesktop( __int64 uid = 0 );
    // uid=0表示自己是否能接收桌面，否则表示别人是否能接收桌面
	BOOL canReceiveDesktop( __int64 uid = 0 );

	// 自己是否能邀请联系人加入会议
	BOOL canInviteUser();
	// 自己是否能驱逐参会人员
	BOOL canKickUser();

	// 自己是否能控制参会人员的视音频
	BOOL canControlUser();
	// 自己是否能接受别人的控制
	BOOL isBeenControledUser();

	YuntaiMgr* getYuntaiMgr() const
	{
		return m_pYuntaiMgr;
	}

	// 获取文件传输管理器指针
	FileTransferMgr* getFileTransferMgr() const
	{
		return m_pFileTransferMgr;
	}

    // 获取指向桌面共享管理器的指针
	DesktopManager* getDesktopManager() const
    {
        return m_pDesktopMgr;
    }

    // 获取视音频管理器的指针
    AVManager* getAVManager() const
    {
        return this->m_pAVManager;
    }

    // 获取指向会议室界面的指针
	CMeetingRoomFrame* getRoom()const
    {
        return m_pRoom;
    };

	// 获取会议名称
	string getConferenceName() const;

    // 获取会议ID
	__int64 getConferenceId() const;

	// 获取当前用户ID
	USERID getSelfId() const;

	// 获取当前用户
	ConferenceUser* getSelf() const;

    // 根据角色ID取得角色名称
    string getRoleName(__int64 nRoleID);

    // 获取某个会议成员
    ConferenceUser* getUser(USERID userID) const;

    // 获得某个会议成员的真实姓名
	string getUserRealName(USERID userId) const;

public:

	// 媒体信号传输中心
	TransModel* m_pTransModel;

public:
	RunningConference(
		Conference* pConference,                 // 召开的会议
		CMeetingRoomFrame* pRoom,                // 会议室界面
		DBUser& dbUser,                          // 用户信息
        LyvcMessage::MatrixC* pMatrixC,          // 底层消息系统
		TransModel* pTransModel,                 // 媒体信号传输中心
		ConferenceModelMgr* pConferenceModeMgr);  // 会议模式管理器

	~RunningConference();
	
	bool create();
	void destroy();

public:
	void cmdSetUserPicture( string fileName, __int64 receiverId = 0 );
    void evaluateVideoQuality( __int64 receiverId, int nQuality );

	// 自己正在输入文字消息
    void cmdWritingMessage( __int64 receiverId, bool bFlag );

	// 指定发言人
	void cmdAppointSpeaker( __int64 uid );

	// 停止发言
	void cmdStopSpeak( __int64 uid );

    // 申请发言
	void cmdApplySpeak();

    // 收看某人，同时停止收看其他人，权限集中控制模式的受控角色调用
    void cmdWatchUser(USERID userId, string beWatched, int nViewMode );

    // 收听某人，同时停止收听其他人，权限集中控制模式的受控角色调用
    void cmdListenToUser(USERID userId, string beListened);

	// 驱逐某个用户
	void cmdKickUser(__int64 userId);

	// 禁止某个用户参加此会议
	void cmdDeleteUserFromConference(__int64 userId);

    // 发送文字信息到某个用户
    void cmdChat(USERID userId, string strContent);

    // 发送文字信息到所有用户
    void cmdChatPublic(string strContent);

	// 邀请某个用户加入会议
	void cmdInviteConference(__int64 contactId, __int64 conferenceId);

	// 发送启动标注消息
	void cmdStartMarkWindow(__int64 uid);

	// 发送或停止发送我的视频
	void sendMyVideo(BOOL bSend);

private:
	// delete 延迟发送的公共消息
	void deleteDelaySendMsgs();

	// 发送延迟发送的公共消息
	void sendDelayMsgs();

    // 加入会议
    void cmdJoinConference();

    // 退出会议
    void cmdQuitConference();

public:
    // Message Handler
    void JoinConferenceResponse(LyvcMessage::BaseMessage* pBaseMessage);
    void UserJoinConference(LyvcMessage::BaseMessage* pBaseMessage);
    void UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage);
    void Broadcast(LyvcMessage::BaseMessage* pBaseMessage);
    void Chat(LyvcMessage::BaseMessage* pBaseMessage);
    void UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage);
    void StartMarkWindow(LyvcMessage::BaseMessage* pBaseMessage);
    void WatchUser(LyvcMessage::BaseMessage* pBaseMessage);
    void ListenToUser(LyvcMessage::BaseMessage* pBaseMessage);
	void ApplySpeak(LyvcMessage::BaseMessage* pBaseMessage);
	void AppointSpeaker(LyvcMessage::BaseMessage* pBaseMessage);
	void StopSpeak(LyvcMessage::BaseMessage* pBaseMessage);
	void WritingMessage(LyvcMessage::BaseMessage* pBaseMessage);
	void EvaluateVideoQuality(LyvcMessage::BaseMessage* pBaseMessage);
	void SetUserPicture(LyvcMessage::BaseMessage* pBaseMessage);

};
#endif //CONFERENCE_H
