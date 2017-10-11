#include "stdafx.h"
#include "TCPForward.h"
#include "AcceptThread.h"
#include "common/common/Log/Log.h"
#include "TCPUser.h"

TCPForward::TCPForward()
{
    m_pAcceptThread = NULL;
}

TCPForward::~TCPForward()
{
}

BOOL TCPForward::start(int port)
{
	// Create the RouteTable
	::InitializeCriticalSection(&m_cs);

    this->m_pAcceptThread = new AcceptThread(this);
    if(!this->m_pAcceptThread->start(port))
    {
		FVS_DEBUG("m_pAcceptThread->start() failed\n");
        delete this->m_pAcceptThread;
        this->m_pAcceptThread = NULL;
        return FALSE;
    }

	return TRUE;
}

void TCPForward::stop()
{
	// Stop accept thread
    if( this->m_pAcceptThread )
    {
        this->m_pAcceptThread->stop();
		delete this->m_pAcceptThread;
        this->m_pAcceptThread = NULL;
    }

	// Stop all user thread
	TCPUserMap::iterator itUser;
	for( itUser = this->m_users.begin(); itUser != this->m_users.end(); itUser++)
	{
		itUser->second->Destroy();
		delete itUser->second;
	}
	this->m_users.clear();

	// Clear the relation
	TCPUserRelationMap::iterator itRelation;
	for( itRelation = this->m_desktopRelations.begin(); itRelation != this->m_desktopRelations.end(); itRelation++)
	{
		delete itRelation->second;
	}
	this->m_desktopRelations.clear();

	for( itRelation = m_whiteboardRelations.begin(); itRelation != m_whiteboardRelations.end(); itRelation++)
	{
		delete itRelation->second;
	}
	this->m_whiteboardRelations.clear();

	::DeleteCriticalSection(&m_cs);

    return;
}

void TCPForward::addUser(__int64 userId)
{
    ::EnterCriticalSection(&m_cs);

    TCPUser* pTCPUser = new TCPUser(userId, this);
	if( !pTCPUser->Create())
	{
		delete pTCPUser;
		return;
	}
    m_users.insert( TCPUserMap::value_type(userId, pTCPUser));

    list<__int64>* pRelation = new list<__int64>;
    m_desktopRelations.insert( TCPUserRelationMap::value_type(userId, pRelation));

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG1("TCPForward addUser: %I64d\n", userId);
}

void TCPForward::removeUser(__int64 userid)
{
    ::EnterCriticalSection(&m_cs);

    // from every user's peer list, remove this user 
    TCPUserRelationMap::iterator itRelation;
    for( itRelation = m_desktopRelations.begin(); itRelation != m_desktopRelations.end(); itRelation++)
    {
        if(itRelation->first == userid)
        {
            continue;
        }

        list<__int64>* pRelation = itRelation->second;
        pRelation->remove(userid);
    }

    for( itRelation = m_whiteboardRelations.begin(); itRelation != m_whiteboardRelations.end(); itRelation++)
    {
        list<__int64>* pRelation = itRelation->second;
        pRelation->remove(userid);
    }

    // Remove this user
    itRelation = m_desktopRelations.find(userid);
    _ASSERTE(itRelation != m_desktopRelations.end());
    delete itRelation->second;
    m_desktopRelations.erase(itRelation);

    TCPUserMap::iterator itUser = m_users.find(userid);
    _ASSERTE(itUser != m_users.end());
	itUser->second->Destroy();
    delete itUser->second;
    m_users.erase(itUser);

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG1("TCPForward removeUser: %I64d\n", userid);
}

void TCPForward::addDesktopRelation(__int64 from, __int64 to)
{
    ::EnterCriticalSection(&m_cs);

    TCPUserRelationMap::iterator itRelation = m_desktopRelations.find(from);
    _ASSERTE(itRelation != m_desktopRelations.end());

    list<__int64>* pRelation = itRelation->second;
    pRelation->push_back(to);

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG2("TCPForward addMediaRelation, from: %I64d, to: %I64d\n", from, to);
}

void TCPForward::removeDesktopRelation(__int64 from, __int64 to)
{
    ::EnterCriticalSection(&m_cs);

    TCPUserRelationMap::iterator itRelation = m_desktopRelations.find(from);
    _ASSERTE(itRelation != m_desktopRelations.end());

    list<__int64>* pRelation = itRelation->second;
    pRelation->remove(to);

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG2("TCPForward removeMediaRelation, from: %I64d, to: %I64d\n", from, to);
}

void TCPForward::addWhiteboardRelation(__int64 userId, __int64 confId)
{
    ::EnterCriticalSection(&m_cs);

    TCPUserRelationMap::iterator itRelation = m_whiteboardRelations.find( confId );
    if( itRelation == m_whiteboardRelations.end() )
	{
		list<__int64>* pRelation = new list<__int64>;
		m_whiteboardRelations.insert( TCPUserRelationMap::value_type(confId, pRelation));
		pRelation->push_back( userId );
	}
	else
	{
		list<__int64>* pRelation = itRelation->second;
		if( ::find( pRelation->begin(), pRelation->end(), userId ) == pRelation->end() )
		{
            pRelation->push_back(userId);
		}
		else
		{
			_ASSERTE(FALSE);
		}
	}

	::LeaveCriticalSection(&m_cs);

	FVS_DEBUG2("TCPForward addWhiteboardRelation, userId: %I64d, confId: %I64d\n", userId, confId);
}

void TCPForward::removeWhiteboardRelation(__int64 userId, __int64 confId)
{
    ::EnterCriticalSection(&m_cs);

    TCPUserRelationMap::iterator itRelation = m_whiteboardRelations.find( confId );
    if( itRelation != m_whiteboardRelations.end() )
	{
		list<__int64>* pRelation = itRelation->second;
		pRelation->remove( userId );
	}
	else
	{
		_ASSERTE(FALSE);
	}

    ::LeaveCriticalSection(&m_cs);

	FVS_DEBUG2("TCPForward removeWhiteboardRelation, userId: %I64d, confId: %I64d\n", userId, confId);
}
