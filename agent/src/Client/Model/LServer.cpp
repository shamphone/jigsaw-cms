#include "stdafx.h"
#include "LServer.h"
#include "CooperationManager.h"
#include "message/MatrixC/MatrixCLib/MatrixC.h"

#include "model/contact/Contact.h"
#include "model/contact/ContactMgr.h"

#include "Model/Conference.h"
#include "Model/ConferenceMgr.h"
#include "Model/ConferenceModelMgr.h"
#include "Model/Room/RunningConference.h"

#include "..\ui\MainFrm\MainFrm.h"
#include "..\ui\MainFrm\ConferenceDlg.h"
#include "transfer\transModel.h"
#include "transfer\transModelImpl.h"

#include "message/MatrixC/MatrixCLib/message/Login.h"
#include "message/MatrixC/MatrixCLib/message/GetMediaServerAddress.h"
#include "message/MatrixC/MatrixCLib/message/MediaServerAddress.h"
#include "message/MatrixC/MatrixCLib/message/UserDetail.h"
#include "message/MatrixC/MatrixCLib/message/UserLoginResponse.h"
#include "message/MatrixC/MatrixCLib/message/ServerInternalError.h"
#include "message/MatrixC/MatrixCLib/message/CheckVersion.h"
#include "message/MatrixC/MatrixCLib/message/Advertizement.h"
#include "message/MatrixC/MatrixCLib/message/ServerInfo.h"
#include "message/MatrixC/MatrixCLib/message/KickDuplicateLogin.h"
#include "message/MatrixC/MatrixCLib/message/ChangeUserInfo.h"
#include "message/MatrixC/MatrixCLib/message/LoginSuccessfully.h"
#include "message/MatrixC/MatrixCLib/message/InviteConference.h"
#include "message/MatrixC/MatrixCLib/message/KeepAlive.h"

#include "Video/VideoCapture/TklSdk.h"
#include "..\..\Common\Common\PathHelper\PathHelper.h"

LServer::LServer(CooperationManager* pCooperManager, CMainFrame* pMainFrame, string sServerName, string IP, int port)
	: LyvcMessage::MessageTarget( NULL )
{
	m_pCooperManager= pCooperManager;
	m_pMainFrame	= pMainFrame;
	m_nServerId		= pMainFrame->getServerId();
	m_sServerName	= sServerName;
	m_sIP			= IP;
	m_nPort			= port;
    m_pTransModel	= NULL;
	m_nStatus		= SERVER_LOGOUT;
	m_bDefaultServer= FALSE;
	m_sUserName		= "";
	m_sPassword		= "";
	m_bRememberUserPass = FALSE;
    m_htiContact	= NULL;
	m_htiConference = NULL;
	m_htiSysmsg		= NULL;
	m_sDeleteConferenceURL = "";
	m_sCreateConferenceNoticeURL = "";
	m_sCreateBulletinURL = "";
	m_sEditConferenceURL = "";
	m_sConferenceModeMgrURL = "";
	m_sSystemRoleMgrURL = "";
	m_sCommonContactMgrURL = "";
	m_sCreateFromalConferenceURL = "";
	m_sSelfInfoMgrURL = "";
	m_sUserRegisterURL = "";
	m_sClientDownloadURL = "";
    m_pConferenceModeMgr = NULL;
	m_pConferenceMgr = NULL;
	m_pTransModel = NULL;
	m_pContactMgr = NULL;
	m_expertId = 0;
	m_confIdFromWeb = 0;
}

LServer::~LServer()
{
}

