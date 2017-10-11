#include "stdafx.h"
#include "string.h"
#include "FLVCC.h"
#include "RunningConference.h"
#include "Transfer/TransModel.h"
#include "Model/Room/FileTransferMgr.h"
#include "Model/Room/DesktopManager.h"
#include "Model/Room/ConferenceUserMgr.h"
#include "model/Room/ConferenceUser.h"
#include "model/room/AVManager.h"
#include "model\room\YuntaiMgr.h"
#include "model/Conference.h"
#include "model/ConferenceMgr.h"
#include "model/ConferenceModel.h"
#include "model/ConferenceRole.h"
#include "model/ConferenceModelMgr.h"
#include "UI\MeetingRoom\RoomMainFrm.h"
#include "UI\Whiteboard\WhiteboardDlg.h"
#include "..\Rights.h"
#include "..\..\..\Common\Common\Base64Coder\Base64Coder.h"
#include "..\..\..\Common\Common\PathHelper\PathHelper.h"
#include "Model/CooperationManager.h"

#include "message/MatrixC/MatrixCLib/MatrixC.h"
#include "message/MatrixC/MatrixCLib/message/Chat.h"
#include "message/MatrixC/MatrixCLib/message/Broadcast.h"
#include "message/MatrixC/MatrixCLib/message/JoinConference.h"
#include "message/MatrixC/MatrixCLib/message/QuitConference.h"
#include "message/MatrixC/MatrixCLib/message/JoinConferenceResponse.h"
#include "message/MatrixC/MatrixCLib/message/UserJoinConference.h"
#include "message/MatrixC/MatrixCLib/message/UserQuitConference.h"
#include "message/MatrixC/MatrixCLib/message/UserChannelBroken.h"
#include "message/MatrixC/MatrixCLib/message/InviteConference.h"
#include "message/MatrixC/MatrixCLib/message/StartMarkWindow.h"
#include "message/MatrixC/MatrixCLib/message/KickUserFromConference.h"
#include "message/MatrixC/MatrixCLib/message/WatchUser.h"
#include "message/MatrixC/MatrixCLib/message/ListenToUser.h"
#include "message/MatrixC/MatrixCLib/message/ApplySpeak.h"
#include "message/MatrixC/MatrixCLib/message/AppointSpeaker.h"
#include "message/MatrixC/MatrixCLib/message/StopSpeak.h"
#include "message/MatrixC/MatrixCLib/message/WritingMessage.h"
#include "message/MatrixC/MatrixCLib/message/EvaluateVideoQuality.h"
#include "message/MatrixC/MatrixCLib/message/SetUserPicture.h"
#include "message/MatrixC/MatrixCLib/message/DeleteConferenceUser.h"

bool RunningConference::isUserInConference(USERID uid)
{
	return m_pConferenceUserMgr->isUserInConference(uid);
}

BOOL RunningConference::isInstantConference()
{
	return m_pConference->m_dbConference.m_modelId == m_pConferenceModeMgr->getInstantConferenceModelId();
}

BOOL RunningConference::canSendVideo( __int64 uid )
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole( m_pConferenceUserMgr->getConferenceUserByID( uid )->getRoleID() );

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::VIDEO_SEND )
            return true;
    }
	return false;
}

BOOL RunningConference::canReceiveVideo()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
    bool bFlag = false;
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::BECONTROLED_USER )
			return false;
		if( rights.at(i) == Rights::VIDEO_RECEIVE )
            bFlag = true;
    }
	return bFlag;
}

BOOL RunningConference::canSendAudio( __int64 uid )
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole( m_pConferenceUserMgr->getConferenceUserByID( uid )->getRoleID() );

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::AUDIO_SEND )
            return true;
    }
	return false;
}

BOOL RunningConference::canReceiveAudio()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
    bool bFlag = false;
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::BECONTROLED_USER )
			return false;
		if( rights.at(i) == Rights::AUDIO_RECEIVE )
            bFlag = true;
    }
	return bFlag;
}

BOOL RunningConference::canApplySpeak()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::SPEAK_APPLY )
            return TRUE;
    }
	return FALSE;
}

