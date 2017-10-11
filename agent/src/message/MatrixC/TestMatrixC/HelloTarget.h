#pragma once
#include "MatrixCLib/MessageTarget.h"
#include "MatrixCLib/message/BaseMessage.h"

using namespace LyvcMessage;

class HelloTarget :
    public MessageTarget
{
public:
    HelloTarget(MatrixC* pMatrixC);
    ~HelloTarget(void);

    // 调用该函数完成初始化工作
	virtual bool create()
    {
        return true;
    }

    // 调用该函数完成清理工作
	virtual void destroy()
    {
        return;
    }

public:
    void LoginHandler(BaseMessage* pMessage);

};
