#include "stdafx.h"
#include "flvcc.h"
#include "ContactMgr.h"
#include "..\..\UI\MainFrm\MainFrm.h"
#include "..\..\UI\MainFrm\ContactDlg.h"
#include "..\..\UI\MainFrm\SysInfoDlg.h"
#include "..\LServer.h"
#include "Contact.h"
#include "ContactGroup.h"

#include "message/MatrixC/MatrixCLib/MatrixC.h"
#include "message/MatrixC/MatrixCLib/message/UserDetail.h"
#include "message/MatrixC/MatrixCLib/message/DefaultGroupId.h"
#include "message/MatrixC/MatrixCLib/message/GetPendingMessageList.h"
#include "message/MatrixC/MatrixCLib/message/SearchContact.h"
#include "message/MatrixC/MatrixCLib/message/SearchContactResult.h"
#include "message/MatrixC/MatrixCLib/message/ApplyContact.h"
#include "message/MatrixC/MatrixCLib/message/AgreeContact.h"
#include "message/MatrixC/MatrixCLib/message/RejectContact.h"
#include "message/MatrixC/MatrixCLib/message/AddGroup.h"
#include "message/MatrixC/MatrixCLib/message/AddContact.h"
#include "message/MatrixC/MatrixCLib/message/RemoveGroup.h"
#include "message/MatrixC/MatrixCLib/message/RemoveContact.h"
#include "message/MatrixC/MatrixCLib/message/ChangeGroupName.h"
#include "message/MatrixC/MatrixCLib/message/MoveContactToGroup.h"
#include "message/MatrixC/MatrixCLib/message/UserNotFoundByEmail.h"
#include "message/MatrixC/MatrixCLib/message/UpdateContactStatus.h"
#include "message/MatrixC/MatrixCLib/message/MoveContactToGroup.h"
#include "message/MatrixC/MatrixCLib/message/Leaveword.h"
#include "message/MatrixC/MatrixCLib/message/UserChannelBroken.h"
#include "message/MatrixC/MatrixCLib/message/SetContactAdmin.h"
#include "message/MatrixC/MatrixCLib/message/CopyContact.h"
#include "message/MatrixC/MatrixCLib/message/ConferenceNotice.h"
#include "message/MatrixC/MatrixCLib/message/SystemMessage.h"
#include "message/MatrixC/MatrixCLib/message/MoveContactToGroup.h"
#include "message/MatrixC/MatrixCLib/message/ChangeGroupName.h"

ContactMgr::ContactMgr( LyvcMessage::MatrixC* pMatrixC, LServer* pServer ) : LyvcMessage::MessageTarget(pMatrixC)
{
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserDetail::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::UserDetail));
    m_pMatrixC->registerMessageHandler(LyvcMessage::DefaultGroupId::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::DefaultGroupId));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ApplyContact::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::ApplyContact));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AddGroup::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::AddGroup));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AddContact::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::AddContact));
    m_pMatrixC->registerMessageHandler(LyvcMessage::AgreeContact::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::AgreeContact));
    m_pMatrixC->registerMessageHandler(LyvcMessage::RejectContact::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::RejectContact));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserNotFoundByEmail::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::UserNotFoundByEmail));
    m_pMatrixC->registerMessageHandler(LyvcMessage::RemoveContact::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::RemoveContact));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UpdateContactStatus::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::UpdateContactStatus));
    m_pMatrixC->registerMessageHandler(LyvcMessage::Leaveword::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::Leaveword));
	m_pMatrixC->registerMessageHandler(LyvcMessage::SearchContactResult::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::SearchContactResult));
    m_pMatrixC->registerMessageHandler(LyvcMessage::UserChannelBroken::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::UserChannelBroken));
    m_pMatrixC->registerMessageHandler(LyvcMessage::SetContactAdmin::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::SetContactAdmin));
    m_pMatrixC->registerMessageHandler(LyvcMessage::ConferenceNotice::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::ConferenceNotice));
    m_pMatrixC->registerMessageHandler(LyvcMessage::SystemMessage::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::SystemMessage));
	//添加移动组的处理方法