BOOL RunningConference::canControlSpeak( __int64 uid )
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = NULL;
	if( uid == 0 )
		pRole = pModel->getRole(m_pUser->getRoleID());
	else
		pRole = pModel->getRole( m_pConferenceUserMgr->getConferenceUserByID( uid )->getRoleID() );

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::SPEAK_CONTROL )
            return TRUE;
    }
	return FALSE;
}

BOOL RunningConference::canSendDesktop( __int64 uid )
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = NULL;
	if( uid == 0 )
		pRole = pModel->getRole(m_pUser->getRoleID());
	else
		pRole = pModel->getRole( m_pConferenceUserMgr->getConferenceUserByID( uid )->getRoleID() );

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::DESKTOP_SEND )
            return true;
    }
	return false;
}

BOOL RunningConference::canReceiveDesktop( __int64 uid )
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = NULL;
	if( uid == 0 )
		pRole = pModel->getRole(m_pUser->getRoleID());
	else
		pRole = pModel->getRole( m_pConferenceUserMgr->getConferenceUserByID( uid )->getRoleID() );

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::DESKTOP_RECEIVE )
            return true;
    }
	return false;
}

BOOL RunningConference::canSendFile()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::FILE_SEND )
            return true;
    }
	return false;
}

BOOL RunningConference::canReceiveFile( __int64 uid )
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole( m_pConferenceUserMgr->getConferenceUserByID( uid )->getRoleID() );

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::FILE_RECEIVE )
            return true;
    }
	return false;
}

BOOL RunningConference::canInviteUser()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::INVITE_USER )
            return true;
    }
	return false;
}

BOOL RunningConference::canKickUser()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::KICK_USER )
            return true;
    }
	return false;
}

BOOL RunningConference::canControlUser()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::CONTROL_USER )
        {
            return true;
        }
    }
	return false;
}

BOOL RunningConference::isBeenControledUser()
{
	ConferenceModel* pModel = m_pConferenceModeMgr->getConferenceModeByID(m_pConference->m_dbConference.m_modelId);
	ConferenceRole* pRole = pModel->getRole(m_pUser->getRoleID());

	vector<__int64> rights = pRole->getRights();
	for(unsigned int i = 0;i < rights.size();i++)
    {
		if( rights.at(i) == Rights::BECONTROLED_USER )
        {
            return true;
        }
    }
	return false;
}

string RunningConference::getConferenceName() const
{
	return m_pConference->m_dbConference.m_sName;
}

__int64 RunningConference::getConferenceId() const
{
	return m_pConference->m_dbConference.m_id;
}

USERID RunningConference::getSelfId() const
{
	return m_pUser->getId();
}

ConferenceUser* RunningConference::getSelf() const
{
	return m_pUser;
}

string RunningConference::getRoleName(__int64 nRoleID)
{
    return m_pConferenceModeMgr->getConferenceRoleNameByID( 
        m_pConference->m_dbConference.m_modelId, nRoleID);
}

ConferenceUser* RunningConference::getUser(USERID userId) const
{
    if( m_pUser->getId() == userId )
    {
        return m_pUser;
    }
    else
    {
        return m_pConferenceUserMgr->getConferenceUserByID(userId);
    }
}

string RunningConference::getUserRealName(USERID userId) const
{
    return this->getUser(userId)->getRealName();
}

