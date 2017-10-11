#pragma once

#include <stdio.h>
#include <string>
using namespace std;

#include "MatrixCLib/message/BaseMessage.h"
#include "MatrixCLib/message/Login.h"
#include "MatrixCLib/MessageTarget.h"
using namespace LyvcMessage;

class SimpleTarget : public MessageTarget
{
public:
    SimpleTarget(MatrixC* pMatrixC);
    ~SimpleTarget(void);

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

    void HandlerOne(BaseMessage* pMessage);

    void HandlerTwo(BaseMessage* pMessage); 

    void HandlerThree(BaseMessage* pMessage);
};