//	m_pMatrixC->registerMessageHandler(LyvcMessage::MoveContactToGroup::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::MoveContactToGroup));
	//添加修改组名的处理方法
//	m_pMatrixC->registerMessageHandler(LyvcMessage::ChangeGroupName::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::ChangeGroupName));
	//添加删除用户的处理方法
	m_pMatrixC->registerMessageHandler(LyvcMessage::RemoveContact::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(ContactMgr::RemoveContact));

	m_nReceivedLeavewords = 0;
	m_bContactAdmin = FALSE;
	m_pServer = pServer;
	this->m_pContactDlg = m_pServer->getMainFrame()->getContactDlg();
	ContactGroup* pGroup = new ContactGroup( 0, "公共联系人", true, NULL );
	pGroup->m_hItem = m_pContactDlg->getCommonGroupItem( this->m_pServer );
	this->m_ContactGroups[pGroup->m_hItem] = pGroup;
}

ContactMgr::~ContactMgr()
{
	// 释放联系人和组资源
	ContactGroupMap::iterator itGroups = this->m_ContactGroups.begin();
	while( itGroups != this->m_ContactGroups.end() )
	{
		ContactGroup* pGroup = itGroups->second;
		delete pGroup;
		itGroups++;
	}
	ContactMap::iterator itContacts = this->m_Contacts.begin();
	while( itContacts != this->m_Contacts.end() )
	{
		Contact* pContact = itContacts->second;
		delete pContact;
		itContacts++;
	}
	// Remove all handlers
	this->m_pMatrixC->removeObjectMessageHandler(this);
}

ContactGroup* ContactMgr::getContactGroupByName( string name, bool bIsCommon )
{
	ContactGroup* pGroup = this->findContactGroup( -1, name, false );
	ASSERT( pGroup != NULL );
	return pGroup;
}

string ContactMgr::getEmailById( __int64 contactId )
{
	Contact* pContact = this->findContact( contactId, "", false, true );
	ASSERT( pContact != NULL );
	if( pContact == NULL )
		return "";
	return pContact->getEmail();
}

string ContactMgr::getRealnameById( __int64 contactId )
{
	Contact* pContact = this->findContact( contactId, "", false, true );
	ASSERT( pContact != NULL );
	if( pContact == NULL )
		return "";
	return pContact->getRealName();
}

void ContactMgr::getContacts( vector<Contact*>& v, int status )
{
	ContactMap::iterator it = this->m_Contacts.begin();
	while( it != this->m_Contacts.end() )
	{
		Contact* pContact = it->second;
		if( pContact->getStatus() & status )
		{
			bool bFound = false;
			vector<Contact*>::iterator it_v = v.begin();
			while( it_v != v.end() )
			{
				if( (*it_v)->getId() == pContact->getId() )
					bFound = true;
				it_v++;
			}
			if( !bFound )
                v.push_back( pContact );;
		}
		it++;
	}
}

void ContactMgr::getPrivateGroupNames( vector<string>& v )
{
	ContactGroupMap::iterator itGroups = this->m_ContactGroups.begin();
	while( itGroups != this->m_ContactGroups.end() )
	{
		ContactGroup* pGroup = itGroups->second;
		if( !pGroup->m_bIsCommon )
			v.push_back( pGroup->m_sName );
		itGroups++;
	}
}

__int64 ContactMgr::getDefaultGroupId()
{
	return m_DefaultGroupId;
}

Contact* ContactMgr::getContactByTreeItem( HTREEITEM hItem )
{
	ASSERT( hItem != NULL );
	ContactMap::iterator it = this->m_Contacts.find( hItem );
	ASSERT( it != this->m_Contacts.end() );
	if( it != this->m_Contacts.end() )
		return it->second;
	return NULL;
}

ContactGroup* ContactMgr::getContactGroupByTreeItem( HTREEITEM hItem )
{
	ASSERT( hItem != NULL );
	ContactGroupMap::iterator it = this->m_ContactGroups.find( hItem );
//	ASSERT( it != this->m_ContactGroups.end() );
	if(it != NULL && it != this->m_ContactGroups.end() )
		return it->second;
	return NULL;
}

