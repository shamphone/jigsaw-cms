#include "StdAfx.h"
#include "ConferenceRole.h"

ConferenceRole::ConferenceRole(__int64 nID, string strName) 
    : m_nID(nID), m_strName(strName)
{
}

ConferenceRole::~ConferenceRole()
{
}

__int64 ConferenceRole::getID() const
{
    return m_nID;
}

string ConferenceRole::getName() const
{
    return m_strName;
}

void ConferenceRole::addRight(__int64 nRightID)
{
    m_rights.push_back(nRightID);
}

vector<__int64> ConferenceRole::getRights()
{
	return m_rights;
}