RunningConference::RunningConference(
	Conference* pConference, 
	CMeetingRoomFrame* pRoom, 
	DBUser& dbUser,
    LyvcMessage::MatrixC* pMatrixC,
    TransModel* pTransModel,
    ConferenceModelMgr* pConferenceModeMgr) : LyvcMessage::MessageTarget(pMatrixC)
{
	m_pConference = pConference;
	m_pRoom = pRoom;
	m_pUser = new ConferenceUser(dbUser);
	m_pConferenceModeMgr = pConferenceModeMgr;
    m_pConferenceUserMgr = new ConferenceUserMgr();
	m_pTransModel = pTransModel;

    m_pAVManager = NULL;
    m_pDesktopMgr = NULL;

	 m_idLastConfUser = 0;

	 // 注册消息
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserJoinConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::UserJoinConference));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserQuitConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::UserQuitConference));
    m_pMatrixC->registerMessageHandler(LyvcMessage::JoinConferenceResponse::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::JoinConferenceResponse));
    m_pMatrixC->registerMessageHandler(LyvcMessage::Broadcast::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::Broadcast));
    m_pMatrixC->registerMessageHandler(LyvcMessage::Chat::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::Chat));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserChannelBroken::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::UserChannelBroken));
	m_pMatrixC->registerMessageHandler(LyvcMessage::StartMarkWindow::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::StartMarkWindow));
    m_pMatrixC->registerMessageHandler(LyvcMessage::WatchUser::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::WatchUser));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ListenToUser::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::ListenToUser));
	m_pMatrixC->registerMessageHandler(LyvcMessage::ApplySpeak::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::ApplySpeak));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AppointSpeaker::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::AppointSpeaker));
    m_pMatrixC->registerMessageHandler(LyvcMessage::StopSpeak::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::StopSpeak));
    m_pMatrixC->registerMessageHandler(LyvcMessage::WritingMessage::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::WritingMessage));
    m_pMatrixC->registerMessageHandler(LyvcMessage::EvaluateVideoQuality::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::EvaluateVideoQuality));
    m_pMatrixC->registerMessageHandler(LyvcMessage::SetUserPicture::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RunningConference::SetUserPicture));
}

RunningConference::~RunningConference()
{
    // 删除消息注册
    m_pMatrixC->removeObjectMessageHandler(this);
	delete m_pUser;
    delete m_pConferenceUserMgr;
	this->deleteDelaySendMsgs();
}

bool RunningConference::create()
{
	m_pYuntaiMgr = new YuntaiMgr( m_pMatrixC, m_pRoom, this );
	m_pYuntaiMgr->create();
	m_pYuntaiMgr->m_bIfHasYuntai = ( ::GetApp()->getCooperationManager()->getTKLChannelNumber() > 0 ) ? true : false;

	//初始化文件传输管理器
	m_pFileTransferMgr = new FileTransferMgr(m_pMatrixC, m_pRoom, this);
	m_pFileTransferMgr->create();

	// 初始化桌面共享管理器
	m_pDesktopMgr = new DesktopManager(m_pMatrixC, this, this->m_pTransModel);
	m_pDesktopMgr->create();

	// 初始化视音频管理器
	m_pAVManager = new AVManager(
		this->getSelfId(),
		this->getConferenceId(),
		this->m_pRoom,
        this->m_pMatrixC,
		this->m_pTransModel);
	m_pAVManager->m_bIsInstantConf = ( m_pConferenceModeMgr->getInstantConferenceModelId() == m_pConference->m_dbConference.m_modelId );
    m_pAVManager->create();

	m_pTransModel->addWhiteboard( this->getConferenceId(), m_pRoom->getWhiteboardDlg() );
	m_pRoom->getWhiteboardDlg()->setTransmodel( this->m_pTransModel );

	// 发送加入会议的命令
    this->cmdJoinConference();

    return true;
}

void RunningConference::destroy()
{
	m_pYuntaiMgr->destroy();
	SAFE_DELETE(m_pYuntaiMgr);

	//调用文件传输管理器的清理函数
	m_pFileTransferMgr->destroy();
	SAFE_DELETE(m_pFileTransferMgr);

    // 调用桌面共享管理器的清理函数
    m_pDesktopMgr->destroy();
	SAFE_DELETE(m_pDesktopMgr);

   // 调用视音频管理器的清理函数
    m_pAVManager->destroy();
    SAFE_DELETE(m_pAVManager);

	m_pTransModel->removeWhiteboard( this->getConferenceId() );

	// 发送退出会议的命令
    this->cmdQuitConference();
	return;
}

