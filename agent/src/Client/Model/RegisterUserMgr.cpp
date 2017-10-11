#include "stdafx.h"
#include "Flvcc.h"
#include "RegisterUserMgr.h"
#include ".\registerusermgr.h"
#include "LServer.h"
#include "..\UI\MainFrm\RegisterUserDlg.h"
#include "..\UI\MainFrm\UserDefinedMessage.h"

#include "..\..\message\MatrixC\MatrixCLib\MatrixC.h"
#include "..\..\message\MatrixC\MatrixCLib\message\RegisterUser.h"
#include "..\..\message\MatrixC\MatrixCLib\message\RegisterUserResponse.h"

RegisterUserMgr::RegisterUserMgr( LServer* pServer, CRegisterUserDlg* pRegisterDlg ) : LyvcMessage::MessageTarget( NULL )
{
	this->m_pServer = pServer;
	this->m_pRegisterDlg = pRegisterDlg;
}

RegisterUserMgr::~RegisterUserMgr(void)
{
}

bool RegisterUserMgr::create()
{
	HOSTENT* pHostEnt = gethostbyname( m_pServer->getIP().c_str() );
	if( pHostEnt == NULL )
	{
		return false;
	}
	string IP = inet_ntoa(*(struct in_addr *)*pHostEnt->h_addr_list);

    m_pMatrixC = LyvcMessage::MatrixC::getInstance();
	if( !m_pMatrixC->Create( IP.c_str(), m_pServer->getPort() ) )
    {
		LyvcMessage::MatrixC::releaseInstance(this->m_pMatrixC);
		m_pMatrixC = NULL;
		return false;
    }

	m_pMatrixC->registerMessageHandler(LyvcMessage::RegisterUserResponse::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(RegisterUserMgr::RegisterUserResponse));
	return true;
}

void RegisterUserMgr::destroy()
{
	if( m_pMatrixC )
	{
		m_pMatrixC->removeObjectMessageHandler(this);
		m_pMatrixC->Destroy();
		LyvcMessage::MatrixC::releaseInstance(m_pMatrixC);
	}
}

void RegisterUserMgr::cmdRegisterUser( string userName, string lastName, string firstName, string email, string password, string domain)
{
	if( this->m_pMatrixC == NULL )
	{
		ASSERT(FALSE);
		return;
	}
	LyvcMessage::RegisterUser msg;
	msg.userName = userName;
	msg.lastName = lastName;
	msg.firstName = firstName;
	msg.email = email;
	msg.password = password;
	msg.domain = domain;

	this->m_pMatrixC->sendMessage( &msg );
}

void RegisterUserMgr::RegisterUserResponse(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::RegisterUserResponse* pMsg = (LyvcMessage::RegisterUserResponse*)pMessage;
	string* pStr = new string( pMsg->message );
	::PostMessage( m_pRegisterDlg->GetSafeHwnd(), WM_REGISTERUSER_RESPONSE, pMsg->bSuccessful, (LPARAM)pStr );
}