bool LServer::create()
{	
	// 传来的有可能是域名，因此对其进行一次转换
	HOSTENT* pHostEnt = gethostbyname( m_sIP.c_str() );
	if( pHostEnt == NULL )
	{
		return false;
	}
	string IP = inet_ntoa(*(struct in_addr *)*pHostEnt->h_addr_list);

	// 初始化传输
    m_pMatrixC = LyvcMessage::MatrixC::getInstance();
    if( !m_pMatrixC->Create( IP.c_str(), m_nPort ) )
    {
//		if( m_nPort != 80 )
//		{
//			if( !m_pMatrixC->Create( IP.c_str(), 80 ) )
//			{
				LyvcMessage::MatrixC::releaseInstance(this->m_pMatrixC);
				m_pMatrixC = NULL;
				return false;
//			}
//		}
    }
    //注册消息处理函数
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserLoginResponse::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::UserLoginResponse));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserDetail::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::UserDetail));
    m_pMatrixC->registerMessageHandler(LyvcMessage::MediaServerAddress::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::MediaServerAddress));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ServerInternalError::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::ServerInternalError));
    m_pMatrixC->registerMessageHandler(LyvcMessage::CheckVersion::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::CheckVersion));
    m_pMatrixC->registerMessageHandler(LyvcMessage::Advertizement::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::Advertizement));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ServerInfo::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::ServerInfo));
    m_pMatrixC->registerMessageHandler(LyvcMessage::KickDuplicateLogin::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::KickDuplicateLogin));
    m_pMatrixC->registerMessageHandler(LyvcMessage::LoginSuccessfully::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::LoginSuccessfully));
    m_pMatrixC->registerMessageHandler(LyvcMessage::KeepAlive::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::KeepAlive));
    //注册例外处理函数
    m_pMatrixC->registerExceptionHandler(this, static_cast<LyvcMessage::PMSG_HANDLER>(LServer::ExceptionHandler));

	// 会议模式管理器
	m_pConferenceModeMgr = new ConferenceModelMgr( m_pMatrixC );
	// 会议管理器
	m_pConferenceMgr = new ConferenceMgr( m_pMatrixC, this );
    // 联系人管理器
    m_pContactMgr = new ContactMgr( m_pMatrixC, this );
	
	this->cmdCheckVersion();
	m_nStatus		= SERVER_LOGINING;
    return true;
}

void LServer::destroy()
{
	this->m_pCooperManager->removeAVCustomer(this->getServerId());
	this->m_pCooperManager->TKLRemoveCustomer(this->getServerId());
	// 销毁传输模型
	// 该对象最后创建（在Create函数之外通过命令），因此最先删除
    if( m_pTransModel != NULL)
    {
		m_pTransModel->Destroy();
        delete m_pTransModel;
		m_pTransModel = NULL;
    }
	// 删除消息处理函数
    m_pMatrixC->removeObjectMessageHandler(this);
    //删除例外处理函数
    m_pMatrixC->removeExceptionHandler();
	// 退出所有正在运行的会议
	this->m_pConferenceMgr->stopAllRunningConference();
    // 调用联系人管理器的清理方法
    this->m_pContactMgr->destroy();

    SAFE_DELETE(m_pContactMgr);
	SAFE_DELETE(m_pConferenceMgr);
	SAFE_DELETE(m_pConferenceModeMgr);

 	// 清理消息传输
    m_pMatrixC->Destroy();
    LyvcMessage::MatrixC::releaseInstance(m_pMatrixC);
	m_pMatrixC = NULL;

	m_nStatus		= SERVER_LOGOUT;

	setExpertId( 0 );
	setConfIdFromWeb( 0 );
}

void LServer::cmdCheckVersion()
{
	CString fileName = PathHelper::getApplicationRoot();
	fileName = fileName + "\\version.dat";
	FILE* file = fopen( fileName, "rt" );
	char buf[33];
	if( file != NULL )
	{
		int n = fread( buf, sizeof(char), 8, file );
		buf[n] = '\0';
		fclose( file );
	}
	LyvcMessage::CheckVersion msg;
	msg.version = atoi( buf );
	msg.domain = this->m_sIP;
	m_pMatrixC->sendMessage( &msg );
}

void LServer::cmdLogin()
{
    LyvcMessage::Login login;
    login.username = this->m_sUserName;
    login.password = this->m_sPassword;
	//设置服务器的域名（IP地址）
	login.domain = this->m_sIP;

	login.avCompressionCardChannelNumber = this->m_pCooperManager->getTKLChannelNumber();

    this->m_pMatrixC->sendMessage(&login);
}

void LServer::cmdChangeUserInfo( DBUser userInfo, string newPassword )
{
	LyvcMessage::ChangeUserInfo msg;
	msg.userId = getSelfInfo().id;
	msg.userName = getSelfInfo().strName;
	msg.lastName = userInfo.strLastName;
	msg.firstName = userInfo.strFirstName;
	msg.email = userInfo.strEmail;
	msg.password = newPassword;

	this->m_pMatrixC->sendMessage( &msg );

	this->setPassword( newPassword );
	DBUser user = this->getSelfInfo();
	user.strEmail = userInfo.strEmail;
	user.strFirstName = userInfo.strFirstName;
	user.strLastName = userInfo.strLastName;
	this->setSelfInfo( user );
	this->m_pMainFrame->SetTaskIcon( this, TRUE );
}

void LServer::cmdGetMediaServerAddr() const
{
    LyvcMessage::GetMediaServerAddress msg;
    this->m_pMatrixC->sendMessage(&msg);
}