void RunningConference::JoinConferenceResponse(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::JoinConferenceResponse* pJoinConferenceResponse = (LyvcMessage::JoinConferenceResponse*)pBaseMessage;
    m_pUser->setRoleId(pJoinConferenceResponse->roleId);
    this->m_pRoom->addConferenceUser( this->m_pUser);

	// 加入压缩卡代表的虚拟用户
	if(::GetApp()->getCooperationManager()->getTKLChannelNumber() > 0){
		
		for(int i = 0;i < ::GetApp()->getCooperationManager()->getTKLChannelNumber();i++){
			DBUser dbuser;
			dbuser.id = 100000000000000000L + m_pUser->getId() * 10 + i + 1;
			dbuser.strName = m_pUser->getName();
			dbuser.strEmail = m_pUser->getEmail();
			char temp [100];
			itoa(i+1,temp,10);
			dbuser.strFirstName = string(temp);
			
			//dbuser.strFirstName = "1";
			dbuser.strLastName = m_pUser->getRealName();
			ConferenceUser* pConferenceUser = new ConferenceUser(dbuser);
			pConferenceUser->setRoleId(m_pUser->getRoleID());
			pConferenceUser->setUserType(ConferenceUser::LocalVirtualUser);
			pConferenceUser->setUserChannel(::GetApp()->getCooperationManager()->getTKLHandle(i));

			// 将视频窗口添加到会议室界面
			this->m_pConferenceUserMgr->addConferenceUser(pConferenceUser);
			this->m_pRoom->addConferenceUser(pConferenceUser);
			// 在压缩卡采集得到的视频数据和本地视频窗口之间建立关联
		}
		this->m_pAVManager->initCompressionCardVideoWindow();
	}
	BOOL bShowPicture = ::GetPrivateProfileInt( "DISPLAY_PICTURE", "show", 0, m_pRoom->getUserConfigFile() );
	this->sendMyVideo( !bShowPicture );
	m_pRoom->setSendMyVideoFlag( !bShowPicture );
}

void RunningConference::UserJoinConference(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UserJoinConference* pCmd = (LyvcMessage::UserJoinConference*)pBaseMessage;

    DBUser dbuser;
    dbuser.id = pCmd->userId;
    dbuser.strName = pCmd->userName;
    dbuser.strEmail = pCmd->email;
    dbuser.strFirstName = pCmd->firstName;
    dbuser.strLastName = pCmd->lastName;
    ConferenceUser* pConferenceUser = new ConferenceUser(dbuser);
    pConferenceUser->setRoleId(pCmd->roleId);
	pConferenceUser->setUserChannelNum(pCmd->avCompressionCardChannelNumber);
    this->m_pConferenceUserMgr->addConferenceUser(pConferenceUser);
    this->m_pRoom->addConferenceUser(pConferenceUser);

	if(pCmd->avCompressionCardChannelNumber > 0){
		char temp[100];
		for(int i = 0;i < pCmd->avCompressionCardChannelNumber;i++){
			DBUser remoteDbuser;
			remoteDbuser.id = 100000000000000000L + pCmd->userId * 10 + i + 1;
			remoteDbuser.strName = pCmd->userName;
			remoteDbuser.strEmail = pCmd->email;
			itoa(i+1,temp,10);
			remoteDbuser.strFirstName = pCmd->firstName + string(temp);
			remoteDbuser.strLastName = pCmd->lastName;
			ConferenceUser* pRemoteUser = new ConferenceUser(remoteDbuser);
			pRemoteUser->setRoleId(pCmd->roleId);
			pRemoteUser->setUserType(ConferenceUser::RemoteVirtualUser);

			//将视频窗口添加到会议室界面
			this->m_pConferenceUserMgr->addConferenceUser(pRemoteUser);
			this->m_pRoom->addConferenceUser(pRemoteUser);
		}
	}

	char buf[256];
	::GetPrivateProfileString( "DISPLAY_PICTURE", "file", "", buf, 256, m_pRoom->getUserConfigFile() );
	string fileName = string(buf);
	if( fileName != "" )
		this->cmdSetUserPicture( fileName, pCmd->userId );

	if( this->m_idLastConfUser == pCmd->userId )
	{
		sendDelayMsgs();
	}
	else
	{
		this->deleteDelaySendMsgs();
	}
	this->m_idLastConfUser = 0;
}