void ContactMgr::setContactDlg( CContactDlg* pContactDlg )
{
	ASSERT( pContactDlg != NULL );
	this->m_pContactDlg = pContactDlg;
	ContactGroup* pGroup = new ContactGroup( 0, "公共联系人", true, NULL );
	pGroup->m_hItem = m_pContactDlg->getCommonGroupItem( this->m_pServer );
	this->m_ContactGroups[pGroup->m_hItem] = pGroup;
}

bool ContactMgr::isContactByEmail( string email )
{
	Contact* pContact = findContact( 0, email, false );
	return ( pContact != NULL );
}

bool ContactMgr::isContactById( __int64 uid )
{
	Contact* pContact = findContact( uid, "", false );
	return ( pContact != NULL );
}

bool ContactMgr::isGroupExist(__int64 groupId, string name, bool bIsCommon )
{
	ContactGroup* pGroup = this->searchContactGroup(groupId, name, false );
	return ( pGroup != NULL );
}

bool  ContactMgr::isGroupExists(ContactGroup* pGroup, string name)
{
	ContactGroupMap::iterator it = this->m_ContactGroups.begin();
	while( it != this->m_ContactGroups.end() )
	{
		ContactGroup* pTemp = it->second;			
		
		if(pTemp->m_sName == name)
		{			
			//判断组名是否与其父组的名称相同
			if(pTemp->m_Id == pGroup->m_pParent->m_Id)
				return true;

			//判断组名是否与其兄弟子组的名称相同
			ContactGroup* pParent = pTemp->m_pParent;
			if(pParent != NULL && pParent->m_Id == pGroup->m_pParent->m_Id)
			{
				if(pTemp->m_Id != pGroup->m_Id)
					return true;
			}

			//判断组名是否与其第一层子组的名称相同
			if(pParent != NULL && pParent->m_Id == pGroup->m_Id )
				return true;	
		}	
				
		it++;
	}

	return false;
}

BOOL ContactMgr::isContactAdmin()
{
	return m_bContactAdmin;
}

void ContactMgr::cmdAddContact( Contact* pContact, ContactGroup* pGroup )
{
	ASSERT( pContact && pGroup );
	if( !pContact || !pGroup )
		return;
	LyvcMessage::AddContact msg;
	msg.contactId = pContact->getId();
	msg.email = pContact->getEmail();
	msg.firstName = pContact->getFirstName();
	msg.isCommon = false;
	msg.lastName = pContact->getLastName();
	msg.name = pContact->getName();
	msg.status = pContact->getStatus();
	msg.groupId = pGroup->m_Id;
    m_pMatrixC->sendMessage( &msg );

    AddContact( &msg );
}

