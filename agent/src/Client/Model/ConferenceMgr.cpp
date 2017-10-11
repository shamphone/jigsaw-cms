#include "StdAfx.h"
#include "ConferenceMgr.h"
#include "Conference.h"
#include "ConferenceModelMgr.h"
#include "MultiConferenceMatrixC.h"
#include "LServer.h"
#include "Contact\ContactMgr.h"
#include "Room\RunningConference.h"
#include "..\UI\MainFrm\MainFrm.h"
#include "..\UI\MainFrm\ConferenceDlg.h"
#include "..\..\Common\Common\Log\Log.h"

#include "message/MatrixC/MatrixCLib/MatrixC.h"
#include "message/MatrixC/MatrixCLib/message/MediaServerAddress.h"
#include "message/MatrixC/MatrixCLib/message/GetConferenceList.h"
#include "message/MatrixC/MatrixCLib/message/AddConference.h"
#include "message/MatrixC/MatrixCLib/message/DeleteConference.h"
#include "message/MatrixC/MatrixCLib/message/ConferenceFinish.h"
#include "message/MatrixC/MatrixCLib/message/ModifyConference.h"
#include "message/MatrixC/MatrixCLib/message/StartConference.h"
#include "message/MatrixC/MatrixCLib/message/InviteConference.h"
#include "message/MatrixC/MatrixCLib/message/KickUserFromConference.h"
#include "message/MatrixC/MatrixCLib/message/CreateInstantConference.h"
#include "message/MatrixC/MatrixCLib/message/DeleteConferenceUser.h"

ConferenceMgr::ConferenceMgr( LyvcMessage::MatrixC* pMatrixC, LServer* pServer ) : LyvcMessage::MessageTarget(pMatrixC)
{
	m_pServer = pServer;
    m_pMatrixC->registerMessageHandler(LyvcMessage::MediaServerAddress::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::MediaServerAddress));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AddConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::AddConference));
	m_pMatrixC->registerMessageHandler(LyvcMessage::DeleteConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::DeleteConference));
	m_pMatrixC->registerMessageHandler(LyvcMessage::ConferenceFinish::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::ConferenceFinish));
	m_pMatrixC->registerMessageHandler(LyvcMessage::ModifyConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::ModifyConference));
    m_pMatrixC->registerMessageHandler(LyvcMessage::StartConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::StartConference));
    m_pMatrixC->registerMessageHandler(LyvcMessage::InviteConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::InviteConference));
    m_pMatrixC->registerMessageHandler(LyvcMessage::KickUserFromConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::KickUserFromConference));
    m_pMatrixC->registerMessageHandler(LyvcMessage::DeleteConferenceUser::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceMgr::DeleteConferenceUser));

}

ConferenceMgr::~ConferenceMgr()
{
	// Remove all handlers
	this->m_pMatrixC->removeObjectMessageHandler(this);

	//调用所有运行中的会议的清理方法
	stopAllRunningConference();

	//删除所有的会议
	ConferenceMap::iterator it = m_conferences.begin();
	while(it != m_conferences.end())
	{
		Conference* pConference = it->second;
		delete pConference;
		it++;
	}
	m_conferences.clear();
}

Conference* ConferenceMgr::getConferenceByItem( HTREEITEM hItem )
{
	ConferenceMap::iterator it = m_conferences.begin();
	while( it != m_conferences.end() )
	{
		Conference* pConference = it->second;
		if( pConference->m_hItem == hItem )
			return pConference;
		it++;
	}
	ASSERT( FALSE );
	return NULL;
}

Conference* ConferenceMgr::getConferenceByName( string sConfName )
{
	ConferenceMap::iterator it = m_conferences.begin();
	while( it != m_conferences.end() )
	{
		Conference* pConference = it->second;
		if( pConference->m_dbConference.m_sName == sConfName )
			return pConference;
		it++;
	}
	return NULL;
}

void ConferenceMgr::addConference(Conference* pConference)
{
	ConferenceMap::iterator it = m_conferences.find( pConference->m_dbConference.m_id );
	_ASSERTE(it == m_conferences.end());
	m_conferences.insert(ConferenceMap::value_type(pConference->m_dbConference.m_id, pConference));
}

void ConferenceMgr::deleteConference(__int64 conferenceID)
{
	ConferenceMap::iterator it = m_conferences.find(conferenceID);
	if( it != m_conferences.end())
	{
		Conference* pConference = it->second;
		delete pConference;
		m_conferences.erase(it);
	}
	else
	{
		_ASSERTE(FALSE);
	}
    return;
}

Conference* ConferenceMgr::getConferenceByID(__int64 conferenceID)
{
	ConferenceMap::iterator it = m_conferences.find(conferenceID);
	if( it != m_conferences.end())
	{
		return it->second;
	}
	return NULL;
}

