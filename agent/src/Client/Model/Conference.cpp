#include "StdAfx.h"
#include "Conference.h"

Conference::Conference(DBConference dbConference) : m_dbConference(dbConference)
{
    m_status = Inactive;
    m_nUserCount = 0;
	m_hItem = NULL;
}

Conference::~Conference()
{
}

Conference::ConferenceStatus Conference::getStatus()
{
	return m_status;
}

void Conference::setStatus(ConferenceStatus status)
{
	this->m_status = status;
}
