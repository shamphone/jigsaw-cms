#include "stdafx.h"
#include "FLVCC.h"
#include "DesktopManager.h"
#include ".\desktopmanager.h"
#include "Desktop\LyvcDesktopClient\DesktopClient.h"
#include "Desktop\LyvcDesktopServer\DesktopServer.h"
#include "message\MatrixC\MatrixCLib\MatrixC.h"
#include "Model\Room\RunningConference.h"
#include "UI\MeetingRoom\RoomMainFrm.h"
#include "UI\Mainfrm\UserDefinedMessage.h"
#include "Transfer\TransModel.h"
#include "Common\Common\RegistryConstant\RegistryConstant.h"

#include "message/MatrixC/MatrixCLib/message/AgreeControlDesktop.h"
#include "message/MatrixC/MatrixCLib/message/AgreeDesktop.h"
#include "message/MatrixC/MatrixCLib/message/AgreeInviteDesktop.h"
#include "message/MatrixC/MatrixCLib/message/ApplyControlDesktop.h"
#include "message/MatrixC/MatrixCLib/message/ApplyDesktop.h"
#include "message/MatrixC/MatrixCLib/message/InviteDesktop.h"
#include "message/MatrixC/MatrixCLib/message/RejectControlDesktop.h"
#include "message/MatrixC/MatrixCLib/message/RejectDesktop.h"
#include "message/MatrixC/MatrixCLib/message/RejectInviteDesktop.h"
#include "message/MatrixC/MatrixCLib/message/StopReceiveControlDesktop.h"
#include "message/MatrixC/MatrixCLib/message/StopReceiveDesktop.h"
#include "message/MatrixC/MatrixCLib/message/StopSendControlDesktop.h"
#include "message/MatrixC/MatrixCLib/message/StopSendDesktop.h"
#include "message/MatrixC/MatrixCLib/message/DesktopClientRequestUpdate.h"
#include "message/MatrixC/MatrixCLib/message/UserChannelBroken.h"
#include "message/MatrixC/MatrixCLib/message/UserQuitConference.h"

DesktopManager::DesktopManager(
        LyvcMessage::MatrixC* pMatrixC, 
        RunningConference* pConference,
        TransModel* pTransModel) : LyvcMessage::MessageTarget(pMatrixC)
{
	m_pTransModel = pTransModel;
	m_pConference = pConference;

	m_pMatrixC->registerMessageHandler(LyvcMessage::AgreeControlDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::AgreeControlDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AgreeDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::AgreeDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AgreeInviteDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::AgreeInviteDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ApplyControlDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::ApplyControlDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ApplyDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::ApplyDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::InviteDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::InviteDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::RejectControlDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::RejectControlDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::RejectDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::RejectDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::RejectInviteDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::RejectInviteDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::StopReceiveControlDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::StopReceiveControlDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::StopReceiveDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::StopReceiveDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::StopSendControlDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::StopSendControlDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::StopSendDesktop::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::StopSendDesktop));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserChannelBroken::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::UserChannelBroken));
	m_pMatrixC->registerMessageHandler(LyvcMessage::DesktopClientRequestUpdate::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::DesktopClientRequestUpdate));
	m_pMatrixC->registerMessageHandler(LyvcMessage::UserQuitConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(DesktopManager::UserQuitConference));
	m_ControlBy = 0;
	
	m_notifier = new Notifier();
	m_notifier->setManager(this);
}

DesktopManager::~DesktopManager(void)
{
    m_pMatrixC->removeObjectMessageHandler(this);
	delete m_notifier;
}

bool DesktopManager::create()
{
    return true;
}

void DesktopManager::destroy()
{
	unsigned int i;
	unsigned int count;

	// 对于所有正在接收我们桌面信号的人，通知会议服务器不再发送
    // 同时通知TransModel不再发送
	count = m_DesktopClientList.size();
    for(i = 0; i < count; i++)
	{
		__int64& userId = m_DesktopClientList.front();
        this->cmdStopSendDesktop(userId);
    }

    // 对于所有我们正在接收桌面信号的人，通知会议服务器和TransModel不再观看
	count = m_DesktopServerList.size();
    for(i = 0; i < count; i++)
	{
		__int64& userId = m_DesktopServerList.front();
		this->cmdStopReceiveDesktop(userId);
	}
}

