#include "StdAfx.h"
#include "ConferenceUserMgr.h"
#include "ConferenceUser.h"


ConferenceUserMgr::ConferenceUserMgr()
{
    return;
}

ConferenceUserMgr::~ConferenceUserMgr()
{
	//删除所有的用户
	ConferenceUserMap::iterator it = m_users.begin();
	while(it != m_users.end())
	{
		ConferenceUser* pConferenceUser = it->second;
		delete pConferenceUser;
		it++;
	}
	m_users.clear();
}

void ConferenceUserMgr::addConferenceUser(ConferenceUser* pUser)
{
    m_users.insert( ConferenceUserMap::value_type( pUser->getId(), pUser));
    return;
}

ConferenceUser* ConferenceUserMgr::getConferenceUserByID(USERID userId)
{
	ConferenceUserMap::const_iterator i = m_users.find(userId);
	if (i != m_users.end())
    {
		return i->second;
    }
    else
    {
        _ASSERTE(FALSE);
	    return NULL;
    }
}

bool ConferenceUserMgr::isUserInConference(USERID  userId)
{
	ConferenceUserMap::const_iterator i = m_users.find(userId);
    return i != m_users.end();
}

void ConferenceUserMgr::deleteConferenceUser(USERID userId)
{
	ConferenceUserMap::iterator it = m_users.find(userId);
	if( it != m_users.end())
	{
		ConferenceUser* pConferenceUser = it->second;
		delete pConferenceUser;
		m_users.erase(it);
	}
	else
	{
        _ASSERTE(FALSE);
	}
    return;
}

void ConferenceUserMgr::getConferenceUserIds(vector<__int64>* v, bool bIncludingVirtualUser)
{
	ConferenceUserMap::iterator it = m_users.begin();
	while(it != m_users.end())
	{
		if( it->second->getUserType() == ConferenceUser::NormalUser 
			|| bIncludingVirtualUser )
		{
			__int64 userId = it->first;
			v->push_back(userId);
		}
		it++;
	}
}

void ConferenceUserMgr::getVirtualConferenceUserIds(USERID userId, int count, vector<__int64>* v)
{
	v->clear();
	for(int i = 0;i < count;i++){
		v->push_back(100000000000000000L + userId * 10 + i + 1);
	}
}

LONG ConferenceUserMgr::getVirtualConferenceUserChannel(USERID userId){
	return (userId - 100000000000000000L) % 10;
}

USERID ConferenceUserMgr::getVirtualUserId(USERID userId, LONG handler)
{
	return 100000000000000000L + userId * 10 + handler;
}

USERID ConferenceUserMgr::getRealIdByVirtualId(USERID virtualId)
{
	return (virtualId - 100000000000000000L) / 10;
}