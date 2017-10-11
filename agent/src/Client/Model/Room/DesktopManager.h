#pragma once

#include "message/MatrixC/MatrixCLib/MessageTarget.h"
#include "Desktop/LyvcDesktopClient/ClientNotifier.h"

class RunningConference;
class DesktopClient;
class DesktopServer;
class Notifier;
class TransModel;

class DesktopManager : public LyvcMessage::MessageTarget 
{
public:
	DesktopManager(
        LyvcMessage::MatrixC* pMatrixC,   // 消息系统
		RunningConference* pConference,   // 会议对象
		TransModel* pTransModel);         // 媒体传送中心
	~DesktopManager(void);

	bool create();
	void destroy();

	// 停止提供桌面共享
	void stopDesktopServer();

//消息处理
public:
	//桌面共享
    void ApplyDesktop(LyvcMessage::BaseMessage* pBaseMessage);//申请
	void AgreeDesktop(LyvcMessage::BaseMessage* pBaseMessage);//同意申请
	void RejectDesktop(LyvcMessage::BaseMessage* pBaseMessage);//拒绝
	void InviteDesktop(LyvcMessage::BaseMessage* pBaseMessage);//邀请
	void AgreeInviteDesktop(LyvcMessage::BaseMessage* pBaseMessage);//同意邀请
	void RejectInviteDesktop(LyvcMessage::BaseMessage* pBaseMessage);//拒绝邀请
	void StopReceiveDesktop(LyvcMessage::BaseMessage* pBaseMessage);//服务端停止指定客户端观看共享
	void StopSendDesktop(LyvcMessage::BaseMessage* pBaseMessage);//客户端主动停止了观看共享
    void DesktopClientRequestUpdate(LyvcMessage::BaseMessage* pBaseMessage); //客户端请求开始更新

    //远程控制
    void ApplyControlDesktop(LyvcMessage::BaseMessage* pBaseMessage);//申请
	void AgreeControlDesktop(LyvcMessage::BaseMessage* pBaseMessage);//同意
	void RejectControlDesktop(LyvcMessage::BaseMessage* pBaseMessage);//拒绝
	void StopSendControlDesktop(LyvcMessage::BaseMessage* pBaseMessage);//服务端收回控制
	void StopReceiveControlDesktop(LyvcMessage::BaseMessage* pBaseMessage);//客户端放弃控制

    void UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage);
    void UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage); //某个客户端意外退出

//消息发送
public:
	//桌面共享
	void cmdApplyDesktop(USERID userId);
	void cmdAgreeDesktop(USERID userId);
	void cmdRejectDesktop(USERID userId);
	void cmdInviteDesktop(USERID userId);
	void cmdAgreeInviteDesktop(USERID userId);
	void cmdRejectInviteDesktop(USERID userId);
	void cmdStopReceiveDesktop(USERID userId);
	void cmdStopSendDesktop(USERID userId);

	//远程控制
	void cmdApplyControlDesktop(USERID userId);
	void cmdAgreeControlDesktop(USERID userId);
	void cmdRejectControlDesktop(USERID userId);
	void cmdStopSendControlDesktop(USERID userId);//客户端放弃控制
	void cmdStopReceiveControlDesktop();//服务端收回控制

public:
	//是否可以邀请该用户
	BOOL canInviteDesktop( __int64 uid );

	//是否可以停止该用户观看我的桌面
	BOOL canStopSendDesktopToUser( __int64 uid = 0 );
	
	//是否可以向该用户申请观看
	BOOL canApplyDesktop( __int64 uid );

	//是否可以停止观看该用户
	BOOL canStopReceiveDesktopFromUser( __int64 uid = 0 );

	//是否可以申请控制
	BOOL canApplyControlDesktop( __int64 uid = 0 );

	//是否可以停止控制
	BOOL canStopSendControlDesktopToUser( __int64 uid = 0 );

	//是否可以停止接受控制
	BOOL canStopReceiveControlDesktopFromUser( __int64 uid = 0 );

	//当前观看窗口在所有窗口最顶层的用户id,为0没有
	__int64 getTopDesktopClientId();

private:
	//是否正在申请
	BOOL isUserInApplyList(__int64 uid);

	//是否正在邀请
	BOOL isUserInInviteList(__int64 uid);

	//是否在看自己
	BOOL isUserInClientList(__int64 uid);

	//自己是否在看对方
	BOOL isUserInServerList(__int64 uid);

	//自己是否在控制对方
	BOOL isControlUser(__int64 uid);

	//是否被用户控制
	BOOL isControledByUser(__int64 uid);

private:
	// 媒体传送中心
	TransModel* m_pTransModel;
	// 所在会议
	RunningConference*	m_pConference;
	// 正在观看自己屏幕的用户
	list<USERID> m_DesktopClientList;
	// 自己正在观看的用户
	list<USERID> m_DesktopServerList;
	// 正在控制自己屏幕的用户
	USERID m_ControlBy;
	// 自己正在控制的用户
	list<USERID> m_UnderControlList;
	// 自己正在申请观看的用户
	list<USERID> m_ApplyList;
	// 自己正在申请控制的用户
	list<USERID> m_ApplyControlList;
	// 自己正在邀请观看的用户
	list<USERID> m_InviteList;
	// 正在申请观看自己的用户列表
	list<USERID> m_ApplyMeList;
	// 正在邀请自己观看的用户列表
	list<USERID> m_InviteMeList;
	// 正在申请控制自己的用户列表
	list<USERID> m_ApplyControlMeList;

	Notifier* m_notifier;
};

class Notifier : public ClientNotifier
{
public:
	virtual void clientClosed(__int64 uid);

	void setManager(DesktopManager* manager);

private:
	DesktopManager* m_manager;	
};