void RunningConference::UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UserQuitConference* pCmd = (LyvcMessage::UserQuitConference*)pBaseMessage;
    ConferenceUser* pConferenceUser = m_pConferenceUserMgr->getConferenceUserByID(pCmd->userId);

	// 删除压缩卡代表的虚拟用户
	if(pConferenceUser->getUserChannelNum() > 0){
		vector<__int64> confUserIds;
		this->m_pConferenceUserMgr->getVirtualConferenceUserIds(pCmd->userId, pConferenceUser->getUserChannelNum(), &confUserIds);
		for(int i = 0;i < confUserIds.size();i++){
			_int64 tempId = confUserIds.at(i);
			this->m_pRoom->deleteConferenceUser(tempId, true);
			this->m_pConferenceUserMgr->deleteConferenceUser(tempId);
		}
	}

    this->m_pRoom->deleteConferenceUser(pCmd->userId);
    this->m_pConferenceUserMgr->deleteConferenceUser(pCmd->userId);
	if( m_pConferenceUserMgr->getCount() == 0 )
	{
		m_idLastConfUser = pCmd->userId;
		if( !m_pRoom->IsWindowVisible() )
		{
			::PostMessage( this->m_pRoom->GetSafeHwnd(), WM_CLOSE, NULL, NULL );
		}
	}
}

void RunningConference::Broadcast(LyvcMessage::BaseMessage* pBaseMessage)
{
	if( !m_pRoom->IsWindowVisible() )
		m_pRoom->ShowWindow( SW_SHOWMINNOACTIVE );
    LyvcMessage::Broadcast* pCmd = (LyvcMessage::Broadcast*)pBaseMessage;
    this->m_pRoom->receivedChatMessage(pCmd->senderId, pCmd->content, TRUE);
}

void RunningConference::Chat(LyvcMessage::BaseMessage* pBaseMessage)
{
	if( !m_pRoom->IsWindowVisible() )
		m_pRoom->ShowWindow( SW_SHOWMINNOACTIVE );
    LyvcMessage::Chat* pCmd = (LyvcMessage::Chat*)pBaseMessage;
    this->m_pRoom->receivedChatMessage(pCmd->senderId, pCmd->content, FALSE);
}

void RunningConference::UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UserChannelBroken* pCmd = (LyvcMessage::UserChannelBroken*)pBaseMessage;
    if( this->m_pConferenceUserMgr->isUserInConference(pCmd->userId) ) {
		ConferenceUser* pConferenceUser = m_pConferenceUserMgr->getConferenceUserByID(pCmd->userId);
		// 删除压缩卡代表的虚拟用户
		if(pConferenceUser->getUserChannelNum() > 0){
			vector<__int64> confUserIds;
			this->m_pConferenceUserMgr->getVirtualConferenceUserIds(pCmd->userId, pConferenceUser->getUserChannelNum(), &confUserIds);
			for(int i = 0;i < confUserIds.size();i++){
				_int64 tempId = confUserIds.at(i);
				this->m_pRoom->deleteConferenceUser(tempId, true);
				this->m_pConferenceUserMgr->deleteConferenceUser(tempId);
			}
		}
        this->m_pRoom->deleteConferenceUser(pCmd->userId);
        this->m_pConferenceUserMgr->deleteConferenceUser(pCmd->userId);
    }
	if( this->m_idLastConfUser == pCmd->userId )
	{
		this->m_idLastConfUser = 0;
		this->deleteDelaySendMsgs();
	}
	if( m_pConferenceUserMgr->getCount() == 0 )
	{
		if( !m_pRoom->IsWindowVisible() )
		{
			::PostMessage( this->m_pRoom->GetSafeHwnd(), WM_CLOSE, NULL, NULL );
		}
	}
}

void RunningConference::StartMarkWindow(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::StartMarkWindow* pCmd = (LyvcMessage::StartMarkWindow*)pBaseMessage;
	if (pCmd->receiverId == this->getSelfId())
	{
        this->m_pRoom->OnMark();
	}
}

