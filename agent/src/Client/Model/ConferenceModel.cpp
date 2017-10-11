#include "stdafx.h"
#include "ConferenceModel.h"


ConferenceModel::ConferenceModel(__int64 nID, string strName) 
    : m_nID(nID), m_strName(strName)
{
}

ConferenceModel::~ConferenceModel()
{
}

__int64 ConferenceModel::getID()
{
    return m_nID;
}

string ConferenceModel::getName()
{
    return m_strName;
}

void ConferenceModel::addRole(ConferenceRole& conferenceRole)
{
    m_roles.push_back(conferenceRole);
}

string ConferenceModel::getRoleName(__int64 nRoleID)
{
    vector<ConferenceRole>::iterator it;
	for(it=m_roles.begin(); it!=m_roles.end(); it++)
    {
        if( it->getID() == nRoleID )
        {
            return it->getName();
        }
    }

    _ASSERTE(FALSE);
	return "";
}

ConferenceRole* ConferenceModel::getRole(__int64 nRoleID)
{
    vector<ConferenceRole>::iterator it;
	for(it=m_roles.begin(); it!=m_roles.end(); it++)
    {
        if( it->getID() == nRoleID )
        {
            return &(*it);
        }
    }

    _ASSERTE(FALSE);
	return NULL;
}