void LServer::cmdKeepAlive()
{
	LyvcMessage::KeepAlive msg;
	this->m_pMatrixC->sendMessage(&msg);
}

void LServer::UserLoginResponse(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::UserLoginResponse* pUserLoginResponse = (LyvcMessage::UserLoginResponse*)pMessage;

	// 登录失败，通知界面
    if(pUserLoginResponse->userId == 0)
	{
        m_pMainFrame->notifyLoginFailed( pUserLoginResponse->message.c_str(), this );
        return;
	}

    // Set default sender id
    m_pMatrixC->setDefaultSenderId(pUserLoginResponse->userId);
	
}

void LServer::UserDetail(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::UserDetail* pDetail = (LyvcMessage::UserDetail*)pMessage;

    // Set local user name
    DBUser dbuser;
	dbuser.userRole = pDetail->userRole;
    dbuser.id = pDetail->userId;
    dbuser.strName = pDetail->userName;
    dbuser.strEmail = pDetail->email;
    dbuser.strFirstName = pDetail->firstName;
    dbuser.strLastName = pDetail->lastName;
	this->setSelfInfo(dbuser);

	m_nStatus = SERVER_LOGIN;
    // Notify interface
	m_pMainFrame->notifyLoginSuccessed( this );

#pragma message ("应该放到各个消息处理实体中, 这些实体分别注册 UserDetail消息")
	// 请求媒体服务器地址
	cmdGetMediaServerAddr();
}

void LServer::MediaServerAddress(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::MediaServerAddress* pMediaServerAddr = (LyvcMessage::MediaServerAddress*)pMessage;

	// 如果传过来的媒体服务器地址是127.0.0.1, 那么媒体服务器
	// 和会议服务器位于同一台计算机上
	if( pMediaServerAddr->host == "127.0.0.1")
	{
		pMediaServerAddr->host = this->m_pMatrixC->getRemoteIP();
	}

    // 启动传输模型
	m_pTransModel = new TransModelImpl(this->getId(), this->m_pMatrixC);
	if( !m_pTransModel->Create()
		|| !m_pTransModel->setUdpServer(pMediaServerAddr->host, pMediaServerAddr->udpport) 
		|| !m_pTransModel->setTcpServer(pMediaServerAddr->host, pMediaServerAddr->tcpport) ) 
	{
		CString* msg = new CString( "无法连接媒体服务器!" );
		::PostMessage( m_pMainFrame->GetSafeHwnd(), WM_DISCONNECT_SERVER, (WPARAM)this, (LPARAM)msg);
	}
	this->m_pCooperManager->addAVCustomer(this->getServerId(), m_pTransModel);
	this->m_pCooperManager->TKLAddCustomer(this->getServerId(), m_pTransModel);
}

void LServer::ServerInternalError(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::ServerInternalError* pCmd = (LyvcMessage::ServerInternalError*)pMessage;
    TRACE1("Server Internal Error: %s\n", pCmd->description.c_str());
	::PostMessage( m_pMainFrame->GetSafeHwnd(), WM_DISCONNECT_SERVER, (WPARAM)this, NULL);
	::SetTimer( m_pMainFrame->GetSafeHwnd(), this->getServerId(), 60000, NULL );
}

void LServer::ExceptionHandler(LyvcMessage::BaseMessage* pMessage)
{
	::PostMessage( m_pMainFrame->GetSafeHwnd(), WM_DISCONNECT_SERVER, (WPARAM)this, NULL);
	::SetTimer( m_pMainFrame->GetSafeHwnd(), this->getServerId(), 60000, NULL );
	TRACE0("Network broken\n");
}

void LServer::CheckVersion(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::CheckVersion* pMsg = (LyvcMessage::CheckVersion*) pMessage;
	pMsg->domain = this->m_sIP;
	m_pMainFrame->notifyCheckVersion( pMsg->bHasNewVersion, pMsg->bNeedUpdate, this );
}

void LServer::Advertizement(LyvcMessage::BaseMessage* pMessage)
{
	if( this->isDefaultServer() )
	{
		LyvcMessage::Advertizement* pMsg = (LyvcMessage::Advertizement*) pMessage;
		m_pMainFrame->notifyAdvertizementAddress( pMsg->homepage.c_str(), pMsg->mainframe.c_str(), pMsg->roomframe.c_str() );
	}
}

