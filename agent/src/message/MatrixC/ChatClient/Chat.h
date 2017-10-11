#include "MatrixCLib/MessageTarget.h"

class Chat : public LyvcMessage::MessageTarget 
{
public:
   	Chat(LyvcMessage::MatrixC* pMatrixC);
    void broadcast(LyvcMessage::BaseMessage* pBaseMessage);

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

};
