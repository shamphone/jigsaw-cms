#include "StdAfx.h"
#include ".\clyvcmsgwriter.h"

#include "../../message/MatrixC/MatrixCLib/MatrixC.h"
#include "../../message/MatrixC/MatrixCLib/message/DesktopClientKeyEvent.h"
#include "../../message/MatrixC/MatrixCLib/message/DesktopClientMouseEvent.h"


CLyvcMsgWriter::CLyvcMsgWriter(LyvcMessage::MatrixC* pMatrixC)
{
	m_pMatrixC = pMatrixC;
}

CLyvcMsgWriter::~CLyvcMsgWriter(void)
{
}

void CLyvcMsgWriter::writeKeyEvent(rdr::U32 key, bool down, __int64 uid)
{
	LyvcMessage::DesktopClientKeyEvent keyEvent;
	keyEvent.downFlag = down;
	keyEvent.key = key;
	keyEvent.receiverId = uid;

	m_pMatrixC->sendMessage(&keyEvent);
}

void CLyvcMsgWriter::writePointerEvent(int x, int y, int buttonMask, __int64 uid)
{
	LyvcMessage::DesktopClientMouseEvent mouseEvent;
	mouseEvent.buttonMask = buttonMask;
	mouseEvent.x = x;
	mouseEvent.y = y;
	mouseEvent.receiverId = uid;

	m_pMatrixC->sendMessage(&mouseEvent);
}