void RunningConference::WatchUser(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::WatchUser* pMsg = (LyvcMessage::WatchUser*) pBaseMessage;

	//从消息中获取将要观看人员的id列表
	int i = -1;
	list<__int64> applyList;
	list<__int64> stopList;
	while( 1 )
	{
		char buf[64];
		int j = i + 1;
		i = pMsg->beWatchedIds.find( ",", i + 1 );
		if( i == -1 )
			break;
		strncpy( buf, pMsg->beWatchedIds.data() + j, i - j );
		buf[i-j] = '\0';
		__int64 id = _atoi64( buf );
		applyList.push_back( id );
	}
	//与当前正在观看的人员列表比较，申请未观看的，停止不再要观看的
	m_pAVManager->getApplyAndStopVideoList( applyList, stopList );

	//如果显示模式改变了，就收回所有弹出显示的窗口，在m_pRoom的函数中判断
	m_pRoom->popbackAllVideoDlg( pMsg->viewMode );
	list<__int64>::iterator iter;
	for( iter = stopList.begin(); iter != stopList.end(); iter++ )
	{
		m_pRoom->stopVideo( *iter );
	}
	m_pRoom->stopVideo( m_pRoom->getSelfId() );
	m_pRoom->setViewMode( pMsg->viewMode );
	for( iter = applyList.begin(); iter != applyList.end(); iter++ )
	{
		this->m_pRoom->popupAttenderDlg( *iter );
	}
}

void RunningConference::ListenToUser(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::ListenToUser* pMsg = (LyvcMessage::ListenToUser*) pBaseMessage;

	//从消息中获取将要收听人员的id列表
	int i = -1;
	list<__int64> applyList;
	list<__int64> stopList;
	while( 1 )
	{
		char buf[64];
		int j = i + 1;
		i = pMsg->beListenedIds.find( ",", i + 1 );
		if( i == -1 )
			break;
		strncpy( buf, pMsg->beListenedIds.data() + j, i - j );
		buf[i-j] = '\0';
		__int64 id = _atoi64( buf );
		applyList.push_back( id );
	}
	//与当前正在收听的人员列表比较，申请未收听的，停止不再要收听的
	m_pAVManager->getApplyAndStopAudioList( applyList, stopList );

	list<__int64>::iterator iter;
	for( iter = stopList.begin(); iter != stopList.end(); iter++ )
	{
		m_pRoom->stopAudio( *iter );
	}
	for( iter = applyList.begin(); iter != applyList.end(); iter++ )
	{
		this->m_pRoom->startAudio( *iter );
	}
}

void RunningConference::ApplySpeak(LyvcMessage::BaseMessage* pBaseMessage)
{
	if( canControlSpeak() )
		m_pRoom->notifyApplySpeak( pBaseMessage->_senderId );
}

void RunningConference::AppointSpeaker(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::AppointSpeaker* pMsg = (LyvcMessage::AppointSpeaker*) pBaseMessage;
	m_pRoom->notifyAppointSpeaker( pMsg->_senderId, pMsg->speakerId );
	
	if( pMsg->speakerId != getSelfId() )
	{
		if( !getAVManager()->isReceiveAudio( pMsg->speakerId ) )
            m_pRoom->startAudio( pMsg->speakerId );
		if( !getAVManager()->isReceiveVideo( pMsg->speakerId ) )
			m_pRoom->startVideo( pMsg->speakerId );
	}
}

void RunningConference::StopSpeak(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::StopSpeak* pMsg = (LyvcMessage::StopSpeak*) pBaseMessage;
	m_pRoom->notifyStopSpeak( pMsg->_senderId, pMsg->speakerId );

	if( pMsg->speakerId != getSelfId() )
	{
		if( getAVManager()->isReceiveAudio( pMsg->speakerId ) )
			m_pRoom->stopAudio( pMsg->speakerId );
		if( getAVManager()->isReceiveVideo( pMsg->speakerId ) )
			m_pRoom->stopVideo( pMsg->speakerId );
	}
}

void RunningConference::WritingMessage(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::WritingMessage* pMsg = (LyvcMessage::WritingMessage*) pBaseMessage;
	m_pRoom->notifyWritingMessage( pMsg->receiverId, pMsg->_senderId, pMsg->bIsWriting );
}