RunningConference* ConferenceMgr::getRunningConferenceByID(__int64 conferenceID)
{
	RunningConferenceMap::iterator it = m_runningConferences.find(conferenceID);
	if( it != m_runningConferences.end())
	{
		return it->second;
	}
	else
	{
        _ASSERTE(FALSE);
		return NULL;
	}
}

void ConferenceMgr::addRunningConference(RunningConference* pRunningConference)
{
	m_runningConferences.insert(RunningConferenceMap::value_type(pRunningConference->getConferenceId(), pRunningConference));
    return;
}

void ConferenceMgr::deleteRunningConference(__int64 conferenceID)
{
	RunningConferenceMap::iterator it = m_runningConferences.find(conferenceID);
	if( it != m_runningConferences.end())
	{
		delete it->second;
		m_runningConferences.erase(it);
	}
	else
	{
        _ASSERTE(FALSE);
	}
    return;
}

BOOL ConferenceMgr::isConferenceGoing(__int64 conferenceId)
{
	RunningConferenceMap::iterator it = m_runningConferences.find(conferenceId);
	if( it != m_runningConferences.end())
	{
		return TRUE;
	}
    return FALSE; 
}

void ConferenceMgr::stopAllRunningConference()
{
	RunningConferenceMap::iterator it = m_runningConferences.begin();
	while( it != m_runningConferences.end() )
	{
		RunningConference* pRunningConference = it->second;
		pRunningConference->destroy();
		delete pRunningConference;
		it++;
	}

	m_runningConferences.clear();
}

RunningConference* ConferenceMgr::createRunningConference( Conference* pConference, CMeetingRoomFrame* pMeetingRoomFrame)
{
    // 创建该会议使用的消息系统
	__int64 conferenceId = pConference->m_dbConference.m_id;
    MultiConferenceMatrixC* pMultiConferenceMatrixC = new MultiConferenceMatrixC(this->m_pMatrixC, conferenceId);
    this->m_MultiConferenceMatrixCMap.insert( MultiConferenceMatrixCMap::value_type(conferenceId, pMultiConferenceMatrixC));

	// 将会议增加到运行会议的列表中
	RunningConference* pRunningConference = new RunningConference(
		pConference, 
		pMeetingRoomFrame, 
		this->m_pServer->getSelfInfo(),
        pMultiConferenceMatrixC,
		this->m_pServer->getTransModel(),
		this->m_pServer->getConferenceModeMgr() );
	addRunningConference(pRunningConference);

	// 调用会议的初始化
	pRunningConference->create();

	// 返回会议指针
	return pRunningConference;
}

void ConferenceMgr::destroyRunningConference(__int64 conferenceId)
{
	// 调用会议的清理函数
	RunningConference* pRunningConference = getRunningConferenceByID(conferenceId);
	pRunningConference->destroy();

	// 从会议列表中删除该会议
	deleteRunningConference(conferenceId);

    // 删除该会议的消息系统
    MultiConferenceMatrixCMap::iterator it = this->m_MultiConferenceMatrixCMap.find(conferenceId);
    _ASSERTE( it != this->m_MultiConferenceMatrixCMap.end());
    it->second->Destroy();
    delete it->second;
    m_MultiConferenceMatrixCMap.erase(it);

	return;
}

void ConferenceMgr::cmdGetConferenceList() const
{
    LyvcMessage::GetConferenceList getConferenceList;
    this->m_pMatrixC->sendMessage(&getConferenceList);
}