void ContactMgr::cmdApplyContact(__int64 uid, string myName, __int64 groupId)
{
	LyvcMessage::ApplyContact msg;
	msg.name = myName;
	msg.receiverId = uid;
	msg.groupId = groupId;
	this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::cmdSearchContact(string email)
{
    // 如果已经存在该Email指定的联系人, 简单返回即可
	if( isContactByEmail( email ) )
	{
		return;
	}
    // 发送消息
    LyvcMessage::SearchContact msg;
    msg.email = email;
    this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::cmdAgreeContact(__int64 contactId, string name, __int64 groupId)
{
    LyvcMessage::AgreeContact msg;
    msg.receiverId = contactId;
    msg.name = name;
	msg.groupId = groupId;
    this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::cmdRemoveGroup( ContactGroup* pGroup )
{
    LyvcMessage::RemoveGroup msg;
    msg.groupId = pGroup->m_Id;
    this->m_pMatrixC->sendMessage(&msg);

	this->removeContactGroup( pGroup );
	delete pGroup;
	pGroup = NULL;
}

void ContactMgr::cmdRejectContact(__int64 contactId, string name)
{
    LyvcMessage::RejectContact msg;
    msg.receiverId = contactId;
    msg.name = name;
    this->m_pMatrixC->sendMessage(&msg);

	_ASSERTE(find(m_ApplyList.begin(), m_ApplyList.end(), contactId) != m_ApplyList.end());
	m_ApplyList.remove(contactId);
}

void ContactMgr::cmdRemoveContact( Contact* pContact )
{
    LyvcMessage::RemoveContact msg;
    msg.contactId = pContact->getId();
	msg.groupId = pContact->m_pGroup->m_Id;
    this->m_pMatrixC->sendMessage(&msg);

	this->removeContact( pContact );
	delete pContact;
	pContact = NULL;
}

void ContactMgr::cmdAddGroup(string groupName, __int64 groupId)
{
    LyvcMessage::AddGroup msg;
    msg.name = groupName;
	msg.groupId = groupId;
    this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::cmdRenameGroup( ContactGroup* pGroup, string newName )
{
	ASSERT( pGroup != NULL );
	if( pGroup == NULL )
		return;
	pGroup->m_sName = newName;

	LyvcMessage::ChangeGroupName msg;
	msg.groupId = pGroup->m_Id;
    msg.newName = newName;
    this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::cmdMoveContactToGroup( Contact* pContact, ContactGroup* pToGroup )
{
	ASSERT( pContact != NULL && pToGroup != NULL );
	if( pContact == NULL || pToGroup == NULL )
		return;

	//发送消息
	LyvcMessage::MoveContactToGroup msg;
    msg.contactId = pContact->getId();
	msg.oldGroupId = pContact->m_pGroup->m_Id;
	msg.newGroupId = pToGroup->m_Id;
    this->m_pMatrixC->sendMessage(&msg);

	// 从原组中删除
	this->removeContact( pContact );
	this->m_pContactDlg->notifyRemoveContact( pContact->m_hItem );
	// 添加到新组
	pToGroup->m_Contacts.push_back( pContact );
	pContact->m_pGroup = pToGroup;
	HTREEITEM hItem = m_pContactDlg->notifyAddContact( pContact );
	pContact->m_hItem = hItem;
	this->m_Contacts[hItem] = pContact;
}

void ContactMgr::cmdSendLeaveword(__int64 contactId, string words, string myName)
{
	int maxPackSize = 1024*60;
	int nTimes = words.length() / maxPackSize;
	int m = words.length() % maxPackSize;
	if ( m > 0 )
	{
		nTimes += 1;
	}

	LyvcMessage::Leaveword msg;
	msg.receiverId = contactId;
	msg.senderName = myName;
	__time64_t t;
	_time64(&t);
	msg.sendDate = t;

	for (int i = 0; i < nTimes - 1; i++)
	{
		msg.content = "";
		msg.content.append(words.c_str() + i*maxPackSize, maxPackSize);
		m_pMatrixC->sendMessage(&msg);
	}
	msg.content = "";
	msg.content.append(words.c_str() + (nTimes - 1)*maxPackSize, m);
	m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::cmdCopyContact( __int64 receiverId, bool bIsGroupId )
{
    LyvcMessage::CopyContact msg;
	msg.receiverId = receiverId;
	msg.bIsGroupId = bIsGroupId;
    this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::UserDetail(LyvcMessage::BaseMessage* pMessage)
{
    LyvcMessage::GetPendingMessageList msg;
    this->m_pMatrixC->sendMessage(&msg);
}

void ContactMgr::DefaultGroupId(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::DefaultGroupId* pMessage = (LyvcMessage::DefaultGroupId*)pBaseMessage;

	m_DefaultGroupId = pMessage->defaultGroupId;
	ContactGroup* pGroup = new ContactGroup( pMessage->defaultGroupId, "我的联系人", false, NULL );
	pGroup->m_hItem = m_pContactDlg->getPrivateGroupItem( this->m_pServer );
	this->m_ContactGroups[pGroup->m_hItem] = pGroup;
}

void ContactMgr::ApplyContact(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::ApplyContact* pMessage = (LyvcMessage::ApplyContact*)pBaseMessage;
	
	// 如果对方已经申请，或者已是联系人，不再提示
	if( find(m_ApplyList.begin(), m_ApplyList.end(), pMessage->_senderId) != m_ApplyList.end()
		|| isContactById(pMessage->_senderId) )
	{
		return;
	}
	m_ApplyList.push_back(pMessage->_senderId);
	this->m_pContactDlg->notifyApplyContact( pMessage->_senderId, pMessage->name, pMessage->groupId, m_pServer );
}

void ContactMgr::AddGroup(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::AddGroup* pMessage = (LyvcMessage::AddGroup*)pBaseMessage;
	// 判断父组是否存在
	ContactGroup* pParent = this->findContactGroup( pMessage->parentGroupId, "", pMessage->isCommon );
//	ASSERT( pParent != NULL );
	if( pParent == NULL )
		return;
	// 判断是否有相同的组存在
//	ContactGroup* pGroup = this->findContactGroup( pMessage->groupId, pMessage->name, pMessage->isCommon );
//	ASSERT( pGroup == NULL );
//	if( pGroup != NULL )
//		return;
    ContactGroup* pContactGroup = new ContactGroup( pMessage->groupId, pMessage->name, pMessage->isCommon, pParent );
	// 通知界面
	HTREEITEM hItem = this->m_pContactDlg->notifyAddGroup( pContactGroup );
	ASSERT( hItem != NULL );
    // Save this group
	if( hItem == NULL )
	{
		delete pContactGroup; 
		return;
	}
	pContactGroup->m_hItem = hItem;
	pParent->m_subGroups.push_back( pContactGroup );
	this->m_ContactGroups[hItem] = pContactGroup;
}

void ContactMgr::AddContact(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::AddContact* pMessage = (LyvcMessage::AddContact*)pBaseMessage;

	ContactGroup* pContactGroup = this->findContactGroup( pMessage->groupId, "", pMessage->isCommon );
//	ASSERT( pContactGroup != NULL );
    if( pContactGroup == NULL )
		return;
	if( findContact( pMessage->contactId, "", pMessage->isCommon ) )
	{
//		ASSERT(FALSE);
		return;
	}

    DBUser dbUser;
    dbUser.id = pMessage->contactId;
    dbUser.strEmail = pMessage->email;
    dbUser.strFirstName = pMessage->firstName;
    dbUser.strLastName = pMessage->lastName;
    dbUser.strName = pMessage->name;
    Contact* pContact = new Contact(dbUser, pContactGroup);
	pContact->m_bIsCommon = pMessage->isCommon;
	pContact->setStatus( pMessage->status );
	pContact->m_pGroup = pContactGroup;
 	// Add the contact to the group which he belongs to.
    pContactGroup->m_Contacts.push_back(pContact);
   // Tell the interface
	HTREEITEM hItem = m_pContactDlg->notifyAddContact( pContact );
	ASSERT( hItem != NULL );
	if( hItem == NULL )
	{
		delete pContact;
		return;
	}
	pContact->m_hItem = hItem;
	m_Contacts[hItem] = pContact;

	//如果用户在申请人列表中，从列表中删除
	if (find(m_ApplyList.begin(), m_ApplyList.end(), pMessage->contactId) != m_ApplyList.end())
	{
		m_ApplyList.remove(pMessage->contactId);
	}
}

void ContactMgr::AgreeContact(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::AgreeContact* pMessage = (LyvcMessage::AgreeContact*)pBaseMessage;
    this->m_pContactDlg->notifyAgreeContact(pMessage->name);
}

void ContactMgr::RejectContact(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::RejectContact* pMessage = (LyvcMessage::RejectContact*)pBaseMessage;
    this->m_pContactDlg->notifyRejectContact(pMessage->name);
}

void ContactMgr::UserNotFoundByEmail(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UserNotFoundByEmail* pMessage = (LyvcMessage::UserNotFoundByEmail*)pBaseMessage;
    this->m_pContactDlg->notifyUserNotFound(pMessage->email);
}

void ContactMgr::RemoveContact(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::RemoveContact* pMessage = (LyvcMessage::RemoveContact*)pBaseMessage;

    // Find this contact
	Contact* pContact = this->findContact( pMessage->contactId, "", false );
//	ASSERT( pContact != NULL );
	if( pContact == NULL )
		return;
	this->removeContact( pContact );
    // Notify UI
	this->m_pContactDlg->notifyRemoveContact( pContact->m_hItem );
	delete pContact;
	pContact = NULL;
}

void ContactMgr::UpdateContactStatus(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::UpdateContactStatus* pMessage = (LyvcMessage::UpdateContactStatus*)pBaseMessage;

	Contact* pContact = this->findContact( pMessage->contactId, "", pMessage->bIsCommon );
//    ASSERT( pContact != NULL );
	if( pContact != NULL )
	{
		this->setContactStatus( pContact, pMessage->status );
	}
}

void ContactMgr::Leaveword(LyvcMessage::BaseMessage* pBaseMessage)
{
	m_nReceivedLeavewords++;
	LyvcMessage::Leaveword* pMsg = (LyvcMessage::Leaveword*) pBaseMessage;
	CTime t = CTime(pMsg->sendDate);
	string sendDate = (t.Format("%Y年%m月%d日 %H:%M")).GetString();
	this->m_pServer->getMainFrame()->getSysinfoDlg()->notifyLeaveword(pMsg->content, pMsg->senderName, m_nReceivedLeavewords, sendDate, m_pServer);
}

void ContactMgr::SearchContactResult(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::SearchContactResult* pMsg = (LyvcMessage::SearchContactResult*) pBaseMessage;
	this->m_pContactDlg->notifySearchContactResult(pMsg->lastName + pMsg->firstName, pMsg->email, pMsg->status, pMsg->contactId);
}

void ContactMgr::UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::UserChannelBroken* pMsg = (LyvcMessage::UserChannelBroken*)pBaseMessage;
	// 更新公共联系人和私有联系人状态,如果需要
	Contact* pContact = this->findContact( pMsg->userId, "", true );
	if(	pContact )
	{
		this->setContactStatus( pContact, CONTACT_OFFLINE );
	}
	pContact = this->findContact( pMsg->userId, "", false );
	if(	pContact )
	{
		this->setContactStatus( pContact, CONTACT_OFFLINE );
	}
}

void ContactMgr::SetContactAdmin(LyvcMessage::BaseMessage* pBaseMessage)
{
	m_bContactAdmin = TRUE;    
}

void ContactMgr::ConferenceNotice(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::ConferenceNotice* pMsg = (LyvcMessage::ConferenceNotice*) pMessage;
	CTime sendDate = CTime(pMsg->sendDate);
	m_pServer->getMainFrame()->getSysinfoDlg()->notifyConfNotice( pMsg->title, pMsg->content, 
									sendDate.Format("%Y年%m月%d日 %H:%M:%S").GetString(), pMsg->conferenceId, m_pServer );
}

void ContactMgr::SystemMessage(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::SystemMessage* pMsg = (LyvcMessage::SystemMessage*) pMessage;
	CTime sendDate = CTime(pMsg->sendDate);
	m_pServer->getMainFrame()->getSysinfoDlg()->notifySystemMsg( pMsg->title, pMsg->content, 
													sendDate.Format("%Y年%m月%d日 %H:%M:%S").GetString(), m_pServer );
}

void ContactMgr::MoveContactToGroup(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::MoveContactToGroup* pMsg = (LyvcMessage::MoveContactToGroup*)pMessage;
	// 判断父组是否存在
	ContactGroup* pGroup = this->findContactGroup( pMsg->newGroupId, "", true);
	if( pGroup == NULL )
		return;
	Contact* contact = this->findContact(pMsg->contactId, "", true, true);
	this->cmdMoveContactToGroup(contact, pGroup);
	this->m_pContactDlg->m_TreeCtrl.UpdateWindow();
}

void ContactMgr::ChangeGroupName(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::ChangeGroupName* pMsg = (LyvcMessage::ChangeGroupName*)pMessage;
	
}

void ContactMgr::setContactStatus( Contact* pContact, int nStatus )
{
	//ASSERT( nStatus != pContact->getStatus() );
	if( nStatus == pContact->getStatus() )
		return;
	pContact->setStatus( nStatus );
	this->m_Contacts.erase( this->m_Contacts.find( pContact->m_hItem ) );
	this->m_pContactDlg->notifyRemoveContact( pContact->m_hItem );
	HTREEITEM hItem = m_pContactDlg->notifyAddContact( pContact );
	ASSERT( hItem != NULL );
	if( hItem )
	{
		pContact->m_hItem = hItem;
		this->m_Contacts[hItem] = pContact;
	}
}

void ContactMgr::removeContactGroup( ContactGroup* pGroup )
{
	ASSERT( pGroup != NULL );
	ContactGroup* pParentGroup = pGroup->m_pParent;
	ASSERT( pParentGroup != NULL );

	ContactGroupMap::iterator itMap = this->m_ContactGroups.find( pGroup->m_hItem );
	ASSERT( itMap != m_ContactGroups.end() );
	if( itMap != m_ContactGroups.end() )
		m_ContactGroups.erase( itMap );

	vector<ContactGroup*>::iterator it = pParentGroup->m_subGroups.begin();
	while( it != pParentGroup->m_subGroups.end() )
	{
		if( (*it) == pGroup )
		{
			pParentGroup->m_subGroups.erase( it );
			return;
		}
		it++;
	}
	ASSERT(FALSE);
}

void ContactMgr::removeContact( Contact* pContact )
{
	ASSERT( pContact != NULL );
	ContactGroup* pGroup = pContact->m_pGroup;
	ASSERT( pGroup != NULL );

	ContactMap::iterator it = this->m_Contacts.find( pContact->m_hItem );
	ASSERT( it != m_Contacts.end() );
	if( it != m_Contacts.end() )
		m_Contacts.erase( it );

	vector<Contact*>::iterator itContact = pGroup->m_Contacts.begin();
    while( itContact != pGroup->m_Contacts.end() )
    {
        if( (*itContact) == pContact )
		{
			pGroup->m_Contacts.erase( itContact );
			return;
		}
        itContact++;
    }
	ASSERT( FALSE );
}

ContactGroup* ContactMgr::findContactGroup( __int64 groupId, string groupName, bool bIsCommon )
{
	ContactGroupMap::iterator it = this->m_ContactGroups.begin();
	while( it != this->m_ContactGroups.end() )
	{
		ContactGroup* pGroup = it->second;
		
		if( ( pGroup->m_Id == groupId || pGroup->m_sName == groupName ) && pGroup->m_bIsCommon == bIsCommon )
		{
			return pGroup;
		}
				
		it++;
	}
	return NULL;
}

ContactGroup* ContactMgr::searchContactGroup( __int64 groupId, string groupName, bool bIsCommon )
{
	ContactGroupMap::iterator it = this->m_ContactGroups.begin();
	while( it != this->m_ContactGroups.end() )
	{
		ContactGroup* pGroup = it->second;
		
		if(pGroup->m_bIsCommon == bIsCommon)
		{
			if(pGroup->m_Id == groupId && pGroup->m_sName == groupName)
				return pGroup;
			
			//判断组名是否与其第一层子组的名称相同
			if(pGroup->m_sName == groupName)
			{
				ContactGroup* pParent = pGroup->m_pParent;
				if(pParent != NULL && pParent->m_Id == groupId )
					return pGroup;	
			}
				
		}
				
		it++;
	}

	return NULL;
}

Contact* ContactMgr::findContact( __int64 contactId, string email, bool bIsCommon, bool bIgnoreIfCommon )
{
	ContactMap::iterator it = this->m_Contacts.begin();
	while( it != this->m_Contacts.end() )
	{
		Contact* pContact = it->second;
		if( ( pContact->getId() == contactId || (pContact->getEmail() == email && email != "")) 
			&& ( bIgnoreIfCommon ? true : pContact->m_bIsCommon == bIsCommon ) )
		{
			return pContact;
		}
		it++;
	}
	return NULL;
}