void RunningConference::EvaluateVideoQuality(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::EvaluateVideoQuality* pMsg = (LyvcMessage::EvaluateVideoQuality*) pBaseMessage;
	m_pRoom->notifyEvaluateVideoQuality( pMsg->_senderId, pMsg->quality );
}

void RunningConference::SetUserPicture(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::SetUserPicture* pMsg = (LyvcMessage::SetUserPicture*) pBaseMessage;

	if( pMsg->fileLength == 0 )
	{
		m_pRoom->notifyShowUserPicture( pMsg->_senderId, "" );
    	return;
	}
	//得到文件名
	string fileName = string( PathHelper::getUserFacePath( getSelf()->getName().c_str(), m_pRoom->getServerIP().c_str() ) );
	char buf[65];
	_i64toa( pMsg->_senderId, buf, 10 );
	fileName = fileName + string(buf) + pMsg->extendFileName;

	//解码
	Base64Coder decoder;
	int len = pMsg->fileData.length();
	decoder.Decode((const PBYTE)pMsg->fileData.c_str(), len);
	string fileData;
	fileData.append(decoder.DecodedMessage(), pMsg->fileLength);

	//写入文件
	FILE* file = NULL;
	file = fopen( fileName.c_str(), "wb" );
    if( file != NULL )
	{
		fwrite( fileData.c_str(), sizeof(char), fileData.length(), file);
		fclose(file);
	}

	//通知界面
	m_pRoom->notifyShowUserPicture( pMsg->_senderId, fileName );
}

void RunningConference::cmdSetUserPicture( string fileName, __int64 receiverId )
{
	LyvcMessage::SetUserPicture msg;
	msg.receiverId = receiverId;
	if( fileName == "" )
	{
		msg.fileLength = 0;
		m_pMatrixC->sendMessage( &msg );
		return;
	}
	msg.extendFileName = msg.extendFileName.append( fileName.data() + fileName.length() - 4, 4 );

	//读取文件数据
	FILE* file = NULL;
	file = fopen( fileName.c_str(), "rb" );
	if( file == NULL ) 
		return;
	char buffer[ 45*1024 ];
	msg.fileLength = fread( buffer, sizeof(char), 45*1024, file );
	fclose(file);

	//编码
	Base64Coder encoder;
	encoder.Encode( (const PBYTE)buffer, msg.fileLength );
	msg.fileData = encoder.EncodedMessage();

	m_pMatrixC->sendMessage( &msg );
}

void RunningConference::evaluateVideoQuality( __int64 receiverId, int nQuality )
{
	LyvcMessage::EvaluateVideoQuality msg;
	msg.receiverId = receiverId;
	msg.quality = nQuality;
	this->m_pMatrixC->sendMessage( &msg );
}

void RunningConference::cmdWritingMessage( __int64 receiverId, bool bFlag )
{
	if( this->m_pConferenceUserMgr->getCount() == 0 )
		return;
	LyvcMessage::WritingMessage msg;
	msg.receiverId = receiverId;
	msg.bIsWriting = bFlag;
	this->m_pMatrixC->sendMessage( &msg );
}

void RunningConference::cmdAppointSpeaker( __int64 uid )
{
	LyvcMessage::AppointSpeaker msg;
	msg.speakerId = uid;
	m_pMatrixC->sendMessage( &msg );

	if( !getAVManager()->isReceiveAudio( uid ) )
        m_pRoom->startAudio( uid );
	if( !getAVManager()->isReceiveVideo( uid ) )
		m_pRoom->startVideo( uid );
}

void RunningConference::cmdStopSpeak( __int64 uid )
{
	LyvcMessage::StopSpeak msg;
	msg.speakerId = uid;
	m_pMatrixC->sendMessage( &msg );

	if( getAVManager()->isReceiveAudio( uid ) )
		m_pRoom->stopAudio( uid );
	if( getAVManager()->isReceiveVideo( uid ) )
		m_pRoom->stopVideo( uid );
}

