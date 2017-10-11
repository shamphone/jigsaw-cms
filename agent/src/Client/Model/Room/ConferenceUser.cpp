#include "stdafx.h"
#include "ConferenceUser.h"
#include "model/room/RunningConference.h"

ConferenceUser::ConferenceUser(const DBUser& dbUser) : m_baseInfo(dbUser)
{
	m_nUserType = NormalUser;
}

ConferenceUser::~ConferenceUser()
{
}
