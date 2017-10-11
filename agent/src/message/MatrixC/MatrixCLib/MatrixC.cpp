#include "StdAfx.h"
#include "MatrixC.h"
#include "Imp/MatrixCImp.h"

const char* LyvcMessage::MatrixC::LYVC_MESSAGE_HEADER = "Protocol Version 2.0\r\nContent-Length:%d\r\n\r\n";

LyvcMessage::MatrixC* LyvcMessage::MatrixC::getInstance()
{
    return new LyvcMessage::MatrixCImp();
}

void LyvcMessage::MatrixC::releaseInstance(LyvcMessage::MatrixC* pMatrixC)
{
    LyvcMessage::MatrixCImp* p = (LyvcMessage::MatrixCImp*)pMatrixC;
    delete p;
}