void RunningConference::cmdApplySpeak()
{
	vector<__int64> v;
	m_pConferenceUserMgr->getConferenceUserIds( &v );
	for( unsigned int i = 0; i < v.size(); i++ )
	{
		if( canControlSpeak( v.at(i) ) )
		{
			LyvcMessage::ApplySpeak msg;
			msg.receiverId = v.at(i);
			m_pMatrixC->sendMessage( &msg );
		}
	}
}

void RunningConference::cmdWatchUser(USERID userId, string beWatched, int nViewMode)
{
	LyvcMessage::WatchUser msg;
	msg.viewMode = nViewMode;
	msg.receiverId = userId;
	msg.beWatchedIds = beWatched;
	m_pMatrixC->sendMessage( &msg );
}

void RunningConference::cmdListenToUser(USERID userId, string beListened)
{
	LyvcMessage::ListenToUser msg;
	msg.receiverId = userId;
	msg.beListenedIds = beListened;
	m_pMatrixC->sendMessage( &msg );
}

void RunningConference::cmdChat(USERID userId, string strContent)
{
    LyvcMessage::Chat msg;
    msg.senderId = this->getSelfId();
    msg.receiverId = userId;
    msg.content = strContent;
    msg.conId = this->getConferenceId();
    this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::cmdChatPublic(string strContent)
{
	if( this->m_pConferenceUserMgr->getCount() == 0 )
	{
		if( m_idLastConfUser != 0 )
		{
			this->cmdInviteConference( m_idLastConfUser, this->getConferenceId() );
			LyvcMessage::Broadcast* pMsg = new LyvcMessage::Broadcast();
			pMsg->conId = this->getConferenceId();
			pMsg->senderId = this->getSelfId();
			pMsg->content  = strContent;
			this->m_delaySendMsgs.push_back( pMsg );
		}
		return;
	}
    LyvcMessage::Broadcast msg;
    msg.conId = this->getConferenceId();
    msg.senderId = this->getSelfId();
    msg.content  = strContent;
    this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::deleteDelaySendMsgs()
{
	vector<LyvcMessage::BaseMessage*>::iterator iter = this->m_delaySendMsgs.begin();
	while( iter != m_delaySendMsgs.end() )
	{
		LyvcMessage::BaseMessage* pMsg = (*iter);
		delete pMsg;
		pMsg = NULL;
		this->m_delaySendMsgs.erase( iter );
		iter = this->m_delaySendMsgs.begin();
	}
}

void RunningConference::sendDelayMsgs()
{
	vector<LyvcMessage::BaseMessage*>::iterator iter = this->m_delaySendMsgs.begin();
	while( iter != m_delaySendMsgs.end() )
	{
		LyvcMessage::BaseMessage* pMsg = (*iter);
		this->m_pMatrixC->sendMessage( pMsg );
		delete pMsg;
		pMsg = NULL;
		this->m_delaySendMsgs.erase( iter );
		iter = this->m_delaySendMsgs.begin();
	}
}

void RunningConference::cmdJoinConference()
{
    LyvcMessage::JoinConference msg;
    msg.conId = this->getConferenceId();
	msg.avCompressionCardChannelNumber = ::GetApp()->getCooperationManager()->getTKLChannelNumber();
    this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::cmdQuitConference()
{
    LyvcMessage::QuitConference msg;
    msg.conId = this->getConferenceId();
    this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::cmdInviteConference(__int64 contactId, __int64 conferenceId)
{
	LyvcMessage::InviteConference msg;
	msg.conferenceId = conferenceId;
	msg.contactId = contactId;
    this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::cmdStartMarkWindow(__int64 uid)
{
	LyvcMessage::StartMarkWindow msg;
	msg.receiverId = uid;
    this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::cmdKickUser(__int64 userId)
{
	LyvcMessage::KickUserFromConference msg;
	msg.confId = this->getConferenceId();
	msg.confUserId = userId;
	this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::cmdDeleteUserFromConference(__int64 userId)
{
	LyvcMessage::DeleteConferenceUser msg;
	msg.userId = userId;
	this->m_pMatrixC->sendMessage(&msg);
}

void RunningConference::sendMyVideo(BOOL bSend)
{
	this->m_pTransModel->EnableSendVideo(bSend);
}
