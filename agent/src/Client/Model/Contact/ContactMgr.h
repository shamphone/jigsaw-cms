#pragma once

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

class CContactDlg;
class Contact;
class ContactGroup;
class LServer;

class ContactMgr : public LyvcMessage::MessageTarget 
{
public:
	ContactMgr( LyvcMessage::MatrixC* pMatrixC, LServer* pServer );
	~ContactMgr();

	bool create() { return true; };
	void destroy() { };

    // 根据组名得到组
	ContactGroup* getContactGroupByName( string name, bool bIsCommon = false );

	// 根据联系人id得到email
	string getEmailById( __int64 contactId );

	// 根据id得到realname
	string getRealnameById( __int64 contactId );

	// 根据状态得到联系人
	void getContacts( vector<Contact*>& v, int status );

	// 得到所有私有联系人组
	void getPrivateGroupNames( vector<string>& v );

    // 获取Default组的ID
    __int64 getDefaultGroupId();

	// 根据树节点获取联系人
    Contact* getContactByTreeItem( HTREEITEM hItem );

    // 根据树节点获取组
    ContactGroup* getContactGroupByTreeItem( HTREEITEM hItem );

    // 设置联系人对话框
	void setContactDlg( CContactDlg* pContactDlg );

	// 判断是否是私有联系人
	bool isContactByEmail( string email );
	bool isContactById( __int64 uid );

	// 判断是否存在指定名字的组
	bool isGroupExist(__int64 groupId, string name, bool bIsCommon = false );

	// 判断是否存在指定名字的组
	bool isGroupExists(ContactGroup* pGroup, string name);

	// 是否是联系人管理员
	BOOL isContactAdmin();

//消息发送
public:
	// 发送添加联系人
	void cmdAddContact( Contact* pContact, ContactGroup* pGroup );

	// 发送申请加为联系人
	void cmdApplyContact(__int64 uid, string myName, __int64 groupId);

	// 发送留言
	void cmdSendLeaveword(__int64 contactId, string words, string myName);

    // 发送Email查找联系人
    void cmdSearchContact(string email);

    // 发送同意申请
    void cmdAgreeContact(__int64 contactId, string name, __int64 groupId);

    // 发送拒绝申请
    void cmdRejectContact(__int64 contactId, string name);

    // 发送从组中删除一个联系人
    void cmdRemoveContact( Contact* pContact );

    // 发送删除一个组
    void cmdRemoveGroup( ContactGroup* pGroup );

    // 发送添加一个组
    void cmdAddGroup(string groupName, __int64 groupId);

    // 发送重命名一个组
    void cmdRenameGroup( ContactGroup* pGroup, string newName );

    // 发送将联系人从一个组移到另一个组
    void cmdMoveContactToGroup( Contact* pContact, ContactGroup* pToGroup );

	// 发送复制私有联系人,
	// 1) receiverId = 0,使联系人管理员的所有联系人互相成为联系人;
	// 2) recieverId != 0, bIsGroupId = false, 使联系人管理员的所有联系人与receiverId用户成为联系人
	// 3) recieverId != 0, bIsGroupId = true, 使联系人管理员的所有联系人与receiverId组下的联系人互相成为联系人
	void cmdCopyContact( __int64 receiverId = 0, bool bIsGroupId = false );

// 消息处理
public:    
    void UserDetail(LyvcMessage::BaseMessage* pMessage);
    void DefaultGroupId(LyvcMessage::BaseMessage* pBaseMessage);
    void ApplyContact(LyvcMessage::BaseMessage* pBaseMessage);
    void AddGroup(LyvcMessage::BaseMessage* pBaseMessage);
    void AddContact(LyvcMessage::BaseMessage* pBaseMessage);
    void AgreeContact(LyvcMessage::BaseMessage* pBaseMessage);
    void RejectContact(LyvcMessage::BaseMessage* pBaseMessage);
    void UserNotFoundByEmail(LyvcMessage::BaseMessage* pBaseMessage);
    void RemoveContact(LyvcMessage::BaseMessage* pBaseMessage);
    void UpdateContactStatus(LyvcMessage::BaseMessage* pBaseMessage);
	void Leaveword(LyvcMessage::BaseMessage* pBaseMessage);
	void SearchContactResult(LyvcMessage::BaseMessage* pBaseMessage);
	void UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage);
	void SetContactAdmin(LyvcMessage::BaseMessage* pBaseMessage);
	void ConferenceNotice(LyvcMessage::BaseMessage* pMessage);
	void SystemMessage(LyvcMessage::BaseMessage* pMessage);
	//添加消息处理方法
	void MoveContactToGroup(LyvcMessage::BaseMessage* pMessage);	//移动组
	void ChangeGroupName(LyvcMessage::BaseMessage* pMessage);		//修改组名
//	void RemoveContact(LyvcMessage::BaseMessage* pMessage);			//删除用户

public:
    CContactDlg* m_pContactDlg;

private:
	// 所在服务器
	LServer* m_pServer;
	// 申请加自己为联系人的用户
	list<__int64> m_ApplyList;

    // 对应的联系人对话框描述
//    CContactDlg* m_pContactDlg;

 	// 联系人和组,与ContactDlg的树控件上的节点对应
	typedef map<HTREEITEM, ContactGroup*>	ContactGroupMap;
	typedef map<HTREEITEM, Contact*>		ContactMap;
	ContactGroupMap m_ContactGroups;
	ContactMap		m_Contacts;
	
	// 收到的留言条数
	int m_nReceivedLeavewords;

	// 联系人管理员标志
	BOOL m_bContactAdmin;

	// 默认组id
	__int64 m_DefaultGroupId;

private:
	// 设置联系人状态
	void setContactStatus( Contact* pContact, int nStatus );
	// 删除组
	void removeContactGroup( ContactGroup* pGroup );
	// 删除联系人
	void removeContact( Contact* pContact ); 
	// 根据组id或组名查找组
	ContactGroup* findContactGroup( __int64 groupId, string groupName, bool bIsCommon );
	// 根据组id或组名查找组
	ContactGroup* searchContactGroup( __int64 groupId, string groupName, bool bIsCommon );
	// 根据id或email查找联系人
	Contact* findContact( __int64 contactId, string email, bool bIsCommon, bool bIgnoreIfCommon = false );
};
