#pragma once

class Contact;

class ContactGroup
{
public:
	ContactGroup( __int64 id, string name, bool bIsCommon, ContactGroup* pParent )
	{
		m_Id		= id;
		m_sName		= name;
		m_bIsCommon = bIsCommon;
		m_pParent	= pParent;
	};
	// 是否是公共联系人组
	bool					m_bIsCommon;
	// 组id
    __int64					m_Id;
	// 组名
    string					m_sName;
	// 对应的树节点
	HTREEITEM				m_hItem;
	// 父组
	ContactGroup*			m_pParent;
	// 组中的联系人
    vector<Contact*>		m_Contacts;
	// 子组
	vector<ContactGroup*>	m_subGroups;
};