void LServer::ServerInfo(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::ServerInfo* pMsg = (LyvcMessage::ServerInfo*) pMessage;
	m_sDeleteConferenceURL = pMsg->DeleteConferenceURL;
	m_sCreateConferenceNoticeURL = pMsg->CreateConferenceNoticeURL;
	m_sCreateBulletinURL = pMsg->CreateBulletinURL;
	m_sEditConferenceURL = pMsg->EditConferenceURL;
	m_sConferenceModeMgrURL = pMsg->ConferenceModeMgrURL;
	m_sSystemRoleMgrURL = pMsg->SystemRoleMgrURL;
	m_sCommonContactMgrURL = pMsg->CommonContactMgrURL;
	m_sCreateFromalConferenceURL = pMsg->CreateFormalConferenceURL;
	m_sSelfInfoMgrURL = pMsg->SelfInfoMgrURL;
	m_sUserRegisterURL = pMsg->UserRegisterURL;
	m_sClientDownloadURL = pMsg->ClientDownloadURL;

    string sIP = this->getIP();
	if( m_sDeleteConferenceURL.find(sIP) == -1 ) 
		m_sDeleteConferenceURL = string("http://")+ sIP + m_sDeleteConferenceURL;
	if( m_sCreateConferenceNoticeURL.find(sIP) == -1 )
		m_sCreateConferenceNoticeURL = string("http://")+ sIP + m_sCreateConferenceNoticeURL;
	if( m_sCreateBulletinURL.find(sIP) == -1 )
		m_sCreateBulletinURL = string("http://")+ sIP + m_sCreateBulletinURL;
	if( m_sEditConferenceURL.find(sIP) == -1 )
		m_sEditConferenceURL = string("http://")+ sIP + m_sEditConferenceURL;
	if( m_sConferenceModeMgrURL.find(sIP) == -1 )
		m_sConferenceModeMgrURL = string("http://")+ sIP + m_sConferenceModeMgrURL;
	if( m_sSystemRoleMgrURL.find(sIP) == -1 )
		m_sSystemRoleMgrURL = string("http://")+ sIP + m_sSystemRoleMgrURL;
	if( m_sCommonContactMgrURL.find(sIP) == -1 )
		m_sCommonContactMgrURL = string("http://")+ sIP + m_sCommonContactMgrURL;
	if( m_sCreateFromalConferenceURL.find(sIP) == -1 )
		m_sCreateFromalConferenceURL = string("http://")+ sIP + m_sCreateFromalConferenceURL;
	if( m_sSelfInfoMgrURL.find(sIP) == -1 )
		m_sSelfInfoMgrURL = string("http://")+ sIP + m_sSelfInfoMgrURL;
	if( m_sUserRegisterURL.find(sIP) == -1 )
		m_sUserRegisterURL = string("http://")+ sIP + m_sUserRegisterURL;
	if( m_sClientDownloadURL.find(sIP) == -1 )
		m_sClientDownloadURL = string("http://")+ sIP + m_sClientDownloadURL;

}

void LServer::KickDuplicateLogin(LyvcMessage::BaseMessage* pMessage)
{
	CString* str = new CString( "重复登录!" );
	::PostMessage( m_pMainFrame->GetSafeHwnd(), WM_DISCONNECT_SERVER, (WPARAM)this, (LPARAM)str );
}

void LServer::LoginSuccessfully(LyvcMessage::BaseMessage* pMessage)
{
	if( m_expertId > 0 )
	{
		vector<__int64> v;
		v.push_back( m_expertId );
		getConferenceMgr()->cmdCreateInstantConference(v);
		m_expertId = 0;
	}
	if( m_confIdFromWeb > 0 )
	{
		LyvcMessage::InviteConference msg;
		msg.conferenceId = m_confIdFromWeb;
        getConferenceMgr()->InviteConference(&msg);
		m_confIdFromWeb = 0;
	}
}

void LServer::KeepAlive(LyvcMessage::BaseMessage* pMessage)
{
}

string LServer::getRemoteIP()
{
	return this->m_pMatrixC->getRemoteIP();
}

void LServer::setTreeItem( HTREEITEM hItem, int nType ) 
{
	switch ( nType )
	{
	case CONTACTDLG_ITEM:
		m_htiContact = hItem;
		break;
	case CONFERENCEDLG_ITEM:
		m_htiConference = hItem;
		break;
	case SYSMSGDLG_ITEM:
		m_htiSysmsg = hItem;
		break;
	default:
		//ASSERT(FALSE);
		break;
	}
}

HTREEITEM LServer::getTreeItem( int nType )
{
	switch ( nType )
	{
	case CONTACTDLG_ITEM:
		return m_htiContact;
	case CONFERENCEDLG_ITEM:
		return m_htiConference;
	case SYSMSGDLG_ITEM:
		return m_htiSysmsg;
	default:
		break;
	}
	//ASSERT( FALSE );
	return NULL;
}
