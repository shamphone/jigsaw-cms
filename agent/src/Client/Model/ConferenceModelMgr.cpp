#include "StdAfx.h"
#include "ConferenceModelMgr.h"
#include "ConferenceModel.h"
#include "ConferenceRole.h"

#include "message/MatrixC/MatrixCLib/MatrixC.h"
#include "message/MatrixC/MatrixCLib/message/UserDetail.h"
#include "message/MatrixC/MatrixCLib/message/GetConferenceModelTable.h"
#include "message/MatrixC/MatrixCLib/message/GetConferenceModelRoleTable.h"
#include "message/MatrixC/MatrixCLib/message/ConferenceModelTableItem.h"
#include "message/MatrixC/MatrixCLib/message/ConferenceModelRoleTableItem.h"
#include "message/MatrixC/MatrixCLib/message/InstantConferenceModelId.h"

ConferenceModelMgr::ConferenceModelMgr( LyvcMessage::MatrixC* pMatrixC )
	: LyvcMessage::MessageTarget(pMatrixC)
{
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserDetail::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceModelMgr::UserDetail));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ConferenceModelTableItem::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceModelMgr::ConferenceModelTableItem));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ConferenceModelRoleTableItem::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceModelMgr::ConferenceModelRoleTableItem));
    m_pMatrixC->registerMessageHandler(LyvcMessage::InstantConferenceModelId::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ConferenceModelMgr::InstantConferenceModelId));
}

ConferenceModelMgr::~ConferenceModelMgr()
{
	// Remove all handlers
	this->m_pMatrixC->removeObjectMessageHandler(this);

	ConferenceModelMap::iterator it;
    for(it=m_modes.begin(); it != m_modes.end(); it++)
    {
        delete it->second;
    }
    m_modes.clear();
}

__int64 ConferenceModelMgr::getInstantConferenceModelId()
{
	return this->m_instantConferenceModelId;
}

void ConferenceModelMgr::setInstantConferenceModelId(__int64 id)
{
	this->m_instantConferenceModelId = id;
}

void ConferenceModelMgr::addConferenceMode(__int64 nID, string strName)
{
    ConferenceModel* pMode = new ConferenceModel(nID, strName);
    m_modes.insert( ConferenceModelMap::value_type( nID, pMode));
}

void ConferenceModelMgr::addConferenceModeRole(__int64 nModeID, __int64 nRoleID, string strName, string rights)
{
    // find the conference mode
    ConferenceModel* pMode = this->getConferenceModeByID(nModeID);

    // Add the new Role
    ConferenceRole role(nRoleID, strName);

	unsigned int begin = 0;
	while(begin < rights.length()){
		unsigned int end = rights.find_first_of(",", begin);
		if(end == string::npos){
			role.addRight(_atoi64(rights.substr(begin, rights.length() - begin).c_str()));
			break;
		}
		if(end > begin){
			role.addRight(_atoi64(rights.substr(begin, end - begin).c_str()));
			begin = end + 1;
		}

	}
    pMode->addRole(role);
}

string ConferenceModelMgr::getConferenceModeNameByID(__int64 nID)
{
    ConferenceModel* pMode = this->getConferenceModeByID(nID);
	return pMode->getName();
}

string ConferenceModelMgr::getConferenceRoleNameByID(__int64 nModeID, __int64 nRoleID)
{
    ConferenceModel* pMode = this->getConferenceModeByID(nModeID);
	return pMode->getRoleName(nRoleID);
}

ConferenceModel* ConferenceModelMgr::getConferenceModeByID(__int64 nModeID)
{
    ConferenceModelMap::iterator it = m_modes.find(nModeID);
    if( it == m_modes.end())
    {
//        _ASSERTE(FALSE);
        return NULL;
    }

    return it->second;
}

void ConferenceModelMgr::cmdGetConferenceModeTable() const
{
    LyvcMessage::GetConferenceModelTable msg;
    this->m_pMatrixC->sendMessage(&msg);
}

void ConferenceModelMgr::cmdGetConferenceModeRoleTable(__int64 nModeID) const
{
    LyvcMessage::GetConferenceModelRoleTable msg;
    msg.modelId = nModeID;
    this->m_pMatrixC->sendMessage(&msg);
}

void ConferenceModelMgr::UserDetail(LyvcMessage::BaseMessage* pMessage)
{
	// 请求会议模式列表
	cmdGetConferenceModeTable();
}

void ConferenceModelMgr::ConferenceModelTableItem(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::ConferenceModelTableItem* pModelTable = (LyvcMessage::ConferenceModelTableItem*)pMessage;
    addConferenceMode(pModelTable->modelId, pModelTable->modelName);

    // 获取该模式的详细角色
    this->cmdGetConferenceModeRoleTable(pModelTable->modelId);
}

void ConferenceModelMgr::ConferenceModelRoleTableItem(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::ConferenceModelRoleTableItem* pRoleTable = (LyvcMessage::ConferenceModelRoleTableItem*)pMessage;
    addConferenceModeRole(pRoleTable->modelId, pRoleTable->roleId, pRoleTable->roleName, pRoleTable->rights);
}

void ConferenceModelMgr::InstantConferenceModelId(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::InstantConferenceModelId* pMsg = (LyvcMessage::InstantConferenceModelId*)pMessage;
	setInstantConferenceModelId( pMsg->instantConferenceModelId);
}
