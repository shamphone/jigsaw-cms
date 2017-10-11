#include "stdafx.h"
#include "FvsmUser.h"
#include "common/common/MediaPacket/udp_media_def.h"

FvsmUser::FvsmUser()
{	
	m_userId = 0;
    memset(&m_udpAddr, 0, sizeof(SOCKADDR));
}

FvsmUser::~FvsmUser()
{
}

BOOL FvsmUser::addReceiver(__int64 userId, int dataType)
{
	if (dataType == FVS_MSG_AUDIO)
	{
        // ASSERT the user does not exist
        list<__int64>::iterator it = find(m_audioReceivers.begin(), m_audioReceivers.end(), userId);
        _ASSERTE( it == m_audioReceivers.end());

        // Add this user
	    m_audioReceivers.push_back(userId);
	}

	else if (dataType == FVS_MSG_VIDEO)
	{
        // ASSERT the user does not exist
        list<__int64>::iterator it = find(m_videoReceivers.begin(), m_videoReceivers.end(), userId);
        _ASSERTE( it == m_videoReceivers.end());

        // Add this user
		m_videoReceivers.push_back(userId);		
	}
	return TRUE;
}

BOOL FvsmUser::removeReceiver(__int64 userId, int dataType)
{
	if (dataType == FVS_MSG_AUDIO)
    {
        // ASSERT this user is in the receive list
        list<__int64>::iterator it = find(m_audioReceivers.begin(), m_audioReceivers.end(), userId);
        _ASSERTE( it != m_audioReceivers.end());

        // Remove this user
	    m_audioReceivers.erase(it);
    }
	else if (dataType == FVS_MSG_VIDEO)
	{
        // ASSERT this user is in the receive list
        list<__int64>::iterator it = find(m_videoReceivers.begin(), m_videoReceivers.end(), userId);
        _ASSERTE( it != m_videoReceivers.end());

        // Remove this user
	    m_videoReceivers.erase(it);
	}
	
	return TRUE;
}

BOOL FvsmUser::isReceiveAudioFromUser(__int64 userId)
{
    return find(m_audioReceivers.begin(), m_audioReceivers.end(), userId) != m_audioReceivers.end();
}

BOOL FvsmUser::isReceiveVideoFromUser(__int64 userId)
{
    return find(m_videoReceivers.begin(), m_videoReceivers.end(), userId) != m_videoReceivers.end();
}

