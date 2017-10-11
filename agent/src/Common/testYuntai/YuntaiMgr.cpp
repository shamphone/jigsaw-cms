#include "stdafx.h"

#include "YuntaiMgr.h"
#include ".\yuntaimgr.h"


YuntaiMgr::YuntaiMgr()
{
}

YuntaiMgr::~YuntaiMgr(void)
{
	m_cnComm.Close();
}

bool YuntaiMgr::init()
{
	return m_cnComm.Open( 1, 2400 );
}

void YuntaiMgr::moveUp()
{
	m_cnComm.Write((char*)START_UP, 7);
}

void YuntaiMgr::moveDown()
{
	m_cnComm.Write((char*)START_DOWN, 7);
}

void YuntaiMgr::moveLeft()
{
	m_cnComm.Write((char*)START_LEFT, 7);
}

void YuntaiMgr::moveRight()
{
	m_cnComm.Write((char*)START_RIGHT, 7);
}

void YuntaiMgr::focusIn()
{
	m_cnComm.Write((char*)START_FOCUSIN, 7);
	Sleep(150);
	m_cnComm.Write((char*)STOP_FOCUSIN, 7);
}

void YuntaiMgr::focusOut()
{
	m_cnComm.Write((char*)START_FOCUSOUT, 7);
	Sleep(150);
	m_cnComm.Write((char*)STOP_FOCUSOUT, 7);
}

void YuntaiMgr::stopMoveUp()
{
	m_cnComm.Write((char*)STOP_UP, 7);
}
void YuntaiMgr::stopMoveDown()
{
	m_cnComm.Write((char*)STOP_DOWN, 7);
}
void YuntaiMgr::stopMoveLeft()
{
	m_cnComm.Write((char*)STOP_LEFT, 7);
}
void YuntaiMgr::stopMoveRight()
{
	m_cnComm.Write((char*)STOP_RIGHT, 7);
}