void DesktopManager::stopDesktopServer()
{
	unsigned int i;
	unsigned int count;
	count = m_DesktopClientList.size();
    for(i = 0; i < count; i++)
	{
		__int64& userId = m_DesktopClientList.front();
        this->cmdStopSendDesktop(userId);
    }
	m_ControlBy = 0;
}

void DesktopManager::ApplyDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::ApplyDesktop* msg = (LyvcMessage::ApplyDesktop*) pBaseMessage;

    // 如果已经申请过了，不再提示
	if( find(m_ApplyMeList.begin(), m_ApplyMeList.end(), msg->senderId) == m_ApplyMeList.end() )
	{
		m_ApplyMeList.push_back( msg->senderId );

		__int64* pId = new __int64( msg->senderId );
		::PostMessage( m_pConference->getRoom()->GetSafeHwnd(), WM_APPLY_DESKTOP, (WPARAM) pId, 0 );
		string str = m_pConference->getUserRealName( msg->senderId ) + string("申请共享桌面");
		m_pConference->getRoom()->notifyConferenceMsg( str );
	}
}

void DesktopManager::AgreeDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::AgreeDesktop* pMsg = (LyvcMessage::AgreeDesktop*) pBaseMessage;

	//判断是否在申请列表里
	if( find(m_ApplyList.begin(), m_ApplyList.end(), pMsg->senderId) != m_ApplyList.end() )
	{
		m_DesktopServerList.push_back(pMsg->senderId);
		this->m_pTransModel->startReceiveUserDesktop(pMsg->senderId, m_pConference->getUserRealName(pMsg->senderId), this->m_notifier);
		m_ApplyList.remove(pMsg->senderId);

		LyvcMessage::DesktopClientRequestUpdate msg;
		msg.receiverId = pMsg->senderId;
		m_pMatrixC->sendMessage(&msg);

		string str = m_pConference->getUserRealName( pMsg->senderId ) + string("同意了您的桌面共享请求");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0,(LPARAM)str );
	}
	if( find(m_InviteList.begin(), m_InviteList.end(), pMsg->senderId) != m_InviteList.end() )
	{
		m_InviteList.remove( pMsg->senderId );
	}
}

void DesktopManager::RejectDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::RejectDesktop* msg = (LyvcMessage::RejectDesktop*) pBaseMessage;

	if( find(m_ApplyList.begin(), m_ApplyList.end(), msg->senderId) != m_ApplyList.end() )
	{
		m_ApplyList.remove(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("拒绝了您的桌面共享请求");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
	if( find(m_InviteList.begin(), m_InviteList.end(), msg->senderId) != m_InviteList.end() )
	{
		m_InviteList.remove( msg->senderId );
	}
}

void DesktopManager::InviteDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::InviteDesktop* msg = (LyvcMessage::InviteDesktop*) pBaseMessage;

	// 如果已经邀请过了，不再提示 
	if( find(m_InviteMeList.begin(), m_InviteMeList.end(), msg->senderId) == m_InviteMeList.end() )
	{
		m_InviteMeList.push_back( msg->senderId );

		__int64* pId = new __int64( msg->senderId );
		::PostMessage( m_pConference->getRoom()->GetSafeHwnd(), WM_INVITE_DESKTOP, (WPARAM) pId, 0 );
		string str = m_pConference->getUserRealName( msg->senderId ) + string("邀请观看共享桌面");
		m_pConference->getRoom()->notifyConferenceMsg( str );
	}
}

