#include "stdafx.h"
#include "Flvcc.h"
#include "YuntaiMgr.h"
#include ".\yuntaimgr.h"
#include "RunningConference.h"

#include "..\..\UI\meetingroom\roommainfrm.h"
#include "..\..\UI\meetingroom\YuntaiDlg.h"

#include "..\..\..\message\MatrixC\MatrixCLib\MatrixC.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\CameraMoveUp.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\CameraMoveDown.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\CameraMoveLeft.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\CameraMoveRight.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\CameraZoomIn.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\CameraZoomOut.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\YuntaiHolderBroadcast.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\UserJoinConference.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\UserQuitConference.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\UserChannelBroken.h"
#include "..\..\..\message\MatrixC\MatrixCLib\message\JoinConferenceResponse.h"


YuntaiMgr::YuntaiMgr(LyvcMessage::MatrixC* pMatrixC, CMeetingRoomFrame* pRoom, RunningConference* pConference) : LyvcMessage::MessageTarget(pMatrixC)
{
	this->m_bIfHasYuntai = false;
	m_pConference = pConference;
	m_pRoom = pRoom;
    m_pMatrixC->registerMessageHandler(LyvcMessage::JoinConferenceResponse::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(YuntaiMgr::JoinConferenceResponse));
}

YuntaiMgr::~YuntaiMgr(void)
{
	m_pMatrixC->removeObjectMessageHandler(this);
}

bool YuntaiMgr::create()
{
	return true;
}

void YuntaiMgr::destroy()
{
	for( int i = 0; i < 4; i++ )
	{
		if( m_cnComm[i].IsOpen() )
			m_cnComm[i].Close();
	}
}

void YuntaiMgr::moveUp(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, START_UP, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::moveDown(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, START_DOWN, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::moveLeft(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, START_LEFT, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::moveRight(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, START_RIGHT, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::zoomIn(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, START_ZOOMIN, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::zoomOut(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, START_ZOOMOUT, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::stopAction(int nAddress, int commPort)
{
	unsigned char buffer[7];
	this->getCommand(nAddress, STOP_ACTION, buffer, 7);
	m_cnComm[commPort-1].Write((char*)buffer, 7);
}

void YuntaiMgr::JoinConferenceResponse(LyvcMessage::BaseMessage* pBaseMessage)
{
 	if( this->m_bIfHasYuntai )
	{
		m_pRoom->addYuntaiHolder( this->m_pConference->getSelfId(), getCommPorts() );
	}
}

void YuntaiMgr::getCommand(int nAddress, const unsigned char* command, unsigned char* buffer, int nLen)
{
	memcpy(buffer, command, nLen);
	buffer[1] = nAddress;
}

int YuntaiMgr::getCommPorts()
{
	int n = 0;
	for( int i = 0; i < 4; i++ )
	{
		if( m_cnComm[i].Open( i+1, 2400 ) )
		{
			n = n + ( 1 << i );
		}
	}
	return n;
}