#include <string>
using namespace std;

#include "MatrixCLib/message/BaseMessage.h"
#include "MatrixCLib/MessageTarget.h"
using namespace LyvcMessage;

class ExceptionTarget :
    public MessageTarget
{
public:
    ExceptionTarget(MatrixC* pMatrixC);
    ~ExceptionTarget(void);

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

    void ExceptionHandler(BaseMessage* pMessage);
};
