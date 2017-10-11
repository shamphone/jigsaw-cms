#pragma once

#include "../../message/MatrixC/MatrixCLib/MessageTarget.h"

class SMsgHandler;

class SMsgReader : public LyvcMessage::MessageTarget
{
public:
	//构造函数
	SMsgReader(LyvcMessage::MatrixC* pMatrixC, SMsgHandler* pMsgHandler);
	~SMsgReader(void);

	bool create() 
    {
        return true;
    }

	void destroy()
    {
        return;
    }

private:
	//读取键盘和鼠标事件，并由handler处理
	void readKeyEvent(LyvcMessage::BaseMessage* pMessage);
	void readMouseEvent(LyvcMessage::BaseMessage* pMessage);

private:
	//处理客户端传来事件的对象指针，指向派生自SMsgHandler的LyvcDesktopServer
	SMsgHandler* m_pMsgHandler;
};
