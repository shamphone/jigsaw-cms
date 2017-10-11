#include "stdafx.h"

#include "structure.h"
#include "WhiteboardDlg.h"

#define WHITEBOARD_BUF_SIZE 1500
using namespace rdr;

//-----------------------------------
// 以下为WhiteboardOutStream类方法实现
//----------------------------------

WhiteboardOutStream::WhiteboardOutStream(CWhiteboardDlg* pDlg)
{
	bufSize = WHITEBOARD_BUF_SIZE;

	ptr = start = new U8[WHITEBOARD_BUF_SIZE];
	end = start + WHITEBOARD_BUF_SIZE;

	m_pWhiteboardDlg = pDlg;
}

WhiteboardOutStream::~WhiteboardOutStream(void)
{
	delete [] start;
}

void WhiteboardOutStream::flush()
{
	m_pWhiteboardDlg->sendWhiteboardData( (char*)start, ptr - start );
	ptr = start + 4;
}

int WhiteboardOutStream::overrun(int itemSize, int nItems)
{ 
	_ASSERTE(FALSE);
	return 0;
}

//-----------------------------------
// 以下为WhiteboardInStream类方法实现
//----------------------------------

WhiteboardInStream::WhiteboardInStream(void) 
{
	bufSize = WHITEBOARD_BUF_SIZE;
	ptr = end = start = new U8[WHITEBOARD_BUF_SIZE];
}

WhiteboardInStream::~WhiteboardInStream(void)
{
	delete [] start;
}

void WhiteboardInStream::setData(const void* data, int length)
{
	memcpy(start, data, length);
	ptr = start;
	end = start + length;
}

int WhiteboardInStream::overrun(int itemSize, int nItems, bool wait)
{
	_ASSERTE(FALSE);
	return nItems;
}
