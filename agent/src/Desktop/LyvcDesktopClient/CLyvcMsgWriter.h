#pragma once

#include "rfb\CMsgWriter.h"

namespace LyvcMessage
{
	class MatrixC;
}

class CLyvcMsgWriter : public rfb::CMsgWriter
{
public:
	CLyvcMsgWriter(LyvcMessage::MatrixC* pMatrixC);      

	~CLyvcMsgWriter(void);

public:
    virtual void writeKeyEvent(rdr::U32 key, bool down, __int64 uid) ;
    virtual void writePointerEvent(int x, int y, int buttonMask, __int64 uid);

private:
	LyvcMessage::MatrixC* m_pMatrixC;

};