void DesktopManager::AgreeInviteDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::AgreeInviteDesktop* msg = (LyvcMessage::AgreeInviteDesktop*) pBaseMessage;

	if( find(m_InviteList.begin(), m_InviteList.end(), msg->senderId) != m_InviteList.end() )
	{
		m_DesktopClientList.push_back(msg->senderId);
		this->m_pTransModel->startSendDesktop();
		m_InviteList.remove(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("同意了您的桌面共享邀请");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
	if( find(m_ApplyList.begin(), m_ApplyList.end(), msg->senderId) != m_ApplyList.end() )
	{
		m_ApplyList.remove( msg->senderId );
	}
}

void DesktopManager::RejectInviteDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::RejectInviteDesktop* msg = (LyvcMessage::RejectInviteDesktop*) pBaseMessage;

	if( find(m_InviteList.begin(), m_InviteList.end(), msg->senderId) != m_InviteList.end() )
	{
		m_InviteList.remove(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("拒绝了您的桌面共享邀请");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
	if( find(m_ApplyList.begin(), m_ApplyList.end(), msg->senderId) != m_ApplyList.end() )
	{
		m_ApplyList.remove( msg->senderId );
	}
}

void DesktopManager::StopSendDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::StopSendDesktop* msg = (LyvcMessage::StopSendDesktop*) pBaseMessage;

	if( find( m_DesktopClientList.begin(), m_DesktopClientList.end(), msg->senderId) != m_DesktopClientList.end() )
	{
		m_DesktopClientList.remove(msg->senderId);
		this->m_pTransModel->stopSendDesktop();
		if (m_ControlBy == msg->senderId)
		{
			m_ControlBy = 0;
		}

		string str = m_pConference->getUserRealName( msg->senderId ) + string("退出了您的桌面共享" );
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void DesktopManager::DesktopClientRequestUpdate(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::DesktopClientRequestUpdate* pMsg = (LyvcMessage::DesktopClientRequestUpdate*) pBaseMessage;
	if( pMsg->receiverId == m_pConference->getSelfId() )
	{
		m_DesktopClientList.push_back(pBaseMessage->_senderId);
        m_pTransModel->startSendDesktop();
	}
}

void DesktopManager::StopReceiveDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::StopReceiveDesktop* msg = (LyvcMessage::StopReceiveDesktop*) pBaseMessage;

	if( find( m_DesktopServerList.begin(), m_DesktopServerList.end(), msg->senderId) != m_DesktopServerList.end() )
	{
		m_DesktopServerList.remove(msg->senderId);
		this->m_UnderControlList.remove(msg->senderId);
		m_pTransModel->stopReceiveUserDesktop(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("停止了您的桌面共享");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void DesktopManager::ApplyControlDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::ApplyControlDesktop* msg = (LyvcMessage::ApplyControlDesktop*) pBaseMessage;

	if( find(m_ApplyControlMeList.begin(), m_ApplyControlMeList.end(), msg->senderId) == m_ApplyControlMeList.end() )
	{
		m_ApplyControlMeList.push_back( msg->senderId );

		__int64* pId = new __int64( msg->senderId );
		::PostMessage( m_pConference->getRoom()->GetSafeHwnd(), WM_APPLY_CONTROL_DESKTOP, (WPARAM) pId, 0 );
		string str = m_pConference->getUserRealName( msg->senderId ) + string("申请桌面控制");
		m_pConference->getRoom()->notifyConferenceMsg( str );
	}
}

void DesktopManager::AgreeControlDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::AgreeControlDesktop* msg = (LyvcMessage::AgreeControlDesktop*) pBaseMessage;

	if( find(m_ApplyControlList.begin(), m_ApplyControlList.end(), msg->senderId) != m_ApplyControlList.end() )
	{
		m_UnderControlList.push_back(msg->senderId);
		m_pTransModel->startControlUserDesktop(msg->senderId);
		m_ApplyControlList.remove(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("同意接受桌面控制");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void DesktopManager::RejectControlDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::RejectControlDesktop* msg = (LyvcMessage::RejectControlDesktop*) pBaseMessage;

	if( find(m_ApplyControlList.begin(), m_ApplyControlList.end(), msg->senderId) != m_ApplyControlList.end() )
	{
		m_ApplyControlList.remove(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("拒绝接受桌面控制");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void DesktopManager::StopSendControlDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::StopSendControlDesktop* msg = (LyvcMessage::StopSendControlDesktop*) pBaseMessage;

	if( find(m_UnderControlList.begin(), m_UnderControlList.end(), msg->senderId) != m_UnderControlList.end() )
	{
		m_UnderControlList.remove(msg->senderId);
		m_pTransModel->stopControlUserDesktop(msg->senderId);

		string str = m_pConference->getUserRealName( msg->senderId ) + string("收回了桌面控制权");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void DesktopManager::StopReceiveControlDesktop(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::StopReceiveControlDesktop* msg = (LyvcMessage::StopReceiveControlDesktop*) pBaseMessage;

	if( m_ControlBy == msg->senderId )
	{
		m_ControlBy = 0;

		string str = m_pConference->getUserRealName( msg->senderId ) + string("停止了桌面控制");
		m_pConference->getRoom()->notifyConferenceMsg( str );
		//::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void DesktopManager::UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UserChannelBroken* msg = (LyvcMessage::UserChannelBroken*)pBaseMessage;
	__int64 userId = msg->userId;

	//如果正在观看自己, 清除这个用户，通知TransModel停止发送
    if(find( m_DesktopClientList.begin(), m_DesktopClientList.end(), userId) != m_DesktopClientList.end() )
	{
		m_DesktopClientList.remove(userId);
        this->m_pTransModel->stopSendDesktop();
	}

    // 如果正在被这个用户控制，那么清除标志
	if(userId == m_ControlBy)
	{
		m_ControlBy = 0;
	}

	//如果自己正在观看这个用户，将其清除
	if( find(m_DesktopServerList.begin(), m_DesktopServerList.end(), userId) != m_DesktopServerList.end())
	{
		m_DesktopServerList.remove(userId);
		m_pTransModel->stopReceiveUserDesktop(userId);
	}

	//如果自己正在控制这个用户，将其清除
	if( find(m_UnderControlList.begin(), m_UnderControlList.end(), userId) != m_UnderControlList.end())
	{
		m_UnderControlList.remove(userId);
	}

	//如果正在申请
	if( find(m_ApplyList.begin(), m_ApplyList.end(), userId) != m_ApplyList.end())
	{
		m_ApplyList.remove(userId);
	}

	//如果正在申请控制
	if( find(m_ApplyControlList.begin(), m_ApplyControlList.end(), userId) != m_ApplyControlList.end())
	{
		m_ApplyControlList.remove(userId);
	}

	//如果正在邀请
	if( find(m_InviteList.begin(), m_InviteList.end(), userId) != m_InviteList.end())
	{
		m_InviteList.remove(userId);
	}

	//如果对方正在申请我
	if( find(m_ApplyMeList.begin(), m_ApplyMeList.end(), userId) != m_ApplyMeList.end() )
	{
		m_ApplyMeList.remove(userId);
	}

	//如果对方正在邀请我
	if( find(m_InviteMeList.begin(), m_InviteMeList.end(), userId) != m_InviteMeList.end() )
	{
		m_InviteMeList.remove(userId);
	}
}

void DesktopManager::UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UserQuitConference* pCmd = (LyvcMessage::UserQuitConference*)pBaseMessage;
	__int64 userId = pCmd->userId;
	//如果正在申请
	if( find(m_ApplyList.begin(), m_ApplyList.end(), userId) != m_ApplyList.end())
	{
		m_ApplyList.remove(userId);
	}

	//如果正在申请控制
	if( find(m_ApplyControlList.begin(), m_ApplyControlList.end(), userId) != m_ApplyControlList.end())
	{
		m_ApplyControlList.remove(userId);
	}

	//如果正在邀请
	if( find(m_InviteList.begin(), m_InviteList.end(), userId) != m_InviteList.end())
	{
		m_InviteList.remove(userId);
	}

	//如果对方正在申请我
	if( find(m_ApplyMeList.begin(), m_ApplyMeList.end(), userId) != m_ApplyMeList.end() )
	{
		m_ApplyMeList.remove(userId);
	}

	//如果对方正在邀请我
	if( find(m_InviteMeList.begin(), m_InviteMeList.end(), userId) != m_InviteMeList.end() )
	{
		m_InviteMeList.remove(userId);
	}
}

void DesktopManager::cmdApplyDesktop(USERID userId)
{
	//如果已经发送了申请，不再发送
	if( find(m_ApplyList.begin(), m_ApplyList.end(), userId) == m_ApplyList.end() )
	{
		m_ApplyList.push_back(userId);

		LyvcMessage::ApplyDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdAgreeDesktop(USERID userId)
{
	if( find(m_ApplyMeList.begin(), m_ApplyMeList.end(), userId) != m_ApplyMeList.end() )
	{
		m_ApplyMeList.remove( userId );

		LyvcMessage::AgreeDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdRejectDesktop(USERID userId)
{
	if( find(m_ApplyMeList.begin(), m_ApplyMeList.end(), userId) != m_ApplyMeList.end() )
	{
		m_ApplyMeList.remove( userId );

		LyvcMessage::RejectDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdInviteDesktop(USERID userId)
{
	//如果已经发送了邀请，不再发送
	if( find(m_InviteList.begin(), m_InviteList.end(), userId) == m_InviteList.end())
	{
		m_InviteList.push_back(userId);

		LyvcMessage::InviteDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdAgreeInviteDesktop(USERID userId)
{
	if( find(m_InviteMeList.begin(), m_InviteMeList.end(), userId) != m_InviteMeList.end() )
	{
		m_InviteMeList.remove( userId );

		LyvcMessage::AgreeInviteDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);

		m_DesktopServerList.push_back( userId );
		this->m_pTransModel->startReceiveUserDesktop( userId, m_pConference->getUserRealName( userId ), this->m_notifier);
	}
}

void DesktopManager::cmdRejectInviteDesktop(USERID userId)
{
	if( find(m_InviteMeList.begin(), m_InviteMeList.end(), userId) != m_InviteMeList.end() )
	{
		m_InviteMeList.remove( userId );

		LyvcMessage::RejectInviteDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdStopSendDesktop(USERID userId)
{
	LyvcMessage::StopSendDesktop msg;
	msg.senderId = m_pConference->getSelfId();
	msg.receiverId = userId;
	msg.conferenceId = m_pConference->getConferenceId();
	m_pMatrixC->sendMessage(&msg);

    this->m_pTransModel->stopSendDesktop();

    this->m_DesktopClientList.remove(userId);
}

void DesktopManager::cmdStopReceiveDesktop(USERID userId)
{
	LyvcMessage::StopReceiveDesktop msg;
	msg.senderId = m_pConference->getSelfId();
	msg.conferenceId = m_pConference->getConferenceId();
	msg.receiverId = userId;
	m_pMatrixC->sendMessage(&msg);

    this->m_pTransModel->stopReceiveUserDesktop(userId);
    this->m_UnderControlList.remove(userId);
    this->m_DesktopServerList.remove(userId);
}

void DesktopManager::cmdApplyControlDesktop(USERID userId)
{
	//如果已经发送了申请，不再发送
	if( find(m_ApplyControlList.begin(), m_ApplyControlList.end(), userId) == m_ApplyControlList.end())
	{
		m_ApplyControlList.push_back(userId);

		LyvcMessage::ApplyControlDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdAgreeControlDesktop(USERID userId)
{
	m_ApplyControlMeList.remove( userId );
	if( isUserInClientList( userId ) )
	{
		if(m_ControlBy != 0)
		{
			this->cmdStopReceiveControlDesktop();
		}

		LyvcMessage::AgreeControlDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);

		m_ControlBy =userId;
	}
}

void DesktopManager::cmdRejectControlDesktop(USERID userId)
{
	m_ApplyControlMeList.remove( userId );
	if( isUserInClientList( userId ) )
	{
		LyvcMessage::RejectControlDesktop msg;
		msg.senderId = m_pConference->getSelfId();
		msg.receiverId = userId;
		msg.conferenceId = m_pConference->getConferenceId();
		m_pMatrixC->sendMessage(&msg);
	}
}

void DesktopManager::cmdStopSendControlDesktop(USERID userId)
{
	m_pTransModel->stopControlUserDesktop(userId);
	m_UnderControlList.remove(userId);

	LyvcMessage::StopSendControlDesktop msg;
	msg.senderId = m_pConference->getSelfId();
	msg.receiverId = userId;
	msg.conferenceId = m_pConference->getConferenceId();
	m_pMatrixC->sendMessage(&msg);
}

void DesktopManager::cmdStopReceiveControlDesktop()
{
	LyvcMessage::StopReceiveControlDesktop msg;
	msg.senderId = m_pConference->getSelfId();
	msg.receiverId = m_ControlBy;
	msg.conferenceId = m_pConference->getConferenceId();
	m_pMatrixC->sendMessage(&msg);

	m_ControlBy = 0;
}

BOOL DesktopManager::canInviteDesktop( __int64 uid )
{
	return m_pConference->canReceiveDesktop(uid) 
		&& !( isUserInClientList(uid) || isUserInServerList(uid) || isUserInApplyList(uid) );
}

BOOL DesktopManager::canStopSendDesktopToUser( __int64 uid )
{
	if( uid == 0 )
		return ( m_DesktopClientList.size() > 0 ) ? TRUE : FALSE;
	return isUserInClientList( uid );
}
	
BOOL DesktopManager::canApplyDesktop( __int64 uid )
{
	return m_pConference->canSendDesktop(uid) 
		&& !( isUserInServerList(uid) || isUserInClientList(uid) || isUserInInviteList(uid) );
}

BOOL DesktopManager::canStopReceiveDesktopFromUser( __int64 uid )
{
	if( uid == 0 )
		return ( m_DesktopServerList.size() > 0 ) ? TRUE : FALSE;
	return isUserInServerList(uid);
}

BOOL DesktopManager::canApplyControlDesktop( __int64 uid )
{
	if( uid == 0 )
		return ( m_DesktopServerList.size() > 0 ) ? TRUE : FALSE;
	return isUserInServerList(uid) && !isControlUser(uid);
}

BOOL DesktopManager::canStopSendControlDesktopToUser( __int64 uid )
{
	if( uid == 0 )
		return ( m_UnderControlList.size() > 0 ) ? TRUE : FALSE;
	return isUserInServerList(uid) && isControlUser(uid);
}

BOOL DesktopManager::canStopReceiveControlDesktopFromUser( __int64 uid )
{
	if( uid == 0 )
		return m_ControlBy != 0 ? TRUE : FALSE;
	return ( m_ControlBy == uid ) ? TRUE : FALSE;
}

BOOL DesktopManager::isUserInClientList(__int64 uid)
{
	return find( m_DesktopClientList.begin(), m_DesktopClientList.end(), uid ) != m_DesktopClientList.end();
}

BOOL DesktopManager::isUserInServerList(__int64 uid)
{
	return find( m_DesktopServerList.begin(), m_DesktopServerList.end(), uid ) != m_DesktopServerList.end();
}

BOOL DesktopManager::isControlUser(__int64 uid)
{
	return find( m_UnderControlList.begin(), m_UnderControlList.end(), uid ) != m_UnderControlList.end();
}

BOOL DesktopManager::isControledByUser(__int64 uid)
{
	return m_ControlBy == uid;
}

BOOL DesktopManager::isUserInApplyList(__int64 uid)
{
	return find( m_ApplyList.begin(), m_ApplyList.end(), uid ) != m_ApplyList.end();
}

BOOL DesktopManager::isUserInInviteList(__int64 uid)
{
	return find( m_InviteList.begin(), m_InviteList.end(), uid ) != m_InviteList.end();
}

__int64 DesktopManager::getTopDesktopClientId()
{
	return this->m_pTransModel->getTopDesktopClientId();
}


//-=Notifier=-

void Notifier::clientClosed(__int64 uid)
{
	m_manager->cmdStopReceiveDesktop(uid);
}

void Notifier::setManager(DesktopManager* manager)
{
	m_manager = manager;
}
