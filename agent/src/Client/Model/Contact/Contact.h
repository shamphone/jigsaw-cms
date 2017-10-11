#pragma once

#include "../DBUser.h"
#include "..\..\UI\MainFrm\UserDefinedMessage.h"

class ContactGroup;

class Contact
{
protected:

    // Contact data.
	DBUser m_dbUser;

    // Contact status
    // 0: Offline
    // 1: Online
    int    m_nStatus;

public:
	// 是否是公共联系人
	bool			m_bIsCommon;

	// 所在组
	ContactGroup*	m_pGroup;

	// 对应的树节点
	HTREEITEM		m_hItem;

	Contact(const DBUser& dbu, ContactGroup* pGroup)
    {
        m_dbUser	= dbu;
        m_nStatus	= CONTACT_OFFLINE;
		m_pGroup	= pGroup;
    };

	int getStatus() const
    {
        return m_nStatus;
    }

	void setStatus(int s)
    {
        m_nStatus = s;
    };

	USERID getId() const
    {
        return m_dbUser.id;
    };	

	string getName()const 
    {
        return m_dbUser.strName;
    };

	string getRealName() const
    {
        return m_dbUser.getRealName();
    };

	string getEmail() const
    {
        return m_dbUser.strEmail;
    };
	string getFirstName() const
	{
		return m_dbUser.strFirstName;
	}
	string getLastName() const
	{
		return m_dbUser.strLastName;
	}
};

