#include "StdAfx.h"
#include ".\smsgreader.h"
#include "SMsgHandler.h"

#include "../../message/MatrixC/MatrixCLib/MatrixC.h"
#include "../../message/MatrixC/MatrixCLib/message/DesktopClientKeyEvent.h"
#include "../../message/MatrixC/MatrixCLib/message/DesktopClientMouseEvent.h"

SMsgReader::SMsgReader(LyvcMessage::MatrixC* pMatrixC, SMsgHandler* pMsgHandler) : MessageTarget(pMatrixC)
{
	m_pMsgHandler = pMsgHandler;
	m_pMatrixC->registerMessageHandler(LyvcMessage::DesktopClientKeyEvent::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(SMsgReader::readKeyEvent));
	m_pMatrixC->registerMessageHandler(LyvcMessage::DesktopClientMouseEvent::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(SMsgReader::readMouseEvent));
}

SMsgReader::~SMsgReader(void)
{
	m_pMatrixC->removeObjectMessageHandler(this);
	m_pMsgHandler = 0;
}

void SMsgReader::readKeyEvent(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::DesktopClientKeyEvent* pKeyEvent = (LyvcMessage::DesktopClientKeyEvent*) pMessage;

	bool downFlag = pKeyEvent->downFlag;
	int key = pKeyEvent->key;

	m_pMsgHandler->setKey(downFlag, key);
}

void SMsgReader::readMouseEvent(LyvcMessage::BaseMessage* pMessage)
{
	LyvcMessage::DesktopClientMouseEvent* pMouseEvent = (LyvcMessage::DesktopClientMouseEvent*) pMessage;

	int mask = pMouseEvent->buttonMask;
	int x = pMouseEvent->x;
	int y = pMouseEvent->y;

	m_pMsgHandler->setMouse(mask, x, y);
}