void ConferenceMgr::cmdCreateInstantConference(vector<__int64> participants)
{
    ASSERT( participants.size() != 0 );
	if( participants.size() == 0 )
		return;
	// 对于2个人的情况根据将要创建的临时会议的名称检查是否有相同的会议存在
	// 如果存在，直接加入会议，并邀请其他人加入
	// 如果不存在，发送创建会议命令
	if( participants.size() == 1 )
	{
		__int64 contactId = participants.at(0);
		string sConfName = m_pServer->getRealName() + string("与") 
						   + m_pServer->getContactMgr()->getRealnameById( contactId ) + string( "的临时会议" );
		string sConfName1 = m_pServer->getContactMgr()->getRealnameById( contactId ) 
						   + string("与") + m_pServer->getRealName() + string( "的临时会议" );
		Conference* pConference = this->getConferenceByName( sConfName );
		Conference* pConference1 = this->getConferenceByName( sConfName1 );
		LyvcMessage::InviteConference msg;
		msg.holderId = m_pServer->getId();
		if( pConference != NULL && pConference1 != NULL )
		{
			if( isConferenceGoing( pConference->m_dbConference.m_id ) )
			{
				msg.conferenceId = pConference->m_dbConference.m_id;
			}
			else if( isConferenceGoing( pConference1->m_dbConference.m_id ) )
			{
				msg.conferenceId = pConference1->m_dbConference.m_id;
			}
			else
			{
				// 将自己加入会议
				msg.contactId = m_pServer->getId();
				msg.conferenceId = pConference->m_dbConference.m_id;
				this->InviteConference( &msg );
			}
			m_pServer->getMainFrame()->getConferenceDlg()->showRoom( m_pServer, msg.conferenceId );
			// 邀请别人加入
			msg.contactId = contactId;
			m_pMatrixC->sendMessage( &msg );
			return;
		}
		else if( pConference != NULL )
		{
			// 邀请别人加入
			msg.conferenceId = pConference->m_dbConference.m_id;
			msg.contactId = contactId;
			m_pMatrixC->sendMessage( &msg );
			if( !isConferenceGoing( pConference->m_dbConference.m_id ) )
			{
				// 将自己加入会议
				msg.contactId = m_pServer->getId();
				this->InviteConference( &msg );
			}
		    m_pServer->getMainFrame()->getConferenceDlg()->showRoom( m_pServer, msg.conferenceId );
			return;
		}
		else if( pConference1 != NULL )
		{
            // 邀请别人加入会议
			msg.conferenceId = pConference1->m_dbConference.m_id;
			msg.contactId = contactId;
			m_pMatrixC->sendMessage( &msg );
			if( !isConferenceGoing( pConference1->m_dbConference.m_id ) )
			{
				// 将自己加入会议
				msg.contactId = m_pServer->getId();
				this->InviteConference( &msg );
			}
		    m_pServer->getMainFrame()->getConferenceDlg()->showRoom( m_pServer, msg.conferenceId );
			return;
		}
	}
	const int BUFFERSIZE = 64;
	char buffer[BUFFERSIZE];

    // Fill first id
	string result;
    _snprintf(buffer, BUFFERSIZE, "%I64d", participants.at(0));
    result = result + buffer;

    // append other ids
    for(unsigned int i=1; i<participants.size(); i++)
    {
        result = result + ",";
        _snprintf(buffer, BUFFERSIZE, "%I64d", participants.at(i));
        result = result + buffer;
    }

    LyvcMessage::CreateInstantConference msg;
    msg.participants = result;
    this->m_pMatrixC->sendMessage(&msg);
}

void ConferenceMgr::MediaServerAddress(LyvcMessage::BaseMessage* pMessage)
{
	cmdGetConferenceList();
}

void ConferenceMgr::AddConference(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::AddConference* pAddConference = (LyvcMessage::AddConference*)pMessage;
	if( this->getConferenceByID( pAddConference->conId ) != NULL )
	{
		//ASSERT(FALSE);
		return;
	}
	DBConference dbConference;
	dbConference.m_creatorId = pAddConference->creatorId;
    dbConference.m_modelId = pAddConference->conModelId;
    dbConference.m_id = pAddConference->conId;
    dbConference.m_sName = pAddConference->conName;
    dbConference.m_sDescription = pAddConference->conDesc;
    dbConference.m_tmEndTime = CTime(pAddConference->endTime);
    dbConference.m_tmStartTime = CTime(pAddConference->startTime);
	dbConference.m_sConfFileDesc = pAddConference->conFilesDesc;
	dbConference.m_sConfFileURL = pAddConference->conFilesURL;

	Conference* pConference = new Conference(dbConference);
	if( pAddConference->isEnded )
		pConference->setStatus( Conference::Finished );
	else if( pAddConference->isStarted )
		pConference->setStatus( Conference::Active );
	else
		pConference->setStatus( Conference::Inactive );

	addConference(pConference);

	// Call interface
	if( pConference->m_dbConference.m_modelId != m_pServer->getConferenceModeMgr()->getInstantConferenceModelId())
	{
		m_pServer->getMainFrame()->getConferenceDlg()->notifyAddConference( pConference, this->m_pServer );
	}
	m_pServer->getMainFrame()->getConferenceDlg()->serverReconnect( m_pServer, pConference );
}

void ConferenceMgr::ConferenceFinish(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::ConferenceFinish* pMsg = (LyvcMessage::ConferenceFinish*) pMessage;
	Conference* pConference = getConferenceByID(pMsg->conId);
	if( pConference == NULL || pConference->getStatus() != Conference::Active )
	{
//		ASSERT( FALSE );
		return;
	}
	pConference->setStatus(Conference::Finished);
	if( pConference->m_dbConference.m_modelId != m_pServer->getConferenceModeMgr()->getInstantConferenceModelId() )
	{
        m_pServer->getMainFrame()->getConferenceDlg()->notifyConferenceFinish( pConference, this->m_pServer );
	}
	else
	{
		this->deleteConference( pMsg->conId );
	}
}

void ConferenceMgr::DeleteConference(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::DeleteConference* pMsg = (LyvcMessage::DeleteConference*) pMessage;
	Conference* pConference = getConferenceByID(pMsg->conId);
	if( pConference == NULL || pConference->getStatus() == Conference::Active )
	{
		ASSERT( FALSE );
		return;
	}
	if( pConference->m_dbConference.m_modelId != m_pServer->getConferenceModeMgr()->getInstantConferenceModelId() )
	{
		m_pServer->getMainFrame()->getConferenceDlg()->notifyDeleteConference( pConference, this->m_pServer );
	}
	deleteConference(pMsg->conId);
}

void ConferenceMgr::ModifyConference(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::ModifyConference* pMsg = (LyvcMessage::ModifyConference*)pMessage;
	Conference* pConference = getConferenceByID(pMsg->conId);
	if( pConference == NULL )
	{
		ASSERT( FALSE );
		return;
	}
	pConference->m_dbConference.m_modelId = pMsg->conModelId;
	pConference->m_dbConference.m_sDescription = pMsg->conDesc;
	pConference->m_dbConference.m_sName = pMsg->conName;
	pConference->m_dbConference.m_tmEndTime = pMsg->endTime;
	pConference->m_dbConference.m_tmStartTime = pMsg->startTime;
	pConference->m_dbConference.m_sConfFileDesc = pMsg->conFileDesc;
	pConference->m_dbConference.m_sConfFileURL = pMsg->conFileURL;

	//Call Client Frame modify conference.
	m_pServer->getMainFrame()->getConferenceDlg()->notifyModifyConference( pConference, this->m_pServer );
}

void ConferenceMgr::StartConference(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::StartConference* pStartConference = (LyvcMessage::StartConference*)pMessage;
    Conference* pConference = getConferenceByID(pStartConference->conferenceId);
	if( pConference == NULL || pConference->getStatus() != Conference::Inactive )
	{
//		ASSERT(FALSE);
		return;
	}
	pConference->setStatus(Conference::Active);
	// 通知界面
	if( pConference->m_dbConference.m_modelId != m_pServer->getConferenceModeMgr()->getInstantConferenceModelId())
	{
		m_pServer->getMainFrame()->getConferenceDlg()->notifyStartConference( pConference, this->m_pServer );
	}
}

void ConferenceMgr::InviteConference(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::InviteConference* pMsg = (LyvcMessage::InviteConference*) pMessage;
	__int64 confId = pMsg->conferenceId;
	Conference* pConference = getConferenceByID( confId );
	if( pConference == NULL || pConference->getStatus() != Conference::Active )
	{
//		ASSERT(FALSE);
		return;
	}
	// 有可能因为收到多个邀请加入同一个会议
	// 如果已在该会议中，不再加入
	if( !isConferenceGoing( confId ) )
	{
		int nShow = SW_SHOWMINNOACTIVE;
		if( pConference->m_dbConference.m_modelId == m_pServer->getConferenceModeMgr()->getInstantConferenceModelId() )
		{
			if( pMsg->holderId != this->m_pServer->getId() )
				nShow = SW_HIDE;
			else
				nShow = SW_NORMAL;
		}
		m_pServer->getMainFrame()->getConferenceDlg()->notifyInviteConference( pConference, m_pServer, nShow );
	}
}

void ConferenceMgr::KickUserFromConference(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::KickUserFromConference* pMsg = (LyvcMessage::KickUserFromConference*) pMessage;
	if( pMsg->confUserId != m_pServer->getId() )
	{
		ASSERT( FALSE );
		return;
	}
	if( isConferenceGoing( pMsg->confId ) )
	{
		m_pServer->getMainFrame()->getConferenceDlg()->notifyKickUserFromConference( pMsg->confId, this->m_pServer );
	}
}

void ConferenceMgr::DeleteConferenceUser(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::DeleteConferenceUser* pMsg = (LyvcMessage::DeleteConferenceUser*) pMessage;
	if( pMsg->userId != m_pServer->getId() )
	{
		ASSERT( FALSE );
		return;
	}
	Conference* pConference = this->getConferenceByID( pMsg->_conferenceId );
	if( pConference == NULL )
	{
		ASSERT( FALSE );
		return;
	}
	if( isConferenceGoing( pMsg->_conferenceId ) )
	{
		m_pServer->getMainFrame()->getConferenceDlg()->notifyKickUserFromConference( pMsg->_conferenceId, m_pServer );
	}
	m_pServer->getMainFrame()->getConferenceDlg()->notifyDeleteConference( pConference, m_pServer );
}
